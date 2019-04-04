package com.wft.appconf.service.poi;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.wft.exception.AppException;

/**
 * @author admin
 * excle
  
 */
public class ExcelReadsUitls {

	 
	public static void main(String[] args) throws Exception {
		System.out.println("start");
	 
		String filePath =  "F:/公司重要文件/部署war包配置文件一键生成/project-list.xlsx"; 
		
		ExcelReadsUitls.readXlsx(new File(filePath));
		 
		System.out.println("ok");
	}

	 
 
	/**
	 * @param file
	 * @return Map<String,List<Map<String,String>>> key-sheetname 
	 * @throws Exception
	 */
	public static Map<String,List<Map<String,String>>> readXlsx(File file) throws Exception {
	 
		Map<String,List<Map<String,String>>> map = new LinkedHashMap<String, List<Map<String,String>>>();
		Workbook xssfWorkbook = WorkbookFactory.create(file);
		 
	 
		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			Sheet  xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			String sheetName = xssfSheet.getSheetName();
			if(!ConfConstants.EXCLE_SHEET_NAME_MAP.containsKey(sheetName)){
				throw new AppException("模板不正确...");
			}
			List<Map<String,String>> results = null;
			results = map.get(sheetName);
			if(results==null){
				results = new ArrayList<Map<String,String>>();
			}
			map.put(sheetName, results);
			
			String firstCol = null;
			String secondCol = null;
			// 循环行Row，计算字段开始行号  和  索引开始行号
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				Row xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow == null) {
					continue;
				}
				//行数据
				Map<String,String> valmap = new LinkedHashMap<String, String>();
				results.add(valmap);
				
				// 循环列Cell
				for (int cellNum = 0; cellNum <= xssfRow.getLastCellNum(); cellNum++) {
					Cell xssfCell = xssfRow.getCell(cellNum);
					if (xssfCell == null) {
						continue;
					}
					String value = getValue(xssfCell);
				    if(cellNum==0){//处理第一列
				    	if(StringUtils.isNotEmpty(value)){
				    		firstCol = value;
				    	}else{
				    		value = firstCol;
				    	}
				    }else if(cellNum==1){//处理第二列
				    	if(StringUtils.isNotEmpty(value)){
				    		secondCol = value;
				    	}else{
				    		value = secondCol;
				    	}
				    }else{//处理其他
				    	
				    }
				    if(cellNum==3){//处理第3列 服务名称 
				    	 
				    	if(!ConfConstants.TOMCAT_MAP.containsValue(value.trim())){
				    		
							throw new AppException("模板不正确..."+value);
						}
				    }
				    
				    valmap.put(ConfConstants.EXCLE_COL_MAP.get(cellNum+""), value.trim());
					
					 
				}//end cell
			}// end row
			 
		}//end sheet
		 
		System.out.println(map);
		return map;
		
	}
	
 
	
	private static String getValue(Cell xssfCell) {
		xssfCell.setCellType(Cell.CELL_TYPE_STRING);
		return String.valueOf(xssfCell.getStringCellValue()); 
	}
	
	 
}
