package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.util.Constants;
import org.usfirst.frc.team2342.robot.sensors.Gyro;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveDistance2 extends Command {

    private TankDrive tankDrive;
    private double distance;
    private boolean forward;

    private double oldP;

    private int leftFinished;
    private int rightFinished;

	public DriveDistance2(TankDrive tankDrive, double distance) {
        System.out.println("DriveDistance2 created.");
        Gyro.reset();
        requires(tankDrive);
    	this.tankDrive = tankDrive;
        this.distance = distance / Constants.TALON_RPS_TO_FPS * Constants.TALON_TICKS_PER_REV;
        this.forward = distance >= 0;
        this.oldP = 0;
        this.leftFinished = 0;
        this.rightFinished = 0;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("init");
        tankDrive.zeroSensors();
        tankDrive.distancePidLoopLeft.setGoal(this.distance);
        tankDrive.distancePidLoopRight.setGoal(this.distance);
    	//tankDrive.setHighGear();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        //System.out.println("distance:" + tankDrive.leftA.getSelectedSensorPosition(0) + " setpoint: " + this.distance + " error: " + tankDrive.distancePidLoopLeft.getError());
        System.out.println(" left error: " + tankDrive.leftA.getSelectedSensorPosition(0) + " right error: " + tankDrive.rightA.getSelectedSensorPosition(0));
        //System.out.println("DriveDinstance2 Execute Started.");
        //System.out.println(Math.abs((tankDrive.leftA.getSelectedSensorPosition(0)) + distance/Constants.TALON_RPS_TO_FPS * Constants.TALON_TICKS_PER_REV));
        /*if(Math.abs(tankDrive.distancePidLoop.getError()) < this.distance / 8) {
            System.out.println("error is pretty low");
            this.oldP = tankDrive.distancePidLoop.getP();
            tankDrive.distancePidLoop.setP(0);
        }*/
        if (forward) {
            //tankDrive.talon(-1200, -1200);
            //tankDrive.leftA.set(ControlMode.PercentOutput,0.5);
            //tankDrive.rightA.set(ControlMode.PercentOutput,0.5);
            tankDrive.goDistance(this.distance);
        } else
            tankDrive.goDistance(this.distance);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        /*System.out.println("TankDrive isFinished fired.");
        if (forward)
            return Math.abs((tankDrive.leftA.getSelectedSensorPosition(0)) + distance/Constants.TALON_RPS_TO_FPS * Constants.TALON_TICKS_PER_REV) < 300;
        else
            return Math.abs((tankDrive.leftA.getSelectedSensorPosition(0)) - distance/Constants.TALON_RPS_TO_FPS * Constants.TALON_TICKS_PER_REV) < 300;
            
        }*/
        //return false;
        if( Math.abs((tankDrive.leftA.getSelectedSensorPosition(0)) + distance) < 800)
            leftFinished++;
        if( Math.abs((tankDrive.rightA.getSelectedSensorPosition(0)) + distance) < 800)
            rightFinished++;
        System.out.println("left finished " + leftFinished + " rightFinished " + rightFinished);
        return (leftFinished >= 20 && rightFinished >= 20);// || ( (Math.abs((tankDrive.leftA.getSelectedSensorPosition(0)) + distance) < 300) && ( Math.abs((tankDrive.rightA.getSelectedSensorPosition(0)) + distance) < 300));
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