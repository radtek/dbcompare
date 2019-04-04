package com.wft.db;

public class ColExist {

	  //条目比对
    private boolean compare = true ;
    
   //表比对  比较某一表下是否不缺表 缺-false
    private boolean tableCompare  = true;
    
    //列比对 比较某一表下是否不缺列 缺-false
    private boolean colCompare   = true;

    //约束是否相同 表示不相同
    private boolean checkSame = true;

    
    //序列是否相同 表示不相同
    private boolean seqSame = true;

    
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

	public boolean isSeqSame() {
		return seqSame;
	}

	public void setSeqSame(boolean seqSame) {
		this.seqSame = seqSame;
	}

	@Override
	public String toString() {
		return "ColExist [compare=" + compare + ", tableCompare="
				+ tableCompare + ", colCompare=" + colCompare + ", checkSame="
				+ checkSame + ", seqSame=" + seqSame + "]";
	}

	 
    
}
