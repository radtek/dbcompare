package com.wft.action;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.wft.db.ColExist;
import com.wft.db.Column;
import com.wft.db.DBTempory;
import com.wft.db.DBUtilBatch;
import com.wft.db.Table;
import com.wft.model.MybasicBank;
import com.wft.monitor.MonitorEmailReport;
import com.wft.monitor.WebSiteMonitor;
import com.wft.service.DbTypeService;
import com.wft.service.MyBasicBankService;
import com.wft.util.BeanNotNullCopyUtils;
import com.wft.util.CommonUtil;
import com.wft.util.DateUtils;
import com.wft.vo.DbTypeVo;
import com.wft.vo.UserVo;
import com.wft.websocket.WsPool;

/**
 * @author admin
 * 标准库下载记录
 */
@Controller
@RequestMapping(value = "/myBasicBank")
public class MyBasicBankAction extends BaseAction{
	
	
	ExecutorService executorService  =  Executors.newFixedThreadPool(1);
	
	@Autowired
	private MonitorEmailReport monitorEmailReport;
	
	@Autowired
	private MyBasicBankService myBasicBankService;
	
	
	@Autowired
	private DbTypeService dbTypeService;
	
	// 查询所有基本表
	@RequestMapping(value = "/findAll")
	public void findAll(HttpServletRequest request, HttpServletResponse response,MybasicBank mybasicBank)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		UserVo userVo = (UserVo) request.getSession().getAttribute("user");
		if(userVo!=null&&"admin".equals(userVo.getUserName())){//超管查询所有
			mybasicBank.setPhysicsFlag(CommonUtil.ALL);
		 }
		List<MybasicBank> u = myBasicBankService.findAll(mybasicBank);
		int total = u.size();
		map.put("rows", u);
		map.put("total", total);
		String str = JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
	
	//作废信息
	@RequestMapping(value = "/add")
	public void add(MybasicBank mybasicBank,HttpServletRequest request, HttpServletResponse response ) throws Exception {
		String type = request.getParameter("type");
		DbTypeVo  dbTypeVo = dbTypeService.getDbTypeVo(type);
		mybasicBank.setUrl(dbTypeVo.getUrl());
		mybasicBank.setUserName(StringUtils.isEmpty(dbTypeVo.getUserName())?mybasicBank.getSechma():dbTypeVo.getUserName());
		mybasicBank.setPwd(StringUtils.isEmpty(dbTypeVo.getPwd())?mybasicBank.getSechma():dbTypeVo.getPwd());
		mybasicBank.setMold(dbTypeVo.getMold());
	 
		mybasicBank.setCreateTime(new Date());
		mybasicBank.setUpdateTime(new Date());
		
		myBasicBankService.add(mybasicBank);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "成功");
		map.put("flag", true);
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
	}
		

	 //作废信息
	@RequestMapping(value = "/disable")
	public void disable(@RequestParam("id") Integer id,HttpServletRequest request, HttpServletResponse response ) throws Exception {
		log.info("id:"+id);
		MybasicBank mybasicBank = myBasicBankService.findById(id);
		if(mybasicBank!=null){
			int physicsFlagNew = 0;
			int physicsFlag = mybasicBank.getPhysicsFlag();
			physicsFlagNew = CommonUtil.EABLE == physicsFlag?CommonUtil.DISEABLE: CommonUtil.EABLE;
			mybasicBank.setPhysicsFlag(physicsFlagNew);
			myBasicBankService.update(mybasicBank);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "成功");
		map.put("flag", true);
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
	}
	
	//修改信息
	@RequestMapping(value = "/modify")
	public void modify(MybasicBank mybasicBank,HttpServletRequest request, HttpServletResponse response ) throws Exception {
		log.info("id:"+mybasicBank.getId());
		MybasicBank tmybasicBank = myBasicBankService.findById(mybasicBank.getId());
		BeanNotNullCopyUtils.copyProperties(mybasicBank, tmybasicBank);
		myBasicBankService.update(tmybasicBank);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "成功");
		map.put("flag", true);
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
	}
	
	 //根据id信息
	@RequestMapping(value = "/findById")
	public void findById(@RequestParam("id") Integer id,HttpServletRequest request, HttpServletResponse response ) throws Exception {
		log.info("id:"+id);
		MybasicBank mybasicBank = myBasicBankService.findById(id);
		 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "成功");
		map.put("flag", true);
		map.put("row", mybasicBank);
		map.put("content", mybasicBank.getFls3()+" 【数据库信息:{"+mybasicBank.getUrl()+","+mybasicBank.getUserName()+","+mybasicBank.getPwd()+"}】 ");
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
	}
		
		
	 
	 //查看银行合并方案各种差异
	@RequestMapping(value = "/checkMerge")
	public void checkMerge( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		final List<MybasicBank> mybasicBanks = myBasicBankService.findCHeck();
		final String sessionId = request.getSession().getId();
		new Thread(){
			public void run() {
				try {
					log.info("checkMerge start:");
					for(MybasicBank mybasicBank:mybasicBanks){
						log.info("checkMerge:"+mybasicBank.getSechma()+" :"+mybasicBank.getRealName());
						checkMergeBank(mybasicBank);
					}	
					log.info("checkMerge end:");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			    //提醒前台
				remind(sessionId);
			};
		}.start();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "成功");
		map.put("flag", true);
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
	}
	
	//银行信息探测
	//比较哪些合并方案  日结方案 ftp类型 清分类型  后台service端口 是否空文件
	//是否冻结 是否补贴 威富通服务费是否分离 是否自动清分 acceptorg platform APP_URL
	private void checkMergeBank( MybasicBank mybasicBank )  {
		try {
			String url = mybasicBank.getUrl();
			String pwd = mybasicBank.getPwd();
			String name = mybasicBank.getUserName();
			String schame = mybasicBank.getSechma();
			String testschame = mybasicBank.getTestSechma();
			if(StringUtils.isNotBlank(testschame)){//针对某些银行schame连不上数据库 只能用测试库 但测试库schame 和实际不一样情况特殊处理 比如徽商 hsb - hsbbank
				schame = testschame;
			}
			url = "jdbc:oracle:thin:@"+url;
			DBTempory dbTempory = new DBTempory(url,name,pwd,schame);
			//检查合并方案
			 String sqlM="select * from "+schame+ ".CLE_BILL_MERGE_CONF where 1=1 and PHYSICS_FLAG=1 ORDER BY PRIORITY";
			 StringBuffer mergeType = new StringBuffer();
			 List<Map<String,String>> maplist = dbTempory.getDatasByTable(sqlM);
			 if(maplist.isEmpty()){
				 mergeType.append("");
			 }else{
				 for(Map<String,String> map:maplist){
					if(map.containsValue("orgMergeStrategy")){
						 mergeType.append("按商户/机构维度合并");
					 }else if(map.containsValue("cleaningTypeOrgMergeStrategy")){
						 mergeType.append("按照清分类型 + 商户/机构维度合并");
					 }else if(map.containsValue("cleanTypeApiOrgMergeStrategy")){
						 mergeType.append("按照清分类型 + API提供方+ 商户/机构维度合并");
					 }else if(map.containsValue("channelDepositMergeStrategy")){
						 mergeType.append("对备付金清分记录进行合并");
					 } else if(map.containsValue("apiProviderOrgMergeStrategy")){
						 mergeType.append("按照API提供方+商户/机构维度合并");
					 } else if(map.containsValue("accountCodeMergeStrategy")){
						 mergeType.append("仅按持卡人姓名+结算卡号的维度合并");
					 } else if(map.containsValue("specifiedAccountMergeStrategy")){
						 mergeType.append("对特定的结算账户按照账户维度+清分类型细项进行合并");
					 }  
					
				 }			 
			 }
			 mybasicBank.setMerge(mergeType.toString());
			//检查清分类型CLE_CLEANING_PROCESS_CONF CLEANING_PROCESS_CLASS ftp类型
			 String sqlp ="select * from "+schame+ ".CLE_CLEANING_PROCESS_CONF where 1=1 and PHYSICS_FLAG=1";
			 List<Map<String,String>> maplistp = dbTempory.getDatasByTable(sqlp);
			 if(maplistp.isEmpty()){
				 mybasicBank.setCleaingProcess("");
			 }else{
				 StringBuffer sp = new StringBuffer();
				 for(Map<String,String> map:maplistp){
					 sp.append("["+map.get("CLEANING_PROCESS_CLASS")+"]");
					 String ftp = map.get("DOWNLOAD_FTP");
					 if(ftp.contains("\"port\":21")){
						 mybasicBank.setFtp("ftp");
					 }else{
						 mybasicBank.setFtp("sftp");
					 }
					
				 }
				 mybasicBank.setCleaingProcess(sp.toString());
			 }
			 
			 if(mybasicBank.getCleaingProcess().contains("biaozhunBankCleanFileGenerateService")){
				 mybasicBank.setCleaingProcessOverride("标准版");
			 }else{
				 mybasicBank.setCleaingProcessOverride("定制版");
			 }
			//service端口通过select TASK_TARGET from TASK_MANAGE where TASK_ID='TASK_INIT'
			 String sqlport="select TASK_TARGET from "+schame+ ".TASK_MANAGE where 1=1 and TASK_ID='TASK_INIT'";
			 List<Map<String,String>> maplistport = dbTempory.getDatasByTable(sqlport);
			 int serverport = 0;
			 if(maplistport.isEmpty()){
				 serverport = 0;
			 }else{
				 
				 for(Map<String,String> map:maplistport){
					 String urlstr = map.get("TASK_TARGET");
					  URL urls = new URL(urlstr);
					  serverport = urls.getPort();
					  
				 }
				
			 }
			 mybasicBank.setServicePort(serverport+"");
			 
			//检查日结方案CLE_ACC_PLAN_CONF PLAN_NAME
			 String sqlpan="select * from "+schame+ ".CLE_ACC_PLAN_CONF where 1=1 and PHYSICS_FLAG=1";
			 List<Map<String,String>> maplistplan = dbTempory.getDatasByTable(sqlpan);
			 if(maplistplan.isEmpty()){
				 mybasicBank.setPlanName("");
			 }else{
				 StringBuffer sp = new StringBuffer();
				 for(Map<String,String> map:maplistplan){
					 sp.append(map.get("PLAN_NAME")+"/");
				 }
				 mybasicBank.setPlanName(sp.toString());
			 }
			 
			/*//检查文件类型CLE_CLEANING_PROCESS_LOG
			 String sqllog="SELECT DISTINCT(SUBSTR(CLEANING_NAME,INSTR(CLEANING_NAME, '.', -1) + 1,LENGTH(CLEANING_NAME))) as FILENAME  from "+schame+ ".CLE_CLEANING_PROCESS_LOG where PHYSICS_FLAG=1";
			 String fileType = "未知/";
			 List<Map<String,String>> loglist = dbTempory.getDatasByTable(sqllog);
			 if(loglist.isEmpty()){
				  
			 }else{
				 int size = loglist.size();
				 Map<String,String> mlogs = loglist.get(size-1);
				 String fileName = mlogs.get("FILENAME");
				 if(StringUtils.isNotBlank(fileName)){
					 fileType = fileName+"/";
				 }
			 }*/
			//检查是否空文件CMS_SYS_PARAMETER  ClEANING_FILE_IS_NULL
			 String nulllog="SELECT PARAMETER_VALUE from  "+schame+ ".CMS_SYS_PARAMETER WHERE PARAMETER_NAME='ClEANING_FILE_IS_NULL'";
			 String nulllType = "无";
			 List<Map<String,String>> nulllogllist = dbTempory.getDatasByTable(nulllog);
			 if(nulllogllist.isEmpty()){
				  
			 }else{
				 Map<String,String> mlogs = nulllogllist.get(0);
				 String val = mlogs.get("PARAMETER_VALUE");
				 if(StringUtils.isNotBlank(val)){
					 if("TRUE".equalsIgnoreCase(val)){
						 nulllType = "有";
					 }else{
						 nulllType = "无";
					 }
				 }
			 }
			 mybasicBank.setNullfile(nulllType);
			//mybasicBank.setFls2(nulllType);
			 
			//检查是否冻结CMS_SYS_PARAMETER  FREEZE_CLEANING_BIZ
		    String biz="SELECT PARAMETER_VALUE from  "+schame+ ".CMS_SYS_PARAMETER WHERE PARAMETER_NAME='FREEZE_CLEANING_BIZ'";
			String bizType = "否";
			 List<Map<String,String>> bizlist = dbTempory.getDatasByTable(biz);
			 if(bizlist.isEmpty()){
				  
			 }else{
				 Map<String,String> mlogs = bizlist.get(0);
				 String val = mlogs.get("PARAMETER_VALUE");
				 if(StringUtils.isNotBlank(val)){
					 if("TRUE".equalsIgnoreCase(val)){
						 bizType = "是";
					 }else{
						 bizType = "否";
					 }
				 }
			 }
			 mybasicBank.setIsfreez(bizType);
			 
			//检查是否补贴CMS_SYS_PARAMETER  CMS_OPEN_MCH_SUBSIDY_RATE
		      biz="SELECT PARAMETER_VALUE from  "+schame+ ".CMS_SYS_PARAMETER WHERE PARAMETER_NAME='CMS_OPEN_MCH_SUBSIDY_RATE'";
			  bizType = "否";
			  bizlist = dbTempory.getDatasByTable(biz);
			 if(bizlist.isEmpty()){
				  
			 }else{
				 Map<String,String> mlogs = bizlist.get(0);
				 String val = mlogs.get("PARAMETER_VALUE");
				 if(StringUtils.isNotBlank(val)){
					 if("1".equalsIgnoreCase(val)){
						 bizType = "是";
					 }else{
						 bizType = "否";
					 }
				 }
			 }
			 mybasicBank.setIssubdiy(bizType);
				 
			//检查威富通服务费是否分离CMS_SYS_PARAMETER  CLEANING_CHANNEL_WFT_ID_LIST
		      biz="SELECT PARAMETER_VALUE from  "+schame+ ".CMS_SYS_PARAMETER WHERE PARAMETER_NAME='CLEANING_CHANNEL_WFT_ID_LIST'";
			  bizType = "否";
			  bizlist = dbTempory.getDatasByTable(biz);
			 if(bizlist.isEmpty()){
				  
			 }else{
				 Map<String,String> mlogs = bizlist.get(0);
				 String val = mlogs.get("PARAMETER_VALUE");
				 if(StringUtils.isNotBlank(val)){
					 bizType = "是";
				 }
			 }
			 mybasicBank.setIsbroken(bizType);
			 
			//检查是否自动清分CMS_SYS_PARAMETER  AUTO_CLEANING_CONF
		      biz="SELECT PARAMETER_VALUE from  "+schame+ ".CMS_SYS_PARAMETER WHERE PARAMETER_NAME='AUTO_CLEANING_CONF'";
			  bizType = "否";
			  bizlist = dbTempory.getDatasByTable(biz);
			 if(bizlist.isEmpty()){
				  
			 }else{
				 Map<String,String> mlogs = bizlist.get(0);
				 String val = mlogs.get("PARAMETER_VALUE");
				 if("0".equalsIgnoreCase(val)){
					 bizType = "否";
				 }else{
					 bizType = "是";
				 }
			 }
			 mybasicBank.setAutoclean(bizType);
			 
			 
			 //检查是acceptorg select t.channel_id from zhifu.CMS_CHANNEL t where t.CHANNEL_TYPE = 1  and rownum = 1
		      biz="select t.channel_id CHAN from  "+schame+ ".CMS_CHANNEL t  where t.CHANNEL_TYPE = 1  and rownum = 1";
			  bizType = "DEFAULT";
			  bizlist = dbTempory.getDatasByTable(biz);
			 if(bizlist.isEmpty()){
				  
			 }else{
				 Map<String,String> mlogs = bizlist.get(0);
				 String val = mlogs.get("CHAN");
				  bizType = val;
			 }
			 mybasicBank.setAcceptorg(bizType);
			 
			 //检查是platform select t.channel_id from zhifu.CMS_CHANNEL t where t.CHANNEL_TYPE = 1  and rownum = 1
			  biz="SELECT PARAMETER_VALUE from  "+schame+ ".CMS_SYS_PARAMETER WHERE PARAMETER_NAME='PLATFORM_FLAG'";
			  bizType = "DEFAULT";
			  bizlist = dbTempory.getDatasByTable(biz);
			 if(bizlist.isEmpty()){
				  
			 }else{
				 Map<String,String> mlogs = bizlist.get(0);
				 String val = mlogs.get("PARAMETER_VALUE");
				  bizType = val;
			 }
			 mybasicBank.setPlatform(bizType);
			 
			 
			 //检查是select APP_URL from CMS_APP where rownum = 1
			  biz="select APP_URL from  "+schame+ ".CMS_APP WHERE  rownum = 1";
			  bizType = "";
			  bizlist = dbTempory.getDatasByTable(biz);
			 if(bizlist.isEmpty()){
				  
			 }else{
				 Map<String,String> mlogs = bizlist.get(0);
				 String val = mlogs.get("APP_URL");
				  bizType = val;
				  if(StringUtils.isNotBlank(bizType)){
					  try {
						  URL appurl = new URL(bizType);
						  bizType="https://"+appurl.getHost();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				  }
			 }
			 dbTempory.close();
			 //WebSite 不刷新了
			// mybasicBank.setWebSite(bizType);
			 mybasicBank.setUpdateTime(new Date());
			 myBasicBankService.update(mybasicBank);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	 
	}
	 
	 //服务检测
	@RequestMapping(value = "/checkMonitor")
	public void checkMonitor( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		final List<MybasicBank> mybasicBanks = myBasicBankService.findCHeck();
		final String sessionId = request.getSession().getId();
		new Thread(){
			public void run() {
				for(MybasicBank mybasicBank:mybasicBanks){
					try {
						int issend = mybasicBank.getIssend();
						if(issend!=CommonUtil.EABLE){
							log.info("issend:"+issend+" issend!=1配置无需发送邮件{}"+mybasicBank.getRealName());
							continue;
						}
						 //异步监控
						executorService.submit(new WebSiteMonitor(myBasicBankService,mybasicBank,monitorEmailReport));
						
					} catch (Exception e) {
						 continue;
					}
					
					
				}
			    //提醒前台
				remind(sessionId);
			};
		}.start();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "成功");
		map.put("flag", true);
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
	}
	
	 //比较差异
	@RequestMapping(value = "/check")
	public void check( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		final List<MybasicBank> mybasicBanks = myBasicBankService.findCHeck();
		final String sessionId = request.getSession().getId();
		final String endTime = getdb_compare_end_time(request);

		new Thread(){
			public void run() {
				for(MybasicBank mybasicBank:mybasicBanks){
					try {
						findAllCompare(mybasicBank,sessionId,endTime);
					} catch (Exception e) {
						 continue;
					}
					
					
				}
			    //提醒前台
				remind(sessionId);
			};
		}.start();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "成功");
		map.put("flag", true);
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
	}
	

	//程序自动化比较数据结果
	private void findAllCompare(MybasicBank mybasicBank,String sessionId,String endTime) {
		 
		StringBuffer checkContent = new StringBuffer();
		String url = mybasicBank.getUrl();
		String pwd = mybasicBank.getPwd();
		String name = mybasicBank.getUserName();
		String schame = mybasicBank.getSechma();
		try {
			url = "jdbc:oracle:thin:@"+url;
			//Map<String,List<Column>> colMaps = new HashMap<String, List<Column>>();
			final Map<String,Table> colMaps = new HashMap<String, Table>();
			DBUtilBatch dBUtilBatch = new DBUtilBatch();
			Map<String,Table> tables= dBUtilBatch.getAllTableNamesAndCols(null,endTime,null);
			dBUtilBatch.close();
			List<Column> u= new ArrayList<Column>();
			int total=tables.size();
			String[] codes = new String[total];
			int i  = 0;
			for (Map.Entry<String, Table> entry : tables.entrySet()) {
				Table t = entry.getValue();
				Column col = new Column();
				col.setCode(t.getName());
				col.setName(t.getComment());

				colMaps.put(t.getName(),t);
				
				codes[i] = t.getName();
				u.add(col);
				i++;
			}
	 
			DBTempory db = new DBTempory(url,name,pwd,schame);
			db.setCheckOrSeq(false);//不比较约束和序列
			 Map<String,ColExist> resulCom = db.checkTableNames(schame.toUpperCase(),colMaps,codes);
			 db.close();
			 for(Column column:u){
				String code = column.getCode().toUpperCase();
				ColExist ccolExist = resulCom.get(code);
				if(ccolExist!=null){
					column.setCompare(ccolExist.isCompare());
					column.setTableCompare(ccolExist.isTableCompare());
					column.setColCompare(ccolExist.isColCompare());
					if(!ccolExist.isTableCompare()){
						checkContent.append(code+"【表】缺失 |");
					}
					if(!ccolExist.isColCompare()){
						checkContent.append(code+"【列】缺失 |");
					}
					
				}
				 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
		mybasicBank.setCheckContent(checkContent.length()+" "+StringUtils.substring(checkContent.toString(), 0,3000));
		mybasicBank.setCheckResult(StringUtils.isNotBlank(checkContent.toString())?CommonUtil.CHECK_RESULT_FAIL:CommonUtil.CHECK_RESULT_OK);
		mybasicBank.setCheckTime(DateUtils.getCurrentTime());
		myBasicBankService.update(mybasicBank);
	
	}
	
	
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	private void remind(String sessionId){
		 
	    //提醒前台
		 WsPool.sendMessageToAll("cmppareremind");
	  /* EventManager manager= EventManager.getInstance();
	    Map map=new HashMap();
	    map.put("cmppareremind_"+sessionId, "cmppareremind");
	    manager.removeEvent("cmppareremind_"+sessionId);
	    manager.createEvent(0, "cmppareremind_"+sessionId,map);*/
	}
	 
	 //获得db比较结束时间
	 private String getdb_compare_end_time(HttpServletRequest request){
		 String endTime= request.getParameter("endTime") ;
		if(endTime!=null&&endTime.equalsIgnoreCase(DateUtils.fmtDate("yyyy-MM-dd"))){
			//这里需要比较是不是今天的时间 ，是因为如果是今天上传脚本 ddl的last_analyzed分析时间是null,会导致查询不到信息
			endTime = null;
		}
		if(StringUtils.isNotBlank(endTime)){
			endTime +=" 23:59:59";
		}else{
			endTime = null;
		}
		return endTime;
	 }
}
