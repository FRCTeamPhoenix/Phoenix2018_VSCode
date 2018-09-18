package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveVoltageTime extends Command {

    private TankDrive tankDrive;
	private double voltage;
	private int ms;
	private long startTime;
	public DriveVoltageTime(TankDrive tankDrive, int ms, double voltage) {
    	this.tankDrive = tankDrive;
    	this.ms = ms;
    	
    	if (voltage > 0) {
    		this.voltage = Math.min(voltage, 1);
    	} else if (voltage < 0) {
    		this.voltage = Math.max(voltage, -1);
    	} else
    		this.voltage = 0;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startTime = System.currentTimeMillis();
    	tankDrive.setHighGear();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	tankDrive.setPercentage(voltage, voltage);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() - startTime > ms;
    }

    // Called once after isFinished returns true
    protected void end() {
    	tankDrive.setPercentage(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
