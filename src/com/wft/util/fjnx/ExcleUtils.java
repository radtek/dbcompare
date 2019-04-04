package com.wft.util.fjnx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.wft.db.Column;
import com.wft.db.DBUtil;
import com.wft.db.Indexs;
import com.wft.util.CommonUtil;
import com.wft.util.CommonUtil.BatchData;

public class ExcleUtils {

	private static ExecutorService threadExec =  Executors.newFixedThreadPool(50);
	 
	private static List<String> tables = new ArrayList<String>();
	
	
	private static File tempfile = new File("D:/公司资料/test/wft/src/com/wft/util/fjnx/系统数据变更表.xlsx");
	
	public static void add(String table){
		tables.add(table);
	}

	public static void addAll(List<String>  ts){
		tables.addAll(ts);
	}
	private static XSSFWorkbook wb = null;
	
	public static void gen(File file,String startTime,String endTime) throws Exception {
		init();
		if(StringUtils.isEmpty(startTime)){
			startTime = null; 
		}else{
			startTime += " 00:00:00";
		}
		if(StringUtils.isEmpty(endTime)){
			endTime = null; 
		}else{
			endTime +=" 23:59:59";
		}
		List<FjnxTableVo> fjnxTableVos = getFilterTableNames();
		final LinkedHashMap<FjnxTableVo, List<Column>>  fjnxSqlVoColumnMaps = new LinkedHashMap<FjnxTableVo, List<Column>>(fjnxTableVos.size());
		final LinkedHashMap<FjnxTableVo, List<Indexs>>  fjnxSqlVoIndexsMaps = new LinkedHashMap<FjnxTableVo, List<Indexs>>(fjnxTableVos.size());
		
		/*
		 * 多线程扛不住
		 List<BatchData<FjnxTableVo>> tabsbatchDataList = CommonUtil.getBatchDataList(20,fjnxTableVos);
		
		 List<Future<String>> tabsbatchcheckTableNamesTasks = new ArrayList<Future<String>>();
    	 for(final BatchData<FjnxTableVo> batch:tabsbatchDataList){
    		 int index = batch.getBatchIndex();
			 System.out.println("tabsbatchDataList 批次:"+index);
			 final List<FjnxTableVo> tabcols = batch.getDataList();
    		 Future<String> tabsfreuslt = threadExec.submit(new Callable<String>() {
    			   @Override
	     			public String call() throws Exception {
    				   for(FjnxTableVo fjnxTableVo:tabcols){
    						//表字段
    						fjnxSqlVoColumnMaps.put(fjnxTableVo,  DBUtil.getOracleTCloumns(fjnxTableVo.getTable(),null,null));
    						//表索引
    						fjnxSqlVoIndexsMaps.put(fjnxTableVo, DBUtil.getIndexsByTableContainPrimarykey(fjnxTableVo.getTable()));
    					}
	     				return "ok";
	     			}
	    		 });
    		 //任务后台运行
	    	 tabsbatchcheckTableNamesTasks.add(tabsfreuslt);
			
    	 }//end for
     
    	 for(Future<String> task:tabsbatchcheckTableNamesTasks){
    		//多线程返回结果
    		 System.out.println("tabsbatchDataList 批次:"+task.get());
    	 }//end batchcheckTableNamesTasks get()
*/     
	//单线程
	 for(FjnxTableVo fjnxTableVo:fjnxTableVos){
			//表字段
			fjnxSqlVoColumnMaps.put(fjnxTableVo,  DBUtil.getOracleTCloumns(fjnxTableVo.getTable(),startTime,endTime));
			//表索引
			fjnxSqlVoIndexsMaps.put(fjnxTableVo, DBUtil.getIndexsByTableContainPrimarykey(fjnxTableVo.getTable()));
		}
		 // excle 2007
		createXLSX表结构变更(fjnxSqlVoColumnMaps,file);
		createXLSX索引变更(fjnxSqlVoIndexsMaps,file);
		createXLSX变更表清单(fjnxTableVos,file);
	}
	
    private static void init() throws Exception{
    	// excel模板路径
		InputStream in = new FileInputStream(tempfile);
		// 读取excel模板
		 wb = new XSSFWorkbook(in);
    }
    
	/**
	 * 
	 * (2007 xlsx后缀 导出)
	 * 
	 * @param TODO
	 * @return void 返回类型
	 * @author xsw
	 * @2016-12-7上午10:44:30
	 */
	private static void createXLSX表结构变更(LinkedHashMap<FjnxTableVo, List<Column>>  fjnxSqlVos,File file) throws IOException {
	
		//step1. 读取了模板内所有sheet内容 表结构变更 
		XSSFSheet sheet = wb.getSheetAt(2);

		// 如果这行没有了，整个公式都不会有自动计算的效果的
		sheet.setForceFormulaRecalculation(true);

		// 在相应的单元格进行赋值
		//int rowsize = fjnxSqlVos.size();
		int start = 2;
		for(Entry<FjnxTableVo, List<Column>> it: fjnxSqlVos.entrySet()){
			FjnxTableVo fjnxTableVo = it.getKey();
			List<Column>  cmns = it.getValue();
		
			int i = 1;
            for(Column column:cmns){
            	  XSSFRow row = sheet.createRow(start);
            	  setCell(sheet,row,0,fjnxTableVo.getTable());
                  setCell(sheet,row,1,fjnxTableVo.getComment());
                  setCell(sheet,row,2,i+"");
                  setCell(sheet,row,3,column.getCode());
                  String name = StringUtils.isBlank(column.getName())?"":column.getName();
                  int len = name.indexOf(".");
                  if(len<0){
                	  len = name.indexOf("，");
                  }
                  if(len<0){
                	  len = name.indexOf(",");
                  }
                  setCell(sheet,row,4,StringUtils.substring(name, 0, len>0?(len):10));
                  setCell(sheet,row,5,column.getDataType());
                  setCell(sheet,row,6,column.getDataLen());
                  setCell(sheet,row,7,(column.getScale()!=null&&column.getScale()>0)?column.getScale()+"":"");
                  setCell(sheet,row,8,"TRUE".equalsIgnoreCase(column.getPrimary())?"是":" ");
                  setCell(sheet,row,9,"FALSE".equalsIgnoreCase(column.getMandatory())?"非空":"");
                  setCell(sheet,row,10, "");
                  setCell(sheet,row,11, "新增");
                  i++;
                  start++;
            }
          
			
		}
		 
		
		
		// 修改模板内容导出新模板
		FileOutputStream out = new FileOutputStream(file);
		wb.write(out);
		out.close();
	}

	
	/**
	 * 
	 * (2007 xlsx后缀 导出)
	 * 
	 * @param TODO
	 * @return void 返回类型
	 * @author xsw
	 * @2016-12-7上午10:44:30
	 */
	private static void createXLSX索引变更(LinkedHashMap<FjnxTableVo, List<Indexs>>  fjnxSqlVos,File file) throws IOException {
		// excel模板路径
		InputStream in = new FileInputStream(file);
		// 读取excel模板
		XSSFWorkbook wb = new XSSFWorkbook(in);
		//step1. 读取了模板内所有sheet内容 索引变更
		XSSFSheet sheet = wb.getSheetAt(4);

		// 如果这行没有了，整个公式都不会有自动计算的效果的
		sheet.setForceFormulaRecalculation(true);

		// 在相应的单元格进行赋值
		//int rowsize = fjnxSqlVos.size();
		int start = 2;
		for(Entry<FjnxTableVo, List<Indexs>> it: fjnxSqlVos.entrySet()){
			FjnxTableVo fjnxTableVo = it.getKey();
			List<Indexs>  cmns = it.getValue();
		 
            for(Indexs indexs:cmns){
            	  XSSFRow row = sheet.createRow(start);
            	  setCell(sheet,row,0,fjnxTableVo.getTable());
                  setCell(sheet,row,1,indexs.getType());
                  setCell(sheet,row,2,indexs.getColumnNames());
                  setCell(sheet,row,3,"新增");
               
                  start++;
            }
          
			
		}
		 
		
		
		// 修改模板内容导出新模板
		FileOutputStream out = new FileOutputStream(file);
		wb.write(out);
		out.close();
	}
	
	/**
	 * 
	 * (2007 xlsx后缀 导出)
	 * 
	 * @param TODO
	 * @return void 返回类型
	 * @author xsw
	 * @2016-12-7上午10:44:30
	 */
	private static void createXLSX变更表清单(List<FjnxTableVo>  fjnxSqlVos,File file) throws IOException {
		// excel模板路径
		InputStream in = new FileInputStream(file);
		// 读取excel模板
		XSSFWorkbook wb = new XSSFWorkbook(in);
		//step1. 读取了模板内所有sheet内容 索引变更
		XSSFSheet sheet = wb.getSheetAt(1);

		// 如果这行没有了，整个公式都不会有自动计算的效果的
		sheet.setForceFormulaRecalculation(true);

		// 在相应的单元格进行赋值
		//int rowsize = fjnxSqlVos.size();
		int start = 8;
		int i = 1;
		for(FjnxTableVo it: fjnxSqlVos){
			XSSFRow row = sheet.createRow(start);
			setCell(sheet,row,0,  "1");
			setCell(sheet,row,1,"CBS");
			setCell(sheet,row,4,"2");
			setCell(sheet,row,5,it.getTable());
      	    setCell(sheet,row,6,it.getComment());
      	    setCell(sheet,row,7,"新增");
	      	  setCell(sheet,row,9,"是");
	      	  setCell(sheet,row,10,"是");
	      	  setCell(sheet,row,11,"是");
	      	  setCell(sheet,row,12,"新增");
	      	 setCell(sheet,row,13,"否");
	      	 setCell(sheet,row,14,"否");
	      	 setCell(sheet,row,15,"月");
	      	setCell(sheet,row,16,"全量");
            i++;
            start++;
          
			
		}
		 
		
		
		// 修改模板内容导出新模板
		FileOutputStream out = new FileOutputStream(file);
		wb.write(out);
		out.close();
	}
	
	private static void setCell(XSSFSheet sheet,XSSFRow row ,int j,String value) {
		XSSFCell cell = row.createCell((short) j);
		cell.setCellValue(new XSSFRichTextString(value));
		
		
	}
	/***
     * 获取所有表名
     * @throws SQLException
     */
	private static List<FjnxTableVo> getFilterTableNames() throws Exception{
		List<FjnxTableVo> vos = new ArrayList<FjnxTableVo>(tables.size());
		 List<Column> mns = DBUtil.getAllTableNames();
		 System.out.println("tabledb size:"+mns.size());
		 System.out.println("tables size:"+tables.size());
		 
		 for(Column column:mns){
			 
			 if(tables.contains(column.getCode().toUpperCase())){
				 System.out.println("talbe:"+column.getCode());
				 FjnxTableVo vo = new FjnxTableVo(column.getCode().toUpperCase(),column.getName());
				 vos.add(vo);
			 }
			
		 }
		 return vos;
		 
	}
	

}
