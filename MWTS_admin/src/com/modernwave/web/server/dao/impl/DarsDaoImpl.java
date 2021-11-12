package com.modernwave.web.server.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.modernwave.web.server.dao.DarsDao;
import com.modernwave.web.server.framework.core.BaseDao_DARS;
import com.modernwave.web.server.framework.dataset.OutputResult;

@Repository
public class DarsDaoImpl extends BaseDao_DARS implements DarsDao {

	Logger log = Logger.getLogger(this.getClass());
	private String namespace = "Dars.";
	
	@Override
	public List<OutputResult> mentSelect(Map param) {
		return (List<OutputResult>) getList(namespace + "mentSelect", param);
	}
	
	@Override
	public int mentInsert(Map param) {
		return updateQuery(namespace+"mentInsert",param);
	}	
	
	@Override
	public int mentUpdate(Map param) {
		return updateQuery(namespace+"mentUpdate",param);
	}	
	
	@Override
	public int mentDelete(Map param) {
		return updateQuery(namespace+"mentDelete",param);
	}	
	
	@Override
	public List<OutputResult> codeMstSiteList(Map param) {
		return (List<OutputResult>)getList(namespace+"codeMstSiteList", param);
	}
	
	@Override
	public List<OutputResult> codeMstHolidayList(Map param) {
		return (List<OutputResult>)getList(namespace+"codeMstHolidayList", param);
	}	
	
	@Override
	public List<OutputResult> holidaySelect(Map param) {
		return (List<OutputResult>) getList(namespace + "holidaySelect", param);
	}	

	@Override
	public int holidayInsert(Map param) {
		return updateQuery(namespace+"holidayInsert",param);
	}	
	
	@Override
	public int holidayDelete(Map param) {
		return updateQuery(namespace+"holidayDelete",param);
	}	

	@Override
	public List<OutputResult> jobTimesSelect(Map param) {
		return (List<OutputResult>) getList(namespace + "jobTimesSelect", param);
	}	

	@Override
	public int updateJobTimes(Map param) {
		return updateQuery(namespace+"updateJobTimes",param);
	}
	
	@Override
	public int copyJobTimes(Map param) {
		return updateQuery(namespace+"copyJobTimes",param);
	}

	@Override
	public List<OutputResult> trafficChSelect(Map param) {
		return (List<OutputResult>) getList(namespace + "trafficChSelect", param);
	}
	
	@Override
	public List<OutputResult> callHistorySelect(Map param) {
		return (List<OutputResult>) getList(namespace + "callHistorySelect", param);
	}
	
	@Override
	public List<OutputResult> svcInfoSelect(Map param) {
		return (List<OutputResult>) getList(namespace + "svcInfoSelect", param);
	}
	
	@Override
	public List<OutputResult> selectParamTable(Map param) {
		return (List<OutputResult>) getList(namespace + "selectParamTable", param);
	}	
	
	@Override
	public List<OutputResult> parameterSelect(Map param) {
		return (List<OutputResult>) getList(namespace + "parameterSelect", param);
	}		
	
	@Override
	public int parameterUpdate(Map param) {
		return updateQuery(namespace+"parameterUpdate",param);
	}
	
	@Override
	public List<OutputResult> selectServiceStts(Map param) {
		return (List<OutputResult>) getList(namespace + "selectServiceStts", param);
	}			
	
	
	
}