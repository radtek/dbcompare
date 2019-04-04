package com.wft.content.freemarker;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wft.content.dao.SysOrgParameterDao;
import com.wft.content.dao.SysParameterDao;
import com.wft.content.dto.CheckType;
import com.wft.content.dto.SysOrgParameter;
import com.wft.content.dto.SysParameter;
import com.wft.content.service.MyArrayList;
import com.wft.content.service.SysOrgParameterService;
import com.wft.content.service.SysParameterService;
import com.wft.db.DBTempory;
import com.wft.util.CommonUtil;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class GenerateContentSQL {

	
	public static String getSql(String commandId, Object model) {  
        try {   
        	Configuration configuration = new Configuration(); 
        	//configuration.setDirectoryForTemplateLoading(new File("D:/公司资料/test/wft/src/com/wft/content/freemarker")); 
   	      	configuration.setDirectoryForTemplateLoading(new File(getSqlTemplate(commandId)).getParentFile()/*new File("D:/公司资料/test/wft/src/com/wft/content/freemarker")*/); 
   	      	configuration.setObjectWrapper(new DefaultObjectWrapper()); 
   	      	configuration.setDefaultEncoding(CommonUtil.OUT_CODE);  //这个一定要设置，不然在生成的页面中 会乱码 
   	       //获取或创建一个模版。 
   	      Template template = configuration.getTemplate(commandId); 
             
            StringWriter writer = new StringWriter(); 
            
            template.process(model, writer);      
            return writer.toString();  
        } catch (TemplateException e) {  
            throw new RuntimeException("Parse sql failed", e);  
        } catch (IOException e) {  
            throw new RuntimeException("Parse sql failed", e);  
        }  
    }  
      
    private static String getSqlTemplate(String commandId) {  
        // 取得配置文件中的SQL文模板  
    	//return new File("D:/公司资料/test/wft/src/com/wft/content/freemarker").getAbsolutePath();
       return GenerateContentSQL.class.getResource(commandId).getFile();
    }  
    
    
	public static void main(String[] args) throws Exception {
		//testsysparameter();
		testsysorgparameter();
	}
    
    
	
	public static void  testsysparameter() throws Exception {
		DBTempory tstandnoLs = new DBTempory("jdbc:oracle:thin:@123.207.118.153:1521:ntest","zhangzhengyi","zhangzhengyi","zhangzhengyi");
		DBTempory tstandLs = new DBTempory("jdbc:oracle:thin:@123.207.118.153:1521:ntest","bolz","bolz","bolz");
		MyArrayList<SysParameter> standLs  = SysParameterDao.getDatas(tstandLs, CheckType.CAN_KAO_DATABASE);
		MyArrayList<SysParameter> standnoLs  = SysParameterDao.getDatas(tstandnoLs, CheckType.NO_CAN_KAO_DATABASE);
		List<SysParameter> ls = SysParameterService.getDatas(standLs,standnoLs);
		for(SysParameter sys:ls){
			System.out.println(sys);
		}
		   Map<String,Object> map = new HashMap<String,Object>();
		   map.put("sysParameters", ls);
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append( GenerateContentSQL.getSql("sysparameter.ftl",map));
		
		System.out.println(sqlBuffer);
	}
	
	public static void  testsysorgparameter() throws Exception {
		DBTempory tstandnoLs = new DBTempory("jdbc:oracle:thin:@123.207.118.153:1521:ntest","zhangzhengyi","zhangzhengyi","zhangzhengyi");
		DBTempory tstandLs = new DBTempory("jdbc:oracle:thin:@123.207.118.153:1521:ntest","gdpsbc","gdpsbc","gdpsbc");
		MyArrayList<SysOrgParameter> standLs  = SysOrgParameterDao.getDatas(tstandLs, CheckType.CAN_KAO_DATABASE);
		MyArrayList<SysOrgParameter> standnoLs  = SysOrgParameterDao.getDatas(tstandnoLs, CheckType.NO_CAN_KAO_DATABASE);
		List<SysOrgParameter> ls = SysOrgParameterService.getDatas(standLs,standnoLs);
		for(SysOrgParameter sys:ls){
			System.out.println(sys);
		}
		   Map<String,Object> map = new HashMap<String,Object>();
		   map.put("sysorgParameters", ls);
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append( GenerateContentSQL.getSql("sysorgparameter.ftl",map));
		
		System.out.println(sqlBuffer);
	}
}
