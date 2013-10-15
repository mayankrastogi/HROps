package com.thebasilisks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
	private static DBConnection instance = null;
	
	private static String username=null;
	private static String host=null;
	private static String password=null;
	private static String port=null;
	private static String dbname=null;
	
	private DBConnection(Properties props)
	{
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		host=props.getProperty("host");
		username=props.getProperty("username");
		password=props.getProperty("password");
		port=props.getProperty("port");
		dbname=props.getProperty("dbname");
	}
	
	public static DBConnection getInstance(Properties props)
	{
		if(instance == null)
			instance =  new DBConnection(props);
		return instance;
	}

	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:db2://"+host+":"+port+"/"+dbname, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
}
