package com.wft.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

public class UnionpayFileMd5 {
	
	/**
	 * 字符集
	 */
	public final static  String CHASET = "GBK";
	 
	/**
	 * 清分文件换行 每条记录末尾以回车（0x0d）换行符(0x0a)结束
	 */
	public final static String LINE = new String(new char[]{0x0d,0x0a});
	
 
    public static void main(String[] args) throws IOException {
		
		File file = new File("C:/Users/admin/Desktop/cleaningFiles_20181221/AONH18122101ZFPOSEAC");
		
		System.out.println(fileMd5("12345678ABCDEFGH",file).toUpperCase());
	}
	
	//md5 md5需要内容+秘钥
	public static String fileMd5(String key,File file) {
			String md5 = "";
			try {
				List<String> bodyList = FileUtils.readLines(file, CHASET);
				StringBuffer sb = new StringBuffer();
				int size = bodyList.size();
				int i = 0;
				for(String body:bodyList){
					i++;
					StringBuffer ssssb = new StringBuffer();
					if(i==size&&body.length()==32){
						System.out.println("md5:"+body);
					}else{
						ssssb.append(body+LINE);
						
					}
				
					sb.append(ssssb);
				}
				sb.append(key);
				md5 = DigestUtils.md5Hex(sb.toString().getBytes(CHASET));
			} catch (Exception e) {
				e.printStackTrace();
				 
				throw new RuntimeException(e);
			}
			
			return md5;
		}
}
