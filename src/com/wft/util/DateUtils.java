package com.wft.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtils {

	/**
	 * 
	 * @return 格式化
	 */
	public static String fmtDate(String fmt) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		java.util.Date d = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.format(d);

	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 得到当前日期
	 */
	public static String getCurrentTime() {

		return fmtDate("yyyy-MM-dd HH:mm:ss");

	}

	/**
	 * yyyy-MM-dd-HH-mm-ss
	 * 
	 * @return 得到当前日期
	 */
	public static String getCurrentTimeFileName() {

		return fmtDate("yyyy-MM-dd-HH-mm-ss");

	}
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 得到指定日期
	 */
	public static String getTime(String ls) {
		Calendar c = Calendar.getInstance();
		try {
			long ll = Long.valueOf(ls);
			c.setTimeInMillis(ll);
		} catch (Exception e) {
			c.setTimeInMillis(System.currentTimeMillis());
		}

		java.util.Date d = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(d);

	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 得到当前日期
	 */
	public static Date formdate(String source) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			if (StringUtils.isNotBlank(source)) {
				date = sdf.parse(source);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return date;

	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 得到当前日期
	 */
	public static String formdate(Date source) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (source != null) {
				return sdf.format(source);
			} else {
				Date date = new Date();
				return sdf.format(date);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}

	public static int compareDate(Date dt1, Date dt2) {

		if (dt1.getTime() > dt2.getTime()) {
			return 1;
		} else if (dt1.getTime() < dt2.getTime()) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * 获取指定日期的开始时间
	 * 
	 * @param
	 * @return
	 * @throw
	 * @date:2016-04-09
	 */
	public static Date getStartTime(Date date) {
		return getCalendar(date, 0, 0, 0, 0).getTime();
	}

	/**
	 * 获取指定日期的结束时间
	 * 
	 * @param
	 * @return
	 * @throw
	 * @date:2016-04-09
	 */
	public static Date getEndTime(Date date) {
		return getCalendar(date, 23, 59, 59, 999).getTime();
	}

	/**
	 * 
	 * @param date
	 *            日期
	 * @param hourOfDay
	 *            时
	 * @param minute
	 *            分
	 * @param second
	 *            秒
	 * @param millisecond
	 *            毫秒
	 * @return
	 */
	public static Calendar getCalendar(Date date, int hourOfDay, int minute,
			int second, int millisecond) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, millisecond);
		return cal;
	}

	public static void main(String[] args) {
		System.out.println(fmtDate("yyyy-MM-dd"));
	}
}
