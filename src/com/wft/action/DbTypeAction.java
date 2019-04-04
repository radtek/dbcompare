package com.wft.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.wft.service.DbTypeService;
import com.wft.vo.DbTypeVo;

/**
 * @author admin
 * 各个银行部署数据库类型选择
 */
@Controller
@RequestMapping(value = "/dbType")
public class DbTypeAction {

	@Autowired
	private DbTypeService dbTypeService;
	
	// 查询所有基本表
	@RequestMapping(value = "/findAll")
	public void findAll(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		 
		List<DbTypeVo> u = dbTypeService.findAll();
		int total = u.size();
		map.put("rows", u);
		map.put("total", total);
		String str = JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
}
