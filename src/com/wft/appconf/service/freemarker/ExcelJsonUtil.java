package com.wft.appconf.service.freemarker;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FilenameUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wft.appconf.service.poi.ConfConstants;

/**
 * @author admin
 *解析MyBankConf excelJson 字段
 *{"val":{"3.5平台公有云平台":[{"moudle":"3.5平台","project":"快服务","desc":"基础资料查询","name":"cms-api","ip":"99.13.37.151","path":"/opt/app/cms-api","tname":"sppay-cms-war.war","port":"9189"},{"moudle":"3.5平台","project":"快服务","desc":"spay基础资料查询","name":"cms-spay","ip":"99.13.37.151","path":"/opt/app/cms-spay","tname":"sppay-cms-war.war","port":"9195"},{"moudle":"3.5平台","project":"快服务","desc":"终端交易查询","name":"tra-service ","ip":"99.13.37.151","path":"/opt/app/tra-service ","tname":"sppay-tra-service.war.war","port":"9197"},{"moudle":"3.5平台","project":"对账服务","desc":"对账服务","name":"acc-service","ip":"99.13.37.154","path":"/opt/app/acc-service","tname":"sppay-acc-war.war","port":"9191"},{"moudle":"3.5平台","project":"管理服务(慢服务)","desc":"公有云管理前端","name":"cmbchina-root-tomcat","ip":"99.13.47.42","path":"/opt/app/cmbchina-root-tomcat","tname":"ROOT.war","port":"9190"},{"moudle":"3.5平台","project":"管理服务(慢服务)","desc":"公有云server服务","name":"cmb-service-tomcat","ip":"99.13.47.42","path":"/opt/app/cmb-service-tomcat","tname":"sppay-service-war.war","port":"9198"},{"moudle":"3.5平台","project":"管理服务(慢服务)","desc":"公有云接口进件","name":"cmbchina-interface-tomcat","ip":"99.13.47.42","path":"/opt/app/cmbchina-interface-tomcat","tname":"sppay-interface-war.war","port":"9290"},{"moudle":"3.5平台","project":"管理服务(慢服务)","desc":"公有云misc","name":"cmbchina-misc-tomcat","ip":"99.13.47.42","path":"/opt/app/cmbchina-misc-tomcat","tname":"sppay-misc-war.war","port":"9292"},{"moudle":"3.5平台","project":"memcache","desc":"memcache","name":"memcached","ip":"99.13.47.46","path":"","tname":"","port":"6399"},{"moudle":"3.5平台","project":"redis","desc":"redis","name":"redis","ip":"99.13.47.46","path":"","tname":"","port":"11211"},{"moudle":"3.5平台","project":"Oracle","desc":"tstcloud","name":"tstcloud","ip":"99.13.37.147","path":"","tname":"","port":"1521"}],"支付平台":[{"moudle":"支付类","project":"支付网关","desc":"支付网关","name":"pay-main\n","ip":"99.13.37.150","path":"/opt/app/pay-main","tname":"","port":"9103"},{"moudle":"支付类","project":"支付网关","desc":"签名服务","name":"keyserver","ip":"99.13.37.150","path":"/opt/app/keyserver","tname":"","port":"9106"},{"moudle":"支付类","project":"支付网关","desc":"支付统一下载","name":"pay-api","ip":"99.13.37.150","path":"/opt/app/pay-api","tname":"","port":"9105"},{"moudle":"支付类","project":"支付网关","desc":"号段服务","name":"billno-service","ip":"99.13.37.150","path":"/opt/app/billno-service","tname":"billno-service-war.war","port":"9104"}],"Spay终端":[{"moudle":"Spay终端","project":"spay接入层","desc":"前端接口","name":"vpay-root-tomcat","ip":"99.13.37.152","path":"/opt/app/vpay-root-tomcat","tname":"spay.war","port":"9921"},{"moudle":"Spay终端","project":"spay接入层","desc":"授权接口","name":"vpay-oauth-client-tomcat","ip":"99.13.37.152","path":"/opt/app/vpay-oauth-client-tomcat","tname":"spay-oauth-war.war","port":"9960"},{"moudle":"Spay终端","project":"spay接入层","desc":"管理系统","name":"spay-manager-admin-tomcat","ip":"99.13.37.152","path":"/opt/app/spay-manager-admin-tomcat","tname":"sppay-spay-client-war.war","port":"9970"},{"moudle":"Spay终端","project":"spay服务层","desc":"授权服务","name":"vpay-oauth-server-tomcat","ip":"99.13.37.153","path":"/opt/app/vpay-oauth-server-tomcat","tname":"spay-oauth-server-war.war","port":"9981"},{"moudle":"Spay终端","project":"spay服务层","desc":"app服务","name":"vpay-server-tomcat","ip":"99.13.37.153","path":"/opt/app/vpay-server-tomcat","tname":"vpay-service.war.war","port":"9984"},{"moudle":"Spay终端","project":"spay服务层","desc":"支付服务","name":"vpay-pay-server-tomcat","ip":"99.13.37.153","path":"/opt/app/vpay-pay-server-tomcat","tname":"vpay-pay-service.war.war","port":"9985"},{"moudle":"Spay终端","project":"spay服务层","desc":"消息推送","name":"vpay-push-tomcat","ip":"99.13.37.153","path":"/opt/app/vpay-push-tomcat","tname":"vpay-push-war.war","port":"9951"},{"moudle":"Spay终端","project":"spay服务层","desc":"管理系统服务层","name":"spay-manager-service-tomcat","ip":"99.13.37.153","path":"/opt/app/spay-manager-service-tomcat","tname":"sppay-spay-service.war.war","port":"9980"}],"私有云平台":[{"moudle":"私有云","project":"私有云服务","desc":"私有云root","name":"cmbchina-root-tomcat","ip":"99.13.47.43","path":"/opt/app/cmbchina-root-tomcat","tname":"ROOT.war","port":"8880"},{"moudle":"私有云","project":"私有云服务","desc":"私有云acc","name":"cmbchina-acc-tomcat","ip":"99.13.47.43","path":"/opt/app/cmbchina-acc-tomcat","tname":"sppay-acc-war.war","port":"8882"},{"moudle":"私有云","project":"私有云服务","desc":"私有云service","name":"cmbchina-service-tomcat","ip":"99.13.47.43","path":"/opt/app/cmbchina-service-tomcat","tname":"sppay-service-war.war","port":"8881"},{"moudle":"私有云","project":"memcache","desc":"memcache","name":"memcached","ip":"99.13.47.46","path":"","tname":"","port":"6399"},{"moudle":"私有云","project":"redis","desc":"redis","name":"redis","ip":"99.13.47.46","path":"","tname":"","port":"11211"},{"moudle":"私有云","project":"Oracle","desc":"Oracle","name":"tstcloud","ip":"99.13.37.147","path":"","tname":"","port":"1521"}]}}
 */ 
@SuppressWarnings("unchecked")
public class ExcelJsonUtil {

	
	public static void parseExcelmemcacheJson(Map<String,Object> resultmap,String tplPath,String sheet_name){
		 
		
		Map<String,Object> cresultmap = (Map<String, Object>) resultmap.get(ConfConstants.EXCLE_SHEET_NAME_MAP.get(sheet_name) );
		for(Entry<String, Object>  en:cresultmap.entrySet()){
			AppConfDto appConfDto = (AppConfDto) en.getValue();
			
			if(tplPath.equals(appConfDto.getName().trim())){
	    		//替换log4j2.xml
	        	resultmap.put("path", appConfDto.getPath());
	        	 
	    	}
			if(sheet_name.contains(appConfDto.getMoudle())&&"memcache".equals(appConfDto.getName())){
				//替换cache.properties
	        	resultmap.put("memcache", appConfDto);
	         
			}
		}
	 
	}
	public static Map<String,Object> parseExcelJson(String excelJson){
		Map<String,Object> resultmap = new HashMap<String,Object>();
	
		JSONObject jsonObject = JSONObject.parseObject(excelJson);
		jsonObject = jsonObject.getJSONObject("val");
		// 3.5平台公有云平台  支付平台  Spay终端 私有云平台
		Map<String,Object> cresultmap1 = new HashMap<String,Object>();
		JSONArray jSONArray =  jsonObject.getJSONArray(ConfConstants.SHEET_1);
		for(int i=0,len=jSONArray.size();i<len;i++){
			JSONObject cjsonObject = jSONArray.getJSONObject(i);
			String moudle = cjsonObject.getString(ConfConstants.moudle);/*"模块分类"*/
			String project =  cjsonObject.getString(ConfConstants.project);/*"项目分类"*/
			String desc =  cjsonObject.getString(ConfConstants.desc);/*"业务描述"*/
			String name = cjsonObject.getString(ConfConstants.name);/*"服务名称"*/
			String ip =  cjsonObject.getString(ConfConstants.ip);/*"应用IP"*/
			String path = cjsonObject.getString(ConfConstants.path) ;/*"项目部署路径"*/
			String tname =  cjsonObject.getString(ConfConstants.tname);/*"包名称"*/
			String port =  cjsonObject.getString(ConfConstants.port);/*"应用端口"*/
			AppConfDto appConfDto = new AppConfDto();
	    	appConfDto.setSheet(ConfConstants.SHEET_1_EN);
	    	appConfDto.setDesc(desc);
	    	appConfDto.setIp(ip);
	    	appConfDto.setName(name);
	    	appConfDto.setMoudle(moudle);
	    	appConfDto.setPath(path);
	    	appConfDto.setPort(port);
	    	appConfDto.setProject(project);
	    	appConfDto.setTname(FilenameUtils.getBaseName(tname) );
	    	cresultmap1.put(name, appConfDto);
	    	
		}
		Map<String,Object> cresultmap2 = new HashMap<String,Object>();
		jSONArray =  jsonObject.getJSONArray(ConfConstants.SHEET_2);
		for(int i=0,len=jSONArray.size();i<len;i++){
			JSONObject cjsonObject = jSONArray.getJSONObject(i);
			String moudle = cjsonObject.getString(ConfConstants.moudle);/*"模块分类"*/
			String project =  cjsonObject.getString(ConfConstants.project);/*"项目分类"*/
			String desc =  cjsonObject.getString(ConfConstants.desc);/*"业务描述"*/
			String name = cjsonObject.getString(ConfConstants.name);/*"服务名称"*/
			String ip =  cjsonObject.getString(ConfConstants.ip);/*"应用IP"*/
			String path = cjsonObject.getString(ConfConstants.path) ;/*"项目部署路径"*/
			String tname =  cjsonObject.getString(ConfConstants.tname);/*"包名称"*/
			String port =  cjsonObject.getString(ConfConstants.port);/*"应用端口"*/
			AppConfDto appConfDto = new AppConfDto();
	    	appConfDto.setSheet(ConfConstants.SHEET_2_EN);
	    	appConfDto.setDesc(desc);
	    	appConfDto.setIp(ip);
	    	appConfDto.setName(name);
	    	appConfDto.setMoudle(moudle);
	    	appConfDto.setPath(path);
	    	appConfDto.setPort(port);
	    	appConfDto.setProject(project);
	    	appConfDto.setTname(FilenameUtils.getBaseName(tname) );
	    	cresultmap2.put(name, appConfDto);
	    	 
		}
		Map<String,Object> cresultmap3 = new HashMap<String,Object>();
		jSONArray =  jsonObject.getJSONArray(ConfConstants.SHEET_3);
		for(int i=0,len=jSONArray.size();i<len;i++){
			JSONObject cjsonObject = jSONArray.getJSONObject(i);
			String moudle = cjsonObject.getString(ConfConstants.moudle);/*"模块分类"*/
			String project =  cjsonObject.getString(ConfConstants.project);/*"项目分类"*/
			String desc =  cjsonObject.getString(ConfConstants.desc);/*"业务描述"*/
			String name = cjsonObject.getString(ConfConstants.name);/*"服务名称"*/
			String ip =  cjsonObject.getString(ConfConstants.ip);/*"应用IP"*/
			String path = cjsonObject.getString(ConfConstants.path) ;/*"项目部署路径"*/
			String tname =  cjsonObject.getString(ConfConstants.tname);/*"包名称"*/
			String port =  cjsonObject.getString(ConfConstants.port);/*"应用端口"*/
			AppConfDto appConfDto = new AppConfDto();
	    	appConfDto.setSheet(ConfConstants.SHEET_3_EN);
	    	appConfDto.setDesc(desc);
	    	appConfDto.setIp(ip);
	    	appConfDto.setName(name);
	    	appConfDto.setMoudle(moudle);
	    	appConfDto.setPath(path);
	    	appConfDto.setPort(port);
	    	appConfDto.setProject(project);
	    	appConfDto.setTname(FilenameUtils.getBaseName(tname) );
	    	cresultmap3.put(name, appConfDto);
	    	 
		}
		Map<String,Object> cresultmap4 = new HashMap<String,Object>();
		jSONArray =  jsonObject.getJSONArray(ConfConstants.SHEET_4);
		for(int i=0,len=jSONArray.size();i<len;i++){
			JSONObject cjsonObject = jSONArray.getJSONObject(i);
			String moudle = cjsonObject.getString(ConfConstants.moudle);/*"模块分类"*/
			String project =  cjsonObject.getString(ConfConstants.project);/*"项目分类"*/
			String desc =  cjsonObject.getString(ConfConstants.desc);/*"业务描述"*/
			String name = cjsonObject.getString(ConfConstants.name);/*"服务名称"*/
			String ip =  cjsonObject.getString(ConfConstants.ip);/*"应用IP"*/
			String path = cjsonObject.getString(ConfConstants.path) ;/*"项目部署路径"*/
			String tname =  cjsonObject.getString(ConfConstants.tname);/*"包名称"*/
			String port =  cjsonObject.getString(ConfConstants.port);/*"应用端口"*/
			AppConfDto appConfDto = new AppConfDto();
	    	appConfDto.setSheet(ConfConstants.SHEET_4_EN);
	    	appConfDto.setDesc(desc);
	    	appConfDto.setIp(ip);
	    	appConfDto.setName(name);
	    	appConfDto.setMoudle(moudle);
	    	appConfDto.setPath(path);
	    	appConfDto.setPort(port);
	    	appConfDto.setProject(project);
	    	appConfDto.setTname(FilenameUtils.getBaseName(tname) );
	    	cresultmap4.put(name, appConfDto);
	    	 
		}
		 
    	//替换app-config.properties
		resultmap.put(ConfConstants.SHEET_1_EN, cresultmap1);
    	resultmap.put(ConfConstants.SHEET_2_EN, cresultmap2);
    	resultmap.put(ConfConstants.SHEET_3_EN, cresultmap3);
    	resultmap.put(ConfConstants.SHEET_4_EN, cresultmap4);
    	resultmap.put("platform", "001");
    	
    	 
		return resultmap;
	}
	
	 
}
