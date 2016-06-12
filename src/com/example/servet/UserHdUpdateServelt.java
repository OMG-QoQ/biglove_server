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

//   用户修改活动详情接口
public class UserHdUpdateServelt extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		JSONObject jsonObject = new JSONObject();
		if (req.getParameter("hdid") == null) {
			jsonObject.put("code", -1);
			jsonObject.put("msg", "参数错误");
			PrintWriter pw = resp.getWriter();
			pw.print(jsonObject);
			pw.close();
			return;
		}
		int hdid = Integer.parseInt(req.getParameter("hdid"));
		String hdname = req.getParameter("hdname");
		String hdcontent = req.getParameter("hdcontent");
		String hdlons = req.getParameter("hdlon");
		double hdlon = 0;
		if (hdlons != null) {

			hdlon = Double.parseDouble(hdlons);
		}
		double hdlat = 0;
		String hdlats = req.getParameter("hdlat");
		if (hdlats != null) {
			hdlat = Double.parseDouble(hdlats);
		}
		String startdate = req.getParameter("startdate");
		String enddate = req.getParameter("enddate");
		
		String address = req.getParameter("address");
		
		boolean tag = HdDao.updateHdInfo(hdid, hdname, hdlon, hdlat, startdate,
				enddate, hdcontent,address);
		if (tag) {
			jsonObject.put("code", 1);
			jsonObject.put("msg", "活动基本资料修改成功");
			HdBean hb = HdDao.getHdInfo(hdid);
			jsonObject.put("info", JSON.toJSON(hb));
		} else {
			jsonObject.put("code", -1);
			jsonObject.put("msg", "活动基本资料修改失败");
		}
		PrintWriter pw = resp.getWriter();
		pw.print(jsonObject);
		pw.close();
	}
}
