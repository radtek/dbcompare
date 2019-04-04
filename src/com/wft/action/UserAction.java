package com.wft.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.wft.util.SystemMessage;
import com.wft.vo.UserVo;

/**
 * @author admin
 * 标准库下载记录
 */
@Controller
@RequestMapping(value = "/user")
public class UserAction extends BaseAction{
	
	private final static Logger log = Logger.getLogger(UserAction.class);
	@Autowired
	private MyParamterService myParamterService;
	public final static Map<String, String> userMpas = new HashMap<String, String>();
	static{
		String users = SystemMessage.getString("users");
		if(StringUtils.isNotBlank(users)){
			String[] userls = users.split(",");
			for(String user:userls){
				String[] usernames = user.split("@");
				userMpas.put(usernames[0], usernames[1]);
			}
		}else{
			userMpas.put("admin", "adminsql");
			userMpas.put("view", "view123");
		}
	
	}
	 
	@RequestMapping(value = "/login")
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserVo userVo = new UserVo();
		userVo.setUserName(StringUtils.trimToEmpty(request.getParameter("userName")));
		userVo.setPwd(StringUtils.trimToEmpty(request.getParameter("pwd")));
		log.info("usesrvo:"+userVo);
		Map<String, Object> map = new HashMap<String, Object>();
		 
		if(verify(userVo)){
			map.put("result", "登录成功");
			map.put("flag", true);
			userVo.setId(request.getSession().getId());
			request.getSession().setAttribute("user", userVo);
			//last_unified upgrade_time 最后一次上传时间
		    MyParamter upload_time = myParamterService.findById(MyParamter.last_upload_time);
		    MyParamter unified_time = myParamterService.findById(MyParamter.last_unified_upgrade_time);
		    MyParamter ignore_table_reg = myParamterService.findById(MyParamter.ignore_table_reg);
		    MyParamter need_table_reg = myParamterService.findById(MyParamter.need_table_reg);
		    //MY_PARAMETER参数表信息 登录时保存
		    IgnoreTable.paramMap.put(MyParamter.ignore_table_reg, ignore_table_reg!=null?ignore_table_reg.getPval():"");
		    IgnoreTable.paramMap.put(MyParamter.need_table_reg, need_table_reg!=null?need_table_reg.getPval():"");
		    
			request.getSession().setAttribute("upload_time", upload_time);
			request.getSession().setAttribute("unified_time", unified_time);
			//保存基本标准库信息
			request.getSession().setAttribute("bdriverClassName", SystemMessage.getString("driverClassName"));
			request.getSession().setAttribute("burl", SystemMessage.getString("url"));
			request.getSession().setAttribute("busername", SystemMessage.getString("username"));
			request.getSession().setAttribute("bpassword", SystemMessage.getString("password"));
			//websocket_port
			request.getSession().setAttribute("websocket_port", SystemMessage.getString("websocket_port"));
		}else{
			map.put("result", "用户名或者密码错误");
			map.put("flag", false);
		}
		
		String str = JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}

	
	@RequestMapping(value = "/login_out")
	public void loginOut(HttpServletRequest request, HttpServletResponse response,UserVo userVo)throws Exception {
	  Map<String, Object> map = new HashMap<String, Object>();
		request.getSession().invalidate();
		map.put("result", "登出成功");
		map.put("flag", true);
		String str = JSONObject.toJSONString(map);
		response.getWriter().write(str);
	}
	
	private boolean verify(UserVo userVo ){
		boolean flag = false;
		 
		String cachepwd = (String) CacheUtil.getInstance().get("users_"+userVo.getUserName());
		if(StringUtils.isNotBlank(cachepwd)){
			log.info("从缓存cachepwd判断:"+cachepwd);
			if(cachepwd.equals(userVo.getPwd())){
				flag = true;
				return flag;
			}
		} 
		String pwd = userMpas.get(userVo.getUserName());
		if(StringUtils.isNotBlank(pwd)){
			if(pwd.equals(userVo.getPwd())){
				flag = true;
			}
		} 
		return flag;
	 
	}
}
