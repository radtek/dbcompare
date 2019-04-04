package com.wft.mail;

import javax.mail.Address;



public class EmailSendBean {
	private  String email_smtp ;//smtp服务器
	private  int email_port ;//端口
	private  String email_user ;//用户名
	private  String email_pwd ;//密码
	private  String email_tip ;//内容
	private  String email_subject;//主题
	private  Address internetAddress[];//发送人地址
	public EmailSendBean() {
		// TODO Auto-generated constructor stub
	}
	


	public String getEmail_smtp() {
		return email_smtp;
	}
	public void setEmail_smtp(String email_smtp) {
		this.email_smtp = email_smtp;
	}
	public int getEmail_port() {
		return email_port;
	}
	public void setEmail_port(int email_port) {
		this.email_port = email_port;
	}
	public String getEmail_user() {
		return email_user;
	}
	public void setEmail_user(String email_user) {
		this.email_user = email_user;
	}
	public String getEmail_pwd() {
		return email_pwd;
	}
	public void setEmail_pwd(String email_pwd) {
		this.email_pwd = email_pwd;
	}
	public String getEmail_tip() {
		return email_tip;
	}
	public void setEmail_tip(String email_tip) {
		this.email_tip = email_tip;
	}
	

	public Address[] getInternetAddress() {
		return internetAddress;
	}



	public void setInternetAddress(Address[] internetAddress) {
		this.internetAddress = internetAddress;
	}



	public String getEmail_subject() {
		return email_subject;
	}



	public void setEmail_subject(String email_subject) {
		this.email_subject = email_subject;
	}
	
	
}