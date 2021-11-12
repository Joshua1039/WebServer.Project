package com.modernwave.web.server.framework.dataset.grid.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modernwave.web.server.framework.core.BaseService;
import com.modernwave.web.server.framework.dataset.OutputResult;
import com.modernwave.web.server.framework.dataset.grid.dao.DhtmlxGridDao;
import com.modernwave.web.server.framework.dataset.grid.service.DhtmlxGridService;

@Service
public class DhtmlxGridServiceImpl extends BaseService implements DhtmlxGridService{
	
	Logger log = Logger.getLogger(this.getClass());
	
	public DhtmlxGridServiceImpl(){
	}

	@Autowired
	DhtmlxGridDao dhtmlxGridDao;

	public List<OutputResult> selectQuery(String statementId, Map param){
		return dhtmlxGridDao.selectQuery(statementId, param);
	}

	public Object insertQuery(String statementId, Map param){
		return dhtmlxGridDao.insertQuery(statementId, param);
	}

	public int updateQuery(String statementId, Map param){
		return dhtmlxGridDao.updateQuery(statementId, param);
	}

	public int deleteQuery(String statementId, Map param){
		return dhtmlxGridDao.deleteQuery(statementId, param);
	}
	
}
