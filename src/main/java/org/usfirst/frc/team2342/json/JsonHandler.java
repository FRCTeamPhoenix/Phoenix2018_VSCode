package org.usfirst.frc.team2342.json;

import java.io.File;

import java.io.IOException;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

//*********************************************************************************************
// The JSONHandler class essentially handles the JSON file. It initializes a new file to hold *
// JSON values, and does other basic methods onto the file, such as writing to, or reading    *
// from it.                                                                                   *
// ********************************************************************************************
public class JsonHandler 
{
	private static String m_fname;
	private static File m_fileHandler;
	private static ObjectMapper m_mapper = new ObjectMapper();
	private static boolean m_fExists;

	//****************************************************************************************
	// This is the constructor. It initializes the file, makes sure the objects in the       *
	// JSON are OK, and checks if file exists.                                               *
	//****************************************************************************************
	
	public static void init(String fname) {
		m_mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		m_fname = fname;
		m_fileHandler = new File(m_fname);
		m_fExists = m_fileHandler.exists();

		if (m_fExists == true) 
		{
			System.out.println("File exists, we are good to go.");
		}
		else 
		{
			System.out.println("This file does not exists.");
			System.out.println("If you want to next time use the write function to create a json file.");
		}
	}


	//***********************************************************************
	// This is the getFname method. It returns the name of the file.        *
	//***********************************************************************
	public static String getFname() 
	{
		return m_fname;
	}


	//***********************************************************************
	// This is the setFname method. It returns the name of the file.        *
	//***********************************************************************
	public static void setFname(String name) 
	{
		m_fname = name;
		m_fileHandler = new File(name);
		m_fExists = m_fileHandler.exists();
	}
	
	public static boolean getFileExisting() {
		return m_fExists;
	}

	//************************************************************************************
	// This is the write method. It essentially writes values to the file                *
	//************************************************************************************
	public static <T> void write(T t) throws JsonGenerationException, JsonMappingException, IOException 
	{
		m_fExists = m_fileHandler.exists();
		m_mapper.writeValue(m_fileHandler, t);
	}


	//**************************************************************************************
	// This is the read method. It essentially reads values to the file                    *
	// WARNING: When you try to read values off of the file you must pass in a Class Type  *
	//**************************************************************************************
	public static <T> T read(T pass) throws JsonGenerationException, JsonMappingException, IOException 
	{
		//JavaType type = mapper.getTypeFactory().constructParametricType(Data.class, pass.class);
		return (T) m_mapper.readValue(m_fileHandler, pass.getClass());
	}
	
	// Get the json file and load the contents into the object
	// If Json file does not exists, write the object into a json file.
	public static <T> void readJson(String fname, T variable) {
		setFname("/home/lvuser/" + fname);
		try {
			if (getFileExisting()) {
				variable = read(variable);
			}
			else {
				write(variable);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// updates the object into the json file
	// WARNING: IT WRITES VALUES TO JSON NOT READS VALUES TO JSON.
	public static <T> void writeJson(String fname, T variable) {
		setFname("/home/lvuser/" + fname);
		try {
			write(variable);
		}
		catch (Exception e) {
			//DONOTHING
		}
	}
		
	//**************************************************************************************
	// This is the read method. It essentially reads values to the file                    *
	// WARNING: When you try to read values off of the file you must pass in a Class Type  *
	//**************************************************************************************
	public <T> T read(Class<T> pass) throws JsonGenerationException, JsonMappingException, IOException 
	{
		return m_mapper.readValue(m_fileHandler, pass);

	}
}