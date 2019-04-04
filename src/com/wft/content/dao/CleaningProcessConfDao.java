package com.wft.content.dao;


/**
 * 清分处理配置表CLE_CLEANING_PROCESS_CONF
 */
public class CleaningProcessConfDao   {
 

	/** 配置ID */
	private Integer confId;

	/** 受理机构ID */
	private String acceptOrgId;

	/** 清分处理类 */
	private String cleaningProcessClass;

	/** 清分类型 */
	private Integer cleaningType;
	
	/** 银行类型 */
	private Integer cleaningBankType;

	/** 是否自动清分 */
	private Integer isAutoClean;

	/** 是否自动上传清分文件 */
	private Integer isAutoUpload;

	/** 是否可多次上传 */
	private Integer isReUpload;

	/** 是否需要回导结果 */
	private Integer isNeedPostback;

	/** 是否自动回导结果 */
	private Integer isAutoPostback;

	/** 物理标识 */
	private Integer physicsFlag;

	/** 上传FTP配置 */
	private String uploadFtp;

	/** 下载FTP配置 */
	private String downloadFtp;

	/** 创建用户 */
	private Integer createUser;

	/** 创建人 */
	private String createEmp;

	/** 创建时间 */
	private java.util.Date createTime;

	/** 更新时间 */
	private java.util.Date updateTime;

	/** 文件最大记录数*/
	private Integer maxRecordCount;
	
	/** 上传文件加密开关：开启：open 关闭：close*/
	private String uploadFileEncrypt;
	
	/** 自动回盘文件开关：开启：open 关闭：close*/
	private String downloadFileAuto;
	
	/**
	 * 取得配置ID
	 */
	public Integer getConfId() {
		return this.confId;
	}

	/**
	 * 设置配置ID
	 */
	public void setConfId(Integer confId) {
		this.confId = confId;
	}

	/**
	 * 取得受理机构ID
	 */
	public String getAcceptOrgId() {
		return this.acceptOrgId;
	}

	/**
	 * 设置受理机构ID
	 */
	public void setAcceptOrgId(String acceptOrgId) {
		this.acceptOrgId = acceptOrgId;
	}

	/**
	 * 取得清分处理类
	 */
	public String getCleaningProcessClass() {
		return this.cleaningProcessClass;
	}

	/**
	 * 设置清分处理类
	 */
	public void setCleaningProcessClass(String cleaningProcessClass) {
		this.cleaningProcessClass = cleaningProcessClass;
	}

	/**
	 * 取得清分类型
	 */
	public Integer getCleaningType() {
		return this.cleaningType;
	}

	/**
	 * 设置清分类型
	 */
	public void setCleaningType(Integer cleaningType) {
		this.cleaningType = cleaningType;
	}
	
	/**
	 * 取得银行类型
	 */
	public Integer getCleaningBankType() {
		return this.cleaningBankType;
	}

	/**
	 * 设置银行类型
	 */
	public void setCleaningBankType(Integer cleaningBankType) {
		this.cleaningBankType = cleaningBankType;
	}

	/**
	 * 取得是否自动清分
	 */
	public Integer getIsAutoClean() {
		return this.isAutoClean;
	}

	/**
	 * 设置是否自动清分
	 */
	public void setIsAutoClean(Integer isAutoClean) {
		this.isAutoClean = isAutoClean;
	}

	/**
	 * 取得是否自动上传清分文件
	 */
	public Integer getIsAutoUpload() {
		return this.isAutoUpload;
	}

	/**
	 * 设置是否自动上传清分文件
	 */
	public void setIsAutoUpload(Integer isAutoUpload) {
		this.isAutoUpload = isAutoUpload;
	}

	/**
	 * 取得是否可多次上传
	 */
	public Integer getIsReUpload() {
		return this.isReUpload;
	}

	/**
	 * 设置是否可多次上传
	 */
	public void setIsReUpload(Integer isReUpload) {
		this.isReUpload = isReUpload;
	}

	/**
	 * 取得是否需要回导结果
	 */
	public Integer getIsNeedPostback() {
		return this.isNeedPostback;
	}

	/**
	 * 设置是否需要回导结果
	 */
	public void setIsNeedPostback(Integer isNeedPostback) {
		this.isNeedPostback = isNeedPostback;
	}

	/**
	 * 取得是否自动回导结果
	 */
	public Integer getIsAutoPostback() {
		return this.isAutoPostback;
	}

	/**
	 * 设置是否自动回导结果
	 */
	public void setIsAutoPostback(Integer isAutoPostback) {
		this.isAutoPostback = isAutoPostback;
	}

	/**
	 * 取得物理标识
	 */
	public Integer getPhysicsFlag() {
		return this.physicsFlag;
	}

	/**
	 * 设置物理标识
	 */
	public void setPhysicsFlag(Integer physicsFlag) {
		this.physicsFlag = physicsFlag;
	}

	/**
	 * 取得上传FTP配置
	 */
	public String getUploadFtp() {
		return this.uploadFtp;
	}

	/**
	 * 设置上传FTP配置
	 */
	public void setUploadFtp(String uploadFtp) {
		this.uploadFtp = uploadFtp;
	}

	/**
	 * 取得下载FTP配置
	 */
	public String getDownloadFtp() {
		return this.downloadFtp;
	}

	/**
	 * 设置下载FTP配置
	 */
	public void setDownloadFtp(String downloadFtp) {
		this.downloadFtp = downloadFtp;
	}

	/**
	 * 取得创建用户
	 */
	public Integer getCreateUser() {
		return this.createUser;
	}

	/**
	 * 设置创建用户
	 */
	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	/**
	 * 取得创建人
	 */
	public String getCreateEmp() {
		return this.createEmp;
	}

	/**
	 * 设置创建人
	 */
	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp;
	}

	/**
	 * 取得创建时间
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * 设置创建时间
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 取得更新时间
	 */
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * 设置更新时间
	 */
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getPrimaryKey() {
		return this.confId;
	}

	public Integer getMaxRecordCount() {
		return maxRecordCount;
	}

	public void setMaxRecordCount(Integer maxRecordCount) {
		this.maxRecordCount = maxRecordCount;
	}

	public String getUploadFileEncrypt() {
		return uploadFileEncrypt;
	}

	public void setUploadFileEncrypt(String uploadFileEncrypt) {
		this.uploadFileEncrypt = uploadFileEncrypt;
	}

	public String getDownloadFileAuto() {
		return downloadFileAuto;
	}

	public void setDownloadFileAuto(String downloadFileAuto) {
		this.downloadFileAuto = downloadFileAuto;
	}
}
