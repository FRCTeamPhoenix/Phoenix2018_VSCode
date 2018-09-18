package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.DriveDistance;
import org.usfirst.frc.team2342.commands.TurnAngle;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class rightswitchright extends CommandGroup {
	public rightswitchright(WestCoastTankDrive westCoast){
		/*//move forward 10.5 feet
		addSequential(new DriveDistance(westCoast, 11));
		//turn 90 degrees to the left
		addSequential(new TurnAngle(300.0, 90, westCoast));
		//move forward 2.5 feet
		addSequential(new DriveDistance(westCoast, 2));*/
	}
}
