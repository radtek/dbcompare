package com.wft.content.dto;


/**
 * 清分记录合并配置表CLE_BILL_MERGE_CONF
 */
public class BillMergeConf  {
 

	/** ID */
	private Integer id;

	/** 受理机构ID */
	private String acceptOrgId;

	/** 策略 */
	private String strategy;

	/** 优先级 */
	private Integer priority;

	/** 创建用户 */
	private Integer createUser;

	/** 创建时间 */
	private java.util.Date createTime;

	/** 更新时间 */
	private java.util.Date updateTime;

	/** 物理标识 */
	private Integer physicsFlag;

	/**
	 * 取得ID
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * 设置ID
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * 取得策略
	 */
	public String getStrategy() {
		return this.strategy;
	}

	/**
	 * 设置策略
	 */
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	/**
	 * 取得优先级
	 */
	public Integer getPriority() {
		return this.priority;
	}

	/**
	 * 设置优先级
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
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

	public Integer getPrimaryKey() {
		return this.id;
	}

}
