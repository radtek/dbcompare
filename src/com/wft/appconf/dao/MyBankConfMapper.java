package com.wft.appconf.dao;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.sql.CLOB;

import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.core.RowMapper;

import com.wft.appconf.dto.MyBankConf;

public class MyBankConfMapper implements RowMapper<MyBankConf> {
 
	@Override
	public MyBankConf mapRow(ResultSet rs, int row) throws SQLException {
		MyBankConf u=new MyBankConf();
		u.setId(rs.getInt("ID"));
		u.setBankId(rs.getInt("bank_id"));
		u.setBankName(rs.getString("bank_name"));
		u.setConfPath(rs.getString("conf_path"));
		u.setContent(rs.getString("content"));
		CLOB clob   = (CLOB) rs.getClob("excel_json"); // 获得CLOB字段str  
         // 注释： 用 rs.getString("str")无法得到 数据 ，返回的 是 NULL;  
	    if(clob!=null){
	    	String excelJson = ClobToString(clob);
	    	u.setExcelJson(excelJson);
	    }
		
	
		u.setExcelPath(rs.getString("excel_path"));
		u.setFldn1(rs.getInt("fld_n1"));
		u.setFlds1(rs.getString("fld_s1"));
		 
		u.setRemark(rs.getString("remark"));
		u.setStype(rs.getInt("stype"));
	 
		
		 u.setPhysicsFlag(rs.getInt("PHYSICS_FLAG")); 
		 u.setUpdateTime(rs.getDate("UPDATE_TIME"));
		 u.setCreateTime(rs.getDate("CREATE_TIME"));
		  
		return u;
	}
	
	

	// 将字CLOB转成STRING类型  
    
    public static String ClobToString(CLOB clob)  {  
    	String reString = ""; 
    	 Reader is = null;
    	 BufferedReader br = null;
    	try {
        	 
             is = clob.getCharacterStream();// 得到流  
             br = new BufferedReader(is);  
            String s = br.readLine();  
            StringBuffer sb = new StringBuffer();  
            // 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING  
            while (s != null) {  
                sb.append(s);  
                s = br.readLine();  
            }  
            reString = sb.toString();  
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(br);
			IOUtils.closeQuietly(is);
		}
    	
        return reString;  
    }    

}
