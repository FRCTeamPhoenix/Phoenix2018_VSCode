package org.usfirst.frc.team2342.robot.sensors;

import java.util.TimerTask;
import org.usfirst.frc.team2342.util.Constants;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

public class RIOduino {
	private SerialPort port;
	private int distance;
	private int faverage;
	private int saverage;
	private int uDistance;
	private java.util.Timer updater;
	
	private int[] distances;
	
	public RIOduino(){
		distance = 1;
		port = new SerialPort(Constants.SERIAL_BAUD_RATE, Constants.LIDAR_PORT);
		updater = new java.util.Timer();
		distances = new int[Constants.SLOW_AVERAGE_SIZE];
		for(int i = 0; i < distances.length; i++){
			distances[i] = 0;
		}
		faverage = 1;
		saverage = 1;
		//start at average
		uDistance = 300;
	}

	private class SensorUpdater extends TimerTask {
        public void run() {
            update();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
	
	public double getDistanceIn(){
		//from cm to inches
		return distance * Constants.CM_TO_INCHES;
	}
	
	public int getDistanceCm(){
		return distance;
	}
	
	//fast average (smaller sample size)
	public double getFDistanceIn(){
		//from cm to inches
		return faverage * Constants.CM_TO_INCHES;
	}
	
	public int getFDistanceCm(){
		return faverage;
	}
	
	public int getUltrasonicDistanceMM(){
		return uDistance;
	}
	
	//slow average
	public double getSDistanceIn(){
		//from cm to inches
		return saverage * Constants.CM_TO_INCHES;
	}
	
	public int getSDistanceCm(){
		return saverage;
	}
	
	public void start(){
		updater.scheduleAtFixedRate(new SensorUpdater(), 0, 100);
	}
	
	private int[] shiftRight(int[] list, int arg_distance)
	{
	   if (list.length < 2) return list;

	   int last = list[list.length - 1];

	   for(int i = list.length - 1; i > 0; i--) {
	      list[i] = list[i - 1];
	   }
	   list[0] = arg_distance;
	   return list;
	}
	
	private void update(){
		port.writeString("H");
		Timer.delay(0.06);
		String recv = port.readString();
		System.out.println(recv+"r");
		
		//check that receive string is complete with s at start and newline at end
		int startChar = recv.indexOf('s');
		int endChar = -1;
		if(startChar != -1)
			endChar = recv.indexOf('\n',startChar);
		else
			return;
		if(startChar == -1 || endChar == -1 || startChar>endChar)
			return;
		
		String finalString = recv.substring(startChar+1, endChar-1);
		
		//split into readings of ultrasonic and lidar (in that order)
		String[] readings = recv.split("\\s+");
		if(readings.length == 2){
			String ultrasonic = readings[0];
			String lidar = readings[1];
			lidar = lidar.replaceAll("[^0-9.]", "");
			ultrasonic = ultrasonic.replaceAll("[^0-9.]", "");
			uDistance = Integer.parseInt(ultrasonic);
			distance = Integer.parseInt(lidar);
			distances = shiftRight(distances, distance);
			
			//calculate the averages
			int locfaverage = 0;
			for(int i = 0; i<Constants.FAST_AVERAGE_SIZE; i++){
				locfaverage += distances[i];
			}
			faverage = locfaverage / Constants.FAST_AVERAGE_SIZE;
			
			int locsaverage = 0;
			for(int i = 0; i<Constants.SLOW_AVERAGE_SIZE; i++){
				locsaverage += distances[i];
			}
			saverage = locsaverage / Constants.SLOW_AVERAGE_SIZE;
			
			Timer.delay(0.005);
		}
	}
	
	public void stop(){
		updater.cancel();
        updater = new java.util.Timer();
	}
}
