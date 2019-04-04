package com.wft.appconf.service.freemarker;

import java.io.Serializable;

/**
 * @author admin
 * 模板替换dto
 */
public class AppConfDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sheet;/*"模块分类"*/
	private String moudle;/*"模块分类"*/
	private String project;/*"项目分类"*/
	private String desc;/*"业务描述"*/
	private String name;/*"服务名称"*/
	private String ip;/*"应用IP"*/
	private String path;/*"项目部署路径"*/
	private String tname;/*"包名称" 需要去掉war后缀*/ 
	private String port;/*"应用端口"*/
	
	
	
	public String getSheet() {
		return sheet;
	}

	public void setSheet(String sheet) {
		this.sheet = sheet;
	}

	public String getMoudle() {
		return moudle;
	}
	
	public void setMoudle(String moudle) {
		this.moudle = moudle;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "AppConfDto [sheet=" + sheet + ", moudle=" + moudle
				+ ", project=" + project + ", desc=" + desc + ", name=" + name
				+ ", ip=" + ip + ", path=" + path + ", tname=" + tname
				+ ", port=" + port + "]";
	}
	 
	
	
}
