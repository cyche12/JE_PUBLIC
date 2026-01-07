package com.algonquin.cst8288.assignment2.database;

import java.sql.Connection;

public class DBConnection {
	
	private static Connection connection = null;
	
	private String serverUrl = "jdbc:mysql://localhost:3306/bookvault";
	private String userString = "***";
	private String passwordString = "***";
	private String driverString = "com.mysql.cj.jdbc.Driver";
	
}
