package org.rest.library.lms.Services;


import java.sql.Connection;
import java.sql.DriverManager;


import org.rest.library.lms.FileManager.QueryConstants;

public class DataBaseServices {
	
	static DataBaseServices dataBaseServices=new DataBaseServices();
	public Connection connection  =  null;
	public static DataBaseServices getInstance()
	{
		return dataBaseServices;
	}
	
	private DataBaseServices()
	{
		
		try
		{
			Class.forName(QueryConstants.CLASS_FOR_NAME);
			connection=DriverManager.getConnection(QueryConstants.DB_CONNECTION_URL,QueryConstants.DB_CONNECTION_USERNAME,QueryConstants.DB_CONNECTION_PASSWORD);   
			System.out.println("connection is: "+ connection);
			
			System.out.println("connected to database successfullly");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();

		}
		
	}

}
