package com.wft.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import com.wft.db.DBTempory;
import com.wft.model.MybasicBank;
import com.wft.service.MyBasicBankService;
 
public class BankTest {
	
	 
	
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("application.xml");
	 
		MyBasicBankService myBasicBankService = ac.getBean("myBasicBankService", MyBasicBankService.class);
	 
		//testmyBasicBankCheck(myBasicBankService);
		testmyBasicBankCheckRecode(myBasicBankService);
	 
	}
	
	
	
	private static void testmyBasicBankCheckRecode(
			MyBasicBankService myBasicBankService) {
		// TODO Auto-generated method stub
		List<MybasicBank> mybasicBanks = myBasicBankService.findCHeck();
		System.out.println(mybasicBanks.size());
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
				String sql= "select  parameter_value CNT   from "+schame+ ".cms_sys_parameter where parameter_name='ACCPET_ORG_EXP_TYPE' ";
				//String sql= "select  count(MODULE_ID)  CNT   from "+schame+ ".CMS_module where MODULE_NAME='风控管理'";
				 //String sql="SELECT DISTINCT(SUBSTR(CLEANING_NAME,INSTR(CLEANING_NAME, '.', -1) + 1,LENGTH(CLEANING_NAME))) as CNT  from "+schame+ ".CLE_CLEANING_PROCESS_LOG where PHYSICS_FLAG=1";
				//String sql= "select count(channel_id) CNT from  "+schame+ ".cms_channel where channel_type = 1 ";
				/* String sql= "SELECT role_id   CNT   from "+schame+ ".CMS_ROLE WHERE ROLE_NAME='威富通测试'";*/
				// String sql= "select COUNT(1) CNT from "+schame+ ".ACC_CHANNEL_BILL_TASK where CHANNEL_ID='755010000003'";
				// String sql= " select max(cnt) CNT from(select count(*) cnt  from "+schame+ ".CLE_CLEANING_BILL b  where b.physics_flag = 1 group BY b.CLEANING_DATE ) a ";
				//  String sql= " select max(am) CNT from(select count(*) cnt,sum(b.EXPORT_AMOUNT) am  from "+schame+ ".CLE_CLEANING_BILL b  where b.physics_flag = 1 group BY b.CLEANING_DATE ) a ";

				 List<Map<String,String>> nulllogllist = dbTempory.getDatasByTable(sql);
				 if(CollectionUtils.isEmpty(nulllogllist)){
					 System.out.println(mybasicBank.getRealName()+","+schame+"没有,"+nulllogllist);
						
				 }else{
					 Map<String,String> mlogs = nulllogllist.get(0);
					 String val = mlogs.get("CNT");
					 System.out.println(mybasicBank.getRealName()+","+schame+"有有,"+val);
					 
				 }
				 	
				
				
				 //System.out.println("update MY_BASIC_BANK set nullfile='"+val+"' where SECHMA='"+schame+"';");
				 //System.out.println(nulllogllist);
			} catch (Exception e) {
				//e.printStackTrace();
				continue;
			}
			
			
			
		}
		
	}
	 



	private static void testmyBasicBankCheck(MyBasicBankService myBasicBankService) throws Exception {
	
		List<MybasicBank> mybasicBanks = myBasicBankService.findCHeck();
		System.out.println(mybasicBanks.size());
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
				//String sql= "select  parameter_value CNT   from "+schame+ ".cms_sys_parameter where parameter_name='PLATFORM_FLAG' ";
				//String sql= "select  count(MODULE_ID)  CNT   from "+schame+ ".CMS_module where MODULE_NAME='代付管理'";
				 //String sql="SELECT DISTINCT(SUBSTR(CLEANING_NAME,INSTR(CLEANING_NAME, '.', -1) + 1,LENGTH(CLEANING_NAME))) as CNT  from "+schame+ ".CLE_CLEANING_PROCESS_LOG where PHYSICS_FLAG=1";
				//String sql= "select  parameter_value CNT   from "+schame+ ".cms_sys_parameter where parameter_name='ClEANING_FILE_IS_NULL' ";
				/* String sql= "SELECT role_id   CNT   from "+schame+ ".CMS_ROLE WHERE ROLE_NAME='威富通测试'";*/
				 String sql= "select COUNT(1) CNT from "+schame+ ".ACC_CHANNEL_BILL_TASK where CHANNEL_ID='755010000003'";
				 List<Map<String,String>> nulllogllist = dbTempory.getDatasByTable(sql);
				 if(nulllogllist.isEmpty()){
					 System.out.println(schame+":没有");
					  //System.out.println("update MY_BASIC_BANK set needback='无' where SECHMA='"+schame+"';");
				 }else{
					
					 Map<String,String> mlogs = nulllogllist.get(0);
					 String val = mlogs.get("CNT");
					 if(val==null||"null".equals(val)){
						 System.out.println("update MY_BASIC_BANK set needback='无' where SECHMA='"+schame+"';");
					 }else{
						 System.out.println(schame+":有"+val);
						 
					 }
					 //System.out.println("update MY_BASIC_BANK set nullfile='"+val+"' where SECHMA='"+schame+"';");
					 
				 }
				 //System.out.println(nulllogllist);
			} catch (Exception e) {
				//e.printStackTrace();
				continue;
			}
			
			
			
		}
		
	}
	 
}
