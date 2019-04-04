package com.wft.content.dto;

import com.wft.content.dao.CompareKey;

/** 
 * 系统参数表CMS_ORG_PARAMETER_CONF
 * 只比较 parameterName ORG_ID parameterValue
 */
public class SysOrgParameter implements CompareKey {


	
	/** 受理结构ID  */
	
	private String orgId;
	/** 参数名称 */
	private String parameterName;

	/** 参数值 */
	private String parameterValue;


	/** 模块ID MODULE_ID  */
	
	private String moduleId;
	
	private String flds1;
	private String flds2;
	
	
	private String remark;
	
	//比较类型
	private int type = CheckType.NO_EXIST;
	
	//库类型
	private int typeDatabase = CheckType.CAN_KAO_DATABASE;
	
	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
	public int getTypeDatabase() {
		return typeDatabase;
	}

	public void setTypeDatabase(int typeDatabase) {
		this.typeDatabase = typeDatabase;
	}

	
	 
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getFlds1() {
		return flds1;
	}

	public void setFlds1(String flds1) {
		this.flds1 = flds1;
	}

	public String getFlds2() {
		return flds2;
	}

	public void setFlds2(String flds2) {
		this.flds2 = flds2;
	}

	 

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
		result = prime * result
				+ ((parameterName == null) ? 0 : parameterName.hashCode());
		result = prime * result
				+ ((parameterValue == null) ? 0 : parameterValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysOrgParameter other = (SysOrgParameter) obj;
		if (orgId == null) {
			if (other.orgId != null)
				return false;
		} else if (!orgId.equals(other.orgId))
			return false;
		if (parameterName == null) {
			if (other.parameterName != null)
				return false;
		} else if (!parameterName.equals(other.parameterName))
			return false;
		if (parameterValue == null) {
			if (other.parameterValue != null)
				return false;
		} else if (!parameterValue.equals(other.parameterValue))
			return false;
		return true;
	}

	@Override
	public String key() {
		// TODO Auto-generated method stub
		return this.orgId+this.parameterName;
	}

	@Override
	public String toString() {
		return "SysOrgParameter [orgId=" + orgId + ", parameterName="
				+ parameterName + ", parameterValue=" + parameterValue
				+ ", moduleId=" + moduleId + ", flds1=" + flds1 + ", flds2="
				+ flds2 + ", remark=" + remark + ", type=" + type
				+ ", typeDatabase=" + typeDatabase + "]";
	}

	 
	
}
