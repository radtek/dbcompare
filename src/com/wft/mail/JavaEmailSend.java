package com.wft.mail;
 import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class JavaEmailSend {

	public  static void  send(EmailSendBean bean) throws Exception{
		Properties properties = new Properties(); 
		properties.setProperty("mail.transport.protocol", "smtp");//发送邮件协议
		properties.setProperty("mail.smtp.auth", "true");//需要验证
		// properties.setProperty("mail.debug", "true");//设置debug模式 后台输出邮件发送的过程
		Session session = Session.getInstance(properties);
		session.setDebug(false);//debug模式
		MimeMultipart mul = new MimeMultipart();
		MimeBodyPart body = new MimeBodyPart();
		//邮件信息
		Message messgae = new MimeMessage(session);
		messgae.setFrom(new InternetAddress( bean.getEmail_user()));//设置发送人
	//	messgae.setText("张江中区雨水泵站的立项批文截止日期2014-12-5到期了，赶快出理");
		messgae.setSubject(bean.getEmail_subject());//设置邮件主题
		//messgae.addRecipient(Message.RecipientType.TO, new InternetAddress("545009370@qq.com"));
		 
		body.setContent(bean.getEmail_tip(), "text/html;charset=gb2312");
		mul.addBodyPart(body);
		messgae.setContent(mul);
		//发送邮件
		Transport tran = session.getTransport();
		// tran.connect("smtp.sohu.com", 25, "wuhuiyao@sohu.com", "xxxx");//连接到新浪邮箱服务器
		//tran.connect("smtp.sina.com", 25, "whyao@sina.cn", "xxxxxxx");//连接到新浪邮箱服务器
		tran.connect(bean.getEmail_smtp(), bean.getEmail_port(), bean.getEmail_user(), bean.getEmail_pwd());//连接到QQ邮箱服务器
		tran.sendMessage(messgae,bean.getInternetAddress());//设置邮件接收人
		tran.close();
	}
	
	public static void main(String[] args)throws Exception {
		//new JavaEmailSend().send();
		 EmailSendBean bean = new EmailSendBean();
		  bean.setEmail_port(25);bean.setEmail_pwd("xx");
		  bean.setEmail_user("545009370@qq.com");
		  bean.setEmail_smtp("smtp.qq.com");
		  bean.setEmail_subject("开会");
		  bean.setEmail_tip("张江中区雨水泵站的立项批文截止日期2014-12-5到期了，赶快出理");
		  Address internetAddress[] = {new InternetAddress("zhengyi.zhang@swiftpass.cn ")};
		  bean.setInternetAddress(internetAddress);
		  JavaEmailSend.send(bean);
	}
}
