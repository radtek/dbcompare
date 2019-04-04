package com.wft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wft.dao.MyBasicBankDao;
import com.wft.model.MybasicBank;


@Service("myBasicBankService")
@Transactional
public class MyBasicBankService {

	@Autowired
	private MyBasicBankDao myBasicBankDao;
	
	public List<MybasicBank> findAll(MybasicBank mybasicBank) {
		return myBasicBankDao.findAll(mybasicBank);
	}
	 
	
	public int add(MybasicBank u){
		return myBasicBankDao.add(u);
	}
	 
	public int update(MybasicBank u) {
		return myBasicBankDao.update(u);
	}
	 
	public MybasicBank findById(int id) {
		return myBasicBankDao.findById(id);
	}
	public int delete(int id) {
		return myBasicBankDao.delete(id);
	}
	
	//查询需要比较的银行
	public List<MybasicBank> findCHeck() {
		return myBasicBankDao.findCHeck();
	}
}
