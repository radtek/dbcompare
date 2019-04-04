package com.wft.appconf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wft.appconf.dao.MyBankConfDao;
import com.wft.appconf.dto.MyBankConf;


@Service("myBankConfService")
@Transactional
public class MyBankConfService {

	@Autowired
	private MyBankConfDao myBankConfDao;
	
	public List<MyBankConf> findAll(MyBankConf u) {
		return myBankConfDao.findAll(u);
	}
	 
	
	public int add(MyBankConf u){
		return myBankConfDao.add(u);
	}
	 
	public int update(MyBankConf u) {
		return myBankConfDao.update(u);
	}
	 
	public MyBankConf findById(int id) {
		return myBankConfDao.findById(id);
	}
	public int delete(int id) {
		return myBankConfDao.delete(id);
	}
	
	 
}
