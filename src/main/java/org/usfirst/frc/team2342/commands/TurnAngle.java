package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.PIDLoops.GyroPIDController;
import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

/*
 * By: Joshua Calzadillas
 * 
 * Command: Turn Angle
 * 
 * Parameters: TurnAngle(speed, angle, driveController);
 * 
 * Example: TurnAngle(1350, -90, westCoastTankDrive);
 * 
 * Turn angle allows the user make the robot rotate to a specific angle with a gyro PID loop controller. 
 * Doing so makes life easier for autonomous modes and maneuvers.
 * It utilizes the Tank Drive to set a specific velocity for the wheels to turn. (I will have a max speed cap later)...
 * The robot will stop turning when the gyro finally reaches the destination/target angle. * 
 */

public class TurnAngle extends Command {
	TankDrive m_westCoast;
	private double cangle = 0.0d;
	private double angle = 0.0d;
	private double vel = 0.0d;
	private double deadZone = 0.5d;
	
	double[] trailingCorrections = new double[Constants.TURN_AVERAGE_SIZE];
	int position;

	// Constructor for the command
	public TurnAngle(double velocity, double angle, TankDrive westCoast){
		requires(westCoast);
		m_westCoast = westCoast;
		m_westCoast.setGyroControl(true);
		this.angle = angle;
		this.vel = velocity;
		position = 0;
	}
	
	// Initialize the setup for the target angle
	protected void initialize(){
		GyroPIDController.gyroReset();
		this.cangle = 0;
		if(m_westCoast.debug)
			System.out.println("turning angle "+angle+" degrees");
		m_westCoast.turnSet(this.angle);
	}
	
	@Override
	// run loop for the command
	protected void execute(){
		//SmartDashboard.putString("DB/String 0", ""+ String.valueOf(m_westCoast.pidc.calculateAE()));
		m_westCoast.rotateAuto(this.vel);
		this.cangle = GyroPIDController.getCurAngle();
		if(m_westCoast.debug)
			System.out.println("current angle: "+GyroPIDController.getCurAngle() + " degrees");
		if(position == 0)
			for(int i=0;i<Constants.TURN_AVERAGE_SIZE;i++)
				trailingCorrections[i] = GyroPIDController.getCurAngle();
		else
			trailingCorrections[position % Constants.TURN_AVERAGE_SIZE] = GyroPIDController.getCurAngle();
		position++;
	}

	@Override
	// Check to see if the gyro is done
	protected boolean isFinished() {
		double avg = 0;
		for(int i=0;i<Constants.TURN_AVERAGE_SIZE;i++)
			avg += trailingCorrections[i];
		avg /= Constants.TURN_AVERAGE_SIZE;
		//System.out.println("trailing avg " + avg);
		return Math.abs(avg - this.angle) <= Constants.TURN_THRESHOLD;
	}
	
	@Override
	// if an accident happens
	protected void interrupted() {
		if(m_westCoast.debug)
			System.out.println("angle interrupted");
		end();
	}
	
	// stop the robot from turning when done
	protected void end() {
		System.out.println("finished angle");
		if(m_westCoast.debug)
			System.out.println("finished turn angle");
		m_westCoast.setVelocity(0, 0);
		m_westCoast.setGyroControl(false);
	}
}