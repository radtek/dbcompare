package com.wft.db;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wft.model.MyParamter;
import com.wft.util.SystemMessage;

/**
 * @author admin
 * 不是系统表 对比时无需显示差异
 */
public class IgnoreTable {
	private final static Logger log = Logger.getLogger(IgnoreTable.class);
	
	//忽视的表信息
	public static final Map<String,String> ignoreTableMap = new HashMap<String, String>();
	//MY_PARAMETER参数表信息 登录时保存：ignore_table_reg need_table_reg
	public static final Map<String,String> paramMap = new HashMap<String, String>();
	static{
		ignoreTableMap.put("MY_FILE_HISTORY", "脚本上传记录表");
		ignoreTableMap.put("MY_DOWN", "下载记录");
		ignoreTableMap.put("MY_BASIC_TABLE", "标准表维护");
		ignoreTableMap.put("MY_BASIC_BANK", "银行表");
		ignoreTableMap.put("MY_PARAMETER", "我的参数");
		ignoreTableMap.put("MY_BANK_CONF", "银行配置模板文件");
	}

	
	//表含数字的为不合法表 也即临时表
	public static boolean isIgnoreDigitTable(String table){
		 String ignore_table_reg = IgnoreTable.paramMap.get(MyParamter.ignore_table_reg);
		 if(StringUtils.isEmpty(ignore_table_reg)){
			  ignore_table_reg = SystemMessage.getString(MyParamter.ignore_table_reg);
			 
		 }
		 log.info("ignore_table_reg:"+ignore_table_reg);
		  if(StringUtils.isNotBlank(ignore_table_reg)){
			  boolean flag = false;
		        Pattern p = Pattern.compile(StringUtils.trim(ignore_table_reg),  Pattern.CASE_INSENSITIVE);
		      
		        Matcher m = p.matcher(table);
		        if (m.matches()) {
		            flag = true;
		        }
		        return flag;
		  }else{
			  return false;
		  }
		  
	}
	
	//表含为合法表 比如cle模块表
	public static boolean isneedTable(String table){
		 String need_table_reg = IgnoreTable.paramMap.get(MyParamter.need_table_reg);
		 if(StringUtils.isEmpty(need_table_reg)){
			 need_table_reg = SystemMessage.getString(MyParamter.need_table_reg);
			 
		 }
		 log.info("need_table_reg:"+need_table_reg);
		  if(StringUtils.isNotBlank(need_table_reg)){
			  boolean flag = false;
		        Pattern p = Pattern.compile(StringUtils.trim(need_table_reg) ,Pattern.CASE_INSENSITIVE);
		        Matcher m = p.matcher(table);
		        if (m.matches()) {
		            flag = true;
		        }
		        return flag;
		  }else{
			  return true;
		  }
		  
	}
		
	public static void main(String[] args) {
		 Pattern p = Pattern.compile("^ACC.{1,}$",Pattern.CASE_INSENSITIVE);
		 
	        Matcher m = p.matcher("aCC_CHECK_BUSINESS_MERCHANT" );
	        System.out.println(m.matches());
		System.out.println(IgnoreTable.isneedTable("aCC_CHECK_BUSINESS_MERCHANT"));
	}
}
