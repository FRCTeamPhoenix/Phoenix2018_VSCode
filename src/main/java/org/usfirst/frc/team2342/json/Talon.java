
package org.usfirst.frc.team2342.json;

public class Talon {
	public int deviceNumber = 0; 		// talon id
	public int mode = 0;				// switch modes between velocity and distance
	public boolean inverted = false;		// invert the talons
	public double maxForwardSpeed = 0.0d;  // max forward speed of the talon
	public double maxReverseSpeed = 0.0d;  // max reverse speed of the talon
	public PIDGains velocityGains = new PIDGains();  // velocity PID constants
	public PIDGains distanceGains = new PIDGains();  // distance PID constants
}
