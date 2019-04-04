package com.wft.sqluldr2.util;

import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;

/**
 * 时间工具类
 * 
 * @author yunfeng.zhou
 */
public class DateUtil {
	
	/** 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss */
	public final static String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 格式化日期时间<br>
	 * 格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date 被格式化的日期
	 * @return 格式化后的日期
	 */
	public static String formatDate(Date date) {
		if (null == date) {
			return null;
		}
		return FastDateFormat.getInstance(NORM_DATETIME_PATTERN).format(date);
	}
	
	/**
	 * 当前时间，格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 当前时间的标准形式字符串
	 */
	public static String now() {
		return formatDate(new Date());
	}
	
	
	
}
