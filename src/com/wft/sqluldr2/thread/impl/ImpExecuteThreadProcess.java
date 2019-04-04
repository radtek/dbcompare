package com.wft.sqluldr2.thread.impl;

import java.util.List;

import cn.swiftpass.core.server.config.xml.SqlldrXmlConfig;
import cn.swiftpass.core.server.sqlldr.SqlldrService;
import cn.swiftpass.core.server.vo.Result;

import com.wft.sqluldr2.SpringContextHolder;
import com.wft.sqluldr2.conf.ConstantsConf;
import com.wft.sqluldr2.conf.ConstantsTools;

public class ImpExecuteThreadProcess extends AbstractExecuteThread {
 
	private SqlldrService sqlldrService;
	
	public ImpExecuteThreadProcess () { 
		super();
	
		this.sqlldrService = SpringContextHolder.getBean(SqlldrService.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public void run() {
		SqlldrXmlConfig sqlldrConfig = ConstantsTools.CONFIGER.getConfig(SqlldrXmlConfig.class, ConstantsConf.PATH_CONFIG_SQLLDR);
		boolean isOk = super.testLink(sqlldrConfig.getXmlConfig());
		
		if (!isOk) {
			logger.info("连接数据库系统异常！");
			logger.info( "连接数据库系统异常！", "Sorry~");
			return;
		}
		String zipFilePath = "";//ImpPanel.fileDirValue.getText();
		logger.info("导入操作，需要解析的文件地址：{}", zipFilePath);
		
		List<Result> results = null;
		try {
			results = (List<Result>) sqlldrService.execSqlldr(zipFilePath, sqlldrConfig);
		} catch (Exception e) {
			logger.error("导入处理异常：", e);
			logger.info( "程序处理导入操作出现异常，请查看打印的日志信息！", "Sorry~");
		}
		logger.info(results.toString());
	}
	
	public void stopProcess () {
		sqlldrService.killProcess();
	}
	
}
