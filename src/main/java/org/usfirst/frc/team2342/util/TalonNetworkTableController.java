package org.usfirst.frc.team2342.util;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;

public class TalonNetworkTableController {
    public static void pushTalon(WPI_TalonSRX talon) {
        NetworkTable nt = NetworkTableInstance.getDefault().getTable(Constants.TALON_TABLE_LOCATION);
        NetworkTable talonTable = nt.getSubTable("" + talon.getDeviceID());
        NetworkTable pidTable = talonTable.getSubTable("pid:0");
        pidTable.getEntry("P").setNumber(talon.configGetParameter(ParamEnum.eProfileParamSlot_P, 0, 0));
        pidTable.getEntry("I").setNumber(talon.configGetParameter(ParamEnum.eProfileParamSlot_I, 0, 0));
        pidTable.getEntry("D").setNumber(talon.configGetParameter(ParamEnum.eProfileParamSlot_D, 0, 0));
        pidTable.getEntry("FF").setNumber(talon.configGetParameter(ParamEnum.eProfileParamSlot_F, 0, 0));
        pidTable.getEntry("IZone").setNumber(talon.configGetParameter(ParamEnum.eProfileParamSlot_IZone, 0, 0));

        pidTable.getEntry("P").addListener(event -> {
            talon.config_kP(0, event.value.getDouble(), 100);
            System.out.println("Talon (" + talon.getDeviceID() + ") P changed: " + event.value.getDouble());
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);

        pidTable.getEntry("I").addListener(event -> {
            talon.config_kI(0, event.value.getDouble(), 100);
            System.out.println("Talon (" + talon.getDeviceID() + ") I changed: " + event.value.getDouble());
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);

        pidTable.getEntry("D").addListener(event -> {
            talon.config_kD(0, event.value.getDouble(), 100);
            System.out.println("Talon (" + talon.getDeviceID() + ") D changed: " + event.value.getDouble());
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);

        pidTable.getEntry("FF").addListener(event -> {
            talon.config_kF(0, event.value.getDouble(), 100);
            System.out.println("Talon (" + talon.getDeviceID() + ") FF changed: " + event.value.getDouble());
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);

        pidTable.getEntry("IZone").addListener(event -> {
            talon.config_IntegralZone(0, Integer.valueOf((String) event.value.getValue()), 100);
            System.out.println("Talon (" + talon.getDeviceID() + ") I changed: " + event.value.getDouble());
        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);

    }
}