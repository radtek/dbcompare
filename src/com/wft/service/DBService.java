package com.wft.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wft.dao.DBDao;
import com.wft.exception.AppException;
import com.wft.reg.ParseSql;
import com.wft.reg.RegexSql;
import com.wft.util.CommonUtil;

/**
 * @author admin
 * 
 */
@Service("dBService")
@Transactional
public class DBService {

	private final static Logger log = Logger.getLogger(DBService.class);

	@Autowired
	private DBDao dBDao;

	/**
	 * @param check是否入库检测,针对有些脚本不需要入库
	 * @param fileName 文件路径
	 * @param shema 表空间
	 * @return
	 */
	public boolean excuteDDLFromFile(int check,String fileName, String shema) {
		boolean flag = true;
		
		try {
			//1.先从脚本文件里解析sql语句，一个sql语句已分号结束
			String[] sqls = ParseSql.parseFromFile(fileName);
			//2.检测是否是DDL语句
			boolean result  = RegexSql.isDDL(sqls);
			if(result){
				result  = RegexSql.isDDLFormat(sqls);
				if(result){
					if(CommonUtil.CHECK_ON==check){
						//3.通过前台界面决定某一脚本sql是否执行入库
						flag = excuteDDL(sqls);
					}else{
						log.info("改DDL脚本不直接入库:"+fileName);
					}
					 
				}
				
			}else{
				flag = false;
				log.info("该文件不是DDL脚本");
				throw new AppException("该文件不是DDL脚本,请检查");
			}
			
		}catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
			throw e;
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
			 
			
		}
		return flag;
	}
	
	/**
	 * @param check是否入库检测 针对有些脚本不需要入库
	 * @param fileName 文件路径
	 * @param shema 表空间
	 * @return
	 */
	public boolean excuteDMLFromFile(int check,String fileName, String shema) {
		boolean flag = true;
		try {
			//1.先从脚本文件里解析sql语句，一个sql语句已分号结束
			String[] sqls = ParseSql.parseFromFile(fileName);
			//2.检测是否是DML语句
			boolean result  = RegexSql.isDML(sqls);
			if(result){
				result  = RegexSql.isDMLFormat(sqls);
				if(result){
					if(CommonUtil.CHECK_ON==check){
						//3.通过前台界面决定某一脚本sql是否执行入库
						flag = excuteDML(sqls);
					}else{
						log.info("改DML脚本不直接入库:"+fileName);
					}
					 
				}
				
			}else{
				flag = false;
				log.info("该文件不是DML脚本");
				throw new AppException("该文件不是DML脚本,请检查");
			}
			
			 
		}catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
			throw e;
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
			 
			
		}
		return flag;
	}
	
	 
	/**
	 * @param sql
	 * sql ddl执行入库
	 * @return
	 */
	private boolean excuteDDL(String... sql) {
		boolean flag = true;
		flag = dBDao.excuteDDL(sql);
		return flag;
}

	/**
	 * @param sql
	 * sql dml执行入库
	 * @return
	 */
	private boolean excuteDML(String... sql) {
		
		boolean flag = true;
		flag = dBDao.excuteDML(sql);
		
		return flag;
		 
	}

	
}
