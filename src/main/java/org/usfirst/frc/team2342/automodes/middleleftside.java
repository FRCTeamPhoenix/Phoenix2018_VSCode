package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.DriveArc;
import org.usfirst.frc.team2342.commands.DriveDistance;
import org.usfirst.frc.team2342.commands.TurnAngle;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class middleleftside extends CommandGroup {
	public middleleftside(WestCoastTankDrive westCoast){
		//drive forward 3 feet
		/*addSequential(new DriveDistance(westCoast, 3));
		//turn 90 degrees to the left
		addSequential(new TurnAngle(Constants.WESTCOAST_MAX_SPEED, -90, westCoast));
		//drive 6 ft forward
		addSequential(new DriveDistance(westCoast, 6));
		//turn 90 degrees to the right
		addSequential(new TurnAngle(Constants.WESTCOAST_MAX_SPEED, 90, westCoast));
		//drive 5 ft forward
		addSequential(new DriveDistance(westCoast, 5));*/
	}
}
