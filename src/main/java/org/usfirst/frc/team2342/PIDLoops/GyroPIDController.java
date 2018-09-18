package org.usfirst.frc.team2342.PIDLoops;

import org.usfirst.frc.team2342.robot.sensors.Gyro;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/*
 * By: Joshua Calzadillas
 * 
 * Class GyroPIDController
 * 
 * Parameters: GyroPIDController(Kp, Ki, Kd);
 * 
 * Example: GyroPIDController(0.02, 0.0, 0.0);
 * 
 * GyroPIDController is a gyro PID loop controller that utilizes the PIDController to correct the robot from straying off the path.
 * It takes in the PID constants to be able to stabilize the correction.
 * The class initializes the ADIS1664 Gyro to access the gyro and the angle along with PIDController, current angle, target angle, and the correction.
 * 
 */

// must implement the velocity PID control into the Gyro
public class GyroPIDController implements PIDSource, PIDOutput {
	//private static ADIS16448_IMU gyro; 		   // gyro instance
	private static PIDController pc;           // PID Controller
	private static double curAngle = 0.0d;     // Current angle
	private static double targetAngle = 0.0d;  // Target angle
	private static double correction = 0.0d;   // PID Correction


	// CONSTRUCTOR
	public void GyroPIDController() {
		//DONOTHING
	}
	
	public void init(double p, double i, double d) {		
		// Gyro and PIDController setup
		if (pc == null)
			pc = new PIDController(p, i, d, 0.0d, this, this);
		pc.disable();
		reset();
	}
	
	// reset the options for PID Controller
	public static void reset() {
		pc.reset();
		pc.setOutputRange(-1, 1);
	}

	// update the angle and the target thing
	public static void updateAngle(double angle) {
		targetAngle = angle;
		reset();
		pc.setSetpoint(targetAngle);
		pc.enable();
	}

	// get the p value
	public static double getP() {
		return pc.getP();
	}

	// set the p value
	public static void setP(double p) {
		pc.setP(p);
	}

	// get the i value
	public static double getI() {
		return pc.getI();
	}

	// set the i value
	public static void setI(double i) {
		pc.setI(i);
	}

	// get the d value
	public static double getD() {
		return pc.getD();
	}

	// set the d value
	public static void setD(double d) {
		pc.setD(d);
	}

	// calculate angle error
	public static double calculateAE() {
		return targetAngle - getCurAngle();
	}

	// set the target angle
	public static void setTargetAngle(double ta) {
		targetAngle = ta;
	}

	// return target angle
	public static double getTargetAngle() {
		return targetAngle;
	}

	/* 
	 * Update the current angle
	 * Angle measures:
	 * right is negative
	 * left is positive
	 */
	private static void updateCurAngle() {
		//this.curAngle = gyro.getAngleX() % 360;
		//this.curAngle = (this.curAngle < 0.0d) ? this.curAngle + 360.0d : this.curAngle;
		curAngle = Gyro.angle();
	}

	// get static the current angle
	public static double getCurAngle() {
		updateCurAngle();
		return curAngle;
	}

	// get the correction from PID LOOP
	public static double getCorrection() {
		return correction;
	}

	// get the PID Controller
	public static PIDController getPC() {
		return pc;
	}

	// Reset the gyro to have the angle set to zero.
	public static void gyroReset() {
		Gyro.reset();
	}
	
	/*
	 * WARNING: MAKE SURE BOT IS ON, AND DO NOT ROTATE THE BOT.
	 * LEAVE THE BOT ALONE!
	 */
	// Recallibrates the gyro 
	public static void callibrateGyro() {
		Gyro.calibrate();
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		Gyro.setPIDSourceType(pidSource);
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		//return PIDSourceType.kDisplacement;
		return Gyro.getPIDSourceType();
	}

	// IMPORTANT
	// PID Controller get the error value
	@Override
	public double pidGet() {
		return getCurAngle();
	}

	// IMPORTANT
	// PID Controller set the correction value
	@Override
	public void pidWrite(double output) {
		correction = output;
	}
}
