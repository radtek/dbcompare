package com.wft.db;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * 索引信息(除了主键唯一索引)
 * select t.INDEX_NAME,T.TABLE_NAME,T.COLUMN_NAME  from user_ind_columns t,user_indexes i where t.index_name = i.index_name  and i.UNIQUENESS!='UNIQUE'
 *  and t.table_name='ACC_CHECK_BILL_MERCHANT'
 */
public class Indexs {
	
	/**
	 * 约束名字
	 */
	private String IndexName;
	/**
	 * 表名
	 */
	private String tableName;
	
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 列明列表
	 */
	private List<String> columnNameLists = new ArrayList<String>();
	
	
	/**
	 * 列明约束组合
	 */
	private String columnNames  ;
	
	public void addColumnNames(String columnName) {
		columnNameLists.add(columnName);
	}
	
	public String getColumnNames( ) {
		 StringBuffer sb = new StringBuffer();
		 for(String cns:columnNameLists){
			 sb.append(cns+",");
		 }
		 if(sb.length()>0){
			 sb.deleteCharAt(sb.length()-1);
		 }
		 return sb.toString();//Arrays.toString(columnNameLists.toArray());
	}
	
	 
	public String getIndexName() {
		return IndexName;
	}

	public void setIndexName(String indexName) {
		IndexName = indexName;
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	 
	
 
}
