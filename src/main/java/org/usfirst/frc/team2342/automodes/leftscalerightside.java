package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.DriveDistance;
import org.usfirst.frc.team2342.commands.TurnAngle;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class leftscalerightside extends CommandGroup {
	public leftscalerightside(WestCoastTankDrive westCoast){
		//move forward 16 feet
		/*addSequential(new DriveDistance(westCoast, 16));
		//turn 90 degrees to the left
		addSequential(new TurnAngle(Constants.WESTCOAST_MAX_SPEED, -90, westCoast));
		//move forward 10 feet
		addSequential(new DriveDistance(westCoast, 10));
		//turn 90 degrees to the right
		addSequential(new TurnAngle(Constants.WESTCOAST_MAX_SPEED, 90, westCoast));
		//drive 5 feet forward
		addSequential(new DriveDistance(westCoast, 5));*/
	}
}
