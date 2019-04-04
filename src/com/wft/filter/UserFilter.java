package com.wft.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wft.action.UserAction;
import com.wft.db.CacheUtil;
import com.wft.vo.UserVo;
 


/**
 * 用户过滤，没有登录后台则跳转到登录页面
 * 
 */
public class UserFilter implements Filter {
	private final static Logger log = Logger.getLogger(UserFilter.class);
	

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse res = (HttpServletResponse) arg1;
		UserVo user = (UserVo) req.getSession().getAttribute("user");
	 
		String url = req.getRequestURL().toString();
		//String ip = req.getRemoteAddr();
		 
		
		if (user == null) {// 没登陆，跳到登陆页
			if (url.indexOf("user") > -1||url.indexOf("login") > -1) {//保障登陆页面无需验证
				arg2.doFilter(req, res);
				return;
			}
			String redirect= "/login.jsp";
			 
			res.sendRedirect(req.getContextPath() + redirect);
			return;
		}
		String cachepwd = (String) CacheUtil.getInstance().get("users_"+user.getUserName());
		if(!UserAction.userMpas.containsKey(user.getUserName())&&StringUtils.isBlank(cachepwd)){
			log.info("从缓存UserFilter 失效重新登录:"+user.getUserName());
			req.getSession().invalidate();
			String redirect= "/login.jsp"; 
			res.sendRedirect(req.getContextPath() + redirect);
			return;
		}
		

		arg2.doFilter(req, res);
	}

	
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
