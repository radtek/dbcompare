package com.wft.wxalarm;

import org.apache.commons.lang.StringUtils;


/**
 * 公众号消息发送后的返回对象
 * @author lisheng
 * @date 2017-08-17
 * 
 */
public class AlarmRespDto {

	/**
	 * 错误码，0表示成功
	 */
	private String errcode;
	
	/**
	 * 错误信息，ok表示成功
	 */
	private String errmsg;
	
	/**
	 * 收消息人员的校验结果
	 */
	private String invaliduser;

	/**
	 * 收消息部门的校验结果
	 */
	private String invalidparty;
	
	/**
	 * 消息tag的校验结果
	 */
	private String invalidtag;

	
	@Override
	public String toString() {
		return "AlarmRespDto [errcode=" + errcode + ", errmsg=" + errmsg
				+ ", invaliduser=" + invaliduser + ", invalidparty="
				+ invalidparty + ", invalidtag=" + invalidtag + "]";
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	
	public boolean isSuccess() {
		if (StringUtils.isNotEmpty(errcode)) {
			return StringUtils.equals(errcode, "0");
		}
		return false;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getInvaliduser() {
		return invaliduser;
	}

	public void setInvaliduser(String invaliduser) {
		this.invaliduser = invaliduser;
	}

	public String getInvalidparty() {
		return invalidparty;
	}

	public void setInvalidparty(String invalidparty) {
		this.invalidparty = invalidparty;
	}

	public String getInvalidtag() {
		return invalidtag;
	}

	public void setInvalidtag(String invalidtag) {
		this.invalidtag = invalidtag;
	}
}