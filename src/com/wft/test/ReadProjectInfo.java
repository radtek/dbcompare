package com.wft.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author admin
 *解析系统组信息
 */
public class ReadProjectInfo {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

		List<String> ls = FileUtils.readLines(new File("F:/公司重要文件/aa私有云项目列表-彭照民-20170623.csv") );
		//JSONArray jarr = new JSONArray();
		Map<String,Object> map = new HashMap<String,Object>();
		for(int i=0,len=ls.size();i<len;i++){
			String str = ls.get(i);
			//System.out.println(str);
			if(StringUtils.isNotBlank(str)){
				String[] arr = str.split(",");
				if(arr!=null&&arr.length>=6){
					JSONObject obj = new JSONObject();
					obj.put("内网IP", arr[0].trim());
					obj.put("所属业务", arr[1].trim());
					obj.put("项目名称", arr[2].trim());
					obj.put("项目路径", arr[3].trim());
					obj.put("数据库", arr[5].trim());
					obj.put("域名", arr[6].trim());
					System.out.println(obj.toString());
					map.put(arr[5].trim(), obj);
				}
				
				
			}
			
			//System.out.println(map);
		}
		
	 
		List<String> ls1 = FileUtils.readLines(new File("F:/公司重要文件/aa查看银行.csv") );
		//JSONArray jarr = new JSONArray();
		Map<String,Object> map1 = new HashMap<String,Object>();
		for(int i=0,len=ls1.size();i<len;i++){
			String str = ls1.get(i);
		//	System.out.println(str);
			if(StringUtils.isNotBlank(str)){
				String[] arr = str.split(",");
				if(arr!=null&&arr.length>=6){
					JSONObject obj = new JSONObject();
					obj.put("端口", arr[3].trim());
					obj.put("用户", arr[4].trim());
					obj.put("密码", arr[5].trim());
					obj.put("公网IP", arr[2].trim());
					 
					System.out.println(obj.toString());
					map1.put(arr[1].trim(), obj);
				}
				
				
			}
			
			//System.out.println(map1);
		}
		
		for(Entry<String, Object> en: map.entrySet()){
			String se = en.getKey();
			JSONObject obj =  (JSONObject) en.getValue();
			String ip = (String) obj.get("内网IP");
			obj.put("登录信息", map1.get(ip));
			
			map.put(se, obj);
		}
		
		
		for(Entry<String, Object> en: map.entrySet()){
			 System.out.println("update MY_BASIC_BANK set FLS3 ='"+en.getValue()+"' where SECHMA='"+en.getKey().toLowerCase()+"';" );
		}
		
		 
	}

}
