package com.wft.util.fjnx;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;


public class ExcleUtilsTest {

	 
	public static void main(String[] args) throws Exception {
		testAll();
	}

	
	public static void testAll() throws Exception {
		ExcleUtils.addAll(getTables());
		 File file = new File( "D:/公司资料/test/wft/src/com/wft/util/fjnx/系统数据变更表(fjnx).xlsx");
		 ExcleUtils.gen(file,null,null);
	}
	
	public static void testA() throws Exception {
		ExcleUtils.addAll(getTables() );
		 File file = new File( "D:/公司资料/test/wft/src/com/wft/util/fjnx/export_time.xlsx");
		 ExcleUtils.gen(file,"2018-01-01","2019-01-01");
	}
	
	
	public static List<String> getTables() throws Exception{
		 List<String> tables = new ArrayList<String>();
		 for(String t:FileUtils.readLines(new File("D:/公司资料/test/wft/src/com/wft/util/fjnx/tables.txt"))){
			 tables.add(t.trim().toUpperCase());
		 }
		 return tables;
	}
}
