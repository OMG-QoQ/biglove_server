package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserInfoDao {
	private static Connection conn = DB.getConnetion();

	public static boolean updateUserInfo(int uid, String uname,
			String usummary, double ulon, double ulat, String uphone,String address) {
		boolean flag = false;
		String sql = "update user set uname = ?,usummary = ?,ulon = ?,ulat = ?,uphone = ?,address = ? where uid = ? ";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, uname);
			ps.setString(2, usummary);
			ps.setDouble(3, ulon);
			ps.setDouble(4, ulat);
			ps.setString(5, uphone);
			ps.setString(6, address);
			ps.setInt(7, uid);
			flag = !ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	// 更新用户对应的活动id
	public static boolean updateUHdId(int uid, int uhdid) {
		boolean flag = false;
		String str = getUHdId(uid);
		String sql = "update user set uhdid = ? where uid = ? ";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			if (str == null) {
				str = "" + uhdid;
			} else {
				str = str + "," + uhdid;
			}
			ps.setString(1, str);
			ps.setInt(2, uid);
			flag = !ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;

	}

	// 用户收藏活动id
	public static boolean updateUScHdId(int uid, int hdid) {
		boolean flag = false;
		String str = getUscHdId(uid);
		if (str != null)
			if (str.length() == 0) {
				str = hdid + "";
				System.out.println("一个都没有");
			} else {
				String[] array = str.split(",");
				for (int i = 0; i < array.length; i++) {
					if (array[i].equals(String.valueOf(hdid))) {
						return false;
					}
				}
				str = str + "," + hdid;
				System.out.println("至少已经有一个了");
			}
		 else{
			str = hdid + "";
			System.out.println("一个都没有");
		 }

		String sql = "update user set uschdid = ? where uid = ? ";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, str);
			ps.setInt(2, uid);
			flag = !ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	// 用户取消收藏活动
	public static boolean cancelHD(int uid,int uschdid) {
		boolean flag = false;
		String str = getUscHdId(uid);
		System.out.println(str+"操作前的字符串是");
		String array[];
		if (str == null || str.isEmpty()) {
			return false;
		} else {
			array = str.split(",");
			System.out.println(array.length+","+array[0]);
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < array.length; i++) {
				list.add(array[i]);
			}
			System.out.println(str+"操作前的字符串是");
			System.out.println("操作之前的集合大小是"+list.size());
			String temp = "";
			// 移除相等的
			for (String string : list) {
				if (string.equals(uschdid + "")) {
					temp = uschdid + "";
				}
			}
			if(!temp.isEmpty()){
				list.remove(temp);
				System.out.println("执行删除这个元素的方法了");
			}
			
			System.out.println("操作后的集合大小是"+list.size());
			if (list.size() == 0) {
				str = "";
			} else if (list.size() == 1) {
				str = list.get(0);
			} else {
				str = "";
				for (int i = 0; i < list.size(); i++) {
					if (i == list.size() - 1) {
						str += list.get(i);
					} else {
						str += list.get(i) + ",";
					}
				}
				
			}
			System.out.println("最后的字符串是"+str);
			String sql = "update user set uschdid = ? where uid = ? ";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, str);
				ps.setInt(2, uid);
				flag = !ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return flag;
		}
	}

	// 更新谁收藏我的用户的id
	public static boolean updateScwUid(int scwUid, int uid) {
		boolean flag = false;
		String str = getSCWUid(uid);
		String sql = "update user set scwuid = ? where uid = ? ";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			if (str == null) {
				str = "" + scwUid;
			} else {
				str = str + "," + scwUid;
			}
			ps.setString(1, str);
			ps.setInt(2, uid);
			flag = !ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;

	}

	// 获得谁收藏我的所有用户id
	public static String getSCWUid(int uid) {
		String sql1 = "select scwuid from user where uid = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql1);
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();// 承接信息
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获得用户收藏的活动的id
	public static String getUscHdId(int uid) {
		String sql = "select uschdid from user where uid = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();// 承接信息
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获得用户活动的id
	public static String getUHdId(int uid) {
		String sql1 = "select uhdid from user where uid = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql1);
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();// 承接信息
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获得我收藏谁的uid
	public static String getWscSuid(int uid) {
		String sql1 = "select wscsuid from user where uid = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql1);
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();// 承接信息
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	// ***********************我收藏谁 收藏人与取消收藏人**************************

	// 收藏某人
	public static boolean collectUid(int uid, int personID) {
		boolean flag = false;
		String str = getWscSuid(uid);
		if (str != null)
			if (str.length() == 0) {
				str = personID + "";
				System.out.println("一个都没有");
			} else {
				String[] array = str.split(",");
				for (int i = 0; i < array.length; i++) {
					if (array[i].equals(String.valueOf(personID))) {
						return false;
					}
				}
				str = str + "," + personID;
				System.out.println("至少已经有一个了");
			}else{
				str = personID + "";
				System.out.println("一个都没有");	
			}

		String sql = "update user set wscsuid = ? where uid = ? ";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, str);
			ps.setInt(2, uid);
			flag = !ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	public static boolean becollectUid(int uid, int personID) {
		boolean flag = false;
		String str = getSCWUid(uid);
		if (str != null)
			if (str.length() == 0) {
				str = personID + "";
				System.out.println("一个都没有");
			} else {
				String[] array = str.split(",");
				for (int i = 0; i < array.length; i++) {
					if (array[i].equals(String.valueOf(personID))) {
						return false;
					}
				}
				str = str + "," + personID;
				System.out.println("至少已经有一个了");
			}else{
				str = personID + "";
				System.out.println("一个都没有");	
			}

		String sql = "update user set scwuid = ? where uid = ? ";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, str);
			ps.setInt(2, uid);
			flag = !ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	// 取消收藏某个人
	public static boolean cancelCollectPerson(int uid, int wscsuid) {
		boolean flag = false;
		String str = getWscSuid(uid);
		String array[];
		if (str == null || str.isEmpty()) {
			return false;
		} else {
			array = str.split(",");
			System.out.println(array.length+","+array[0]);
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < array.length; i++) {
				list.add(array[i]);
			}
			System.out.println("操作之前的集合大小是"+list.size());
			String temp = "";
			// 移除相等的
			for (String string : list) {
				if (string.equals(wscsuid + "")) {
					temp = wscsuid + "";
				}
			}
			if(!temp.isEmpty()){
				list.remove(temp);
				System.out.println("执行删除这个元素的方法了");
			}
			
			System.out.println("操作后的集合大小是"+list.size());
			if (list.size() == 0) {
				str = "";
			} else if (list.size() == 1) {
				str = list.get(0);
			} else {
				str = "";
				for (int i = 0; i < list.size(); i++) {
					if (i == list.size() - 1) {
						str += list.get(i);
					} else {
						str += list.get(i) + ",";
					}
				}
				
			}
			System.out.println("最后的字符串是"+str);
			String sql = "update user set wscsuid = ? where uid = ? ";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, str);
				ps.setInt(2, uid);
				flag = !ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return flag;
		}
	}
	
	public static boolean cancelbeCollectPerson(int uid, int wscsuid) {
		boolean flag = false;
		String str = getSCWUid(uid);
		String array[];
		if (str == null || str.isEmpty()) {
			return false;
		} else {
			array = str.split(",");
			System.out.println(array.length+","+array[0]);
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < array.length; i++) {
				list.add(array[i]);
			}
			System.out.println("操作之前的集合大小是"+list.size());
			String temp = "";
			// 移除相等的
			for (String string : list) {
				if (string.equals(wscsuid + "")) {
					temp = wscsuid + "";
				}
			}
			if(!temp.isEmpty()){
				list.remove(temp);
				System.out.println("执行删除这个元素的方法了");
			}
			
			System.out.println("操作后的集合大小是"+list.size());
			if (list.size() == 0) {
				str = "";
			} else if (list.size() == 1) {
				str = list.get(0);
			} else {
				str = "";
				for (int i = 0; i < list.size(); i++) {
					if (i == list.size() - 1) {
						str += list.get(i);
					} else {
						str += list.get(i) + ",";
					}
				}
				
			}
			System.out.println("最后的字符串是"+str);
			String sql = "update user set scwuid = ? where uid = ? ";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, str);
				ps.setInt(2, uid);
				flag = !ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return flag;
		}
	}

}
