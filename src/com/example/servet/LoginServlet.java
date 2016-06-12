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
import com.example.dao.LoginDao;
import com.example.dao.RegisterDao;

public class LoginServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		req.setCharacterEncoding("utf-8");
		String uemail=req.getParameter("uemail");//�û���
		String pwd=req.getParameter("pwd");//����
		System.out.println(uemail + "^^^^^^^^" +pwd);
		Boolean  isSuccess = LoginDao.matchInfo(uemail, pwd);
		String message ="��¼ʧ�ܣ��û������������";
		
		PrintWriter pw = resp.getWriter();
		JSONObject jsonObj = new JSONObject();
		if(isSuccess) {
			jsonObj.put("code", 1);
			jsonObj.put("msg", "��¼�ɹ�");
			UserBean user = RegisterDao.query(uemail);
			jsonObj.put("info", JSON.toJSON(user));
		} else {
			jsonObj.put("code", -1);
			jsonObj.put("msg", message);
		}
		
		pw.print(jsonObj);
		pw.close();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	
}
