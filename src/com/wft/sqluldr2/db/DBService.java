package com.wft.sqluldr2.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBService {
	
	/**
	 * 连接数据库服务
	 * @return 
	 * @return
	 */
	public Connection getConnection() throws SQLException;
	
	/**
	 * 关闭数据库连接
	 * @throws SQLException
	 */
	public void close () throws SQLException;
	
}
