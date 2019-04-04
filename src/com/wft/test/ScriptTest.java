package com.wft.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wft.db.DBTempory;
import com.wft.model.MybasicBank;
import com.wft.service.MyBasicBankService;
 
public class ScriptTest {
	
	 
	
	public static void main(String[] args) throws Exception {
	 /*  ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("application.xml");
	 
		MyBasicBankService myBasicBankService = ac.getBean("myBasicBankService", MyBasicBankService.class);
	 
		testcsvBankCheck(myBasicBankService);
		ac.destroy();
		ac.close();*/
		
		testsecma();
	}
	
	private static void testsecma() throws Exception {
		File file = new File("D:/公司资料/test/3.5标准版/受理机构用户帐号梳理/脚本生成/执行脚本");
		String[] fs =  file.list();
		for(String f:fs){
			System.out.println(f);
		}
	}
	
	private static void testgenscv() throws Exception {
		File file = new File("D:/公司资料/test/3.5标准版/受理机构用户帐号梳理/bank-删除用户已标记1.csv");
		List<String> lines = FileUtils.readLines(file, "GBK");
		Map<String,List<String>> maps = new HashMap<String, List<String>>();
		for(int i=1,len=lines.size();i<len;i++){
			 String line = lines.get(i);
			 String[] lstrs = line.split(",",7);
			 System.out.println(lstrs[6]);
			 if("1,".equals(lstrs[6])){
				 System.out.println(line);
				 List<String> ls = maps.get(lstrs[5]);
				 if(ls==null){
					 ls = new ArrayList<String>();
					 maps.put(lstrs[5], ls);
				 }
				 ls.add(line);
			 }
			
		}
		
		System.out.println(maps);
		
		for(Entry<String, List<String>> en:maps.entrySet()){
			String schema = en.getKey().replaceAll("'", "");
			File f = new File("D:/公司资料/test/3.5标准版/受理机构用户帐号梳理/脚本生成/执行脚本/"+schema);
			FileUtils.forceMkdir(f);
			List<String> sqls = new ArrayList<String>();
			sqls.add("set define off;");
			List<String> linelist = en.getValue();
			for(String line:linelist){
				 String[] lstrs = line.split(",",7);
				 StringBuffer sb  = new StringBuffer();
				 String USER_ID = lstrs[0].replaceAll("'", "");
				 String USER_NAME = lstrs[1].replaceAll("'", "");
				 sb.append("delete from "+schema+".CMS_USER where USER_ID='"+USER_ID+"' and  USER_NAME='"+USER_NAME+"';");
				 sqls.add(sb.toString());
			}
			sqls.add("commit;");sqls.add("exit;");
			FileUtils.writeLines(new File(f.getAbsoluteFile()+"/01.dml.sql"),"gb2312", sqls);
		}
	 
	}
	
	private static void testcsvBankCheck(MyBasicBankService myBasicBankService) throws Exception {
	
		List<MybasicBank> mybasicBanks = myBasicBankService.findCHeck();
		List<String> lines = new ArrayList<String>();
		 String header = "USER_ID,USER_NAME,CREATE_TIME,CREATE_EMP,ROLE_NAME,SCHAME,STATUS";
		 lines.add(header);
		 System.out.println("==============start=================");
		for(MybasicBank mybasicBank:mybasicBanks){
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
				String sql= "select u.* from "+schame+ ".CMS_USER u join  "+schame+ ".CMS_USER_ROLE r on (u.USER_ID=r.USER_ID) where r.role_id=(select ROLE_ID from "+schame+ ".CMS_ROLE where ROLE_NAME='受理机构超级管理员')";
				/*List<String> lines = new ArrayList<String>();
				 String header = "USER_ID,USER_NAME,CREATE_TIME,CREATE_EMP,ROLE_NAME,SCHAME,STATUS";
				 lines.add(header);*/
				
				 List<Map<String,String>> nulllogllist = dbTempory.getDatasByTable(sql);
				 if(nulllogllist.isEmpty()){
					  System.out.println(schame+":没有");
				 }else{
					 for(Map<String,String> map:nulllogllist){
						 System.out.println(schame+":"+map);
						 StringBuffer sb  = new StringBuffer();
						 sb.append("'"+map.get("USER_ID")+",").append("'"+map.get("USER_NAME")+",").append("'"+map.get("CREATE_TIME")+",").append("'"+map.get("CREATE_EMP")+",").append("'"+map.get("ROLE_NAME")+",").append("'"+schame+",");
						 
						 lines.add(sb.toString());
					 }
					 
				 }
				 lines.add(""); lines.add("");
				// FileUtils.writeLines(new File("D:/公司资料/test/3.5标准版/受理机构用户帐号梳理/bank/"+schame+".csv"),"GBK", lines);
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
	 
	
	private static void testmyBasicBankCheck(MyBasicBankService myBasicBankService) throws Exception {
		List<MybasicBank> mybasicBanks = myBasicBankService.findCHeck();
		for(MybasicBank mybasicBank:mybasicBanks){
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
				// String sql= "select u.USER_ID ,u.USER_NAME,u.CREATE_TIME,u.CREATE_EMP ,'受理机构超级管理员' as role_name from "+schame+ ".CMS_USER u join  "+schame+ ".CMS_USER_ROLE r on (u.USER_ID=r.USER_ID) where r.role_id=(select ROLE_ID from "+schame+ ".CMS_ROLE where ROLE_NAME='受理机构超级管理员')";
				  String sql= "SELECT *   from "+schame+ ".CMS_USER  where USER_ID='1' and  USER_NAME='superadmin'";

				 List<Map<String,String>> nulllogllist = dbTempory.getDatasByTable(sql);
				 if(nulllogllist.isEmpty()){
					  System.out.println(schame+":没有");
				 }else{
					 Map<String,String> mlogs = nulllogllist.get(0);
					 
					 System.out.println("mlogs:"+mlogs);
				 }
				 System.out.println(nulllogllist);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			
			
			
		}
		
		System.out.println("down");
	}
}
