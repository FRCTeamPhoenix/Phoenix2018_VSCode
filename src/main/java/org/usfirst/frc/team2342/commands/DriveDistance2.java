package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveDistance2 extends Command {

    private TankDrive tankDrive;
	private double distance;
	
	private double speed;

	public DriveDistance2(TankDrive tankDrive, double distance, double speed) {
    	requires(tankDrive);
    	this.tankDrive = tankDrive;
    	this.distance = distance;
    	this.speed = speed;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }
	
	public DriveDistance2(TankDrive tankDrive, double distance) {
		this(tankDrive, distance, Constants.WESTCOAST_HALF_SPEED);
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	tankDrive.zeroSensors();
    	tankDrive.setHighGear();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	tankDrive.goDistance2(distance, -speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs((tankDrive.leftA.getSelectedSensorPosition(0)) + distance/Constants.TALON_RPS_TO_FPS * Constants.TALON_TICKS_PER_REV) < 300;
    }

    // Called once after isFinished returns true
    protected void end() {
    	tankDrive.setVelocity(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
