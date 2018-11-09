/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2342.automodes;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2342.util.Constants;

public class RealMotionProfile extends Command {

  private double[][] _profile;

  private MotionProfileStatus _status = new MotionProfileStatus();

  private WPI_TalonSRX _talon;

  private int _loopTimeout = -1;
  private int _state = 0;

  private boolean _finished = false;

  class PeriodicRunnable implements java.lang.Runnable {
    public void run() {  _talon.processMotionProfileBuffer();    }
  }

  private Notifier _notifier = new Notifier(new PeriodicRunnable());

  public RealMotionProfile(double[][] profile, WPI_TalonSRX talon) {
    _talon = talon;
    _profile = profile;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    _talon.changeMotionControlFramePeriod(5);
    _notifier.startPeriodic(0.005);
    _talon.set(ControlMode.MotionProfile, 0);

    setupTrajectory();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    _talon.getMotionProfileStatus(_status);
    
    if(_state == 0) {
      if(_status.btmBufferCnt > Constants.MIN_PROFILE_POINTS) {
        //we have enough points to start
        _state = 1;
      }
    } else if(_state == 1) {
      _talon.set(ControlMode.MotionProfile, 1);

      if(_status.activePointValid && _status.isLast) {
        _finished = true;
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return _finished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    _notifier.stop();
    _talon.set(ControlMode.PercentOutput, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }

  private void setupTrajectory() {
    int totalCnt = _profile.length;

    TrajectoryPoint point = new TrajectoryPoint();

    if(_status.hasUnderrun) {
      _talon.clearMotionProfileHasUnderrun(10);
    }

    _talon.clearMotionProfileTrajectories();
    //we assume that the trajectory has a constant period equal to first duration
    _talon.configMotionProfileTrajectoryPeriod((int) _profile[0][2], 10);

    for(int i=0;i<totalCnt;i++) {
      point.position = _profile[i][0];
      point.velocity = _profile[i][1];
			point.profileSlotSelect0 = 0; /* which set of gains would you like to use? */

			point.zeroPos = (i == 0);

			point.isLastPoint = (i + 1 == totalCnt);
      System.out.println(point);
			_talon.pushMotionProfileTrajectory(point);
    }
  }
}
