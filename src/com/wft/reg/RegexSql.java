package com.wft.reg;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.wft.exception.AppException;

/**
 * @author admin sql正则表达式验证
 */
public class RegexSql {

	private final static Logger log = Logger.getLogger(RegexSql.class);
 
	public static final String CREATE_TABLE = "(.*)(create)|(alter)(.*)";
	public static final String INSERT_TABLE = "insert{1,}(.*)";//index on
	
	public static final String CREATE_TABLE_SCHEMA = "(.*)(column|table|index|sequence)\\s+zhifu\\.(.*)";
	public static final String CREATE_TABLE_SCHEMA_ON = "(.*)(on)\\s+zhifu\\.(.*)";
	public static final String INSERT_TABLE_SCHEMA = "(.*)(into|update|from)\\s+zhifu\\.(.*)";
	
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	
	//是否ddl脚本
	public static boolean isDDL(String ...sources) {
		boolean flag = false;
		for(String source:sources){
			source = source.toLowerCase().trim();
			boolean resultC = source.startsWith("create")||source.startsWith("alter");
			if(resultC){
				flag = true;
				break;
			}else{
				flag = false;
			}
			
		}
		System.out.println("isDDL:"+flag);
		return flag;
	}
	
	//是否dml脚本
	public static boolean isDML(String ...sources) {
		boolean flag = false;
		for(String source:sources){
			source = source.toLowerCase().trim();
			boolean resultC = source.startsWith("insert")||source.startsWith("update")||source.startsWith("delete"); 
		 
			if(resultC){
				flag = true;
				break;
			}else{
				flag = false;
			}
			 
		}
		System.out.println("isDML:"+flag);
		return flag;
	}
	
	//是否符合ddl脚本
	public static boolean isDDLFormat(String ...sources) {
		boolean flag = false;
		for(String source:sources){
			flag = matches(true,CREATE_TABLE_SCHEMA, source);
			if(!flag){
				throw new AppException("该语句没有加SCHEMA:zhifu,请加上:"+source);
				
			}
			//bug修复 此处add constraint zhifu.CMS_QR_CODE_DYNAMIC_INFO_PK错误 不能加 zhifu
			boolean b =  Pattern.matches("(.*)(constraint)\\s+zhifu\\.(.*)", source);
			 
			if(b){
				throw new AppException("该语句constraint 关键字后名字不能添加zhifu:"+source);
				
			}
		}
		
		return flag;
	}
 

	//是否符合dml脚本
	public static boolean isDMLFormat(String ...sources) {
		boolean flag = false;
		for(String source:sources){
			flag = matches(false,INSERT_TABLE_SCHEMA, source);
			if(!flag){
				throw new AppException("该语句没有加SCHEMA:zhifu,请加上:"+source);
				
			}
			 
		}
		
		return flag;
	}
	
	
	private static boolean matches(boolean isDDl,String regex, String source)	{
	    if ((source == null) || (source.equals(""))) {
	    	log.info("Invalid Souce Value.");
	      return false;
	    }
	    Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
	    Matcher m = pattern.matcher(source);
	    boolean b = m.matches();
	    
	    String zhifustr = source.toLowerCase().trim();
	    boolean isIndex = false;
	    if(isDDl){//\bfirst\b
	    	//alter table zhifu.CMS_RNS_CONF add constraint CMS_RNS_CONF_PK primary key (ID) using index;排除这种可能
	    	isIndex = (!zhifustr.endsWith("index"))&&zhifustr.contains("index");//
	    	if(isIndex){//此处on替换下index 防止mon之类的干扰,这里同时也取巧使用on化为index
	    		zhifustr = zhifustr.replaceAll("\\bon\\b", "index");
	    	}
	    }
	    boolean result = b;
	     while(result){//循环截取
	    	 //此处取后面判断 会导致前面没有判断导致bug,所有需要判断前面截取情况
	    	  int len = zhifustr.length();
	    	  int index = zhifustr.indexOf("zhifu.");
		      String preZhifustr =zhifustr.substring(0,index);
	    	  zhifustr = zhifustr.substring(index+6,len);//此处取后面判断 会导致前面没有判断导致bug
	    	  if(isDDl){//是ddl语句得需要查看是否是index创建索引语句 因为索引on后面需要添加表空间  
	    		  if(isIndex&&(zhifustr.contains("index"))&&index>=0){ 
	    			  System.out.println("是ddl语句得需要查看是否是index创建索引语句 因为索引on后面需要添加表空间");
		    		  m = pattern.matcher(zhifustr);
			    	  result = b = m.matches();
			    	  
		    	  }else{
		    		  result = false;
		    	  }  
	    	  }else{
	    		// int fromindex = zhifustr.indexOf("from");

		    	  System.out.println("zhifustr:"+zhifustr + " preZhifustr:"+preZhifustr);
	    		if((zhifustr.contains("from"))&&index>=0){ 
	    			  //dml语句得需要后面查看是否使用了select from zhifu.table语句
	    			  System.out.println("dml语句得需要后面查看是否使用了select from zhifu.table语句 zhifustr:"+zhifustr);
		    		  m = pattern.matcher(zhifustr);
			    	  result = b = m.matches();
			    	  
		    	  }else{
		    		  result = false;
		    	  } 
	    		 if((preZhifustr.contains("from"))){ 
	    			int fromindex = preZhifustr.lastIndexOf("from");
	    			if(fromindex>=0){
	    				 preZhifustr =preZhifustr.substring(0,fromindex);
	    				 if((preZhifustr.contains("from"))){
	    					 //dml语句得需要后面查看是否使用了select from zhifu.table语句
			    			  System.out.println("dml语句得需要后面查看是否使用了select from zhifu.table语句 preZhifustr:"+preZhifustr);
				    		  m = pattern.matcher(preZhifustr);
					    	  result = b = m.matches();
					    	  
	    				 }
		    			 
	    			}
			    	
		    	  }/*else{
		    		  result = false;
		    	  } */
	    	  }
	    	
	    } 
	     //针对索引需要另外判断
	     String nextvalstr = source.toLowerCase();
	     if(b&&nextvalstr.contains("nextval")&&nextvalstr.contains("values")){
	    	 int vindex = nextvalstr.indexOf("values");
	    	 nextvalstr = nextvalstr.substring(vindex);
	    	 System.out.println("针对nextval索引需要另外判断");
	    	 boolean nextvalresult = b;
		     while(nextvalresult){//循环截取
		    	  int index = nextvalstr.lastIndexOf("nextval");
		    	  System.out.println(index);
		    	  if(index==-1){
		    		  return b;
		    	  }
		    	  nextvalstr = nextvalstr.substring(0,index);
	    		 if(index>=0){//nextval是否序列语句
	    			 String ss = nextvalstr+"nextval";
	    			 System.out.println(ss);
	    			 nextvalresult = b =  Pattern.matches("(.*)zhifu\\.(.*)\\.nextval(.*)", ss);
	    		  }
	    		 else{
	    			 nextvalresult = false;
		    	  } 
		    } //end while
		    if(!b){
		    	throw new AppException("该语句索引nextval没有加SCHEMA:zhifu,请加上:"+source);
		    }
	     }//end if if(b&&nextvalstr.contains("nextval")){
	    
	     return b;
	  }

 
	public static void main(String[] args) throws IOException {
	    String upload  =  "insert into zhifu.cms_role_permission (id, role_id, func_id, permission_id, create_time) values (zhifu.seq_cms_role_permission.nextval, (select r.role_id from  zhifu.cms_role r where r.role_name = '超级系统管理员角色'), (select FUNC_ID from zhifu.CMS_FUNC where FUNC_CODE(select FUNC_ID from zhifu.CMS_FUNC where FUNC_COD = 'ACC_TERMINAL_TASK'), 'EDIT', sysdate)";
	 //   String upload  ="cms_bank t where t.bank_name = '深圳发展银行'),'深圳发展银行',null,null,null,null,null,null,sysdate,null);";
	    //System.out.println(upload.replace("zhifu.", " "));
		//boolean result = isDMLFormat(upload);
		//slog.info(result);
	    /*String ddl  = "alter table zhifu.CMS_RNS_CONF add constraint CMS_RNS_CONF_PK primary key (ID) using index;";
	    //ddl="create index zhifu.idx_cle_cleaning_bill_01 on CLE_CLEANING_BILL(SERIAL_NUMBER) online;";
	    boolean result = isDDLFormat(ddl);
		log.info(result);*/
		String[] sqls = ParseSql.parseFromFile("C:/Users/admin/Desktop/20180329-硬件台卡接入相关表.sql");
		boolean result =isDDLFormat(sqls);
		log.info(result);
	}
}
