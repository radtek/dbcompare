package com.wft.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.wft.model.MyDown;
import com.wft.service.MyDownService;
import com.wft.util.CommonUtil;
import com.wft.vo.MyDownVo;
import com.wft.vo.UserVo;

/**
 * @author admin
 * 标准库下载记录
 */
@Controller
@RequestMapping(value = "/myDown")
public class MyDownAction extends BaseAction{
	
	private final static Logger log = Logger.getLogger(MyDownAction.class);
	
	
	
	@Autowired
	private MyDownService myDownService;
	
	// 查询所有基本表
	@RequestMapping(value = "/findAll")
	public void findAll(HttpServletRequest request, HttpServletResponse response,MyDownVo myDownVo)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		UserVo userVo = (UserVo) request.getSession().getAttribute("user");
		if(userVo!=null&&"admin".equals(userVo.getUserName())){//超管查询所有
			myDownVo.setPhysicsFlag(CommonUtil.ALL);
		 }
		List<MyDown> u = myDownService.findAll(myDownVo);
		int total = u.size();
		map.put("rows", u);
		map.put("total", total);
		String str = JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}

		 //作废信息
		@RequestMapping(value = "/disable")
		public void disable(@RequestParam("id") Integer id,HttpServletRequest request, HttpServletResponse response ) throws Exception {
			log.info("id:"+id);
			MyDown myDown = myDownService.findById(id);
			if(myDown!=null){
				int physicsFlagNew = 0;
				int physicsFlag = myDown.getPhysicsFlag();
				physicsFlagNew = CommonUtil.EABLE == physicsFlag?CommonUtil.DISEABLE: CommonUtil.EABLE;
				myDown.setPhysicsFlag(physicsFlagNew);
				myDownService.update(myDown);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", "成功");
			map.put("flag", true);
			String str = JSONObject.toJSONString(map);
			log.info(str);
			response.getWriter().write(str);
		}
		
		// 获得比较库获取时最后一次最新的时间的记录
		@RequestMapping(value = "/findBySechmaAndLastTime")
		public void findBySechmaAndLastTime(HttpServletRequest request, HttpServletResponse response,MyDownVo myDownVo)
				throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
			String sechma = request.getParameter("sechma");
			MyDown u = myDownService.findBySechmaAndLastTime(sechma);
			map.put("rows", u);
			String str = JSONObject.toJSONString(map);
			response.getWriter().write(str);
		}

}
