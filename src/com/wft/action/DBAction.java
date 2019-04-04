package com.wft.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.wft.db.CacheUtil;
import com.wft.db.ColExist;
import com.wft.db.Column;
import com.wft.db.Constraints;
import com.wft.db.DBTempory;
import com.wft.db.DBUtil;
import com.wft.db.DBUtilBatch;
import com.wft.db.Indexs;
import com.wft.db.Table;
import com.wft.freemarker.GenerateSQLUitl;
import com.wft.model.FileHistory;
import com.wft.model.MyDown;
import com.wft.model.MyParamter;
import com.wft.service.FileHistoryService;
import com.wft.service.MyDownService;
import com.wft.service.MyParamterService;
import com.wft.util.CommonUtil;
import com.wft.util.CommonUtil.BatchData;
import com.wft.util.DateUtils;
import com.wft.util.FileUtil;
import com.wft.util.ReplaceSchema;
import com.wft.util.SystemMessage;
import com.wft.util.UploadPathUtil;
import com.wft.websocket.WsPool;

/**
 * @author admin
 * 基础表管理
 */
@Controller
@RequestMapping(value = "/dbs")
public class DBAction extends BaseAction{
	 
	private final static Logger log = Logger.getLogger(DBAction.class);
	
	@Autowired
	private FileHistoryService fileHistoryService;
	@Autowired
	private MyDownService myDownService;
	@Autowired
	private MyParamterService myParamterService;
	
	private final String newLine = System.getProperty("line.separator"); 
	
	 
	//查询所有基本表
	@RequestMapping(value = "/findAll")
	public void findAll(HttpServletRequest request,HttpServletResponse response)throws Exception{
	 
		Map<String, Object> map=new HashMap<String, Object>();
		String tableName = request.getParameter("tableName");
		List<Column> u= DBUtil.getAllTableNamesByTableName(tableName);
		int total=u.size();
		map.put("rows", u);
		map.put("total", total);
		String str=JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
	
	//查询某一基本表所有字段
	@RequestMapping(value = "/findAllCloumns")
	public void findAllCloumns(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String table= request.getParameter("table") ;
	 
		Map<String, Object> map=new HashMap<String, Object>();
		 
		List<Column> u= DBUtil.getOracleTCloumns(table.toUpperCase(),null,null);
		int total=u.size();
		map.put("rows", u);
		map.put("total", total);
		String str=JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
	
	//查询某一基本表所有的数据
	@RequestMapping(value = "/findAlldatas")
	public void findAlldatas(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String table= request.getParameter("table") ;
		 
		Map<String, Object> map=new HashMap<String, Object>();
		 
		List<Map<String,String>>  u= DBUtil.getDatasByTable(table.toUpperCase());
		int total=u.size();
		map.put("rows", u);
		map.put("total", total);
		String str=JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
		
		
	//清空所有缓存
	@RequestMapping(value = "/clear")
	public void clearDatas(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		//是否有人在比较中,如果在比较中不容许在比较
		boolean isCompare = false;
		MyParamter myParamter = myParamterService.findById(MyParamter.is_compare);
		 
		if(myParamter!=null){
			if("true".equalsIgnoreCase(myParamter.getPval())){
				isCompare = true;
			}
		}
		if(isCompare){
			map.put("result", "有人正在比对中,暂时无法清空缓存,请骚等...");
			map.put("flag", false);
		}else{
			String keys = request.getParameter("keys");
			if(StringUtils.isBlank(keys)){
				CacheUtil.getInstance().clear();
			}else{
				CacheUtil.getInstance().clear(keys);
			}
			
			map.put("result", "清空成功");
			map.put("flag", true);
		}
		
		String str = JSONObject.toJSONString(map);
	 
		response.getWriter().write(str);
	}	
	
		

		
		
	//	====================比较库=====================================
	
	//查询所有基本表与库比较差异
	@RequestMapping(value = "/findAllCompare")
	public void findAllCompare(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map=new HashMap<String, Object>();
		String durl= request.getParameter("url") ;
		if(!durl.startsWith("jdbc")){
			durl = "jdbc:oracle:thin:@"+durl;
		}
 
		final String url = durl;
		final String pwd= request.getParameter("pwd") ;
		final String name= request.getParameter("name") ;
		final String schame= request.getParameter("schame") ;
		//db结构比较开始时间
		final String startTime = getdb_compare_start_time(request);
		final String endTime = getdb_compare_end_time(request);

		//是否有人在比较中,如果在比较中不容许在比较
		boolean isCompare = false;
		final MyParamter myParamter = myParamterService.findById(MyParamter.is_compare);
		log.info("myParamter:"+myParamter);
		if(myParamter!=null){
			if("true".equalsIgnoreCase(myParamter.getPval())){
				isCompare = true;
			}
		}
		if(isCompare){
			map.put("result", "有人正在比对中,暂时无法比对,请骚等...");
			map.put("flag", false);
			String str=JSONObject.toJSONString(map);
			response.getWriter().write(str);
			return;
		}
		//探测哈是否可以连接
		try {
			DBTempory db = new DBTempory(url,name,pwd,schame);
			db.close();
		} catch (Exception e) {
			map.put("result",e.getMessage());
			map.put("flag", false);
			String str=JSONObject.toJSONString(map);
			response.getWriter().write(str);
			return;
		}
		
		
		//更新is_compare参数
		myParamter.setPval("true");
		myParamterService.update(myParamter);
		//开始比较
		
		//此处需要用线程比较耗时操作 防止前台挂死
		Future<Map<String, Object>> freuslt = threadExec.submit(new Callable<Map<String, Object>>() {
			@Override
			public Map<String, Object> call() throws Exception {
				Map<String, Object> map=new HashMap<String, Object>();
				try {
					
					//final Map<String,List<Column>> colMaps = new HashMap<String, List<Column>>();
					final Map<String,Table> colMaps = new HashMap<String, Table>();
					//多线程比较 
					//获取所有表
					List<Column> tabs = DBUtil.getAllTableNamesByTableName(null);
					//将tables分组 50 一个组 启用多线程比较 50一组表 是经过测试合理 多了会报closed异常
			    	 int tabsmaxSize = 50;
			    	 List<BatchData<Column>> tabsbatchDataList = CommonUtil.getBatchDataList(tabsmaxSize,tabs);
			    	 log.info("tabsbatchDataList  批次个数:"+tabsbatchDataList.size());
			    	 List<Future<Map<String,Table>>> tabsbatchcheckTableNamesTasks = new ArrayList<Future<Map<String,Table>>>();
			    	 for(final BatchData<Column> batch:tabsbatchDataList){
			    		 int index = batch.getBatchIndex();
	    				 log.info("tabsbatchDataList 批次:"+index);
	    				 List<Column> tabcols = batch.getDataList();
	    				 final List<String> tablelist = new ArrayList<String>();
	    				 for(Column col:tabcols){//Column - table
	    					 tablelist.add(col.getCode());
	    				 }
			    		 Future<Map<String,Table>> tabsfreuslt = threadExec.submit(new Callable<Map<String,Table>>() {
			    			 DBUtilBatch dBUtilBatch = new DBUtilBatch();
			    			   @Override
				     			public Map<String,Table> call() throws Exception {
				     				Map<String,Table> tables= dBUtilBatch.getAllTableNamesAndCols(startTime,endTime,tablelist);
				     				dBUtilBatch.close();
				     				return tables;
				     			}
				    		 });
			    		 //任务后台运行
				    	 tabsbatchcheckTableNamesTasks.add(tabsfreuslt);
						
			    	 }//end for
			    	 //所有结果
			    	 Map<String,Table> tables = new HashMap<String, Table>();
			    	 int tabtaskCounts = 0;
			    	 for(Future<Map<String,Table>> task:tabsbatchcheckTableNamesTasks){
			    		//多线程返回结果
			    		 Map<String,Table> resutlMap = task.get();
			    		 tables.putAll(resutlMap);
			    		 tabtaskCounts++;
			    	 }//end batchcheckTableNamesTasks get()
			    	 log.info("tabsbatchDataList 拿到结果批次个数done:"+tabtaskCounts);
			    	 
			    	 //开始与临时库比较
					List<Column> resutlUexis= new ArrayList<Column>();
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
						resutlUexis.add(col);
						i++;
					}	
					//将tables分组 20 一个组 启用多线程比较 20一组表 是经过测试合理 多了会报closed异常
			    	 int maxSize = 10;
			    	 List<BatchData<String>> batchDataList = CommonUtil.getBatchDataList(maxSize,Arrays.asList(codes));
			    	 log.info("batchDataList  批次个数:"+batchDataList.size());
			    	 List<Future<Map<String,ColExist>>> batchcheckTableNamesTasks = new ArrayList<Future<Map<String,ColExist>>>();
			    	 Map<String,ColExist> resulCom = new HashMap<String, ColExist>();
			    	 for(final BatchData<String> batch:batchDataList){
			    		 final DBTempory db = new DBTempory(url,name,pwd,schame);
			    		 int index = batch.getBatchIndex();
	    				 log.info("checkTableNames 批次:"+index);
	    				List<String> bachttables = batch.getDataList();
	    				 final String[] bachcodes = bachttables.toArray(new String[0]);
			    		 Future<Map<String,ColExist>> freuslt = threadExec.submit(new Callable<Map<String,ColExist>>() {
			     			@Override
			     			public Map<String,ColExist> call() throws Exception {
			     				Map<String,ColExist> result = db.checkTableNames(schame.toUpperCase(),colMaps,bachcodes);
			     				db.close();
			     				return result;
			     			}
			    		 });
			    		 //任务后台运行
			    		 batchcheckTableNamesTasks.add(freuslt);
			    		
			    	 }//end 比较表现出
					 int taskCounts = 0;
			    	 for(Future<Map<String,ColExist>> task:batchcheckTableNamesTasks){
			    		//多线程返回结果
			    		 Map<String,ColExist> resutlMap = task.get();
			    		 for( Entry<String, ColExist>  en:resutlMap.entrySet()){
			    			 resulCom.put(en.getKey(), en.getValue());
			    		 }
			    		 taskCounts++;
			    	 }//end batchcheckTableNamesTasks get()
			    	 log.info("batchDataList 拿到结果批次个数done:"+taskCounts);
			    	 
			    	 
					for(Column column:resutlUexis){//把值赋值给前台
						//返回结果
						ColExist ccolExist = resulCom.get(column.getCode().toUpperCase());
						if(ccolExist!=null){
							column.setCompare(ccolExist.isCompare());
							column.setTableCompare(ccolExist.isTableCompare());
							column.setColCompare(ccolExist.isColCompare());
							column.setCheckSame(ccolExist.isCheckSame());
							column.setSeqSame(ccolExist.isSeqSame());
						}
						 
					}
					map.put("rows", resutlUexis);
					map.put("total", total);
					map.put("result", "成功");
					map.put("flag", true);
				} catch (Exception e) {
					e.printStackTrace();
					map.put("result",e.getMessage());
					map.put("flag", false);
				}finally{
					//更新is_compare参数
					myParamter.setPval("false");
					myParamterService.update(myParamter);
				}
				
				return map;
				 
			}
		});// end
		
		try {
			Map<String,Object> resultmap = freuslt.get(15, TimeUnit.SECONDS);
			map = resultmap;
			log.info("resultmap返回:"+map);
		} catch (TimeoutException e) {
			map.put("result","正在后台比较 请稍等....");
			map.put("flag", false);
		} catch (Exception e) {
			map.put("result"," 请稍等....");
			map.put("flag", false);
		}
		
	
		String str=JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
	
	
	//查询某一基本表与库比较差异
	@RequestMapping(value = "/findAllCompareCloumns")
	public void findAllCompareCloumns(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		String url= request.getParameter("url") ;
		if(!url.startsWith("jdbc")){
			url = "jdbc:oracle:thin:@"+url;
		}
		
		String pwd= request.getParameter("pwd") ;
		String name= request.getParameter("name") ;
		String schame= request.getParameter("schame") ;
		String table= request.getParameter("table") ;
		//db结构比较开始时间
		String startTime = getdb_compare_start_time(request);
		String endTime = getdb_compare_end_time(request);

		//是否有人在比较中,如果在比较中不容许在比较
		boolean isCompare = false;
		final MyParamter myParamter = myParamterService.findById(MyParamter.is_compare);
		log.info("myParamter:"+myParamter);
		if(myParamter!=null){
			if("true".equalsIgnoreCase(myParamter.getPval())){
				isCompare = true;
			}
		}
		if(isCompare){
			map.put("result", "有人正在比对中,暂时无法比对,请骚等...");
			map.put("flag", false);
			String str=JSONObject.toJSONString(map);
			response.getWriter().write(str);
			return;
		}
		//更新is_compare参数
		myParamter.setPval("true");
		myParamterService.update(myParamter);
		//开始比较
		List<Column> u=  null;
		try {
			DBTempory db = new DBTempory(url,name,pwd,schame);
			 //指定某一表的差异
			 u = DBUtil.getOracleTCloumns(table.toUpperCase(),startTime,endTime);
			List<Column> comu = db.sysoutOracleTCloumns(table.toUpperCase(), schame.toUpperCase());
			db.close();
			for(Column column:u){
				column.setCompare(comu.contains(column));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//更新is_compare参数
			myParamter.setPval("false");
			myParamterService.update(myParamter);
		}
		int total=u.size();
		map.put("rows", u);
		map.put("total", total);
		String str=JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
	
	
  //	====================与标准库比较生成脚本=====================================
	
	//查询某一基本表与库比较差异生成脚本(主要通过时间差异来下载脚本)
	@RequestMapping(value = "/generateScript")
	public void generateScript(HttpServletRequest request,HttpServletResponse response)throws Exception{
		final String tables= request.getParameter("tables") ;
		final String schame= request.getParameter("schame") ;
		String url= request.getParameter("url") ;
		if(!url.startsWith("jdbc")){
			url = "jdbc:oracle:thin:@"+url;
		}
		final String urlC = url;
		final String pwd= request.getParameter("pwd") ;
		final String name= request.getParameter("name") ;
		//db结构比较开始时间
		final String startTime = getdb_compare_start_time(request);
		final String endTime= getdb_compare_end_time(request);
		
		log.info("tables:"+tables);
		log.info("schame:"+schame);
		log.info("url:"+url);
		log.info("pwd:"+pwd);
		log.info("name:"+name);
		//表结构差异_0缺表 _1缺列  B|1缺列 C|2缺约束  D|3缺序列
		final String[] tabless = tables.split(",");
		final String ip = getRemortIP(request);
		final String sessionId = request.getSession().getId();
		Map<String, Object> map=new HashMap<String, Object>();
		//是否有人在比较中,如果在比较中不容许在比较
		boolean isCompare = false;
		final MyParamter myParamter = myParamterService.findById(MyParamter.is_compare);
		log.info("myParamter:"+myParamter);
		if(myParamter!=null){
			if("true".equalsIgnoreCase(myParamter.getPval())){
				isCompare = true;
			}
		}
		if(!isCompare&&StringUtils.isNotBlank(tables)&&StringUtils.isNotBlank(schame)){
			myParamter.setPval("true");
			myParamterService.update(myParamter);
			 //通过线程下载
			 new Thread(){
				 public void run() {
					 Date minDate = null;
					 Date maxDate =  null;
					try {
						List<Column> allList = new ArrayList<Column>();
						//List<Column> u= DBUtil.getTableNamesByTable(tabless);
						for(String table:tabless ){
							  //表结构差异表结构差异A|0缺表 B|1缺列 C|2缺约束  D|3缺序列
							//主要通过时间差异来下载脚本 不需要关注 C|2缺约束  D|3缺序列
							String[]  tt = table.split("\\|");
							table = tt[0];
							 //指定某一表的差异
							List<Column> basicCols= DBUtil.getOracleTCloumns(table.toUpperCase(),startTime,endTime);
							try {
								DBTempory db = new DBTempory(urlC,name,pwd,schame);
								List<Column> tepCols = db.sysoutOracleTCloumns(table.toUpperCase(), schame.toUpperCase());
								db.close();
								for(Column column:basicCols){
									if(!tepCols.contains(column)){//去掉已有的
										allList.add(column);
									}
									 
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						log.info("allList:"+allList.size());
						for(Column column:allList){
							String lastAnalyzed = column.getLastAnalyzed();
							 Date dlastAnalyzed = DateUtils.formdate(lastAnalyzed);
							if(minDate!=null){
								if(DateUtils.compareDate(minDate,  dlastAnalyzed)>0){
									minDate = dlastAnalyzed;
								}
								
							}else{
								minDate = dlastAnalyzed;
							
							}
							
							if(maxDate!=null){
								if(DateUtils.compareDate(maxDate,  dlastAnalyzed)<0){
									maxDate =  dlastAnalyzed;
								}
							}else{
								 
								maxDate = dlastAnalyzed;
							}
						}
						
						log.info("minDate:"+minDate);
						log.info("maxDate:"+maxDate);//
						/*if(StringUtils.isNotBlank(endTime)){
						 * //如果前台选择了时间则需要直接把最大时间设置为选择时间
						 * 此处逻辑不明删除 20171215
							 Date eendTime = DateUtils.formdate(endTime);
							 maxDate = eendTime;
							 log.info("maxDate:"+maxDate);
						}*/
						aysncDown(schame,ip,minDate,maxDate,sessionId);
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						
						myParamter.setPval("false");
						myParamterService.update(myParamter);
					}
				 };
			 }.start();
			//Thread.sleep(1000);
			map.put("result", "骚等片刻, 请在增量下载记录功能菜单里下载");
			map.put("flag", true);
		}else{
			map.put("result", "请选比对后再创建...");
			if(isCompare){
				map.put("result", "有人正在比对中,暂时无法比对,请骚等...");
			}
			map.put("flag", false);
		}
		
		String str=JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
	
	//异步生成脚本
	private void aysncDown(String schame,String ip, Date minDate,Date maxDate,String sessionId) throws IOException {
		try {
			//readme.txt内容
			List<String> readMeCotent = new ArrayList<String>();
			readMeCotent.add(0, "");
			readMeCotent.add("下载包含的脚本:"+CommonUtil.LINE);
			
	    	String way = "按时间跨度下载脚本";
	    	StringBuffer content = new StringBuffer();
		    String remark = "通过比较库创建脚本" ;
		    Date minChoiceDate = null;
			Date maxChoiceDate =  null;
		    List<FileHistory> u = fileHistoryService.findAllByUpdateTime(minDate,maxDate);;
			log.info(u.size());
			List<File> fileDDLs = new ArrayList<File>();
			List<File> fileDMLs = new ArrayList<File>();
			for(FileHistory fileHistory:u){
				File destFile =  new File(UploadPathUtil.getTempPath()+fileHistory.getServiceUrl());
				//标记下载的文件更前台显示
				content.append(destFile.getName()+"  "+fileHistory.getFileType()+",");
				readMeCotent.add(destFile.getName()+"   类型:"+fileHistory.getFileType()+"  ");
				if("DDL".equalsIgnoreCase(fileHistory.getFileType())){
					fileDDLs.add(destFile);
				}else{
					fileDMLs.add(destFile);
				}
				if(minChoiceDate!=null){ //ddl下载实际区间，防止前台人员误操作,选择区间范围
					if(DateUtils.compareDate(minChoiceDate,  fileHistory.getCreateTime())>0){
						minChoiceDate = fileHistory.getCreateTime();
					}
					 
				}else{
					minChoiceDate = fileHistory.getCreateTime();
					 
				}
				
				if(maxChoiceDate!=null){ //ddl下载实际区间，防止前台人员误操作,选择区间范围
					if(DateUtils.compareDate(maxChoiceDate,  fileHistory.getCreateTime())<0){
						maxChoiceDate = fileHistory.getCreateTime();
					}
				}else{
					 
					maxChoiceDate = fileHistory.getCreateTime();
				}
			}
			List<File> files = new ArrayList<File>();
			if(fileDDLs.size()>0){
				File ddlFile = ReplaceSchema.mergeSql(schame, "ddl", UploadPathUtil.getScriptPath(), fileDDLs);
				files.add(ddlFile);
			}
			if(fileDMLs.size()>0){
				File dmlFile = ReplaceSchema.mergeSql(schame, "dml",UploadPathUtil.getScriptPath(), fileDMLs);
				files.add(dmlFile);
			}
			
			//命名规则
	        String fileName = "";//不含路径
	        String filePath = "";//不包含最后的 /，不含文件名称
	        File destFile = null;
	      //每个压缩脚本添加注意事项
	        File readerme = new File(UploadPathUtil.getScriptPath()+schame,"readme.txt");
	        if(readerme.exists()){
	        	readerme.delete();
	        }
	        readMeCotent.set(0, "下载时间区间:【"+DateUtils.formdate(minChoiceDate)+","+DateUtils.formdate(maxChoiceDate)+"】 "+CommonUtil.LINE);
			FileUtils.writeLines(readerme, readMeCotent);
	        if (files!=null && files.size()>=1){
	            SimpleDateFormat dateformat=new SimpleDateFormat("yyyyMMddhhmmss");   
	            filePath = files.get(0).getParent();
	            fileName = "script_"+dateformat.format(new Date())+"_"+schame+".zip";
	            //追加注释脚本
	            files.add(readerme);
	            FileUtil.zip(filePath+"/"+fileName, files, "UTF-8", false);
	            destFile = new File(filePath+"/"+fileName);
	            //记录下载
		        MyDown myDown = new MyDown();
		        myDown.setContent(StringUtils.substring(content.toString(), 0, 3000));
		        myDown.setCreateTime(new Date());
		        myDown.setDownType(1);
		        myDown.setIp(ip);
		        myDown.setSechma(schame);
		        myDown.setUpdateTime(new Date());
		        myDown.setUrl("script/"+schame+"/"+destFile.getName());
		        myDown.setWay(way);
		        myDown.setRange("【"+DateUtils.formdate(minDate)+","+DateUtils.formdate(maxDate)+"】 ");
		        myDown.setRemark(remark);
		        if(minChoiceDate!=null&maxChoiceDate!=null){
		        	  myDown.setRealRange("【"+DateUtils.formdate(minChoiceDate)+","+DateUtils.formdate(maxChoiceDate)+"】 ");
		        }
		      
		        myDownService.add(myDown);
		      
		        //提醒前台
			    remind(sessionId,"userremind");
	        } 
	 
		} catch (Exception e) {
			e.printStackTrace();
			  //提醒前台
		    remind(sessionId,"aysncDownError");
		}
		
	}
	
	//===================询某一基本表与库比较差异自动生成脚本临时ddl=需要做大量工作优化===================================================================
	//查询某一基本表与库比较差异自动生成脚本临时ddl
	@RequestMapping(value = "/generateTempDDLScript")
	public void generateTempDDLScript(HttpServletRequest request,HttpServletResponse response)throws Exception{
		final String tables= request.getParameter("tables") ;
		final String schame= request.getParameter("schame") ;
		String url= request.getParameter("url") ;
		if(!url.startsWith("jdbc")){
			url = "jdbc:oracle:thin:@"+url;
		}
		final String urlC = url;
		final String pwd= request.getParameter("pwd") ;
		final String name= request.getParameter("name") ;
		final String[] genTypes= request.getParameterValues("genTypes");//生成类型
		//db结构比较开始时间
		final String startTime = getdb_compare_start_time(request);
		final String endTime= getdb_compare_end_time(request);
		final Date eendTime = DateUtils.formdate(endTime);
	 
		log.info("tables:"+tables);
		log.info("schame:"+schame);
		log.info("url:"+schame);
		log.info("pwd:"+pwd);
		log.info("name:"+name);
		//表结构差异_0缺表 _1缺列
		final String[] tabless = tables.split(",");
		final String ip = getRemortIP(request);
		final String sessionId = request.getSession().getId();
		Map<String, Object> map=new HashMap<String, Object>();
		//是否有人在比较中,如果在比较中不容许在比较
		boolean isCompare = false;
		final MyParamter myParamter = myParamterService.findById(MyParamter.is_compare);
	 
		if(myParamter!=null){
			if("true".equalsIgnoreCase(myParamter.getPval())){
				isCompare = true;
			}
		}
		if(!isCompare&&StringUtils.isNotBlank(tables)&&StringUtils.isNotBlank(schame)){
			myParamter.setPval("true");
			myParamterService.update(myParamter);
			 //通过线程下载
			 new Thread(){
				 public void run() {
					try {
						
						boolean isCreate = false;// 创建表
						boolean isColum = false;// 创建l列
						
						boolean isCheck = false;// 创建Check
						boolean isSeq = false;//创建表Seq
						
						String genType = null;
						if(genTypes==null||genTypes.length==0){//默认全选
							genType = CommonUtil.GEN_TYPE_STR;
						}else{
							genType = ArrayUtils.toString(genTypes);
						}
					    if(genType.contains(CommonUtil.GEN_TYPE_0)){
					    	isCreate = true;
					    }if(genType.contains(CommonUtil.GEN_TYPE_1)){
					    	isColum = true;
					    }if(genType.contains(CommonUtil.GEN_TYPE_2)){
					    	isCheck = true;
					    }if(genType.contains(CommonUtil.GEN_TYPE_3)){
					    	isSeq = true;
					    } 
						
						//查找某库所有的序列
						DBTempory seqdb = new DBTempory(urlC,name,pwd,schame);
						Set<String> seqsets = seqdb.getSequenceByTable();
						seqdb.close();
						
						StringBuffer sqlBuffer = new StringBuffer();
						for(String table:tabless ){
							List<Column> allList = new ArrayList<Column>();
							//表结构差异_0缺表 _1缺列，通过前台传输 A|1,B|0这种方式
							 //表结构差异表结构差异A|0缺表 B|1缺列 C|2缺约束  D|3缺序列
							//查询某一基本表与库比较差异自动生成脚本临时ddl 需要关注 C|2缺约束  D|3缺序列
							String[] tt = table.split("\\|");
							table = tt[0];
							String exist = tt[1];
							DBTempory db =  new DBTempory(urlC,name,pwd,schame);
							 
							if((isCreate||isColum)&&(CommonUtil.GEN_TYPE_0.equals(exist)||CommonUtil.GEN_TYPE_1.equals(exist))){
								//优化 ，表结构差异_0缺表 _1缺列 才去寻找getOracleTCloumns
								 //指定某一表的差异
								try {
									List<Column> basicCols= DBUtil.getOracleTCloumns(table.toUpperCase(),startTime,endTime);
									
									List<Column> tepCols = db.sysoutOracleTCloumns(table.toUpperCase(), schame.toUpperCase());
									
									for(Column column:basicCols){
										if(!tepCols.contains(column)){
											allList.add(column);
										}
										 
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								log.info("allList:"+allList.size());
							}
							
							if(isCreate&&CommonUtil.GEN_TYPE_0.equals(exist)){
								log.info("table:表缺失 需要创建表,序列 ,约束，索引"+table);
								
								//0.创建表
								sqlBuffer.append( GenerateSQLUitl.getCreateSql(allList, schame, table));
								//是否创建序列
								boolean iscreateseq = false;
								for(String seq:seqsets){
									if(seq.contains(table)){
										iscreateseq = true;
										break;
									}
								}
								//1.创建序列
								if(iscreateseq){
									String seq = DBUtil.getSequenceByTable(table,schame);
									if(StringUtils.isNotBlank(seq)){
										sqlBuffer.append( "--create sequence");
										sqlBuffer.append(newLine);
										sqlBuffer.append( seq);
										sqlBuffer.append(newLine);
									}
								}
								
								//2.创建约束条件
								List<Constraints> constraintsls = DBUtil.getConstraintsByTable(table);
								if(CollectionUtils.isNotEmpty(constraintsls)){
									sqlBuffer.append(GenerateSQLUitl.getCreateConstraintsSql(constraintsls, schame, table));
									
								}

								List<Indexs> indexsls = DBUtil.getIndexsByTable(table);
								//3.创建索引信息
								if(CollectionUtils.isNotEmpty(indexsls)){
									sqlBuffer.append(GenerateSQLUitl.getCreateIndexsSql(indexsls, schame, table));
									
								}
								
							}else if(isColum&&CommonUtil.GEN_TYPE_1.equals(exist)){
								//todo  
								//保证比较库 与基准库的约束条件一样
								log.info("table:列缺失"+table);
								sqlBuffer.append( GenerateSQLUitl.getAlertSql(allList, schame, table));
							}
							else if(isCheck&&CommonUtil.GEN_TYPE_2.equals(exist)){
								//todo C|2缺约束 
								//保证比较库 与基准库的约束条件一样
								log.info("table:列约束"+table);
								//2.创建约束条件
								List<Constraints> basesconstraintsls = DBUtil.getConstraintsByTable(table);
								List<Constraints> tempconstraintsls = db.getConstraintsByTable(table);
								List<Constraints> diffis = Constraints.getDiffConstraints(basesconstraintsls,tempconstraintsls);
								if(CollectionUtils.isNotEmpty(diffis)){
									sqlBuffer.append(GenerateSQLUitl.getCreateConstraintsSql(diffis, schame, table));
									
								}
							}
							else if(isSeq&&CommonUtil.GEN_TYPE_3.equals(exist)){
								//todo  D|3缺序列
								//保证比较库 与基准库的序列一样
								log.info("table:列序列"+table);
								String seq = DBUtil.getSequenceByTable(table,schame);
								if(StringUtils.isNotBlank(seq)){
									sqlBuffer.append( "--create sequence");
									sqlBuffer.append(newLine);
									sqlBuffer.append( seq);
									sqlBuffer.append(newLine);
								}
								 
							}
							//关闭db
							db.close();
							
						}//end for
						//log.info("sqlBuffer:"+sqlBuffer);
						aysncTempDDLDown(schame,ip,sqlBuffer.toString(),sessionId,eendTime);
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						 
						
						myParamter.setPval("false");
						myParamterService.update(myParamter);
					}
				 };
			 }.start();
			//Thread.sleep(1000);
			map.put("result", "骚等片刻, 请在增量下载记录功能菜单里下载");
			map.put("flag", true);
		}else{
			map.put("result", "请选比对后再创建...");
			if(isCompare){
				map.put("result", "有人正在比对中,暂时无法比对,请骚等...");
			}
			map.put("flag", false);
		}
		
		String str=JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
	
	//临时生成ddl脚本
	private void aysncTempDDLDown(String schame,String ip,String contentDDL,String sessionId,Date endTime) throws IOException {
    	String way = "纯比较生成DDL脚本";
	    String remark = "临时生成ddl脚本,不含dml脚本" ;
	   
		//命名规则
        String fileName = "";//不含路径
        String filePath = "";//不包含最后的 /，不含文件名称
        File destFile = null;
       
        SimpleDateFormat dateformat=new SimpleDateFormat("yyyyMMddhhmmss");   
        filePath = UploadPathUtil.getScriptPath()+schame;
        File parentFile  = new File(filePath);
        if(!parentFile.exists()){
        	parentFile.mkdirs();
        }
        fileName = schame+"_"+dateformat.format(new Date())+"_test.sql";
        destFile = new File(filePath+"/"+fileName);
         
        FileUtils.writeStringToFile(destFile, contentDDL,CommonUtil.OUT_CODE);
     
        //记录下载
        MyDown myDown = new MyDown();
        myDown.setContent(fileName);
        myDown.setCreateTime(new Date());
        //临时库类型
        myDown.setDownType(2);
        myDown.setIp(ip);
        myDown.setSechma(schame);
        myDown.setUpdateTime(new Date());
        myDown.setUrl("script/"+schame+"/"+destFile.getName());
        myDown.setWay(way);
        myDown.setRange(DateUtils.formdate(endTime));
        myDown.setRemark(remark);
        myDown.setRealRange("【"+DateUtils.formdate(endTime)+","+DateUtils.formdate(endTime)+"】 ");
      
	    myDownService.add(myDown);
	    //提醒前台
	    remind(sessionId,"userremind");
 
	}
	
 
	private void remind(String sessionId,String type){
		 WsPool.sendMessageToAll(type);
	  /*  //提醒前台
   	   EventManager manager= EventManager.getInstance();
		Map map=new HashMap();
	    map.put(type+"_"+sessionId, type);
	    manager.removeEvent(type+"_"+sessionId);
	    manager.createEvent(0, type+"_"+sessionId,map);  */
	}
	
	 //获得db比较开始时间
	 private String getdb_compare_start_time(HttpServletRequest request){
		//提供表结构起始比较时间
		 String cstartTime= request.getParameter("cstartTime") ;
		 if(StringUtils.isNotBlank(cstartTime)){
			 cstartTime +=" 00:00:00";
			 return cstartTime;
		 }
		String startTime= SystemMessage.getString("db_compare_start_time");
		if(StringUtils.isEmpty(startTime)){
			//startTime = "1970-01-01";//默认为1970时间
			 
			return null;
		}else{
			startTime += " 00:00:00";
			return startTime;
		}
		
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
