package org.usfirst.frc.team2342.commands;

import edu.wpi.first.wpilibj.command.Command;

public class Wait extends Command {

	int length;
	long time;
	
	public Wait(int length) {
		this.length = length;
	}
	
	public void initialize() {
		time = System.currentTimeMillis();
	}
	
	@Override
	protected boolean isFinished() {
		return System.currentTimeMillis() - time > length;
	}

}
