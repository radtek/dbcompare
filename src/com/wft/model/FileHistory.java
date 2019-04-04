package com.wft.model;

import java.util.Date;

import com.wft.util.CommonUtil;

/**
 * @author admin
 * 脚本文件
 */
public class FileHistory {
 
	private int id;
	private String fileType;
	private String realName;
	private String serviceName;
	private String serviceUrl;
	private String md5;
	private Date createTime;
	private Date updateTime;
	private int status;
	private int physicsFlag = CommonUtil.EABLE;
	private int checkOn;
	private int appendBasic;
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
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPhysicsFlag() {
		return physicsFlag;
	}
	public void setPhysicsFlag(int physicsFlag) {
		this.physicsFlag = physicsFlag;
	}
	public int getCheckOn() {
		return checkOn;
	}
	public void setCheckOn(int checkOn) {
		this.checkOn = checkOn;
	}
	public int getAppendBasic() {
		return appendBasic;
	}
	public void setAppendBasic(int appendBasic) {
		this.appendBasic = appendBasic;
	}
	@Override
	public String toString() {
		return "FileHistory [id=" + id + ", fileType=" + fileType
				+ ", realName=" + realName + ", serviceName=" + serviceName
				+ ", serviceUrl=" + serviceUrl + ", md5=" + md5
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", status=" + status + ", physicsFlag=" + physicsFlag
				+ ", checkOn=" + checkOn + ", appendBasic=" + appendBasic + "]";
	}
	 

	 
}
