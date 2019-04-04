package com.wft.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.wft.model.MyParamter;

public class MyParamterMapper implements RowMapper<MyParamter> {

	@Override
	public MyParamter mapRow(ResultSet rs, int row) throws SQLException {
		MyParamter u=new MyParamter();
		 
		 
		u.setCreateTime(rs.getDate("CREATE_TIME"));
		 
		u.setUpdateTime(rs.getDate("UPDATE_TIME"));
		 u.setPname(rs.getString("PARAMETER_NAME"));
		 u.setPval(rs.getString("PARAMETER_VALUE"));
	 
		u.setRemark(rs.getString("REMARK"));
	 
		return u;
	}

}
