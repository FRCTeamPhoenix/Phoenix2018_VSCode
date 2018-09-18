package org.usfirst.frc.team2342.util;

import edu.wpi.first.wpilibj.DriverStation;

public class FMS {
	static String gameData = "LLL";
	//1 2 or 3
	static int position = 1;
	
	public static void init(){
		position = DriverStation.getInstance().getLocation();
		gameData = DriverStation.getInstance().getGameSpecificMessage();
	}
	
	public static int getPosition(){
		return position;
	}
	
	/*
	 * true == left
	 * false == right
	 */
	public static boolean teamSwitch(){
		return gameData.charAt(0) == 'L';
	}
	
	/*
	 * true == left
	 * false == right
	 */
	public static boolean scale(){
		return gameData.charAt(1) == 'L';
	}
	
	/*
	 * true == left
	 * false == right
	 */
	public static boolean enemySwitch(){
		return gameData.charAt(2) == 'L';
	}
}
