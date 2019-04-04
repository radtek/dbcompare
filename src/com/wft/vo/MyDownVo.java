package com.wft.vo;

import com.wft.util.CommonUtil;

/**
 * @author admin 
 * 下载记录
 */
public class MyDownVo {
 
	private String sechma;
 
	private int physicsFlag = CommonUtil.EABLE;

	public String getSechma() {
		return sechma;
	}

	public void setSechma(String sechma) {
		this.sechma = sechma;
	}

	public int getPhysicsFlag() {
		return physicsFlag;
	}

	public void setPhysicsFlag(int physicsFlag) {
		this.physicsFlag = physicsFlag;
	}
	 
 

}
