package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.Winch;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Joystick;

public class WinchMove extends Command {
  public WinchMove(W_length, W_dir) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Winch); 
    timer = new Timer();
    public double = W_length; //time to run winch
    public boolean = W_dir; //direction to run winch TRUE=pull, FALSE=let out
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    timer.start();
    }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (W_dir){ //if true, pull in
      Winch.winchSuckIn();
    } 
    if (!W_dir) { //if false, let out
      Winch.winchLetOut();
    }

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return timer.get() > W_length; 
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Winch.end();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
