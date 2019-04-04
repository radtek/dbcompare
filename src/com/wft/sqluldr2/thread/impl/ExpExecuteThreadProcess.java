package com.wft.sqluldr2.thread.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.swiftpass.core.server.config.xml.SqluldrXmlConfig;
import cn.swiftpass.core.server.config.xml.XmlConfig;
import cn.swiftpass.core.server.sqluldr.SqluldrService;

import com.wft.sqluldr2.SpringContextHolder;
import com.wft.sqluldr2.conf.ConstantsConf;
import com.wft.sqluldr2.conf.ConstantsTools;
import com.wft.sqluldr2.logic.bean.Table;
import com.wft.sqluldr2.util.DbUtilOracle;
import com.wft.sqluldr2.util.LogLevel;
import com.wft.sqluldr2.util.PropertyUtil;

/**
 * 执行器线程
 *
 * @author Bob
 */
public class ExpExecuteThreadProcess extends AbstractExecuteThread {

    protected static final Logger logger = LoggerFactory.getLogger(ExpExecuteThreadProcess.class);
    
    private SqluldrService sqluldrService;
    
    // 表-字段配置文件内容Map,key:目标表名,value:对应关系内容List
    public static LinkedHashMap<String, ArrayList<String>> tableFieldMap;
    // TriggerMap,key:快照名,value:触发表
    public static LinkedHashMap<String, String[]> triggerMap;
    // 来源表Map,key:快照名,value:Table
    public static LinkedHashMap<String, Table> originalTablesMap;
    // 目标表Map,key:快照名,value:Table
    public static LinkedHashMap<String, Table> targetTablesMap;
    
    /**
     * 初始化变量
     */
    static {
    	tableFieldMap = new LinkedHashMap<String, ArrayList<String>>();
        triggerMap = new LinkedHashMap<String, String[]>();
        originalTablesMap = new LinkedHashMap<String, Table>();
        targetTablesMap = new LinkedHashMap<String, Table>();
    }
    
    public ExpExecuteThreadProcess () {
   
    	//this.setSqluldrService();
    	this.sqluldrService = SpringContextHolder.getBean(SqluldrService.class);
    }
    
    /**
     * 测试连接
     */
    public boolean testLink(XmlConfig config) {
    	logger.info(PropertyUtil.getProperty("ds.logic.testLinking"), LogLevel.INFO);
        boolean isLinkedPass = true;
        DbUtilOracle dbOracle = null;
        try {
        	//测试oracle数据连接
        	dbOracle = DbUtilOracle.getInstance(config);
            Connection connOracle = dbOracle.testConnection();
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
    
	@Override
    public void run() {
    	//ExpPanel.progressTotal.setMaximum(2);
        this.setName("ExecuteThread");
        //SqluldrConfig sqluldrConfig = ConstantsTools.CONFIGER.getConfig(SqluldrConfig.class, ConstantsConf.PATH_CONFIG_SQLULDR);
        SqluldrXmlConfig sqluldrConfig = ConstantsTools.CONFIGER.getConfig(SqluldrXmlConfig.class, ConstantsConf.PATH_CONFIG_SQLULDR);
        boolean isLinked = testLink(sqluldrConfig.getXmlConfig());
        //ExpPanel.progressTotal.setValue(1);
        if (!isLinked) {
        	String fail = String.valueOf((Long.parseLong(ConstantsTools.CONFIGER.getFailTime()) + 1));
          
        	logger.info(  "      系统异常！", "Sorry~{}" ,fail);
        }else {
        	try {
        		String destFilePath = sqluldrService.startSqluldr(sqluldrConfig.getXmlConfig().getDir(), sqluldrConfig);
        		logger.info("***************导出完成!!!!，文件路径: {}", destFilePath);
			} catch (Exception e) {
				logger.error("执行导出异常 ： ", e);
				logger.info(  "    执行导出异常！", "Sorry~" );
			}
        }
      
    }
    
	/*public void setSqluldrService() {
    	//初始化容器
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath*:config/application.xml"); 
        logger.info("容器初始化实例：{}", applicationContext.getBeansOfType(BaseService.class));
		this.sqluldrService = (SqluldrService) applicationContext.getBean(SqluldrService.class);;
	}*/
    
}
