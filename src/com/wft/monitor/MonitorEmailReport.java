package com.wft.monitor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wft.mail.MailSendService;
import com.wft.model.MybasicBank;
import com.wft.util.DateUtils;
import com.wft.util.SystemMessage;
import com.wft.wxalarm.AlarmService;

@Component
public class MonitorEmailReport implements MonitorReport {

	private final static Logger log = Logger.getLogger(MonitorEmailReport.class);
	 
	@Autowired
	private MailSendService mailSendService;
	@Override
	public void report(MybasicBank mybasicBank,String message) {
		
		log.info("mail:"+message);
		try {
			int issend = mybasicBank.getIssend();
			if(issend!=1){
				log.info("issend:"+issend+" issend!=1配置无需发送邮件{}"+mybasicBank.getRealName());
				return;
			}
			int count =  mybasicBank.getSendcount();
			int emailmaxCount = SystemMessage.getInteger("emailmaxCount");
			if(count>=emailmaxCount){
				log.info("count:"+count+" 发送次数超过最大次数 不发邮件{}"+mybasicBank.getRealName());
			}else{
				/*//此处修改哈逻辑 只有连续报两次错误后再发送通知
				if(count<2){
					log.info("count:"+count+" 只有连续报两次错误后再发送通知{}"+mybasicBank.getRealName());
					return;
				}*/
				//发送邮件
				mailSendService.sendMail(  mybasicBank,message);
				//发送微信
				String email_subject_template = SystemMessage.getString("email_subject_template");
				email_subject_template = email_subject_template.
						replaceAll("count",(mybasicBank.getSendcount()+1)+"").
						replaceAll("bank",mybasicBank.getRealName()).
						replaceAll("url",mybasicBank.getWebSite()).
						replaceAll("time", DateUtils.getCurrentTime());
				AlarmService.sendAlarmMessage(email_subject_template);
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
