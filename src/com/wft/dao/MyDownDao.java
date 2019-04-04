package com.wft.dao;

import java.sql.Types;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wft.dao.mapper.MyDownMapper;
import com.wft.model.MyDown;
import com.wft.util.CommonUtil;
import com.wft.vo.MyDownVo;


@Repository("myDownDao")
public class MyDownDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	 

 
	public List<MyDown> findAll(MyDownVo myDownVo) {
		 
		List<MyDown> fs = null;
		if(myDownVo==null){
			Object[] o = {CommonUtil.EABLE};
			fs= jdbcTemplate.query("select * from MY_DOWN where PHYSICS_FLAG = ? order by id desc ",o,new MyDownMapper());
		}else{
			int physicsFlag = myDownVo.getPhysicsFlag();
			String sechma = myDownVo.getSechma();
			StringBuffer sb = null;
			if(CommonUtil.ALL==physicsFlag){//查询所有
				 sb = new StringBuffer("select * from MY_DOWN  where 1=1  ");
			}else{
				
				 sb = new StringBuffer("select * from MY_DOWN  where 1=1 and PHYSICS_FLAG= "+physicsFlag);;
			}
			if(StringUtils.isNotBlank(sechma)){
				sb.append(" and   SECHMA like '%"+sechma+"%'");
			}
			 
			sb.append("  order by id desc ");
			System.out.println("MyDownDao findAll:"+sb.toString());
			fs= jdbcTemplate.query(sb.toString(),new MyDownMapper());
		}

		return fs;
	}
	 
	
	public int add(MyDown u){
		String sql="INSERT INTO MY_DOWN (ID, SECHMA,WAY, CONTENT, URL,IP,DOWN_TYPE,CREATE_TIME,UPDATE_TIME,PHYSICS_FLAG,RANGE,REMARK,REAL_RANGE) VALUES (SEQ_MY_DOWN.nextval, ?,?, ?, ?,?,?,?,?,?,?,?,?)";
		Object[] o={u.getSechma(),u.getWay(),u.getContent(),u.getUrl(),u.getIp(),u.getDownType(),u.getCreateTime(),u.getUpdateTime(),u.getPhysicsFlag(),u.getRange(),u.getRemark(),u.getRealRange()};
		int[] argTypes={Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.TIMESTAMP,Types.TIMESTAMP,Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
		int res=jdbcTemplate.update(sql, o, argTypes);
		return res;
	}
	 
	public int update(MyDown u) {
		String sql="UPDATE MY_DOWN SET PHYSICS_FLAG = ? WHERE id =?";
		Object[] o={u.getPhysicsFlag(),u.getId()};
		int[] argTypes={Types.INTEGER,Types.INTEGER};
		int res=jdbcTemplate.update(sql, o, argTypes);
		return res;
	}
	 
	public MyDown findById(int id) {
		String sql="select * FROM MY_DOWN WHERE id = ?";
		Object[] o={id};
		List<MyDown> fs = jdbcTemplate.query(sql,o,new MyDownMapper());
		if(fs!=null&&fs.size()>0){
			return fs.get(0);
		}
		return null;
	}
	
	public int delete(int id) {
		String sql="DELETE FROM MY_DOWN WHERE id = ?";
		Object[] o={id};
		int[] argTypes={Types.INTEGER};
		int res=jdbcTemplate.update(sql, o, argTypes);
		return res;
	}
	 
	 
	//获得比较库获取时最后一次最新的时间的记录
	public MyDown findBySechmaAndLastTime(String sechma) {
		String sql="select * from (select * FROM MY_DOWN WHERE SECHMA =? and PHYSICS_FLAG = 1 and DOWN_TYPE=1  ORDER BY CREATE_TIME DESC) where rownum=1 ";
		Object[] o={sechma};
		List<MyDown> fs = jdbcTemplate.query(sql,o,new MyDownMapper());
		if(fs!=null&&fs.size()>0){
			return fs.get(0);
		}
		return null;
	}
}
