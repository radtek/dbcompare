package com.wft.tag;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;

import com.wft.vo.UserVo;

/**
 * @author admin 权限tag
 */
public class PermissionTag extends BodyTagSupport {
	
	private final static Logger log = Logger.getLogger(PermissionTag.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PageContext pageContext;

    @Override
    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }
    
	@Override
    public int doStartTag() throws JspException {
		 HttpSession session = pageContext.getSession(); 
		 UserVo userVo = (UserVo) session.getAttribute("user");
		 log.info("PermissionTag:user:"+userVo);
		 //只有超管才可以查看
		 if(userVo!=null&&"admin".equals(userVo.getUserName())){
			 log.info("表示输出标签体内容");
			   return Tag.EVAL_BODY_INCLUDE;
		 }
        //返回BodyTag.EVAL_BODY_BUFFERED，表示输出标签体内容
        //返回Tag.SKIP_BODY,表示不输出内容
     
        return Tag.SKIP_BODY;
    }
}
