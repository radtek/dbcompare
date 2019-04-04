package com.wft.util;

import java.io.File;
import java.text.MessageFormat;

import org.apache.log4j.Logger;

/**
 * @author admin
 * UploadPath路径
 */
public class UploadPathUtil {
	private final static Logger log = Logger.getLogger(UploadPathUtil.class);
	
	public static final String DDL = "DDL";
	public static final String DML = "DML";
	 private static String OS = System.getProperty("os.name").toLowerCase();
	 
	public static String getRealPath(String serviceName) {
		String relativePathFmt = format(SystemMessage.getString("tempFilePath"),serviceName);
		File relativeFile = new File(getTempPath()+relativePathFmt);
		if(!relativeFile.exists()){
			relativeFile.mkdirs();
		}
		
		return relativePathFmt;
	}
	
	
	public static String getPath(String serviceName) {
		String relativePathFmt = format(SystemMessage.getString("tempFilePath"),serviceName);
		File relativeFile = new File(getTempPath()+relativePathFmt);
		if(!relativeFile.exists()){
			relativeFile.mkdirs();
		}
		
		return relativeFile.getAbsolutePath();
	}
	
	
	public static String getTempPath() {
		String tempPath = null;
		if(isWindows()){
			tempPath =  SystemMessage.getString("tempPath");
		}else{
			tempPath =  SystemMessage.getString("tempPathUnix");
		}
		
		log.info("tempPath:"+tempPath);
		return tempPath;
	}
	
	public static String getScriptPath() {
		String scriptPath = null;
		if(isWindows()){
			scriptPath =   SystemMessage.getString("scriptPath");
		}else{
			scriptPath =  SystemMessage.getString("scriptPathUnix");
		}
		scriptPath = getTempPath()+scriptPath;
		log.info("scriptPath:"+scriptPath);
		return scriptPath;
	}
	
	public static String getBasicTablePath() {
		String scriptPath = null;
		if(isWindows()){
			scriptPath =   SystemMessage.getString("basicTablePath");
		}else{
			scriptPath =  SystemMessage.getString("basicTablePathUnix");
		}
		scriptPath = getTempPath()+scriptPath;
		File relativeFile = new File(scriptPath);
		if(!relativeFile.exists()){
			relativeFile.mkdirs();
		}
		log.info("getBasicTablePath:"+scriptPath);
		return scriptPath;
	}
	
	public static String getCodePath() {
		String scriptPath = null;
		if(isWindows()){
			scriptPath =   SystemMessage.getString("codePath");
		}else{
			scriptPath =  SystemMessage.getString("codePathUnix");
		}
		scriptPath = getTempPath()+scriptPath;
		File relativeFile = new File(scriptPath);
		if(!relativeFile.exists()){
			relativeFile.mkdirs();
		}
		log.info("codePath:"+scriptPath);
		return scriptPath;
	}
	
	public static String getJavaTempPath() {
		String tmpdir = System.getProperty("java.io.tmpdir");
		System.out.println("tmpdir:"+tmpdir);
		return tmpdir;
	}
	
	
	public static String getSqluldr2Path() {
		String sqluldr2Path= null;
		if(isWindows()){
			sqluldr2Path =   SystemMessage.getString("sqluldr2Path");
		}else{
			sqluldr2Path =  SystemMessage.getString("sqluldr2PathUnix");
		}
		sqluldr2Path = getTempPath()+sqluldr2Path;
		File relativeFile = new File(sqluldr2Path);
		if(!relativeFile.exists()){
			relativeFile.mkdirs();
		}
		log.info("sqluldr2PathUnix:"+sqluldr2Path);
		return sqluldr2Path;
	}
	
	
	public static String getConftplPath() {
		String tempPath = null;
		tempPath =  SystemMessage.getString("conftplPath");
		tempPath = getTempPath()+tempPath;
		File relativeFile = new File(tempPath);
		if(!relativeFile.exists()){
			relativeFile.mkdirs();
		}
		log.info("conftplPath:"+tempPath);
		return tempPath;
	}
	
	public static String getConfgenPath() {
		String tempPath = null;
		tempPath =  SystemMessage.getString("confgenPath");
		tempPath = getTempPath()+tempPath;
		File relativeFile = new File(tempPath);
		if(!relativeFile.exists()){
			relativeFile.mkdirs();
		}
		log.info("confgenPath:"+tempPath);
		return tempPath;
	}
	
	private static boolean isWindows(){  
	        return OS.indexOf("windows")>=0;  
	}  
	
 
	private static String format(String pattern,Object ...pathArgs) {
		 return MessageFormat.format(pattern, pathArgs);
	}

	public static void main(String[] args) {
		 System.out.println(OS);
	}
	 
}
