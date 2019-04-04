package com.wft.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wft.db.DBTempory;
import com.wft.model.MybasicBank;
import com.wft.service.MyBasicBankService;
 
public class ScriptBatchTest {
	
	 
	
	public static void main(String[] args) throws Exception {
	  ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("application.xml");
	 
		MyBasicBankService myBasicBankService = ac.getBean("myBasicBankService", MyBasicBankService.class);
	 
		//testcsvBankCheck(myBasicBankService);
		/*testCMS_SYS_PARAMETER(myBasicBankService);
		testTRA_PAY_API(myBasicBankService);
		testTRA_PAY_CENTER(myBasicBankService);
		testTRA_PAY_TYPE(myBasicBankService);
		testTCMS_ORG_PARAMETER_CONF(myBasicBankService);*/
		testCMS_CHANNEL(myBasicBankService);
		//testCLE_ACC_PLAN_CONF(myBasicBankService);
		ac.destroy();
		ac.close(); 
		
	 
	}
    private static void testCLE_ACC_PLAN_CONF(MyBasicBankService myBasicBankService) throws Exception {
		
		List<MybasicBank> mybasicBanks = myBasicBankService.findCHeck();
		 int i = 0;
		 int max = 1;
		 System.out.println("==============start=================");
		for(MybasicBank mybasicBank:mybasicBanks){
			if(i>max)
				//break;
			i++;
			String url = mybasicBank.getUrl();
			String pwd = mybasicBank.getPwd();
			String name = mybasicBank.getUserName();
			String schame = mybasicBank.getSechma();
			url = "jdbc:oracle:thin:@"+url;
			
		
		
			
			//String sql= "select count(1) as cnt       from "+schame+ ".cms_sys_type t  where t.TYPE_CLASS = 'API_PROVIDER'    and t.TYPE_CODE = '8'";
			  
			try {
				DBTempory dbTempory = new DBTempory(url,name,pwd,name);
				File pfile = new File("D:/公司资料/test/测试维护库/受理机构信息维护/"+schame);
				if(!pfile.exists()){
					FileUtils.forceMkdir(pfile);
				}
				//String sql= "select count(1)  AMOUNT from  "+schame+ ".CLE_CLEANING_PROCESS_CONF t ";
				//String sql= "select  parameter_value CNT   from "+schame+ ".cms_sys_parameter where parameter_name='CLEANING_CHANNEL_WFT_ID' ";
				 //String sql= "select  count(MODULE_ID)  CNT   from "+schame+ ".CMS_module where MODULE_NAME='代付管理'";
				// String sql= "select  t.RETURN_CODE  CNT   from "+schame+ ".CLE_CLEANING_BILL where  ROWNUM=1";
				 // String sql= "select u.USER_ID ,u.USER_NAME,u.CREATE_TIME,u.CREATE_EMP ,'受理机构超级管理员' as ROLE_NAME from "+schame+ ".CMS_USER u join  "+schame+ ".CMS_USER_ROLE r on (u.USER_ID=r.USER_ID) where r.role_id=(select ROLE_ID from "+schame+ ".CMS_ROLE where ROLE_NAME='受理机构超级管理员')";
				String sql= "SELECT	* from "+schame+ ".CLE_ACC_PLAN_CONF   where physics_flag = 1";
				List<String> lines = new ArrayList<String>();
				lines.add("set define off;");
				lines.add("truncate table "+schame+ ".CLE_ACC_PLAN_CONF ;");
				lines.add("truncate table "+schame+ ".CLE_CLEANING_PROCESS_CONF ;");
				lines.add("truncate table "+schame+ ".cle_bill_merge_conf ;");
				 List<Map<String,String>> nulllogllist = dbTempory.getDatasByTable(sql);
				 if(nulllogllist.isEmpty()){
					  System.out.println(schame+":没有");
				 }else{
					 for(Map<String,String> map:nulllogllist){
						 System.out.println(schame+":"+map);
						 StringBuffer ss  = new StringBuffer();
						 StringBuffer header  = new StringBuffer("(");
						 StringBuffer fail  = new StringBuffer("(");
						 for(Entry<String, String> en: map.entrySet()){
							 String key = en.getKey();
							 String value = en.getValue();
							 if("null".equalsIgnoreCase(value)||StringUtils.isBlank(value)){
								 continue;
							 }
							 if(key.contains("TIME")){
								 value = "sysdate";
								 fail.append(""+value+" ,");
							 }else{
								 fail.append("'"+value+"' ,");
							 }
							 
							 header.append(key+" ,");
							
						 }
						 header.deleteCharAt(header.length()-1).append(")");
						 fail.deleteCharAt(fail.length()-1).append(")");
						 ss.append("INSERT INTO ").append(schame).append(".CLE_ACC_PLAN_CONF").append(header).append(" VALUES ").append(fail).append(";");
						 System.out.println(":"+ss);
						 lines.add(ss.toString());
					 }
					 
				 }
				 lines.add(""); lines.add("");
				sql= "SELECT	* from "+schame+ ".CLE_CLEANING_PROCESS_CONF   where physics_flag = 1";
				 nulllogllist = dbTempory.getDatasByTable(sql);
				 if(nulllogllist.isEmpty()){
					  System.out.println(schame+":没有");
				 }else{
					 for(Map<String,String> map:nulllogllist){
						 System.out.println(schame+":"+map);
						 StringBuffer ss  = new StringBuffer();
						 StringBuffer header  = new StringBuffer("(");
						 StringBuffer fail  = new StringBuffer("(");
						 for(Entry<String, String> en: map.entrySet()){
							 String key = en.getKey();
							 String value = en.getValue();
							 if("null".equalsIgnoreCase(value)||StringUtils.isBlank(value)){
								 continue;
							 }
							 if(key.contains("TIME")){
								 value = "sysdate";
								 fail.append(""+value+" ,");
							 }else{
								 fail.append("'"+value+"' ,");
							 }
							 
							 header.append(key+" ,");
							
						 }
						 header.deleteCharAt(header.length()-1).append(")");
						 fail.deleteCharAt(fail.length()-1).append(")");
						 ss.append("INSERT INTO ").append(schame).append(".CLE_CLEANING_PROCESS_CONF").append(header).append(" VALUES ").append(fail).append(";");
						 System.out.println(":"+ss);
						 lines.add(ss.toString());
					 }
					 
				 }
				 
				 sql= "SELECT	* from "+schame+ ".cle_bill_merge_conf   where physics_flag = 1";
				 nulllogllist = dbTempory.getDatasByTable(sql);
				 if(nulllogllist.isEmpty()){
					  System.out.println(schame+":没有");
				 }else{
					 for(Map<String,String> map:nulllogllist){
						 System.out.println(schame+":"+map);
						 StringBuffer ss  = new StringBuffer();
						 StringBuffer header  = new StringBuffer("(");
						 StringBuffer fail  = new StringBuffer("(");
						 for(Entry<String, String> en: map.entrySet()){
							 String key = en.getKey();
							 String value = en.getValue();
							 if("null".equalsIgnoreCase(value)||StringUtils.isBlank(value)){
								 continue;
							 }
							 if(key.contains("TIME")){
								 value = "sysdate";
								 fail.append(""+value+" ,");
							 }else{
								 fail.append("'"+value+"' ,");
							 }
							 
							 header.append(key+" ,");
							
						 }
						 header.deleteCharAt(header.length()-1).append(")");
						 fail.deleteCharAt(fail.length()-1).append(")");
						 ss.append("INSERT INTO ").append(schame).append(".cle_bill_merge_conf").append(header).append(" VALUES ").append(fail).append(";");
						 System.out.println(":"+ss);
						 lines.add(ss.toString());
					 }
					 
				 }
				 lines.add("commit;");lines.add("exit;");
				 FileUtils.writeLines(new File(pfile.getAbsoluteFile()+"/07.dml_CLE_ACC_PLAN_CONF.sql"),"gb2312", lines);
				// System.out.println(nulllogllist);
			} catch (Exception e) {
				//e.printStackTrace();
				continue;
			}
			
			 System.out.println("====================schame====================================="+schame);
			
		}
		//FileUtils.writeLines(new File("D:/公司资料/test/3.5标准版/受理机构用户帐号梳理/bank.csv"),"GBK", lines);
		System.out.println("down");
	}
 
private static void testCMS_CHANNEL(MyBasicBankService myBasicBankService) throws Exception {
		
		List<MybasicBank> mybasicBanks = myBasicBankService.findCHeck();
		 int i = 0;
		 int max = 1;
		 System.out.println("==============start=================");
		for(MybasicBank mybasicBank:mybasicBanks){
			if(i>max)
				//break;
			i++;
			String url = mybasicBank.getUrl();
			String pwd = mybasicBank.getPwd();
			String name = mybasicBank.getUserName();
			String schame = mybasicBank.getSechma();
			url = "jdbc:oracle:thin:@"+url;
			if(!"shisircb35".equalsIgnoreCase(schame)){
				continue;
			}
		
		
			
			//String sql= "select count(1) as cnt       from "+schame+ ".cms_sys_type t  where t.TYPE_CLASS = 'API_PROVIDER'    and t.TYPE_CODE = '8'";
			  
			try {
				DBTempory dbTempory = new DBTempory(url,name,pwd,name);
				File pfile = new File("D:/公司资料/test/测试维护库/受理机构信息维护/"+schame);
				if(!pfile.exists()){
					FileUtils.forceMkdir(pfile);
				}
				//String sql= "select count(1)  AMOUNT from  "+schame+ ".CLE_CLEANING_PROCESS_CONF t ";
				//String sql= "select  parameter_value CNT   from "+schame+ ".cms_sys_parameter where parameter_name='CLEANING_CHANNEL_WFT_ID' ";
				 //String sql= "select  count(MODULE_ID)  CNT   from "+schame+ ".CMS_module where MODULE_NAME='代付管理'";
				// String sql= "select  t.RETURN_CODE  CNT   from "+schame+ ".CLE_CLEANING_BILL where  ROWNUM=1";
				 // String sql= "select u.USER_ID ,u.USER_NAME,u.CREATE_TIME,u.CREATE_EMP ,'受理机构超级管理员' as ROLE_NAME from "+schame+ ".CMS_USER u join  "+schame+ ".CMS_USER_ROLE r on (u.USER_ID=r.USER_ID) where r.role_id=(select ROLE_ID from "+schame+ ".CMS_ROLE where ROLE_NAME='受理机构超级管理员')";
				String sql= "SELECT	* from "+schame+ ".CMS_CHANNEL where CHANNEL_TYPE = 1 and rownum = 1 UNION SELECT	* from  "+schame+ ".CMS_CHANNEL where  channel_id=(SELECT	wft_channel_id from  "+schame+ ".CLE_ACC_PLAN_CONF   where physics_flag = 1 AND ROWNUM = 1) UNION SELECT	* from  "+schame+ ".CMS_CHANNEL where parent_channel = (SELECT	CHANNEL_ID from  "+schame+ ".CMS_CHANNEL where CHANNEL_TYPE = 1 and rownum = 1)";
				List<String> lines = new ArrayList<String>();
				List<String> userids = new ArrayList<String>();
				StringBuffer resuts = new StringBuffer("(");
				lines.add("set define off;");
				lines.add("truncate table "+schame+ ".CMS_CHANNEL ;");
				lines.add("truncate table "+schame+ ".cms_app ;");
				 List<Map<String,String>> nulllogllist = dbTempory.getDatasByTable(sql);
				 if(nulllogllist.isEmpty()){
					  System.out.println(schame+":没有");
				 }else{
					 for(Map<String,String> map:nulllogllist){
						 System.out.println(schame+":"+map);
						 resuts.append("'"+map.get("CHANNEL_ID")+"',");
						// channids.add(map.get("CHANNEL_ID"));
						 StringBuffer ss  = new StringBuffer();
						 StringBuffer header  = new StringBuffer("(");
						 StringBuffer fail  = new StringBuffer("(");
						 for(Entry<String, String> en: map.entrySet()){
							 String key = en.getKey();
							 String value = en.getValue();
							 if("null".equalsIgnoreCase(value)||StringUtils.isBlank(value)){
								 continue;
							 }
							 //数据脱密
							 if("TEL".equalsIgnoreCase(key) ){
								 value = "18128866224";
							 }
							 if("PRINCIPAL".equalsIgnoreCase(key) ){
								 value = "test";
							 }
							 if("EMAIL".equalsIgnoreCase(key) ){
								 value = "zhen.lin@swiftpass.cn";
							 }
							 if(key.contains("TIME")){
								 value = "sysdate";
								 fail.append(""+value+" ,");
							 }else{
								 fail.append("'"+value+"' ,");
							 }
							 
							 header.append(key+" ,");
							
						 }
						 header.deleteCharAt(header.length()-1).append(")");
						 fail.deleteCharAt(fail.length()-1).append(")");
						 ss.append("INSERT INTO ").append(schame).append(".CMS_CHANNEL").append(header).append(" VALUES ").append(fail).append(";");
						 System.out.println(":"+ss);
						 lines.add(ss.toString());
					 }
					 resuts.deleteCharAt(resuts.length()-1).append(")");
				 }
				 lines.add(""); lines.add("");
				 sql= "SELECT	* from "+schame+ ".cms_org_relation   where ORG_ID in "+resuts;
				 nulllogllist = dbTempory.getDatasByTable(sql);
				 if(nulllogllist.isEmpty()){
					  System.out.println(schame+":没有");
				 }else{
					 for(Map<String,String> map:nulllogllist){
						 System.out.println(schame+":"+map);
						
						 StringBuffer ss  = new StringBuffer();
						 StringBuffer header  = new StringBuffer("(");
						 StringBuffer fail  = new StringBuffer("(");
						 for(Entry<String, String> en: map.entrySet()){
							 String key = en.getKey();
							 String value = en.getValue();
							 if("null".equalsIgnoreCase(value)||StringUtils.isBlank(value)){
								 continue;
							 }
							 if(key.contains("TIME")){
								 value = "sysdate";
								 fail.append(""+value+" ,");
							 }else{
								 fail.append("'"+value+"' ,");
							 }
							 
							 header.append(key+" ,");
							
						 }
						 header.deleteCharAt(header.length()-1).append(")");
						 fail.deleteCharAt(fail.length()-1).append(")");
						 ss.append("INSERT INTO ").append(schame).append(".cms_org_relation").append(header).append(" VALUES ").append(fail).append(";");
						 System.out.println(":"+ss);
						 lines.add(ss.toString());
					 }
					 
				 }
				 
				 sql= "SELECT	* from "+schame+ ".cms_user   where USER_NAME in "+resuts;
				 nulllogllist = dbTempory.getDatasByTable(sql);
				 if(nulllogllist.isEmpty()){
					  System.out.println(schame+":没有");
				 }else{
					 for(Map<String,String> map:nulllogllist){
						 System.out.println(schame+":"+map);
						 userids.add(map.get("USER_ID"));
						 StringBuffer ss  = new StringBuffer();
						 StringBuffer header  = new StringBuffer("(");
						 StringBuffer fail  = new StringBuffer("(");
						 for(Entry<String, String> en: map.entrySet()){
							 String key = en.getKey();
							 String value = en.getValue();
							 if("null".equalsIgnoreCase(value)||StringUtils.isBlank(value)){
								 continue;
							 }
							 if(key.contains("TIME")){
								 value = "sysdate";
								 fail.append(""+value+" ,");
							 }else{
								 fail.append("'"+value+"' ,");
							 }
							 
							 header.append(key+" ,");
							
						 }
						 header.deleteCharAt(header.length()-1).append(")");
						 fail.deleteCharAt(fail.length()-1).append(")");
						 ss.append("INSERT INTO ").append(schame).append(".cms_user").append(header).append(" VALUES ").append(fail).append(";");
						 System.out.println(":"+ss);
						 lines.add(ss.toString());
					 }
					 
				 }
				 for(String chanid:userids){
					 lines.add("insert into  "+schame+".cms_user_role (USER_ID, ROLE_ID, CREATE_EMP, CREATE_TIME) values ('"+chanid+"', 1, null, sysdate);");
					 lines.add("insert into  "+schame+".cms_app_user (APP_ID, USER_ID, CREATE_TIME) values (1, '"+chanid+"', sysdate);");
					 
				 }
				 
				 sql= "SELECT	* from "+schame+ ".cms_app    ";
				 nulllogllist = dbTempory.getDatasByTable(sql);
				 if(nulllogllist.isEmpty()){
					  System.out.println(schame+":没有");
				 }else{
					 for(Map<String,String> map:nulllogllist){
						 System.out.println(schame+":"+map);
						
						 StringBuffer ss  = new StringBuffer();
						 StringBuffer header  = new StringBuffer("(");
						 StringBuffer fail  = new StringBuffer("(");
						 for(Entry<String, String> en: map.entrySet()){
							 String key = en.getKey();
							 String value = en.getValue();
							 if("null".equalsIgnoreCase(value)||StringUtils.isBlank(value)){
								 continue;
							 }
							 if(key.contains("TIME")){
								 value = "sysdate";
								 fail.append(""+value+" ,");
							 }else{
								 fail.append("'"+value+"' ,");
							 }
							 
							 header.append(key+" ,");
							
						 }
						 header.deleteCharAt(header.length()-1).append(")");
						 fail.deleteCharAt(fail.length()-1).append(")");
						 ss.append("INSERT INTO ").append(schame).append(".cms_app").append(header).append(" VALUES ").append(fail).append(";");
						 System.out.println(":"+ss);
						 lines.add(ss.toString());
					 }
					 
				 }
				 lines.add("update "+schame+".cms_app set app_url='https://admin60.swiftpass.cn/loginUI' where 1=1;");
				 lines.add("commit;");lines.add("exit;");
				 FileUtils.writeLines(new File(pfile.getAbsoluteFile()+"/06.dml_CMS_CHANNEL.sql"),"gb2312", lines);
				// System.out.println(nulllogllist);
			} catch (Exception e) {
				//e.printStackTrace();
				continue;
			}
			
			 System.out.println("====================schame====================================="+schame);
			
		}
		//FileUtils.writeLines(new File("D:/公司资料/test/3.5标准版/受理机构用户帐号梳理/bank.csv"),"GBK", lines);
		System.out.println("down");
	}
private static void testTCMS_ORG_PARAMETER_CONF(MyBasicBankService myBasicBankService) throws Exception {
		
		List<MybasicBank> mybasicBanks = myBasicBankService.findCHeck();
		 int i = 0;
		 int max = 1;
		 System.out.println("==============start=================");
		for(MybasicBank mybasicBank:mybasicBanks){
			if(i>max)
				//break;
			i++;
			String url = mybasicBank.getUrl();
			String pwd = mybasicBank.getPwd();
			String name = mybasicBank.getUserName();
			String schame = mybasicBank.getSechma();
			url = "jdbc:oracle:thin:@"+url;
			
		
		
			
			//String sql= "select count(1) as cnt       from "+schame+ ".cms_sys_type t  where t.TYPE_CLASS = 'API_PROVIDER'    and t.TYPE_CODE = '8'";
			  
			try {
				DBTempory dbTempory = new DBTempory(url,name,pwd,name);
				File pfile = new File("D:/公司资料/test/测试维护库/受理机构信息维护/"+schame);
				if(!pfile.exists()){
					FileUtils.forceMkdir(pfile);
				}
				//String sql= "select count(1)  AMOUNT from  "+schame+ ".CLE_CLEANING_PROCESS_CONF t ";
				//String sql= "select  parameter_value CNT   from "+schame+ ".cms_sys_parameter where parameter_name='CLEANING_CHANNEL_WFT_ID' ";
				 //String sql= "select  count(MODULE_ID)  CNT   from "+schame+ ".CMS_module where MODULE_NAME='代付管理'";
				// String sql= "select  t.RETURN_CODE  CNT   from "+schame+ ".CLE_CLEANING_BILL where  ROWNUM=1";
				 // String sql= "select u.USER_ID ,u.USER_NAME,u.CREATE_TIME,u.CREATE_EMP ,'受理机构超级管理员' as ROLE_NAME from "+schame+ ".CMS_USER u join  "+schame+ ".CMS_USER_ROLE r on (u.USER_ID=r.USER_ID) where r.role_id=(select ROLE_ID from "+schame+ ".CMS_ROLE where ROLE_NAME='受理机构超级管理员')";
				String sql= "SELECT	* from "+schame+ ".CMS_ORG_PARAMETER_CONF ";
				List<String> lines = new ArrayList<String>();
				lines.add("set define off;");
				lines.add("truncate table "+schame+ ".CMS_ORG_PARAMETER_CONF ;");
				 List<Map<String,String>> nulllogllist = dbTempory.getDatasByTable(sql);
				 if(nulllogllist.isEmpty()){
					  System.out.println(schame+":没有");
				 }else{
					 for(Map<String,String> map:nulllogllist){
						 System.out.println(schame+":"+map);
						 StringBuffer ss  = new StringBuffer();
						 StringBuffer header  = new StringBuffer("(");
						 StringBuffer fail  = new StringBuffer("(");
						 for(Entry<String, String> en: map.entrySet()){
							 String key = en.getKey();
							 String value = en.getValue();
							 if("null".equalsIgnoreCase(value)||StringUtils.isBlank(value)){
								 continue;
							 }
							 if(key.contains("TIME")){
								 value = "sysdate";
								 fail.append(""+value+" ,");
							 }else{
								 fail.append("'"+value+"' ,");
							 }
							 
							 header.append(key+" ,");
							
						 }
						 header.deleteCharAt(header.length()-1).append(")");
						 fail.deleteCharAt(fail.length()-1).append(")");
						 ss.append("INSERT INTO ").append(schame).append(".CMS_ORG_PARAMETER_CONF").append(header).append(" VALUES ").append(fail).append(";");
						 System.out.println(":"+ss);
						 lines.add(ss.toString());
					 }
					 
				 }
				 lines.add(""); lines.add("");
				 lines.add("commit;");lines.add("exit;");
				 FileUtils.writeLines(new File(pfile.getAbsoluteFile()+"/05.dml_CMS_ORG_PARAMETER_CONF.sql"),"gb2312", lines);
				// System.out.println(nulllogllist);
			} catch (Exception e) {
				//e.printStackTrace();
				continue;
			}
			
			 System.out.println("====================schame====================================="+schame);
			
		}
		//FileUtils.writeLines(new File("D:/公司资料/test/3.5标准版/受理机构用户帐号梳理/bank.csv"),"GBK", lines);
		System.out.println("down");
	}
private static void testTRA_PAY_TYPE(MyBasicBankService myBasicBankService) throws Exception {
		
		List<MybasicBank> mybasicBanks = myBasicBankService.findCHeck();
		 int i = 0;
		 int max = 1;
		 System.out.println("==============start=================");
		for(MybasicBank mybasicBank:mybasicBanks){
			if(i>max)
				//break;
			i++;
			String url = mybasicBank.getUrl();
			String pwd = mybasicBank.getPwd();
			String name = mybasicBank.getUserName();
			String schame = mybasicBank.getSechma();
			url = "jdbc:oracle:thin:@"+url;
			
		
		
			
			//String sql= "select count(1) as cnt       from "+schame+ ".cms_sys_type t  where t.TYPE_CLASS = 'API_PROVIDER'    and t.TYPE_CODE = '8'";
			  
			try {
				DBTempory dbTempory = new DBTempory(url,name,pwd,name);
				File pfile = new File("D:/公司资料/test/测试维护库/受理机构信息维护/"+schame);
				if(!pfile.exists()){
					FileUtils.forceMkdir(pfile);
				}
				//String sql= "select count(1)  AMOUNT from  "+schame+ ".CLE_CLEANING_PROCESS_CONF t ";
				//String sql= "select  parameter_value CNT   from "+schame+ ".cms_sys_parameter where parameter_name='CLEANING_CHANNEL_WFT_ID' ";
				 //String sql= "select  count(MODULE_ID)  CNT   from "+schame+ ".CMS_module where MODULE_NAME='代付管理'";
				// String sql= "select  t.RETURN_CODE  CNT   from "+schame+ ".CLE_CLEANING_BILL where  ROWNUM=1";
				 // String sql= "select u.USER_ID ,u.USER_NAME,u.CREATE_TIME,u.CREATE_EMP ,'受理机构超级管理员' as ROLE_NAME from "+schame+ ".CMS_USER u join  "+schame+ ".CMS_USER_ROLE r on (u.USER_ID=r.USER_ID) where r.role_id=(select ROLE_ID from "+schame+ ".CMS_ROLE where ROLE_NAME='受理机构超级管理员')";
				String sql= "SELECT	* from "+schame+ ".TRA_PAY_TYPE ";
				List<String> lines = new ArrayList<String>();
				lines.add("set define off;");
				lines.add("truncate table "+schame+ ".TRA_PAY_TYPE ;");
				 List<Map<String,String>> nulllogllist = dbTempory.getDatasByTable(sql);
				 if(nulllogllist.isEmpty()){
					  System.out.println(schame+":没有");
				 }else{
					 for(Map<String,String> map:nulllogllist){
						 System.out.println(schame+":"+map);
						 StringBuffer ss  = new StringBuffer();
						 StringBuffer header  = new StringBuffer("(");
						 StringBuffer fail  = new StringBuffer("(");
						 for(Entry<String, String> en: map.entrySet()){
							 String key = en.getKey();
							 String value = en.getValue();
							 if("null".equalsIgnoreCase(value)||StringUtils.isBlank(value)){
								 continue;
							 }
							 if(key.contains("TIME")){
								 value = "sysdate";
								 fail.append(""+value+" ,");
							 }else{
								 fail.append("'"+value+"' ,");
							 }
							 
							 header.append(key+" ,");
							
						 }
						 header.deleteCharAt(header.length()-1).append(")");
						 fail.deleteCharAt(fail.length()-1).append(")");
						 ss.append("INSERT INTO ").append(schame).append(".TRA_PAY_TYPE").append(header).append(" VALUES ").append(fail).append(";");
						 System.out.println(":"+ss);
						 lines.add(ss.toString());
					 }
					 
				 }
				 lines.add(""); lines.add("");
				 lines.add("commit;");lines.add("exit;");
				 FileUtils.writeLines(new File(pfile.getAbsoluteFile()+"/04.dml_TRA_PAY_TYPE.sql"),"gb2312", lines);
				// System.out.println(nulllogllist);
			} catch (Exception e) {
				//e.printStackTrace();
				continue;
			}
			
			 System.out.println("====================schame====================================="+schame);
			
		}
		//FileUtils.writeLines(new File("D:/公司资料/test/3.5标准版/受理机构用户帐号梳理/bank.csv"),"GBK", lines);
		System.out.println("down");
	}
private static void testTRA_PAY_CENTER(MyBasicBankService myBasicBankService) throws Exception {
		
		List<MybasicBank> mybasicBanks = myBasicBankService.findCHeck();
		 int i = 0;
		 int max = 1;
		 System.out.println("==============start=================");
		for(MybasicBank mybasicBank:mybasicBanks){
			if(i>max)
				//break;
			i++;
			String url = mybasicBank.getUrl();
			String pwd = mybasicBank.getPwd();
			String name = mybasicBank.getUserName();
			String schame = mybasicBank.getSechma();
			url = "jdbc:oracle:thin:@"+url;
			
		
		
			
			//String sql= "select count(1) as cnt       from "+schame+ ".cms_sys_type t  where t.TYPE_CLASS = 'API_PROVIDER'    and t.TYPE_CODE = '8'";
			  
			try {
				DBTempory dbTempory = new DBTempory(url,name,pwd,name);
				File pfile = new File("D:/公司资料/test/测试维护库/受理机构信息维护/"+schame);
				if(!pfile.exists()){
					FileUtils.forceMkdir(pfile);
				}
				//String sql= "select count(1)  AMOUNT from  "+schame+ ".CLE_CLEANING_PROCESS_CONF t ";
				//String sql= "select  parameter_value CNT   from "+schame+ ".cms_sys_parameter where parameter_name='CLEANING_CHANNEL_WFT_ID' ";
				 //String sql= "select  count(MODULE_ID)  CNT   from "+schame+ ".CMS_module where MODULE_NAME='代付管理'";
				// String sql= "select  t.RETURN_CODE  CNT   from "+schame+ ".CLE_CLEANING_BILL where  ROWNUM=1";
				 // String sql= "select u.USER_ID ,u.USER_NAME,u.CREATE_TIME,u.CREATE_EMP ,'受理机构超级管理员' as ROLE_NAME from "+schame+ ".CMS_USER u join  "+schame+ ".CMS_USER_ROLE r on (u.USER_ID=r.USER_ID) where r.role_id=(select ROLE_ID from "+schame+ ".CMS_ROLE where ROLE_NAME='受理机构超级管理员')";
				String sql= "SELECT	* from "+schame+ ".TRA_PAY_CENTER ";
				List<String> lines = new ArrayList<String>();
				lines.add("set define off;");
				lines.add("truncate table "+schame+ ".TRA_PAY_CENTER ;");
				 List<Map<String,String>> nulllogllist = dbTempory.getDatasByTable(sql);
				 if(nulllogllist.isEmpty()){
					  System.out.println(schame+":没有");
				 }else{
					 for(Map<String,String> map:nulllogllist){
						 System.out.println(schame+":"+map);
						 StringBuffer ss  = new StringBuffer();
						 StringBuffer header  = new StringBuffer("(");
						 StringBuffer fail  = new StringBuffer("(");
						 for(Entry<String, String> en: map.entrySet()){
							 String key = en.getKey();
							 String value = en.getValue();
							 if("null".equalsIgnoreCase(value)||StringUtils.isBlank(value)){
								 continue;
							 }
							 if(key.contains("TIME")){
								 value = "sysdate";
								 fail.append(""+value+" ,");
							 }else{
								 fail.append("'"+value+"' ,");
							 }
							 
							 header.append(key+" ,");
							
						 }
						 header.deleteCharAt(header.length()-1).append(")");
						 fail.deleteCharAt(fail.length()-1).append(")");
						 ss.append("INSERT INTO ").append(schame).append(".TRA_PAY_CENTER").append(header).append(" VALUES ").append(fail).append(";");
						 System.out.println(":"+ss);
						 lines.add(ss.toString());
					 }
					 
				 }
				 lines.add(""); lines.add("");
				 lines.add("commit;");lines.add("exit;");
				 FileUtils.writeLines(new File(pfile.getAbsoluteFile()+"/03.dml_TRA_PAY_CENTER.sql"),"gb2312", lines);
				// System.out.println(nulllogllist);
			} catch (Exception e) {
				//e.printStackTrace();
				continue;
			}
			
			 System.out.println("====================schame====================================="+schame);
			
		}
		//FileUtils.writeLines(new File("D:/公司资料/test/3.5标准版/受理机构用户帐号梳理/bank.csv"),"GBK", lines);
		System.out.println("down");
	}
	
	private static void testTRA_PAY_API(MyBasicBankService myBasicBankService) throws Exception {
		
		List<MybasicBank> mybasicBanks = myBasicBankService.findCHeck();
		 int i = 0;
		 int max = 1;
		 System.out.println("==============start=================");
		for(MybasicBank mybasicBank:mybasicBanks){
			if(i>max)
				//break;
			i++;
			String url = mybasicBank.getUrl();
			String pwd = mybasicBank.getPwd();
			String name = mybasicBank.getUserName();
			String schame = mybasicBank.getSechma();
			url = "jdbc:oracle:thin:@"+url;
			
		
		
			
			//String sql= "select count(1) as cnt       from "+schame+ ".cms_sys_type t  where t.TYPE_CLASS = 'API_PROVIDER'    and t.TYPE_CODE = '8'";
			  
			try {
				DBTempory dbTempory = new DBTempory(url,name,pwd,name);
				File pfile = new File("D:/公司资料/test/测试维护库/受理机构信息维护/"+schame);
				if(!pfile.exists()){
					FileUtils.forceMkdir(pfile);
				}
				//String sql= "select count(1)  AMOUNT from  "+schame+ ".CLE_CLEANING_PROCESS_CONF t ";
				//String sql= "select  parameter_value CNT   from "+schame+ ".cms_sys_parameter where parameter_name='CLEANING_CHANNEL_WFT_ID' ";
				 //String sql= "select  count(MODULE_ID)  CNT   from "+schame+ ".CMS_module where MODULE_NAME='代付管理'";
				// String sql= "select  t.RETURN_CODE  CNT   from "+schame+ ".CLE_CLEANING_BILL where  ROWNUM=1";
				 // String sql= "select u.USER_ID ,u.USER_NAME,u.CREATE_TIME,u.CREATE_EMP ,'受理机构超级管理员' as ROLE_NAME from "+schame+ ".CMS_USER u join  "+schame+ ".CMS_USER_ROLE r on (u.USER_ID=r.USER_ID) where r.role_id=(select ROLE_ID from "+schame+ ".CMS_ROLE where ROLE_NAME='受理机构超级管理员')";
				String sql= "SELECT	* from "+schame+ ".TRA_PAY_API ";
				List<String> lines = new ArrayList<String>();
				lines.add("set define off;");
				lines.add("truncate table "+schame+ ".TRA_PAY_API ;");
				 List<Map<String,String>> nulllogllist = dbTempory.getDatasByTable(sql);
				 if(nulllogllist.isEmpty()){
					  System.out.println(schame+":没有");
				 }else{
					 for(Map<String,String> map:nulllogllist){
						 System.out.println(schame+":"+map);
						 StringBuffer ss  = new StringBuffer();
						 StringBuffer header  = new StringBuffer("(");
						 StringBuffer fail  = new StringBuffer("(");
						 for(Entry<String, String> en: map.entrySet()){
							 String key = en.getKey();
							 String value = en.getValue();
							 if("null".equalsIgnoreCase(value)||StringUtils.isBlank(value)){
								 continue;
							 }
							 if(key.contains("TIME")){
								 value = "sysdate";
								 fail.append(""+value+" ,");
							 }else{
								 fail.append("'"+value+"' ,");
							 }
							 
							 header.append(key+" ,");
							
						 }
						 header.deleteCharAt(header.length()-1).append(")");
						 fail.deleteCharAt(fail.length()-1).append(")");
						 ss.append("INSERT INTO ").append(schame).append(".TRA_PAY_API").append(header).append(" VALUES ").append(fail).append(";");
						 System.out.println(":"+ss);
						 lines.add(ss.toString());
					 }
					 
				 }
				 lines.add(""); lines.add("");
				 lines.add("commit;");lines.add("exit;");
				 FileUtils.writeLines(new File(pfile.getAbsoluteFile()+"/02.dml_TRA_PAY_API.sql"),"gb2312", lines);
				// System.out.println(nulllogllist);
			} catch (Exception e) {
				//e.printStackTrace();
				continue;
			}
			
			 System.out.println("====================schame====================================="+schame);
			
		}
		//FileUtils.writeLines(new File("D:/公司资料/test/3.5标准版/受理机构用户帐号梳理/bank.csv"),"GBK", lines);
		System.out.println("down");
	}
	
	private static void testCMS_SYS_PARAMETER(MyBasicBankService myBasicBankService) throws Exception {
	
		List<MybasicBank> mybasicBanks = myBasicBankService.findCHeck();
		 int i = 0;
		 int max = 1;
		 System.out.println("==============start=================");
		for(MybasicBank mybasicBank:mybasicBanks){
			if(i>max)
				//break;
			i++;
			String url = mybasicBank.getUrl();
			String pwd = mybasicBank.getPwd();
			String name = mybasicBank.getUserName();
			String schame = mybasicBank.getSechma();
			url = "jdbc:oracle:thin:@"+url;
			
		
		
			
			//String sql= "select count(1) as cnt       from "+schame+ ".cms_sys_type t  where t.TYPE_CLASS = 'API_PROVIDER'    and t.TYPE_CODE = '8'";
			  
			try {
				DBTempory dbTempory = new DBTempory(url,name,pwd,name);
				File pfile = new File("D:/公司资料/test/测试维护库/受理机构信息维护/"+schame);
				if(!pfile.exists()){
					FileUtils.forceMkdir(pfile);
				}
				//String sql= "select count(1)  AMOUNT from  "+schame+ ".CLE_CLEANING_PROCESS_CONF t ";
				//String sql= "select  parameter_value CNT   from "+schame+ ".cms_sys_parameter where parameter_name='CLEANING_CHANNEL_WFT_ID' ";
				 //String sql= "select  count(MODULE_ID)  CNT   from "+schame+ ".CMS_module where MODULE_NAME='代付管理'";
				// String sql= "select  t.RETURN_CODE  CNT   from "+schame+ ".CLE_CLEANING_BILL where  ROWNUM=1";
				 // String sql= "select u.USER_ID ,u.USER_NAME,u.CREATE_TIME,u.CREATE_EMP ,'受理机构超级管理员' as ROLE_NAME from "+schame+ ".CMS_USER u join  "+schame+ ".CMS_USER_ROLE r on (u.USER_ID=r.USER_ID) where r.role_id=(select ROLE_ID from "+schame+ ".CMS_ROLE where ROLE_NAME='受理机构超级管理员')";
				String sql= "SELECT	* from "+schame+ ".CMS_SYS_PARAMETER ";
				List<String> lines = new ArrayList<String>();
				lines.add("set define off;");
				lines.add("truncate table "+schame+ ".CMS_SYS_PARAMETER ;");
				 List<Map<String,String>> nulllogllist = dbTempory.getDatasByTable(sql);
				 if(nulllogllist.isEmpty()){
					  System.out.println(schame+":没有");
				 }else{
					 for(Map<String,String> map:nulllogllist){
						 System.out.println(schame+":"+map);
						 StringBuffer ss  = new StringBuffer();
						 StringBuffer header  = new StringBuffer("(");
						 StringBuffer fail  = new StringBuffer("(");
						 for(Entry<String, String> en: map.entrySet()){
							 String key = en.getKey();
							 String value = en.getValue();
							 
							 if("null".equalsIgnoreCase(value)||StringUtils.isEmpty(value)){
								 continue;
							 }
							 if(key.contains("TIME")){
								 value = "sysdate";
								 fail.append(""+value+" ,");
							 }else{
								 fail.append("'"+value+"' ,");
							 }
							 
							 header.append(key+" ,");
							
						 }
						 header.deleteCharAt(header.length()-1).append(")");
						 fail.deleteCharAt(fail.length()-1).append(")");
						 ss.append("INSERT INTO ").append(schame).append(".CMS_SYS_PARAMETER").append(header).append(" VALUES ").append(fail).append(";");
						 System.out.println(":"+ss);
						 lines.add(ss.toString());
					 }
					 
				 }
				 lines.add(""); lines.add("");
				 lines.add("commit;");lines.add("exit;");
				 FileUtils.writeLines(new File(pfile.getAbsoluteFile()+"/01.dml_CMS_SYS_PARAMETER.sql"),"gb2312", lines);
				// System.out.println(nulllogllist);
			} catch (Exception e) {
				//e.printStackTrace();
				continue;
			}
			
			 System.out.println("====================schame====================================="+schame);
			
		}
		//FileUtils.writeLines(new File("D:/公司资料/test/3.5标准版/受理机构用户帐号梳理/bank.csv"),"GBK", lines);
		System.out.println("down");
	}
	 
	 
	
	
	private static void testcsvBankCheck(MyBasicBankService myBasicBankService) throws Exception {
		
		List<MybasicBank> mybasicBanks = myBasicBankService.findCHeck();
		 int i = 0;
		 int max = 3;
		 System.out.println("==============start=================");
		for(MybasicBank mybasicBank:mybasicBanks){
			if(i>max)
				//break;
			i++;
			String url = mybasicBank.getUrl();
			String pwd = mybasicBank.getPwd();
			String name = mybasicBank.getUserName();
			String schame = mybasicBank.getSechma();
			url = "jdbc:oracle:thin:@"+url;
			
			//String sql= "select count(1) as cnt       from "+schame+ ".cms_sys_type t  where t.TYPE_CLASS = 'API_PROVIDER'    and t.TYPE_CODE = '8'";
			  
			try {
				DBTempory dbTempory = new DBTempory(url,name,pwd,name);
				//String sql= "select count(1)  AMOUNT from  "+schame+ ".CLE_CLEANING_PROCESS_CONF t ";
				//String sql= "select  parameter_value CNT   from "+schame+ ".cms_sys_parameter where parameter_name='CLEANING_CHANNEL_WFT_ID' ";
				 //String sql= "select  count(MODULE_ID)  CNT   from "+schame+ ".CMS_module where MODULE_NAME='代付管理'";
				// String sql= "select  t.RETURN_CODE  CNT   from "+schame+ ".CLE_CLEANING_BILL where  ROWNUM=1";
				 // String sql= "select u.USER_ID ,u.USER_NAME,u.CREATE_TIME,u.CREATE_EMP ,'受理机构超级管理员' as ROLE_NAME from "+schame+ ".CMS_USER u join  "+schame+ ".CMS_USER_ROLE r on (u.USER_ID=r.USER_ID) where r.role_id=(select ROLE_ID from "+schame+ ".CMS_ROLE where ROLE_NAME='受理机构超级管理员')";
				String sql= "SELECT	'INSERT INTO zhifu.CMS_SYS_PARAMETER (PARAMETER_NAME, PARAMETER_VALUE, REMARK, PHYSICS_FLAG, CREATE_TIME, UPDATE_TIME) VALUES (&'||a.PARAMETER_NAME||'&, &'||a.PARAMETER_VALUE||'&,&'||a.REMARK||'&,1,sysdate,sysdate);' as MM from "+schame+ ".CMS_SYS_PARAMETER a";
				List<String> lines = new ArrayList<String>();
				
				 List<Map<String,String>> nulllogllist = dbTempory.getDatasByTable(sql);
				 if(nulllogllist.isEmpty()){
					  System.out.println(schame+":没有");
				 }else{
					 for(Map<String,String> map:nulllogllist){
						 System.out.println(schame+":"+map);
						 String str = map.get("MM");
						 str =  str.replaceAll("zhifu", schame).replaceAll("&&", "null").replaceAll("&", "'");
						 StringBuffer sb  = new StringBuffer(str);
						 System.out.println(str);
						 lines.add(sb.toString());
					 }
					 
				 }
				 lines.add(""); lines.add("");
				FileUtils.writeLines(new File("F:/批量刷新库/"+schame+".sql"),"GBK", lines);
				// System.out.println(nulllogllist);
			} catch (Exception e) {
				//e.printStackTrace();
				continue;
			}
			
			 System.out.println("====================schame====================================="+schame);
			
		}
		//FileUtils.writeLines(new File("D:/公司资料/test/3.5标准版/受理机构用户帐号梳理/bank.csv"),"GBK", lines);
		System.out.println("down");
	}
	 
	 

	private static void testDisBankCheck(MyBasicBankService myBasicBankService) throws Exception {
	
		List<MybasicBank> mybasicBanks = myBasicBankService.findCHeck();
	 
		for(MybasicBank mybasicBank:mybasicBanks){
		
			String schame = mybasicBank.getSechma();
		 
			String srcDir = "D:/公司资料/test/测试维护库/20171023/cmbgz";
			String destDir = "D:/公司资料/test/测试维护库/20171023/"+schame;
		     if("cmbgz".equalsIgnoreCase(schame)){
		    	 continue;
		     }
			FileUtils.copyDirectory(new File(srcDir), new File(destDir));
		 
		}
		System.out.println("down");
	}
	 
}
