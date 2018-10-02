package org.usfirst.frc.team2342.robot.subsystems;

import org.usfirst.frc.team2342.util.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CascadeElevator extends Subsystem {
	public WPI_TalonSRX talonCascade;

	public static final int BASE = 0;
	public static final int SWITCH = 1;
	public static final int SCALE = 2;
	public static final int TOP = 3;

	private final int PidLoopIndex = 0;
	private final int PidTimeOutMs = 10;
	private final boolean SensorPhase = false;
	private final boolean InvertMotor = false;
	public boolean runningPreset = false;

	public DigitalInput lowerLimit;
	public DigitalInput upperLimit;
	
	public double lastPosition = 0;

	private Joystick xbox;
	
	public CascadeElevator(WPI_TalonSRX talonCascade, Joystick xbox) {
		this.talonCascade = talonCascade;
		this.xbox = xbox;
		this.talonCascade.setInverted(false);
		talonCascade.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PidLoopIndex, PidTimeOutMs);
		talonCascade.setSensorPhase(SensorPhase);
		talonCascade.setInverted(InvertMotor);
		talonCascade.configNominalOutputForward(0, PidTimeOutMs);
		talonCascade.configNominalOutputReverse(0, PidTimeOutMs);
		talonCascade.configPeakOutputForward(1, PidTimeOutMs);
		talonCascade.configPeakOutputReverse(-1, PidTimeOutMs);
		talonCascade.configAllowableClosedloopError(0, PidLoopIndex, PidTimeOutMs);

		talonCascade.config_kF(0, 1, PidTimeOutMs);
		talonCascade.config_kP(0, 0.4, PidTimeOutMs);
		talonCascade.config_kI(0, 0.0001, PidTimeOutMs);
		talonCascade.config_kD(0, 0, PidTimeOutMs);
		
		talonCascade.config_kF(1, 0, 10);
		talonCascade.config_kP(1, 1, 10);
		talonCascade.config_kI(1, 0, 10);
		talonCascade.config_kD(1, 0, 10);
		
		
		talonCascade.getSensorCollection().setQuadraturePosition(0, PidTimeOutMs);

		lowerLimit = new DigitalInput(Constants.LOWER_LIMIT_SWITCH);
		upperLimit = new DigitalInput(Constants.UPPER_LIMIT_SWITCH);
	}

	public void goToPosition(double position) {
		double speed;
		
		if (talonCascade.getSelectedSensorPosition(PidLoopIndex) < -position) {
			speed = 1200; //down speed POSITIVE
		} else
			speed = -3200; //up speed NEGATIVE
		
		if(Math.abs(talonCascade.getSelectedSensorPosition(PidLoopIndex) + position) < 2000)
			speed /= 2;

		setVelocity(speed);
	}

	public void holdPosition() {
		talonCascade.set(ControlMode.Position, lastPosition);
	}

	public void goToBase() {
		goToPosition(BASE);
	}

	public void goToSwitch() {
		goToPosition(SWITCH);
	}

	public void goToScale() {
		goToPosition(SCALE);
	}

	public void goToTop() {
		goToPosition(TOP);
	}

	public void setVelocity(double speed) {

		try {
			if(!xbox.getRawButton(10)){
				if (talonCascade.getSelectedSensorPosition(PidLoopIndex) < Constants.UPPER_SENSOR_POSITION) {
					//System.out.println("ABOVE");
					speed = Math.max(speed, 0);	
				} 
				
				if (talonCascade.getSelectedSensorPosition(PidLoopIndex) > Constants.LOWER_SENSOR_POSITION) {
					//System.out.println("BELOW");
					speed = Math.min(speed, 0);
				}
			
				if (lowerLimit.get()) { // switches are NC so true if tripped
					//System.out.println("LOWER LIMIT REACHED");
					speed = Math.min(speed, 0);
					if(Math.abs(talonCascade.getSelectedSensorPosition(0)) > 50)
						talonCascade.setSelectedSensorPosition(Constants.LOWER_SENSOR_POSITION, PidLoopIndex, PidTimeOutMs);
				}
	
				if (upperLimit.get()) {
					//System.out.println("UPPER LIMIT REACHED");
					speed = Math.max(speed, 0);
					// talonCascade.setSelectedSensorPosition(Constants.UPPER_SENSOR_POSITION,
					// PidLoopIndex, PidTimeOutMs);
				}
			}
			talonCascade.selectProfileSlot(0, 0);
			/*System.out.println("preset: " + runningPreset + 
					" position: " + talonCascade.getSelectedSensorPosition(PidLoopIndex) + 
					" speed: " + speed + " llim: " + lowerLimit.get() + " ulim: " + upperLimit.get());
*/
		} catch (Exception e) {

		}

		//System.out.println("going velocity " + speed);
		talonCascade.set(ControlMode.Velocity, speed);

	}

	public void outputToSmartDashboard() {
		SmartDashboard.putString("DB/String 0", "Motor Output: " + (talonCascade.getMotorOutputPercent() * 100) + "%");
		SmartDashboard.putString("DB/String 1", "Position: " + talonCascade.getSelectedSensorPosition(0));
	}

	public void stop() {
		talonCascade.set(ControlMode.Current, 0.0);
	}

	public void zeroSensors() {
		talonCascade.setSelectedSensorPosition(Constants.LOWER_SENSOR_POSITION, PidLoopIndex, PidTimeOutMs);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

}