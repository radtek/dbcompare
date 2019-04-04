package com.wft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wft.dao.MyDownDao;
import com.wft.model.MyDown;
import com.wft.vo.MyDownVo;


@Service("myDownService")
@Transactional
public class MyDownService {

	@Autowired
	private MyDownDao myDownDao;
	
	public List<MyDown> findAll(MyDownVo myDownVo) {
		return myDownDao.findAll(myDownVo);
	}
	 
	
	public int add(MyDown u){
		return myDownDao.add(u);
	}
	 
	public int update(MyDown u) {
		return myDownDao.update(u);
	}
	 
	public MyDown findById(int id) {
		return myDownDao.findById(id);
	}
	public int delete(int id) {
		return myDownDao.delete(id);
	}
	//获得比较库获取时最后一次最新的时间的记录
	public MyDown findBySechmaAndLastTime(String sechma) {
		return myDownDao.findBySechmaAndLastTime(sechma);
	}
	 
}
