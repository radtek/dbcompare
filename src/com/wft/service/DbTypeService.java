package com.wft.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wft.vo.DbTypeVo;

/**
 * @author admin
 * 各个银行部署数据库类型选择
 */
@Service("dbTypeService")
@Transactional
public class DbTypeService {

	
	public    DbTypeVo  getDbTypeVo(String type){
		List<DbTypeVo> vos = findAll();
		DbTypeVo vo = null;
		for(DbTypeVo v:vos){
			if(type.equals(v.getType())){
				vo = v;
				break;
			}
		}
	
		return vo;
	}
	public   List<DbTypeVo> findAll(){
		List<DbTypeVo> vos = new ArrayList<DbTypeVo>();
		URL url =  DbTypeService.class.getClassLoader().getResource("dbType.json");
	    
		File file = /*new File("D:/公司资料/test/wft/src/dbType.json");*/new File(url.getFile());
		try {
			String jsons = FileUtils.readFileToString(file);
			 JSONArray array =  JSONArray.parseArray(jsons);
			 for(int i=0,len=array.size();i<len;i++){
				 JSONObject json = array.getJSONObject(i);
				 vos.add(JSONObject.parseObject(json.toString(),DbTypeVo.class));
			 }
			System.out.println(jsons);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vos;
	}
	
	
	public static void main(String[] args) {
		System.out.println(new DbTypeService().findAll().get(0));
	}
}
