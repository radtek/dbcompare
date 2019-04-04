package com.wft.appconf.dto;

import java.util.Date;

import com.wft.util.CommonUtil;

/**
 * @author admin 银行配置模板文件
 */
public class MyBankConf {

	 
	private int id;
	 
	private int bankId;
	private String bankName;
	private int stype;
	private String remark;
	private String excelJson;
	private String content;
	private String excelPath;
	private String confPath;
	private int fldn1;
	private String flds1;
	
	private Date createTime;
	private Date updateTime;
	private int physicsFlag = CommonUtil.EABLE;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBankId() {
		return bankId;
	}
	public void setBankId(int bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public int getStype() {
		return stype;
	}
	public void setStype(int stype) {
		this.stype = stype;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getExcelJson() {
		return excelJson;
	}
	public void setExcelJson(String excelJson) {
		this.excelJson = excelJson;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getExcelPath() {
		return excelPath;
	}
	public void setExcelPath(String excelPath) {
		this.excelPath = excelPath;
	}
	public String getConfPath() {
		return confPath;
	}
	public void setConfPath(String confPath) {
		this.confPath = confPath;
	}
	public int getFldn1() {
		return fldn1;
	}
	public void setFldn1(int fldn1) {
		this.fldn1 = fldn1;
	}
	public String getFlds1() {
		return flds1;
	}
	public void setFlds1(String flds1) {
		this.flds1 = flds1;
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
	 
  

}
