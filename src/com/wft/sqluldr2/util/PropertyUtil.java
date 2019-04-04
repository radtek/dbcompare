package com.wft.sqluldr2.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wft.sqluldr2.conf.ConstantsConf;

/**
 * Created by zhouy on 2017/2/27.
 */
public class PropertyUtil {

	private static Logger logger = LoggerFactory.getLogger(PropertyUtil.class.getName());;
	
    /**
     * 获取property
     * @param key
     * @return
     */
    public static String getProperty(String key){
        Properties pps = new Properties();
        InputStream input = null;
        try {
        	input = new BufferedInputStream (new FileInputStream(ConstantsConf.PATH_CONFIG_PRO_LAN));
            pps.load(input);
            String value = pps.getProperty(key);
            return value;
        }catch (IOException e) {
            logger.error("异常：", e);
            return null;
        } finally {
        	close(input);
        }
    }
    
    public static String[] getPropertys(String ... keys) {
    	String[] values = {};
    	if (null == keys || keys.length == 0) return values;
    	List<String> list = new ArrayList<String>(keys.length);
    	InputStream input = null;
    	try {
    		Properties pps = new Properties();
    		input = new BufferedInputStream (new FileInputStream(ConstantsConf.PATH_CONFIG_PRO_LAN));
    		pps.load(input);
    		for (String key : keys) {
    			String value = pps.getProperty(key);
    			list.add(value);
        	}
		} catch (Exception e) {
			logger.error("异常：", e);
		} finally {
			close(input);
		}
    	return list.toArray(values);
    }
    
    public static void close(InputStream input){
    	IOUtils.closeQuietly(input);
    }
    
}
