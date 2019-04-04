package com.wft.monitor;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.wft.model.MybasicBank;
import com.wft.service.MyBasicBankService;
import com.wft.util.CommonUtil;

/**
 * @author admin
 * 定时任 
 */
 
@Component
public class Monitorjob implements Job {
	
	private final static Logger log = Logger.getLogger(Monitorjob.class);
	
	ExecutorService executorService  =  Executors.newFixedThreadPool(50);
	
	 //把要执行的操作，写在execute方法中  
	 @Override  
    public void execute(JobExecutionContext content) throws JobExecutionException {  
		
		 log.info("定时Quartz"+new Date()); 
        JobDataMap dataMap = content.getJobDetail().getJobDataMap();  
        
        final MonitorEmailReport monitorEmailReport = (MonitorEmailReport) dataMap.get("monitorEmailReport");
        final MyBasicBankService myBasicBankService = (MyBasicBankService) dataMap.get("myBasicBankService");
        
        final List<MybasicBank> mybasicBanks = myBasicBankService.findCHeck();
        if(CollectionUtils.isNotEmpty(mybasicBanks)){
        	executorService.execute(new Runnable() {
    			@Override
    			public void run() {
    				for(MybasicBank mybasicBank:mybasicBanks){
    					try {
    						int issend = mybasicBank.getIssend();
    						if(issend!=CommonUtil.EABLE){
    							log.info("issend:"+issend+" issend!=1配置无需发送邮件{}"+mybasicBank.getRealName());
    							continue;
    						}
    						 //异步监控
    						executorService.submit(new WebSiteMonitor(myBasicBankService,mybasicBank,monitorEmailReport));
    						
    					} catch (Exception e) {
    						 continue;
    					}
    					
    					
    				}
    				
    			}
    		});
    		 
        }
        
    }  
	 
	 
	 @PreDestroy
	 public void destoryResource() {
		 executorService.shutdown(); // Disable new tasks from being submitted
			try {
				// Wait a while for existing tasks to terminate
				if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
					executorService.shutdownNow(); // Cancel currently executing tasks
					// Wait a while for tasks to respond to being cancelled
					if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
						 
					}
				}
			} catch (InterruptedException ie) {
				// (Re-)Cancel if current thread also interrupted
				executorService.shutdownNow();
				// Preserve interrupt status
				Thread.currentThread().interrupt();
			}
		}
}
