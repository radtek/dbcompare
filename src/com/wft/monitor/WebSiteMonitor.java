package com.wft.monitor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wft.model.MybasicBank;
import com.wft.service.MyBasicBankService;

/**
 * @author admin
 * 私有云网站监控
 * 
 */
public class WebSiteMonitor implements Callable<Boolean> {

	private final static Logger log = Logger.getLogger(WebSiteMonitor.class);
	private MybasicBank mybasicBank;
	private MonitorReport monitorReport;
	private  MyBasicBankService myBasicBankService;
	
	public WebSiteMonitor( MyBasicBankService myBasicBankService,MybasicBank mybasicBank, MonitorReport monitorReport) {
		this.myBasicBankService = myBasicBankService;
		this.mybasicBank = mybasicBank;
		this.monitorReport = monitorReport;
	}


	/**
	 * @param weburl
	 * https://snccb.swiftpass.cn/
	 *  curl https://snccb.swiftpass.cn/
	 *  正常返回 {"appCode":"APP_PAY_CLOUT","logoImgUrl":"/images/logo_snccb.png","downloadUrl":"https://spay.swiftpass.cn/v3/spay/","orgName":"遂宁银行"}
	 *  不正常返回{"success":false,"errorCode":"sys.error.9999","msg":"系统异常","status":0}
	 * @return
	 */ 
	public  boolean monitor(){
		boolean ok = false;
		String result = "";
		 try {
			URL appurl = new URL(mybasicBank.getWebSite());
			appurl = null;
		    ok = false;
			result = ShellUtils.doCurl(mybasicBank.getWebSite());
			//log.info("monitor:"+result);
			if(StringUtils.isNotBlank(result)){
				if(result.contains("APP_PAY")){//访问正常
					ok = true;
				}
				 
			}else{
				//如果没有返回信息 默认也为true防止误判 发送邮件
				log.info("如果没有返回信息 默认也为true防止误判 发送邮件:"+result);
				ok = true;
			}
			
		} catch (MalformedURLException e) {
			 
			e.printStackTrace();
		}
		
		if(!ok){
			monitorReport.report(mybasicBank,"【"+mybasicBank.getWebSite()+"】 "+result);
		}
		return ok;
	}


	@Override
	public Boolean call() throws Exception {
		 boolean ok =  monitor();				
		 if(ok){
			 //归位邮件发送次数
			 mybasicBank.setSendcount(0);
			 mybasicBank.setWebok("正常");
		 }else{
			int count =  mybasicBank.getSendcount();
			 mybasicBank.setSendcount(count+1);
			 mybasicBank.setWebok("down机");
		 }
		 mybasicBank.setUpdateTime(new Date());
		 myBasicBankService.update(mybasicBank);
		return ok;
	}


	 
}
