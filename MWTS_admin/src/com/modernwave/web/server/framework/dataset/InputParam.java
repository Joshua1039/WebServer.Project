package com.modernwave.web.server.framework.dataset;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class InputParam extends IOData{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Logger log = Logger.getLogger(InputParam.class);
	
	public void setParameter(HttpServletRequest req){
		//log.info("========== HttpServletRequest request parameter print start	==========");
		@SuppressWarnings("rawtypes")
		Enumeration e = req.getParameterNames();
		while(e.hasMoreElements()){
			String key = e.nextElement().toString();
			String value = req.getParameter(key);
			//log.debug("key["+key + "] value["+value+"]");
			super.put(key, value);
		}
		//log.info("========== HttpServletRequest request parameter print end	==========");
	}
	

}
