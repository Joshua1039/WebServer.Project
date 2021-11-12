package com.modernwave.web.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.modernwave.web.server.dao.MwtsDao;
import com.modernwave.web.server.framework.core.BaseDao_MWTS;
import com.modernwave.web.server.framework.dataset.OutputResult;

@Repository
public class MwtsDaoImpl extends BaseDao_MWTS implements MwtsDao {

	Logger log = Logger.getLogger(this.getClass());
	public static final String namespace = "Mwts.";


	@Override
	public void loginProc(Map param){
		getObject(namespace+"loginProc", param);
	}

	@Override
	public void modifyPwProc(Map param) {
		getObject(namespace+"modifyPwProc", param);		
	}

	@Override
	public List<OutputResult> menuSelect(Map param) {
		return (List<OutputResult>) getList(namespace + "menuSelect", param);
	}

	@Override
	public List<OutputResult> userSelect(Map param) {
		return (List<OutputResult>) getList(namespace + "userSelect", param);
	}
	
	@Override
	public List<OutputResult> selectMenuAuth(Map param) {
		return (List<OutputResult>)getList(namespace+"selectMenuAuth", param);
	}
	
	@Override
	public int updateMenuAuth(Map param) {
		return updateQuery(namespace+"updateMenuAuth",param);
	}	

	@Override
	public List<OutputResult> selectTheme(Map param) {
		return (List<OutputResult>) getList(namespace + "selectTheme", param);
	}	
	
	@Override
	public int updateTheme(Map param) {
		return updateQuery(namespace+"updateTheme",param);
	}	
	
	@Override
	public int codeMasterInsert(Map param) {
		return updateQuery(namespace+"codeMasterInsert",param);
	}	
	
	@Override
	public int codeMasterUpdate(Map param) {
		return updateQuery(namespace+"codeMasterUpdate",param);
	}	
	
	@Override
	public int codeMasterDelete(Map param) {
		return updateQuery(namespace+"codeMasterDelete",param);
	}	
	
	@Override
	public List<OutputResult> selectCodeMaster(Map param) {
		return (List<OutputResult>) getList(namespace + "selectCodeMaster", param);
	}	
	
	@Override
	public int userInfoInsert(Map param) {
		return updateQuery(namespace+"userInfoInsert",param);
	}	
	
	@Override
	public int userInfoUpdate(Map param) {
		return updateQuery(namespace+"userInfoUpdate",param);
	}	
	
	@Override
	public int userInfoDelete(Map param) {
		return updateQuery(namespace+"userInfoDelete",param);
	}	
		
	@Override
	public int userInfoResetPW(Map param) {
		return updateQuery(namespace+"userInfoResetPW",param);
	}	
		
	@Override
	public void loginHistory(Map param){
		getObject(namespace+"loginHistory", param);
	}
	
}