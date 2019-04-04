package com.wft.sqluldr2.util;


import cn.swiftpass.core.server.config.xml.XmlConfig;

import com.wft.sqluldr2.db.AbstractDBService;
import com.wft.sqluldr2.logic.bean.DBVo;

/**
 * Oracle数据库工具，单例，持久连接
 *
 * @author Bob
 */
public class DbUtilOracle extends AbstractDBService{

    private static DbUtilOracle instance = null;
    
    private static XmlConfig config;
    
    /**
     * 私有的构造
     */
    private DbUtilOracle() {
        loadConfig();
    }
    
    public void loadConfig(){
    	try {
    		dbVo = new DBVo();
			dbVo.setType(config.getType());
			dbVo.setDBClassName(config.getClassName());
			dbVo.setDBServiceName(config.getName());
			dbVo.setPort(config.getPort());
			dbVo.setIp(config.getHost());
			dbVo.setDBUser(config.getDecodeUserName());
			dbVo.setDBPwd(config.getDecodePwd());
			Class.forName(dbVo.getDBClassName());
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
    } 

    /**
     * 获取实例，线程安全
     *
     * @return
     */
    public static synchronized DbUtilOracle getInstance(XmlConfig config) {
    	DbUtilOracle.config = config;
        if (instance == null) {
            instance = new DbUtilOracle();
        }else {
        	instance.loadConfig();
        }
        return instance;
    }
}
