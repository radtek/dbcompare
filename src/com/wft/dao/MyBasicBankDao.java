package com.wft.dao;

import java.sql.Types;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wft.dao.mapper.MyBasicBankMapper;
import com.wft.model.MybasicBank;
import com.wft.util.CommonUtil;


@Repository("myBasicBankDao")
public class MyBasicBankDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	 

 
	public List<MybasicBank> findAll(MybasicBank mybasicBank) {
		 
		List<MybasicBank> fs = null;
		if(mybasicBank==null){
			Object[] o = {CommonUtil.EABLE};
			fs= jdbcTemplate.query("select * from MY_BASIC_BANK where PHYSICS_FLAG = ?   ",o,new MyBasicBankMapper());
		}else{
			int physicsFlag = mybasicBank.getPhysicsFlag();
			String sechma = mybasicBank.getSechma();
			StringBuffer sb = null;
			if(CommonUtil.ALL==physicsFlag){//查询所有
				 sb = new StringBuffer("select * from MY_BASIC_BANK  where 1=1  ");
			}else{
				
				 sb = new StringBuffer("select * from MY_BASIC_BANK  where 1=1 and PHYSICS_FLAG= "+physicsFlag);;
			}
			if(StringUtils.isNotBlank(sechma)){
				sb.append(" and   (SECHMA like '%"+sechma.toLowerCase()+"%' or REAL_NAME like '%"+sechma.toLowerCase()+"%')" );
			}
			 
			 
			System.out.println("  findAll:"+sb.toString());
			fs= jdbcTemplate.query(sb.toString(),new MyBasicBankMapper());
		}

		return fs;
	}
	 
	
	public int add(MybasicBank u){
		String sql="INSERT INTO MY_BASIC_BANK (ID, URL,WEB_STIE,USER_NAME,REAL_NAME, PWD, SECHMA,BANK_TYPE,MOLD,CHECK_TIME,CHECK_RESULT,CHECK_CONTENT,PHYSICS_FLAG,UPDATE_TIME,CREATE_TIME,CHECK_IS,issend,sendcount) VALUES (SEQ_MY_BASIC_BANK.nextval, ?,?,?,?, ?,?, ?,?,?,?,?,?,?,?,?,?,?)";
		Object[] o={u.getUrl(),u.getWebSite(),u.getUserName(),u.getRealName(),u.getPwd(),u.getSechma(),u.getBankType(),u.getMold(),u.getCheckTime(),u.getCheckResult(),u.getCheckContent(),u.getPhysicsFlag(),u.getUpdateTime(),u.getCreateTime(),u.getCheckIs(),0,0};
		int[] argTypes={Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP,Types.TIMESTAMP,Types.INTEGER,Types.INTEGER,Types.INTEGER};
		int res=jdbcTemplate.update(sql, o, argTypes);
		return res;
	}
 
    
	public int update(MybasicBank u) {
		String sql="UPDATE MY_BASIC_BANK SET CHECK_TIME=?,CHECK_RESULT=?, CHECK_CONTENT=?,PHYSICS_FLAG = ?,CHECK_IS=?,MERGE=?,CLEAING_PROCESS=? ,PLAN_NAME=?,FLS1=?,FLS2=?,FLS3=?,WEB_STIE=?,"
				+ " acceptorg = ?,platform = ?, jar = ?, md5 = ?, fileType = ?,filecount = ?,nullfile = ?,plan_pame_override = ?,cleaing_process_override = ?,ftp = ?,"
				+ " notify = ?,autoclean = ?,service_port = ?,etraparam = ?,isfreez = ?,issubdiy = ?,isbroken = ?,"
				+ " webok = ?,needback = ?,needfile = ?,needcode = ?,issend = ?,sendcount = ?,UPDATE_TIME=? WHERE id =?";
		Object[] o={u.getCheckTime(),u.getCheckResult(),u.getCheckContent(),u.getPhysicsFlag(),u.getCheckIs(),u.getMerge(),u.getCleaingProcess(),u.getPlanName(),u.getFls1(),u.getFls2(),u.getFls3(),u.getWebSite(),
				 u.getAcceptorg(),		 u.getPlatform(),		 u.getJar(),			 u.getMd5(),		 u.getFiletype(),
				 u.getFilecount(),		 u.getNullfile(),	 u.getPlanPameOverride(),		 u.getCleaingProcessOverride(),		 u.getFtp( ),
				 u.getNotify( ),		 u.getAutoclean( ),		 u.getServicePort( ),		 u.getEtraparam(),		 u.getIsfreez( ),		 u.getIssubdiy (),		 u.getIsbroken(),
				 u.getWebok(),		 u.getNeedback(),		 u.getNeedfile(),		 u.getNeedcode(), u.getIssend(),u.getSendcount(),u.getUpdateTime(),
				 u.getId()};
		int[] argTypes={Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.INTEGER,Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
				Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
				Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
				Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER,Types.TIMESTAMP,Types.INTEGER};
		int res=jdbcTemplate.update(sql, o, argTypes);
		return res;
	}
	 
	public MybasicBank findById(int id) {
		String sql="select * FROM MY_BASIC_BANK WHERE id = ?";
		Object[] o={id};
		List<MybasicBank> fs = jdbcTemplate.query(sql,o,new MyBasicBankMapper());
		if(fs!=null&&fs.size()>0){
			return fs.get(0);
		}
		return null;
	}
	
	public int delete(int id) {
		String sql="DELETE FROM MY_BASIC_BANK WHERE id = ?";
		Object[] o={id};
		int[] argTypes={Types.INTEGER};
		int res=jdbcTemplate.update(sql, o, argTypes);
		return res;
	}
	 
	 
	public List<MybasicBank> findCHeck() {
		 
		List<MybasicBank> fs = null;
		fs= jdbcTemplate.query("select * from MY_BASIC_BANK where PHYSICS_FLAG = 1 and CHECK_IS=0  ",new MyBasicBankMapper());
	 
		return fs;
	}
	 
	
}
