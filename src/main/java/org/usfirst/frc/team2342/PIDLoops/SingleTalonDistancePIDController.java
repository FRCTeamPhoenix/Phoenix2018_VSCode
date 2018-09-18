package org.usfirst.frc.team2342.PIDLoops;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class SingleTalonDistancePIDController implements PIDOutput, PIDSource {
	private WPI_TalonSRX talon;
	private double Kp = 0.0d;
	private double Ki = 0.0d;
	private double Kd = 0.0d;
	private double Kff = 0.0d;
	private PIDController pc;
	private PIDSourceType type = PIDSourceType.kDisplacement;
	private double correction = 0.0d;
	
	public SingleTalonDistancePIDController(double p, double i, double d, double ff, WPI_TalonSRX talon){
		this.talon= talon;
		this.Kp = p;
		this.Ki = i;
		this.Kd = d;
		this.Kff = ff;
		pc = new PIDController(this.Kp, 0.0d, 0.0d, 0.0d, this, this);
		pc.setOutputRange(-1.0, 1.0);
		pc.enable();
	}
	
	public void setGoal(double distanceTicks){
		pc.setSetpoint(-distanceTicks + getPositionAverage());
	}
	
	public double getGoal(){
		return pc.getSetpoint();
	}
	
	public double getCorrection(){
		return this.correction;
	}
	
	private double getPositionAverage(){
		return talon.getSelectedSensorPosition(0);
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		type = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return type;
	}

	@Override
	public double pidGet() {
		return getPositionAverage();
	}

	@Override
	public void pidWrite(double output) {
		this.correction = output;
	}

}
