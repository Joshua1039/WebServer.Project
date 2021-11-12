package com.modernwave.web.server.dao;

import java.util.List;
import java.util.Map;

import com.modernwave.web.server.framework.dataset.OutputResult;

public interface MwtsDao{

	
	public void loginProc(Map param);
	
	public void modifyPwProc(Map param);

	public List<OutputResult> menuSelect(Map param);
	
	public List<OutputResult> userSelect(Map param);
	
	public List<OutputResult> selectMenuAuth(Map param);
	
	public int updateMenuAuth( Map param);
	
	public List<OutputResult> selectTheme(Map param);
	
	public int updateTheme( Map param);
	
	public int codeMasterInsert( Map param);
	
	public int codeMasterUpdate( Map param);
	
	public int codeMasterDelete( Map param);
	
	public List<OutputResult> selectCodeMaster(Map param);
	
	public int userInfoInsert( Map param);
	
	public int userInfoUpdate( Map param);
	
	public int userInfoDelete( Map param);
	
	public int userInfoResetPW( Map param);
	
	public void loginHistory(Map param);
	
}