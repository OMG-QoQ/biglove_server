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
 * �������ݣ���Ҫ���ϴ�ͷ�񣬸���ͷ��·��
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
		//getServletContext().getContextPath() ��õ�ǰ��Ŀ¼
		String genPath2 = req.getSession().getServletContext()
				.getRealPath("/") + "HdImg";
		String genPath ="C:\\Users\\riterkai\\Workspaces\\MyEclipse 10\\biglovenew\\WebContent\\HdImg";
		System.out.println("updata---genPath----"+genPath);
		new File(genPath).mkdirs();
		//�ϴ�ͼƬ
		DiskFileUpload diskFileUpload=new DiskFileUpload();
		diskFileUpload.setSizeMax(10*1024*1024);//���
		diskFileUpload.setSizeThreshold(1024);//��������С
		diskFileUpload.setRepositoryPath(genPath);
		String message="�޸�ʧ��";
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
					//��������
//					name=item.getFieldName();
					username = item.getString();
					
					String value=item.getString();
					buffer=value.getBytes();
					System.out.println("username----"+username);
				}else{
					//����ͼƬ
					 //username = item.getString();
					 name= item.getName();
					 username = name.replaceAll(".png","").replaceAll("h","");
					 buffer=item.get();
//					file="http://192.168.1.17:8080/JD/PIC/"+name; //��������ͷ���ַ
					 hdimg_url ="/biglovenew/HdImg/" +name ;
					System.out.println("picname----"+name);
					FileOutputStream fos=new FileOutputStream(new File(genPath,name));//�����ִ���
					fos.write(buffer);
					fos.flush();
					fos.close();
					
					FileOutputStream fos2=new FileOutputStream(new File(genPath2,name));//�����ִ���
					fos2.write(buffer);
					fos2.flush();
					fos2.close();
				}
			}
			boolean bo= HdDao.updateHdImage(Integer.parseInt(username), hdimg_url);
			if(true){
				message = "�޸ĳɹ���";
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
//				+ "temp"; // ��ʱĿ¼
//		System.out.println("temp=" + temp);
//		String loadpath = request.getSession().getServletContext()
//				.getRealPath("/") + "Image"; // �ϴ��ļ����Ŀ¼
//		System.out.println("loadpath=" + loadpath);
//		DiskFileUpload fu = new DiskFileUpload();
//		fu.setSizeMax(1 * 1024 * 1024); // ���������û��ϴ��ļ���С,��λ:�ֽ�
//		fu.setSizeThreshold(4096); // �������ֻ�������ڴ��д洢������,��λ:�ֽ�
//		fu.setRepositoryPath(temp); // ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
//
//		// ��ʼ��ȡ�ϴ���Ϣ
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
//		Iterator iter = fileItems.iterator(); // ���δ���ÿ���ϴ����ļ�
//		while (iter.hasNext()) {
//			FileItem item = (FileItem) iter.next();// �������������ļ�������б���Ϣ
//			if (!item.isFormField()) {
//				String name = item.getName();// ��ȡ�ϴ��ļ���,����·��
//				name = name.substring(name.lastIndexOf("\\") + 1);// ��ȫ·������ȡ�ļ���
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
//			} else// ȡ�������ļ�������б���Ϣ
//			{
//				String fieldvalue = item.getString();
//			}
//		}
//		String text1 = "11";
//		response.sendRedirect("result.jsp?text1=" + text1);
	}

}
