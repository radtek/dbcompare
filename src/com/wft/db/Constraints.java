package com.wft.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author admin
 * 表约束信息(除了外键 暂时无法生成语句)
 select  CU.constraint_name,CU.table_name,CU.column_name,au.CONSTRAINT_TYPE,au.SEARCH_CONDITION from user_cons_columns cu, user_constraints au 
  where cu.constraint_name=au.constraint_name  
 and cu.table_name= 'CMS_MERCHANT_BILL_NOTE_DETAIL';
 */
//columnNames constraintType tableName组合来判断唯一性 也就是相等 对象就相同
public class Constraints implements Comparable<Constraints>{
	
	//表约束信息类型枚举
	public  static enum CON_TYPE{
		
		 U("U"),//唯一建
		 C("C"),//check建
		 P("P"),//主键
		 R("R");//外键
		 private String value;
		 private CON_TYPE(String value) {
		        this.value = value;
		 }
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		 
		 
	};
	
 
	/**
	 * 约束名字
	 */
	private String constraintName = "";
	/**
	 *  表约束信息类型
	 */
	private String constraintType;
	
	/**
	 *  表约束信息类型=C 约束类型信息内容
	 */
	private String searchCondition ;
	
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 列明列表
	 */
	private List<String> columnNameLists = new ArrayList<String>();
	
	/**
	 * 是否删除 默认不删除 
	 */
	private boolean drop = false;
	
	/**
	 * 列明约束组合
	 */
	private String columnNames  ;
	
	public Constraints() {
		
	}
	
	
	
	public Constraints( String tableName,String constraintType, String columnNames) {
		super();
		this.tableName = tableName;
		this.constraintType = constraintType;
		this.columnNames = columnNames;
	}
	public Constraints( String tableName,String constraintType, String columnNames,String constraintName) {
		 this(tableName, constraintType, columnNames);
		this.constraintName = constraintName;
	}


	public void addColumnNames(String columnName) {
		columnNameLists.add(columnName);
	}
	 
	
	public String getColumnNames( ) {
		 StringBuffer sb = new StringBuffer();
		 for(String cns:columnNameLists){
			 sb.append(cns.toUpperCase().trim()+",");
		 }
		 if(sb.length()>0){
			 sb.deleteCharAt(sb.length()-1);
		 }
		 return sb.toString();//Arrays.toString(columnNameLists.toArray());
	}
	
	public String getConstraintName() {
		return constraintName;
	}
	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	
	 


	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}



	public String getConstraintType() {
		return StringUtils.isEmpty(constraintType)?"":constraintType.toUpperCase();
	}



	public void setConstraintType(String constraintType) {
		this.constraintType = constraintType;
	}



	public String getSearchCondition() {
		return searchCondition;
	}



	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

 
	public boolean isDrop() {
		return drop;
	}



	public void setDrop(boolean drop) {
		this.drop = drop;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columnNames == null) ? 0 : columnNames.hashCode());
		result = prime * result
				+ ((constraintType == null) ? 0 : constraintType.hashCode());
		result = prime * result
				+ ((tableName == null) ? 0 : tableName.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Constraints other = (Constraints) obj;
		if (columnNames == null) {
			if (other.columnNames != null)
				return false;
		} else if (!columnNames.equals(other.columnNames))
			return false;
		if (constraintType == null) {
			if (other.constraintType != null)
				return false;
		} else if (!constraintType.equals(other.constraintType))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}




	/* (non-Javadoc)
	 *columnNames constraintType tableName组合来判断唯一性
	 */
	@Override
	public int compareTo(Constraints o) {
		 StringBuffer thissb = new StringBuffer(this.tableName);
		 thissb.append(this.constraintType);
		 thissb.append(this.columnNames);
		 
		 StringBuffer othersb = new StringBuffer(o.tableName);
		 othersb.append(o.constraintType);
		 othersb.append(o.columnNames);
		 
		 return thissb.toString().compareToIgnoreCase(othersb.toString());
	}


 

 
	/**
	 * @param bases 基础库
	 * @param temps 比较库
	 * @return 返回两个Constraints差异 顺序是 比较快需要drop的约束排在需create约束前面
	 */
	 @SuppressWarnings("unchecked")
	public static  List<Constraints> getDiffConstraints(List<Constraints> bases,List<Constraints> temps){
		 List<Constraints> results = new ArrayList<Constraints>();
		 if(bases==null){
			 bases = new ArrayList<Constraints>();
		 }
		 if(temps==null){
			 temps = new ArrayList<Constraints>();
		 }
		 //两者交集 说明都存在不做处理
		Collection<Constraints> interConstraints = CollectionUtils.intersection(bases, temps);
		 //临时库多的 需要drop掉
		 Collection<Constraints> tempsOUtConstraints = CollectionUtils.subtract(temps, interConstraints);
		 //基础库多的 需要create
		 Collection<Constraints> basesOUtConstraints = CollectionUtils.subtract(bases, interConstraints);
		// 需要drop掉
		 if(CollectionUtils.isNotEmpty(tempsOUtConstraints)){
			 for(Constraints c:tempsOUtConstraints){
				 c.setDrop(true);
				 results.add(c);
			 }
		 }
		// 需要create
		 if(CollectionUtils.isNotEmpty(basesOUtConstraints)){
			 for(Constraints c:basesOUtConstraints){
				  
				 results.add(c);
			 }
		 }
		 return results;
	}
	 
	@Override
	public String toString() {
		return "Constraints [constraintName=" + constraintName
				+ ", constraintType=" + constraintType + ", searchCondition="
				+ searchCondition + ", tableName=" + tableName
				+ ", columnNameLists=" + columnNameLists + ", drop="
				+ drop + ", columnNames=" + columnNames + "]";
	}

	public static void main(String[] args) {
		List<Constraints> bases = new ArrayList<Constraints>();
	 	bases.add(new Constraints("AA", "U", "ZS"));
		bases.add(new Constraints("AA", "U", "B"));
		bases.add(new Constraints("AA", "U", "ZS.N"));
		 List<Constraints> temps =  new ArrayList<Constraints>();
		  
		 temps.add(new Constraints("AA", "U", "B"));
		 temps.add(new Constraints("AA", "U", "ZS.d"));
		 
		 System.out.println( ArrayUtils.toString(Constraints.getDiffConstraints(bases,temps)));
	}
 
}
