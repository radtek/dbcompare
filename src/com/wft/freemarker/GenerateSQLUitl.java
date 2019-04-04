package com.wft.freemarker;

import java.util.ArrayList;
import java.util.List;

import com.wft.db.Column;
import com.wft.db.Constraints;
import com.wft.db.Indexs;

public class GenerateSQLUitl {

	
	/**
	 * 创建表索引信息(除了主键)
	 * @param columns
	 * @return
	 */
	public static String getCreateIndexsSql(List<Indexs> tepCols,String schame,String table) { 
		CreateIndexsVo model = new CreateIndexsVo();
		model.setColumns(tepCols);
		model.setShema(schame);
		model.setTable(table);
		return GenerateSQL.getCreateIndexsSql(model);
	}
	
	/**
	 * 创建表约束信息(除了主键)
	 * @param columns
	 * @return
	 */
	public static String getCreateConstraintsSql(List<Constraints> tepCols,String schame,String table) { 
		CreateConstraintsVo model = new CreateConstraintsVo();
		model.setColumns(tepCols);
		model.setShema(schame);
		model.setTable(table);
		return GenerateSQL.getCreateConstraintsSql(model);
	}
	/**
	 * 创建表
	 * @param model
	 * @return
	 */
	public static String getCreateSql(List<Column> tepCols,String shema,String table) {
		List<SqlVo> sqlVos = new ArrayList<SqlVo>();
    	CreateTableVo createTableVo = new CreateTableVo();
    	String primaryKey = "ID";
    	for(Column column:tepCols){
    		SqlVo sqlVo = new SqlVo();
    		sqlVo.setCode(column.getCode());sqlVo.setComment(column.getName());
    		sqlVo.setLen(column.getDataLen());
    		if(column.getScale()!=null&&column.getScale()>0){
    			sqlVo.setScale(column.getScale());
    		}
    		if("TRUE".equalsIgnoreCase(column.getPrimary())){//是否主键
    			sqlVo.setPrimary(true);
    			primaryKey = column.getCode();
    		}
    		if("FALSE".equalsIgnoreCase(column.getMandatory())){//是否为空
    			sqlVo.setNotNull(true);
    		 
    		}
    		 
    		for(String specialType:SqlVo.specialTypes){//是否构造脚本时不需要 指名长度了类似时间列,需要特别处理 会自带类型
    			if(column.getDataType().toUpperCase().contains(specialType)){
    				sqlVo.setTimeCol(true);
    				break;
    			}
    		}
    		 
    		sqlVo.setShema(shema);
    		sqlVo.setTable(table);sqlVo.setType(column.getDataType());
    		sqlVos.add(sqlVo);
    	}
    	
    	createTableVo.setColumns(sqlVos);
    	createTableVo.setPrimaryKey(primaryKey);
    	createTableVo.setShema(shema);
    	createTableVo.setTable(table);
	    return GenerateSQL.getCreateSql(createTableVo);
	}  
	
	/**
	 * 创建缺失列
	 * @param columns
	 * @return
	 */
	public static String getAlertSql(List<Column> tepCols,String schame,String table) { 
		List<SqlVo> sqlVos = new ArrayList<SqlVo>();
		for(Column column:tepCols){
    		SqlVo sqlVo = new SqlVo();
    		sqlVo.setCode(column.getCode());sqlVo.setComment(column.getName());
    		sqlVo.setLen(column.getDataLen());
    		if("TRUE".equalsIgnoreCase(column.getPrimary())){
    			sqlVo.setPrimary(true);
    		 
    		}
    		if("FALSE".equalsIgnoreCase(column.getMandatory())){
    			sqlVo.setNotNull(true);
    		 
    		}
    		if(column.getDataType().contains("TIMESTAMP")){//是否时间列,需要特别处理
    			sqlVo.setTimeCol(true);
    		 
    		}
    		sqlVo.setShema(schame);
    		sqlVo.setTable(table);sqlVo.setType(column.getDataType());
    		sqlVos.add(sqlVo);
    	}
    	
	    return  GenerateSQL.getAlertSql(sqlVos);
	}  
		
	
 
}
