package org.usfirst.frc.team2342.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.util.Constants;
import java.*;
import java.util.Scanner;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveDistance2 extends Command {

    private TankDrive tankDrive;
    private double distance;
    private boolean forward;

	public DriveDistance2(TankDrive tankDrive, double distance) {
    	requires(tankDrive);
    	this.tankDrive = tankDrive;
        this.distance = distance;
        this.forward = distance >= 0;


        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	tankDrive.zeroSensors();
    	//tankDrive.setHighGear();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (forward) {
            //tankDrive.talon(-1200, -1200);
            tankDrive.leftA.set(ControlMode.PercentOutput,0.5);
            tankDrive.rightA.set(ControlMode.PercentOutput,0.5);
        } else
            tankDrive.setVelocity(1200, 1200);
        System.out.println(tankDrive.leftA.getSelectedSensorPosition(0));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (forward)
            return Math.abs((tankDrive.leftA.getSelectedSensorPosition(0)) + distance/Constants.TALON_RPS_TO_FPS * Constants.TALON_TICKS_PER_REV) < 300;
        else
            return Math.abs((tankDrive.leftA.getSelectedSensorPosition(0)) - distance/Constants.TALON_RPS_TO_FPS * Constants.TALON_TICKS_PER_REV) < 300;
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