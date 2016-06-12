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

public class RegisterServlet extends HttpServlet{

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
		String uemail = req.getParameter("uemail");
		String pwd = req.getParameter("pwd");
		boolean tag = RegisterDao.isExitEmail(uemail);
		
		PrintWriter pw = resp.getWriter();
		JSONObject jsonObject = new JSONObject();
		if(tag) {
			jsonObject.put("code", -1);
			jsonObject.put("msg", "∏√” œ‰“—æ≠±ª◊¢≤·");
		} else {
			RegisterDao.insert(uemail, pwd);
			if(RegisterDao.isExitEmail(uemail)){
				jsonObject.put("code", 1);
				jsonObject.put("msg", "πßœ≤ƒ„£¨◊¢≤·≥…π¶£°");
				UserBean user = RegisterDao.query(uemail);
				jsonObject.put("info", JSON.toJSON(user));
			} else {
				jsonObject.put("code", -1);
				jsonObject.put("msg", "◊¢≤· ß∞‹£¨«Î÷ÿ ‘£°");
			}
		}
		pw.print(jsonObject);
		pw.close();
		
		
	}
}
