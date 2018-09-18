package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.DriveDistance2;
import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class MultiCubeAutoRightSide extends CommandGroup {

	public MultiCubeAutoRightSide(TankDrive drive, CascadeElevator cascade, BoxManipulator manip, Joystick gamepad) {
		//addSequential(new DriveDistance(drive, 20.25));
		addSequential(new DriveDistance2(drive, Constants.WESTCOAST_HALF_SPEED));
	}
	
}
