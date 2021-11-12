package com.modernwave.web.server.dao;

import java.util.List;
import java.util.Map;

import com.modernwave.web.server.framework.dataset.OutputResult;

public interface MctmDao{

	public List<OutputResult> selectCodeReg(Map param);
	
	public int codeRegInsert( Map param);
	
	public int codeRegUpdate( Map param);
	
	public int codeRegDelete( Map param);
	
	public List<OutputResult> selectRealTimeVAgent(Map param);
	
	public List<OutputResult> selectVGroup(Map param);
	
	public List<OutputResult> selectVagent(Map param);
	
	public List<OutputResult> selectCodeMaster(Map param);
	
	public List<OutputResult> selectPredialog(Map param);
	
	public int deletePredialog( Map param);
	
	public int updatePredialog( Map param);
	
	public int updatePredialogAgentReg( Map param);
	
	public int distributePredialog( Map param);
	
	public List<OutputResult> selectUserTel(Map param);
	
	public int updateUserTel( Map param);
	
	public List<OutputResult> selectgroupRegDept(Map param);
	
	public int groupRegDeptInsert( Map param);
	
	public int groupRegDeptUpdate( Map param);
	
	public int groupRegDeptDelete( Map param);
	
	public List<OutputResult> selectgroupRegTeam(Map param);
	
	public int groupRegTeamInsert( Map param);
	
	public int groupRegTeamUpdate( Map param);
	
	public int groupRegTeamDelete( Map param);
	
	public List<OutputResult> selectGroupRegCode(Map param);
	
	public List<OutputResult> selectAgentReg(Map param);
	
	public int agentRegInsert( Map param);
	
	public int agentRegUpdate( Map param);
	
	public int agentRegDelete( Map param);
	
	public List<OutputResult> selectCallStatistic(Map param);
	
	public List<OutputResult> selectArsStts(Map param);
	
	public List<OutputResult> selectGroupStts(Map param);
	
	public List<OutputResult> selectQueueStts(Map param);
	
	public List<OutputResult> selectServiceLevelStts(Map param);
	
	public List<OutputResult> selectCallbackStts(Map param);
	
	public List<OutputResult> selectArsMonitoring(Map param);
	
	public List<OutputResult> selectServiceMonitoring(Map param);
	
	public List<OutputResult> selectMctmArs(Map param);
	
	public List<OutputResult> selectMctmAgent(Map param);
	
	public List<OutputResult> selectAgentCallStts(Map param);
	
	public List<OutputResult> selectCallRecodeStts(Map param);
	
	
}