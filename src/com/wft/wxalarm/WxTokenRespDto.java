package com.wft.wxalarm;


/**
 * 公众号token返回对象
 * @author lisheng
 * @date 2017-08-17
 * 
 */
public class WxTokenRespDto {

	/**
	 * 错误码，如果值为0则表示:正常、成功
	 * 具体的错误码请到http://qydev.weixin.qq.com里查询
	 */
	private int errcode;
	
	/**
	 * 错误信息，如果值为ok则表示:正常、成功
	 */
	private String errmsg;
	
	/**
	 * 生成的token
	 */
	private String access_token;
	
	/**
	 * 该token的有效访问时间（单位秒，缺省是7200）
	 */
	private int expires_in;

	@Override
	public String toString() {
		return "WxTokenDto [errcode=" + errcode + ", errmsg=" + errmsg
				+ ", access_token=" + access_token + ", expires_in="
				+ expires_in + "]";
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
}