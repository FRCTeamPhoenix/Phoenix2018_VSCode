package org.usfirst.frc.team2342.util;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team2342.robot.sensors.Gyro;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class GyroPIDLoop implements PIDSource, PIDOutput {

    private WPI_TalonSRX talon;
	private double Kp = 0.0d;
	private double Ki = 0.0d;
	private double Kd = 0.0d;
	private double Kff = 0.0d;
	public PIDController pc;
	private PIDSourceType type = PIDSourceType.kDisplacement;
	private double correction = 0.0d;
	
	public GyroPIDLoop(double p, double i, double d, double ff){
		this.Kp = p;
		this.Ki = i;
		this.Kd = d;
        this.Kff = ff;
		pc = new PIDController(this.Kp, this.Ki, this.Kd, this.Kff, this, this);
        pc.setOutputRange(-0.65, 0.65);
        //pc.setContinuous(true);
		pc.enable();
	}
	
	public void setGoal(double distanceTicks){
        pc.reset();
        pc.setSetpoint(-distanceTicks + getPositionAverage());
        pc.enable();
	}
	
	public double getGoal(){
		return pc.getSetpoint();
	}
	
	public double getCorrection(){
		return this.correction;
	}
	
	private double getPositionAverage(){
		return Gyro.angle();
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
    
    public void setPID(double p, double i, double d, double ff) {
        pc.setPID(p, i, d, ff);
    }

    public void setP(double p) {
        pc.setP(p);
    }

    public double getP() {
        return pc.getP();
    }

    public double getError() {
        return pc.getError();
    }

}