package com.example.servet;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.example.bean.UserBean;
import com.example.dao.RegisterDao;
import com.example.dao.UserInfoDao;

public class UserInfoUpdateServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		req.setCharacterEncoding("utf-8");
		JSONObject jsonObject = new JSONObject();
		if(req.getParameter("uid")==null) {
			jsonObject.put("code", -1);
			jsonObject.put("msg", "基本资料修改失败");
			PrintWriter pw = resp.getWriter();
			pw.print(jsonObject);
			return;
		}
		int uid = Integer.parseInt(req.getParameter("uid"));
		String uname = req.getParameter("uname");
		String usummary = req.getParameter("usummary");
		String ulons = req.getParameter("ulon");
		double ulon = 0;
		if(ulons!=null){
			
			 ulon = Double.parseDouble(ulons);
		}
		double ulat= 0;
		String ulats = req.getParameter("ulat");
		if(ulons!=null){
			 ulat = Double.parseDouble(ulats);
		}
		String uphone = req.getParameter("uphone");
		String address = req.getParameter("address");
		boolean tag = UserInfoDao.updateUserInfo(uid, uname, usummary, ulon, ulat, uphone,address);
		System.out.println(usummary.toString());
		if(tag){
			jsonObject.put("code", 1);
			jsonObject.put("msg", "基本资料修改成功");
			UserBean user = RegisterDao.queryByid(uid);
			jsonObject.put("info", JSON.toJSON(user));
		} else {
			jsonObject.put("code", -1);
			jsonObject.put("msg", "基本资料修改失败");
		}
		PrintWriter pw = resp.getWriter();
		pw.print(jsonObject);
	}
}
