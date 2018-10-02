package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;

import edu.wpi.first.wpilibj.command.Command;

public class TiltManipulator extends Command {

	long startTime;
	BoxManipulator manip;
	
	public TiltManipulator(BoxManipulator manip) {
		this.manip = manip;
	}
	
	protected void initialize() {
		startTime = System.currentTimeMillis();
		manip.talonTip.set(-1);
	}
	
	@Override
	protected boolean isFinished() {
		return System.currentTimeMillis() - startTime > 25;
	}
	
	protected void end() {
		manip.talonTip.set(0);
	}
	
	

}
