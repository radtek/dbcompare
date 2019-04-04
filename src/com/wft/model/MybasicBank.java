package com.wft.model;

import java.util.Date;

import com.wft.util.CommonUtil;

/**
 * @author admin 脚本文件
 */
public class MybasicBank {

	 
	private int id;
	private String url;
	private String userName;
	private String realName;
	
	private String pwd;
	private String sechma;
	private String bankType = CommonUtil.BANK_TYPE_C;
	private String webSite;
	private int mold;
	private String checkTime;
	private int checkResult = CommonUtil.CHECK_RESULT_NOT_START;
	
	private String checkContent;

	private Date createTime;
	private Date updateTime;
	private int physicsFlag = CommonUtil.EABLE;
	
	private int checkIs ;
	
	private String merge;
	
	private String cleaingProcess;
	private String planName;
	private String fls1;
	private String fls2;
	private String fls3;
	
	 
    
    
	private String acceptorg;
	private String platform;
	private String jar;
	private String md5;
	private String filetype;
	
	private String filecount;
	private String nullfile;
	private String planPameOverride;
	private String cleaingProcessOverride;
	private String ftp;
	
	private String notify;
	private String autoclean;
	private String servicePort;
	private String etraparam;
	
	private String isfreez;
	private String issubdiy;
	private String isbroken;
	 
	private String webok;
	private String needback;
	private String needfile;
	private String needcode;
	
	private int issend;
	private int sendcount;
	 
	private String testSechma;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getSechma() {
		return sechma;
	}
	public void setSechma(String sechma) {
		this.sechma = sechma;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public int getMold() {
		return mold;
	}
	public void setMold(int mold) {
		this.mold = mold;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public int getCheckResult() {
		return checkResult;
	}
	
	public void setCheckResult(int checkResult) {
		this.checkResult = checkResult;
	}
	public String getCheckContent() {
		return checkContent;
	}
	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
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
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public int getCheckIs() {
		return checkIs;
	}
	public void setCheckIs(int checkIs) {
		this.checkIs = checkIs;
	}
	public String getWebSite() {
		return webSite;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	public String getMerge() {
		return merge;
	}
	public void setMerge(String merge) {
		this.merge = merge;
	}
	public String getCleaingProcess() {
		return cleaingProcess;
	}
	public void setCleaingProcess(String cleaingProcess) {
		this.cleaingProcess = cleaingProcess;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getFls1() {
		return fls1;
	}
	public void setFls1(String fls1) {
		this.fls1 = fls1;
	}
	public String getFls2() {
		return fls2;
	}
	public void setFls2(String fls2) {
		this.fls2 = fls2;
	}
	public String getFls3() {
		return fls3;
	}
	public void setFls3(String fls3) {
		this.fls3 = fls3;
	}
	public String getAcceptorg() {
		return acceptorg;
	}
	public void setAcceptorg(String acceptorg) {
		this.acceptorg = acceptorg;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getJar() {
		return jar;
	}
	public void setJar(String jar) {
		this.jar = jar;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public String getFilecount() {
		return filecount;
	}
	public void setFilecount(String filecount) {
		this.filecount = filecount;
	}
	public String getNullfile() {
		return nullfile;
	}
	public void setNullfile(String nullfile) {
		this.nullfile = nullfile;
	}
	public String getPlanPameOverride() {
		return planPameOverride;
	}
	public void setPlanPameOverride(String planPameOverride) {
		this.planPameOverride = planPameOverride;
	}
	public String getCleaingProcessOverride() {
		return cleaingProcessOverride;
	}
	public void setCleaingProcessOverride(String cleaingProcessOverride) {
		this.cleaingProcessOverride = cleaingProcessOverride;
	}
	public String getFtp() {
		return ftp;
	}
	public void setFtp(String ftp) {
		this.ftp = ftp;
	}
	public String getNotify() {
		return notify;
	}
	public void setNotify(String notify) {
		this.notify = notify;
	}
	public String getAutoclean() {
		return autoclean;
	}
	public void setAutoclean(String autoclean) {
		this.autoclean = autoclean;
	}
	public String getServicePort() {
		return servicePort;
	}
	public void setServicePort(String servicePort) {
		this.servicePort = servicePort;
	}
	public String getEtraparam() {
		return etraparam;
	}
	public void setEtraparam(String etraparam) {
		this.etraparam = etraparam;
	}
	public String getIsfreez() {
		return isfreez;
	}
	public void setIsfreez(String isfreez) {
		this.isfreez = isfreez;
	}
	public String getIssubdiy() {
		return issubdiy;
	}
	public void setIssubdiy(String issubdiy) {
		this.issubdiy = issubdiy;
	}
	public String getIsbroken() {
		return isbroken;
	}
	public void setIsbroken(String isbroken) {
		this.isbroken = isbroken;
	}
	public String getWebok() {
		return webok;
	}
	public void setWebok(String webok) {
		this.webok = webok;
	}
	public String getNeedback() {
		return needback;
	}
	public void setNeedback(String needback) {
		this.needback = needback;
	}
	public String getNeedfile() {
		return needfile;
	}
	public void setNeedfile(String needfile) {
		this.needfile = needfile;
	}
	public String getNeedcode() {
		return needcode;
	}
	public void setNeedcode(String needcode) {
		this.needcode = needcode;
	}
	public int getIssend() {
		return issend;
	}
	public void setIssend(int issend) {
		this.issend = issend;
	}
	public int getSendcount() {
		return sendcount;
	}
	public void setSendcount(int sendcount) {
		this.sendcount = sendcount;
	}
	public String getTestSechma() {
		return testSechma;
	}
	public void setTestSechma(String testSechma) {
		this.testSechma = testSechma;
	}
	 
	

}
