package com.wft.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.wft.db.CacheUtil;
import com.wft.db.IgnoreTable;
import com.wft.model.MyParamter;
import com.wft.service.MyParamterService;

/**
 * @author admin
 * 系统参数表
 */
@Controller
@RequestMapping(value = "/myParamter")
public class MyParamterAction extends BaseAction{
private final static Logger log = Logger.getLogger(BasicTableAction.class);
	
	@Autowired
	private MyParamterService myParamterService;
	
	// 查询所有基本表
	@RequestMapping(value = "/findAll")
	public void findAll(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		List<MyParamter> u = myParamterService.findAll();
		int total = u.size();
		map.put("rows", u);
		map.put("total", total);
		String str = JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
	

	 //修改信息
	@RequestMapping(value = "/update")
	public void disable( HttpServletRequest request, HttpServletResponse response,MyParamter myParamter ) throws Exception {
	 
		MyParamter myParamterp = myParamterService.findById(myParamter.getPname());
		if(myParamterp!=null){
			myParamterp.setPval(myParamter.getPval());
			myParamterp.setRemark(myParamter.getRemark());
			myParamterp.setUpdateTime(new Date());
			myParamterService.update(myParamterp);
			if(IgnoreTable.paramMap.containsKey(myParamter.getPname())){
				//移除
				IgnoreTable.paramMap.put(myParamter.getPname(), myParamter.getPval());
				//清除所有信息
				CacheUtil.getInstance().clear();
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "成功");
		map.put("flag", true);
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
	}
	
	
	 //随机生成用户 登录
	@RequestMapping(value = "/gen")
	public void gen( HttpServletRequest request, HttpServletResponse response,MyParamter myParamter ) throws Exception {
	    String user = RandomStringUtils.randomAlphanumeric(5);
	    String pwd = RandomStringUtils.randomAlphanumeric(5);
	    CacheUtil.getInstance().set("users_"+user, pwd,CacheUtil.default_long_overdue_10h);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", user+"/"+pwd);
		map.put("flag", true);
		String str = JSONObject.toJSONString(map);
		log.info(str);
		response.getWriter().write(str);
	}
}
