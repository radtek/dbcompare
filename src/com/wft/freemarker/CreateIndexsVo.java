package com.wft.freemarker;

import java.util.ArrayList;
import java.util.List;

import com.wft.db.Indexs;

/**
 * @author admin
 *表约束信息(除了主键)
 */
public class CreateIndexsVo {

	private String shema;//表空间
	
	private String table;//表
	
 
	private List<Indexs> columns = new ArrayList<Indexs>();
	
	
	

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




	public List<Indexs> getColumns() {
		return columns;
	}




	public void setColumns(List<Indexs> columns) {
		this.columns = columns;
	}



 
	
	
}
