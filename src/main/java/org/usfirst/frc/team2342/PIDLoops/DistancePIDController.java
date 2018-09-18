package org.usfirst.frc.team2342.PIDLoops;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

// Distance pid controller
public class DistancePIDController implements PIDOutput, PIDSource {
	private static WPI_TalonSRX left;
	private static WPI_TalonSRX right;
	private static PIDController pc;
	static private PIDSourceType type = PIDSourceType.kDisplacement;
	static private double correction = 0.0d;
	
	public DistancePIDController(){
		
	}
	
	public void init(double p, double i, double d, double ff, WPI_TalonSRX talonMasterLeft, WPI_TalonSRX talonMasterRight){
		left = talonMasterLeft;
		right = talonMasterRight;
		pc = new PIDController(p, i, d, ff, this, this);
		pc.setOutputRange(-1.0, 1.0);
		pc.enable();
	}
	
	public static void setGoal(double distanceTicks){
		pc.setSetpoint(distanceTicks + getPositionAverage());
	}
	
	public static double getGoal(){
		return pc.getSetpoint();
	}
	
	public double getCorrection(){
		return correction;
	}
	
	private static double getPositionAverage(){
		return (left.getSelectedSensorPosition(0)+right.getSelectedSensorPosition(0))/2;
	}
	
	public static double getP(){
		return pc.getP();
	}
	
	public static double getI(){
		return pc.getI();
	}
	
	public static double getD(){
		return pc.getD();
	}
	
	public static double getFF(){
		return pc.getF();
	}
	
	public static void setP(double value){
		pc.setP(value);
	}
	
	public static void setI(double value){
		pc.setI(value);
	}
	
	public static void setD(double value){
		pc.setD(value);
	}
	
	public static void setF(double value){
		pc.setF(value);
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
