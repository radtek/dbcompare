package com.wft.sqluldr2.logic.bean;

import java.io.Serializable;




/**
 * 使用lombok简化Java重复冗余代码
 * @Data 表示getter setter toString方法
 * @NoArgsConstructor 表示无参构造方法
 * 
 * @author admin
 */
public class DBVo implements Serializable{
	
	private static final long serialVersionUID = -1716074711802482796L;
	
	/** 数据库类型  */
	private String type;
	
	/** 驱动名称 */
	private String DBClassName;
	
	/** 数据库IP */
	private String Ip;
	
	/** 数据库PORT */
	private String port;
	
	/** 数据连接URL */
	private String DBUrl;
	
	/** 数据库Service名称 */
	private String DBServiceName;
	
	/** 数据库帐号 */
	private String DBUser;
	
	/** 数据库密码 */
	private String DBPwd;
	
	/**
	 * 获取数据库类型 
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * 设置数据库类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取数据库IP
	 */
	public String getIp() {
		return Ip;
	}

	/**
	 * 设置数据库IP
	 */
	public void setIp(String ip) {
		Ip = ip;
	}
	
	/**
	 * 获取端口
	 */
	public String getPort() {
		return port;
	}
	
	/**
	 * 设置端口
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * 获取驱动名称
	 */
	public String getDBClassName() {
		return DBClassName;
	}
	
	/**
	 * 设置驱动名称
	 */
	public void setDBClassName(String dBClassName) {
		DBClassName = dBClassName;
	}
	
	/**
	 * 获取数据连接URL
	 */
	public String getDBUrl() {
		if (this.type.equalsIgnoreCase("oracle")) {
			DBUrl = "jdbc:oracle:thin:@" + this.getIp() + ":" + this.port +":" + this.getDBServiceName();
		}else if (this.type.equalsIgnoreCase("mysql")){
			DBUrl = "jdbc:mysql://" + this.getIp() + "/" + this.getDBServiceName() + "?useUnicode=true&characterEncoding=utf8";
		}else if (this.type.equalsIgnoreCase("sqlserver")){
			DBUrl = "jdbc:sqlserver://" + this.getIp() + ";DatabaseName=" + this.getDBServiceName();
		}
		return DBUrl;
	}

	/**
	 * 设置数据连接URL
	 */
	public void setDBUrl(String dBUrl) {
		DBUrl = dBUrl;
	}
	
	/**
	 * 获取数据库Service名称
	 */
	public String getDBServiceName() {
		return DBServiceName;
	}
	
	/**
	 * 设置数据库Service名称
	 */
	public void setDBServiceName(String dBServiceName) {
		DBServiceName = dBServiceName;
	}
	
	/**
	 * 获取数据库帐号
	 */
	public String getDBUser() {
		return DBUser;
	}
	
	/**
	 * 设置数据库帐号
	 */
	public void setDBUser(String dBUser) {
		DBUser = dBUser;
	}
	
	/**
	 * 获取数据库密码
	 */
	public String getDBPwd() {
		return DBPwd;
	}
	
	/**
	 * 设置数据库密码
	 */
	public void setDBPwd(String dBPwd) {
		DBPwd = dBPwd;
	}

}
