package com.modernwave.web.server.model;

public class CodeVo {

	public String code_value;
	public String code_name;
	
	public CodeVo(){};
	
	public CodeVo(String code_value, String code_name) {
		this.code_value = code_value;
		this.code_name = code_name;
	}
	public String getCode_value() {
		return code_value;
	}
	public void setCode_value(String code_value) {
		this.code_value = code_value;
	}
	public String getCode_name() {
		return code_name;
	}
	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}
	
	
	
}
