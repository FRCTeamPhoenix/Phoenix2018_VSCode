package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;

import edu.wpi.first.wpilibj.command.Command;

public class DriveArc extends Command {
	boolean isLeftInner;
	WestCoastTankDrive m_westCoast;
	private double radius;
	private double angle;
	private double innerMultiplyer;
	private double outerMultiplyer;
	
	public DriveArc(WestCoastTankDrive westCoast, double radius, double angle, double innerMultiplyer, double outerMultiplyer, boolean isLeftInner) {
		requires(westCoast);
		this.m_westCoast = westCoast;
		this.isLeftInner = isLeftInner;
		this.radius = radius;
		this.angle = angle;
		this.innerMultiplyer = innerMultiplyer;
		this.outerMultiplyer = outerMultiplyer;
	}
	
	protected void initialize() {
		//m_westCoast.goArc(this.radius, this.angle, this.outerMultiplyer, this.innerMultiplyer, this.isLeftInner);
	}
	
	protected void execute() {
		//m_westCoast.arcLoop(this.isLeftInner);
	}
	
	public boolean isFinished(){
		return true;
    	//return m_westCoast.isDistanceFinished();
    }
	
	protected void end() {
		//m_westCoast.setVelocity(0, 0);
	}
	
	protected void interrupted() {
		end();
	}

}
