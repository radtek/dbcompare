package com.wft.util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
 

/**
 * @author zzy
 * 获取文件md5
 */
public class FileMD5 {

	public static String getMd5ByFile(File file) {
		try {
			 FileInputStream fis= new FileInputStream(file);    
		      String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));    
		     IOUtils.closeQuietly(fis);
		     
		     return md5;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	 
	
	public static void main(String[] args) {
		System.out.println(getMd5ByFile((new File("C:/Users/admin/Desktop/20170307001.txt"))));
	}
}
