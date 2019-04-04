package com.wft.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.wft.model.MybasicBank;

public class MyBasicBankMapper implements RowMapper<MybasicBank> {

	@Override
	public MybasicBank mapRow(ResultSet rs, int row) throws SQLException {
		MybasicBank u=new MybasicBank();
		u.setId(rs.getInt("ID"));
		u.setBankType(rs.getString("BANK_TYPE"));
		 u.setCheckContent(rs.getString("CHECK_CONTENT"));
		 u.setRealName(rs.getString("REAL_NAME"));
		 u.setCheckTime(rs.getString("CHECK_TIME"));
		 u.setCreateTime(rs.getDate("CREATE_TIME"));
		 u.setMold(rs.getInt("MOLD"));
		 u.setWebSite(rs.getString("WEB_STIE"));
		 u.setPhysicsFlag(rs.getInt("PHYSICS_FLAG"));
		 u.setPwd(rs.getString("PWD"));
		 u.setSechma(rs.getString("SECHMA"));
		 
		 u.setUpdateTime(rs.getDate("UPDATE_TIME"));
		 u.setUrl(rs.getString("URL"));
		 u.setUserName(rs.getString("USER_NAME"));
		 u.setCheckResult(rs.getInt("CHECK_RESULT"));
		 
		 u.setCheckIs(rs.getInt("CHECK_IS"));
		 u.setMerge(rs.getString("MERGE"));
		 u.setCleaingProcess(rs.getString("CLEAING_PROCESS"));
		 u.setPlanName(rs.getString("PLAN_NAME"));
		 u.setFls1(rs.getString("FLS1"));
		 u.setFls2(rs.getString("FLS2"));
		 u.setFls3(rs.getString("FLS3"));
		 
		 u.setAcceptorg(rs.getString("ACCEPTORG"));
		 u.setPlatform(rs.getString("PLATFORM"));
		 u.setJar(rs.getString("JAR"));
		
		 u.setMd5(rs.getString("MD5"));
		 u.setFiletype(rs.getString("FILETYPE"));
		 u.setFilecount(rs.getString("FILECOUNT"));
		 
		 u.setNullfile(rs.getString("NULLFILE"));
		 u.setPlanPameOverride(rs.getString("PLAN_PAME_OVERRIDE"));
		 u.setCleaingProcessOverride(rs.getString("CLEAING_PROCESS_OVERRIDE"));
			 
		 
		 u.setFtp(rs.getString("FTP"));
		 u.setNotify(rs.getString("NOTIFY"));
		 u.setAutoclean(rs.getString("AUTOCLEAN"));
		 
		 u.setServicePort(rs.getString("SERVICE_PORT"));
		 u.setEtraparam(rs.getString("ETRAPARAM"));
		 
		 u.setIsfreez(rs.getString("ISFREEZ"));
		 u.setIssubdiy(rs.getString("ISSUBDIY"));
		 u.setIsbroken(rs.getString("ISBROKEN"));
	 
		 u.setWebok(rs.getString("WEBOK"));
		 u.setNeedback(rs.getString("NEEDBACK"));
		 u.setNeedfile(rs.getString("NEEDFILE"));
		 u.setNeedcode(rs.getString("NEEDCODE"));
		 
		 u.setIssend(rs.getInt("ISSEND"));
		 u.setSendcount(rs.getInt("SENDCOUNT"));
		  
		 u.setTestSechma(rs.getString("TEST_SECHMA"));
		return u;
	}

}
