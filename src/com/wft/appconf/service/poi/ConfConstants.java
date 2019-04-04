package com.wft.appconf.service.poi;

import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 * conf excel 常量
 */
public class ConfConstants {

	// 3.5平台公有云平台  支付平台  Spay终端 私有云平台
	public final static String SHEET_1 = "3.5平台公有云平台";
	public final static String SHEET_1_EN = "GYY";
	
	public final static String SHEET_2 = "支付平台";
	public final static String SHEET_2_EN = "ZHIFU";
	
	public final static String SHEET_3 = "Spay终端";
	public final static String SHEET_3_EN = "SPPAY";
	
	public final static String SHEET_4 = "私有云平台";
	public final static String SHEET_4_EN = "SYY";
	
	public final static  String moudle = "moudle";/*"模块分类"*/
	public final static  String project = "project";/*"项目分类"*/
	public final static  String desc = "desc";/*"业务描述"*/
	public final static  String name = "name";/*"服务名称"*/
	public final static  String ip = "ip";/*"应用IP"*/
	public final static  String path = "path";/*"项目部署路径"*/
	public final static  String tname = "tname";/*"包名称"*/
	public final static  String port = "port";/*"应用端口"*/
	
	public final static Map<String,String> EXCLE_SHEET_NAME_MAP = new HashMap<String,String>();
	public final static Map<String,String> EXCLE_COL_MAP = new HashMap<String,String>();
	public final static Map<String,String> TOMCAT_MAP = new HashMap<String,String>();
	
	static{
		EXCLE_SHEET_NAME_MAP.put(SHEET_1,SHEET_1_EN);
		EXCLE_SHEET_NAME_MAP.put(SHEET_2,SHEET_2_EN);
		EXCLE_SHEET_NAME_MAP.put(SHEET_3,SHEET_3_EN);
		EXCLE_SHEET_NAME_MAP.put(SHEET_4,SHEET_4_EN);
		
		
		
		EXCLE_COL_MAP.put("0", moudle/*"模块分类"*/);
		EXCLE_COL_MAP.put("1", project/*"项目分类"*/);
		EXCLE_COL_MAP.put("2",desc /*"业务描述"*/);
		EXCLE_COL_MAP.put("3", name/*"服务名称"*/);
		EXCLE_COL_MAP.put("4", ip/*"应用IP"*/);
		EXCLE_COL_MAP.put("5",path /*"项目部署路径"*/);
		EXCLE_COL_MAP.put("6",tname /*"包名称"*/);
		EXCLE_COL_MAP.put("7", port/*"应用端口"*/);
		
		
		TOMCAT_MAP.put("private-root-conf", "private-root-tomcat");
		TOMCAT_MAP.put("private-acc-conf", "private-acc-tomcat");
		TOMCAT_MAP.put("private-service-conf", "private-service-tomcat");
		
		TOMCAT_MAP.put("public-acc-conf", "public-acc-tomcat");
		TOMCAT_MAP.put("public-root-conf", "public-root-tomcat");
		TOMCAT_MAP.put("public-service-conf", "public-service-tomcat");
		TOMCAT_MAP.put("interface-conf", "interface-tomcat");
		TOMCAT_MAP.put("misc-conf", "misc-tomcat");
		
		TOMCAT_MAP.put("cms-api-conf", "cms-api");
		TOMCAT_MAP.put("cms-spay-conf", "cms-spay");
		TOMCAT_MAP.put("tra-conf", "tra-service");
		TOMCAT_MAP.put("seq-conf", "public-root-tomcat");
		
		TOMCAT_MAP.put("redis-conf", "redis");
		TOMCAT_MAP.put("memcache-conf", "memcache");
		TOMCAT_MAP.put("Oracle-conf", "Oracle");
		
		
		TOMCAT_MAP.put("pay-main-conf", "pay-main");
		TOMCAT_MAP.put("keyserver-conf", "keyserver");
		TOMCAT_MAP.put("pay-api-conf", "pay-api");
		TOMCAT_MAP.put("billno-service-conf", "billno-service");
		TOMCAT_MAP.put("vpay-root-tomcat-conf", "vpay-root-tomcat");
		TOMCAT_MAP.put("vpay-oauth-client-tomca-conf", "vpay-oauth-client-tomcat");
		 
		TOMCAT_MAP.put("spay-manager-admin-tomcat-conf", "spay-manager-admin-tomcat");
		TOMCAT_MAP.put("vpay-oauth-server-tomcat-conf", "vpay-oauth-server-tomcat");
		TOMCAT_MAP.put("vpay-server-tomcat-conf", "vpay-server-tomcat");
		TOMCAT_MAP.put("vpay-pay-server-tomcat-conf", "vpay-pay-server-tomcat");
		TOMCAT_MAP.put("vpay-push-tomcat-conf", "vpay-push-tomcat");
		TOMCAT_MAP.put("spay-manager-service-tomcat", "spay-manager-service-tomcat");
	}
	
}
