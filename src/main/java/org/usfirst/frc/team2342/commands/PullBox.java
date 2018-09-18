package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class PullBox extends Command {
	Timer timer;
	BoxManipulator manipulator;
	Joystick gamepad;
	
    public PullBox(BoxManipulator manipulator, Joystick gamepad) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(manipulator);
    	this.gamepad = gamepad;
    	this.manipulator = manipulator;
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
    	manipulator.pullBox();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() > 0.3;
    }

    // Called once after isFinished returns true
    protected void end() {
    	manipulator.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
