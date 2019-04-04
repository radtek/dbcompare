package com.wft.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.wft.model.MyDown;

public class MyDownMapper implements RowMapper<MyDown> {

	@Override
	public MyDown mapRow(ResultSet rs, int row) throws SQLException {
		MyDown u=new MyDown();
		u.setId(rs.getInt("ID"));
		u.setSechma(rs.getString("SECHMA"));
		u.setContent(rs.getString("CONTENT"));
		u.setCreateTime(rs.getDate("CREATE_TIME"));
		u.setDownType(rs.getInt("DOWN_TYPE"));
		u.setIp(rs.getString("IP"));
		u.setPhysicsFlag(rs.getInt("PHYSICS_FLAG"));
		u.setUpdateTime(rs.getDate("UPDATE_TIME"));
		u.setUrl(rs.getString("URL"));
		u.setWay(rs.getString("WAY"));
		u.setRange(rs.getString("RANGE"));
		u.setRemark(rs.getString("REMARK"));
		u.setRealRange(rs.getString("REAL_RANGE"));
		return u;
	}

}
