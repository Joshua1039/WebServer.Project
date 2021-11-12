package com.modernwave.web.server.framework.core;

public class BaseDhtmlxException extends Exception{
	
	public BaseDhtmlxException(String errorMessage){
		super(errorMessage);
	}
	
	public BaseDhtmlxException(String errorMessage, Throwable e){
		super(errorMessage, e);
	}
	
	public BaseDhtmlxException(Throwable e) throws BaseDhtmlxException{
		e.printStackTrace();
		throw new BaseDhtmlxException("grid 조회중 오류 발생");
	}
}
