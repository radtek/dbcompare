package com.wft.wxalarm;


/**
 * 公众号告警消息对象
 * @author lisheng
 * @date 2017-08-15
 *
 */
public class AlarmDto {

	/**
	 * CorpID是企业号的标识，每个企业号拥有一个唯一的CorpID。
	 */
	private String corpid;
	
	/**
	 * secret是管理组凭证密钥，系统管理员在企业号管理后台创建管理组时，企业号后台为该管理组分配一个唯一的secret。
	 * 通过该secret能够确定管理组，及管理组所拥有的对应用、通讯录、接口的访问权限。
	 */
	private String corpsecret;
	
	/**
	 * AccessToken是企业号的全局唯一票据，调用接口时需携带AccessToken。
	 * AccessToken需要用CorpID和Secret来换取，有效期为7200秒，有效期内重复获取返回相同结果
	 */
	private String token;
	
	/**
	 * 获得token的API url地址， 需要使用String.format替换%s
	 */
	private String tokenUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
	
	/**
	 * 公众号推送消息api接口地址， 需要使用String.format替换%s
	 * https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=TOKEN
	 */
	private String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=%s";
	
	/**
	 * https请求的编码
	 */
	private String charCode = "UTF-8";
	
	/**
	 * 【API报文-JSON字段】
	 * 成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。
	 * 特殊情况：指定为@all，则向关注该企业应用的全部成员发送
	 */
	private String touser = "@all";
	
	/**
	 * 【API报文-JSON字段】
	 * 部门ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
	 */
	private String toparty = "@all";
	
	/** 
	 * 【API报文-JSON字段】
	 * 标签ID列表，多个接收者用‘|’分隔，最多支持100个。当touser为@all时忽略本参数
	 */
	private String totag = "@all";
	
	/**
	 * 【API报文-JSON字段】
	 * 消息类型，此时固定为：text （支持消息型应用跟主页型应用）
	 */
	private String msgtype = "text";
	
	/**
	 * 【API报文-JSON字段】
	 * 企业应用的id，整型。可在应用的设置页面查看
	 */
	private int agentid;
	
	/**
	 * 【API报文-JSON字段】
	 * 消息内容，最长不超过2048个字节，注意：主页型应用推送的文本消息在微信端最多只显示20个字（包含中英文）
	 */
	private String content;
	
	/**
	 * 【API报文-JSON字段】
	 * 表示是否是保密消息，0表示否，1表示是，默认0
	 */
	private int safe = 0;
	
	/**
	 * 生成告警消息json报文
	 * @return
	 */
	public String toJson() {
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append(String.format("\"%s\":\"%s\",", "touser", touser));
		json.append(String.format("\"%s\":\"%s\",", "toparty", toparty));
		json.append(String.format("\"%s\":\"%s\",", "totag", totag));
		json.append(String.format("\"%s\":\"%s\",", "msgtype", msgtype));
		json.append(String.format("\"%s\":%s,", "agentid", agentid));
		json.append(String.format("\"%s\":{\"%s\":\"%s\"},", "text", "content", content));
		json.append(String.format("\"%s\":%s", "safe", safe));
		json.append("}");
		return json.toString();
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getCorpsecret() {
		return corpsecret;
	}

	public void setCorpsecret(String corpsecret) {
		this.corpsecret = corpsecret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenUrl() {
		return tokenUrl;
	}

	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
	}

	public String getUrl() {
		return String.format(url, getToken());
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCharCode() {
		return charCode;
	}

	public void setCharCode(String charCode) {
		this.charCode = charCode;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getToparty() {
		return toparty;
	}

	public void setToparty(String toparty) {
		this.toparty = toparty;
	}

	public String getTotag() {
		return totag;
	}

	public void setTotag(String totag) {
		this.totag = totag;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public int getAgentid() {
		return agentid;
	}

	public void setAgentid(int agentid) {
		this.agentid = agentid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getSafe() {
		return safe;
	}

	public void setSafe(int safe) {
		this.safe = safe;
	}
}
