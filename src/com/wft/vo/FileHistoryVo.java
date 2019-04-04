package com.wft.vo;

import java.util.Date;
import java.util.List;

import com.wft.util.CommonUtil;

/**
 * @author admin
 * 脚本文件 前台搜索
 */
public class FileHistoryVo {
 
	 
	private String fileType;
	private String realName;
	private Date beginTime;
	private Date endTime;
	private String timeType = "create";//时间类型 create-创建时间 upload-上传时间
	private int physicsFlag = CommonUtil.EABLE;
	private List<String> realNameLists;
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
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getPhysicsFlag() {
		return physicsFlag;
	}
	public void setPhysicsFlag(int physicsFlag) {
		this.physicsFlag = physicsFlag;
	}
	public String getTimeType() {
		return timeType;
	}
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	public List<String> getRealNameLists() {
		return realNameLists;
	}
	public void setRealNameLists(List<String> realNameLists) {
		this.realNameLists = realNameLists;
	}
 
	
	
	 
}
