package com.wft.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wft.dao.FileHistoryDao;
import com.wft.model.FileHistory;
import com.wft.util.DateUtils;
import com.wft.vo.FileHistoryVo;


@Service("fileHistoryService")
@Transactional
public class FileHistoryService {

	@Autowired
	private FileHistoryDao fileHistoryDao;
	
	public List<FileHistory> findAll(FileHistoryVo fileHistoryVo) {
		return fileHistoryDao.findAll(fileHistoryVo);
	}
	 
	
	public int add(FileHistory u){
		return fileHistoryDao.add(u);
	}
	 
	public int update(FileHistory u) {
		return fileHistoryDao.update(u);
	}
	 
	public FileHistory findById(int id) {
		return fileHistoryDao.findById(id);
	}
	public int delete(int id) {
		return fileHistoryDao.delete(id);
	}
	
	public boolean findByMD5Name(String md5,String realName) { 
		int count  = fileHistoryDao.countByMD5(md5,realName);
		return count>0;
	}
	
	public List<FileHistory> findAll(Map<String, Object> map) {
		return fileHistoryDao.findAll(map);
	}
	
	//通过创建时间查询
	public List<FileHistory> findAllByCreateTime(Date strDate,Date endDate) {
		strDate = DateUtils.getStartTime(strDate);
		endDate = DateUtils.getEndTime(endDate);
		return fileHistoryDao.findAllByCreateTime(strDate,endDate);
	}
	
	//通过更新时间
	public List<FileHistory> findAllByUpdateTime(Date strDate,Date endDate) {
		strDate = DateUtils.getStartTime(strDate);
		endDate = DateUtils.getEndTime(endDate);
		return fileHistoryDao.findAllByUpdateTime(strDate,endDate);
	}
}
