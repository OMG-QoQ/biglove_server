package com.example.servet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.example.dao.UserInfoDao;

public class IsCollectPersonServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		JSONObject jsonObject = new JSONObject();
		PrintWriter pw= resp.getWriter();
		int uid = 0;
		String uids= req.getParameter("uid");
		if(uids == null){
			jsonObject.put("code", -1);
			jsonObject.put("msg", "请求失败,参数未知");
			pw.print(jsonObject);
			return;
		}
		uid = Integer.parseInt(uids);
		String personID = req.getParameter("personID");
		if(personID == null){
			jsonObject.put("code", -1);
			jsonObject.put("msg", "该用户id未知，不能判断是否收藏");
			pw.print(jsonObject);
			return;
		}
		int who_id = Integer.valueOf(personID);
		System.out.println(personID+",刚传进来值后"+who_id);
		String str = UserInfoDao.getWscSuid(uid);
		if(str == null || str.isEmpty()){
			jsonObject.put("code", 0);
			jsonObject.put("msg", "你没有收藏该用户");
			pw.print(jsonObject);
			return;
		} else {
			boolean bo = false;
			String[] array = str.split(",");
			for (int i = 0; i < array.length; i++) {
				if (array[i].equals(String.valueOf(personID))) {
					jsonObject.put("code", 1);
					jsonObject.put("msg", "你已经收藏该用户");
					pw.print(jsonObject);
					pw.close();
					bo = true;
					return ;
				} else {
					bo = false;
				}
			}
			if(!bo) {
				jsonObject.put("code", 0);
				jsonObject.put("msg", "你没有收藏该用户");
				pw.print(jsonObject);
				pw.close();
			} 
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
