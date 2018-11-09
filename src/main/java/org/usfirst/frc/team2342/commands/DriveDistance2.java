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
        System.out.println("DriveDistance2 created.");
        requires(tankDrive);
    	this.tankDrive = tankDrive;
        this.distance = distance;
        this.forward = distance >= 0;


        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("init");
    	tankDrive.zeroSensors();
    	//tankDrive.setHighGear();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        System.out.println("DriveDinstance2 Execute Started.");
        System.out.println(Math.abs((tankDrive.leftA.getSelectedSensorPosition(0)) + distance/Constants.TALON_RPS_TO_FPS * Constants.TALON_TICKS_PER_REV));
        if (forward) {
            //tankDrive.talon(-1200, -1200);
            tankDrive.leftA.set(ControlMode.PercentOutput,0.5);
            tankDrive.rightA.set(ControlMode.PercentOutput,0.5);
        } else
            tankDrive.setVelocity(1200, 1200);
            System.out.println("DriveDinstance2 Execute Ended.");
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        System.out.println("TankDrive isFinished fired.");
        if (forward)
            return Math.abs((tankDrive.leftA.getSelectedSensorPosition(0)) + distance/Constants.TALON_RPS_TO_FPS * Constants.TALON_TICKS_PER_REV) < 300;
        else
            return Math.abs((tankDrive.leftA.getSelectedSensorPosition(0)) - distance/Constants.TALON_RPS_TO_FPS * Constants.TALON_TICKS_PER_REV) < 300;
            
        }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        System.out.println("TankDrive End fired.");
        tankDrive.setPercentage(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        System.out.println("TankDrive Interrupted fired.");
        end();
    }
}