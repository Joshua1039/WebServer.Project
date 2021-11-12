package com.modernwave.web.server.framework.core;

public class BaseAjaxException extends Exception{

	public BaseAjaxException(String errorMessage){
		super(errorMessage);
	}
	
	public BaseAjaxException(String errorMessage, Throwable e){
		super(errorMessage, e);
	}
	
	public BaseAjaxException(Throwable e) throws BaseAjaxException{
		e.printStackTrace();
		throw new BaseAjaxException(e.getMessage());
	}
}
