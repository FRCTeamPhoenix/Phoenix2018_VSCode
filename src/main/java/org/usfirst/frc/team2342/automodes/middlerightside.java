package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.DriveDistance;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class middlerightside extends CommandGroup {
	public middlerightside(WestCoastTankDrive westCoast){
		//drive 8.5 ft forward
		//addSequential(new DriveDistance(westCoast, 8.5));
	}
}
