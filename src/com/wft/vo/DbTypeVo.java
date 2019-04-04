package com.wft.vo;


public class DbTypeVo {

	private String type;
	private String url;
	private String userName;
	private String pwd;
	private String name;
	private int mold;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMold() {
		return mold;
	}
	public void setMold(int mold) {
		this.mold = mold;
	}
	@Override
	public String toString() {
		return "DbTypeVo [type=" + type + ", url=" + url + ", userName="
				+ userName + ", pwd=" + pwd + ", name=" + name + ", mold="
				+ mold + "]";
	}
	
	
	
}
