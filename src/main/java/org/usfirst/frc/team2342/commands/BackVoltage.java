package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.TankDrive;

import edu.wpi.first.wpilibj.command.Command;

public class BackVoltage extends Command {

	TankDrive drive;
	
	public BackVoltage(TankDrive drive) {
		this.drive = drive;
	}
	
	public void initialize() {
		
	}
	
	protected void execute() {
		
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

}
