package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.CascadePosition;
import org.usfirst.frc.team2342.commands.DriveVoltageTime;
import org.usfirst.frc.team2342.commands.PushBox;
import org.usfirst.frc.team2342.commands.TiltManipulator;
import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class SwitchAuto extends CommandGroup{
	public SwitchAuto(TankDrive drive, CascadeElevator cascade, BoxManipulator manip, Joystick gamepad) {
		addParallel(new CascadePosition(cascade, Constants.CASCADE_SWITCH * 1.5, gamepad));
		addSequential(new DriveVoltageTime(drive, 1700, 1));
	//	addParallel(new CascadeHold(cascade));
		addSequential(new TiltManipulator(manip));
		addSequential(new PushBox(manip, gamepad));
	}
}
