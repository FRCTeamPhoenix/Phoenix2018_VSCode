package org.usfirst.frc.team2342.util;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;

@SuppressWarnings("deprecation")
public class NetworkTableInterface {
	
	//use slashes to separate subtables sort of like a file system
	
	public static void setValue(String subtablePath, String varName, double value){
		String[] tables = splitString(subtablePath);
		NetworkTable table = NetworkTable.getTable(tables[0]);
		ITable subtable = null;
		if(tables.length > 1)
			subtable = table.getSubTable(tables[1]);
		for(int i = 2; i < tables.length; i++){
			subtable = subtable.getSubTable(tables[i]);
		}
		if(tables.length == 1){
			table.putDouble(varName, value);
		}else{
			subtable.putDouble(varName, value);
		}
	}
	
	public static void setValue(String subtablePath, String varName, String value){
		String[] tables = splitString(subtablePath);
		NetworkTable table = NetworkTable.getTable(tables[0]);
		ITable subtable = null;
		if(tables.length > 1)
			subtable = table.getSubTable(tables[1]);
		for(int i = 2; i < tables.length; i++){
			subtable = subtable.getSubTable(tables[i]);
		}
		if(tables.length == 1){
			table.putString(varName, value);
		}else{
			subtable.putString(varName, value);
		}
	}
	
	public static void setValue(String subtablePath, String varName, Boolean value){
		String[] tables = splitString(subtablePath);
		NetworkTable table = NetworkTable.getTable(tables[0]);
		ITable subtable = null;
		if(tables.length > 1)
			subtable = table.getSubTable(tables[1]);
		for(int i = 2; i < tables.length; i++){
			subtable = subtable.getSubTable(tables[i]);
		}
		if(tables.length == 1){
			table.putBoolean(varName, value);
		}else{
			subtable.putBoolean(varName, value);
		}
	}
	

	public static double getDouble(String subtablePath, String varName){
		String[] tables = splitString(subtablePath);
		NetworkTable table = NetworkTable.getTable(tables[0]);
		ITable subtable = null;
		if(tables.length > 1)
			subtable = table.getSubTable(tables[1]);
		for(int i = 2; i < tables.length; i++){
			subtable = subtable.getSubTable(tables[i]);
		}
		if(tables.length == 1){
			return table.getDouble(varName, 0.0);
		}else{
			return subtable.getDouble(varName, 0.0);
		}
	}
	
	public static String getString(String subtablePath, String varName){
		String[] tables = splitString(subtablePath);
		NetworkTable table = NetworkTable.getTable(tables[0]);
		ITable subtable = null;
		if(tables.length > 1)
			subtable = table.getSubTable(tables[1]);
		for(int i = 2; i < tables.length; i++){
			subtable = subtable.getSubTable(tables[i]);
		}
		if(tables.length == 1){
			return table.getString(varName, "NULL");
		}else{
			return subtable.getString(varName, "NULL");
		}
	}
	
	public static Boolean getBoolean(String subtablePath, String varName){
		String[] tables = splitString(subtablePath);
		NetworkTable table = NetworkTable.getTable(tables[0]);
		ITable subtable = null;
		if(tables.length > 1)
			subtable = table.getSubTable(tables[1]);
		for(int i = 2; i < tables.length; i++){
			subtable = subtable.getSubTable(tables[i]);
		}
		if(tables.length == 1){
			return table.getBoolean(varName, false);
		}else{
			return subtable.getBoolean(varName, false);
		}

	}
	
	private static String[] splitString(String target){
		String[] tables = {"one"};
		if(target.contains("/")){
			tables = target.split("/");
		}else if(target.contains("\\")){
			//have to use four slashes for some reason
			tables = target.split("\\\\");
		}else{
			tables[0] = target;
		}
		return tables;
	}
	
}
