package com.modernwave.web.server.framework.dataset.grid.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.modernwave.web.server.framework.dataset.OutputResult;

@Transactional
public interface DhtmlxGridService {

	public List<OutputResult> selectQuery(String statementId, Map param);
	
	public Object insertQuery(String statementId, Map param);
	
	public int updateQuery(String statementId, Map param);
	
	public int deleteQuery(String statementId, Map param);
	
}
