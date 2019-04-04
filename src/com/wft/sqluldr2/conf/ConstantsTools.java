package com.wft.sqluldr2.conf;

import java.io.File;

/**
 * 工具层相关的常量
 * 
 * @author Bob
 *
 */
public interface ConstantsTools {
			
	 /**
     * 系统当前路径
     */
    public final static String CURRENT_DIR = "D:/公司资料/test/wft/src";//ConstantsTools.class.getClassLoader ().getResource("/").getPath();

	
	// 配置文件实例
	public final static ConfigManager CONFIGER = ConfigManager.getConfigManager();
	
	// 日志文件 路径
	public final static String PATH_LOG = CURRENT_DIR + File.separator + "log" + File.separator + "log.log";
	
	// xpath
	public final static String XPATH_LAST_SYNC_TIME = "//dataSync/status/lastSyncTime";
	public final static String XPATH_LAST_KEEP_TIME = "//dataSync/status/lastKeepTime";
	public final static String XPATH_SUCCESS_TIME = "//dataSync/status/successTime";
	public final static String XPATH_FAIL_TIME = "//dataSync/status/failTime";
	public final static String XPATH_SCHEDULE = "//dataSync/schedule/radio";
	public final static String XPATH_SCHEDULE_FIX_TIME = "//dataSync/schedule/fixtime";
	public final static String XPATH_AUTO_BAK = "//dataSync/setting/autoBak";
	public final static String XPATH_DEBUG_MODE = "//dataSync/setting/debugMode";
	public final static String XPATH_STRICT_MODE = "//dataSync/setting/strictMode";
	public final static String XPATH_MYSQL_PATH = "//dataSync/setting/mysqlPath";
	public final static String XPATH_PRODUCT_NAME = "//dataSync/setting/productname";
	public final static String XPATH_POSITION_CODE = "//dataSync/increase/POSITION_CODE";
	
	/**
	 * 源数据库信息
	 * @author Regan.zhou
	 */
	enum DB {
		//源
		XPATH_FROM_CLASSNAME("源数据库配置-驱动名称","//dataSync/database/from/className"),
		XPATH_FROM_DIR("源数据库配置-文件路径","//dataSync/database/from/dir"),
		XPATH_FROM_TYPE("源数据库配置-数据库类型", "//dataSync/database/from/type"),
		XPATH_FROM_HOST("源数据库配置-数据库host", "//dataSync/database/from/host"),
		XPATH_FROM_NAME("源数据库配置-数据库实例名", "//dataSync/database/from/name"),
		XPATH_FROM_USER("源数据库配置-数据库用户名", "//dataSync/database/from/user"),
		XPATH_FROM_PASSWORD("源数据库配置-数据库密码", "//dataSync/database/from/password"),
		
		//目标
		XPATH_TO_CLASSNAME("目标数据库配置-驱动名称", "//dataSync/database/to/className"),
		XPATH_TO_DIR("目标数据库配置-文件路径", "//dataSync/database/to/dir"),
		XPATH_TO_TYPE("目标数据库配置-数据库类型", "//dataSync/database/to/type"),
		XPATH_TO_HOST("目标数据库配置-数据库host", "//dataSync/database/to/host"),
		XPATH_TO_NAME("目标数据库配置-数据库实例名", "//dataSync/database/to/name"),
		XPATH_TO_USER("目标数据库配置-数据库用户名", "//dataSync/database/to/user"),
		XPATH_TO_PASSWORD("目标数据库配置-数据库密码", "//dataSync/database/to/password");
		
		private String name;
		
		private String value;
		
		private DB(String name, String value){
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}
	}
	
}
