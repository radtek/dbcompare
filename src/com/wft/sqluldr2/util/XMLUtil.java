package com.wft.sqluldr2.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 封装了XML转换成object，object转换成XML的代码
 * 
 * @author yunfeng.zhou
 * @date 2018-05-18
 */
public class XMLUtil {

	private static final Logger logger = LoggerFactory.getLogger(XMLUtil.class);
	
	/** 
     * 将对象直接转换成String类型的 XML输出 
     *  
     * @param obj 
     * @return 
     */  
    public static String convertToXml(Object obj) {  
        // 创建输出流  
        StringWriter sw = new StringWriter();  
        try {  
            // 利用jdk中自带的转换类实现  
            JAXBContext context = JAXBContext.newInstance(obj.getClass());  
            Marshaller marshaller = context.createMarshaller();  
            // 格式化xml输出的格式  
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); 
            // 将对象转换成输出流形式的xml  
            marshaller.marshal(obj, sw);  
        } catch (JAXBException e) {  
            logger.error("JAXBException: {}", e);
        }  
        return sw.toString();  
    }
    
    /** 
     * 将对象根据路径转换成xml文件 
     *  
     * @param obj 
     * @param path 
     * @return 
     */  
    public static void convertToXml(Object obj, String path) {  
        try {  
            // 利用jdk中自带的转换类实现  
            JAXBContext context = JAXBContext.newInstance(obj.getClass());  
            Marshaller marshaller = context.createMarshaller();  
            // 格式化xml输出的格式  
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // 将对象转换成输出流形式的xml  
            // 创建输出流  
            FileWriter fw = null;  
            try {  
                fw = new FileWriter(path);  
            } catch (IOException e) {  
                logger.error("IOException: {}", e);
            }  
            marshaller.marshal(obj, fw);  
        } catch (JAXBException e) {  
            logger.error("JAXBException: {}", e);
        }  
    }
    
    /** 
     * 将String类型的xml转换成对象 
     */
    @SuppressWarnings("rawtypes")
	public static Object convertXmlStrToObject(Class clazz, String xmlStr) {  
        Object xmlObject = null;  
        try {  
            JAXBContext context = JAXBContext.newInstance(clazz);  
            // 进行将Xml转成对象的核心接口  
            Unmarshaller unmarshaller = context.createUnmarshaller();  
            StringReader sr = new StringReader(xmlStr);  
            xmlObject = unmarshaller.unmarshal(sr);  
        } catch (JAXBException e) {  
            logger.error("JAXBException: {}", e);
        }  
        return xmlObject;  
    }
    
    /** 
     * 将file类型的xml转换成对象 
     */  
    @SuppressWarnings("rawtypes")
	public static Object convertXmlFileToObject(Class clazz, String xmlPath) {  
        Object xmlObject = null;  
        try {  
            JAXBContext context = JAXBContext.newInstance(clazz);  
            Unmarshaller unmarshaller = context.createUnmarshaller();  
            FileReader fr = null;  
            try {  
                fr = new FileReader(xmlPath);  
            } catch (FileNotFoundException e) {  
                logger.error("FileNotFoundException: {}", e);
            }  
            xmlObject = unmarshaller.unmarshal(fr);  
        } catch (JAXBException e) {  
            logger.error("JAXBException: {}", e);
        }  
        return xmlObject;  
    }
    
    public static void main(String[] args){
    	//SqluldrConfig sqluldrConfig = (SqluldrConfig) convertXmlFileToObject(SqluldrConfig.class, ConstantsUI.CURRENT_DIR + File.separator + "config" + File.separator + "sqluldr-config.xml");
    	//System.out.println("导出配置:" + sqluldrConfig.toString());
    	
    	//SqlldrConfig sqlldrConfig = (SqlldrConfig) convertXmlFileToObject(SqlldrConfig.class, ConstantsUI.CURRENT_DIR + File.separator + "config" + File.separator + "sqlldr-config.xml");
    	//System.out.println("导入配置:" + sqlldrConfig.toString());
    	
    	//SqluldrConfig sqluldrConfig = ConstantsTools.CONFIGER.getConfig(SqluldrConfig.class, ConstantsUI.CURRENT_DIR + File.separator + "config" + File.separator + "sqluldr-config.xml");
    	//System.out.println("导出配置:" + sqluldrConfig.toString());
    }
}
