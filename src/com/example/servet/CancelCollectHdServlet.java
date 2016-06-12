package com.example.servet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dao.HdDao;
import com.example.dao.UserInfoDao;

import net.sf.json.JSONObject;

public class CancelCollectHdServlet extends HttpServlet{
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
			jsonObject.put("msg", "ȡ�ղػʧ��,����δ֪");
			pw.print(jsonObject);
			return;
		}
		uid = Integer.parseInt(uids);
		String uschdids = req.getParameter("uschdid");
		int uschdid = 0;
		if(uschdids == null){
			jsonObject.put("code", -1);
			jsonObject.put("msg", "ȡ���ղػʧ��,�idδ֪");
			pw.print(jsonObject);
			return;
		}
		uschdid = Integer.valueOf(uschdids);
		System.out.println(uschdids+",�մ�����ֵ��"+uschdid);
		boolean tag = UserInfoDao.cancelHD(uid, uschdid);
		if(tag) {
			jsonObject.put("code", 1);
			jsonObject.put("msg", "ȡ���ղػ�ɹ�");
			pw.print(jsonObject);
		} else {
			jsonObject.put("code", -1);
			jsonObject.put("msg", "ȡ���ղػʧ��,�����Ѿ�ȡ���ղ���");
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
