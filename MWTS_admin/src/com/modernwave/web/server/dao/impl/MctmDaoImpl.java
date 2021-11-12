package com.modernwave.web.server.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.modernwave.web.server.dao.MctmDao;
import com.modernwave.web.server.framework.core.BaseDao_MCTM;
import com.modernwave.web.server.framework.dataset.OutputResult;

@Repository
public class MctmDaoImpl extends BaseDao_MCTM implements MctmDao {

	Logger log = Logger.getLogger(this.getClass());
	private String namespace = "Mctm.";

	@Override
	public List<OutputResult> selectCodeReg(Map param) {
		return (List<OutputResult>) getList(namespace + "selectCodeReg", param);
	}	
	
	@Override
	public int codeRegInsert(Map param) {
		return updateQuery(namespace+"codeRegInsert",param);
	}	
	
	@Override
	public int codeRegUpdate(Map param) {
		return updateQuery(namespace+"codeRegUpdate",param);
	}	
	
	@Override
	public int codeRegDelete(Map param) {
		return updateQuery(namespace+"codeRegDelete",param);
	}	
	
	@Override
	public List<OutputResult> selectRealTimeVAgent(Map param) {
		return (List<OutputResult>)getList(namespace+"selectRealTimeVAgent", param);
	}

	@Override
	public List<OutputResult> selectVGroup(Map param) {
		return (List<OutputResult>) getList(namespace + "selectVGroup", param);
	}	
	
	@Override
	public List<OutputResult> selectVagent(Map param) {
		return (List<OutputResult>) getList(namespace + "selectVagent", param);
	}		
	
	@Override
	public List<OutputResult> selectCodeMaster(Map param) {
		return (List<OutputResult>) getList(namespace + "selectCodeMaster", param);
	}		
		
	@Override
	public List<OutputResult> selectPredialog(Map param) {
		return (List<OutputResult>) getList(namespace + "selectPredialog", param);
	}		
	
	@Override
	public int deletePredialog(Map param) {
		return updateQuery(namespace+"deletePredialog",param);
	}

	@Override
	public int updatePredialog(Map param) {
		return updateQuery(namespace+"updatePredialog",param);
	}
	
	@Override
	public int updatePredialogAgentReg(Map param) {
		return updateQuery(namespace+"updatePredialogAgentReg",param);
	}	
	
	@Override
	public int distributePredialog(Map param) {
		return updateQuery(namespace+"distributePredialog",param);
	}	
	
	@Override
	public List<OutputResult> selectUserTel(Map param) {
		return (List<OutputResult>) getList(namespace + "selectUserTel", param);
	}	
	
	@Override
	public int updateUserTel(Map param) {
		return updateQuery(namespace+"updateUserTel",param);
	}	
	
	@Override
	public List<OutputResult> selectgroupRegDept(Map param) {
		return (List<OutputResult>) getList(namespace + "selectgroupRegDept", param);
	}	

	@Override
	public int groupRegDeptInsert(Map param) {
		return updateQuery(namespace+"groupRegDeptInsert",param);
	}	
	
	@Override
	public int groupRegDeptUpdate(Map param) {
		return updateQuery(namespace+"groupRegDeptUpdate",param);
	}	
	
	@Override
	public int groupRegDeptDelete(Map param) {
		return updateQuery(namespace+"groupRegDeptDelete",param);
	}	
	
	@Override
	public List<OutputResult> selectgroupRegTeam(Map param) {
		return (List<OutputResult>) getList(namespace + "selectgroupRegTeam", param);
	}	
		
	@Override
	public int groupRegTeamInsert(Map param) {
		return updateQuery(namespace+"groupRegTeamInsert",param);
	}	
	
	@Override
	public int groupRegTeamUpdate(Map param) {
		return updateQuery(namespace+"groupRegTeamUpdate",param);
	}	
	
	@Override
	public int groupRegTeamDelete(Map param) {
		return updateQuery(namespace+"groupRegTeamDelete",param);
	}	
	
	@Override
	public List<OutputResult> selectGroupRegCode(Map param) {
		return (List<OutputResult>) getList(namespace + "selectGroupRegCode", param);
	}		
	
	@Override
	public List<OutputResult> selectAgentReg(Map param) {
		return (List<OutputResult>) getList(namespace + "selectAgentReg", param);
	}
	
	@Override
	public int agentRegInsert(Map param) {
		return updateQuery(namespace+"agentRegInsert",param);
	}	
	
	@Override
	public int agentRegUpdate(Map param) {
		return updateQuery(namespace+"agentRegUpdate",param);
	}	
	
	@Override
	public int agentRegDelete(Map param) {
		return updateQuery(namespace+"agentRegDelete",param);
	}	
	
	@Override
	public List<OutputResult> selectCallStatistic(Map param) {
		return (List<OutputResult>) getList(namespace + "selectCallStatistic", param);
	}
	
	@Override
	public List<OutputResult> selectArsStts(Map param) {
		return (List<OutputResult>) getList(namespace + "selectArsStts", param);
	}
	
	@Override
	public List<OutputResult> selectGroupStts(Map param) {
		return (List<OutputResult>) getList(namespace + "selectGroupStts", param);
	}	
	
	@Override
	public List<OutputResult> selectQueueStts(Map param) {
		return (List<OutputResult>) getList(namespace + "selectQueueStts", param);
	}		
	
	@Override
	public List<OutputResult> selectServiceLevelStts(Map param) {
		return (List<OutputResult>) getList(namespace + "selectServiceLevelStts", param);
	}		
	
	@Override
	public List<OutputResult> selectCallbackStts(Map param) {
		return (List<OutputResult>) getList(namespace + "selectCallbackStts", param);
	}		

	@Override
	public List<OutputResult> selectArsMonitoring(Map param) {
		return (List<OutputResult>) getList(namespace + "selectArsMonitoring", param);
	}	
	
	@Override
	public List<OutputResult> selectServiceMonitoring(Map param) {
		return (List<OutputResult>) getList(namespace + "selectServiceMonitoring", param);
	}	
		
	@Override
	public List<OutputResult> selectMctmArs(Map param) {
		return (List<OutputResult>) getList(namespace + "selectMctmArs", param);
	}	
	
	@Override
	public List<OutputResult> selectMctmAgent(Map param) {
		return (List<OutputResult>) getList(namespace + "selectMctmAgent", param);
	}	
	
	@Override
	public List<OutputResult> selectAgentCallStts(Map param) {
		return (List<OutputResult>) getList(namespace + "selectAgentCallStts", param);
	}

	@Override
	public List<OutputResult> selectCallRecodeStts(Map param) {
		return (List<OutputResult>) getList(namespace + "selectCallRecodeStts", param);
	}
	
	
}