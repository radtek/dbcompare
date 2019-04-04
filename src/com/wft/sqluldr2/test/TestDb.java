package com.wft.sqluldr2.test;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.swiftpass.core.server.config.xml.SqluldrXmlConfig;
import cn.swiftpass.core.server.config.xml.XmlConfig;

import com.wft.sqluldr2.conf.ConstantsConf;
import com.wft.sqluldr2.conf.ConstantsTools;
import com.wft.sqluldr2.thread.impl.ExpExecuteThreadProcess;
import com.wft.sqluldr2.util.DbUtilOracle;
import com.wft.sqluldr2.util.LogLevel;
import com.wft.sqluldr2.util.PropertyUtil;

public class TestDb {
	protected static final Logger logger = LoggerFactory.getLogger(ExpExecuteThreadProcess.class);
	
	
	public static void main(String[] args) {
		  SqluldrXmlConfig sqluldrConfig = ConstantsTools.CONFIGER.getConfig(SqluldrXmlConfig.class, ConstantsConf.PATH_CONFIG_SQLULDR);
	        boolean isLinked = testLink(sqluldrConfig.getXmlConfig());
	        System.out.println(isLinked);
	}
	
	/**
     * 测试连接
     */
	protected static boolean testLink(XmlConfig config) {
		System.out.println("config:"+config);
		logger.info(PropertyUtil.getProperty("ds.logic.testLinking"), LogLevel.INFO);
        //ExpPanel.progressCurrent.setMaximum(2);
        //ExpPanel.progressCurrent.setValue(0);
        boolean isLinkedPass = true;
        DbUtilOracle dbOracle = null;
        try {
        	//测试oracle数据连接
        	dbOracle = DbUtilOracle.getInstance(config);
            Connection connOracle = dbOracle.testConnection();
            //ExpPanel.progressCurrent.setValue(1);
            if (connOracle == null) {
                isLinkedPass = false;
                logger.info("Connection Oracle " + PropertyUtil.getProperty("ds.logic.testLinkFail"), LogLevel.ERROR);
            }
            logger.info(PropertyUtil.getProperty("ds.logic.testLinkFinish"), LogLevel.INFO);

        } catch (Exception e) {
        	logger.info(PropertyUtil.getProperty("ds.logic.testLinkFail") + e.toString(), LogLevel.ERROR);
            isLinkedPass = false;
        } finally {
        	dbOracle.close();
        }
        return isLinkedPass;
    }
	
}
