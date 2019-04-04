package com.wft.sqluldr2.conf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wft.sqluldr2.util.XMLUtil;

/**
 * 配置文件管理 单例
 * 
 * @author Bob
 *
 */
public class ConfigManager {
	
	private volatile static ConfigManager confManager;
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
	
	public Document document;
	
	/**
	 * 私有的构造
	 */
	private ConfigManager() {
		reloadDom();
	}
	
	/**
	 * 获取实例，线程安全
	 * 
	 * @return
	 */
	public static ConfigManager getConfigManager() {
		if (confManager == null) {
			synchronized (ConfigManager.class) {
				if (confManager == null) {
					confManager = new ConfigManager();
				}
			}
		}
		return confManager;
	}
	
	/**
	 * 读取xml，加载到dom
	 */
	public void reloadDom() {
		SAXReader reader = new SAXReader();
		try {
			document = reader.read(new File(ConstantsConf.PATH_CONFIG_XML));
		} catch (DocumentException e) {
			e.printStackTrace();
			logger.error("Read config xml error:" + e.toString());
		}
	}

	/**
	 * 把document对象写入新的文件
	 * 
	 * @param document
	 * @throws Exception
	 */
	public void writeToXml() throws Exception {
		// 排版缩进的格式
		OutputFormat format = OutputFormat.createPrettyPrint();
		// 设置编码
		format.setEncoding("UTF-8");
		// 创建XMLWriter对象,指定了写出文件及编码格式
		XMLWriter writer = null;
		writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File(ConstantsConf.PATH_CONFIG_XML)), "UTF-8"), format);

		// 写入
		writer.write(document);
		writer.flush();
		writer.close();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getConfig(Class<T> clazz, String xmlPath){
		T t = (T) XMLUtil.convertXmlFileToObject(clazz, xmlPath);
		return t;
	}
	
	public String getLastSyncTime() {
		return this.document.selectSingleNode(ConstantsTools.XPATH_LAST_SYNC_TIME).getText();
	}

	public void setLastSyncTime(String lastSyncTime) throws Exception {
		this.document.selectSingleNode(ConstantsTools.XPATH_LAST_SYNC_TIME).setText(lastSyncTime);
		writeToXml();
	}

	public String getLastKeepTime() {
		return this.document.selectSingleNode(ConstantsTools.XPATH_LAST_KEEP_TIME).getText();
	}

	public void setLastKeepTime(String lastKeepTime) throws Exception {
		this.document.selectSingleNode(ConstantsTools.XPATH_LAST_KEEP_TIME).setText(lastKeepTime);
		writeToXml();
	}

	public String getSuccessTime() {
		return this.document.selectSingleNode(ConstantsTools.XPATH_SUCCESS_TIME).getText();
	}

	public void setSuccessTime(String successTime) throws Exception {
		this.document.selectSingleNode(ConstantsTools.XPATH_SUCCESS_TIME).setText(successTime);
		writeToXml();
	}

	public String getFailTime() {
		return this.document.selectSingleNode(ConstantsTools.XPATH_FAIL_TIME).getText();
	}

	public void setFailTime(String failTime) throws Exception {
		this.document.selectSingleNode(ConstantsTools.XPATH_FAIL_TIME).setText(failTime);
		writeToXml();
	}
	
	/**
	 * 获取源数据库配置-数据库驱动名称
	 * @return
	 */
	public String getFromClassName(){
		return this.document.selectSingleNode(ConstantsTools.DB.XPATH_FROM_CLASSNAME.getValue()).getText();
	}
	
	/**
	 * 设置源数据库配置-数据库驱动名称
	 */
	public void setFromClassName(String fromClassName) throws Exception {
		this.document.selectSingleNode(ConstantsTools.DB.XPATH_FROM_CLASSNAME.getValue()).setText(fromClassName);
		writeToXml();
	}
	
	/**
	 * 获取源数据库配置-文件路径
	 * @return
	 */
	public String getFromDir(){
		return this.document.selectSingleNode(ConstantsTools.DB.XPATH_FROM_DIR.getValue()).getText();
	}
	
	/**
	 * 设置源数据库配置-文件路径
	 */
	public void setFromDir(String fromDir) throws Exception {
		this.document.selectSingleNode(ConstantsTools.DB.XPATH_FROM_DIR.getValue()).setText(fromDir);
		writeToXml();
	}
	
	/**
	 * 获取源数据库配置-数据库类型
	 * @return
	 */
	public String getFromType() {
		return this.document.selectSingleNode(ConstantsTools.DB.XPATH_FROM_TYPE.getValue()).getText();
	}
	
	/**
	 * 设置源数据库配置-数据库类型
	 */
	public void setFromType(String typeFrom) throws Exception {
		this.document.selectSingleNode(ConstantsTools.DB.XPATH_FROM_TYPE.getValue()).setText(typeFrom);
		writeToXml();
	}
	
	/**
	 * 获取源数据库配置-数据库host
	 * @return
	 */
	public String getFromHost() {
		return this.document.selectSingleNode(ConstantsTools.DB.XPATH_FROM_HOST.getValue()).getText();
	}
	
	/**
	 * 设置源数据库配置-数据库host
	 */
	public void setFromHost(String hostFrom) throws Exception {
		this.document.selectSingleNode(ConstantsTools.DB.XPATH_FROM_HOST.getValue()).setText(hostFrom);
		writeToXml();
	}
	
	/**
	 * 获取源数据库配置-数据库实例名
	 */
	public String getFromName() {
		return this.document.selectSingleNode(ConstantsTools.DB.XPATH_FROM_NAME.getValue()).getText();
	}
	
	/**
	 * 设置源数据库配置-数据库实例名
	 */
	public void setFromName(String nameFrom) throws Exception {
		this.document.selectSingleNode(ConstantsTools.DB.XPATH_FROM_NAME.getValue()).setText(nameFrom);
		writeToXml();
	}
	
	/**
	 * 获取源数据库配置-数据库用户名
	 */
	public String getFromUser() {
		return this.document.selectSingleNode(ConstantsTools.DB.XPATH_FROM_USER.getValue()).getText();
	}

	/**
	 * 设置源数据库配置-数据库用户名
	 */
	public void setFromUser(String userFrom) throws Exception {
		this.document.selectSingleNode(ConstantsTools.DB.XPATH_FROM_USER.getValue()).setText(userFrom);
		writeToXml();
	}
	
	/**
	 * 获取源数据库配置-数据库密码
	 */
	public String getFromPassword() {
		return this.document.selectSingleNode(ConstantsTools.DB.XPATH_FROM_PASSWORD.getValue()).getText();
	}
	
	/**
	 * 设置源数据库配置-数据库密码
	 */
	public void setFromPassword(String passwordFrom) throws Exception {
		this.document.selectSingleNode(ConstantsTools.DB.XPATH_FROM_PASSWORD.getValue()).setText(passwordFrom);
		writeToXml();
	}
	
	/**
	 * 获取目标数据库配置-数据库驱动名称
	 * @return
	 */
	public String getToClassName(){
		return this.document.selectSingleNode(ConstantsTools.DB.XPATH_TO_CLASSNAME.getValue()).getText();
	}
	
	/**
	 * 设置目标数据库配置-数据库驱动名称
	 */
	public void setToClassName(String toClassName) throws Exception {
		this.document.selectSingleNode(ConstantsTools.DB.XPATH_TO_CLASSNAME.getValue()).setText(toClassName);
		writeToXml();
	}
	
	/**
	 * 获取目标数据库配置-文件路径
	 * @return
	 */
	public String getToDir(){
		return this.document.selectSingleNode(ConstantsTools.DB.XPATH_TO_DIR.getValue()).getText();
	}
	
	/**
	 * 设置目标数据库配置-文件路径
	 */
	public void setToDir(String toDir) throws Exception {
		this.document.selectSingleNode(ConstantsTools.DB.XPATH_TO_DIR.getValue()).setText(toDir);
		writeToXml();
	}
	
	/**
	 * 获取目标数据库配置-数据库类型
	 */
	public String getToType() {
		return this.document.selectSingleNode(ConstantsTools.DB.XPATH_TO_TYPE.getValue()).getText();
	}
	
	/**
	 * 设置目标数据库配置-数据库类型
	 */
	public void setToType(String typeTo) throws Exception {
		this.document.selectSingleNode(ConstantsTools.DB.XPATH_TO_TYPE.getValue()).setText(typeTo);
		writeToXml();
	}
	
	/**
	 * 获取目标数据库配置-数据库host
	 */
	public String getToHost() {
		return this.document.selectSingleNode(ConstantsTools.DB.XPATH_TO_HOST.getValue()).getText();
	}
	
	/**
	 * 设置目标数据库配置-数据库host
	 */
	public void setToHost(String hostTo) throws Exception {
		this.document.selectSingleNode(ConstantsTools.DB.XPATH_TO_HOST.getValue()).setText(hostTo);
		writeToXml();
	}
	
	/**
	 * 获取目标数据库配置-数据库实例名
	 */
	public String getToName() {
		return this.document.selectSingleNode(ConstantsTools.DB.XPATH_TO_NAME.getValue()).getText();
	}
	
	/**
	 * 设置目标数据库配置-数据库实例名
	 */
	public void setToName(String nameTo) throws Exception {
		this.document.selectSingleNode(ConstantsTools.DB.XPATH_TO_NAME.getValue()).setText(nameTo);
		writeToXml();
	}
	
	/**
	 * 获取目标数据库配置-数据库用户名
	 */
	public String getToUser() {
		return this.document.selectSingleNode(ConstantsTools.DB.XPATH_TO_USER.getValue()).getText();
	}
	
	/**
	 * 设置目标数据库配置-数据库用户名
	 */
	public void setToUser(String userTo) throws Exception {
		this.document.selectSingleNode(ConstantsTools.DB.XPATH_TO_USER.getValue()).setText(userTo);
		writeToXml();
	}
	
	/**
	 * 获取目标数据库配置-数据库密码
	 */
	public String getToPassword() {
		return this.document.selectSingleNode(ConstantsTools.DB.XPATH_TO_PASSWORD.getValue()).getText();
	}
	
	/**
	 * 设置目标数据库配置-数据库密码
	 */
	public void setToPassword(String passwordTo) throws Exception {
		this.document.selectSingleNode(ConstantsTools.DB.XPATH_TO_PASSWORD.getValue()).setText(passwordTo);
		writeToXml();
	}
	
	public String getSchedule() {
		return this.document.selectSingleNode(ConstantsTools.XPATH_SCHEDULE).getText();
	}

	public void setSchedule(String schedule) throws Exception {
		this.document.selectSingleNode(ConstantsTools.XPATH_SCHEDULE).setText(schedule);
		writeToXml();
	}

	public String getScheduleFixTime() {
		return this.document.selectSingleNode(ConstantsTools.XPATH_SCHEDULE_FIX_TIME).getText();
	}

	public void setScheduleFixTime(String fixTime) throws Exception {
		this.document.selectSingleNode(ConstantsTools.XPATH_SCHEDULE_FIX_TIME).setText(fixTime);
		writeToXml();
	}

	public String getAutoBak() {
		return this.document.selectSingleNode(ConstantsTools.XPATH_AUTO_BAK).getText();
	}

	public void setAutoBak(String autoBak) throws Exception {
		this.document.selectSingleNode(ConstantsTools.XPATH_AUTO_BAK).setText(autoBak);
		writeToXml();
	}

	public String getDebugMode() {
		return this.document.selectSingleNode(ConstantsTools.XPATH_DEBUG_MODE).getText();
	}

	public void setDebugMode(String debugMode) throws Exception {
		this.document.selectSingleNode(ConstantsTools.XPATH_DEBUG_MODE).setText(debugMode);
		writeToXml();
	}

	public String getStrictMode() {
		return this.document.selectSingleNode(ConstantsTools.XPATH_STRICT_MODE).getText();
	}

	public void setStrictMode(String strictMode) throws Exception {
		this.document.selectSingleNode(ConstantsTools.XPATH_STRICT_MODE).setText(strictMode);
		writeToXml();
	}

	public String getMysqlPath() {
		return this.document.selectSingleNode(ConstantsTools.XPATH_MYSQL_PATH).getText();
	}

	public void setMysqlPath(String mysqlPath) throws Exception {
		this.document.selectSingleNode(ConstantsTools.XPATH_MYSQL_PATH).setText(mysqlPath);
		writeToXml();
	}

	public String getProductName() {
		return this.document.selectSingleNode(ConstantsTools.XPATH_PRODUCT_NAME).getText();
	}

	public void setProductName(String productName) throws Exception {
		this.document.selectSingleNode(ConstantsTools.XPATH_PRODUCT_NAME).setText(productName);
		writeToXml();
	}

	public String getPositionCode() {
		return this.document.selectSingleNode(ConstantsTools.XPATH_POSITION_CODE).getText();
	}

	public void setPositionCode(String positionCode) throws Exception {
		this.document.selectSingleNode(ConstantsTools.XPATH_POSITION_CODE).setText(positionCode);
		writeToXml();
	}
}
