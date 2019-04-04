package com.wft.wxalarm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https请求工具
 * @author lisheng
 * @date 2017-08-15
 *
 */
public class HttpsRequest {

	private static final Logger logger = LoggerFactory.getLogger(HttpsRequest.class);
	
	/** 
	 * 发送请求. 
	 * @param httpsUrl 
	 *            请求的地址 
	 * @param dataStr 
	 *            请求的数据 
	 * @param charCode
	 * 			  字符编码
	 * @return 行方返回的报文
	 */  
	public static String post(String httpsUrl, String dataStr, String charCode) throws Exception{  
		logger.info("httpsUrl={}, dataStr={}, charCode={}", httpsUrl, dataStr, charCode);
		HttpsURLConnection urlCon = null; 
		StringBuilder sb = new StringBuilder();
		try {  
			byte[] datas = dataStr.getBytes(charCode);
			urlCon = (HttpsURLConnection) (new URL(httpsUrl)).openConnection();
			urlCon.setDoInput(true);  
			urlCon.setDoOutput(true); 
			urlCon.setUseCaches(false); 
			urlCon.setRequestProperty("Content-Length", String.valueOf(datas.length));
			urlCon.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			//设置为gbk可以解决服务器接收时读取的数据中文乱码问题  
			urlCon.getOutputStream().write(datas);  
			urlCon.getOutputStream().flush();  
			urlCon.getOutputStream().close();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), charCode));  
			String line;  
			while ((line = in.readLine()) != null) { 
				logger.debug("post返回的信息：" + line);
				sb.append(line);
			}  
		} catch (MalformedURLException e) {  
			logger.error("MalformedURLException, ex:\n"+ e.getMessage());
			throw e;
		} catch (IOException e) {  
			logger.error("IOException, ex:\n"+ e.getMessage());
			throw e;
		} catch (Exception e) {  
			logger.error("Exception e, ex:\n"+ e.getMessage());
			throw e;
		}  
		logger.info("post返回的完整信息：\n"+ sb.toString());
		return sb.toString();
	}  
	
	/** 
	 * 发送请求. 
	 * @param httpsUrl 
	 *            请求的地址 
	 * @param dataStr 
	 *            请求的数据 
	 * @param charCode
	 * 			  字符编码
	 * @return 行方返回的报文
	 */  
	public static String get(String httpsUrl, String charCode) throws Exception{  
		logger.info("httpsUrl={}, charCode={}", httpsUrl, charCode);
		HttpsURLConnection urlCon = null; 
		StringBuilder sb = new StringBuilder();
		try {  
			urlCon = (HttpsURLConnection) (new URL(httpsUrl)).openConnection();
			urlCon.setDoInput(true);  
			urlCon.setDoOutput(true); 
			urlCon.setUseCaches(false); 
			//urlCon.setRequestProperty("Content-Length", String.valueOf(datas.length));
			urlCon.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			//设置为gbk可以解决服务器接收时读取的数据中文乱码问题  
			//urlCon.getOutputStream().write(datas);  
			urlCon.getOutputStream().flush();  
			urlCon.getOutputStream().close();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), charCode));  
			String line;  
			while ((line = in.readLine()) != null) { 
				sb.append(line);
			}  
		} catch (MalformedURLException e) {  
			logger.error("MalformedURLException, ex:\n"+ e.getMessage());
			throw e;
		} catch (IOException e) {  
			logger.error("IOException, ex:\n"+ e.getMessage());
			throw e;
		} catch (Exception e) {  
			logger.error("Exception e, ex:\n"+ e.getMessage());
			throw e;
		}  
		logger.info("get返回的完整信息：\n"+ sb.toString());
		return sb.toString();
	}
}