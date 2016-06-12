package com.example.servet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.example.dao.UserInfoDao;

public class IsCollectHdServlet extends HttpServlet{

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
			jsonObject.put("msg", "����ʧ��,����δ֪");
			pw.print(jsonObject);
			return;
		}
		uid = Integer.parseInt(uids);
		String hdID = req.getParameter("hdID");
		System.out.println("Ҫ�ղػ��id" + hdID);
		if(hdID == null){
			jsonObject.put("code", -1);
			jsonObject.put("msg", "�ûidδ֪�������ж��Ƿ��ղ�");
			pw.print(jsonObject);
			return;
		}
		int who_id = Integer.valueOf(hdID);
		System.out.println(hdID+",�մ�����ֵ��"+who_id);
		String str = UserInfoDao.getUscHdId(uid);
		if(str == null || str.isEmpty()){
			jsonObject.put("code", 0);
			jsonObject.put("msg", "��û���ղظû");
			pw.print(jsonObject);
			return;
		} else {
			boolean bo = false;
			String[] array = str.split(",");
			for (int i = 0; i < array.length; i++) {
				if (array[i].equals(String.valueOf(hdID))) {
					jsonObject.put("code", 1);
					jsonObject.put("msg", "���Ѿ��ղظû");
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
				jsonObject.put("msg", "��û���ղظû");
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
