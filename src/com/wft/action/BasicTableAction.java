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
import org.apache.commons.io.FilenameUtils;
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
import com.wft.model.MyBasicTable;
import com.wft.service.BasicTableService;
import com.wft.sqluldr2.ExpSQL;
import com.wft.sqluldr2.ExpTaskResultVo;
import com.wft.util.CommonUtil;
import com.wft.util.FileCharsetDetector;
import com.wft.util.FileUtil;
import com.wft.util.UploadPathUtil;

/**
 * @author admin
 * 标准库
 */
@Controller
@RequestMapping(value = "/basic")
public class BasicTableAction extends BaseAction{
	
	private final static Logger log = Logger.getLogger(BasicTableAction.class);
	
	@Autowired
	private BasicTableService basicTableService;
	
	// 查询所有基本表
	@RequestMapping(value = "/findAll")
	public void findAll(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		List<MyBasicTable> u = basicTableService.findAll();
		int total = u.size();
		map.put("rows", u);
		map.put("total", total);
		String str = JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}

	// 查询所有基本表
	@RequestMapping(value = "/add")
	public void add(@RequestParam("uploadFile") MultipartFile uploadFile,HttpServletRequest request, HttpServletResponse response,MyBasicTable myBasicTable) throws Exception {
		log.info("uploadFile:" + uploadFile);
	 
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
				String realPath = UploadPathUtil.getBasicTablePath();
			   realTempPathName = realPath+fileName;
				log.info("realTempPathName:" + realTempPathName);
				tempFile = new File(realTempPathName);
				uploadFile.transferTo(tempFile);	
				try {
					 
					String chart = new FileCharsetDetector().checkEncoding(tempFile);
					String ext = FilenameUtils.getExtension(fileName);
					//sql必须时gb2312字符集,系统组要求 新银行导出全量脚本信息
					if(!"txt".equalsIgnoreCase(ext)&&!CommonUtil.OUT_CODE.equalsIgnoreCase(chart)){
						flag = false;
						result = "文件字符集不是gb2312:"+fileName;
					}
				} catch (Exception e) {
					flag = false;
					result = e.getMessage();
				}
				if (flag) {// 成功则入库
					myBasicTable.setCreateTime(new Date());
					myBasicTable.setIp(getRemortIP(request));
					myBasicTable.setRealName(fileName);
					myBasicTable.setServiceUrl("basicTable/"+fileName);
					myBasicTable.setUpdateTime(new Date());
					log.info("myBasicTable:" + myBasicTable);
					basicTableService.add(myBasicTable); 
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
	
	 
	
	 //修改信息
	@RequestMapping(value = "/update")
	public void disable( HttpServletRequest request, HttpServletResponse response,MyBasicTable myBasicTable ) throws Exception {
	 
		MyBasicTable myBasicTableKu = basicTableService.findById(myBasicTable.getId());
		if(myBasicTableKu!=null){
			//这里暂时只提供修改文件名
			File oldFile = new File( UploadPathUtil.getBasicTablePath()+myBasicTableKu.getRealName());
			File newFile = new File( UploadPathUtil.getBasicTablePath()+myBasicTable.getRealName());
			oldFile.renameTo(newFile);
			myBasicTableKu.setRealName(myBasicTable.getRealName()); 
			myBasicTableKu.setServiceUrl("basicTable/"+myBasicTable.getRealName());
			basicTableService.update(myBasicTableKu);
			
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "成功");
		map.put("flag", true);
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
	}

	 //根据id查找文件内容
	@RequestMapping(value = "/findById")
	public void findById(@RequestParam("id") Integer id,HttpServletRequest request, HttpServletResponse response ) throws Exception {
		log.info("id:"+id);
		MyBasicTable myBasicTable = basicTableService.findById(id);
		StringBuffer content = new StringBuffer();
		if(myBasicTable!=null){
			String url = UploadPathUtil.getBasicTablePath()+myBasicTable.getRealName();
			List<String> ls = FileUtils.readLines(new File(url), CommonUtil.OUT_CODE);
			for(String str:ls){
				//给前台换行
				content.append(str+"&");
			}
			 
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "成功");
		map.put("flag", true);
		//文件内容反馈给前台
		map.put("content", content.toString());
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
	}
	
	//压缩下载脚本
	@RequestMapping(value = "/makeFile")
    public ResponseEntity<byte[]> downCleaningFileS(HttpServletRequest request) throws IOException {
    	 HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);	
		
		List<File> files = new ArrayList<File>();
		List<MyBasicTable> myBasicTables = basicTableService.findAll();
		for(MyBasicTable myBasicTable:myBasicTables){
			files.add(new File(UploadPathUtil.getBasicTablePath()+myBasicTable.getRealName()));
		}
		 
		//命名规则
        String fileName = "";//不含路径
        String filePath = "";//不包含最后的 /，不含文件名称
        File destFile = null;
        if (files!=null && files.size()>=1){
            SimpleDateFormat dateformat=new SimpleDateFormat("yyyyMMddhhmmss");   
            filePath = files.get(0).getParent();
            fileName = "basic_"+dateformat.format(new Date())+".zip";
            FileUtil.zip(filePath+"/"+fileName, files, CommonUtil.OUT_CODE, false);
            destFile = new File(filePath+"/"+fileName);
        } else {
        	  return new ResponseEntity<byte[]>("文件不存在！".getBytes(), headers, HttpStatus.NOT_FOUND);
        }
        headers.setContentDispositionFormData("attachment", fileName);
      
        
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(destFile), headers, HttpStatus.OK);
    }
    
    
	 //================================================
	//sqluldr2导出数据
@RequestMapping(value = "/sqluldr2")
   public ResponseEntity<byte[]> sqluldr2(HttpServletRequest request) throws IOException {
   	 HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);	
       File destFile = null;
       String fileName = null;//不含路径
       ExpSQL expSQL = new ExpSQL();
       ExpTaskResultVo result = expSQL.beginExp();
       if (result!=null&&!result.isExit()){
           destFile = new File(result.getFilePath());
           fileName = destFile.getName();
       } else {
       	  return new ResponseEntity<byte[]>("文件不存在！".getBytes(), headers, HttpStatus.NOT_FOUND);
       }
       headers.setContentDispositionFormData("attachment", fileName);
     
       
       return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(destFile), headers, HttpStatus.OK);
   }
}
