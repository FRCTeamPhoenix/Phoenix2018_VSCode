package org.usfirst.frc.team2342.robot.subsystems;

import org.usfirst.frc.team2342.PIDLoops.DistancePIDController;
import org.usfirst.frc.team2342.PIDLoops.GyroPIDController;
import org.usfirst.frc.team2342.json.PIDGains;
import org.usfirst.frc.team2342.robot.PCMHandler;
//import org.usfirst.frc.team2342.robot.TalonNWT;
import org.usfirst.frc.team2342.util.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WestCoastTankDrive extends Subsystem {

	private WPI_TalonSRX leftA, rightA, leftB, rightB;
	private PCMHandler m_PCM;
	public DistancePIDController dpidc;
	public GyroPIDController pidc;       // GyroPIDController
	public boolean debug = false;        // debug messages
	private boolean gyroControl = false; // gyro Control
	private boolean isLeftInner = false; // is the left wheel inner

	public WestCoastTankDrive(PCMHandler PCM, WPI_TalonSRX leftFR, WPI_TalonSRX rightFR, WPI_TalonSRX leftBA, WPI_TalonSRX rightBA) {
		//Json config = JsonHelpe.getConfig();
		m_PCM = PCM;
		leftA = leftFR;
		rightA = rightFR;
		leftB = leftBA;
		rightB = rightBA;
		//dpidc = new DistancePIDController(0.0008d, 0.0d, 0.0d, 0.0d, leftA, rightA);
		
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


		leftB.follow(leftA);
		rightB.follow(rightA);

		// TODO Temporary! Once JsonHelper works, we should use that.
		PIDGains fakeGains = new PIDGains();
		fakeGains.p = 0.13333;
		fakeGains.i = 0;
		fakeGains.d = 0.05;
		fakeGains.ff = 0.0; // Note: only velocity mode do we need ff
		fakeGains.izone = 0;

		// TODO are these the right indices of the talons?
		/*        PIDGains leftVelocityGains = //config.talons.get(0).velocityGains;
        PIDGains leftDistanceGains = //config.talons.get(0).distanceGains;
        PIDGains rightVelocityGains = //config.talons.get(1).velocityGains;
        PIDGains rightDistanceGains = //config.talons.get(1).distanceGains;


        WestCoastTankDrive.loadGains(leftA, Constants.TALON_VELOCITY_SLOT_IDX, leftVelocityGains);
        WestCoastTankDrive.loadGains(leftA, Constants.TALON_DISTANCE_SLOT_IDX, leftDistanceGains);
        WestCoastTankDrive.loadGains(rightA, Constants.TALON_VELOCITY_SLOT_IDX, rightVelocityGains);
        WestCoastTankDrive.loadGains(rightA, Constants.TALON_DISTANCE_SLOT_IDX, rightDistanceGains);
        WestCoastTankDrive.loadGains(leftB, Constants.TALON_VELOCITY_SLOT_IDX, leftVelocityGains);
        WestCoastTankDrive.loadGains(leftB, Constants.TALON_DISTANCE_SLOT_IDX, leftDistanceGains);
        WestCoastTankDrive.loadGains(rightB, Constants.TALON_VELOCITY_SLOT_IDX, rightVelocityGains);
        WestCoastTankDrive.loadGains(rightB, Constants.TALON_DISTANCE_SLOT_IDX, rightDistanceGains);*/

		pidc = null; // gyropidcontroller is not needed in westcoast yet.
		zeroSensors(); 
	}

	public void setOpenLoop(double left, double right) {
		leftA.set(ControlMode.PercentOutput, left);
		rightA.set(ControlMode.PercentOutput, right);
		leftB.set(ControlMode.PercentOutput, left);
		rightB.set(ControlMode.PercentOutput, right);
	}

	// Set the velocity for the tank drive
	public void setVelocity(double left, double right) {
		if (!leftA.getControlMode().equals(ControlMode.Velocity)) {
			leftA.selectProfileSlot(Constants.TALON_VELOCITY_SLOT_IDX, 0);
		}
		if (this.gyroControl != true) {
			leftA.set(ControlMode.Velocity, left);
			rightA.set(ControlMode.Velocity, right);
			if (this.debug) {
				SmartDashboard.putString("DB/String 0", String.valueOf(left));
				SmartDashboard.putString("DB/String 1", String.valueOf(right));
			}
		}
		else {
			left = left  * (1 - pidc.getCorrection());
			right = right * (1 + pidc.getCorrection());
			leftA.set(ControlMode.Velocity,  left);
			rightA.set(ControlMode.Velocity, right);
			if (this.debug) {
				//printGDebug(left, right);
				SmartDashboard.putString("DB/String 3", String.valueOf(left));
				SmartDashboard.putString("DB/String 4", String.valueOf(right));
			}
		}
	}

	public void setPercentage(double left, double right) {
		leftA.set(ControlMode.PercentOutput, left);
		rightA.set(ControlMode.PercentOutput, right);
		if (this.debug == true) {
			System.out.println("LEFT:  " + String.valueOf(left));
			System.out.println("RIGHT: " + String.valueOf(right));
		}
	}

	public void goDistance(double distanceInFeet){
		double distance = distanceInFeet/Constants.TALON_RPS_TO_FPS * Constants.TALON_TICKS_PER_REV;
		dpidc.setGoal(distance);
		if (!leftA.getControlMode().equals(ControlMode.Velocity)) {
			leftA.selectProfileSlot(Constants.TALON_VELOCITY_SLOT_IDX, 0);
		}
		double vel = Constants.WESTCOAST_MAX_SPEED * dpidc.getCorrection();
		if (!this.gyroControl) {
			leftA.set(ControlMode.Velocity,  vel);
			rightA.set(ControlMode.Velocity, vel);
		}
		else {
			SmartDashboard.putString("DB/String 2", String.valueOf(vel));
			this.setVelocity(vel, vel);
		}
	}

	public void distanceLoop(){
		if (!leftA.getControlMode().equals(ControlMode.Velocity)) {
			leftA.selectProfileSlot(Constants.TALON_VELOCITY_SLOT_IDX, 0);
		}
		double vel = Constants.WESTCOAST_MAX_SPEED * dpidc.getCorrection();
		if (!this.gyroControl) {
			leftA.set(ControlMode.Velocity,  vel);
			rightA.set(ControlMode.Velocity, vel);
		}
		else {
			SmartDashboard.putString("DB/String 2", String.valueOf(vel));
			this.setVelocity(vel, vel);
		}
	}

	// Turn setup for rotating the robot
	public void turnSet(double angle) {
		//		this.pidc.setP(Constants.tKp);
		//		this.pidc.setI(Constants.tKi);
		//		this.pidc.setD(Constants.tKd);
		this.updateGyroPID();
	}

	// Rotate the robot in autonomous
	public void rotateAuto(double velocity) {
		if (!leftA.getControlMode().equals(ControlMode.Velocity)) {
			leftA.selectProfileSlot(Constants.TALON_VELOCITY_SLOT_IDX, 0);
		}

		if (this.gyroControl == true) {
			double lspeed = -velocity * pidc.getCorrection();
			double rspeed =  velocity * pidc.getCorrection();
			if (this.debug) {
				SmartDashboard.putString("DB/String 0", String.valueOf(this.pidc.getCurAngle()));
				SmartDashboard.putString("DB/String 2", String.valueOf(lspeed));
				SmartDashboard.putString("DB/String 3", String.valueOf(rspeed));
				printGDebug(lspeed, rspeed);
			}
			leftA.set(ControlMode.Velocity,  lspeed);
			rightA.set(ControlMode.Velocity, rspeed);
		}
	}

	public boolean isDistanceFinished(){
		return (dpidc.pidGet() > dpidc.getGoal()*1.02 && dpidc.pidGet() < dpidc.getGoal()*0.98);
	}

	private double innerSpeed = 0.0d;
	private double outerSpeed = 0.0d;

	public void goArc(double radius, double degrees, double outerMultiplyer, double innerMultiplyer, boolean isLeftInner){
		this.isLeftInner = isLeftInner;
		innerMultiplyer = 1.0d;

		this.pidc.updateAngle(this.pidc.getCurAngle());
		double circumfrence = 2 * radius * Math.PI;
		double innerCircumfrence = 2 * (radius - (11.0/12.0)) * Math.PI;
		double outerCircumfrence = 2 * (radius + (11.0/12.0)) * Math.PI;

		double degreeMultiplyer = degrees / 360;
		innerSpeed = Constants.WESTCOAST_MAX_SPEED * ((radius - (2)/2.0)/(radius + (2)/2.0));
		//		innerSpeed = Constants.WESTCOAST_MAX_SPEED * ((radius - (2)/2.0)/(radius + (2)/2.0));
		outerSpeed = Constants.WESTCOAST_MAX_SPEED * outerMultiplyer;
		double distance = circumfrence/Constants.TALON_RPS_TO_FPS * Constants.TALON_TICKS_PER_REV;
		dpidc.setGoal(distance * degreeMultiplyer);
		if (!leftA.getControlMode().equals(ControlMode.Velocity)) {
			leftA.selectProfileSlot(Constants.TALON_VELOCITY_SLOT_IDX, 0);
		}
		if (!rightA.getControlMode().equals(ControlMode.Velocity)) {
			rightA.selectProfileSlot(Constants.TALON_VELOCITY_SLOT_IDX, 0);
		}
		if (this.debug == true) 
			this.printGDebug(innerSpeed, outerSpeed);
		//leftA.set(ControlMode.Velocity, Constants.WESTCOAST_MAX_SPEED * dpidc.getCorrection());
		//rightA.set(ControlMode.Velocity, Constants.WESTCOAST_MAX_SPEED * dpidc.getCorrection());
	}

	public void arcLoop(boolean isLeftInner){
		double leftV = 0.0d;
		double rightV = 0.0d;
		if (!leftA.getControlMode().equals(ControlMode.Velocity)) {
			leftA.selectProfileSlot(Constants.TALON_VELOCITY_SLOT_IDX, 0);
		}
		if(this.isLeftInner){
			leftV = innerSpeed * (1 - dpidc.getCorrection());
			rightV = outerSpeed * (1 + dpidc.getCorrection());
			leftA.set(ControlMode.Velocity, -leftV);
			rightA.set(ControlMode.Velocity, -rightV);
		}else{
			rightV = innerSpeed * (1 - dpidc.getCorrection());
			leftV = outerSpeed * (1 + dpidc.getCorrection());
			rightA.set(ControlMode.Velocity, -rightV);
			leftA.set(ControlMode.Velocity, -leftV);
		}
		if (this.debug == true) 
			this.printGDebug(leftV, rightV);
	}

	public double getInner() {
		return this.innerSpeed;
	}

	public void outputToSmartDashboard() {
//		TalonNWT.updateTalon(leftA);
//		TalonNWT.updateTalon(leftB);
//		TalonNWT.updateTalon(rightA);
//		TalonNWT.updateTalon(rightB);
	}

	public void stop() {
		setOpenLoop(0, 0);
	}

	public void zeroSensors() {
		WestCoastTankDrive.zeroEncoders(leftA);
		WestCoastTankDrive.zeroEncoders(rightA);
		WestCoastTankDrive.zeroEncoders(leftB);
		WestCoastTankDrive.zeroEncoders(rightB);
	}

	public void setHighGear() {
		m_PCM.setLowGear(false);
		m_PCM.setHighGear(true);
		m_PCM.compressorRegulate();
	}

	public void setLowGear() {
		m_PCM.setHighGear(false);
		m_PCM.setLowGear(true);
		m_PCM.compressorRegulate();
	}

	public void setNoGear() {
		m_PCM.setHighGear(false);
		m_PCM.setLowGear(false);
		m_PCM.compressorRegulate();
	}

	public void zeroSnesors() {
		this.pidc.reset();
	}

	private static void zeroEncoders(WPI_TalonSRX talon) {
		talon.setSelectedSensorPosition(0, Constants.TALON_VELOCITY_SLOT_IDX, 0);
	}

	private void loadGains(WPI_TalonSRX talon, int slotIdx, PIDGains talonPID) {
		talon.config_kP(slotIdx, talonPID.p, 0);
		talon.config_kI(slotIdx, talonPID.i, 0);
		talon.config_kD(slotIdx, talonPID.d, 0);
		talon.config_kF(slotIdx, talonPID.ff, 0);
		talon.config_IntegralZone(slotIdx, talonPID.izone, 0);
		talon.configOpenloopRamp(talonPID.rr, 0);
		//TalonNWT.setPIDValues(slotIdx, talon);
	}

	public void updateTalonPID(int slotIdx, PIDGains talonPID) {
		this.loadGains(this.leftA, slotIdx, talonPID);
		this.loadGains(this.leftB, slotIdx, talonPID);
		this.loadGains(this.rightA, slotIdx, talonPID);
		this.loadGains(this.rightB, slotIdx, talonPID);
	}

	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

	// updates the PID in gyro with the sliders or the networktables.
	public void updateGyroPID() {
		//TalonNWT.populateGyroPID(this.pidc);
		pidc.setP(SmartDashboard.getNumber("DB/Slider 0", 0));
		pidc.setI(SmartDashboard.getNumber("DB/Slider 1", 0));
		pidc.setD(SmartDashboard.getNumber("DB/Slider 2", 0));
		if (this.debug) {
			SmartDashboard.putString("DB/String 7", String.valueOf(pidc.getP()));
			SmartDashboard.putString("DB/String 8", String.valueOf(pidc.getI()));
			SmartDashboard.putString("DB/String 9", String.valueOf(pidc.getD()));
		}
	}

	// print gyro debug
	public void printGDebug(double lspeed, double rspeed) {
		System.out.println("INNER SPEED: " + String.valueOf(this.innerSpeed) + "     OUTER SPEED: " + String.valueOf(this.outerSpeed));
		System.out.println("Correction: " + pidc.getCorrection() + "    CurAngle: " + pidc.getCurAngle() + "    AngleD: " + pidc.calculateAE());
		System.out.println("LEFTV: " + String.valueOf(lspeed) + "  RIGHTV: " + String.valueOf(rspeed));
		System.out.println("Kp Vlaue: " + String.valueOf(pidc.getP()));
		System.out.println("Ki Vlaue: " + String.valueOf(pidc.getI()));
		System.out.println("Kd Vlaue: " + String.valueOf(pidc.getD()));
	}

	/*
	 * Need to get values for talons from network tables to be able to read and write talon values.
	 */

	// set gyro control
	public void setGyroControl(boolean b) {
		this.gyroControl = b;
	}
	
	public void setGyroPID(double p, double i, double d) {
		pidc.setP(p);
		pidc.setI(i);
		pidc.setD(d);
	}

}
