
package org.usfirst.frc.team2342.robot.subsystems;

import org.usfirst.frc.team2342.robot.PCMHandler;
import org.usfirst.frc.team2342.util.Constants;
import org.usfirst.frc.team2342.util.PIDGains;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//..
public class Winch extends Subsystem {
  
  //assuiming a talon is used:
  public WPI_TalonSRX talonwinch;

  
  //    Methods / Commands:
  
  protected void initialize() {
    
  }

  //input inches or time?
  public winchSuckIn() {
    talonwinch.set(ControlMode.PercentOutput, 1.00);
  }

  public winchLetOut() {
    talonwinch.set(ControlMode.PercentOutput, -1.00);
  }

  // ??winch go to X distance let out (asssuming encoder)
  public winchGoTo() {

  }
  
  protected boolean isFinished() {
      return isTimedOut();
  }

  protected void end() {
    talonwinch.set(ControlMode.PercentOutput, 00);
  }

  protected void interrupted() {
    end();
  }



  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }


}
