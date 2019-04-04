package com.wft.appconf.service.freemarker;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.wft.appconf.service.poi.ConfConstants;
import com.wft.db.DBTempory;
import com.wft.util.DateUtils;
import com.wft.util.UploadPathUtil;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author admin
 *替换规则 ${GYY.name.tname.port} ${sheet.服务名.包名称.应用端口}
 *
 */
public class AppConfFreeMakerUtil {

	
	public static void getConf(File pfile, Map<String,Object> resultmap,String tplpath) throws IOException {  
		File pgenfile = new File(pfile,tplpath );
		if(!pgenfile.exists()){
			pgenfile.mkdirs();
		}
		//替换log4j2.xml
    	StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append( AppConfFreeMakerUtil.getConf(new File(UploadPathUtil.getConftplPath()+tplpath+"/log4j2.xml"),resultmap));
		FileUtils.writeStringToFile(new File(pgenfile,"log4j2.xml"), sqlBuffer.toString(), "UTF-8");
		 
		 
    	 
    	sqlBuffer = new StringBuffer();
		sqlBuffer.append( AppConfFreeMakerUtil.getConf(new File(UploadPathUtil.getConftplPath()+tplpath+"/cache.properties"),resultmap));
		FileUtils.writeStringToFile(new File(pgenfile,"cache.properties"), sqlBuffer.toString(), "UTF-8");
		
		//替换app-config.properties 
	
    	sqlBuffer = new StringBuffer();
		sqlBuffer.append( AppConfFreeMakerUtil.getConf(new File(UploadPathUtil.getConftplPath()+tplpath+"/app-config.properties"),resultmap));
		FileUtils.writeStringToFile(new File(pgenfile,"app-config.properties"), sqlBuffer.toString(), "UTF-8");
		
    }  
	private static String getConf(File file, Object model) {  
        try {   
        	Configuration configuration = new Configuration();  
   	      	configuration.setDirectoryForTemplateLoading(new File(getSqlTemplate(file)).getParentFile()) ;
   	      	configuration.setObjectWrapper(new DefaultObjectWrapper()); 
   	      	configuration.setDefaultEncoding("UTF-8");  //这个一定要设置，不然在生成的页面中 会乱码 
   	       //获取或创建一个模版。 
   	      Template template = configuration.getTemplate(file.getName()); 
             
            StringWriter writer = new StringWriter(); 
            
            template.process(model, writer);      
            return writer.toString();  
        } catch (TemplateException e) {  
            throw new RuntimeException("Parse sql failed", e);  
        } catch (IOException e) {  
            throw new RuntimeException("Parse sql failed", e);  
        }  
    }  
      
    private static String getSqlTemplate(File pFile) {  
        // 取得配置文件中的SQL文模板  
    	return pFile.getAbsolutePath();
      
    } 
    
    public static void main(String[] args) throws Exception {
    	DBTempory db = new DBTempory("jdbc:oracle:thin:@118.89.50.227:1521:pingan","cmbcapp01_test","sef2t2t3tEt22!ees3","cmbcapp01_test");
    	List<Map<String,String>> maplist = db.getDatasByTable("select * from MY_BANK_CONF  where 1=1 and PHYSICS_FLAG= 1");
     
    	Map<String,String> dbmap = maplist.get(0);
    	String excelJson = dbmap.get("EXCEL_JSON");
    	//System.out.println(excelJson);
    	Map<String,Object> resultmap = ExcelJsonUtil.parseExcelJson(excelJson);
    	String tomcat_conf = "public-service-conf";
    	ExcelJsonUtil.parseExcelmemcacheJson(resultmap  ,ConfConstants.TOMCAT_MAP.get(tomcat_conf),ConfConstants.SHEET_1);
    	resultmap.put("platform", "180");
    	String genPath = UploadPathUtil.getConfgenPath(); 
    	File pgenfile = new File(genPath,DateUtils.getCurrentTimeFileName());
		if(!pgenfile.exists()){
			pgenfile.mkdirs();
		}
    	getConf(pgenfile,resultmap,tomcat_conf);
     
    	System.out.println("ok");
	}
}
