package org.usfirst.frc.team2342.util;

import com.ctre.phoenix.motion.TrajectoryPoint;

public class MotionProfileUtil {

    public static void generateTrapezoidalProfile(float cruiseVelocity, float accelTime, float cruiseTime, float decelTime, int timeMs, int timeStepMs) {
        for(int i=0;i<timeMs/timeStepMs;i++) {
            
        }
    }

}