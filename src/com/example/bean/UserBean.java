package com.example.bean;

public class UserBean {
	private int uid;
	private String uemail;
	private String uname;
	private String pwd;
	private String usummary;
	private String uavatar;
	private double ulon;
	private double ulat;
	private String uphone;
	private String uhdid;  //用户活动id
	private String uschdid; // 用户收藏活动的id
	private String scwuid; // 谁收藏我用户id
	private String wscsuid; // 我收藏谁用户id
	private String address;
	public UserBean() {
		super();
	}
	public UserBean(int uid, String uemail, String uname, String pwd,
			String usummary, String uavatar, double ulon, double ulat,
			String uphone, String uhdid, String uschdid, String scwuid,
			String wscsuid) {
		super();
		this.uid = uid;
		this.uemail = uemail;
		this.uname = uname;
		this.pwd = pwd;
		this.usummary = usummary;
		this.uavatar = uavatar;
		this.ulon = ulon;
		this.ulat = ulat;
		this.uphone = uphone;
		this.uhdid = uhdid;
		this.uschdid = uschdid;
		this.scwuid = scwuid;
		this.wscsuid = wscsuid;
	}
	
	
	public UserBean(int uid, String uemail, String uname, String pwd,
			String usummary, String uavatar, double ulon, double ulat,
			String uphone, String uhdid, String uschdid, String scwuid,
			String wscsuid,String address) {
		super();
		this.uid = uid;
		this.uemail = uemail;
		this.uname = uname;
		this.pwd = pwd;
		this.usummary = usummary;
		this.uavatar = uavatar;
		this.ulon = ulon;
		this.ulat = ulat;
		this.uphone = uphone;
		this.uhdid = uhdid;
		this.uschdid = uschdid;
		this.scwuid = scwuid;
		this.wscsuid = wscsuid;
		this.address = address;
	}




	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUemail() {
		return uemail;
	}
	public void setUemail(String uemail) {
		this.uemail = uemail;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getUsummary() {
		return usummary;
	}
	public void setUsummary(String usummary) {
		this.usummary = usummary;
	}
	public String getUavatar() {
		return uavatar;
	}
	public void setUavatar(String uavatar) {
		this.uavatar = uavatar;
	}
	public double getUlon() {
		return ulon;
	}
	public void setUlon(double ulon) {
		this.ulon = ulon;
	}
	public double getUlat() {
		return ulat;
	}
	public void setUlat(double ulat) {
		this.ulat = ulat;
	}
	public String getUphone() {
		return uphone;
	}
	public void setUphone(String uphone) {
		this.uphone = uphone;
	}
	public String getUhdid() {
		return uhdid;
	}
	public void setUhdid(String uhdid) {
		this.uhdid = uhdid;
	}
	public String getUschdid() {
		return uschdid;
	}
	public void setUschdid(String uschdid) {
		this.uschdid = uschdid;
	}
	public String getScwuid() {
		return scwuid;
	}
	public void setScwuid(String scwuid) {
		this.scwuid = scwuid;
	}
	public String getWscsuid() {
		return wscsuid;
	}
	public void setWscsuid(String wscsuid) {
		this.wscsuid = wscsuid;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress() {
		return address;
	}
	
}
