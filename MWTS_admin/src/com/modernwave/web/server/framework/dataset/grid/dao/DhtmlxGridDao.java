package com.modernwave.web.server.framework.dataset.grid.dao;

import java.util.List;
import java.util.Map;

import com.modernwave.web.server.framework.dataset.OutputResult;


public interface DhtmlxGridDao {

	public List<OutputResult> selectQuery(String statementId, Map param);
	
	public Object insertQuery(String statementId, Map param);
	
	public int updateQuery(String statementId, Map param);
	
	public int deleteQuery(String statementId, Map param);
	
}
