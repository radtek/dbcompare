package com.wft.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author admin
 * 用户空间替换
 */
public class ReplaceSchema {
 
	public static void main(String[] args) throws Exception {
		//mergeSql(SCHEMAS_DDL, "ddl");
		//mergeSql(SCHEMAS_DML, "dml");
	}
	
	public static File mergeSql(String schemasType, String sqlType,String targetDir,List<File> files) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put(schemasType, schemasType);
		return mergeSql(map, sqlType, targetDir, files);
		
	}
	
	private static File mergeSql(Map<String, String> schemasType, String sqlType,String targetDir,List<File> files) throws Exception {
		File file = null;
		BufferedWriter out = null;
		try {
				for (String key : schemasType.keySet()) {
					File target = new File(targetDir + "/" + key);
					if (target.exists()) {
						target.delete();
					} else {
						target.mkdirs();
					}
					String sqlName = "ddl".equalsIgnoreCase(sqlType) ? "01.ddl" : "02.dml";
//					target = new File(TARGET_DIRECTORY + "/" + key, schemasType.get(key) + ".sql");
					target = new File(targetDir + "/" + key, sqlName + ".sql");
					if (target.exists()) {
						target.delete();
					} 
					
					out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target, true),CommonUtil.OUT_CODE));
					if ("dml".equals(sqlType)) {
						out.write("set define off;");
						out.write("\n");
					}
					out.flush();
					for (File f : files) {
						mergeFile(target, f, key);
					}
					if ("dml".equals(sqlType)) {
						out.write("commit;");
						out.write("\n");
					}
					out.write("exit;");
					out.write("\n");
					out.flush();
					
					file = target;
				}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return file;
	}

	private static void mergeFile(File target, File child, String schema) throws Exception {
		BufferedWriter out = null;
		BufferedReader reader = null;
		String v = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target, true), CommonUtil.OUT_CODE));
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(child), CommonUtil.IN_CODE));
			v = reader.readLine();
			out.write("--"+child.getName());
			out.write("\n");
			while (v != null) {
				out.write(v.replaceAll("zhifu", schema).replaceAll("zhifu", schema));
				out.write("\n");
				v = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}