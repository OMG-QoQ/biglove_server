package com.example.servet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.example.dao.UserInfoDao;

public class PersonColletcServlet extends HttpServlet{

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
			jsonObject.put("msg", "收藏失败,参数未知");
			pw.print(jsonObject);
			return;
		}
		uid = Integer.parseInt(uids);
		String wscsuids = req.getParameter("wscsuid");
		int wscsuid = 0;
		if(wscsuids == null){
			jsonObject.put("code", -1);
			jsonObject.put("msg", "收藏失败,收藏用户id未知");
			pw.print(jsonObject);
			return;
		}
		wscsuid = Integer.valueOf(wscsuids);
		System.out.println(wscsuids+",刚传进来值是"+wscsuid);
		boolean tag = UserInfoDao.collectUid(uid, wscsuid);
		tag =UserInfoDao.becollectUid(wscsuid, uid);
		if(tag) {
			jsonObject.put("code", 1);
			jsonObject.put("msg", "收藏成功");
			pw.print(jsonObject);
		} else {
			jsonObject.put("code", -1);
			jsonObject.put("msg", "收藏失败,可能已经收藏了");
			pw.print(jsonObject);
		}
		pw.close();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
