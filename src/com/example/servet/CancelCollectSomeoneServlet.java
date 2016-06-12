package com.example.servet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.example.dao.UserInfoDao;

public class CancelCollectSomeoneServlet extends HttpServlet{
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
			jsonObject.put("msg", "ȡ�ղ��û�ʧ��,����δ֪");
			pw.print(jsonObject);
			return;
		}
		uid = Integer.parseInt(uids);
		String wscsuids = req.getParameter("wscsuid");
		int wscsuid = 0;
		if(wscsuids == null){
			jsonObject.put("code", -1);
			jsonObject.put("msg", "ȡ���ղ��û�ʧ��,�ղ��û�idδ֪");
			pw.print(jsonObject);
			return;
		}
		wscsuid = Integer.valueOf(wscsuids);
		System.out.println(wscsuids+",�մ�����ֵ��"+wscsuid);
		boolean tag = UserInfoDao.cancelCollectPerson(uid, wscsuid);
		tag = UserInfoDao.cancelbeCollectPerson(wscsuid, uid);
		if(tag) {
			jsonObject.put("code", 1);
			jsonObject.put("msg", "ȡ���ղ��û��ɹ�");
			pw.print(jsonObject);
		} else {
			jsonObject.put("code", -1);
			jsonObject.put("msg", "ȡ���ղ��û�ʧ��,�����Ѿ�ȡ���ղ���");
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
