package org.usfirst.frc.team2342.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.UsbCameraInfo;
import edu.wpi.first.wpilibj.CameraServer;


public class CameraControl{
	
	private Camera[] cameras;
	
	UsbCameraInfo[] cameraInfo;
	CameraServer cameraServer;

	
	//Initialize all the cameras
	public CameraControl(int resolutionX, int resolutionY, int fps){
		
		cameraServer = CameraServer.getInstance();
		cameraInfo = UsbCamera.enumerateUsbCameras();
		cameras = new Camera[cameraInfo.length];
		for (int i = 0; i < cameras.length; i++) {
			cameras[i] = new Camera(resolutionX, resolutionY, i, fps, cameraServer);
		}
		
	}
}


