package com.wft.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wft.appconf.dto.MyBankConf;
import com.wft.appconf.service.MyBankConfService;
import com.wft.appconf.service.freemarker.AppConfFreeMakerUtil;
import com.wft.appconf.service.freemarker.ExcelJsonUtil;
import com.wft.appconf.service.poi.ConfConstants;
import com.wft.appconf.service.poi.ExcelReadsUitls;
import com.wft.model.MybasicBank;
import com.wft.service.MyBasicBankService;
import com.wft.util.AppConfigReplaceUtils;
import com.wft.util.BeanNotNullCopyUtils;
import com.wft.util.DateUtils;
import com.wft.util.SystemMessage;
import com.wft.util.UploadPathUtil;
import com.wft.util.ZipUtil;

/**
 * @author admin
 * 配置文件模板
 */
@Controller
@RequestMapping(value = "/myBankConf")
public class MyBankConfAction extends BaseAction{
	@Autowired
	private MyBasicBankService myBasicBankService;
	
	@Autowired
	private MyBankConfService myBankConfService;
	
	//=========================myBankConfs.jsp===================================
	// 查询所有 
	@RequestMapping(value = "/findAll")
	public void findAll(HttpServletRequest request, HttpServletResponse response,MyBankConf myBankConf) throws Exception {
 
		Map<String, Object> map = new HashMap<String, Object>();
		 
		List<MyBankConf> u = myBankConfService.findAll(myBankConf);
		int total = u.size();
		map.put("rows", u);
		map.put("total", total);
		String str = JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
	
	
	// 查询id单独
	@RequestMapping(value = "/findById")
	public void findById(HttpServletRequest request, HttpServletResponse response,MyBankConf myBankConf) throws Exception {

			
		Map<String, Object> map = new HashMap<String, Object>();
		 
		MyBankConf u = myBankConfService.findById(myBankConf.getId());
	 
		map.put("rows", u);
		map.put("total", 0);
		String str = JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
		
	// 查询添加
	@RequestMapping(value = "/add")
	public void add(@RequestParam("uploadFile") MultipartFile uploadFile,HttpServletRequest request, HttpServletResponse response,MyBankConf myBankConf ) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", true);
		map.put("result", "添加成功");
		log.info("uploadFile:" + uploadFile);
		try {
			if (uploadFile != null) {
				String fileName = uploadFile.getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					log.info("fileName:" + fileName);
					// 先上传到零时目录
					String realPath = UploadPathUtil.getConfgenPath();
				    String realTempPathName = DateUtils.getCurrentTimeFileName()+fileName;
					log.info("realTempPathName:" + realTempPathName);
					File tempFile = new File(realPath,realTempPathName);
					uploadFile.transferTo(tempFile);
					JSONObject jsonObject = new JSONObject(true); 
					jsonObject.put("val", ExcelReadsUitls.readXlsx(tempFile));
					myBankConf.setExcelJson(jsonObject.toString());
					myBankConf.setExcelPath(SystemMessage.getString("confgenPath")+realTempPathName);
					myBankConfService.add(myBankConf);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("flag", false);
			map.put("result", e.getMessage());
		}
		
		String str = JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
	
	
	// 查询update
	@RequestMapping(value = "/update")
	public void update(@RequestParam("uploadFile") MultipartFile uploadFile,HttpServletRequest request, HttpServletResponse response,MyBankConf myBankConf ) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", true);
		map.put("result", "更新成功");
		log.info("uploadFile:" + uploadFile);
		try {
			if (uploadFile != null) {
				MyBankConf pmyBankConf = myBankConfService.findById(myBankConf.getId());
				BeanNotNullCopyUtils.copyProperties(myBankConf, pmyBankConf);
				String fileName = uploadFile.getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					log.info("fileName:" + fileName);
					// 先上传到零时目录
					String realPath = UploadPathUtil.getConfgenPath();
				    String realTempPathName = DateUtils.getCurrentTimeFileName()+fileName;
					log.info("realTempPathName:" + realTempPathName);
					File tempFile = new File(realPath,realTempPathName);
					uploadFile.transferTo(tempFile);
					JSONObject jsonObject = new JSONObject(true); 
					jsonObject.put("val", ExcelReadsUitls.readXlsx(tempFile));
					pmyBankConf.setExcelJson(jsonObject.toString());
					pmyBankConf.setExcelPath(SystemMessage.getString("confgenPath")+realTempPathName);
					//需要重新一键生成模板
					pmyBankConf.setConfPath(null);
					myBankConfService.update(pmyBankConf);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("flag", false);
			map.put("result", e.getMessage());
		}
		
		String str = JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
	
	
	// 一键生成模板update
	@RequestMapping(value = "/oneGen")
	public void oneGen(HttpServletRequest request, HttpServletResponse response,MyBankConf myBankConf ) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", true);
		map.put("result", "一键生成模板成功");
		try {
			MyBankConf pmyBankConf = myBankConfService.findById(myBankConf.getId());
			//生成替换模板文件
			File tlpFile = gentpl(pmyBankConf);
			//打包
			String filename = tlpFile.getName()+".zip";
			String genPath = UploadPathUtil.getConfgenPath(); 
			ZipUtil.zip(tlpFile.getAbsolutePath(),genPath+filename );
			pmyBankConf.setConfPath(SystemMessage.getString("confgenPath")+filename);
			myBankConfService.update(pmyBankConf);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("flag", false);
			map.put("result", e.getMessage());
		}
		
		String str = JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
	
	
	

	//模板替换
	private File gentpl(MyBankConf pmyBankConf) throws IOException {
		MybasicBank myBasicBank = myBasicBankService.findById(pmyBankConf.getBankId());
		String genPath = UploadPathUtil.getConfgenPath(); 
		File pgenfile = new File(genPath,DateUtils.getCurrentTimeFileName()+pmyBankConf.getBankId());
		if(!pgenfile.exists()){
			pgenfile.mkdirs();
		}
		Map<String,Object> resultmap = ExcelJsonUtil.parseExcelJson(pmyBankConf.getExcelJson());
		resultmap.put("platform", myBasicBank.getPlatform());
		
		String tomcat = "private-root-conf";
    	ExcelJsonUtil.parseExcelmemcacheJson(resultmap  ,ConfConstants.TOMCAT_MAP.get(tomcat),ConfConstants.SHEET_4);
		AppConfFreeMakerUtil.getConf(pgenfile,resultmap,tomcat);
		
		tomcat = "private-acc-conf";
    	ExcelJsonUtil.parseExcelmemcacheJson(resultmap  ,ConfConstants.TOMCAT_MAP.get(tomcat),ConfConstants.SHEET_4);
		AppConfFreeMakerUtil.getConf(pgenfile,resultmap,tomcat);
		
		tomcat = "private-service-conf";
    	ExcelJsonUtil.parseExcelmemcacheJson(resultmap  ,ConfConstants.TOMCAT_MAP.get(tomcat),ConfConstants.SHEET_4);
		AppConfFreeMakerUtil.getConf(pgenfile,resultmap,tomcat);
		

		tomcat = "public-root-conf";
    	ExcelJsonUtil.parseExcelmemcacheJson(resultmap  ,ConfConstants.TOMCAT_MAP.get(tomcat),ConfConstants.SHEET_1);
		AppConfFreeMakerUtil.getConf(pgenfile,resultmap,tomcat);
		log.info("生成成功:" + tomcat);
		
		tomcat = "public-acc-conf";
    	ExcelJsonUtil.parseExcelmemcacheJson(resultmap  ,ConfConstants.TOMCAT_MAP.get(tomcat),ConfConstants.SHEET_1);
		AppConfFreeMakerUtil.getConf(pgenfile,resultmap,tomcat);
		log.info("生成成功:" + tomcat);
		
		tomcat = "public-service-conf";
    	ExcelJsonUtil.parseExcelmemcacheJson(resultmap  ,ConfConstants.TOMCAT_MAP.get(tomcat),ConfConstants.SHEET_1);
		AppConfFreeMakerUtil.getConf(pgenfile,resultmap,tomcat);
		log.info("生成成功:" + tomcat);
		
		tomcat = "interface-conf";
    	ExcelJsonUtil.parseExcelmemcacheJson(resultmap  ,ConfConstants.TOMCAT_MAP.get(tomcat),ConfConstants.SHEET_1);
		AppConfFreeMakerUtil.getConf(pgenfile,resultmap,tomcat);
		log.info("生成成功:" + tomcat);
		
		tomcat = "misc-conf";
    	ExcelJsonUtil.parseExcelmemcacheJson(resultmap  ,ConfConstants.TOMCAT_MAP.get(tomcat),ConfConstants.SHEET_1);
		AppConfFreeMakerUtil.getConf(pgenfile,resultmap,tomcat);
		log.info("生成成功:" + tomcat);
		
		tomcat = "tra-conf";
    	ExcelJsonUtil.parseExcelmemcacheJson(resultmap  ,ConfConstants.TOMCAT_MAP.get(tomcat),ConfConstants.SHEET_1);
		AppConfFreeMakerUtil.getConf(pgenfile,resultmap,tomcat);
		log.info("生成成功:" + tomcat);
		
		tomcat = "seq-conf";
    	ExcelJsonUtil.parseExcelmemcacheJson(resultmap  ,ConfConstants.TOMCAT_MAP.get(tomcat),ConfConstants.SHEET_1);
		AppConfFreeMakerUtil.getConf(pgenfile,resultmap,tomcat);
		log.info("生成成功:" + tomcat);
		
		//说明文档
		FileUtils.writeStringToFile(new File(pgenfile,"说明.txt"), "改配置只生成共性部分,某些配置需要按情况修改，比如数据库信息，和某银行独特配置", "UTF-8");
		
		return pgenfile;
	}
	
	
	
	
	
	
	
	
	//==========================myBankConftpls.jsp==================================
	//模板树形
 	@RequestMapping(value = "/gentree")
	public void gentree(HttpServletRequest request, HttpServletResponse response,MyBankConf myBankConf)	throws Exception {
 
	    //
		JSONArray jsonarry = new JSONArray();
		String tplPath = UploadPathUtil.getConftplPath();
		File tplPathFile = new File(tplPath);
		File[] files = tplPathFile.listFiles();
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				// TODO Auto-generated method stub
				return o1.getName().compareTo(o2.getName());
			}
		});
		int i = 0;
		int j = 0;
		for(File pfile:files){
			 
			if(pfile.isDirectory()){//只有一个层次
				i++; 
				JSONObject jsonobj = new JSONObject();
				
				 jsonobj.put("id", i);
				 jsonobj.put("text", pfile.getName());
				 JSONArray jsoncarry = new JSONArray();
				 jsonobj.put("children", jsoncarry);
				 
				 File[] cfiles = pfile.listFiles();
					for(File cfile:cfiles){
						if(!cfile.isDirectory()){//只有一个层次
							j++; 
							JSONObject jsoncobj = new JSONObject();
							jsoncobj.put("id", i+""+j);
							jsoncobj.put("text", cfile.getName());
							 jsoncarry.add(jsoncobj);
						}
					}
				jsonarry.add(jsonobj);
			}
		}
	  
		response.getWriter().write(jsonarry.toString());
	}
	
	@RequestMapping(value = "/findContentTpl")
	public void findContentTpl(HttpServletRequest request, HttpServletResponse response,MyBankConf myBankConf)	throws Exception {
		String fileName = request.getParameter("fileName");
		String tplPath = UploadPathUtil.getConftplPath();
		File tplPathFile = new File(tplPath);
		StringBuffer content = new StringBuffer();
		List<String> ls = FileUtils.readLines( new File(tplPathFile,fileName), "UTF-8");
		for(String str:ls){
			//给前台换行
			content.append(str+line);
		}
		response.getWriter().write(content.toString());
	}
	
	@RequestMapping(value = "/updateContentTpl")
	public void updateContentTpl(HttpServletRequest request, HttpServletResponse response,MyBankConf myBankConf)	throws Exception {
		String fileName = request.getParameter("fileName");
		String content = request.getParameter("content");
		String tplPath = UploadPathUtil.getConftplPath();
		File tplPathFile = new File(tplPath);
	 
		FileUtils.writeStringToFile(new File(tplPathFile,fileName), content, "UTF-8");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", true);
		map.put("result", "修改模板成功");
		String str = JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
	
	//=========================myBankConftplCompares.jsp===================================
	//模板类型
	@RequestMapping(value = "/comparesContentTplType")
	public void comparesContentTplType(HttpServletRequest request, HttpServletResponse response )	throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> ls = new ArrayList<String>();
		
		String tplPath = UploadPathUtil.getConftplPath();
		File tplPathFile = new File(tplPath);
		File[] files = tplPathFile.listFiles();
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				// TODO Auto-generated method stub
				return o1.getName().compareTo(o2.getName());
			}
		});
		for(File f:files){
			if(!"excel".equalsIgnoreCase(f.getName())){
				ls.add(f.getName());
			}
			
			
		}
		map.put("flag", true);
		map.put("result", ls);
		String str = JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}

	//模板比较
	@RequestMapping(value = "/comparesContentTpl")
	public void comparesContentTpl(@RequestParam("uploadFile") MultipartFile uploadFile,HttpServletRequest request, HttpServletResponse response )	throws Exception {
		String myBankConftplComparesType = request.getParameter("myBankConftplComparesType");
		//是否反向比较
		String isRerverse = request.getParameter("isRerverse");
		if(StringUtils.isBlank(myBankConftplComparesType)){
			myBankConftplComparesType = "public-root-conf";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", true);
		map.put("isRerverse", false);
		map.put("result", "更新成功");
		log.info("uploadFile:" + uploadFile);
		try {
			if (uploadFile != null) {
				File left = null;
				File right = null;
				Map<String, String> pparmansmap = new LinkedHashMap<String, String>();
				
				String fileName = uploadFile.getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					log.info("fileName:" + fileName);
					// 先上传到零时目录
					String realPath = UploadPathUtil.getConfgenPath();
				    String realTempPathName = DateUtils.getCurrentTimeFileName()+fileName;
					log.info("realTempPathName:" + realTempPathName);
					File tempFile = new File(realPath,realTempPathName);
					uploadFile.transferTo(tempFile);
					left = tempFile;
					
					//模板文件
					String tplPath = UploadPathUtil.getConftplPath();
					File rootconfFile = new File(tplPath+myBankConftplComparesType,"app-config.properties");
					right = rootconfFile;
					
					if("isRerverse".equals(isRerverse)){//反向比较
						left = rootconfFile;
						right = tempFile ;
						map.put("isRerverse", true);
					}
					Map<String, String> pparmansmap1 = AppConfigReplaceUtils.get(left);
					Map<String, String> pparmansmap2 = AppConfigReplaceUtils.get(right);
					
					for(Entry<String, String> en:pparmansmap2.entrySet()){
						String key = (String) en.getKey();
						if(!pparmansmap1.containsKey(key)){
							pparmansmap.put(key, (String)en.getValue());
						}
					}
					StringBuffer content = new StringBuffer();
					//以hessian.sec.server hessian前缀相同换行
					boolean precontrl =  false;
					String startKey  = "";
					for(Entry<String, String> en:pparmansmap.entrySet()){
						String key = en.getKey();
						if(key.startsWith(startKey)){
							precontrl = true;
						}else{
							precontrl = false;
						}
						if(!precontrl){
							//给前台换行
							content.append (line);
						}
						if(key.indexOf(".")>-1){
							String[] keys = key.split("\\.", 2);
							if(keys!=null&&keys.length>=1){
								startKey = keys[0];
							}				
						}else{
							startKey = key;
						}
						
						//给前台换行
						content.append(key+"="+en.getValue()+line);
						
						tempFile.delete();
					}
					map.put("result", content.toString());
					 
				}else{
					map.put("flag", false);
					map.put("result","请上传配置文件");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("flag", false);
			map.put("result", e.getMessage());
		}
		
		String str = JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
	
	
	 
}
