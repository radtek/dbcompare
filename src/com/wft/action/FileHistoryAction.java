package com.wft.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.wft.db.CacheUtil;
import com.wft.exception.AppException;
import com.wft.lunece.IndexManager;
import com.wft.model.FileHistory;
import com.wft.model.MyDown;
import com.wft.model.MyParamter;
import com.wft.service.DBService;
import com.wft.service.FileHistoryService;
import com.wft.service.MyDownService;
import com.wft.service.MyParamterService;
import com.wft.util.CommonUtil;
import com.wft.util.DateUtils;
import com.wft.util.FileCharsetDetector;
import com.wft.util.FileMD5;
import com.wft.util.FileUtil;
import com.wft.util.ReplaceSchema;
import com.wft.util.SystemMessage;
import com.wft.util.UploadPathUtil;
import com.wft.vo.FileHistoryVo;
import com.wft.vo.UserVo;

/**
 * @author admin
 *基础表脚本维护
 */
@Controller
@RequestMapping(value = "/file")
public class FileHistoryAction extends BaseAction {

	private final static Logger log = Logger.getLogger(FileHistoryAction.class);
	
	@Autowired
	private FileHistoryService fileHistoryService;
	@Autowired
	private DBService dBService;
	@Autowired
	private MyDownService myDownService;
	@Autowired
	private MyParamterService myParamterService;

	// 查询所有基本表
	@RequestMapping(value = "/findAll")
	public void findAll(HttpServletRequest request, HttpServletResponse response,FileHistoryVo fileHistoryVo)
			throws Exception {
		//时间转换
		 String strDate = request.getParameter("sbeginTime") ;
		 String endDate = request.getParameter("sendTime") ;
		 String lunece = request.getParameter("lunece") ;
		 if(StringUtils.isNotBlank(strDate)&&StringUtils.isNotBlank(endDate)){
			 Date beginTime = DateUtils.formdate(strDate);
			 Date endTime =  DateUtils.formdate(endDate);
			 fileHistoryVo.setBeginTime(beginTime);
			 fileHistoryVo.setEndTime(endTime);
		}
		
		 UserVo userVo = (UserVo) request.getSession().getAttribute("user");
		Map<String, Object> map = new HashMap<String, Object>();
		if(userVo!=null&&"admin".equals(userVo.getUserName())){//超管查询所有
			fileHistoryVo.setPhysicsFlag(CommonUtil.ALL);
		 }
		if("lunece".equalsIgnoreCase(lunece)){
			List<String> names1 = IndexManager.searchIndex(fileHistoryVo.getRealName());
			if(StringUtils.isNotEmpty(fileHistoryVo.getRealName())){
				//zhifu 前缀搜索
				List<String> names2 = IndexManager.searchIndex("zhifu."+fileHistoryVo.getRealName());
				names1.addAll(names2);
			}
			
			fileHistoryVo.setRealName(null);
			fileHistoryVo.setRealNameLists(names1);
		}
		List<FileHistory> u = fileHistoryService.findAll(fileHistoryVo);
		int total = u.size();
		map.put("rows", u);
		map.put("total", total);
		String str = JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}

	// 查询所有基本表
	@RequestMapping(value = "/add")
	public void add(@RequestParam("uploadFile") MultipartFile uploadFile,@RequestParam("time") String time,@RequestParam("isreg") String isreg,HttpServletRequest request, HttpServletResponse response,FileHistory fileHistory) throws Exception {
		log.info("uploadFile:" + uploadFile);
		log.info("time:" + time);
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = true;
		String result = "";
		File tempFile = null;
		String realTempPathName = null;
		if (uploadFile != null) {
			String fileName = uploadFile.getOriginalFilename();
			if (StringUtils.isNotBlank(fileName)) {
				log.info("fileName:" + fileName);
				// 先上传到零时目录
				String realPath = UploadPathUtil.getRealPath(fileHistory.getFileType());
			   realTempPathName = realPath+fileName;
				log.info("realTempPathName:" + realTempPathName);
				tempFile = new File(UploadPathUtil.getTempPath()+realTempPathName);
				uploadFile.transferTo(tempFile);	
				try {
					String chart = new FileCharsetDetector().checkEncoding(tempFile);
					log.info("chart:" + chart);
					if(CommonUtil.GB2312.equalsIgnoreCase(chart)){
						log.info("文件编码:不是UTF-8:" + fileName);
						throw new AppException("文件编码:不是UTF-8:" + fileName);
					}
					//1.先检查是否重复，通过md5或者文件名
					boolean isMD5 = fileHistoryService.findByMD5Name(FileMD5.getMd5ByFile(tempFile),fileName);
					if(isMD5){
						log.info("md5或者文件名已重复:" + fileName);
						throw new AppException("md5或者文件名已重复:" + fileName);
					}
					//
					//2. 检查sql语句语法校验,如果是储存过程之类复杂语句跳过通过其他方式处理
					if("1".equals(isreg)){
						log.info("如果是储存过程之类复杂语句跳过通过其他方式处理");
						flag = true;
					}else{
						String fileType = fileHistory.getFileType();
						if("DDL".equalsIgnoreCase(fileType)){
							flag = dBService.excuteDDLFromFile(fileHistory.getCheckOn(),tempFile.getAbsolutePath(), "zzy");
						}else{
							flag = dBService.excuteDMLFromFile(fileHistory.getCheckOn(),tempFile.getAbsolutePath(), "zzy");
						}
					}
					
				} catch (Exception e) {
					flag = false;
					result = e.getMessage();
				}
				if (flag) {// 成功则入库
					fileHistory.setRealName(fileName);
					// fileHistory.setFileType("ddl");
					fileHistory.setMd5(FileMD5.getMd5ByFile(tempFile));
					fileHistory.setServiceName(tempFile.getName());
					fileHistory.setServiceUrl(realTempPathName);
					fileHistory.setStatus(0);
					fileHistory.setCreateTime(DateUtils.formdate(time));
					fileHistory.setUpdateTime(new Date());
					
					fileHistoryService.add(fileHistory);
					//删除全库基本信息表缓存
					 CacheUtil.getInstance().clear();
					 
					//是否追加到标准库脚本文件
					appendBaiscFile(fileHistory,tempFile);
					
					//修改下最后一次上传脚本更新时间以便查询
					MyParamter myParamter = myParamterService.findById(MyParamter.last_upload_time);
					myParamter.setPval(DateUtils.formdate(new Date()));
					myParamterService.update(myParamter);
				}else{//删除文件
					if(tempFile!=null){
						tempFile.delete();
					}
				}
				

				map.put("result", result);
				map.put("flag", flag);
			 
				String str = JSONObject.toJSONString(map);
				log.info(str);
				response.getWriter().write(str);

			}

		}
	}

	
	 ////是否追加到标准库脚本文件
	private void appendBaiscFile(FileHistory fileHistory, File tempFile) throws IOException {
		// TODO Auto-generated method stub
		if(CommonUtil.APPEND_ON==fileHistory.getAppendBasic()){
			log.info("追加到标准库脚本文件:"+tempFile.getName());
			String path = UploadPathUtil.getBasicTablePath();
			String fileName = null;//追加的文件名
			String fileNameApendEnd = null;//文件内容最后的结尾是什么,比如ddl结尾有exit;追加时先要去掉exit;追加内容后再添加exit;否则exit;会一直追加commit;|exit;
			String[] fileNameApendEnds = null;
			
			if("DDL".equalsIgnoreCase(fileHistory.getFileType())){//跟据不同类型追加
				fileName = SystemMessage.getString("append_ddl");
				fileNameApendEnd = SystemMessage.getString("append_ddl_end");
			}else{
				fileName = SystemMessage.getString("append_dml");
				fileNameApendEnd = SystemMessage.getString("append_dml_end");
			}
			 
			String content = FileUtils.readFileToString(tempFile, CommonUtil.IN_CODE);
			
			//List<String> contentList = FileUtils.readLines(tempFile, "UTF-8");
			List<String> contentFilterList = new ArrayList<String>();
			//占位  0 为原文件内容
			contentFilterList.add(0, "");
			content = content.replaceAll("zhifu.", " ");//因为所有的入库脚本都带上了zhifu.,新增标准脚本时需要去掉
			//追加的内容
			contentFilterList.add(content+CommonUtil.LINE);
			if(StringUtils.isNotBlank(fileNameApendEnd)){
				fileNameApendEnds = fileNameApendEnd.split("\\|");
				for(String end:fileNameApendEnds){
					contentFilterList.add(end+CommonUtil.LINE);;//追加的文件内容最后的结尾是什么,比如ddl结尾有exit;需要新追加,对应原内容需要去掉相应的结尾
				}
			
			}
			//新增标准脚本名称注释
			contentFilterList.add("--"+tempFile.getName()+CommonUtil.LINE);

			//读取源文件内容 对应原内容需要去掉相应的结尾  原gb2312格式
			File basefile = new File(path+fileName);
			String basefilecontent = FileUtils.readFileToString(basefile, CommonUtil.OUT_CODE);
			if(StringUtils.isNotBlank(fileNameApendEnd)){
				log.info("=结尾有exit="+fileNameApendEnd);
				fileNameApendEnds = fileNameApendEnd.split("\\|");
				for(String end:fileNameApendEnds){
					log.info("=结尾有end="+end);
					basefilecontent = basefilecontent.replaceAll(end, "");//文件内容最后的结尾是什么,比如ddl结尾有exit;追加时先要去掉exit;追加内容后再添加exit;否则exit;会一直追加
				}
				 
			
			}
			//添加源内容
			contentFilterList.set(0, basefilecontent);
			//FileUtils.writeStringToFile(file, data, encoding);
			FileUtils.writeLines(basefile, CommonUtil.OUT_CODE,contentFilterList,false);
		 
		}
	
		
	}

	 
	 
	@RequestMapping(value = "/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String id = request.getParameter("id") ;
        FileHistory fileHistory = fileHistoryService.findById(Integer.valueOf(id));
        File destFile = null;
        if(fileHistory!=null){
        	destFile =  new File(UploadPathUtil.getTempPath()+fileHistory.getServiceUrl());
        }
      
        if (destFile==null|| !destFile.isFile() ) {
            return new ResponseEntity<byte[]>("文件不存在！".getBytes(), headers, HttpStatus.NOT_FOUND);
        }
        headers.setContentDispositionFormData("attachment", new String(destFile.getName().getBytes(System.getProperty("file.encoding")), "ISO8859-1"));  //解决文件名中文乱码问题
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(destFile), headers, HttpStatus.OK);
    }
	    
	    
    @RequestMapping(value = "/makeFile")
    public ResponseEntity<byte[]> downCleaningFileS(HttpServletRequest request) throws IOException {
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    	try {
    		
    			//readme.txt内容
    			List<String> readMeCotent = new ArrayList<String>();
    			readMeCotent.add(0, "");
    			readMeCotent.add("下载包含的脚本:"+CommonUtil.LINE);
    			
    	    	String way = "按id号下载脚本";
    	    	String range = "";
    	    	StringBuffer content = new StringBuffer();
    		    String ids = request.getParameter("ids") ;
    		    String strDate = request.getParameter("strDate") ;
    		    String endDate = request.getParameter("endDate") ;
    		    String schame = request.getParameter("schame") ;
    		    String remark = request.getParameter("remark") ;
    		    String timeType = request.getParameter("timeType") ;//时间类型 create-创建时间 upload-上传时间
    		    //ddl下载实际区间，防止前台人员误操作
    		    Date minDate = null;
    			Date maxDate =  null;
    		    if(StringUtils.isBlank(schame)){
    		    	schame="zhifu";
    		    }
    		    List<FileHistory> u = null;
    		    if(StringUtils.isNotBlank(ids)){
    	    	   Map<String, Object> map = new HashMap<String,Object>();
    			    map.put("ids", ids);
    			    way = "按id号下载脚本";
    			    range= "id:【"+ids+" 】";
    				u = fileHistoryService.findAll(map);
    		    }else{
    		    	Date strDated = DateUtils.formdate(strDate);
    				Date endDated =  DateUtils.formdate(endDate);
    				way = "按时间跨度下载脚本";
    				 range= "【"+strDate+","+endDate+"】 ";
    				 if("upload".equalsIgnoreCase(timeType)){//上传时间
    					 u = fileHistoryService.findAllByUpdateTime(strDated,endDated);
    					 range = "[上传]"+range;
    				 }else{//创建时间
    					 u = fileHistoryService.findAllByCreateTime(strDated,endDated);
    					 range  = "[创建]"+range;
    				 }
    		    	
    		    }
    		 
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
    				
    				if(minDate!=null){ //ddl下载实际区间，防止前台人员误操作,选择区间范围
    					if(DateUtils.compareDate(minDate,  fileHistory.getCreateTime())>0){
    						minDate = fileHistory.getCreateTime();
    					}
    					 
    				}else{
    					minDate = fileHistory.getCreateTime();
    					 
    				}
    				
    				if(maxDate!=null){ //ddl下载实际区间，防止前台人员误操作,选择区间范围
    				 
    					if(DateUtils.compareDate(maxDate,  fileHistory.getCreateTime())<0){
    						maxDate = fileHistory.getCreateTime();
    					}
    				}else{
    					 
    					maxDate = fileHistory.getCreateTime();
    				}
    				
    			}
    			List<File> files = new ArrayList<File>();
    			if(fileDDLs.size()>0){
    				File ddlFile = ReplaceSchema.mergeSql(schame, "ddl", UploadPathUtil.getScriptPath(), fileDDLs);
    				if(!ddlFile.exists()){
    					 return new ResponseEntity<byte[]>(("文件不存在！"+ddlFile.getName()).getBytes(), headers, HttpStatus.NOT_FOUND);
    				}
    				files.add(ddlFile);
    			}
    			if(fileDMLs.size()>0){
    				File dmlFile = ReplaceSchema.mergeSql(schame, "dml",UploadPathUtil.getScriptPath(), fileDMLs);
    				if(!dmlFile.exists()){
    					 return new ResponseEntity<byte[]>(("文件不存在！"+dmlFile.getName()).getBytes(), headers, HttpStatus.NOT_FOUND);
    				}
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
    	        readMeCotent.set(0, "下载时间区间:【"+DateUtils.formdate(minDate)+","+DateUtils.formdate(maxDate)+"】 "+CommonUtil.LINE);
    			FileUtils.writeLines(readerme, readMeCotent);
    		 
    	     /*   if (files!=null && files.size()==1){
    	            destFile = files.get(0);
    	            fileName = destFile.getName().substring(destFile.getName().lastIndexOf("/") + 1);
    	        } else*/ if (files!=null && files.size()>=1){
    	            SimpleDateFormat dateformat=new SimpleDateFormat("yyyyMMddhhmmss");   
    	            filePath = files.get(0).getParent();
    	            fileName = "script_"+dateformat.format(new Date())+"_"+schame+".zip";
    	            //追加注释脚本
    	            files.add(readerme);
    	            FileUtil.zip(filePath+"/"+fileName, files, "UTF-8", false);
    	            destFile = new File(filePath+"/"+fileName);
    	        } else {
    	        	  return new ResponseEntity<byte[]>("文件不存在！".getBytes(), headers, HttpStatus.NOT_FOUND);
    	        }
    	        headers.setContentDispositionFormData("attachment", fileName);
    	         
    	        //记录下载
    	        MyDown myDown = new MyDown();
    	        myDown.setContent(StringUtils.substring(content.toString(), 0, 3000));
    	        myDown.setCreateTime(new Date());
    	        myDown.setDownType(1);
    	        myDown.setIp(getRemortIP(request));
    	        myDown.setSechma(schame);
    	        myDown.setUpdateTime(new Date());
    	        myDown.setUrl("script/"+schame+"/"+destFile.getName());
    	        myDown.setWay(way);
    	        myDown.setRange(range);
    	        myDown.setRemark(remark);
    	        if(minDate!=null&maxDate!=null){
    	        	  myDown.setRealRange("【"+DateUtils.formdate(minDate)+","+DateUtils.formdate(maxDate)+"】 ");
    	        }
    	      
    	        myDownService.add(myDown);
    	        
    	        
    	        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(destFile), headers, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity<byte[]>("文件不存在！".getBytes(), headers, HttpStatus.NOT_FOUND);
		}
    	
    }
    
	    
	 //脚本时间变更
	@RequestMapping(value = "/changeTime")
	public void changeTime(HttpServletRequest request, HttpServletResponse response ) throws Exception {
		 //创建时间
		 String screateTime = request.getParameter("screateTime");
		//上传时间
		 String supdateTime = request.getParameter("supdateTime");
		//上传时间
		 String id = request.getParameter("id");
		 FileHistory fileHistoryp = fileHistoryService.findById(Integer.valueOf(id));
		if(StringUtils.isNotBlank(screateTime)){
			fileHistoryp.setCreateTime(DateUtils.formdate(screateTime));
			
		}
		if(StringUtils.isNotBlank(supdateTime)){
			fileHistoryp.setUpdateTime(DateUtils.formdate(supdateTime));
			
		}
		fileHistoryService.update(fileHistoryp);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "成功");
		map.put("flag", true);
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
	}
	
	 //根据id查找 文件内容
	@RequestMapping(value = "/findById")
	public void findById(@RequestParam("id") Integer id,HttpServletRequest request, HttpServletResponse response ) throws Exception {
		log.info("id:"+id);
		FileHistory fileHistory = fileHistoryService.findById(id);
		StringBuffer content = new StringBuffer();
		if(fileHistory!=null){
			String url = UploadPathUtil.getTempPath()+fileHistory.getServiceUrl();
			List<String> ls = FileUtils.readLines(new File(url), CommonUtil.IN_CODE);
			for(String str:ls){
				content.append(str+"&");
			}
			 
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "成功");
		map.put("flag", true);
		map.put("content", content.toString());
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
	}
	
	
	 //创建lunece索引
	@RequestMapping(value = "/luneceIndex")
	public void luneceIndex(HttpServletRequest request, HttpServletResponse response ) throws Exception {
		IndexManager.createIndex();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "创建lunece索引成功");
		map.put("flag", true);
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
	}
}
