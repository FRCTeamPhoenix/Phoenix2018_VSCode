package org.usfirst.frc.team2342.robot.sensors;

import com.analog.adis16448.frc.ADIS16448_IMU;

import edu.wpi.first.wpilibj.PIDSourceType;

public class Gyro {
	static ADIS16448_IMU imu;
	
	private static double goalAngle;
	
	public static void init(){
		imu = new ADIS16448_IMU();
		goalAngle = 0.0;
	}
	
	/*
	 * sets current position to 0 degrees*/
	public static void reset(){
		imu.reset();
	}
	
	public static void setGoal(){
		goalAngle = imu.getAngleX();
	}
	
	public static void setGoal(double offset){
		goalAngle = imu.getAngleX() + offset;
	}
	
	public static void calibrate() {
		imu.calibrate();
	}
	
	public static double angleFromGoal(){
		return goalAngle - imu.getAngleX();
	}
	
	public static double angle(){
		return imu.getAngleX();
	}

	public static void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		imu.setPIDSourceType(pidSource);
	}

	public static PIDSourceType getPIDSourceType() {
		return imu.getPIDSourceType();
	}
}
