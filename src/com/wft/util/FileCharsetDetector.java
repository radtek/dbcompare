package com.wft.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;

/**
 * @author admin
 *字符集探测
 */
public class FileCharsetDetector {
	/**
     * 字符集名称
     */
    private static String encoding;
    
    /**
     * 字符集是否已检测到
     */
    private   boolean found;
    
    private   nsDetector detector;
    
    private   nsICharsetDetectionObserver observer;

    /**
     * 适应语言枚举
     * @author robin
     *
     */
    enum Language{
        Japanese(1),
        Chinese(2),
        SimplifiedChinese(3),
        TraditionalChinese(4), 
        Korean(5), 
        DontKnow(6);
        
        private int hint;
        
        Language(int hint){
            this.hint = hint;
        }
        
        public int getHint(){
            return this.hint;
        }
    }
    
    /**
     * 传入一个文件(File)对象，检查文件编码
     * 
     * @param file
     *            File对象实例
     * @return 文件编码，若无，则返回null
     * @throws FileNotFoundException
     * @throws IOException
     */
    public   String checkEncoding(File file) throws FileNotFoundException,
            IOException {
        return checkEncoding(file, getNsdetector());
    }

    /**
     * 获取文件的编码
     * 
     * @param file
     *            File对象实例
     * @param language
     *            语言
     * @return 文件编码
     * @throws FileNotFoundException
     * @throws IOException
     */
    public   String checkEncoding(File file, Language lang)
            throws FileNotFoundException, IOException {
        return checkEncoding(file, new nsDetector(lang.getHint()));
    }

    /**
     * 获取文件的编码
     * 
     * @param path
     *            文件路径
     * @return 文件编码，eg：UTF-8,GBK,GB2312形式，若无，则返回null
     * @throws FileNotFoundException
     * @throws IOException
     */
    public   String checkEncoding(String path) throws FileNotFoundException,
            IOException {
        return checkEncoding(new File(path));
    }

    /**
     * 获取文件的编码
     * 
     * @param path
     *            文件路径
     * @param language
     *                 语言
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public   String checkEncoding(String path, Language lang)
            throws FileNotFoundException, IOException {
        return checkEncoding(new File(path), lang);
    }

    /**
     * 获取文件的编码
     * 
     * @param file
     * @param det
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    private   String checkEncoding(File file, nsDetector detector)
            throws FileNotFoundException, IOException {
        
    	try {
    		  detector.Init(getCharsetDetectionObserver());
    	        
    	        if (isAscii(file, detector)) {
    	            encoding = "ASCII";
    	            found = true;
    	        }

    	        if (!found) {
    	            String prob[] = detector.getProbableCharsets();
    	            if (prob.length > 0) {
    	                encoding = prob[0];
    	            } else {
    	                return null;
    	            }
    	        }
    	        
		} finally{
			 
		   
		}
      
        return encoding;
    }
    
    /**
     * 检查文件编码类型是否是ASCII型
     * @param file
     *             要检查编码的文件
     * @param detector
     * @return
     * @throws IOException
     */
    private   boolean isAscii(File file, nsDetector detector) throws IOException{
        BufferedInputStream input = null;
        try{
            input = new BufferedInputStream(new FileInputStream(file));
            
            byte[] buffer = new byte[1024];
            int hasRead;
            boolean done = false;
            boolean isAscii = true;

            while ((hasRead=input.read(buffer)) != -1) {
                if (isAscii)
                    isAscii = detector.isAscii(buffer, hasRead);
                if (!isAscii && !done)
                    done = detector.DoIt(buffer, hasRead, false);
            }
            
            return isAscii;
        }finally{
            detector.DataEnd();
            if(null!=input)input.close();
        }
    }
    
    /**
     * nsDetector单例创建
     * @return
     */
    private   nsDetector getNsdetector(){
        if(null == detector){
            detector = new nsDetector();
        }
        return detector;
    }
    
    /**
     * nsICharsetDetectionObserver 单例创建
     * @return
     */
    private   nsICharsetDetectionObserver getCharsetDetectionObserver(){
        if(null==observer){
            observer = new nsICharsetDetectionObserver() {
                public void Notify(String charset) {
                    found = true;
                    encoding = charset;
                    System.out.println("charset:"+charset);
                }
            };
        }
        return observer;
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
    	//20171106_ziyuqi_清分合并方案添加相关表.sql
    	File f = new File("D:/公司资料/test/私有云通用sql/增量差异(升级使用)/私有云通用sql-20171113-1213增量差异/DDL/");
    	for(File file:f.listFiles()){
    		System.out.println(file.getAbsolutePath());
    		String chart = new FileCharsetDetector().checkEncoding(file.getAbsolutePath());
    		System.out.println("chart:" + chart);
    	}
    	
	}
    
    
}
