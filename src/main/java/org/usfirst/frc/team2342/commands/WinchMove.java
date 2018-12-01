package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.Winch;
import edu.wpi.first.wpilibj.command.Command;

public class WinchMove extends Command {

  public double W_length; //time to run winch
  public boolean W_dir;//direction to run winch TRUE=pull, FALSE=let out
  public Winch winch;
  public long startTime;
  public WinchMove(Winch winch,int W_length,boolean W_dir) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.winch = winch;
    this.W_length = W_length;
    this.W_dir = W_dir;
    requires(winch); 
    

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    startTime = System.currentTimeMillis();
    }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (W_dir){ //if true, pull in
      winch.winchSuckIn();
    } 
    if (!W_dir) { //if false, let out
      winch.winchLetOut();
    }

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return System.currentTimeMillis() - startTime > W_length; 
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    winch.end();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    winch.end();
  }
}
