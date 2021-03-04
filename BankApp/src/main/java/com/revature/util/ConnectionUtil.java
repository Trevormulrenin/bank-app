package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.postgresql.Driver;

public class ConnectionUtil {
	
	private static Logger log = Logger.getLogger(ConnectionUtil.class);
	
	public static Connection getConnection(){
		
		try {
			Driver driver = new Driver();
		
			DriverManager.registerDriver(driver);
		
			String url = System.getenv("db_url");
			String username = System.getenv("db_username");
			String password = System.getenv("db_password");
		
			Connection con = DriverManager.getConnection(url, username, password);
		
			return con;
		} catch (SQLException e) {
			log.error("Applcation unable to establish connection! Exception message is: " + e.getMessage());
			return null;
		}
	}
}
