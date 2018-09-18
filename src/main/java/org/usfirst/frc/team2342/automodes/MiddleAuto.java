package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.CascadePosition;
import org.usfirst.frc.team2342.commands.DriveDistance;
import org.usfirst.frc.team2342.commands.PushBox;
import org.usfirst.frc.team2342.commands.TurnAngle;
import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class MiddleAuto extends CommandGroup {

	public MiddleAuto(TankDrive drive, CascadeElevator cascade, BoxManipulator manip, Joystick gamepad) {
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addSequential(new DriveDistance(drive, 0.66));
			addSequential(new TurnAngle(Constants.WESTCOAST_TURN_SPEED, 34, drive));
			addParallel(new CascadePosition(cascade, Constants.CASCADE_SWITCH, gamepad));
			addSequential(new DriveDistance(drive, 8.33));
			addSequential(new TurnAngle(Constants.WESTCOAST_TURN_SPEED, -34, drive));
			//addSequential(new DriveDistance(drive, 0.66));
			addSequential(new PushBox(manip, gamepad, 1.0));
		} else {
			addSequential(new DriveDistance(drive, 0.66));
			addSequential(new TurnAngle(Constants.WESTCOAST_TURN_SPEED, -34, drive));
			addParallel(new CascadePosition(cascade, Constants.CASCADE_SWITCH, gamepad));
			addSequential(new DriveDistance(drive, 8.33));
			addSequential(new TurnAngle(Constants.WESTCOAST_TURN_SPEED, 34, drive));
			//addSequential(new DriveDistance(drive, 0.66));
			addSequential(new PushBox(manip, gamepad, 1.0));
		}
		/*if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addSequential(new TurnAngle(Constants.WESTCOAST_TURN_SPEED, 34, drive));
			addParallel(new CascadePosition(cascade, Constants.CASCADE_SWITCH, gamepad));
			addSequential(new DriveDistance(drive, 8.33));
			addSequential(new TurnAngle(Constants.WESTCOAST_TURN_SPEED, -34, drive));
			//addSequential(new DriveDistance(drive, 2));
			//addSequential(new PushBox(manip, gamepad, 0.5));
		} else {
			addSequential(new TurnAngle(Constants.WESTCOAST_TURN_SPEED, -34, drive));
			addParallel(new CascadePosition(cascade, Constants.CASCADE_SWITCH, gamepad));
			addSequential(new DriveDistance(drive, 8.33));
			addSequential(new TurnAngle(Constants.WESTCOAST_TURN_SPEED, 34, drive));
			//addSequential(new DriveDistance(drive, 1));
			//addSequential(new PushBox(manip, gamepad, 0.5));
		}
		addSequential(new DriveDistance(drive, -2));
		addSequential(new CascadePosition(cascade, Constants.CASCADE_BASE, gamepad));*/
		
	}
	
}
