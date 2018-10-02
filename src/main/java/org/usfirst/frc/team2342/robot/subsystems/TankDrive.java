package org.usfirst.frc.team2342.robot.subsystems;

import org.usfirst.frc.team2342.robot.PCMHandler;
import org.usfirst.frc.team2342.util.Constants;
import org.usfirst.frc.team2342.util.PIDGains;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TankDrive extends Subsystem{

	public WPI_TalonSRX leftA;
	public WPI_TalonSRX rightA;
	private WPI_TalonSRX leftB;
	private WPI_TalonSRX rightB;
	private PCMHandler PCM;
	private final int PidLoopIndexHigh = 0;
	private final int PidLoopIndexLow = 0;
	private final int PidTimeOutMs = 10;
	
	public TankDrive(PCMHandler PCM, WPI_TalonSRX leftFR, WPI_TalonSRX rightFR, WPI_TalonSRX leftBA, WPI_TalonSRX rightBA) {
		
		this.PCM = PCM;
		leftA = leftFR;
		rightA = rightFR;
		leftB = leftBA;
		rightB = rightBA;
		leftA.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.TALON_VELOCITY_SLOT_IDX, 0);
		rightA.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.TALON_VELOCITY_SLOT_IDX, 0);

		leftA.configAllowableClosedloopError(0, Constants.TALON_DISTANCE_SLOT_IDX, 0);
		rightA.configAllowableClosedloopError(0, Constants.TALON_DISTANCE_SLOT_IDX, 0);
	
		// If the talons run indefinitely, the sensors may be reading in the wrong direction,
		// in which case the sensor phase should be inverted.
		leftA.setSensorPhase(false);
		rightA.setSensorPhase(false);

		// Invert the appropriate talons
		leftA.setInverted(false);
		rightA.setInverted(true);
		leftB.setInverted(false);
		rightB.setInverted(true);

		// Constrain the speed of all talons to [-max, max]
		leftA.configNominalOutputForward(0, 0);
		leftA.configNominalOutputReverse(0, 0);
		leftA.configPeakOutputForward(1.0, 0);
		leftA.configPeakOutputReverse(-1.0, 0);
		rightA.configNominalOutputForward(0, 0);
		rightA.configNominalOutputReverse(0, 0);
		rightA.configPeakOutputForward(1.0, 0);
		rightA.configPeakOutputReverse(-1.0, 0);
	
		leftA.config_kF(PidLoopIndexHigh, SmartDashboard.getNumber("DB/Slider 0", 0), PidTimeOutMs);
		leftA.config_kP(PidLoopIndexHigh, SmartDashboard.getNumber("DB/Slider 1", 0), PidTimeOutMs);
		leftA.config_kI(PidLoopIndexHigh, SmartDashboard.getNumber("DB/Slider 2", 0), PidTimeOutMs);
		leftA.config_kD(PidLoopIndexHigh, SmartDashboard.getNumber("DB/Slider 3", 0), PidTimeOutMs);
		
		rightA.config_kF(PidLoopIndexHigh, SmartDashboard.getNumber("DB/Slider 0", 0), PidTimeOutMs);
		rightA.config_kP(PidLoopIndexHigh, SmartDashboard.getNumber("DB/Slider 1", 0), PidTimeOutMs);
		rightA.config_kI(PidLoopIndexHigh, SmartDashboard.getNumber("DB/Slider 2", 0), PidTimeOutMs);
		rightA.config_kD(PidLoopIndexHigh, SmartDashboard.getNumber("DB/Slider 3", 0), PidTimeOutMs);
		
		leftA.config_kF(PidLoopIndexLow, 0.0, PidTimeOutMs);
		leftA.config_kP(PidLoopIndexLow, 0.02, PidTimeOutMs);
		leftA.config_kI(PidLoopIndexLow, 0.0, PidTimeOutMs);
		leftA.config_kD(PidLoopIndexLow, 0, PidTimeOutMs);
		
		rightA.config_kF(PidLoopIndexLow, 0.0, PidTimeOutMs);
		rightA.config_kP(PidLoopIndexLow, 0.02, PidTimeOutMs);
		rightA.config_kI(PidLoopIndexLow, 0.0, PidTimeOutMs);
		rightA.config_kD(PidLoopIndexLow, 0, PidTimeOutMs);
		
		leftB.follow(leftA);
		rightB.follow(rightA);
		zeroSensors(); 
	}
	static void zeroEncoders(WPI_TalonSRX talon) {
		talon.setSelectedSensorPosition(0, Constants.TALON_VELOCITY_SLOT_IDX, 10);
	}
	
	public void zeroSensors() {
		leftA.setSelectedSensorPosition(0, Constants.TALON_VELOCITY_SLOT_IDX, 10);
		leftB.setSelectedSensorPosition(0, Constants.TALON_VELOCITY_SLOT_IDX, 10);
		rightA.setSelectedSensorPosition(0, Constants.TALON_VELOCITY_SLOT_IDX, 10);
		rightB.setSelectedSensorPosition(0, Constants.TALON_VELOCITY_SLOT_IDX, 10);
	}
	
	public void setPercentage(double left,double right) {
		leftA.set(ControlMode.PercentOutput, -left);
		rightA.set(ControlMode.PercentOutput,-right);
	}
	
	public void setLowGear() {
		PCM.setLowGear(false);
		PCM.setHighGear(true);
		PCM.compressorRegulate();
		leftA.selectProfileSlot(Constants.TALON_VELOCITY_SLOT_IDX, PidLoopIndexHigh);
	}

	public void setHighGear() {
		/*leftA.config_kF(PidLoopIndexHigh, SmartDashboard.getNumber("DB/Slider 0", 0), PidTimeOutMs);
		leftA.config_kP(PidLoopIndexHigh, SmartDashboard.getNumber("DB/Slider 1", 0), PidTimeOutMs);
		leftA.config_kI(PidLoopIndexHigh, SmartDashboard.getNumber("DB/Slider 2", 0), PidTimeOutMs);
		leftA.config_kD(PidLoopIndexHigh, SmartDashboard.getNumber("DB/Slider 3", 0), PidTimeOutMs);
		
		rightA.config_kF(PidLoopIndexHigh, SmartDashboard.getNumber("DB/Slider 0", 0), PidTimeOutMs);
		rightA.config_kP(PidLoopIndexHigh, SmartDashboard.getNumber("DB/Slider 1", 0), PidTimeOutMs);
		rightA.config_kI(PidLoopIndexHigh, SmartDashboard.getNumber("DB/Slider 2", 0), PidTimeOutMs);
		rightA.config_kD(PidLoopIndexHigh, SmartDashboard.getNumber("DB/Slider 3", 0), PidTimeOutMs);*/
		
		PCM.setHighGear(false);
		PCM.setLowGear(true);
		PCM.compressorRegulate();
		leftA.selectProfileSlot(Constants.TALON_VELOCITY_SLOT_IDX, PidLoopIndexLow);
	}

	public void setNoGear() {
		PCM.setHighGear(false);
		PCM.setLowGear(false);
		PCM.compressorRegulate();
	}
	
	public void setVelocity(double left,double right) {
//		if (left > 0)
//			left = Math.min(left, Constants.WESTCOAST_MAX_SPEED);
//		else if (left < 0)
//			left = Math.max(left, -Constants.WESTCOAST_MAX_SPEED);
//		if (right > 0)
//			right = Math.min(right, Constants.WESTCOAST_MAX_SPEED);
//		else if (right < 0)
//			right = Math.max(right, -Constants.WESTCOAST_MAX_SPEED);
		//System.out.println("going " + left + " " + right + "    " + leftA.getSelectedSensorVelocity(0) + " " + rightA.getSelectedSensorVelocity(0));
		leftA.set(ControlMode.Velocity,left);
		rightA.set(ControlMode.Velocity,right);
	}
	
	//the encoder values has to be zeroed for this to work
	public void goDistance(double distanceInFeet) {
		
		
		double speed = Constants.WESTCOAST_HALF_SPEED;
		/*if (-leftA.getSelectedSensorPosition(PidLoopIndexHigh) < distanceInFeet/Constants.TALON_RPS_TO_FPS * Constants.TALON_TICKS_PER_REV)
			speed *= -1;*/
		
		setVelocity(-speed,-speed);
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	public void setPid(PIDGains pid) {
		leftA.config_kF(PidLoopIndexHigh, pid.ff, PidTimeOutMs);
		leftA.config_kP(PidLoopIndexHigh, pid.p, PidTimeOutMs);
		leftA.config_kI(PidLoopIndexHigh, pid.i, PidTimeOutMs);
		leftA.config_kD(PidLoopIndexHigh, pid.d, PidTimeOutMs);
		rightA.config_kF(PidLoopIndexHigh, pid.ff, PidTimeOutMs);
		rightA.config_kP(PidLoopIndexHigh, pid.p, PidTimeOutMs);
		rightA.config_kI(PidLoopIndexHigh, pid.i, PidTimeOutMs);
		rightA.config_kD(PidLoopIndexHigh, pid.d, PidTimeOutMs);
	}
	
}