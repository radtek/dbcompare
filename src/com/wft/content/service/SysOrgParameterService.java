package com.wft.content.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.wft.content.dao.SysOrgParameterDao;
import com.wft.content.dto.CheckType;
import com.wft.content.dto.SysOrgParameter;
import com.wft.db.DBTempory;

/**
 * @author admin
 * 系统参数表CMS_ORG_PARAMETER_CONF
 */
public class SysOrgParameterService  {


	private final static Logger log = Logger.getLogger(SysOrgParameterService.class);
	
	public static List<SysOrgParameter> getDatas(MyArrayList<SysOrgParameter> standLs ,MyArrayList<SysOrgParameter> standnoLs ) throws Exception{
		return ParameterService.getDatas(standLs, standnoLs);
	}
	 
	
	
	public static void main(String[] args) throws Exception {
		DBTempory tstandnoLs = new DBTempory("jdbc:oracle:thin:@123.207.118.153:1521:ntest","zhangzhengyi","zhangzhengyi","zhangzhengyi");
		DBTempory tstandLs = new DBTempory("jdbc:oracle:thin:@123.207.118.153:1521:ntest","gdpsbc","gdpsbc","gdpsbc");
		MyArrayList<SysOrgParameter> standLs  = SysOrgParameterDao.getDatas(tstandLs, CheckType.CAN_KAO_DATABASE);
		MyArrayList<SysOrgParameter> standnoLs  = SysOrgParameterDao.getDatas(tstandnoLs, CheckType.NO_CAN_KAO_DATABASE);
		List<SysOrgParameter> ls = getDatas(standLs,standnoLs);
		for(SysOrgParameter sys:ls){
			log.info(sys);
		}
		
		 
	}
}
