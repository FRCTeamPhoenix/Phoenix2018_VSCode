package org.usfirst.frc.team2342.robot.subsystems;

import org.usfirst.frc.team2342.robot.PCMHandler;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BoxManipulator extends Subsystem {
	public WPI_TalonSRX talonIntakeRight;
	public WPI_TalonSRX talonIntakeLeft;
	public WPI_TalonSRX talonTip;
	private PCMHandler pcm;
	Timer timer = new Timer();
	
	public static final int PULL = 0;
	public static final int PUSH = 1;
	
	private final int PidLoopIndex = 0;
	private final int PidTimeOutMs = 10;
	private final boolean SensorPhase = true;
	private final boolean InvertMotor = false;
	
	
	public BoxManipulator(WPI_TalonSRX talonIntakeRight, WPI_TalonSRX talonIntakeLeft, WPI_TalonSRX talonTip, PCMHandler pcm) {
		this.talonIntakeRight = talonIntakeRight;
		this.talonIntakeLeft = talonIntakeLeft;
		this.talonTip = talonTip;
		
		talonIntakeRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PidLoopIndex, PidTimeOutMs);
		talonIntakeRight.setSensorPhase(SensorPhase);
		talonIntakeRight.setInverted(InvertMotor);
		talonIntakeRight.configNominalOutputForward(0, PidTimeOutMs);
		talonIntakeRight.configNominalOutputReverse(0, PidTimeOutMs);
		talonIntakeRight.configPeakOutputForward(1, PidTimeOutMs);
		talonIntakeRight.configPeakOutputReverse(-1, PidTimeOutMs);
		talonIntakeRight.configAllowableClosedloopError(0, PidLoopIndex, PidTimeOutMs);
		
		talonIntakeRight.config_kF(PidLoopIndex, 0.0, PidTimeOutMs);
		talonIntakeRight.config_kP(PidLoopIndex, 0.1, PidTimeOutMs);
		talonIntakeRight.config_kI(PidLoopIndex, 0.0, PidTimeOutMs);
		talonIntakeRight.config_kD(PidLoopIndex, 0.0, PidTimeOutMs);
		
		talonIntakeRight.getSensorCollection().setQuadraturePosition(0, PidTimeOutMs);
		talonIntakeLeft.getSensorCollection().setQuadraturePosition(0, PidTimeOutMs);
		this.talonIntakeLeft.follow(this.talonIntakeRight);
		//Need equivalent for solenoids
		this.pcm = pcm;
		
	}
	
	public void initialize() {
		talonTip.set(ControlMode.PercentOutput, 0.1);
	}
	
	public void setTipToZero() {
		talonIntakeRight.set(ControlMode.Current, 0.0);
	}
	
	public void closeManipulator() {
		pcm.closeManipulator();
	}
	
	public void openManipulator() {
		pcm.openManipulator();
	}
	
	public void goToPosition(double position) {
		talonIntakeRight.set(ControlMode.Position, position);
	}
	
	public void pullBox() {
		talonIntakeRight.set(ControlMode.PercentOutput, -0.5);
		talonIntakeLeft.set(ControlMode.PercentOutput, 0.5);
		
	}
	
	public void pushBox() {
		pushBox(1);
	}
	
	public void pushBox(double power) {
		talonIntakeRight.set(ControlMode.PercentOutput, power);
		talonIntakeLeft.set(ControlMode.PercentOutput, -power);
	}
	
	public void outputToSmartDashboard() {
		SmartDashboard.putString("DB/String 0", "Motor Output: " + (talonIntakeRight.getMotorOutputPercent()*100) + "%");
		SmartDashboard.putString("DB/String 1", "Position: " + talonIntakeRight.getSelectedSensorPosition(0));
		//Need equivalent for solenoids
	}
	public boolean atUpperLimit() {
		return talonTip.getSensorCollection().isRevLimitSwitchClosed();
	}
	
	public void end(int t) {
		talonTip.set(ControlMode.PercentOutput, 0.4 * Math.exp(-t / 50));
	}

	public void stop() {
		talonIntakeRight.set(ControlMode.PercentOutput, 0.0);
		talonIntakeLeft.set(ControlMode.PercentOutput, 0.0);
	}

	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
}