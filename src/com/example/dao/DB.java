package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DB {
	public static Connection getConnetion() {
		Connection conn=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("connection-------");
			try {
				conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/biglovenew","root","bdpq25");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
		
	}

}
