package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.alibaba.fastjson.JSON;
import com.example.bean.UserBean;

public class RegisterDao {
	private static Connection conn = DB.getConnetion();

	public static void insert(String uemail, String pwd) {
		String sql = "insert into user(uid,uemail,pwd) values(?,?,?);";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, count() + 1);
			ps.setString(2, uemail);
			ps.setString(3, pwd);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static int count() {
		int n = 0;
		try {
			Statement st = conn.createStatement();
			String sql = "select count(_id) from user";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				n = rs.getInt(1);
				return n;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n;
	}

	public static Boolean isExitEmail(String email) {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select uemail from user");
			while (rs.next()) {
				String uemail = rs.getString(1);
				if (uemail.equals(email))
					return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	public static UserBean query(String uemail){
		UserBean user = null;
		try {
			String sql="select * from user where uemail = ?";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, uemail);
			ResultSet rs = ps.executeQuery();
		while(rs.next()){
			int uid=rs.getInt("uid");
			String email=rs.getString("uemail");
			String uname=rs.getString("uname");
			String pwd=rs.getString("pwd");
			String usummary=rs.getString("usummary");
			String uavatar=rs.getString("uavatar");
			double ulon=rs.getDouble("ulon");
			double ulat=rs.getDouble("ulat");
			String uphone=rs.getString("uphone");
			String  uhdid=rs.getString("uhdid");
			String uschdid=rs.getString("uschdid");
			String scwuid = rs.getString("scwuid");
			String wscsuid = rs.getString("wscsuid");
			String address = rs.getString("address");
			user=new UserBean(uid, email, uname, pwd, usummary,
					uavatar, ulon, ulat, uphone, uhdid, uschdid, scwuid,wscsuid,address);
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
		
	}
	
	
	
	public static UserBean queryByid(int uid1){
		UserBean user = null;
		try {
			String sql="select * from user where uid = ?";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1, uid1);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				int uid=rs.getInt("uid");
				String email=rs.getString("uemail");
				String uname=rs.getString("uname");
				String pwd=rs.getString("pwd");
				String usummary=rs.getString("usummary");
				String uavatar=rs.getString("uavatar");
				double ulon=rs.getDouble("ulon");
				double ulat=rs.getDouble("ulat");
				String uphone=rs.getString("uphone");
				String  uhdid=rs.getString("uhdid");
				String uschdid=rs.getString("uschdid");
				String scwuid = rs.getString("scwuid");
				String wscsuid = rs.getString("wscsuid");
				String address = rs.getString("address");
				user=new UserBean(uid, email, uname, pwd, usummary,
						uavatar, ulon, ulat, uphone, uhdid, uschdid, scwuid,wscsuid,address);
			}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return user;
		
	}
}
