package com.modernwave.web.server.dao;

import java.util.List;
import java.util.Map;

import com.modernwave.web.server.framework.dataset.OutputResult;

public interface AcrDao{
	
	public List<OutputResult> selectServerInfo(Map param);
	
	public int serverInfoInsert( Map param);
	
	public int serverInfoUpdate( Map param);
	
	public int serverInfoDelete( Map param);
	
	public List<OutputResult> selectBranchCode(Map param);
	
	public int branchCodeInsert( Map param);
	
	public int branchCodeUpdate( Map param);
	
	public int branchCodeDelete( Map param);
		
	public List<OutputResult> selectDept(Map param);
	
	public int deptInsert( Map param);
	
	public int deptUpdate( Map param);
	
	public int deptDelete( Map param);
	
	public List<OutputResult> selectVpmInfo(Map param);
	
	public int vpmInfoInsert( Map param);
	
	public int vpmInfoUpdate( Map param);
	
	public int vpmInfoDelete( Map param);
	
	public List<OutputResult> selectLevelCode(Map param);
	
	public List<OutputResult> selectAgentInfo(Map param);
	
	public int agentInfoInsert( Map param);
	
	public int agentInfoUpdate( Map param);
	
	public int agentInfoDelete( Map param);	
	
	public int agentInfoResetPW( Map param);	
	
	public List<OutputResult> selectRecData(Map param);
	
	public List<OutputResult> selectListenLog(Map param);
	
	public List<OutputResult> selectRecStatistic(Map param);
	
	public List<OutputResult> vpmMonitoringSelect(Map param);
	
	public List<OutputResult> selectUserTel(Map param);
	
	public int updateUserTel( Map param);	
	
	public List<OutputResult> selectRealTimeVAgent(Map param);
	
	public int listenLogUpdate( Map param);
	
	
}