package com.wft.util.security;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yunfeng.zhou
 * 目的：用于银联数据加解密敏感信息（数据库账户密码、SFTP、FTP账户）
 */
public class AESCoderNew {
	
	private static final Logger logger = LoggerFactory.getLogger(AESCoderNew.class);
	
	private static String defaultKey = "E8NLfxcUyFMKt22Rn5k1PQ==";
	
	/**
	 * 加密
	 * @return
	 */
	public static String encrypt(String str, String key){
		logger.debug("加密信息：{}", str);
		String encryptData = null;
		
		key = StringUtils.isBlank(key) ? defaultKey : key;
		if (StringUtils.isBlank(str)) return encryptData;
		
		//加密
		try {
			byte[] data = AESCoder.encrypt(str.getBytes(), key);
			encryptData = AESCoder.encryptBASE64(data);
			logger.info("*******密文 encryptData: {}*********", encryptData);
		} catch (Exception e) {
			logger.error("*******加密失败：", e);
		}
		return encryptData;
	}
	
	/**
	 * 解密
	 * @return
	 */
	public static String decrypt(String str, String key){
		logger.debug("解密信息：{}", str);
		String decryptData = null;
		
		key = StringUtils.isBlank(key) ? defaultKey : key;
		if (StringUtils.isBlank(str)) return decryptData;
		
		//解密
		try {
			byte[] data = AESCoder.decrypt(AESCoder.decryptBASE64(str), key);
			decryptData = new String(data);
			logger.info("*******明文 decryptData: {}*********", decryptData);
		} catch (Exception e) {
			logger.error("*******解密异常：{}", e);
		}
		
		return decryptData;
	}
	
	
	public static void main(String[] args) throws Exception{
		//密钥
		String key= "E8NLfxcUyFMKt22Rn5k1PQ==";
		
		//明文
		String user = "weixin03";
		String pwd = "szsz654321";
		System.out.println("*******明文*********");
		System.out.println("*******user:"+user+"、pwd:"+pwd+"*********");
		System.out.println("*******明文*********\n");
		
		byte[] userData = user.getBytes();
		byte[] pwdData = pwd.getBytes();
		
		//加密
		userData = AESCoder.encrypt(userData, key);
		pwdData  = AESCoder.encrypt(pwdData, key);
		String encryptUser = AESCoder.encryptBASE64(userData);
		String encryptPwd = AESCoder.encryptBASE64(pwdData);
		System.out.println("*******密文*********");
		System.out.println("*******encryptUser:"+encryptUser+"、encryptPwd:"+encryptPwd+"*********");
		System.out.println("*******密文*********\n");
		
		//解密
		byte[] decryptUser = AESCoder.decrypt(AESCoder.decryptBASE64(encryptUser), key);
		byte[] decryptPwd = AESCoder.decrypt(AESCoder.decryptBASE64(encryptPwd), key);
		System.out.println("*******解密*********");
		System.out.println("*******decryptUser:"+new String(decryptUser)+"、decryptPwd:"+new String(decryptPwd)+"*********");
		System.out.println("*******解密*********\n");
	}
}
