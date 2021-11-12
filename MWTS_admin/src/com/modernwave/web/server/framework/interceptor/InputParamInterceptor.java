package com.modernwave.web.server.framework.interceptor;


import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.modernwave.web.server.framework.core.BaseController;
import com.modernwave.web.server.model.UserVo;

public class InputParamInterceptor extends HandlerInterceptorAdapter{
	
	private Logger log = Logger.getLogger(this.getClass());
	
	private String[] skipUrls = new String[0];
	
	public void setSkipUrls(String[] skipUrls) {
		this.skipUrls = skipUrls;
	}
	
	public String[] getSkipUrls() {
		return skipUrls;
	}
	
/*	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		log.info("========== HttpServletRequest request parameter print start	==========");
		Enumeration e = request.getParameterNames();
		while(e.hasMoreElements()){
			String key = e.nextElement().toString();
			String value = request.getParameter(key);
			log.debug("key["+key + "] value["+value+"]");
			//super.put(key, value);
		}
		log.info("========== HttpServletRequest request parameter print end	==========");
		
		//if(true)
		//	return true;
		
		String requestUrl = request.getServletPath();
		//log.debug("session id["+loginId+"]");
//		log.debug("session id["+loginId+"]requestUrl["+requestUrl+"]?["+StringUtils.equals(requestUrl, "/mwts/login.do")+"]");
		for (int i = 0; i < skipUrls.length; i++) {
			if(StringUtils.equals(requestUrl, skipUrls[i]))
				return true;	
		}
		
		UserVo userVo = (UserVo) request.getSession().getAttribute(BaseController.SESSION_USER);
		
//		UserVo userVo = new UserVo();
//		userVo.setUserId("chanwook3");
//		userVo.setUserName("������");
//		HttpSession session = request.getSession();
//		session.setAttribute(BaseController.SESSION_USER, userVo);
		
		
		
		
		if(userVo == null || StringUtils.isEmpty(userVo.getUserId()) ){
			response.sendRedirect("/MWTS/mwts/login.do");
			return false;
		}else{
			log.info("session id ["+userVo.getUserId()+"]");
			return true; 
		}
	}*/
	
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex){
		try {
			
			super.afterCompletion(request, response, handler, ex);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
