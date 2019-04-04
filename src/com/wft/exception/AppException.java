package com.wft.exception;

/**
 * @author admin
 * 业务异常
 */
public class AppException extends  RuntimeException   {
	private static final long serialVersionUID = 1L;

	public AppException(String paramString) {
		super(paramString);
	}
}
