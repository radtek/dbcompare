package com.wft.util.security;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DES安全编码组件
 * 
 * <pre>
 * 支持 DES、DESede(TripleDES,就是3DES)、AES、Blowfish、RC2、RC4(ARCFOUR) 
 * DES                  key size must be equal to 56 
 * DESede(TripleDES)    key size must be equal to 112 or 168 
 * AES                  key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available 
 * Blowfish             key size must be multiple of 8, and can only range from 32 to 448 (inclusive) 
 * RC2                  key size must be between 40 and 1024 bits 
 * RC4(ARCFOUR)         key size must be between 40 and 1024 bits 
 * 具体内容 需要关注 JDK Document http://.../docs/technotes/guides/security/SunProviders.html
 * </pre>
 * 
 * @author 梁栋
 * @version 1.0
 * @since 1.0
 */
public abstract class AESCoder extends Coder {
	private static final Logger logger = LoggerFactory.getLogger(AESCoder.class);
	
	/**
	 * ALGORITHM 算法 <br>
	 * 可替换为以下任意一种算法，同时key值的size相应改变。
	 * 
	 * <pre>
	 * DES                  key size must be equal to 56 
	 * DESede(TripleDES)    key size must be equal to 112 or 168 
	 * AES                  key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available 
	 * Blowfish             key size must be multiple of 8, and can only range from 32 to 448 (inclusive) 
	 * RC2                  key size must be between 40 and 1024 bits 
	 * RC4(ARCFOUR)         key size must be between 40 and 1024 bits
	 * </pre>
	 * 
	 * 在Key toKey(byte[] key)方法中使用下述代码
	 * <code>SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);</code> 替换
	 * <code> 
	 * DESKeySpec dks = new DESKeySpec(key); 
	 * SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM); 
	 * SecretKey secretKey = keyFactory.generateSecret(dks); 
	 * </code>
	 */
	public static final String ALGORITHM = "AES";

	/**
	 * 转换密钥<br>
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception {
		/*
		 * DESKeySpec dks = new DESKeySpec(key); SecretKeyFactory keyFactory =
		 * SecretKeyFactory.getInstance(ALGORITHM); SecretKey secretKey =
		 * keyFactory.generateSecret(dks);
		 */

		// 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
		SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);

		return secretKey;
	}

	/**
	 * 解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, String key) throws Exception {
		Key k = toKey(decryptBASE64(key));

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);

		return cipher.doFinal(data);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, String key) throws Exception {
		Key k = toKey(decryptBASE64(key));
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);

		return cipher.doFinal(data);
	}

	/**
	 * 把文件srcFile加密后存储为destFile
	 * 
	 * @param srcFile
	 * @param destFile
	 */
	public static void encryptFile(String srcFile, String destFile, String key) throws Exception {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		try {
			Key k = toKey(decryptBASE64(key));
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, k);
			
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(destFile);
			
			byte[] b = new byte[2048];
			int num = 0;
			while ((num = fis.read(b)) != -1) {
				fos.write(cipher.doFinal(b, 0, num));
			}
		} catch (Exception e) {
			logger.error("encryptFile exception:" + e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(fos);
			IOUtils.closeQuietly(fis);
		}
	}



	/**
	 * 把文件srcFile解密后存储为destFile
	 * 
	 * @param srcFile
	 * @param destFile
	 * @param key
	 * @throws Exception
	 */
	public static void decryptFile(String srcFile, String destFile, String key) throws Exception {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		try {
			Key k = toKey(decryptBASE64(key));
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, k);
			
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(destFile);
			
			byte[] b = new byte[2064];
			int num = 0;
			while ((num = fis.read(b)) != -1) {
				fos.write(cipher.doFinal(b, 0, num));
			}
		} catch (Exception e) {
			logger.error("decryptFile exception:" + e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(fos);
			IOUtils.closeQuietly(fis);
		}
	}

	/**
	 * 生成密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String initKey() throws Exception {
		return initKey(null);
	}

	/**
	 * 生成密钥
	 * 
	 * @param seed
	 * @return
	 * @throws Exception
	 */
	public static String initKey(String seed) throws Exception {
		SecureRandom secureRandom = null;

		if (seed != null) {
			secureRandom = new SecureRandom(decryptBASE64(seed));
		} else {
			secureRandom = new SecureRandom();
		}

		KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
		kg.init(secureRandom);

		SecretKey secretKey = kg.generateKey();

		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * 加密byte，加密前需指定解密密钥和加密数据的编码格式（受理机构会解密此数据，请勿更改）
	 * @param rawKeyData
	 *            加密密钥
	 * @param source
	 *            　需要加密的byte
	 */
	public static byte[] aesEncrypt(byte[] source, byte rawKeyData[])
			throws GeneralSecurityException {
		// 处理密钥
		SecretKeySpec key = new SecretKeySpec(rawKeyData, "AES");
		// 加密
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(source);
	}

	 /**
	 * 解密byte数据，需指定编码格式（解密受理机构返回的加密数据，请勿更改）
	 * @param rawKeyData 解密密钥
	 * @param source　需解密的byte
	 */
	public static byte[] aesDecrypt(byte[] data, byte rawKeyData[])
			throws GeneralSecurityException {
		// 处理密钥
		SecretKeySpec key = new SecretKeySpec(rawKeyData, "AES");
		// 解密
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(data);
	}


	 
}