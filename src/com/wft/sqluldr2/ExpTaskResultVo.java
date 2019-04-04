package com.wft.sqluldr2;

public class ExpTaskResultVo {
	
 
	
	private String filePath;
	
	private String extResultDesc;
	
	private boolean exit ;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getExtResultDesc() {
		return extResultDesc;
	}

	public void setExtResultDesc(String extResultDesc) {
		this.extResultDesc = extResultDesc;
	}

	public boolean isExit() {
		return exit;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
	}

	@Override
	public String toString() {
		return "ExpTaskResultVo [filePath=" + filePath + ", extResultDesc="
				+ extResultDesc + ", exit=" + exit + "]";
	}

	
}
