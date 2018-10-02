package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.CascadePosition;
import org.usfirst.frc.team2342.commands.DriveDistance2;
import org.usfirst.frc.team2342.commands.PushBox;
import org.usfirst.frc.team2342.commands.TiltManipulator;
import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class MiddleAuto2 extends CommandGroup {

	public MiddleAuto2(TankDrive drive, CascadeElevator cascade, BoxManipulator manip, Joystick gamepad) {
		if(DriverStation.getInstance().getGameSpecificMessage().length() > 0 && DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'R') {
			addSequential(new TiltManipulator(manip));
			addParallel(new CascadePosition(cascade, Constants.CASCADE_SWITCH, gamepad));
			addSequential(new DriveDistance2(drive, 8.5));
			addSequential(new PushBox(manip, gamepad, 0.5));
		} else {
			addParallel(new CascadePosition(cascade, Constants.CASCADE_SWITCH, gamepad));
			addSequential(new DriveDistance2(drive, 8.5));
		}
	}
	
}
