package com.wft.util;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;


/**
 * 配置
 *
 */
public final class SystemMessage {
 
	public static Properties properties = new Properties();
	
	//本地配置文件
	private static final String BUNDLE_NAME = "application";

	static{
		//开启本地读取模式
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);
		//properties = new Properties();
		Enumeration<String> e = bundle.getKeys();
		while(e.hasMoreElements()){
			String key = e.nextElement();
			properties.put(key, bundle.getString(key));
		}
		 
	}
	
 
	
	public static Map getAllConfig(){
		return properties;
	}
	
	public static String getString(String key) {
		return properties.getProperty(key);
	}
	/**
	 * 获取配置，并将配置值转换为整数
	 * 
	 * @param key
	 * @return
	 */
	public static Integer getInteger(String key) {
		String value = getString(key);
		try {
			return Integer.valueOf(value);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取配置，并将配置值转换为Long类型
	 * 
	 * @param key
	 * @return
	 */
	public static Long getLong(String key) {
		String value = getString(key);
		try {
			return Long.valueOf(value);
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		}
	}

	/**
	 * 获取配置，并将配置值转换为Double类型
	 * 
	 * @param key
	 * @return
	 */
	public static Double getDouble(String key) {
		String value = getString(key);
		try {
			return Double.valueOf(value);
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0d;
		}
	}

	/**
	 * 获取配置，并将配置值转换为Float类型
	 * 
	 * @param key
	 * @return
	 */
	public static Float getFloat(String key) {
		String value = getString(key);
		try {
			return Float.valueOf(value);
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0f;
		}
	}

	/**
	 * 获取配置，并将配置值转换为bool类型
	 * 
	 * @param key
	 * @return
	 */
	public static Boolean getBoolean(String key) {
		String value = getString(key);
		try {
			return Boolean.valueOf(value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取配置，并将配置值转换为BigDecimal类型
	 * 
	 * @param key
	 * @return
	 */
	public static BigDecimal getBigDecimal(String key) {
		String value = getString(key);
		try {
			return new BigDecimal(value);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void main(String[] args) {
		System.out.println(getAllConfig());
	}
	
}

