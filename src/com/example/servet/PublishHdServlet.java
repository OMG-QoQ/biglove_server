package com.example.servet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.example.bean.HdBean;
import com.example.dao.HdDao;

public class PublishHdServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		JSONObject jsonObject = new JSONObject();
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		
		String uids=req.getParameter("uid");
		String hdName=req.getParameter("hdname");
		
        if(hdName == null) {
        	jsonObject.put("code", -1);
			jsonObject.put("msg", "活动发布失败");
			PrintWriter pw = resp.getWriter();
			pw.print(jsonObject);
			return;
        }
		String startdate = req.getParameter("startdate");
		String enddate = req.getParameter("enddate");
		String content = req.getParameter("hdcontent");
		String lon = req.getParameter("hdlon");
		String address = req.getParameter("address");
		String creattime =req.getParameter("creattime");
		System.out.println("12345");
		
		double hdlon = 0;
		if(lon !=null){
			hdlon = Double.parseDouble(lon);
		}
		double hdlat= 0;
		String lat = req.getParameter("hdlat");
		if(lat!=null){
			hdlat = Double.parseDouble(lat);
		}
		if(uids==null) {
			jsonObject.put("code", -1);
			jsonObject.put("msg", "活动发布失败");
			PrintWriter pw = resp.getWriter();
			pw.print(jsonObject);
			return;
		}
		int uid = Integer.parseInt(uids);
		HdBean hd = HdDao.publishHd(uid, hdName, hdlon, hdlat, startdate, enddate, content,address,creattime);
		if(hd != null) {
			jsonObject.put("code", 1);
			jsonObject.put("msg", "活动发布成功");
			jsonObject.put("info", JSON.toJSON(hd));
		}
		PrintWriter pw = resp.getWriter();
		pw.print(jsonObject);
		
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
