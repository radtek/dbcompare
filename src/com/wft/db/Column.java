package com.wft.db;

import org.apache.commons.lang.StringUtils;

/**
 * @author admin
 * 只比较code
 * 只比较code 也就是表名称 对象就相同
 */
public class Column implements Comparable<Column> {
	 
    //1列中文名
    private String name = "";
     
    //2列名
    private String code = "";
     
    //3数据类型
    private String dataType="";
 
     
    //5是否主键
    private String primary="";
     
    //6是否允许null
    private String mandatory="";
    
    //7长度
    private String dataLen="";
    
    //8 创建时间
    private String lastAnalyzed="";
    
    //精度
    private Integer scale ;
     
    //对比 条目比对
    private boolean compare ;
    
    //对比 条目比对 false 表示不存在
    private boolean tableCompare ;
    
    //对比 条目比对 false 表示不存在
    private boolean colCompare ;
    
    //约束是否相同 表示不相同
    private boolean checkSame ;
    
    //序列是否相同 表示不相同
    private boolean seqSame ;
    
   //2列名 作废 但是不能删除
    private String colCode = "";
     
    public String getColCode() {
		return colCode;
	}

	public void setColCode(String colCode) {
		this.colCode = colCode;
	}

	public Column(){
         
    }

	public Column(String name, String code, String dataType,
			String primary, String mandatory, String dataLen,Integer scale ) {
		super();
		this.name = name;
		this.code = code;
		this.dataType = dataType;
		this.primary = primary;
		this.mandatory = mandatory;
		this.dataLen = dataLen;
		this.scale = scale;
	}

	public Column(String name, String code, String dataType,
			String primary, String mandatory, String dataLen,Integer scale,boolean compare) {
		super();
		this.name = name;
		this.code = code;
		this.dataType = dataType;
		this.primary = primary;
		this.mandatory = mandatory;
		this.dataLen = dataLen;
		this.scale = scale;
		this.compare = compare;
	}
	public Column(String name, String code, String dataType) {
		super();
		this.name = name;
		this.code = code;
		this.dataType = dataType;
	}

	public Column(String name, String code, String dataType,boolean compare) {
		super();
		this.name = name;
		this.code = code;
		this.dataType = dataType;
		this.compare = compare;
	}
	
	
	
	public String getLastAnalyzed() {
		return lastAnalyzed;
	}

	public void setLastAnalyzed(String lastAnalyzed) {
		this.lastAnalyzed = lastAnalyzed;
	}

	
	public String getName() {
		name = StringUtils.isBlank(name)?"":name;
        int len = name.indexOf(".");
        if(len<0){
      	  len = name.indexOf("，");
        }
        if(len<0){
      	  len = name.indexOf(",");
        }
		return StringUtils.substring(name, 0, len>0?(len):10);
	}

	public void setName(String name) {
		this.name = name;
		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

 

	public String getPrimary() {
		return primary;
	}

	public void setPrimary(String primary) {
		this.primary = primary;
	}

	public String getMandatory() {
		return mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

	public String getDataLen() {
		return dataLen;
	}

	public void setDataLen(String dataLen) {
		this.dataLen = dataLen;
	}

	public boolean isCompare() {
		return compare;
	}

	public void setCompare(boolean compare) {
		this.compare = compare;
	}

	public boolean isTableCompare() {
		return tableCompare;
	}

	public void setTableCompare(boolean tableCompare) {
		this.tableCompare = tableCompare;
	}

	public boolean isColCompare() {
		return colCompare;
	}

	public void setColCompare(boolean colCompare) {
		this.colCompare = colCompare;
	}


     

	public boolean isCheckSame() {
		return checkSame;
	}

	public void setCheckSame(boolean checkSame) {
		this.checkSame = checkSame;
	}

		//code重写也就是表名称
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((code == null) ? 0 : code.hashCode());
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
			Column other = (Column) obj;
			if (code == null) {
				if (other.code != null)
					return false;
			} else if (!code.equals(other.code))
				return false;
			return true;
		}

	 

	public boolean isSeqSame() {
			return seqSame;
		}

		public void setSeqSame(boolean seqSame) {
			this.seqSame = seqSame;
		}

 

	@Override
		public String toString() {
			return "Column [name=" + name + ", code=" + code + ", dataType="
					+ dataType + ", primary=" + primary + ", mandatory="
					+ mandatory + ", dataLen=" + dataLen +", scale=" + scale +  ", lastAnalyzed="
					+ lastAnalyzed + ", compare=" + compare + ", tableCompare="
					+ tableCompare + ", colCompare=" + colCompare
					+ ", checkSame=" + checkSame + ", seqSame=" + seqSame
					+ ", colCode=" + colCode + "]";
		}

	
	
	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	@Override
	public int compareTo(Column o) {
		// 只比较code 也就是表名称
		return this.code.compareToIgnoreCase(o.code);
	}

	 
     
  
     
}