package com.wft.model;

import java.util.Date;

import com.wft.util.CommonUtil;

/**
 * @author admin 脚本文件
 */
public class MyBasicTable {


	private int id;
	private String fileType;
	private String realName;
	private String serviceUrl;
	private String ip;
	private Date createTime;
	private Date updateTime;
	private int orders;
	private int physicsFlag = CommonUtil.EABLE;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getOrders() {
		return orders;
	}
	public void setOrders(int orders) {
		this.orders = orders;
	}
	public int getPhysicsFlag() {
		return physicsFlag;
	}
	public void setPhysicsFlag(int physicsFlag) {
		this.physicsFlag = physicsFlag;
	}
	@Override
	public String toString() {
		return "MyBasicTable [id=" + id + ", fileType=" + fileType
				+ ", realName=" + realName   
				+ ", serviceUrl=" + serviceUrl + ", ip=" + ip + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", orders="
				+ orders + ", physicsFlag=" + physicsFlag + "]";
	}
	 
	
	
}
