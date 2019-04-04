package com.wft.dao;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wft.dao.mapper.BasicTableMapper;
import com.wft.model.MyBasicTable;


@Repository("basicTableDao")
public class BasicTableDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	 

 
	public List<MyBasicTable> findAll(int physicsFlag) {
		Object[] o = {physicsFlag};
		List<MyBasicTable> fs= jdbcTemplate.query("select * from MY_BASIC_TABLE where PHYSICS_FLAG = ? order by ORDERS ",o,new BasicTableMapper());
		return fs;
	}
	 
	
	public int add(MyBasicTable u){
		String sql="INSERT INTO MY_BASIC_TABLE (ID, FILE_TYPE, REAL_NAME,SERVICE_URL,IP,CREATE_TIME,UPDATE_TIME,ORDERS,PHYSICS_FLAG) VALUES (SEQ_MY_BASIC_TABLE.nextval, ?, ?,?,?,?,?,?,?)";
		Object[] o={u.getFileType(),u.getRealName(),u.getServiceUrl(),u.getIp(),u.getCreateTime(),u.getUpdateTime(),u.getOrders(),u.getPhysicsFlag()};
		int[] argTypes={Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.DATE,Types.DATE,Types.INTEGER,Types.INTEGER};
		int res=jdbcTemplate.update(sql, o, argTypes);
		return res;
	}
	 
	public int update(MyBasicTable u) {
		String sql="UPDATE MY_BASIC_TABLE SET PHYSICS_FLAG = ?,ORDERS =?,REAL_NAME=?,SERVICE_URL=?, UPDATE_TIME = sysdate WHERE id =?";
		Object[] o={u.getPhysicsFlag(),u.getOrders(),u.getRealName(),u.getServiceUrl(),u.getId()};
		int[] argTypes={Types.INTEGER,Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		int res=jdbcTemplate.update(sql, o, argTypes);
		return res;
	}
	 
	public MyBasicTable findById(int id) {
		String sql="select * FROM MY_BASIC_TABLE WHERE id = ?";
		Object[] o={id};
		List<MyBasicTable> fs = jdbcTemplate.query(sql,o,new BasicTableMapper());
		if(fs!=null&&fs.size()>0){
			return fs.get(0);
		}
		return null;
	}
	
	 
}
