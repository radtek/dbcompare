package com.wft.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static int BUFFER_SIZE = 2048;
	public static final int BUFFER = 1024;
	public static final String GZ = ".gz";

	public static void createDirectory(String outputDir, String subDir) {
		File file = new File(outputDir);
		if (!(subDir == null || subDir.trim().equals(""))) {// 子目录不为空
			file = new File(outputDir + File.separator + subDir);
		}
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static void zip(String zipFileName, List<File> files, String encoding, boolean deleteSrcFileFlag) {
		if (files == null) {
			return;
		}
		try {
			FileOutputStream fou = new FileOutputStream(zipFileName);
			ArchiveOutputStream archOuts = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, fou);
			if (archOuts instanceof ZipArchiveOutputStream) {
				ZipArchiveOutputStream zipOut = (ZipArchiveOutputStream) archOuts;
				for (int i = 0; i < files.size(); i++) {
					ZipArchiveEntry zipEntry = new ZipArchiveEntry(files.get(i), files.get(i).getName());
					zipOut.putArchiveEntry(zipEntry);
					zipOut.setEncoding(encoding);
					zipOut.write(FileUtils.readFileToByteArray(files.get(i)));
				}
				zipOut.closeArchiveEntry();
				zipOut.flush();
				zipOut.finish();
				IOUtils.closeQuietly(zipOut);
			}
			IOUtils.closeQuietly(fou);
			IOUtils.closeQuietly(archOuts);
			
			if (deleteSrcFileFlag) {
				for (int i = 0; i < files.size(); i++) {
					files.get(i).delete();
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public static List<String> unZip(File zipfile, String destDir) {
		if (StringUtils.isBlank(destDir)) {
			destDir = zipfile.getParent();
		}
		destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
		ZipArchiveInputStream is = null;
		List<String> fileNames = new ArrayList<String>();

		try {
			is = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipfile), BUFFER_SIZE));
			ZipArchiveEntry entry = null;
			while ((entry = is.getNextZipEntry()) != null) {
				fileNames.add(entry.getName());
				if (entry.isDirectory()) {
					File directory = new File(destDir, entry.getName());
					directory.mkdirs();
				} else {
					OutputStream os = null;
					try {
						os = new BufferedOutputStream(new FileOutputStream(new File(destDir, entry.getName())), BUFFER_SIZE);
						IOUtils.copy(is, os);
					} finally {
						IOUtils.closeQuietly(os);
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			IOUtils.closeQuietly(is);
		}

		return fileNames;
	}

	public static List<String> unZip(String zipfile, String destDir) {
		File zipFile = new File(zipfile);
		return unZip(zipFile, destDir);
	}
	 
	public static String getFileSign(String signFilePath) {
		String fileSign = null;
		FileInputStream fileInputStream = null;
	    InputStreamReader inputStreamReader = null;
	    BufferedReader bufferedReader = null;
		try {
			fileInputStream = new FileInputStream(signFilePath);
		    inputStreamReader = new InputStreamReader(fileInputStream);
		    bufferedReader = new BufferedReader(inputStreamReader);
		    fileSign = bufferedReader.readLine();
		} catch (Exception e) {
			logger.error("", e);
		} finally {
		    IOUtils.closeQuietly(bufferedReader);
		    IOUtils.closeQuietly(inputStreamReader);
		    IOUtils.closeQuietly(fileInputStream);
		}
	    return fileSign;
	}
	
	/**
	 * 删除文件夹
	 * @param path
	 * @return
	 */
	public static boolean deleteDir(String path) {
	    File dir = new File(path);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                File tmpFile = new File(dir, children[i]);
                String p = tmpFile.getPath();
                boolean success = deleteDir(p);
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
	
	public static String genDescribeFile(String describe,File outFile) {
        BufferedWriter bufferedWriter = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(outFile, false), "UTF-8");
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(describe);
        } catch (Exception e) {
            logger.error("生成描述文件异常", e);
        } finally {
            IOUtils.closeQuietly(bufferedWriter);
            IOUtils.closeQuietly(outputStreamWriter);
        }
        return outFile.getPath();
    }
	
	public static void write(String filepath, String fileName, byte[] data) throws Exception{
		FileOutputStream fos = null;
		try{
			File folder = new File(filepath);
			if (!folder.exists())	throw new Exception();
			File file = new File(folder, fileName);
			fos = new FileOutputStream(file);
			fos.write(data);
		} catch (IOException e){
			logger.error(e.getMessage());
		}
		finally{
			if (fos != null){
				try{
					fos.close();
				} catch (IOException e){
					logger.error(e.getMessage());
				}
			}
		}
		
	}
	
	public static byte[] read(String filepath, String filename) throws Exception{
		byte[] ret = null;
		File file = new File(new File(filepath), filename);
		if (!file.exists())	throw new Exception();
		long fileLen = file.length();
		ret = new byte[(int) fileLen];
		long numRead = 0;
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(file);
			numRead = fis.read(ret, 0, (int) fileLen);
		} catch (IOException e){
			logger.error(e.getMessage());
		}
		finally{
			if (fis != null){
				try{
					fis.close();
				} catch (IOException e){
					logger.error(e.getMessage());
				}
			}
		}
		if (numRead != fileLen){
			throw new Exception("读取文件错误");
		}
		return ret;
	}
	
	 
	public static void gZip(String zipFileName, File file, boolean deleteSrcFileFlag) {
		if (file == null) {
			return;
		}
		try {
			FileOutputStream fou = new FileOutputStream(zipFileName);
			GZIPOutputStream gos = new GZIPOutputStream(fou);  
			gos.write(FileUtils.readFileToByteArray(file));
			gos.finish();
			gos.flush();
			IOUtils.closeQuietly(fou);
			IOUtils.closeQuietly(gos);
			
			if (deleteSrcFileFlag) {
				file.delete();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public static void unGzip(File zipfile, String destDir) {
		if (StringUtils.isBlank(destDir)) {
			destDir = zipfile.getParent();
		}
		destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
		GZIPInputStream gis = null; 
		try {
			gis = new GZIPInputStream(new BufferedInputStream(new FileInputStream(zipfile), BUFFER));
			OutputStream os = null;
			try {
				os = new BufferedOutputStream(new FileOutputStream(new File(destDir)), BUFFER);
				IOUtils.copy(gis, os);
			} finally {
				IOUtils.closeQuietly(os);
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			IOUtils.closeQuietly(gis);
		}
	}
	 

	/**
	 * 获取文件大小 -1 表示获取失败
	 *
	 * @param file
	 * @return Long
	 * @throws
	 * @author tangbaodong
	 * @date 2016-07-21 16:44:11
	 **/
	public static Long getFileLength(File file) {
		FileChannel fc= null;
		try {
			if (file.exists() && file.isFile()){
				FileInputStream fis= new FileInputStream(file);
				fc = fis.getChannel();
				return fc.size();
			}else{
				logger.info("file doesn't exist or is not a file");
			}
		} catch (FileNotFoundException e) {
			logger.error("file doesn't exist or is not a file", e);
		} catch (IOException e) {
			logger.error("file get length error", e);
		} finally {
			if ( null != fc ){
				try {
					fc.close();
				} catch(IOException e){
					logger.error("fileChannel close error", e);
				}
			}
		}
		return -1l;
	}

	/**
	 *  获取文件大小描述
	 *
	 * @param fileLength 文件大小
	 * @return String 文件大小描述
	 * @throws
	 * @author tangbaodong
	 * @date 2016-08-19 14:40:09
	 **/
	public static String getFileLengthName(Long fileLength) {
		if ( null == fileLength ) {
			return null;
		}
		double kiloByte = fileLength/1024;
		if(kiloByte < 1) {
			return fileLength + "Byte(s)";
		}
		double megaByte = kiloByte/1024;
		if(megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
		}

		double gigaByte = megaByte/1024;
		if(gigaByte < 1) {
			BigDecimal result2  = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
		}

		double teraBytes = gigaByte/1024;
		if(teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
	}
	
	/** 
	 * 判断文件的编码格式 
	 * @param fileName :file 
	 * @return 文件编码格式 
	 * @throws Exception 
	 */  
	public static String codeString(String fileName) throws Exception{  
	     
		InputStream inputStream = new FileInputStream(fileName);    
		 byte[] head = new byte[3]; 
		 inputStream.read(head); 
	    String code = "";    
	    code = "gb2312";    
	    if (head[0] == -1 && head[1] == -2 )    
	              code = "UTF-16";    
	    if (head[0] == -2 && head[1] == -1 )    
	              code = "Unicode";    
	    if(head[0]==-17 && head[1]==-69 && head[2] ==-65)    
	              code = "UTF-8";       
	    System.out.println(code);  
	    return code;  
	}

	public static void main(String[] args) throws Exception {
 
		System.out.println(codeString("D:/公司资料/test/私有云通用sql/增量差异(升级使用)/私有云通用sql-20170510-0602增量差异/DDL/1.DDL合并.sql"));
		 System.out.println("文件编码:"
				    + new FileCharsetDetector().checkEncoding("D:/公司资料/test/私有云通用sql/增量差异(升级使用)/私有云通用sql-20170510-0602增量差异/DDL/1.DDL合并.sql"));
	}

}