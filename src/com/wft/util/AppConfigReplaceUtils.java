package com.wft.util;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

/**
 * @author admin
 *app-config.properties  
  
 */
public class AppConfigReplaceUtils {

	public static Map<String, String>   get(File srcFile)  {
		Map<String, String> pparmansmap = new LinkedHashMap<String, String>(); 
		try {
			String updatetime = DateUtils.getCurrentTime();
			pparmansmap.put("##modify 时间 ", "time:"+updatetime);
			List<String> appContents = FileUtils.readLines(srcFile, "UTF-8");
			for(String appContent:appContents){
				
				appContent = appContent.trim();
				if(appContent.startsWith("#")){//注释
					//pparmansmap.put(appContent, "  time:"+updatetime);
					
				}else{
					String[] strarr = appContent.split("=");
					if(strarr!=null&&strarr.length==2){
						String strname = strarr[0].trim();
						String stral = strarr[1].trim();
						pparmansmap.put(strname, stral);
						
					}
				}//
				
			}//end if
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
			 
		}
		
		return pparmansmap;
		
	}
	
	 
}
