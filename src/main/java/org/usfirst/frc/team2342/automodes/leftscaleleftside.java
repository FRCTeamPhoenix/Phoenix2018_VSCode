package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.DriveDistance;
import org.usfirst.frc.team2342.commands.TurnAngle;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class leftscaleleftside extends CommandGroup {
	public leftscaleleftside(WestCoastTankDrive westCoast){
		/*addSequential(new DriveDistance(westCoast, 22));
		//turn 90 degrees to the right
		addSequential(new TurnAngle(Constants.WESTCOAST_MAX_SPEED, 90, westCoast));*/
	}
}
