package org.usfirst.frc.team2342.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class Camera{
	
	private UsbCamera camera;
    
	//private CvSink cvSink;
	
	public Camera(int resolutionX, int resolutionY, int cameraIndex, int fps, CameraServer server){		
		camera = server.startAutomaticCapture(cameraIndex);
		camera.setResolution(resolutionX, resolutionY);
		camera.setFPS(fps);
		camera.setExposureAuto();
	}
	
	
}