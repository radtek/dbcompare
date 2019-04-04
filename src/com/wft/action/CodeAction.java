package com.wft.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import rcb.sm4api.SM4API;

import com.aes.AESCoder;
import com.aes.DataCode;
import com.aes.FileDES;
import com.aes.PureSecAPIUtil;
import com.aes.SensitiveCode;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wft.exception.AppException;
import com.wft.reg.JsqlparserDemo;
import com.wft.util.CommonUtil;
import com.wft.util.FileCharsetDetector;
import com.wft.util.UnionpayFileMd5;
import com.wft.util.UploadPathUtil;
import com.wft.util.security.AESCoderNew;

/**
 * @author admin
 * 加解密控制端
 */
@Controller
@RequestMapping(value = "/code")
public class CodeAction  extends BaseAction{

	private final static Logger log = Logger.getLogger(CodeAction.class);
	//数据库加解密
	@RequestMapping(value = "/codedb")
	public void codedb(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code = request.getParameter("code");
		log.info("数据库加解密code:" + code);
		String inputstr = request.getParameter("inputstr");
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = true;
		String result = "";
		try {
			if (StringUtils.isNotBlank(inputstr)) {
				if("en".equalsIgnoreCase(code)){
					result = DataCode.code(inputstr.split(","));
				}else{
					result = DataCode.decode(inputstr.split(","));
				}
				
			}
		}catch (Exception e) {
			flag = false;
			result = e.getMessage();
		}
		 
		map.put("result", result);
		map.put("flag", flag);
	 
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
 
		 
	}
	
	
	//清分文件加解密
	@RequestMapping(value = "/codefile")
	public void codedb(@RequestParam("uploadcodefile") MultipartFile uploadcodefile,HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("uploadcodefile:" + uploadcodefile);
		String pwd = request.getParameter("codefilepwd");
		log.info("数据库加解密pwd:" + pwd);
		String stype = request.getParameter("stype");
		log.info("数据库加解密stype:" + stype);
		String type = request.getParameter("type");
		log.info("数据库加解密type:" + type);
		String codefilebank = request.getParameter("codefilebank");
		log.info("数据库加解密codefilebank:" + codefilebank);
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = true;
		String result = "";
		File tempFile = null;
		try {
			if (uploadcodefile != null) {
				String fileName = uploadcodefile.getOriginalFilename();
				 
				if (StringUtils.isNotBlank(fileName)) {
					String ext = FilenameUtils.getExtension(fileName);
					
					log.info("fileName:" + fileName);
					// 先上传到零时目录
					String realPath = UploadPathUtil.getCodePath();
					tempFile = new File(realPath,System.currentTimeMillis()+"."+ext);
				 
					uploadcodefile.transferTo(tempFile);
				 
					File destFile = null;
				    if("0".equals(type)){//解密
				    	destFile = new File(realPath,"de_"+fileName);
				    	if(destFile.exists()){
				    		destFile.delete();
				    	}
				    	 if("北京银行".equals(codefilebank)){
				    		 FileDES fileDES = new FileDES(pwd);
				    		 fileDES.decrypt(tempFile.getAbsolutePath(), destFile.getAbsolutePath());
				    	 }
				    	 else if("成都农商行".equals(codefilebank)){
				    		 SM4API.decryptFile(pwd, tempFile.getAbsolutePath(), destFile.getAbsolutePath(), 1);
				    	 }
				    	 else if("厦门建行".equals(codefilebank)){
				    		 PureSecAPIUtil.decode(tempFile.getAbsolutePath(), pwd, destFile.getAbsolutePath());
				    	 }else if("银总".equals(codefilebank)){
				    		    String md5 = UnionpayFileMd5.fileMd5("12345678ABCDEFGH",tempFile).toUpperCase();
				    			System.out.println("银总:"+md5);
				    			destFile = new File(realPath,"md5.txt");
				    			FileUtils.write(destFile, md5);
				    	 }
				    	 else{
				    		 AESCoder.decryptFile(tempFile.getAbsolutePath(), destFile.getAbsolutePath(), pwd);
				    	 }
				    	
				    }else{//加密
				    	
				    	 destFile = new File(realPath,"en_"+fileName);
				    	 if(destFile.exists()){
					    		destFile.delete();
					    }
				    	 if("北京银行".equals(codefilebank)){
				    		 FileDES fileDES = new FileDES(pwd);
				    		 fileDES.encrypt(tempFile.getAbsolutePath(), destFile.getAbsolutePath());
				    	 }
				    	 else if("成都农商行".equals(codefilebank)){
				    		 SM4API.encryptFile(pwd, tempFile.getAbsolutePath(), destFile.getAbsolutePath(), 1);
				    	 }
				    	 else if("厦门建行".equals(codefilebank)){
				    		 PureSecAPIUtil.encode(tempFile.getAbsolutePath(), pwd, destFile.getAbsolutePath());
				    	 }else if("银总".equals(codefilebank)){
				    		 String md5 = UnionpayFileMd5.fileMd5("12345678ABCDEFGH",tempFile).toUpperCase();
				    			System.out.println("银总:"+md5);
				    			destFile = new File(realPath,"md5.txt");
				    			FileUtils.write(destFile, md5);
				    			
				    	 }
				    	 else{
				    		 AESCoder.encryptFile(tempFile.getAbsolutePath(), destFile.getAbsolutePath(), pwd);
				    	 }
				    
				    }
				    result = destFile.getName();
				    
				}

			}else{
				throw new Exception("请上传文件");
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			result = e.getMessage();
		}finally{
			 
		}
		

		map.put("result", result);
		map.put("flag", flag);
		
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
	}
	
	
	//敏感信息加加解密
	@RequestMapping(value = "/codesensitiveinput")
	public void codesensitiveinput(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String codesensitiveinput = request.getParameter("codesensitiveinput");
		String codesensitivepwd = request.getParameter("codesensitivepwd");
		String codesensitivetype = request.getParameter("codesensitivetype");
		String codesensitivebank = request.getParameter("codesensitivebank");
		log.info("数据库加解密codesensitiveinput:" + codesensitiveinput);
		log.info("数据库加解密codesensitivepwd:" + codesensitivepwd);
		log.info("数据库加解密codesensitivetype:" + codesensitivetype);
		log.info("数据库加解密codesensitivebank:" + codesensitivebank);
		
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = true;
		String result = "";
		try {
			 
			JSONObject json = JSON.parseObject(codesensitivepwd);
			if (StringUtils.isNotBlank(codesensitivepwd)) {
				if("en".equalsIgnoreCase(codesensitivetype)){
					 
					result=SensitiveCode.enAccountCode(codesensitivebank,json.getString(codesensitivebank), codesensitiveinput.split(","));
				}else{
					result=SensitiveCode.deAccountCode(codesensitivebank,json.getString(codesensitivebank), codesensitiveinput.split(","));
				}
				
			}
		}catch (Exception e) {
			flag = false;
			result = e.getMessage();
		}
		 
		map.put("result", result);
		map.put("flag", flag);
	 
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
 
		 
	}
	
	//敏感信息加解密
	@RequestMapping(value = "/codesensitiveuploadFile")
	public void codesensitiveuploadFile(@RequestParam("codesensitiveuploadFile") MultipartFile codesensitiveuploadFile,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String codesensitiveinput = request.getParameter("codesensitiveinput");
		String codesensitivepwd = request.getParameter("codesensitivepwd");
		String codesensitivetype = request.getParameter("codesensitivetype");
		String codesensitivebank = request.getParameter("codesensitivebank");
		log.info("数据库加解密codesensitiveinput:" + codesensitiveinput);
		log.info("数据库加解密codesensitivepwd:" + codesensitivepwd);
		log.info("数据库加解密codesensitivetype:" + codesensitivetype);
		log.info("数据库加解密codesensitivebank:" + codesensitivebank);
		
		log.info("uploadcodefile:" + codesensitiveuploadFile);
		 
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = true;
		String result = "";
		File tempFile = null;
		try {
			JSONObject json = JSON.parseObject(codesensitivepwd);
			 
			if (codesensitiveinput != null) {
				String fileName = codesensitiveuploadFile.getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					String ext = FilenameUtils.getExtension(fileName);
					 
					log.info("fileName:" + fileName);
					// 先上传到零时目录
					String realPath = UploadPathUtil.getCodePath();
					tempFile = new File(realPath,System.currentTimeMillis()+"."+ext);
					 
					if(tempFile.exists()){
						tempFile.delete();
			    	}
					codesensitiveuploadFile.transferTo(tempFile);	
					File destFile = null;
				    if("de".equals(codesensitivetype)){//解密
				    	destFile = new File(realPath,"de_"+fileName);
				    	if(destFile.exists()){
				    		destFile.delete();
				    	}
				   	 
						SensitiveCode.deAccountCode(codesensitivebank,json.getString(codesensitivebank), tempFile,destFile);
			
				    	 
				    }else{//加密
				    	
				    	 destFile = new File(realPath,"en_"+fileName);
				    	 if(destFile.exists()){
					    		destFile.delete();
					    	}
				    	SensitiveCode.enAccountCode(codesensitivebank,json.getString(codesensitivebank), tempFile,destFile);
							 
				    }
				    result = destFile.getName();
				    
				}

			}else{
				throw new Exception("请上传文件");
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			result = e.getMessage();
		}
		

		map.put("result", result);
		map.put("flag", flag);
		
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
	}
	
	
	//通用AES加解密
	@RequestMapping(value = "/codeaesinput")
	public void codeaesinput(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String codeaesinput = request.getParameter("codeaesinput");
		String codeaespwd = request.getParameter("codeaespwd");
		String codeaestype = request.getParameter("codeaestype");
		log.info("数据库加解密codeaesinput:" + codeaesinput);
		log.info("数据库加解密codeaespwd:" + codeaespwd);
		log.info("数据库加解密codeaestype:" + codeaestype);
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = true;
		String result = "";
		try {
		 
			if (StringUtils.isNotBlank(codeaespwd)) {
				if("en".equalsIgnoreCase(codeaestype)){
					result = AESCoderNew.encrypt(codeaesinput, codeaespwd);
					 
				}else{
					result = AESCoderNew.decrypt(codeaesinput, codeaespwd);
				}
				
			}
		}catch (Exception e) {
			flag = false;
			result = e.getMessage();
		}
		 
		map.put("result", result);
		map.put("flag", flag);
	 
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
 
		 
	}
	
	//通用AES加解密
	@RequestMapping(value = "/codeaesuploadFile")
	public void codeaesuploadFile(@RequestParam("codeaesuploadFile") MultipartFile codeaesuploadFile,HttpServletRequest request, HttpServletResponse response) throws Exception {
	  
		String codeaespwd = request.getParameter("codeaespwd");
		String codeaestype = request.getParameter("codeaestype");
		 
		log.info("数据库加解密codeaespwd:" + codeaespwd);
		log.info("数据库加解密codeaestype:" + codeaestype);
	   log.info("uploadcodefile:" + codeaesuploadFile);
		 
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = true;
		String result = "";
		File tempFile = null;
		try {
			  
			if (codeaesuploadFile != null) {
				String fileName = codeaesuploadFile.getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					log.info("fileName:" + fileName);
					// 先上传到零时目录
					String realPath = UploadPathUtil.getCodePath();
					String ext = FilenameUtils.getExtension(fileName);
					 
					tempFile = new File(realPath,System.currentTimeMillis()+"."+ext);
					 
					if(tempFile.exists()){
						tempFile.delete();
			    	}
					codeaesuploadFile.transferTo(tempFile);	
					File destFile = null;
				    if("de".equals(codeaestype)){//解密
				    	destFile = new File(realPath,"de_"+fileName);
				    	if(destFile.exists()){
				    		destFile.delete();
				    	}
				   	 
				    	AESCoder.decryptFile(tempFile.getAbsolutePath(), destFile.getAbsolutePath(), codeaespwd);
				    	 
				    }else{//加密
				    	
				    	 destFile = new File(realPath,"en_"+fileName);
				    	 if(destFile.exists()){
					    		destFile.delete();
					    	}
				    	 AESCoder.encryptFile(tempFile.getAbsolutePath(), destFile.getAbsolutePath(), codeaespwd);	 
				    }
				    result = destFile.getName();
				    
				}

			}else{
				throw new Exception("请上传文件");
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			result = e.getMessage();
		}
		

		map.put("result", result);
		map.put("flag", flag);
		
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
	}
	
	//sql null属性过滤
	@RequestMapping(value = "/sqlnullFile")
	public void sqlnullFile(@RequestParam("sqlnullFile") MultipartFile sqlnullFile,HttpServletRequest request, HttpServletResponse response) throws Exception {
	 
		 
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = true;
		String result = "";
		File tempFile = null;
		try {
			  
			if (sqlnullFile != null) {
				String fileName = sqlnullFile.getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					log.info("fileName:" + fileName);
					// 先上传到零时目录
					
					String realPath = UploadPathUtil.getCodePath();
					String ext = FilenameUtils.getExtension(fileName);
					tempFile = new File(realPath,System.currentTimeMillis()+"."+ext);
					 
					if(tempFile.exists()){
						tempFile.delete();
			    	}
					sqlnullFile.transferTo(tempFile);	
					
					String chart = new FileCharsetDetector().checkEncoding(tempFile);
					log.info("chart:" + chart);
					if(CommonUtil.GB2312.equalsIgnoreCase(chart)){
						log.info("文件编码:不是UTF-8:" + fileName);
						throw new AppException("文件编码:不是UTF-8:" + fileName);
					}
					
					File destFile = null;
					destFile = new File(realPath,"de_"+fileName);
			    	if(destFile.exists()){
			    		destFile.delete();
			    	}
			    	List<String> results = JsqlparserDemo.parseFile(tempFile);
			     
			    	FileUtils.writeLines(destFile, "gb2312", results);
			     
				    result = destFile.getName();
				    
				}

			}else{
				throw new Exception("请上传文件");
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			result = e.getMessage();
		}
		

		map.put("result", result);
		map.put("flag", flag);
		
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
	}
		
	@RequestMapping(value = "/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request) throws IOException {
    	String filename = request.getParameter("filename");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String realPath = UploadPathUtil.getCodePath();
        File destFile = null;
        destFile =  new File(realPath,filename);
      
        if (destFile==null|| !destFile.isFile() ) {
            return new ResponseEntity<byte[]>("文件不存在！".getBytes(), headers, HttpStatus.NOT_FOUND);
        }
        headers.setContentDispositionFormData("attachment", new String(destFile.getName().getBytes(System.getProperty("file.encoding")), "ISO8859-1"));  //解决文件名中文乱码问题
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(destFile), headers, HttpStatus.OK);
    }
}
