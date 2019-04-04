package com.wft.appconf.dao;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.wft.appconf.dto.MyBankConf;


@Repository("myBankConfDao")
public class MyBankConfDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	 

 
	public List<MyBankConf> findAll(MyBankConf MyBankConf) {
		 
		List<MyBankConf> fs = null;
		StringBuffer   sb = new StringBuffer("select * from MY_BANK_CONF  where 1=1 and PHYSICS_FLAG= 1"); 
		if(StringUtils.isNotBlank(MyBankConf.getBankName())){
			sb.append(" and bank_name like  '%"+MyBankConf.getBankName().trim()+"%'");
		}
		fs= jdbcTemplate.query(sb.toString(),new MyBankConfMapper());
		 

		return fs;
	}
	 
	
	public int add(final MyBankConf u){
		 String sql = " INSERT INTO MY_BANK_CONF(id,bank_id,bank_name,stype,remark,content,excel_path,conf_path,fld_n1,fld_s1,excel_json,PHYSICS_FLAG,CREATE_TIME,UPDATE_TIME)"  
                 + " VALUES(SEQ_MY_BANK_CONF.nextval,?,?,?,?,?,?,?,?,?,?,1,sysdate,sysdate)";  
		
		 final String excelJson = u.getExcelJson()==null?"":u.getExcelJson();
		 final Reader clobReader = new StringReader(excelJson); // 将 text转成流形式  
         int  affectNum =  this.jdbcTemplate.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pStat) throws SQLException {
				 
				pStat.setInt(1,u.getBankId());  
                pStat.setString(2,u.getBankName());  
                pStat.setInt(3,u.getStype());  
                pStat.setString(4,u.getRemark());  
                pStat.setString(5,u.getContent());  
                pStat.setString(6,u.getExcelPath());  
                pStat.setString(7,u.getConfPath());  
                pStat.setInt(8,u.getFldn1());  
                pStat.setString(9,u.getFlds1());  
                pStat.setCharacterStream(10, clobReader, excelJson.length());
			}
		}); 
         if(clobReader!=null){
        	 try {
				clobReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
        return affectNum;
	}
 
	public int update(final MyBankConf u){
		 String sql = "update MY_BANK_CONF set  bank_id=?,bank_name=?,stype=?,remark=?,content=?,excel_path=?,conf_path=?,fld_n1=?,fld_s1=?,excel_json=? where id=? "  ;
               
		 final String excelJson = u.getExcelJson()==null?"":u.getExcelJson();
		 final Reader clobReader = new StringReader(excelJson); // 将 text转成流形式  
         int  affectNum =  this.jdbcTemplate.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement pStat) throws SQLException {
				 
				pStat.setInt(1,u.getBankId());  
                pStat.setString(2,u.getBankName());  
                pStat.setInt(3,u.getStype());  
                pStat.setString(4,u.getRemark());  
                pStat.setString(5,u.getContent());  
                pStat.setString(6,u.getExcelPath());  
                pStat.setString(7,u.getConfPath());  
                pStat.setInt(8,u.getFldn1());  
                pStat.setString(9,u.getFlds1());  
                pStat.setCharacterStream(10, clobReader, excelJson.length());
                pStat.setInt(11,u.getId()); 
			}
		}); 
         if(clobReader!=null){
        	 try {
				clobReader.close();
			} catch (IOException e) {
				 
				e.printStackTrace();
			}
         }
       return affectNum;
	}
   
	public MyBankConf findById(int id) {
		String sql="select * FROM MY_BANK_CONF WHERE id = ?";
		Object[] o={id};
		List<MyBankConf> fs = jdbcTemplate.query(sql,o,new MyBankConfMapper());
		if(fs!=null&&fs.size()>0){
			return fs.get(0);
		}
		return null;
	}
	
	public int delete(int id) {
		String sql="DELETE FROM MY_BANK_CONF WHERE id = ?";
		Object[] o={id};
		int[] argTypes={Types.INTEGER};
		int res=jdbcTemplate.update(sql, o, argTypes);
		return res;
	}
	 
	 
	 
}
