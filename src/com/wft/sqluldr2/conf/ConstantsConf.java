package com.wft.sqluldr2.conf;

import java.io.File;


/**
 * 读取配置文件实例
 * @author yunfeng.zhou
 *
 */
public class ConstantsConf {
	
	/**
	 * config.xml配置文件
	 */
	public final static String PATH_CONFIG_XML = ConstantsTools.CURRENT_DIR + File.separator + "sqluldr2" + File.separator + "config.xml";
	
	/**
	 * sqlldr-config.xml配置文件
	 */
	public final static String PATH_CONFIG_SQLLDR = ConstantsTools.CURRENT_DIR + File.separator + "sqluldr2" + File.separator + "sqlldr-config.xml";
	
	/**
	 * sqluldr-config.xml配置文件
	 */
	public final static String PATH_CONFIG_SQLULDR = ConstantsTools.CURRENT_DIR + File.separator + "sqluldr2" + File.separator + "sqluldr-config.xml";
	
	/**
	 * zh-cn.properties配置文件
	 */
	public final static String PATH_CONFIG_PRO_LAN = ConstantsTools.CURRENT_DIR + File.separator + "sqluldr2" + File.separator + "zh-cn.properties";
	public static void main(String[] args) {
		System.out.println(PATH_CONFIG_SQLULDR);
	}
}
