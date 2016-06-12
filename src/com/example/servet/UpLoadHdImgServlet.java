package com.example.servet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.tomcat.util.http.fileupload.DiskFileUpload;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;

import com.example.dao.HdDao;

/**
 * 更新数据，主要是上传头像，更新头像路径
 * 
 * @author asus
 * 
 */
public class UpLoadHdImgServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		//getServletContext().getContextPath() 获得当前的目录
		String genPath2 = req.getSession().getServletContext()
				.getRealPath("/") + "HdImg";
		String genPath ="C:\\Users\\riterkai\\Workspaces\\MyEclipse 10\\biglovenew\\WebContent\\HdImg";
		System.out.println("updata---genPath----"+genPath);
		new File(genPath).mkdirs();
		//上传图片
		DiskFileUpload diskFileUpload=new DiskFileUpload();
		diskFileUpload.setSizeMax(10*1024*1024);//最大
		diskFileUpload.setSizeThreshold(1024);//缓冲区大小
		diskFileUpload.setRepositoryPath(genPath);
		String message="修改失败";
		String name="";
		String username="";
//		String file="";
		String hdimg_url ="";
		try {
			List list = diskFileUpload.parseRequest(req);
			
		
			for (int i = 0; i < list.size(); i++) {
				FileItem item=(FileItem) list.get(i);
				byte[] buffer=null;
				if(item.isFormField()){
					//若是文字
//					name=item.getFieldName();
					username = item.getString();
					
					String value=item.getString();
					buffer=value.getBytes();
					System.out.println("username----"+username);
				}else{
					//若是图片
					 //username = item.getString();
					 name= item.getName();
					 username = name.replaceAll(".png","").replaceAll("h","");
					 buffer=item.get();
//					file="http://192.168.1.17:8080/JD/PIC/"+name; //服务器端头像地址
					 hdimg_url ="/biglovenew/HdImg/" +name ;
					System.out.println("picname----"+name);
					FileOutputStream fos=new FileOutputStream(new File(genPath,name));//带名字存入
					fos.write(buffer);
					fos.flush();
					fos.close();
					
					FileOutputStream fos2=new FileOutputStream(new File(genPath2,name));//带名字存入
					fos2.write(buffer);
					fos2.flush();
					fos2.close();
				}
			}
			boolean bo= HdDao.updateHdImage(Integer.parseInt(username), hdimg_url);
			if(true){
				message = "修改成功！";
			}
			JSONObject json=new JSONObject();
			json.accumulate("message", message);
			json.accumulate("username", username);
			PrintWriter pw=resp.getWriter();
			pw.print(json);
			pw.close();
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
		
//
//		String temp = request.getSession().getServletContext().getRealPath("/")
//				+ "temp"; // 临时目录
//		System.out.println("temp=" + temp);
//		String loadpath = request.getSession().getServletContext()
//				.getRealPath("/") + "Image"; // 上传文件存放目录
//		System.out.println("loadpath=" + loadpath);
//		DiskFileUpload fu = new DiskFileUpload();
//		fu.setSizeMax(1 * 1024 * 1024); // 设置允许用户上传文件大小,单位:字节
//		fu.setSizeThreshold(4096); // 设置最多只允许在内存中存储的数据,单位:字节
//		fu.setRepositoryPath(temp); // 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
//
//		// 开始读取上传信息
//		int index = 0;
//		List fileItems = null;
//
//		try {
//			fileItems = fu.parseRequest(request);
//			System.out.println("fileItems=" + fileItems);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		Iterator iter = fileItems.iterator(); // 依次处理每个上传的文件
//		while (iter.hasNext()) {
//			FileItem item = (FileItem) iter.next();// 忽略其他不是文件域的所有表单信息
//			if (!item.isFormField()) {
//				String name = item.getName();// 获取上传文件名,包括路径
//				name = name.substring(name.lastIndexOf("\\") + 1);// 从全路径中提取文件名
//				long size = item.getSize();
//				if ((name == null || name.equals("")) && size == 0)
//					continue;
//				int point = name.indexOf(".");
//				name = (new Date()).getTime()
//						+ name.substring(point, name.length()) + index;
//				index++;
//				File fNew = new File(loadpath, name);
//				try {
//					item.write(fNew);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//			} else// 取出不是文件域的所有表单信息
//			{
//				String fieldvalue = item.getString();
//			}
//		}
//		String text1 = "11";
//		response.sendRedirect("result.jsp?text1=" + text1);
	}

}
