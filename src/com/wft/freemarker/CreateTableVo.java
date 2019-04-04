package com.wft.freemarker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 *
 */
public class CreateTableVo {

	private String shema;//表空间
	
	private String table;//表
	
	private String primaryKey;//主键
	
	private List<SqlVo> columns = new ArrayList<SqlVo>();
	 

	public String getShema() {
		return shema;
	}




	public void setShema(String shema) {
		this.shema = shema;
	}




	public String getTable() {
		return table;
	}




	public void setTable(String table) {
		this.table = table;
	}




	public String getPrimaryKey() {
		return primaryKey;
	}




	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}




	public List<SqlVo> getColumns() {
		return columns;
	}




	public void setColumns(List<SqlVo> columns) {
		this.columns = columns;
	}




	@Override
	public String toString() {
		return "CreateTableVo [shema=" + shema + ", table=" + table
				+ ", primaryKey=" + primaryKey + ", columns=" + columns + "]";
	}

 


 
	
}
