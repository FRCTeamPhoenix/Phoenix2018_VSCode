package org.usfirst.frc.team2342.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.usfirst.frc.team2342.automodes.LeftSideAuto;
import org.usfirst.frc.team2342.automodes.LeftSideAuto2;
import org.usfirst.frc.team2342.automodes.LeftSwitchAuto;
import org.usfirst.frc.team2342.automodes.MiddleAuto;
import org.usfirst.frc.team2342.automodes.RightSideAuto;
import org.usfirst.frc.team2342.automodes.RightSideAuto2;
import org.usfirst.frc.team2342.automodes.RightSwitchAuto;
import org.usfirst.frc.team2342.commands.CascadePosition;
import org.usfirst.frc.team2342.commands.DriveDistance2;
import org.usfirst.frc.team2342.commands.DriveGamepad;
import org.usfirst.frc.team2342.robot.subsystems.BoxManipulator;
import org.usfirst.frc.team2342.robot.subsystems.CascadeElevator;
import org.usfirst.frc.team2342.robot.subsystems.TankDrive;
import org.usfirst.frc.team2342.util.Constants;
import org.usfirst.frc.team2342.util.FMS;
import org.usfirst.frc.team2342.util.PIDGains;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends IterativeRobot {

	Joystick gamepad;
	PCMHandler PCM;
	WPI_TalonSRX talonFR;
	WPI_TalonSRX talonFL;
	WPI_TalonSRX talonBR;
	WPI_TalonSRX talonBL;
	WPI_TalonSRX talonCascade;
	WPI_TalonSRX talonIntakeRight;
	WPI_TalonSRX talonIntakeLeft;
	WPI_TalonSRX talonTip;

	TankDrive tankDrive;
	Joystick joystickR;
	Joystick XBOX;
	CascadeElevator cascadeElevator;
	BoxManipulator boxManipulator;

	double speed = 0.0d;
	double tangle = 0.0d;
	UsbCamera camera0;
	UsbCamera camera1;
	VideoSink server;

	boolean intakeLowVoltage = false;
	boolean pressed8 = false;

	public Robot() {
		// Gyro.init();
		gamepad = new Joystick(0);
		PCM = new PCMHandler(11);
		talonFR = new WPI_TalonSRX(Constants.RIGHT_MASTER_TALON_ID);
		talonFL = new WPI_TalonSRX(Constants.LEFT_MASTER_TALON_ID);
		talonBR = new WPI_TalonSRX(Constants.RIGHT_SLAVE_TALON_ID);
		talonBL = new WPI_TalonSRX(Constants.LEFT_SLAVE_TALON_ID);
		talonCascade = new WPI_TalonSRX(Constants.TALON_CASCADE);
		talonIntakeRight = new WPI_TalonSRX(Constants.TALON_INTAKE_RIGHT);
		talonIntakeLeft = new WPI_TalonSRX(Constants.TALON_INTAKE_LEFT);
		talonTip = new WPI_TalonSRX(Constants.TALON_TIP);
		tankDrive = new TankDrive(PCM, talonFL, talonFR, talonBL, talonBR);
		joystickR = new Joystick(2);
		XBOX = new Joystick(1);
		cascadeElevator = new CascadeElevator(talonCascade, gamepad);
		boxManipulator = new BoxManipulator(talonIntakeRight, talonIntakeLeft, talonTip, PCM);

	}

	@Override
	public void robotInit() {
		String[] autoList = { "Center Switch", "Right Switch", "Left Switch", "Drive Forward", "Left Scale",
				"Right Scale", "Left Switch/Scale", "Right Switch/Scale", "Test Auto" };
		SmartDashboard.putStringArray("Auto List", autoList);

		if (!cascadeElevator.lowerLimit.get())
			cascadeElevator.zeroSensors();

		// Start up cameras
		CameraControl cameras = new CameraControl(320, 240, 15);
		cascadeElevator.lastPosition = 0;

		updatePID();

	}

	public void teleopInit() {

		System.out.println("TELEOP MODE INIT");
		talonFR.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 0.0, 0, 0, 0);
		talonFL.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 0.0, 0, 0, 0);
		PCM.turnOn();
		Command driveJoystick = new DriveGamepad(gamepad, tankDrive, cascadeElevator);
		Scheduler.getInstance().add(driveJoystick);

		this.updatePID();

		talonFR.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 0.0, 0, 0, 0);
		talonFL.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 0.0, 0, 0, 0);
		talonTip.setSelectedSensorPosition(0, 0, 10);
		
		cascadeElevator.lastPosition = talonCascade.getSelectedSensorPosition(0);

		// cascadeElevator.lastPosition = 0;

	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		// Drive with joystick control in velocity mode
		// Buttons 8 & 9 or (gamepad) 5 & 6 are Low & High gear, respectively
		if (gamepad.getRawButton(Constants.LOGITECH_LEFTBUMPER))
			tankDrive.setLowGear();
		else if (gamepad.getRawButton(Constants.LOGITECH_RIGHTBUMPER))
			tankDrive.setHighGear();
		else
			tankDrive.setNoGear();

		boolean p = XBOX.getRawButton(Constants.XBOX_SELECT);
		if(p && !pressed8) {
			intakeLowVoltage = !intakeLowVoltage;
			pressed8 = p;
		} else if (!p && pressed8)
			pressed8 = p;

		if (Math.abs(XBOX.getRawAxis(Constants.XBOX_LEFTSTICK_YAXIS)) > 0.1) {
			double speed = XBOX.getRawAxis(Constants.XBOX_LEFTSTICK_YAXIS);
			if (speed < 0)
				speed /= 10;
			talonTip.set(ControlMode.PercentOutput, -XBOX.getRawAxis(Constants.XBOX_LEFTSTICK_YAXIS));
		} else
			talonTip.set(ControlMode.PercentOutput, 0);

		if (Math.abs(XBOX.getRawAxis(Constants.XBOX_RIGHTSTICK_YAXIS)) > Constants.CASCADE_DEADZONE) {
			double s = XBOX.getRawAxis(Constants.XBOX_RIGHTSTICK_YAXIS);
			double max = s < 0 ? 1200 : 600;

			cascadeElevator.setVelocity(s * max);
			cascadeElevator.lastPosition = cascadeElevator.talonCascade.getSelectedSensorPosition(0);

		} else if (XBOX.getRawButton(Constants.XBOX_A))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_BASE, XBOX));
		else if (XBOX.getRawButton(Constants.XBOX_B))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_SWITCH, XBOX));
		else if (XBOX.getRawButton(Constants.XBOX_X))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_LOWER_SCALE, XBOX));
		else if (XBOX.getRawButton(Constants.XBOX_Y))
			Scheduler.getInstance().add(new CascadePosition(cascadeElevator, Constants.CASCADE_UPPER_SCALE, XBOX));
		else if (!cascadeElevator.runningPreset) {
			if (Math.abs(cascadeElevator.talonCascade.getSelectedSensorPosition(0)) > 100
					&& !cascadeElevator.lowerLimit.get()) {
				cascadeElevator.talonCascade.selectProfileSlot(1, 0);
				cascadeElevator.talonCascade.set(ControlMode.Position, cascadeElevator.lastPosition);

			}
		}

		if (XBOX.getRawAxis(Constants.XBOX_LEFTTRIGGER) > 0.1) {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, 0.5);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, -0.5);
		}

		if (XBOX.getRawButton(Constants.XBOX_LEFTBUMPER) || XBOX.getRawButton(Constants.XBOX_RIGHTBUMPER)
				|| gamepad.getRawAxis(2) > 0.8 || gamepad.getRawAxis(3) > 0.8)
			boxManipulator.closeManipulator();
		else
			boxManipulator.openManipulator();

		double triggerL = XBOX.getRawAxis(Constants.XBOX_LEFTTRIGGER);
		double triggerR = XBOX.getRawAxis(Constants.XBOX_RIGHTTRIGGER);

		if (triggerL > 0.9) {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, triggerL * triggerL);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, -triggerL * triggerL);
		}
		if (triggerL > 0.1) {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, triggerL * triggerL / 2);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, -triggerL * triggerL / 2);
		} else if (triggerR > 0.1) {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, -triggerR * triggerR / 2);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, triggerR * triggerR / 2);
		} else if (intakeLowVoltage) {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, -0.1);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, 0.1);
		}

		else if (XBOX.getRawAxis(Constants.XBOX_RIGHTTRIGGER) > 0.1) {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, -0.5);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, 0.5);
		} else {
			boxManipulator.talonIntakeRight.set(ControlMode.PercentOutput, 0);
			boxManipulator.talonIntakeLeft.set(ControlMode.PercentOutput, 0);
		}
		try {
			Thread.sleep(10);
		} catch (Exception e) {
		}

	}

	public void disabledInit() {
		Scheduler.getInstance().removeAll();
	}

	public void autonomousInit() {
		talonFR.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 1.0, 1, 0, 0);
		talonFL.configSetParameter(ParamEnum.eOnBoot_BrakeMode, 1.0, 1, 0, 0);

		System.out.println("AUTOMODE INIT");

		FMS.init();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		updatePID();
		String AutonomousMode;
		AutonomousMode = SmartDashboard.getString("Auto Selector", "");
		if (AutonomousMode.equals("Drive Forward"))
			Scheduler.getInstance().add(new DriveDistance2(tankDrive, 10));
		else if (AutonomousMode.equals("Center Switch"))
			Scheduler.getInstance().add(new MiddleAuto(tankDrive, cascadeElevator, boxManipulator, gamepad));
		else if (AutonomousMode.equals("Right Switch"))
			Scheduler.getInstance().add(new RightSwitchAuto(tankDrive, cascadeElevator, boxManipulator, gamepad));
		else if (AutonomousMode.equals("Left Switch"))
			Scheduler.getInstance().add(new LeftSwitchAuto(tankDrive, cascadeElevator, boxManipulator, gamepad));
		else if (AutonomousMode.equals("Right Scale"))
			Scheduler.getInstance().add(new RightSideAuto(tankDrive, cascadeElevator, boxManipulator, gamepad));
		else if (AutonomousMode.equals("Left Scale"))
			Scheduler.getInstance().add(new LeftSideAuto(tankDrive, cascadeElevator, boxManipulator, gamepad));
		else if (AutonomousMode.equals("Left Switch/Scale"))
			Scheduler.getInstance().add(new LeftSideAuto2(tankDrive, cascadeElevator, boxManipulator, gamepad));
		else if (AutonomousMode.equals("Right Switch/Scale"))
			Scheduler.getInstance().add(new RightSideAuto2(tankDrive, cascadeElevator, boxManipulator, gamepad));
		else if (AutonomousMode.equals("Test Auto"))

			Scheduler.getInstance().add(new MiddleAuto(tankDrive, cascadeElevator, boxManipulator, gamepad));

	}

	public void autonomousPeriodic() {

		Scheduler.getInstance().run();

		try {
			Thread.sleep(10);
		} catch (Exception e) {
		}
		if (!cascadeElevator.runningPreset) {
			if (Math.abs(cascadeElevator.talonCascade.getSelectedSensorPosition(0)) > 100
					&& !cascadeElevator.lowerLimit.get()) {
				cascadeElevator.talonCascade.selectProfileSlot(1, 0);
				cascadeElevator.talonCascade.set(ControlMode.Position, cascadeElevator.lastPosition);
			}
		}
	}

	@Override
	public void testInit() {
		System.out.println("TEST MODE INIT");
	}
	@Override
	public void testPeriodic() {
	}
	// updates the PID in gyro with the sliders or the networktables.
	public void updatePID() {
		PIDGains p = new PIDGains();
	
		p.p = 1.0; // SmartDashboard.getNumber("DB/Slider 0", 0);
		p.i = 0; // SmartDashboard.getNumber("DB/Slider 1", 0);
		p.d = 0; // SmartDashboard.getNumber("DB/Slider 2", 0);
		p.ff = 0; // SmartDashboard.getNumber("DB/Slider 3", 0);
		tankDrive.setPid(p);
		SmartDashboard.putString("DB/String 6", String.valueOf(p.p));
		SmartDashboard.putString("DB/String 7", String.valueOf(p.i));
		SmartDashboard.putString("DB/String 8", String.valueOf(p.d));
		SmartDashboard.putString("DB/String 9", String.valueOf(p.ff));
	}

}
