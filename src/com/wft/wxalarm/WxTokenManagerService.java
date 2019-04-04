package com.wft.wxalarm;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;


public class WxTokenManagerService {
	private  static Logger logger = LoggerFactory.getLogger(WxTokenManagerService.class);
	
	/*
	 * 公众号接口返回的token有效时间是7200秒，一天只能访问30次，所以我们需要设置缓存7000秒
	 * (non-Javadoc)
	 * @see cn.swiftpass.core.facade.cle.WxTokenManagerService#createToken(cn.swiftpass.core.facade.cle.dto.AlarmDto)
	 */
 
	public static  WxTokenRespDto  createToken(AlarmDto alarmDto) {
		WxTokenRespDto token = null;
		try {
			Object obj = CacheUtil.getInstance().get("token");
			if(obj!=null){//缓存token
				token = (WxTokenRespDto) obj;
				return token;
			}
			String url = String.format(alarmDto.getTokenUrl(), alarmDto.getCorpid(), alarmDto.getCorpsecret());
			logger.info("url={}", url);
			String result = HttpsRequest.get(url, alarmDto.getCharCode());
			if (StringUtils.isEmpty(result)) {
				logger.error("获取的token为null/空，请检查!");
				return token;
			}
			JSONObject jsonObj = JSONObject.parseObject(result);
			token = (WxTokenRespDto) JSONObject.toJavaObject(jsonObj, WxTokenRespDto.class);


			//缓存token 7200s
			CacheUtil.getInstance().set("token", token, 0L);
		} catch (Exception e) {
			logger.error("获取API TOKEN 异常, ex:\n" + e.getMessage());
		}
		return token;
	}

}
