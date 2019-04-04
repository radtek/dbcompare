package com.wft.wxalarm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.wft.util.SystemMessage;


/**
 * @author admin
 * 发送
 */
public class AlarmService {

	private static Logger logger = LoggerFactory.getLogger(AlarmService.class);
	private static ExecutorService executorService  =  Executors.newFixedThreadPool(10);
	
	//公众号告警配置参数
	private static String SYS_ALARM_API_INFO = "{'corpid':'wxa1ca44b43ea133fa','corpsecret':'4ayLuW-SF54wi6HgX4OYSGkHulgzjSCZkDRiFVm7SmQ','agentid':1000002}";
	 
	static{
		String emailswx = SystemMessage.getString("emailswx");
		if(StringUtils.isNotBlank(emailswx)){
			SYS_ALARM_API_INFO = emailswx;
		}
	}
	/*
	 * 用线程处理，避免等待
	 * (non-Javadoc)
	 * @see cn.swiftpass.core.facade.cle.alarm.AlarmService#sendAlarmMessage(java.lang.String, java.lang.String)
	 */
	 
	public static void sendAlarmMessage(final String message) {
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				//根据受理机构ID查询 配置的参数
				String jsonResult = SYS_ALARM_API_INFO;
				logger.info("jsonResult={}", jsonResult);
				try {
					JSONObject jsonObj = JSONObject.parseObject(jsonResult);
					AlarmDto alarm = (AlarmDto) JSONObject.toJavaObject(jsonObj, AlarmDto.class);
					WxTokenRespDto tokenDto = WxTokenManagerService.createToken(alarm);
					logger.info("tokenDto={}", tokenDto);
					if (null == tokenDto || 0 != tokenDto.getErrcode() || StringUtils.isEmpty(tokenDto.getAccess_token())) {
						logger.error("调用公众号接口[{}]获取token失败, 无法发起公众号告警, 请检查.", alarm.getTokenUrl());
						return;
					}
					alarm.setToken(tokenDto.getAccess_token());
					alarm.setContent(message);
					
					//调用企业号接口发消息
					String alarmRespJson = HttpsRequest.post(alarm.getUrl(), alarm.toJson(), alarm.getCharCode());
					logger.info("alarmRespJson={}", alarmRespJson);
					if (StringUtils.isEmpty(alarmRespJson)) {
						logger.error("告警消息：【{}】已发送，但未收到公众号API返回的信息.", message);
						return;
					}
					
					JSONObject alarmJsonObj = JSONObject.parseObject(alarmRespJson);
					AlarmRespDto alarmResp = (AlarmRespDto) JSONObject.toJavaObject(alarmJsonObj, AlarmRespDto.class);
					logger.info("本次消息发送：【{}】, msg：【{}】", alarmResp.isSuccess(), alarmResp.getErrmsg());
				} catch (Exception e) {
					logger.error("json转换异常, 无法发起公众号告警, 请检查. ex:\n" + e);
				}
			}
		});
	}

}
