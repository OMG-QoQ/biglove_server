package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {

	private static Connection conn = DB.getConnetion();
	public static boolean matchInfo(String email,String pwd) {
		String sql = "select pwd from user where uemail = ?";
		try {
			if(conn==null){
				System.out.println("conn是空的");
				return false;
			}
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();//承接信息
			if(rs.next()){
				return pwd.equals(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
