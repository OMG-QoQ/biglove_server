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

public class UserInfoGetServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		JSONObject jsonObject = new JSONObject();
		if(req.getParameter("uid")==null) {
			jsonObject.put("code", -1);
			jsonObject.put("msg", "参数确实，缺少用户id");
			PrintWriter pw = resp.getWriter();
			pw.print(jsonObject);
			return;
		}
		int uid = Integer.parseInt(req.getParameter("uid"));
		UserBean user = RegisterDao.queryByid(uid);
		if( user != null){
			jsonObject.put("code", 1);
			jsonObject.put("msg", "获取信息成功");
			jsonObject.put("info", JSON.toJSON(user));
		} else {
			jsonObject.put("code", -1);
			jsonObject.put("msg", "获取信息失败，未知原因");
		}
		PrintWriter pw = resp.getWriter();
		pw.print(jsonObject);
	}
}
