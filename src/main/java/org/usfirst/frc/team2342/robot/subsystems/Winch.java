
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

  public Winch(WPI_TalonSRX talonwinch) {
    this.talonwinch = talonwinch;
  }

  //input inches or time?
  public void winchSuckIn() {
    talonwinch.set(ControlMode.PercentOutput, 1.00);
  }

  public void winchLetOut() {
    talonwinch.set(ControlMode.PercentOutput, -1.00);
  }

  // ??winch go to X distance let out (asssuming encoder)
  public void winchGoTo() {

  }
  


  public void end() {
    talonwinch.set(ControlMode.PercentOutput, 00);
  }





  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }


}
