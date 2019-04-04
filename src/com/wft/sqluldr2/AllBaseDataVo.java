package com.wft.sqluldr2;

import java.util.Arrays;

/**
 * 全量基础资料VO
 * 
 */
public class AllBaseDataVo {

	/** 导出数据库用户名*/
	private String expUser = "zzy";
	
	/** 导出数据库密码*/
	private String expPwd = "zzy";
	/** 导出数据库sid*/
	private String expsid = "ntest";
	
	/** 导出数据库IP*/
	private String expIp = "123.207.118.153";
	
	/** 基础表名 */
	private String[] baseTable;
	
	/** 最大查询次数, 默认是100次 */
	private Integer maxTryCount;
	
	/** 间隔时间， 默认是10秒 */
	private Integer timeInterval;
	
	/** 导入数据库用户名*/
	private String impUser;
	
	/** 导入数据库密码*/
	private String impPwd;
	/** 导入数据库sid*/
	private String impSid;
	
	/** 导入数据库IP*/
	private String impIp;
	
	/** 分割符 */
	private String separate="0x2c";

	/**
	 * 获取 导出数据库用户名
	 */
	public String getExpUser() {
		return expUser;
	}
	
	/**
	 * 获取 导出数据库用户名
	 */
	public void setExpUser(String expUser) {
		this.expUser = expUser;
	}
	
	/**
	 * 获取 导出数据库密码
	 */
	public String getExpPwd() {
		return expPwd;
	}
	
	/**
	 * 设置 导出数据库密码
	 */
	public void setExpPwd(String expPwd) {
		this.expPwd = expPwd;
	}
	
	/**
	 * 获取 导出数据库IP
	 */
	public String getExpIp() {
		return expIp;
	}
	
	/**
	 * 设置 导出数据库IP
	 */
	public void setExpIp(String expIp) {
		this.expIp = expIp;
	}
	
	/**
	 * 获取 基础表名
	 */
	public String[] getBaseTable() {
		return baseTable;
	}
	
	/**
	 * 设置 基础表名
	 */
	public void setBaseTable(String[] baseTable) {
		this.baseTable = baseTable;
	}
	
	/**
	 * 获取 最大查询次数, 默认是100次
	 */
	public Integer getMaxTryCount() {
		if (null == this.maxTryCount) {
			return 100;
		}
		return maxTryCount;
	}
	
	/**
	 * 设置 最大查询次数, 默认是100次
	 */
	public void setMaxTryCount(Integer maxTryCount) {
		this.maxTryCount = maxTryCount;
	}
	
	/**
	 * 获取 间隔时间， 默认是10秒
	 */
	public Integer getTimeInterval() {
		return timeInterval;
	}
	
	/**
	 * 设置 间隔时间， 默认是10秒
	 */
	public void setTimeInterval(Integer timeInterval) {
		this.timeInterval = timeInterval;
	}
	
	/**
	 * 获取 间隔时间，单位为毫秒，默认值是10000毫秒
	 */
	public Integer getTimeIntervalMs(){
		if (null == this.timeInterval) {
			return 10000;
		}
		return this.timeInterval*1000;
	}
	
	/**
	 * 获取 导入数据库用户名
	 */
	public String getImpUser() {
		return impUser;
	}
	
	/**
	 * 设置 导入数据库用户名
	 */
	public void setImpUser(String impUser) {
		this.impUser = impUser;
	}
	
	/**
	 * 获取 导入数据库密码
	 */
	public String getImpPwd() {
		return impPwd;
	}
	
	/**
	 * 设置 导入数据库密码
	 */
	public void setImpPwd(String impPwd) {
		this.impPwd = impPwd;
	}
	
	/**
	 * 获取 导入数据库IP
	 */
	public String getImpIp() {
		return impIp;
	}
	
	/**
	 * 设置 导入数据库IP
	 */
	public void setImpIp(String impIp) {
		this.impIp = impIp;
	}
	
	/**
	 * 获取分割符
	 */
	public String getSeparate() {
		return separate;
	}
	
	/**
	 * 设置分割符
	 */
	public void setSeparate(String separate) {
		this.separate = separate;
	}

	public String getExpsid() {
		return expsid;
	}

	public void setExpsid(String expsid) {
		this.expsid = expsid;
	}

	public String getImpSid() {
		return impSid;
	}

	public void setImpSid(String impSid) {
		this.impSid = impSid;
	}

	@Override
	public String toString() {
		return "AllBaseDataVo [expUser=" + expUser + ", expPwd=" + expPwd
				+ ", expsid=" + expsid + ", expIp=" + expIp + ", baseTable="
				+ Arrays.toString(baseTable) + ", maxTryCount=" + maxTryCount
				+ ", timeInterval=" + timeInterval + ", impUser=" + impUser
				+ ", impPwd=" + impPwd + ", impSid=" + impSid + ", impIp="
				+ impIp + ", separate=" + separate + "]";
	}

	 
}
