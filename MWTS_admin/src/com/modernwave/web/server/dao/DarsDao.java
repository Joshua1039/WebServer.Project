package com.modernwave.web.server.dao;

import java.util.List;
import java.util.Map;

import com.modernwave.web.server.framework.dataset.OutputResult;

public interface DarsDao{
	
	public List<OutputResult> mentSelect(Map param);
	
	public int mentInsert( Map param);
	
	public int mentUpdate( Map param);
	
	public int mentDelete( Map param);	
	
	public List<OutputResult> codeMstSiteList(Map param);
	
	public List<OutputResult> codeMstHolidayList(Map param);
	
	public List<OutputResult> holidaySelect(Map param);
	
	public int holidayInsert( Map param);
	
	public int holidayDelete( Map param);
	
	public List<OutputResult> jobTimesSelect(Map param);
	
	public int updateJobTimes( Map param);
	
	public int copyJobTimes( Map param);
	
	public List<OutputResult> trafficChSelect(Map param);
	
	public List<OutputResult> callHistorySelect(Map param);
	
	public List<OutputResult> svcInfoSelect(Map param);
	
	public List<OutputResult> selectParamTable(Map param);
	
	public List<OutputResult> parameterSelect(Map param);
	
	public int parameterUpdate( Map param);
	
	public List<OutputResult> selectServiceStts(Map param);
	
	
	
}