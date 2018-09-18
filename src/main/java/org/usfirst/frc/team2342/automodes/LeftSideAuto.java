package org.usfirst.frc.team2342.automodes;

import org.usfirst.frc.team2342.commands.CascadePosition;
import org.usfirst.frc.team2342.commands.DriveDistance2;
import org.usfirst.frc.team2342.commands.PushBox;
import org.usfirst.frc.team2342.commands.TiltManipulator;
import org.usfirst.frc.team2342.commands.Turn90;
import org.usfirst.frc.team2342.commands.Wait;
import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LeftSideAuto extends CommandGroup {

	public LeftSideAuto(TankDrive drive, CascadeElevator cascade, BoxManipulator manip, Joystick gamepad) {
		if(DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'L') {
			addSequential(new DriveDistance2(drive,25));
			//addParallel(new CascadeHold(cascade, Constants.CASCADE_SWITCH, gamepad));
			addSequential(new Wait(500));
			addParallel(new TiltManipulator(manip));
			addParallel(new Turn90(drive, false));
			addSequential(new CascadePosition(cascade, Constants.CASCADE_UPPER_SCALE, gamepad));
			addSequential(new DriveDistance2(drive,1,Constants.WESTCOAST_HALF_SPEED/2));
			addSequential(new PushBox(manip, gamepad));
			addSequential(new DriveDistance2(drive, -2));
			addSequential(new CascadePosition(cascade, Constants.CASCADE_BASE, gamepad));
		} else if(DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L') {
			addParallel(new CascadePosition(cascade, Constants.CASCADE_SWITCH, gamepad));
			addSequential(new DriveDistance2(drive,12));
			//addParallel(new CascadeHold(cascade, Constants.CASCADE_SWITCH, gamepad));
			addSequential(new Wait(500));
			addSequential(new Turn90(drive, false));
			addSequential(new DriveDistance2(drive, 2, Constants.WESTCOAST_HALF_SPEED / 2));
			//addSequential(new TiltManipulator(manip));
			addSequential(new PushBox(manip, gamepad));
			addSequential(new DriveDistance2(drive, -2));
			addSequential(new CascadePosition(cascade, Constants.CASCADE_BASE, gamepad));
		} else if(SmartDashboard.getBoolean("DB/Button 1", false)) {
			addSequential(new DriveDistance2(drive, 18));
			addSequential(new Turn90(drive, false));
			addSequential(new DriveDistance2(drive, 17));
			addSequential(new Turn90(drive, true));
			addSequential(new CascadePosition(cascade, Constants.CASCADE_UPPER_SCALE, gamepad));
			addSequential(new DriveDistance2(drive,1,Constants.WESTCOAST_HALF_SPEED/2));
			addSequential(new TiltManipulator(manip));
			addSequential(new PushBox(manip, gamepad));
			addSequential(new DriveDistance2(drive, -2));
			addSequential(new CascadePosition(cascade, Constants.CASCADE_BASE, gamepad));
		} else {
			addSequential(new DriveDistance2(drive, 19));
			addSequential(new Turn90(drive, false));
			addSequential(new DriveDistance2(drive, 10));
		}
	}

}
