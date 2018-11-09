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
public class DriveMotor extends Command {

    private TankDrive tankDrive;
    private int distance;
    private int leftFinished;
    private int rightFinished;
    //Distance in feet
	public DriveMotor(TankDrive tankDrive, double distance) {
        System.out.println("DriveDistance2 created.");
        requires(tankDrive);
    	this.tankDrive = tankDrive;
        this.distance = (int) (-distance * 4096 / 1.57);
        leftFinished = 0;
        rightFinished = 0;

        


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
        tankDrive.goToEncoderTick(distance);
        

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        System.out.println("left distance: " + (Math.abs(tankDrive.leftA.getSelectedSensorPosition(0) - distance)));
        System.out.println("right distance: " + (Math.abs(tankDrive.rightA.getSelectedSensorPosition(0) - distance)));
        System.out.println("left finished: " + leftFinished + " right finished: " + rightFinished);
        if (Math.abs(tankDrive.leftA.getSelectedSensorPosition(0) - distance) < 100)
            leftFinished++;
        if (Math.abs(tankDrive.rightA.getSelectedSensorPosition(0) - distance) < 100)
            rightFinished++;
        return leftFinished > 100 && rightFinished > 100;


        }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        System.out.println("left finished: " + leftFinished + " right finished: " + rightFinished);
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
