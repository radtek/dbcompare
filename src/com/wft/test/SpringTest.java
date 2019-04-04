package com.wft.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.wft.appconf.dto.MyBankConf;
import com.wft.appconf.service.MyBankConfService;
import com.wft.appconf.service.poi.ExcelReadsUitls;
import com.wft.db.DBTempory;
import com.wft.model.FileHistory;
import com.wft.model.MybasicBank;
import com.wft.reg.ParseSql;
import com.wft.service.DBService;
import com.wft.service.FileHistoryService;
import com.wft.service.MyBasicBankService;
import com.wft.util.DateUtils;
import com.wft.util.ReplaceSchema;
import com.wft.util.UploadPathUtil;
 
public class SpringTest {
	
	private static Map<String, String> SCHEMAS_DDL = new HashMap<String, String>();
	 
	static {
	 	SCHEMAS_DDL.put("shisircb35","石狮农商行3.5_DDL");
		SCHEMAS_DDL.put("pingan","平安深圳_DDL");
		SCHEMAS_DDL.put("nbcb","宁波银行_DDL");
		SCHEMAS_DDL.put("bsbc","宝生村镇银行_DDL");
		//SCHEMAS_DDL.put("qcloud","昆山农商行_DDL");
		SCHEMAS_DDL.put("czbank","浙商银行_DDL");
		SCHEMAS_DDL.put("qbsbc","包商银行_DDL");
		SCHEMAS_DDL.put("xmcmbc","民生厦门_DDL");
		//SCHEMAS_DDL.put("wujbank","吴江农商_DDL");
		SCHEMAS_DDL.put("cmb","招商深圳_DDL");
		//SCHEMAS_DDL.put("czcb","稠州银行_DDL");
		SCHEMAS_DDL.put("fjnx","福建农信_DDL");
		SCHEMAS_DDL.put("zzbank","郑州银行_DDL");
		SCHEMAS_DDL.put("webank","微众银行_DDL");
		SCHEMAS_DDL.put("hrbcb","哈尔滨银行_DDL");
		SCHEMAS_DDL.put("gfbank","广发总行_DDL");
		SCHEMAS_DDL.put("lnbank","辽宁建行_DDL");
		SCHEMAS_DDL.put("szceb","光大深圳_DDL");
		SCHEMAS_DDL.put("shanghcebbank","光大上海_DDL");
		SCHEMAS_DDL.put("hxbank","深圳华夏_DDL");
		SCHEMAS_DDL.put("bjbank","北京银行_DDL");
		SCHEMAS_DDL.put("cbhbnj","渤海南京银行_DDL");
		//SCHEMAS_DDL.put("qzns","泉州农商_DDL");
		SCHEMAS_DDL.put("suzhouceb","光大苏州_DDL");
		SCHEMAS_DDL.put("bolz","柳州银行_DDL");
		SCHEMAS_DDL.put("snccb","四川遂宁_DDL");
		SCHEMAS_DDL.put("gzcb","广州银行_DDL");
		SCHEMAS_DDL.put("lyyhwxsd","洛阳银行_DDL");
		SCHEMAS_DDL.put("ljzfzs","厦门建行_DDL");
		SCHEMAS_DDL.put("zdnsyh","正定农商行_DDL");
		SCHEMAS_DDL.put("bos","上海银行_DDL");
		SCHEMAS_DDL.put("szsccb","石嘴山银行_DDL");
		SCHEMAS_DDL.put("bou","乌鲁木齐银行_DDL");
		SCHEMAS_DDL.put("zjtlcb","泰隆银行_DDL");
		SCHEMAS_DDL.put("qzccbank","泉州银行_DDL");
		SCHEMAS_DDL.put("hxyhsmsh","华兴银行_DDL");
		SCHEMAS_DDL.put("myrbank","三河蒙银村镇银行_DDL");
		SCHEMAS_DDL.put("haihai","嗨嗨旅游云_DDL");
		SCHEMAS_DDL.put("jzbankch","晋中银行3.5_DDL");
		SCHEMAS_DDL.put("sdbank","顺德农商行_DDL");
		//SCHEMAS_DDL.put("tayh","泰安银行_DDL");
		SCHEMAS_DDL.put("cdrcb","成都农商行_DDL");
		SCHEMAS_DDL.put("gzcebbank","光大广州_DDL");
		SCHEMAS_DDL.put("cmbwh","招行武汉_DDL");
		//SCHEMAS_DDL.put("jingwai","境外支付_DDL");
		SCHEMAS_DDL.put("dxzx","点芯_DDL");
		SCHEMAS_DDL.put("cmbchina","招商总行_DDL");
		SCHEMAS_DDL.put("jgrcb","金谷农商_DDL");
		SCHEMAS_DDL.put("ljbank","龙江银行_DDL");
		SCHEMAS_DDL.put("srcb","深圳农商_DDL");
		SCHEMAS_DDL.put("cmbsy","招商沈阳银行_DDL");
		SCHEMAS_DDL.put("wzrcb","武陟农商行_DDL");
		SCHEMAS_DDL.put("ycrcb","永城农商行_DDL");
		SCHEMAS_DDL.put("lbrcb","灵宝农商行_DDL");
		SCHEMAS_DDL.put("lyrcb","洛阳农村商业银行_DDL");
		SCHEMAS_DDL.put("jcbank","晋城银行 _DDL");
		SCHEMAS_DDL.put("tccb","天津银行_DDL");
		 
		 
	}
	
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("application.xml");
		//DBService dBService = ac.getBean("dBService", DBService.class);
	//	MyBasicBankService myBasicBankService = ac.getBean("myBasicBankService", MyBasicBankService.class);
		//dBService.excuteDDLFromFile("C:/Users/admin/Desktop/20170117_chenli_退汇手补表.sql", "zzy");
		//System.out.println(dBService.excuteDDL("create table MY_TEST1(  ID NUMBER(10) NOT NULL)","comment on table MY_TEST1 is 'SPAY收银员统计.'"));
	/*	FileHistoryService fileHistoryService = ac.getBean("fileHistoryService", FileHistoryService.class);
		FileHistory fileHistory = new FileHistory();
		fileHistory.setCreateTime(new Date());
		fileHistory.setUpdateTime(new Date());
		fileHistory.setRealName("20170117_chenli_退汇手补表.sql");
		fileHistory.setFileType("ddl");
		fileHistory.setMd5("md5");
		fileHistory.setServiceName("11111111.sql");
		fileHistory.setServiceUrl("usrl");
		fileHistory.setStatus(0);*/
		//fileHistoryService.add(fileHistory);
		
		//System.out.println(fileHistoryService.findById(4));
		//testfindAll(fileHistoryService);
		
		//testfindAllByTime(fileHistoryService);
		//testFromFile(dBService);
		//testmyBasicBankCheck(myBasicBankService);
		testMyBankConfService(ac);
		
		ac.close();
	}
	
	
	
	private static void testMyBankConfService(ClassPathXmlApplicationContext ac) throws Exception {
		// TODO Auto-generated method stub
		
		String filePath = "F:/公司重要文件/部署war包配置文件一键生成/环境列表模板.xlsx"; 
		
		
		MyBankConfService myBankConfService = ac.getBean("myBankConfService", MyBankConfService.class);
		
		MyBankConf myBankConf = new MyBankConf();
		
		myBankConf.setBankId(1);
		myBankConf.setConfPath("");
		myBankConf.setBankName("广发银行");
		JSONObject jsonObject = new JSONObject(true); 
		jsonObject.put("val", ExcelReadsUitls.readXlsx(new File(filePath)));
		myBankConf.setExcelJson(jsonObject.toString());
	 
		myBankConfService.add(myBankConf);
		//System.out.println(myBankConfService.findAll(myBankConf).get(1).getExcelJson());
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
				String sql= "select  count(1) CNT   from "+schame+ ".CMS_APP where APP_CODE='APP_PAY_CLOUT' ";
				 List<Map<String,String>> nulllogllist = dbTempory.getDatasByTable(sql);
				 if(nulllogllist.isEmpty()){
					  System.out.println(schame+":没有");
				 }else{
					 Map<String,String> mlogs = nulllogllist.get(0);
					 String val = mlogs.get("CNT");
					 if("2".equals(val)){
						 System.out.println(schame+":金额:"+val);
					 }else{
						 System.out.println(schame+":金额:"+val);
					 }
					 
				 }
				 System.out.println(nulllogllist);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			
			
			
		}
		
	}
	
	private static void  insertmyBasicBankService (MyBasicBankService myBasicBankService) throws Exception{
		for (String key : SCHEMAS_DDL.keySet()) {
			MybasicBank mybasicBank = new MybasicBank();
			mybasicBank.setUrl("139.199.174.101:1521:pcloud");
			mybasicBank.setUserName("dbview");
			mybasicBank.setPwd("dbbie12w");
			mybasicBank.setSechma(key);
			mybasicBank.setCreateTime(new Date());
			mybasicBank.setUpdateTime(new Date());
			mybasicBank.setRealName(SCHEMAS_DDL.get(key).split("_")[0]);
			mybasicBank.setMold(1);
			myBasicBankService.add(mybasicBank);
		}
		
	}
	
	

	public static void testFromFile(DBService dBService ) {
		try {
			ParseSql.parseFromFile("C:/Users/admin/Desktop/20170524_tangbaodong_WOP新加入两张表.sql");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testfindAllByTime(FileHistoryService fileHistoryService ) {
		Date strDate = DateUtils.formdate("2017-05-19");
		Date endDate =  DateUtils.formdate("2017-05-20");
		List<FileHistory> u = fileHistoryService.findAllByCreateTime(strDate,endDate);
		System.out.println("files:"+u.size());
		
	}
	public static void testfindAll(FileHistoryService fileHistoryService ) throws Exception {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("ids", "13,12,16");

		List<FileHistory> u= fileHistoryService.findAll(map);
		System.out.println(u.size());
		 
		List<File> fileDDLs = new ArrayList<File>();
		List<File> fileDMLs = new ArrayList<File>();
		for(FileHistory fileHistory:u){
			File destFile =  new File(UploadPathUtil.getTempPath()+fileHistory.getServiceUrl());
			if("DDL".equalsIgnoreCase(fileHistory.getFileType())){
				fileDDLs.add(destFile);
			}else{
				fileDMLs.add(destFile);
			}
			
		}
		
		List<File> files = new ArrayList<File>();
		if(fileDDLs.size()>0){
			File ddlFile = ReplaceSchema.mergeSql("zzy", "ddl",  UploadPathUtil.getScriptPath(), fileDDLs);
			files.add(ddlFile);
		}
		if(fileDMLs.size()>0){
			File dmlFile = ReplaceSchema.mergeSql("zzy", "dml",  UploadPathUtil.getScriptPath() , fileDMLs);
			files.add(dmlFile);
		}
		System.out.println("files:"+files.size());
	}
}
