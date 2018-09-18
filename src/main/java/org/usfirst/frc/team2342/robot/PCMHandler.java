package org.usfirst.frc.team2342.robot;

import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PCMHandler {
	Compressor compressor; 
	Solenoid highgearSol;
	Solenoid lowgearSol; 
	Solenoid clawSol;

	public PCMHandler(int port) {
		
		compressor = new Compressor(port);
		compressor.setClosedLoopControl(true);
		
		highgearSol = new Solenoid(Constants.PCM_CAN_ID, Constants.PCM_SLOT_HIGHGEAR);
		lowgearSol = new Solenoid(Constants.PCM_CAN_ID, Constants.PCM_SLOT_LOWGEAR);
		clawSol = new Solenoid(Constants.PCM_CAN_ID, Constants.PCM_BOX_MANIPULATOR);
	}
	
	public void turnOn(){
		compressor.setClosedLoopControl(true);
	}
	
	public void turnOff(){
		compressor.setClosedLoopControl(false);
	}
	
	public void setLowGear(boolean value) {
		lowgearSol.set(value);
		
	}
	public void setHighGear(boolean value) {
		highgearSol.set(value);
	}
	
	
	public double getCurrent (){
		return compressor.getCompressorCurrent();
	}
	
	public void compressorRegulate () {
		SmartDashboard.putString("DB/String 4", ""+compressor.getPressureSwitchValue());
		/*if (compressor.getPressureSwitchValue()) {
			compressor.start();
		} else {
			compressor.stop();
		}*/
	}

	public void openManipulator() {
		clawSol.set(false);
	}
	
	public void closeManipulator() {
		clawSol.set(true);
	}
	
}
