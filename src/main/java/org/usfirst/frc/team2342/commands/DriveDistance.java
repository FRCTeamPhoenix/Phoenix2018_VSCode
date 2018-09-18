package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.PIDLoops.DistancePIDController;
import org.usfirst.frc.team2342.PIDLoops.GyroPIDController;
import org.usfirst.frc.team2342.robot.sensors.Gyro;
import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

public class DriveDistance extends Command {
	TankDrive m_westCoast;
	private double m_distance;
	private double m_start;
	
	public DriveDistance(TankDrive westCoast, double distance) {
		//only one system can use westCoast
		requires(westCoast);
		m_westCoast = westCoast;
		m_distance = -distance / Constants.TALON_RPS_TO_FPS * Constants.TALON_TICKS_PER_REV;
	}
	
	protected void initialize() {
		//called on command adding to scheduler
//		if(m_westCoast.debug)
//			System.out.println("started distance: "+m_distance+" ft");
		//sets distance when created
//		m_start = m_westCoast.getEncoderDistance();
//		m_westCoast.goDistance(m_distance);
		
		DistancePIDController.setGoal(-m_distance);
		Gyro.reset();
		m_westCoast.leftA.setSelectedSensorPosition(0, 0, 10);
		m_westCoast.rightA.setSelectedSensorPosition(0, 0, 10);
		m_start = m_westCoast.getEncoderDistance();
		GyroPIDController.updateAngle(GyroPIDController.getCurAngle());
	}
	
	protected void execute() {
		//adjusts velocity values and called on scheduler.getInstance().run()
		m_westCoast.goDistance(m_distance);
	}
	
	public boolean isFinished(){
		//should the command terminate
		System.out.println("distance: " + m_distance + " encoder distance: " + m_westCoast.getEncoderDistance());
    	return Math.abs(m_distance - (m_westCoast.getEncoderDistance() - m_start)) < 1000 ;
    }
	
	protected void end() {
		//called at end of command
		if(true)
			System.out.println("finished distance");
		//stop moving
		m_westCoast.setVelocity(0, 0);
	}
	
	protected void interrupted() {
		//called if command stopped
		if(m_westCoast.debug)
			System.out.println("distance interrupted");
		end();
	}
	
}
