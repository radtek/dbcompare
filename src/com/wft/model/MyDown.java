package com.wft.model;

import java.util.Date;

import com.wft.util.CommonUtil;

/**
 * @author admin 脚本文件
 */
public class MyDown {

	private int id;
	private String sechma;
	private String way;
	private String content;
	private String url;
	private String ip;
	private int downType;
	private Date createTime;
	private Date updateTime;
	private int physicsFlag = CommonUtil.EABLE;
	private String range;
	private String remark;
	private String realRange;
	 
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getSechma() {
		return sechma;
	}

	public void setSechma(String sechma) {
		this.sechma = sechma;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getDownType() {
		return downType;
	}

	public void setDownType(int downType) {
		this.downType = downType;
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

	public int getPhysicsFlag() {
		return physicsFlag;
	}

	public void setPhysicsFlag(int physicsFlag) {
		this.physicsFlag = physicsFlag;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRealRange() {
		return realRange;
	}

	public void setRealRange(String realRange) {
		this.realRange = realRange;
	}

	@Override
	public String toString() {
		return "MyDown [id=" + id + ", sechma=" + sechma + ", way=" + way
				+ ", content=" + content + ", url=" + url + ", ip=" + ip
				+ ", downType=" + downType + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", physicsFlag=" + physicsFlag
				+ ", range=" + range + ", remark=" + remark + ", realRange="
				+ realRange + "]";
	}

	 

 

}
