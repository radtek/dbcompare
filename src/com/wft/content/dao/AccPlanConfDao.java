package com.wft.content.dao;


/**
 * 渠道日结方案配置表CLE_ACC_PLAN_CONF
 */
public class AccPlanConfDao   {

	 

	/** 方案ID */
	private Integer planId;

	/** 受理机构ID */
	private String acceptOrgId;

	/** 方案名称 */
	private String planName;

	/** 方案查询类 */
	private String planSerrchClass;

	/** 方案处理类 */
	private String planHandleClass;
	
	/** 清分记录生成类 */
	private String cleanRecordGenClass;

	/** 方案处理线程数 */
	private Integer planThreadCount;

	/** 威富通渠道号 */
	private String wftChannelId;

	/** 威富通补贴费率 */
	private Integer wftSubsidyRate;
	
	/** 基准费率 */
	private Integer baseRate;
	
	/** 固定分成费率 */
	private Integer fixedDividedRate;
	
	/** 备注 */
	private String remark;
	
	/** 目标渠道 */
	private String planParam1;

	/** 非目标渠道(多个用英文逗号分隔) */
	private String planParam2;

	/** 方案参数3 */
	private String planParam3;

	/** 商户结算方式 */
	private Integer planParam4;

	/** 分润渠道层级 */
	private Integer planParam5;

	/** 渠道属性 */
	private Integer planParam6;

	/** 物理标识 */
	private Integer physicsFlag;

	/** 创建用户 */
	private Integer createUser;

	/** 创建人 */
	private String createEmp;

	/** 创建时间 */
	private java.util.Date createTime;

	/** 更新时间 */
	private java.util.Date updateTime;

	/** 目标支付中心(多个用英文逗号分隔)*/
	private String targetPayCenter;
	
	/** 非目标支付中心(多个用英文逗号分隔)*/
	private String nonTargetPayCenter;

	/** 目标支付中心(多个用英文逗号分隔)*/
	public String getTargetPayCenter() {
		return targetPayCenter;
	}
	
	/** 目标支付中心(多个用英文逗号分隔)*/
	public void setTargetPayCenter(String targetPayCenter) {
		this.targetPayCenter = targetPayCenter;
	}
	
	/** 非目标支付中心(多个用英文逗号分隔)*/
	public String getNonTargetPayCenter() {
		return nonTargetPayCenter;
	}
	
	/** 非目标支付中心(多个用英文逗号分隔)*/
	public void setNonTargetPayCenter(String nonTargetPayCenter) {
		this.nonTargetPayCenter = nonTargetPayCenter;
	}
	
	/** 获取固定分成费率 */
	public Integer getFixedDividedRate() {
		return fixedDividedRate;
	}
	
	/** 设置固定分成费率 */
	public void setFixedDividedRate(Integer fixedDividedRate) {
		this.fixedDividedRate = fixedDividedRate;
	}
	
	/** 获取威富通渠道号 */
	public String getWftChannelId() {
		return wftChannelId;
	}
	
	/** 设置威富通渠道号 */
	public void setWftChannelId(String wftChannelId) {
		this.wftChannelId = wftChannelId;
	}
	
	/** 获取威富通补贴费率 */
	public Integer getWftSubsidyRate() {
		return wftSubsidyRate;
	}
	
	/** 设置威富通补贴费率 */
	public void setWftSubsidyRate(Integer wftSubsidyRate) {
		this.wftSubsidyRate = wftSubsidyRate;
	}
	
	/** 获取基准费率 */
	public Integer getBaseRate() {
		return baseRate;
	}
	
	/** 设置基准费率 */
	public void setBaseRate(Integer baseRate) {
		this.baseRate = baseRate;
	}
	
	/** 获取备注 */
	public String getRemark() {
		return remark;
	}
	
	/** 设置备注 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * 取得方案ID
	 */
	public Integer getPlanId() {
		return this.planId;
	}

	/**
	 * 设置方案ID
	 */
	public void setPlanId(Integer planId) {
		this.planId = planId;
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
	 * 取得方案名称
	 */
	public String getPlanName() {
		return this.planName;
	}

	/**
	 * 设置方案名称
	 */
	public void setPlanName(String planName) {
		this.planName = planName;
	}

	/**
	 * 取得方案查询类
	 */
	public String getPlanSerrchClass() {
		return this.planSerrchClass;
	}

	/**
	 * 设置方案查询类
	 */
	public void setPlanSerrchClass(String planSerrchClass) {
		this.planSerrchClass = planSerrchClass;
	}

	/**
	 * 取得方案处理类
	 */
	public String getPlanHandleClass() {
		return this.planHandleClass;
	}

	/**
	 * 设置方案处理类
	 */
	public void setPlanHandleClass(String planHandleClass) {
		this.planHandleClass = planHandleClass;
	}

	/**
	 * 取得清分记录生成类
	 */
	public String getCleanRecordGenClass() {
		return cleanRecordGenClass;
	}
	
	/**
	 * 设置清分记录生成类
	 */
	public void setCleanRecordGenClass(String cleanRecordGenClass) {
		this.cleanRecordGenClass = cleanRecordGenClass;
	}

	/**
	 * 取得方案处理线程数
	 */
	public Integer getPlanThreadCount() {
		return this.planThreadCount;
	}

	/**
	 * 设置方案处理线程数
	 */
	public void setPlanThreadCount(Integer planThreadCount) {
		this.planThreadCount = planThreadCount;
	}

	/**
	 * 取得目标渠道
	 */
	public String getPlanParam1() {
		return this.planParam1;
	}

	/**
	 * 设置目标渠道
	 */
	public void setPlanParam1(String planParam1) {
		this.planParam1 = planParam1;
	}

	/**
	 * 取得非目标渠道(多个用英文逗号分隔)
	 */
	public String getPlanParam2() {
		return this.planParam2;
	}

	/**
	 * 设置非目标渠道(多个用英文逗号分隔)
	 */
	public void setPlanParam2(String planParam2) {
		this.planParam2 = planParam2;
	}

	/**
	 * 取得方案参数3
	 */
	public String getPlanParam3() {
		return this.planParam3;
	}

	/**
	 * 设置方案参数3
	 */
	public void setPlanParam3(String planParam3) {
		this.planParam3 = planParam3;
	}

	/**
	 * 取得商户结算方式
	 */
	public Integer getPlanParam4() {
		return this.planParam4;
	}

	/**
	 * 设置商户结算方式
	 */
	public void setPlanParam4(Integer planParam4) {
		this.planParam4 = planParam4;
	}

	/**
	 * 取得分润渠道层级
	 */
	public Integer getPlanParam5() {
		return this.planParam5;
	}

	/**
	 * 设置分润渠道层级
	 */
	public void setPlanParam5(Integer planParam5) {
		this.planParam5 = planParam5;
	}

	/**
	 * 取得渠道属性
	 */
	public Integer getPlanParam6() {
		return this.planParam6;
	}

	/**
	 * 设置渠道属性
	 */
	public void setPlanParam6(Integer planParam6) {
		this.planParam6 = planParam6;
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
		return this.planId;
	}

}
