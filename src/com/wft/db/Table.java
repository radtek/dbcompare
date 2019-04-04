package com.wft.db;

import java.util.ArrayList;
import java.util.List;
 
/**
 * @author admin
 *表相关所有信息
 */
public class Table {
    private String name  ;//表名称
    private String comment  ;//注释
    private List<Column> cols = new ArrayList<Column>();//列
    private String seq  ;//序列
    private List<Constraints> constraints = new ArrayList<Constraints>();//约束
    
    public Table() {
		// TODO Auto-generated constructor stub
	}
	public Table(String name, String comment) {
		super();
		this.name = name;
		this.comment = comment;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public List<Column> getCols() {
		return cols;
	}


	public void setCols(List<Column> cols) {
		this.cols = cols;
	}


	public String getSeq() {
		return seq;
	}


	public void setSeq(String seq) {
		this.seq = seq;
	}


	public List<Constraints> getConstraints() {
		return constraints;
	}


	public void setConstraints(List<Constraints> constraints) {
		this.constraints = constraints;
	}


	@Override
	public String toString() {
		return "Table [name=" + name + ", comment=" + comment + ", cols="
				+ cols + ", seq=" + seq + ", constraints=" + constraints + "]";
	}
 
   
 
}