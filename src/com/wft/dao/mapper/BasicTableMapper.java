package com.wft.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.wft.model.MyBasicTable;

public class BasicTableMapper implements RowMapper<MyBasicTable> {

	@Override
	public MyBasicTable mapRow(ResultSet rs, int row) throws SQLException {
		MyBasicTable u=new MyBasicTable();
		u.setId(rs.getInt("ID"));
		u.setFileType(rs.getString("FILE_TYPE"));
		u.setRealName(rs.getString("REAL_NAME"));
		 
		u.setServiceUrl(rs.getString("SERVICE_URL")); 
		u.setIp(rs.getString("IP"));
		u.setCreateTime(rs.getDate("CREATE_TIME"));
		u.setUpdateTime(rs.getDate("UPDATE_TIME"));
		u.setOrders(rs.getInt("ORDERS"));
		
		return u;
	}

}
