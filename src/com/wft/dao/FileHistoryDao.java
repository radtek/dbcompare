package com.wft.dao;

import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wft.dao.mapper.FileHistoryMapper;
import com.wft.model.FileHistory;
import com.wft.util.CommonUtil;
import com.wft.vo.FileHistoryVo;


@Repository("fileHistoryDao")
public class FileHistoryDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	 

 
	public List<FileHistory> findAll(FileHistoryVo fileHistoryVo) {
		List<FileHistory> fs = null;
		if(fileHistoryVo==null){
			Object[] o = {CommonUtil.EABLE};
			fs= jdbcTemplate.query("select * from MY_FILE_HISTORY where PHYSICS_FLAG = ? order by file_type, CREATE_TIME desc ",o,new FileHistoryMapper());
		}else{
			 String fileType = fileHistoryVo.getFileType();
			 String realName = fileHistoryVo.getRealName();
			Date beginTime = fileHistoryVo.getBeginTime();
			Date endTime = fileHistoryVo.getEndTime();
			int physicsFlag = fileHistoryVo.getPhysicsFlag();
			StringBuffer sb = null;
			Object[] o=new Object[]{};
			if(CommonUtil.ALL==physicsFlag){//查询所有
				 sb = new StringBuffer("select * from MY_FILE_HISTORY  where 1=1  ");
			}else{
				
				 sb = new StringBuffer("select * from MY_FILE_HISTORY  where 1=1 and PHYSICS_FLAG= "+physicsFlag);
			}
			
			if(StringUtils.isNotBlank(realName)){
				sb.append(" and   REAL_NAME like '%"+realName+"%'");
			}
			List<String> realNameLists = fileHistoryVo.getRealNameLists();
			if(CollectionUtils.isNotEmpty(realNameLists)){
				sb.append("  and (");
				List<String> ls = fileHistoryVo.getRealNameLists() ;
				for(int i=0,len=ls.size();i<len;i++){
					
					if(i==len-1){
						sb.append("     REAL_NAME = '"+ls.get(i)+"'   ");
					}else{
						sb.append("     REAL_NAME = '"+ls.get(i)+"' or ");
					}
				}
				 
				sb.append(" ) ");
			}else{//如果查询不到
				if(realNameLists!=null&&realNameLists.size()==0){
					sb.append("  and 1=2 ");
				}
				
			}
			if(StringUtils.isNotBlank(fileType)){
				sb.append(" and   FILE_TYPE ='"+fileType+"'");
			}
			if(beginTime!=null&&endTime!=null){
				o=new Object[]{beginTime,endTime};
				if("create".equalsIgnoreCase(fileHistoryVo.getTimeType())){
					sb.append(" and CREATE_TIME>=? and CREATE_TIME<=?");
				}else{
					sb.append(" and UPDATE_TIME>=? and UPDATE_TIME<=?");
				}
			
			}
			sb.append("  order by file_type, CREATE_TIME desc");
			System.out.println("FileHistoryDao findAll:"+sb.toString());
			fs= jdbcTemplate.query(sb.toString(),o,new FileHistoryMapper());
		}
		
		return fs;
	}
	 
	
	public int add(FileHistory u){
		String sql="INSERT INTO MY_FILE_HISTORY (ID, FILE_TYPE, REAL_NAME, SERVICE_NAME,SERVICE_URL,MD5,CREATE_TIME,UPDATE_TIME,STATUS,PHYSICS_FLAG,CHECK_ON,APPEND_BASIC) VALUES (SEQ_MY_FILE_HISTORY.nextval, ?, ?, ?,?,?,?,?,?,?,?,?)";
		Object[] o={u.getFileType(),u.getRealName(),u.getServiceName(),u.getServiceUrl(),u.getMd5(),u.getCreateTime(),u.getUpdateTime(),u.getStatus(),u.getPhysicsFlag(),u.getCheckOn(),u.getAppendBasic()};
		int[] argTypes={Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.DATE,Types.DATE,Types.INTEGER,Types.INTEGER,Types.INTEGER,Types.INTEGER};
		int res=jdbcTemplate.update(sql, o, argTypes);
		return res;
	}
	 
	public int update(FileHistory u) {
		String sql="UPDATE MY_FILE_HISTORY SET PHYSICS_FLAG = ?,STATUS =?,CREATE_TIME=?, UPDATE_TIME=? WHERE id =?";
		Object[] o={u.getPhysicsFlag(),u.getStatus(),u.getCreateTime(),u.getUpdateTime(),u.getId()};
		int[] argTypes={Types.INTEGER,Types.INTEGER ,Types.DATE,Types.DATE,Types.INTEGER};
		int res=jdbcTemplate.update(sql, o, argTypes);
		return res;
	}
	 
	public FileHistory findById(int id) {
		String sql="select * FROM MY_FILE_HISTORY WHERE id = ?";
		Object[] o={id};
		List<FileHistory> fs = jdbcTemplate.query(sql,o,new FileHistoryMapper());
		if(fs!=null&&fs.size()>0){
			return fs.get(0);
		}
		return null;
	}
	
	public int delete(int id) {
		String sql="DELETE FROM MY_FILE_HISTORY WHERE id = ?";
		Object[] o={id};
		int[] argTypes={Types.INTEGER};
		int res=jdbcTemplate.update(sql, o, argTypes);
		return res;
	}
	 
	public int countByMD5(String md5,String realName) {
		Object[] o=new Object[]{md5,realName};
		return jdbcTemplate.queryForObject("select count(1) from MY_FILE_HISTORY where MD5=? or REAL_NAME=?",o,Integer.class);
	}
	
	public List<FileHistory> findAll(Map<String, Object> map) {
		Object[] o=new Object[]{};
		List<FileHistory> fs= jdbcTemplate.query("select * from MY_FILE_HISTORY where PHYSICS_FLAG = 1 and ID in   ("+map.get("ids")+") order by id",o,new FileHistoryMapper());
		return fs;
	}
	 
	
	public List<FileHistory> findAllByCreateTime(Date strDate,Date endDate) {
		
		Object[] o=new Object[]{strDate,endDate};
		List<FileHistory> fs= jdbcTemplate.query("select * from MY_FILE_HISTORY where PHYSICS_FLAG = 1 and CREATE_TIME>=? and CREATE_TIME<=? order by id",o,new FileHistoryMapper());
		return fs;
	}
	
	public List<FileHistory> findAllByUpdateTime(Date strDate,Date endDate) {
		
		Object[] o=new Object[]{strDate,endDate};
		List<FileHistory> fs= jdbcTemplate.query("select * from MY_FILE_HISTORY where PHYSICS_FLAG = 1 and UPDATE_TIME>=? and UPDATE_TIME<=? order by id",o,new FileHistoryMapper());
		return fs;
	}
}
