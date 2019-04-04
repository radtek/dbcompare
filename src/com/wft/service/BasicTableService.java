package com.wft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wft.dao.BasicTableDao;
import com.wft.model.MyBasicTable;
import com.wft.util.CommonUtil;


/**
 * @author admin
 *
 */
@Service("basicTableService")
@Transactional
public class BasicTableService {

	@Autowired
	private BasicTableDao basicTableDao;
	
	public List<MyBasicTable> findAll() {
		return basicTableDao.findAll(CommonUtil.EABLE);
	}
	 
	
	public int add(MyBasicTable u){
		return basicTableDao.add(u);
	}
	 
	public int update(MyBasicTable u) {
		return basicTableDao.update(u);
	}
	 
	public MyBasicTable findById(int id) {
		return basicTableDao.findById(id);
	}
	 
	
	 
}
