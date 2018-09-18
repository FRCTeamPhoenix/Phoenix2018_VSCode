package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.sensors.Gyro;
import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Turn90 extends Command {

	TankDrive westCoast;	
	boolean direction;
	double startAngle;
	long startTime;
	double angle;
	
	public Turn90(TankDrive westCoast,boolean direction,double angle) {
		this.westCoast = westCoast;
    	
    	this.direction = direction;
    	this.startAngle = Gyro.angle();
    	this.angle = angle;
	}
	
    public Turn90(TankDrive westCoast,boolean direction) {
    	
    	this(westCoast,direction,90);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	
    	//westCoast.setLowGear();
    	westCoast.setLowGear();
    	startTime = System.currentTimeMillis();
    	
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = Constants.WESTCOAST_HALF_SPEED;
    	if (direction)
    		speed *= -1;
    	westCoast.setVelocity(-speed, speed);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() - startTime > ((direction ? SmartDashboard.getNumber("DB/Slider 3", 2000) : SmartDashboard.getNumber("DB/Slider 2", 2000))*angle/90.0);
    }

    // Called once after isFinished returns true
    protected void end() {
    	westCoast.setVelocity(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
