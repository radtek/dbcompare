package com.wft.dao;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wft.dao.mapper.MyParamterMapper;
import com.wft.model.MyParamter;


@Repository("myParamterDao")
public class MyParamterDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	 

 
	public List<MyParamter> findAll() {
		 
		List<MyParamter> fs = jdbcTemplate.query("select * from MY_PARAMETER",new MyParamterMapper());;
 
		return fs;
	}
 
	 
	public int update(MyParamter u) {
		String sql="UPDATE MY_PARAMETER SET PARAMETER_VALUE = ? ,REMARK=? ,UPDATE_TIME=sysdate WHERE PARAMETER_NAME =?";
		Object[] o={u.getPval(),u.getRemark(),u.getPname()};
		int[] argTypes={Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
		int res=jdbcTemplate.update(sql, o, argTypes);
		return res;
	}
	 
	public MyParamter findById(String pname) {
		String sql="select * FROM MY_PARAMETER WHERE PARAMETER_NAME = ?";
		Object[] o={pname};
		List<MyParamter> fs = jdbcTemplate.query(sql,o,new MyParamterMapper());
		if(fs!=null&&fs.size()>0){
			return fs.get(0);
		}
		return null;
	}
	
	public int delete(String pname) {
		String sql="DELETE FROM MY_PARAMETER WHERE PARAMETER_NAME = ?";
		Object[] o={pname};
		int[] argTypes={Types.VARCHAR};
		int res=jdbcTemplate.update(sql, o, argTypes);
		return res;
	}
	 
	 
}
