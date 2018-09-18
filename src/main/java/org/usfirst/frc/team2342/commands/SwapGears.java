package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.PCMHandler;

import edu.wpi.first.wpilibj.command.Command;

public class SwapGears extends Command {

	//false = low, true = high
	private boolean gear;
	
	PCMHandler pcm;
	
	public SwapGears(PCMHandler pcm, boolean gear) {
		this.pcm = pcm;
		this.gear = gear;
	}
	
	public void initialize() {
		pcm.setHighGear(gear);
		pcm.setLowGear(!gear);
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}

}
