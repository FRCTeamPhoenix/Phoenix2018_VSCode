package org.usfirst.frc.team2342.commands;

import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.robot.subsystems.WestCoastTankDrive;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveGamepad extends Command {

	double leftVelocity = 0.0;
	double rightVelocity = 0.0;
	Joystick m_gamepad;
	TankDrive m_westCoast;


	public DriveGamepad(Joystick gamepad, TankDrive tankDrive) {
		requires(tankDrive);
		//limit at max val
		m_gamepad = gamepad;

		m_westCoast = tankDrive;
		if(Math.abs(gamepad.getRawAxis(1)) > Constants.JOYSTICK_DEADZONE)
			leftVelocity = gamepad.getRawAxis(1);
		else
			leftVelocity = 0.0;

		if(Math.abs(gamepad.getRawAxis(5)) > Constants.JOYSTICK_DEADZONE)
			rightVelocity = gamepad.getRawAxis(5);
		else
			rightVelocity = 0.0;
	}

	protected void initialize() {
		m_westCoast.setPercentage(-leftVelocity, -rightVelocity);
	}

	@Override
	protected void execute(){
		double leftVelocity = 0.0;
		double rightVelocity = 0.0;
		double axis1 = m_gamepad.getRawAxis(1);
		double axis3 = m_gamepad.getRawAxis(5);

		//		System.out.println(axis1);
		if(Math.abs(axis1) > Constants.JOYSTICK_DEADZONE)
			leftVelocity = axis1; // velocity maybe

		if(Math.abs(axis3) > Constants.JOYSTICK_DEADZONE)
			rightVelocity = axis3; // velocity maybe
		m_westCoast.setPercentage(-leftVelocity, -rightVelocity);
	}

	protected boolean isFinished(){
		return false;
	}

	protected void end() {
		m_westCoast.setVelocity(0, 0);
	}

	protected void interrupted() {
		end();
	}

}
