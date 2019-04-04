package com.wft.mail;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wft.model.MybasicBank;
import com.wft.util.DateUtils;
import com.wft.util.SystemMessage;

@Service
public class MailSendService {
	private final static Logger log = Logger.getLogger(MailSendService.class);
 
	//邮件提醒
	@Value("${email_switch}")
	private String email_switch ;
	@Value("${email_smtp}")
	private String email_smtp ;
	@Value("${email_port}")
	private String email_port ;
	@Value("${email_user}")
	private String email_user ;
	@Value("${email_pwd}")
	private String email_pwd ;
	
	@Value("${emails}")
	private String emails ;
	//发送邮件
	public void sendMail(MybasicBank mybasicBank, String content) throws Exception {
			 
			Thread.sleep(5000);
			if("on".equalsIgnoreCase(email_switch)){
				 
				if(StringUtils.isNotBlank(emails)){
					String[] emaills = emails.split(",");
					 Address internetAddress[] = new InternetAddress[emaills.length];
					for(int i=0;i<emaills.length;i++ ){
						internetAddress[i]=new InternetAddress(emaills[i]);
					}
				
					log.info("================邮件提醒===================");
					  final EmailSendBean emailSendBean = new EmailSendBean();
					 
					  emailSendBean.setEmail_smtp(email_smtp);
					  emailSendBean.setEmail_port(Integer.valueOf(email_port));
					  emailSendBean.setEmail_pwd(email_pwd);
					  emailSendBean.setEmail_user(email_user);
					  String email_subject_template = SystemMessage.getString("email_subject_template");
					  email_subject_template = email_subject_template.replaceAll("bank",mybasicBank.getRealName()).replaceAll("time", DateUtils.getCurrentTime());
					  String email_subject = email_subject_template;
					  emailSendBean.setEmail_subject(email_subject);
					  String email_content_template = SystemMessage.getString("email_content_template");
					  email_content_template = email_content_template.replaceAll("content", content);
					  String email_content = email_content_template;
					  emailSendBean.setEmail_tip(email_content);
					 
					  emailSendBean.setInternetAddress(internetAddress);
					 //发送邮件
					  new Thread(new Runnable() {
						public void run() {
							 try {
								JavaEmailSend.send(emailSendBean);
							} catch (Exception e) {
								 
								e.printStackTrace();
								log.error("邮件发送失败"+e.getMessage());
							}
							
						}
					}).start();
				}
				
			}
		}
}
