package com.wft.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.wft.model.FileHistory;

public class FileHistoryMapper implements RowMapper<FileHistory> {

	@Override
	public FileHistory mapRow(ResultSet rs, int row) throws SQLException {
		FileHistory u=new FileHistory();
		u.setId(rs.getInt("ID"));
		u.setFileType(rs.getString("FILE_TYPE"));
		u.setRealName(rs.getString("REAL_NAME"));
		u.setServiceName(rs.getString("SERVICE_NAME"));
		u.setServiceUrl(rs.getString("SERVICE_URL")); 
		u.setMd5(rs.getString("MD5"));
		u.setCreateTime(rs.getDate("CREATE_TIME"));
		u.setUpdateTime(rs.getDate("UPDATE_TIME"));
		u.setStatus(rs.getInt("STATUS"));
		u.setPhysicsFlag(rs.getInt("PHYSICS_FLAG"));
		u.setAppendBasic(rs.getInt("CHECK_ON"));
		u.setCheckOn(rs.getInt("APPEND_BASIC"));
		return u;
	}

}
