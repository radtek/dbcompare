package com.wft.sqluldr2;

import java.util.Date;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

/**
 * @author admin
 * 异步导出库数据
 */
public class ExecuteExpRunnable implements Callable<ExpTaskResultVo> {
	private final static Logger logger = Logger.getLogger(ExpSQL.class);
	private String sql;
	
	public ExecuteExpRunnable(String sql) {
		this.sql = sql;
	}
	
	@Override
	public ExpTaskResultVo call()  {
		ExpTaskResultVo expTaskResultVo = new ExpTaskResultVo();
		Date beginTime = new Date();
		try {
	        Process proc = Runtime.getRuntime().exec(sql);
	        // 打印程序输出
	        ProcessOutput.readProcessOutput(proc);
	        // 等待程序执行结束并输出状态
	        int exitCode = proc.waitFor();
	   
	        if (exitCode == 0) {
	            logger.info(String.format("程序执行结束并输出状态:{%s}-成功！", exitCode));
	            expTaskResultVo.setExit(false);
	        } else {
	            logger.error(String.format("程序执行结束并输出状态-失败:【{%s-%s}】" , exitCode,SqlUldrUitl.sqluldrerrorMap.get(exitCode)));
	            expTaskResultVo.setExit(true);
	            expTaskResultVo.setExtResultDesc(String.format("程序执行结束并输出状态-失败:【{%s-%s}】" , exitCode,SqlUldrUitl.sqluldrerrorMap.get(exitCode)));
	        }
		} catch (Exception e) {
			e.printStackTrace();
			expTaskResultVo.setExtResultDesc(e.getMessage());
			expTaskResultVo.setExit(true);
		}
        Date endTime = new Date();
        long interval = (endTime.getTime() - beginTime.getTime())/1000;
        logger.info(String.format("执行结束，exec  花费的时间{%s}s", interval));
		return expTaskResultVo;
	}

}
