package com.wft.vo;

import java.io.Serializable;

/**
 * @author admin
 * 用户信息
 */
public class UserVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String userName;
	private String pwd;
	 
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	@Override
	public String toString() {
		return "UserVo [id=" + id + ", userName=" + userName + ", pwd=" + pwd
				+ "]";
	}
	 
	 
	
}
