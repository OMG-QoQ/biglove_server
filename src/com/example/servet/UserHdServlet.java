package com.example.servet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.example.bean.HdBean;
import com.example.dao.HdDao;
import com.example.dao.UserInfoDao;

import net.sf.json.JSONObject;

// 获得用户的所有的活动详情
public class UserHdServlet extends HttpServlet{

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ArrayList<HdBean>  list = new ArrayList<HdBean>();
		JSONObject jsonObject = new JSONObject();
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		String uids=req.getParameter("uid");
	    int uid = 0;
	    if(uids==null) {
	    	jsonObject.put("code", -1);
			jsonObject.put("msg", "用户不存在");
			PrintWriter pw = resp.getWriter();
			pw.print(jsonObject);
			pw.close();
	    	return ;
	    }
	    uid = Integer.valueOf(uids);
		String hdidStr = UserInfoDao.getUHdId(uid);
		if(hdidStr == null||hdidStr.equals("")){
			jsonObject.put("code", -1);
			jsonObject.put("msg", "用户没有收藏任何活动");
			jsonObject.put("infoArray", JSON.toJSONString(list,true));
			PrintWriter pw = resp.getWriter();
			pw.print(jsonObject);
			pw.close();
	    	return ;
		}
		String hdids[]  = hdidStr.split(",");
		int hdid[] = new int[hdids.length];
		for(int i=0;i<hdids.length;i++)
		{
		    hdid[i]=Integer.valueOf(hdids[i]);
		}
		for(int i = 0 ;i < hdid.length ;i++) {      
			for(int j = i +1 ;j < hdid.length ;j ++) {     
				if(hdid[i] < hdid[j]) {       
					int temp = hdid[i];       
					hdid[i] = hdid[j];       
					hdid[j] = temp;      
				}
			}
		}
		for (int i = 0; i < hdids.length; i++) {
			int hdidq = 0;
		    if(hdids!=null) {
		    	hdidq = hdid[i];
		    	HdBean hb = HdDao.getHdInfo(hdidq);
		    	list.add(hb);
		    }
		}
		if(list!=null&& list.size()>0) {
			jsonObject.put("code", 1);
			jsonObject.put("msg", "用户活动获得成功");
			System.out.print("用戶獲得活動成功");
			jsonObject.put("infoArray", JSON.toJSONString(list,true));
		} else {
			jsonObject.put("code", -1);
			jsonObject.put("msg", "用户没有发布活动");
		}
		PrintWriter pw = resp.getWriter();
		pw.print(jsonObject);
		pw.close();
	}
}
