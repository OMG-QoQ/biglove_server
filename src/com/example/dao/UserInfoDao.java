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

	// �����û���Ӧ�Ļid
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

	// �û��ղػid
	public static boolean updateUScHdId(int uid, int hdid) {
		boolean flag = false;
		String str = getUscHdId(uid);
		if (str != null)
			if (str.length() == 0) {
				str = hdid + "";
				System.out.println("һ����û��");
			} else {
				String[] array = str.split(",");
				for (int i = 0; i < array.length; i++) {
					if (array[i].equals(String.valueOf(hdid))) {
						return false;
					}
				}
				str = str + "," + hdid;
				System.out.println("�����Ѿ���һ����");
			}
		 else{
			str = hdid + "";
			System.out.println("һ����û��");
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

	// �û�ȡ���ղػ
	public static boolean cancelHD(int uid,int uschdid) {
		boolean flag = false;
		String str = getUscHdId(uid);
		System.out.println(str+"����ǰ���ַ�����");
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
			System.out.println(str+"����ǰ���ַ�����");
			System.out.println("����֮ǰ�ļ��ϴ�С��"+list.size());
			String temp = "";
			// �Ƴ���ȵ�
			for (String string : list) {
				if (string.equals(uschdid + "")) {
					temp = uschdid + "";
				}
			}
			if(!temp.isEmpty()){
				list.remove(temp);
				System.out.println("ִ��ɾ�����Ԫ�صķ�����");
			}
			
			System.out.println("������ļ��ϴ�С��"+list.size());
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
			System.out.println("�����ַ�����"+str);
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

	// ����˭�ղ��ҵ��û���id
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

	// ���˭�ղ��ҵ������û�id
	public static String getSCWUid(int uid) {
		String sql1 = "select scwuid from user where uid = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql1);
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();// �н���Ϣ
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// ����û��ղصĻ��id
	public static String getUscHdId(int uid) {
		String sql = "select uschdid from user where uid = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();// �н���Ϣ
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// ����û����id
	public static String getUHdId(int uid) {
		String sql1 = "select uhdid from user where uid = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql1);
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();// �н���Ϣ
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// ������ղ�˭��uid
	public static String getWscSuid(int uid) {
		String sql1 = "select wscsuid from user where uid = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql1);
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();// �н���Ϣ
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	// ***********************���ղ�˭ �ղ�����ȡ���ղ���**************************

	// �ղ�ĳ��
	public static boolean collectUid(int uid, int personID) {
		boolean flag = false;
		String str = getWscSuid(uid);
		if (str != null)
			if (str.length() == 0) {
				str = personID + "";
				System.out.println("һ����û��");
			} else {
				String[] array = str.split(",");
				for (int i = 0; i < array.length; i++) {
					if (array[i].equals(String.valueOf(personID))) {
						return false;
					}
				}
				str = str + "," + personID;
				System.out.println("�����Ѿ���һ����");
			}else{
				str = personID + "";
				System.out.println("һ����û��");	
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
				System.out.println("һ����û��");
			} else {
				String[] array = str.split(",");
				for (int i = 0; i < array.length; i++) {
					if (array[i].equals(String.valueOf(personID))) {
						return false;
					}
				}
				str = str + "," + personID;
				System.out.println("�����Ѿ���һ����");
			}else{
				str = personID + "";
				System.out.println("һ����û��");	
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

	// ȡ���ղ�ĳ����
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
			System.out.println("����֮ǰ�ļ��ϴ�С��"+list.size());
			String temp = "";
			// �Ƴ���ȵ�
			for (String string : list) {
				if (string.equals(wscsuid + "")) {
					temp = wscsuid + "";
				}
			}
			if(!temp.isEmpty()){
				list.remove(temp);
				System.out.println("ִ��ɾ�����Ԫ�صķ�����");
			}
			
			System.out.println("������ļ��ϴ�С��"+list.size());
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
			System.out.println("�����ַ�����"+str);
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
			System.out.println("����֮ǰ�ļ��ϴ�С��"+list.size());
			String temp = "";
			// �Ƴ���ȵ�
			for (String string : list) {
				if (string.equals(wscsuid + "")) {
					temp = wscsuid + "";
				}
			}
			if(!temp.isEmpty()){
				list.remove(temp);
				System.out.println("ִ��ɾ�����Ԫ�صķ�����");
			}
			
			System.out.println("������ļ��ϴ�С��"+list.size());
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
			System.out.println("�����ַ�����"+str);
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
