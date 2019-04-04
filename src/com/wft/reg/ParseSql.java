package com.wft.reg;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wft.exception.AppException;
import com.wft.util.CommonUtil;

/**
 * @author admin
 *解析sql
 *1.sql脚本使用utf-8格式
 *2.sql脚本分号结束表示一个sql语句
 *3.-开头表示注释或者#，跳过
 *4.最后一个sql语句必须有分号结束符
 */
public class ParseSql {

	private final static Logger log = Logger.getLogger(ParseSql.class);
	
	
	/**
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String[]  parseFromFile(String fileName) throws IOException {
		Set<String> sets = new LinkedHashSet<String>(); 
		File file = new File(fileName);
		StringBuilder sb = new StringBuilder();
		List<String> ls = FileUtils.readLines(file, CommonUtil.IN_CODE);
		for (String sql : ls) {
			String comment = sql.trim();
			if (StringUtils.isNotBlank(sql)) {
				if (comment.startsWith("--")||comment.startsWith("#")||comment.startsWith("/")) {//--开头表示注释或者#  /*，跳过
					//log.info("comment:" + comment);
					continue;
				}
				if (comment.endsWith(";")) {//分号结束表示一个sql语句
					log.info("sql:" + comment);
					int end = comment.lastIndexOf(";");
					String lastStr = comment.substring(0, end );
					sb.append(lastStr + " ");
					sets.add(sb.toString());
					sb = new StringBuilder();
				} else {
					sb.append(comment + " ");
				}
			}
		}
		if(StringUtils.isNotBlank(sb.toString())){//最后一个sql语句必须有分号结束符,否则抛异常
			throw new AppException("语法错误,没有添加分号:"+sb.toString());
			//sets.add(sb.toString());
		}
 
		log.info("sets:" + sets);
		return sets.toArray(new String[sets.size()]);
		 
	}
 
}
