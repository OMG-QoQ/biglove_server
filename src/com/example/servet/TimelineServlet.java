package com.example.servet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.example.bean.HdBean;
import com.example.bean.UserBean;
import com.example.bean.UserHdBean;
import com.example.dao.HdDao;
import com.example.dao.RegisterDao;
import com.example.dao.UserInfoDao;

public class TimelineServlet extends HttpServlet{

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ArrayList<UserHdBean>  list = new ArrayList<UserHdBean>();
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
		String wscsuidStr = UserInfoDao.getWscSuid(uid);
		if(wscsuidStr == null||wscsuidStr.equals("")){
			jsonObject.put("code", -1);
			jsonObject.put("msg", "沒有關注任何用户");
			jsonObject.put("infoArray", JSON.toJSONString(list,true));
			PrintWriter pw = resp.getWriter();
			pw.print(jsonObject);
			pw.close();
	    	return ;
		}
		String wscsuids[]  = wscsuidStr.split(",");
		String uidhdStr="";
		//String uidhds[] =uidhdStr.split(",");
		
		for (int i = 0; i < wscsuids.length; i++) {
			if(i == (wscsuids.length-1)){
			 uidhdStr = uidhdStr+UserInfoDao.getUHdId(Integer.valueOf(wscsuids[i]));
			}else{
			 uidhdStr = uidhdStr+UserInfoDao.getUHdId(Integer.valueOf(wscsuids[i]))+",";
			}
		}
		
		if(uidhdStr == null||uidhdStr.equals("")){
			jsonObject.put("code", -1);
			jsonObject.put("msg", "關注用户没有發布任何活动");
			jsonObject.put("infoArray", JSON.toJSONString(list,true));
			PrintWriter pw = resp.getWriter();
			pw.print(jsonObject);
			pw.close();
	    	return ;
		}
		
		String uidhds[] =uidhdStr.split(",");
		int hdid[] = new int[uidhds.length];
		for(int i=0;i<uidhds.length;i++)
		{
		    hdid[i]=Integer.valueOf(uidhds[i]);
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
		

		
		for (int i = 0; i < hdid.length; i++) {
			int hdidq = 0;
		    if(hdid!=null) {
		    	hdidq = Integer.valueOf(hdid[i]);
		    	HdBean hb = HdDao.getHdInfo(hdidq);
		    	UserBean user = RegisterDao.queryByid( hb.getHdsuid());
		        UserHdBean  uh= new UserHdBean(user.getUname(), user.getUavatar(),
		        		hb.getHdid(), hb.getHdname(), hb.getHdlon(), hb.getHdlat(), hb.getStartdate(), 
		        		hb.getEnddate(), hb.getHdImg(), hb.getHdcontent(), hb.getHdsuid(),hb.getAddress(),hb.getCreattime());
		    	list.add(uh);
		    }
		}
		
		if(list!=null&& list.size()>0) {
			jsonObject.put("code", 1);
			jsonObject.put("msg", "获得收藏活动的信息成功");
			jsonObject.put("infoArray", JSON.toJSONString(list,true));
		} else {
			jsonObject.put("code", -1);
			jsonObject.put("msg", "用户没有收藏任何活动");
		}
		PrintWriter pw = resp.getWriter();
		pw.print(jsonObject);
		pw.close();
	}
}
