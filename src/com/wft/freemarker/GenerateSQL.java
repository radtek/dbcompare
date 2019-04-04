package com.wft.freemarker;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wft.db.Constraints;
import com.wft.util.CommonUtil;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author admin
 * 包内调用
 */
class GenerateSQL {

	
	/**
	 * 创建索引信息(除了主键)
	 * @param model
	 * @return
	 */
	public static String getCreateIndexsSql(CreateIndexsVo model) {
		 
	    return getSql("index.ftl",model);
	}  
	
	
	/**
	 * 创建表约束信息(除了主键)
	 * @param model
	 * @return
	 */
	public static String getCreateConstraintsSql(CreateConstraintsVo model) {
		 
	    return getSql("constraint.ftl",model);
	}  
	
	
	/**
	 * 创建表
	 * @param model
	 * @return
	 */
	public static String getCreateSql(CreateTableVo model) {
		  
	    return getSql("create.ftl",model);
	}  
	
	/**
	 * 创建缺失列
	 * @param columns
	 * @return
	 */
	public static String getAlertSql(List<SqlVo> columns) { 
		
	   Map<String,Object> map = new HashMap<String,Object>();
	   map.put("columns", columns);
	  
       return getSql("alter.ftl",map);
    }  
	
	
	private static String getSql(String commandId, Object model) {  
        try {  
        	Configuration configuration = new Configuration(); 
   	      	configuration.setDirectoryForTemplateLoading(new File(getSqlTemplate(commandId)).getParentFile()); 
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
    	//return "D:/公司资料/test/wft/src/com/wft/freemarker/constraint.ftl";
       return GenerateSQL.class.getResource(commandId).getFile();
    }  
    
    
    public static void main(String[] args) {
    	List<SqlVo> columns = new ArrayList<SqlVo>();
    	CreateTableVo createTableVo = new CreateTableVo();
    	createTableVo.setPrimaryKey("id");
    	createTableVo.setShema("bsbank");
    	createTableVo.setTable("MY");
    	SqlVo sqlVo = new SqlVo();
    	sqlVo.setCode("ID");sqlVo.setComment("主键");sqlVo.setLen("10");sqlVo.setShema("bsbank");sqlVo.setTable("MY");sqlVo.setNotNull(true);
    	sqlVo.setType("NUMBER");sqlVo.setTimeCol(false);sqlVo.setScale(6);
    	columns.add(sqlVo);
    	sqlVo = new SqlVo();
    	sqlVo.setCode("NAME");sqlVo.setComment("姓名");sqlVo.setLen("100");sqlVo.setShema("bsbank");sqlVo.setTable("MY");
    	sqlVo.setType("VARCHAR2");sqlVo.setTimeCol(false);
    	columns.add(sqlVo);
    	createTableVo.setColumns(columns);
    	System.out.println(getAlertSql(columns)); 
    	System.out.println(getCreateSql(createTableVo)); 
    	
    /*	List<Constraints> bases = new ArrayList<Constraints>();
	 	bases.add(new Constraints("AA", "U", "ZS"));
		bases.add(new Constraints("AA", "U", "B"));
		bases.add(new Constraints("AA", "U", "ZS.N"));
		 List<Constraints> temps =  new ArrayList<Constraints>();
		  
		// temps.add(new Constraints("AA", "U", "B"));
		 temps.add(new Constraints("AA", "U", "ZS.d", "SYS_C12"));
		 
		 CreateConstraintsVo model = new CreateConstraintsVo();
			model.setColumns(Constraints.getDiffConstraints(bases,temps));
			model.setShema("zs");
			model.setTable("tt");
		 System.out.println(  getCreateConstraintsSql(model));*/
	}
    
    
}
