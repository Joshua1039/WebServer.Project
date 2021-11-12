package com.modernwave.web.server.framework.dataset.grid.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.modernwave.web.server.framework.core.BaseDao;
import com.modernwave.web.server.framework.dataset.OutputResult;
import com.modernwave.web.server.framework.dataset.grid.dao.DhtmlxGridDao;

@Repository
public class DhtmlxGridDaoImpl extends BaseDao implements DhtmlxGridDao{

	public List<OutputResult> selectQuery(String statementId, Map param){
		List<OutputResult> list = (List<OutputResult>)getList(statementId, param);
		return (list == null)? new ArrayList<OutputResult>():list;
	}

	public Object insertQuery(String statementId, Map param){
		return insertQuery(statementId, param);
	}

	public int updateQuery(String statementId, Map param){
		return updateQuery(statementId, param);
	}

	public int deleteQuery(String statementId, Map param){
		return deleteQuery(statementId, param);
	}
	
}
