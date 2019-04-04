package com.wft.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

import com.wft.util.FileCharsetDetector;

public class UtfScan {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

		Iterator<File> it = FileUtils.iterateFiles(new File("D:\\公司资料\\test\\民生贵阳\\20170825_民生贵阳sql"), new String[]{"sql"}, true);
		while(it.hasNext()){
			File tempFile = it.next();
			System.out.println(tempFile.getAbsolutePath());
			String chart = new FileCharsetDetector().checkEncoding(tempFile);
			System.out.println("chart:" + chart);
			if("UTF-8".equalsIgnoreCase(chart)){
				System.out.println("文件编码:是UTF-8:" + tempFile.getAbsolutePath());
			
			}
		}
	 
		
	}

}
