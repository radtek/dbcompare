package com.wft.content.dto;

import com.wft.content.dao.CompareKey;

/** 
 * 系统参数表CMS_SYS_PARAMETER
 * 只比较 parameterName parameterValue
 */
public class SysParameter implements CompareKey {


	/** 参数名称 */
	private String parameterName;

	/** 参数值 */
	private String parameterValue;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		SysParameter other = (SysParameter) obj;
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
		return this.parameterName;
	}

	@Override
	public String toString() {
		return "SysParameter [parameterName=" + parameterName
				+ ", parameterValue=" + parameterValue + ", remark=" + remark
				+ ", type=" + type + ", typeDatabase=" + typeDatabase + "]";
	}
	
	
}
