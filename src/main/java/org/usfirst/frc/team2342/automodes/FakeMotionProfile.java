/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2342.automodes;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

public class FakeMotionProfile extends Command {

  WPI_TalonSRX talon;

  private float cruiseVelocity;
  private int accelTimeMs;
  private int cruiseTimeMs;
  private int decelTimeMs;
  private int timeStepMs;

  private long startTime;
  private boolean finished;

  public FakeMotionProfile(WPI_TalonSRX talon, float cruiseVelocity, int accelTimeMs, int cruiseTimeMs, int decelTimeMs, int timeStepMs) {
    this.talon = talon;
    this.cruiseVelocity = cruiseVelocity;
    this.accelTimeMs = accelTimeMs;
    this.cruiseTimeMs = cruiseTimeMs;
    this.decelTimeMs = decelTimeMs;
    this.timeStepMs = timeStepMs;
    this.finished = true;
  }

  public FakeMotionProfile(WPI_TalonSRX talon, float cruiseVelocity, int accelTimeMs, int cruiseTimeMs, int decelTimeMs) {
    this(talon, cruiseVelocity, accelTimeMs, cruiseTimeMs, decelTimeMs, 10);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    talon.set(ControlMode.Velocity, 0);
    startTime = System.currentTimeMillis();
    finished = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    int time = (int)(System.currentTimeMillis() - startTime);
    if(time < accelTimeMs)  {
      talon.set(ControlMode.Velocity, cruiseVelocity * (float)time / accelTimeMs);
      System.out.println("accelerating: " + cruiseVelocity * (float)time / accelTimeMs);
    } else if(time < accelTimeMs + cruiseTimeMs) {
      talon.set(ControlMode.Velocity, cruiseVelocity);
      System.out.println("cruising: " + cruiseVelocity);
    } else if(time < accelTimeMs + cruiseTimeMs + decelTimeMs) {
      talon.set(ControlMode.Velocity, cruiseVelocity * (1.0 - ((float)time - accelTimeMs - cruiseTimeMs)/decelTimeMs));
      System.out.println("decelerating: " + cruiseVelocity * (1.0 - ((float)time - accelTimeMs - cruiseTimeMs)/decelTimeMs));
    } else {
      finished = true;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return this.finished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    talon.set(ControlMode.Velocity, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
