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
import com.example.bean.UserBean;
import com.example.bean.UserHdBean;
import com.example.dao.HdDao;
import com.example.dao.RegisterDao;
import com.example.dao.UserInfoDao;

import net.sf.json.JSONObject;

// 获得用户的所有的活动
public class AllHdServlet extends HttpServlet{

	
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
	    
		int hdidcount = HdDao.count();
		//String hdids[]  = hdidStr.split(",");
		for (int i = 1; i <= hdidcount; i++) {
			
		    	HdBean hb = HdDao.getHdInfo(i);
		    	UserBean user = RegisterDao.queryByid( hb.getHdsuid());
//	
		        UserHdBean  uh= new UserHdBean(user.getUname(), user.getUavatar(),
		        		hb.getHdid(), hb.getHdname(), hb.getHdlon(), hb.getHdlat(), hb.getStartdate(), 
		        		hb.getEnddate(), hb.getHdImg(), hb.getHdcontent(), hb.getHdsuid(),hb.getAddress());
		        String jsonText = JSON.toJSONString(uh, true);  
		    	list.add(uh);
		    	
		    	//list.add(hb);
		}
		if(list!=null&& list.size()>0) {
			jsonObject.put("code", 1);
			jsonObject.put("msg", "全部活动获得成功");
			System.out.print("全部活動獲得成功");
			jsonObject.put("infoArray",JSON.toJSONString(list,true));
		} else {
			jsonObject.put("code", -1);
			jsonObject.put("msg", "用户没有发布活动");
		}
		PrintWriter pw = resp.getWriter();
		pw.print(jsonObject);
		pw.close();
	}
}
