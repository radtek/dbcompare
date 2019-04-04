package com.wft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wft.dao.MyParamterDao;
import com.wft.model.MyParamter;


@Service("myParamterService")
@Transactional
public class MyParamterService {

	@Autowired
	private MyParamterDao myParamterDao;
	
	public List<MyParamter> findAll() {
		return myParamterDao.findAll();
	}
	 
 
	 
	public int update(MyParamter u) {
		return myParamterDao.update(u);
	}
	 
	public MyParamter findById(String pname) {
		return myParamterDao.findById(pname);
	}
	public int delete(String pname) {
		return myParamterDao.delete(pname);
	}
	
	 
}
