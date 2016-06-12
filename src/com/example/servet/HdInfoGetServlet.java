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

//  �����������
public class HdInfoGetServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		JSONObject jsonObject = new JSONObject();
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		String hdids=req.getParameter("hdid");
	    int hdid = 0;
	    if(hdids==null) {
	    	jsonObject.put("code", -1);
			jsonObject.put("msg", "��ȡ�����ʧ�ܣ�ԭ���ǻid����Ϊ��");
			PrintWriter pw = resp.getWriter();
			pw.print(jsonObject);
			pw.close();
	    	return ;
	    }
	    hdid = Integer.valueOf(hdids);
	    HdBean hb = HdDao.getHdInfo(hdid);
	    if(hb != null) {
			jsonObject.put("code", 1);
			jsonObject.put("msg", "������óɹ�");
			jsonObject.put("info", JSON.toJSON(hb));
		} else {
			jsonObject.put("code", -1);
			jsonObject.put("msg", "�Բ��𣬸û�ݲ�����");
		}
		PrintWriter pw = resp.getWriter();
		pw.print(jsonObject);
		pw.close();
		
	}
}
