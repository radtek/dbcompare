package com.wft.sqluldr2;

import java.util.HashMap;
import java.util.Map;

public class SqlUldrUitl {

	public static final Map<Integer,String> sqluldrerrorMap = new HashMap<Integer, String>();
	static{
		sqluldrerrorMap.put(1, "不能登录数据库");
		sqluldrerrorMap.put(2, "不能创建cusor");
		sqluldrerrorMap.put(3, "不能分析sql");
		sqluldrerrorMap.put(4, "不能执行sql");
		sqluldrerrorMap.put(5, "不能解释返回结果集");
		sqluldrerrorMap.put(6, "不能生成文件");
		sqluldrerrorMap.put(7, "数据库其它错误");
	}
}
