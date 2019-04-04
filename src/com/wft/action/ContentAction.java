package com.wft.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.wft.action.BaseAction;
import com.wft.content.dao.SysOrgParameterDao;
import com.wft.content.dao.SysParameterDao;
import com.wft.content.dto.CheckType;
import com.wft.content.dto.SysOrgParameter;
import com.wft.content.dto.SysParameter;
import com.wft.content.freemarker.GenerateContentSQL;
import com.wft.content.service.MyArrayList;
import com.wft.content.service.SysOrgParameterService;
import com.wft.content.service.SysParameterService;
import com.wft.db.Column;
import com.wft.db.DBTempory;

/**
 * @author admin
 * 内容比较管理
 */
@Controller
@RequestMapping(value = "/content")
public class ContentAction extends BaseAction{
	 
	private final static Logger log = Logger.getLogger(ContentAction.class);
		
	//	====================比较库====CMS_ORG_PARAMETER=================================
	
	//查询所有基本表与库比较差异系统参数表CMS_SYS_PARAMETER
	@RequestMapping(value = "/sysParameter")
	public void findSysParameter(HttpServletRequest request,HttpServletResponse response)throws Exception{

		Map<String, Object> map=new HashMap<String, Object>();
		try {
			//基础库
			String url= request.getParameter("url") ;
			url = "jdbc:oracle:thin:@"+url;
			String pwd= request.getParameter("pwd") ;
			String name= request.getParameter("name") ;
			String schame= request.getParameter("schame") ;
			 
			String url1= request.getParameter("url1") ;
			url1 = "jdbc:oracle:thin:@"+url1;
			String pwd1= request.getParameter("pwd1") ;
			String name1= request.getParameter("name1") ;
			String schame1= request.getParameter("schame1") ;
			//比较库
			DBTempory tstandLs = new DBTempory(url,name,pwd,schame);
			log.info("tstandLs:"+tstandLs);
			DBTempory tstandnoLs = new DBTempory(url1,name1,pwd1,schame1);
			log.info("tstandnoLs:"+tstandnoLs);
			MyArrayList<SysParameter> standLs  = SysParameterDao.getDatas(tstandLs, CheckType.CAN_KAO_DATABASE);
			MyArrayList<SysParameter> standnoLs  = SysParameterDao.getDatas(tstandnoLs, CheckType.NO_CAN_KAO_DATABASE);
			List<SysParameter> ls = SysParameterService.getDatas(standLs,standnoLs);
			
			tstandLs.close();
			tstandnoLs.close();
			
			map.put("rows", ls);
			map.put("total", ls.size());
			map.put("flag", true);
		} catch (Exception e) {
			map.put("result", e.getMessage());
			map.put("flag", false);
		}
		

		String str=JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
	
	
//	====================比较库==CMS_ORG_PARAMETER_CONF===================================
	
	//查询所有基本表与库比较差异系统参数表CMS_SYS_PARAMETER_conf
	@RequestMapping(value = "/sysorgParameter")
	public void findSysorgParameter(HttpServletRequest request,HttpServletResponse response)throws Exception{

		Map<String, Object> map=new HashMap<String, Object>();
		try {
			//基础库
			String url= request.getParameter("url") ;
			url = "jdbc:oracle:thin:@"+url;
			String pwd= request.getParameter("pwd") ;
			String name= request.getParameter("name") ;
			String schame= request.getParameter("schame") ;
			 
			String url1= request.getParameter("url1") ;
			url1 = "jdbc:oracle:thin:@"+url1;
			String pwd1= request.getParameter("pwd1") ;
			String name1= request.getParameter("name1") ;
			String schame1= request.getParameter("schame1") ;
			//比较库
			DBTempory tstandLs = new DBTempory(url,name,pwd,schame);
			log.info("tstandLs:"+tstandLs);
			DBTempory tstandnoLs = new DBTempory(url1,name1,pwd1,schame1);
			log.info("tstandnoLs:"+tstandnoLs);
			MyArrayList<SysOrgParameter> standLs  = SysOrgParameterDao.getDatas(tstandLs, CheckType.CAN_KAO_DATABASE);
			MyArrayList<SysOrgParameter> standnoLs  = SysOrgParameterDao.getDatas(tstandnoLs, CheckType.NO_CAN_KAO_DATABASE);
			List<SysOrgParameter> ls = SysOrgParameterService.getDatas(standLs,standnoLs);
			
			tstandLs.close();
			tstandnoLs.close();
			

			map.put("rows", ls);
			map.put("total", ls.size());
			map.put("flag", true);
		} catch (Exception e) {
			map.put("result", e.getMessage());
			map.put("flag", false);
		}
		

		String str=JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
	//查询某一基本表与库比较差异自动生成脚本临时系统参数表CMS_SYS_PARAMETERddl
	@RequestMapping(value = "/generateSysParameterScript")
	public void generateTempDDLScript(HttpServletRequest request,HttpServletResponse response)throws Exception{
 
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			//基础库
			String url= request.getParameter("url") ;
			url = "jdbc:oracle:thin:@"+url;
			String pwd= request.getParameter("pwd") ;
			String name= request.getParameter("name") ;
			String schame= request.getParameter("schame") ;
			 
			String url1= request.getParameter("url1") ;
			url1 = "jdbc:oracle:thin:@"+url1;
			String pwd1= request.getParameter("pwd1") ;
			String name1= request.getParameter("name1") ;
			String schame1= request.getParameter("schame1") ;
			//比较库
			DBTempory tstandLs = new DBTempory(url,name,pwd,schame);
			DBTempory tstandnoLs = new DBTempory(url1,name1,pwd1,schame1);
			StringBuffer sqlBuffer = new StringBuffer("");
			//CMS_ORG_PARAMETER
			MyArrayList<SysParameter> standLs  = SysParameterDao.getDatas(tstandLs, CheckType.CAN_KAO_DATABASE);
			MyArrayList<SysParameter> standnoLs  = SysParameterDao.getDatas(tstandnoLs, CheckType.NO_CAN_KAO_DATABASE);
		
			List<SysParameter> ls = SysParameterService.getDatas(standLs,standnoLs);
			 Map<String,Object> freemap = new HashMap<String,Object>();
			 freemap.put("sysParameters", ls);
			
			sqlBuffer.append( GenerateContentSQL.getSql("sysparameter.ftl",freemap));
			log.info("sqlBuffer:"+sqlBuffer);
			
			 
			//CMS_ORG_PARAMETER_CONF
			MyArrayList<SysOrgParameter> orgstandLs  = SysOrgParameterDao.getDatas(tstandLs, CheckType.CAN_KAO_DATABASE);
			MyArrayList<SysOrgParameter> orgstandnoLs  = SysOrgParameterDao.getDatas(tstandnoLs, CheckType.NO_CAN_KAO_DATABASE);
			List<SysOrgParameter> orgls = SysOrgParameterService.getDatas(orgstandLs,orgstandnoLs);
			 Map<String,Object> orgfreemap = new HashMap<String,Object>();
			 orgfreemap.put("sysorgParameters", orgls);
			 
			sqlBuffer.append( GenerateContentSQL.getSql("sysorgparameter.ftl",orgfreemap));
			
			tstandLs.close();
			tstandnoLs.close();
			
			map.put("result", sqlBuffer.toString());
			map.put("flag", true);
		} catch (Exception e) {
			map.put("result", e.getMessage());
			map.put("flag", false);
		}
		

		String str=JSONObject.toJSONString(map);
		response.getWriter().write(str);
	  
	}
		
	
	//查询某一基本表所有字段
	@RequestMapping(value = "/findAllCloumns")
	public void findAllCloumns(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String table= request.getParameter("table") ;
		//基础库
		String url= request.getParameter("url") ;
		url = "jdbc:oracle:thin:@"+url;
		String pwd= request.getParameter("pwd") ;
		String name= request.getParameter("name") ;
		String schame= request.getParameter("schame") ;
		Map<String, Object> map=new HashMap<String, Object>();
		DBTempory db = new DBTempory(url,name,pwd,schame);
		List<Column> u= db.sysoutOracleTCloumns(table.toUpperCase(),schame);
		db.close();
		int total=u.size();
		map.put("rows", u);
		map.put("total", total);
		String str=JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
	
	//查询某一基本表所有的数据
	@RequestMapping(value = "/findAlldatas")
	public void findAlldatas(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String table= request.getParameter("table") ;
		//基础库
		String url= request.getParameter("url") ;
		url = "jdbc:oracle:thin:@"+url;
		String pwd= request.getParameter("pwd") ;
		String name= request.getParameter("name") ;
		String schame= request.getParameter("schame") ;
		Map<String, Object> map=new HashMap<String, Object>();
		DBTempory db = new DBTempory(url,name,pwd,schame);
	    String sql="select * from "   +schame+ "."+table.toUpperCase() +" where PHYSICS_FLAG=1";
		List<Map<String,String>>  u= db.getDatasByTable(sql);
		db.close();
		int total=u.size();
		map.put("rows", u);
		map.put("total", total);
		String str=JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
			
}
