package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.DriveArc;
import org.usfirst.frc.team2342.commands.DriveDistance;
import org.usfirst.frc.team2342.commands.TurnAngle;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class leftswitchleft extends CommandGroup {
	public leftswitchleft(WestCoastTankDrive westCoast){
		//move forward 10.5 feet
		/*addSequential(new DriveDistance(westCoast, 10.5));
		//turn 90 degrees to the right
		addSequential(new TurnAngle(Constants.WESTCOAST_MAX_SPEED, 90, westCoast));
		//move forward 2.5 feet
		addSequential(new DriveDistance(westCoast, 2.5));*/
	}
}
