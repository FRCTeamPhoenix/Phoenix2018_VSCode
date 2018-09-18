package org.usfirst.frc.team2342.util;

import edu.wpi.first.wpilibj.SerialPort;

public class Constants {
	
	public static final int AUTO_POS_LEFT = 0;
	public static final int AUTO_POS_MID = 1;
	public static final int AUTO_POS_RIGHT = 2;
	
	public static final int LEFT_MASTER_TALON_ID = 1;
	public static final int RIGHT_MASTER_TALON_ID = 2;
	public static final int LEFT_SLAVE_TALON_ID = 3;
	public static final int RIGHT_SLAVE_TALON_ID = 4;

	public static final int TALON_VELOCITY_SLOT_IDX = 0;
	public static final int TALON_DISTANCE_SLOT_IDX = 1;

	public static final double TALON_TICKS_PER_REV = 4096.0;
	public static final double TALON_SPEED_RPS = TALON_TICKS_PER_REV / 10;
	//public static final double TALON_RPM_TO_VELOCITY = 1;//TALON_TICKS_PER_REV / 600.0;

	public static final double TALON_RPS_TO_FPS = 1.57;

	public static final double JOYSTICK_DEADZONE = 0.025;

	// Scales the speed of velocity mode (in rps)
	public static final double WESTCOAST_MAX_SPEED = TALON_SPEED_RPS * 5.95d; // It is set for the tuning of the controller manuverablility
	public static final double WESTCOAST_HALF_SPEED = WESTCOAST_MAX_SPEED * 0.5d;
	public static final double WESTCOAST_TURN_SPEED = WESTCOAST_MAX_SPEED * 0.7d;
	
	public static final int TURN_AVERAGE_SIZE = 10;
	public static final double TURN_THRESHOLD = 3;
	
	public static final int PCM_PORT = 11;

	public static final int LOWER_SENSOR_POSITION = 0;
	public static final int UPPER_SENSOR_POSITION = -28000;
	public static final int INCHES_TO_TICKS_CASCADE = -290;

	//where on the smartdashboard talons go
	public static final String TALON_TABLE_LOCATION = "Talons";

	//sensor ports
	public static final SerialPort.Port LIDAR_PORT = SerialPort.Port.kMXP;
	//DIO port for infrared
	public static final int INFRARED_PORT = 9;

	//sensor stuff
	public static final int SERIAL_BAUD_RATE = 115200;

	//lidar averages
	public static final int SLOW_AVERAGE_SIZE = 25;
	public static final int FAST_AVERAGE_SIZE = 10;


	//cm to inches
	public static final double CM_TO_INCHES = 0.393701;

	//inches to cm
	public static final double INCHES_TO_CM = 2.54;

	//limit switches
	public static final int LOWER_LIMIT_SWITCH = 1;
	public static final int UPPER_LIMIT_SWITCH = 0;

	//boxmanipulator talon constants
	public static final int TALON_CASCADE = 6;
	public static final int TALON_INTAKE_RIGHT = 7;
	public static final int TALON_INTAKE_LEFT = 5;
	public static final int TALON_TIP = 8;
	public static final int TALON_CLIMBER = 0;

	//positions of important cascade heights
	public static final int CASCADE_BASE = 0;
	public static final int CASCADE_SWITCH = 12000;
	public static final int CASCADE_LOWER_SCALE = 22000;
	public static final int CASCADE_UPPER_SCALE = 25500;

	//solenoid constants
	public static final int PCM_CAN_ID = 11;
	public static final int PCM_SLOT_HIGHGEAR = 0;
	public static final int PCM_SLOT_LOWGEAR = 1;

	public static final int PCM_BOX_MANIPULATOR = 2;
	
	//logitech controller
	public static final int LOGITECH_LEFTSTICK_XAXIS = 0;
	public static final int LOGITECH_LEFTSTICK_YAXIS = 1;
	public static final int LOGITECH_RIGHTSTICK_XAXIS = 2;
	public static final int LOGITECH_RIGHTSTICK_YAXIS = 3;
	
	public static final int LOGITECH_X = 1;
	public static final int LOGITECH_A = 2;
	public static final int LOGITECH_B = 3;
	public static final int LOGITECH_Y = 4;
	
	public static final int LOGITECH_LEFTBUMPER = 5;
	public static final int LOGITECH_RIGHTBUMPER = 6;
	public static final int LOGITECH_LEFTTRIGGER = 7;
	public static final int LOGITECH_RIGHTTRIGGER = 8;
	
	public static final int LOGITECH_BACK = 9;
	public static final int LOGITECH_START = 10;
	
	//xbox controller
	public static final int XBOX_LEFTSTICK_XAXIS = 0;
	public static final int XBOX_LEFTSTICK_YAXIS = 1;
	public static final int XBOX_LEFTTRIGGER = 2;
	public static final int XBOX_RIGHTTRIGGER = 3;
	public static final int XBOX_RIGHTSTICK_XAXIS = 4;
	public static final int XBOX_RIGHTSTICK_YAXIS = 5;
	
	public static final int XBOX_X = 3;
	public static final int XBOX_A = 1;
	public static final int XBOX_B = 2;
	public static final int XBOX_Y = 4;
	
	public static final int XBOX_LEFTBUMPER = 5;
	public static final int XBOX_RIGHTBUMPER = 6;
	
	public static final int XBOX_SELECT = 8;
	public static final int XBOX_START = 7;
	
	public static final double tSpeed = 4.5d;
	
	public static final double CASCADE_DEADZONE = 0.1;

	// Drive Talon PID Constants
	public static final double dtKp     = 1.5d;
	public static final double dtKi     = 0.0d;
	public static final double dtKd     = 0.0d;
	public static final double dtKrr    = 2.0d;
	public static final int dtKizone    = 0;
	public static final double dtKff    = 0.15d;
	
	// PID for Gyro Forward
	public static final double Kp = 0.02d;
	public static final double Ki = 0.0d;
	public static final double Kd = 0.0d;

	// PID for Gyro TIP (Turn In Place)
	public static final double tKp = 0.1d;
	public static final double tKi = 0.001d;
	public static final double tKd = 0.0d;
}
