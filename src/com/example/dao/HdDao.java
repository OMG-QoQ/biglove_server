package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.bean.HdBean;

public class HdDao {
	private static Connection conn = DB.getConnetion();

	// 发布活动
	public static HdBean publishHd(int uid, String hdName, double hdlon,
			double hdlat, String startdate, String enddate, String content,String address,String creattime) {
		String sql = "insert into activity(hdid,hdname,hdlon,hdlat,startdate,enddate,hdcontent,hdsuid,address,creattime) values(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = null;
		int hdid = count() + 1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, hdid);
			ps.setString(2, hdName);
			ps.setDouble(3, hdlon);
			ps.setDouble(4, hdlat);
			ps.setString(5, startdate);
			ps.setString(6, enddate);
			ps.setString(7, content);
			ps.setInt(8, uid);
			ps.setString(9, address);
			ps.setString(10, creattime);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		UserInfoDao.updateUHdId(uid, hdid);

		return getHdInfo(hdid);

	}

	/**
	 * 获得用户没有收藏的 活动id
	 * 
	 * @param hdidStr
	 * @return
	 */
	public static String getAvaiableHdid(int uid, String hdidString) {
		if (hdidString.isEmpty()) {
			return null;
		}
		String hdids[] = hdidString.split(",");
		int hidsTemp[] = new int[hdids.length];
		String set = "";
		for (int i = 0; i < hdids.length; i++) {
			hidsTemp[i] = Integer.parseInt(hdids[i]);
			if (i == hdids.length - 1) {
				set += Integer.parseInt(hdids[i]);
			} else {
				set += Integer.parseInt(hdids[i]) + ",";
			}
		}

		String hdidResult = "";
		try {
			String sql = "select hdid from activity where hdid not in ( +"
					+ set + ")" + "and hdsuid != ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int hdid = rs.getInt("hdid");
				if (rs.isLast()) {
					hdidResult += hdid;
				} else {
					hdidResult += hdid + ",";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("用户为收藏id" + hdidResult);
		return hdidResult;

	}

	public static HdBean getHdInfo(int hdid1) {
		HdBean hd = null;
		try {
			String sql = "select * from activity where hdid = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, hdid1);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int hdid = rs.getInt("hdid");
				String hdname = rs.getString("hdname");
				double hdlon = rs.getDouble("hdlon");
				double hdlat = rs.getDouble("hdlat");
				String startdate = rs.getString("startdate");
				String enddate = rs.getString("enddate");
				String hdImg = rs.getString("hdImg");
				String hdcontent = rs.getString("hdcontent");
				int hdsuid = rs.getInt("hdsuid");
				String address = rs.getString("address");
				String creattime = rs.getString("creattime");
				hd = new HdBean(hdid, hdname, hdlon, hdlat, startdate, enddate,
						hdImg, hdcontent, hdsuid,address,creattime);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hd;

	}

	// 点击收藏活动
	public static boolean collectHd(int uid, int hdid) {
		return UserInfoDao.updateUScHdId(uid, hdid);

	}

	// 修改活动详情基本资料
	public static boolean updateHdInfo(int hdid, String hdname, double hdlon,
			double hdlat, String startdate, String enddate, String hdcontent,String address) {
		boolean flag = false;
		String sql = "update activity set hdname = ?,hdlon = ?,hdlat = ?,startdate = ?,enddate = ?,hdcontent = ?,address = ? where hdid = ? ";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, hdname);
			ps.setDouble(2, hdlon);
			ps.setDouble(3, hdlat);
			ps.setString(4, startdate);
			ps.setString(5, enddate);
			ps.setString(6, hdcontent);
			ps.setString(7, address);
			ps.setInt(8, hdid);
			flag = !ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	// 修改活动详情基本资料
		public static boolean updateHdImage(int hdid, String hdimg_url) {
			boolean flag = false;
			String sql = "update activity set hdImg = ? where hdid = ? ";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, hdimg_url);
				ps.setInt(2, hdid);
				flag = !ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return flag;
		}

		
		public static boolean updateUserImage(int uid, String hdimg_url) {
			boolean flag = false;
			String sql = "update user set uavatar = ? where uid = ? ";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, hdimg_url);
				ps.setInt(2, uid);
				flag = !ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return flag;
		}


	public static int count() {
		int n = 0;
		try {
			Statement st = conn.createStatement();
			String sql = "select count(_id) from activity";
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

}
