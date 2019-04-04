package com.wft.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 参数
 */
public class MyParamter  implements Serializable{

	private String pname;
	private String pval;
	private String remark;
	private Date createTime;
	private Date updateTime;
	
	//是否数据库比较中
	public final static String is_compare = "is_compare";
	//最后一次统一升级时间
	public final static String last_unified_upgrade_time = "last_unified_upgrade_time";
	//最后一次上传脚本更新时间
	public final static String last_upload_time = "last_upload_time";
	// 忽视该表信息
	public final static String ignore_table_reg = "ignore_table_reg";
	// 保留表信息
	public final static String need_table_reg = "need_table_reg";
	
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPval() {
		return pval;
	}
	public void setPval(String pval) {
		this.pval = pval;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	@Override
	public String toString() {
		return "MyParamter [pname=" + pname + ", pval=" + pval + ", remark="
				+ remark + ", createTime=" + createTime + ", updateTime="
				+ updateTime + "]";
	}
	 
	 

}
