package com.wft.freemarker;

import java.util.ArrayList;
import java.util.List;

public class SqlVo {

	//特殊类型类似时间列 构造脚本时不需要 指名长度了
	public static List<String> specialTypes = new ArrayList<String>();
	static{
		specialTypes.add("BLOB");
		specialTypes.add("CLOB");
		specialTypes.add("DATE");
		specialTypes.add("TIMESTAMP");
	}
	private String shema;//表空间
	
	private String table;//表
	
	private String code;//列
	
	private String type;//类型  BLOB CLOB DATE 不需要加 NUMBER(11)  VARCHAR2(128)   TIMESTAMP(6) 
	
	 
	private String len;//长度
	private int scale = 0;
	private String scaleStr = "";
	private String comment;//注释
	
	 
    private boolean primary ; //是否主键
     
    
    private boolean notNull;//是否允许null
    
    
    private boolean timeCol;//是否TIME

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLen() {
		return len;
	}

	public void setLen(String len) {
		this.len = len;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isTimeCol() {
		return timeCol;
	}

	public void setTimeCol(boolean timeCol) {
		this.timeCol = timeCol;
	}
	
	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}
	
	

	public String getScaleStr() {
		return scale>0?","+scale:"";
	}

	public void setScaleStr(String scaleStr) {
		this.scaleStr = scaleStr;
	}

	@Override
	public String toString() {
		return "SqlVo [shema=" + shema + ", table=" + table + ", code=" + code
				+ ", type=" + type + ", len=" + len+", scale=" + scale + ", comment=" + comment
				+ ", primary=" + primary + ", notNull=" + notNull
				+ ", timeCol=" + timeCol + "]";
	}



	 
	 
}
