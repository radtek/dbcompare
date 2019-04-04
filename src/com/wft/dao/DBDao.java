package com.wft.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wft.exception.AppException;

/**
 * @author admin 执行sql的工具包
 */
@Repository("dBDao")
public class DBDao {

	private final static Logger log = Logger.getLogger(DBDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * @param sqls
	 * @return
	 * 执行ddl 由于不会回滚 所以错误忽视
	 */
	public boolean excuteDDL(String... sqls) {
		boolean result = false;
		for (String sql : sqls) {
			log.info("excuteDDL bach :"+sql);
			sql = sql.replace("zhifu.", " ");
			try {
				jdbcTemplate.execute(sql);
				result = true;
			} catch (Exception e) {
				String es = e.getMessage();
				if(es.contains("ORA-00955")){
					log.info("ORA-00955: 名称已由现有对象使用无需处理:"+es);
					result = true;
					continue;
					//result = false;
					//throw new AppException("ORA-00955: 名称已由现有对象使用无需处理:"+es);
				}else if(es.contains("ORA-01430")){
					log.info("ORA-01430: column being added already exists in table"+es);
					result = true;
					continue;
					//result = false;
					//throw new AppException("ORA-01430: column being added already exists in table"+es);
				}
				else{
					log.info("需处理改异常信息");
					result = false;
					throw new AppException(e.getMessage());
				}
				
			}

		}
 
		return result;
	}
	
	/**
	 * @param sqls
	 * @return
	 * 执行dml 由于会回滚 所以错误不忽视
	 */
	public boolean excuteDML(String... sqls) {
		boolean result = false;
		for (String sql : sqls) {
			log.info("excuteDDL bach :"+sql);
			sql = sql.replace("zhifu.", " ");
			try {
				jdbcTemplate.execute(sql);
				result = true;
			} catch (Exception e) {
				String es = e.getMessage();
				if(es.contains("ORA-00955")){
					log.info("ORA-00955: 名称已由现有对象使用无需处理"+es);
					//continue;
					result = false;
					throw new AppException("ORA-00955: 名称已由现有对象使用无需处理"+es);
				}else if(es.contains("ORA-00001")){
					log.info("ORA-00001: 违反唯一约束条件"+es);
					//continue;
					result = false;
					throw new AppException("ORA-00001: 违反唯一约束条件"+es);
				}else{
					log.info("需处理改异常信息");
					result = false;
					throw new AppException(e.getMessage());
				}
				
			}

		}
 
		return result;
	}
}
