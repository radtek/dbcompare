package com.wft.sqluldr2.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wft.sqluldr2.conf.ConstantsTools;
import com.wft.sqluldr2.logic.bean.DBVo;
import com.wft.sqluldr2.util.PropertyUtil;

/**
 * 线程接口抽象类
 * 
 * @author yunfeng.zhou
 */
public abstract class AbstractDBService implements DBService {
	
	protected static final Logger logger = LoggerFactory.getLogger(AbstractDBService.class);
	
	protected Connection connection = null;
	protected Statement statement = null;
	protected ResultSet resultSet = null;
	protected DBVo dbVo;
	
	/**
     * 从配置文件加载设置数据库信息
     */
	protected void loadConfig() {
		try {
			/*dbVo = new DBVo();
			
			dbVo.setType(config.getType());
			dbVo.setDBClassName(config.getClassName());
			dbVo.setDBServiceName(config.getName());
			dbVo.setIp(config.getHost());
			dbVo.setDBUser(config.getUser());
			dbVo.setDBPwd(config.getPassword());
			
			dbVo.setType(ConstantsTools.CONFIGER.getFromType());
            dbVo.setDBClassName(ConstantsTools.CONFIGER.getFromClassName());
            dbVo.setDBServiceName(ConstantsTools.CONFIGER.getFromName());
            dbVo.setIp(ConstantsTools.CONFIGER.getFromHost());
            dbVo.setDBUser(ConstantsTools.CONFIGER.getFromUser());
            dbVo.setDBPwd(ConstantsTools.CONFIGER.getFromPassword());
            Class.forName(dbVo.getDBClassName());*/
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
    }
    
    /**
     * 获取连接，线程安全
     * 
     * @return
     * @throws SQLException
     */
	@Override
    public synchronized Connection getConnection() throws SQLException{
    	String user = "";
        String password = "";
        try {
        	user = dbVo.getDBUser();
        	password = dbVo.getDBPwd();
        } catch (Exception e) {
            logger.error(PropertyUtil.getProperty("ds.ui.database.to.err.decode") + e.toString());
            e.printStackTrace();
        }
        
        // 当DB配置变更时重新获取
        if (!ConstantsTools.CONFIGER.getToHost().equals(dbVo.getIp()) 
        		|| !ConstantsTools.CONFIGER.getToName().equals(dbVo.getDBServiceName())
                || !ConstantsTools.CONFIGER.getToUser().equals(dbVo.getDBUser())
                || !ConstantsTools.CONFIGER.getToPassword().equals(dbVo.getDBPwd())) {
            loadConfig();

            connection = DriverManager.getConnection(dbVo.getDBUrl() , user, password);
            // 把事务提交方式改为手工提交
            connection.setAutoCommit(false);
        }

        // 当connection失效时重新获取
        if (connection == null || connection.isValid(10) == false) {
            connection = DriverManager.getConnection(dbVo.getDBUrl() , user, password);
            // 把事务提交方式改为手工提交
            connection.setAutoCommit(false);
        }

        if (connection == null) {
            logger.error("Can not load Oracle jdbc and get connection.");
        }
        return connection;
    }
    
    /**
     * 测试连接，线程安全 参数从配置文件获取
     * 
     * @return
     * @throws SQLException
     */
    public synchronized Connection testConnection() throws SQLException {
    	return this.getConnection();
    }
    
    /**
     * 关闭（结果集、声明、连接），线程安全
     *
     * @throws SQLException
     */
    @Override
    public synchronized void close() {
    	try {
    		if (resultSet != null) {
    			resultSet.close();
    			resultSet = null;
    		}
    		
    		if (statement != null) {
    			statement.close();
    			statement = null;
    		}
    		
    		if (connection != null) {
    			connection.close();
    			connection = null;
    		}
		} catch (SQLException e) {
			logger.error("关闭数据库（结果集、声明、连接）异常, exception: ", e);
		}
    }

    /**
     * 执行查询，线程安全
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public synchronized ResultSet executeQuery(String sql) throws SQLException {
        getStatement();
        if (resultSet != null && resultSet.isClosed() == false) {
            resultSet.close();
        }
        resultSet = null;
        resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    /**
     * 执行更新，线程安全
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public synchronized int executeUpdate(String sql) throws SQLException {
        int result = 0;
        getStatement();
        result = statement.executeUpdate(sql);
        return result;
    }
    
    /**
     * 获取数据库声明，私有，线程安全
     *
     * @throws SQLException
     */
    private synchronized void getStatement() throws SQLException {
    	this.getConnection();
        // 仅当statement失效时才重新创建
        if (statement == null || statement.isClosed() == true) {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        }
    }
	
}
