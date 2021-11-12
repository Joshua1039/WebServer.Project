package com.modernwave.web.server.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.modernwave.web.server.dao.AcrDao;
import com.modernwave.web.server.framework.core.BaseDao_ACR;
import com.modernwave.web.server.framework.dataset.OutputResult;

@Repository
public class AcrDaoImpl extends BaseDao_ACR implements AcrDao {

	Logger log = Logger.getLogger(this.getClass());
	private String namespace = "Acr.";

	
	@Override
	public List<OutputResult> selectServerInfo(Map param) {
		return (List<OutputResult>) getList(namespace + "selectServerInfo", param);
	}	
	
	@Override
	public int serverInfoInsert(Map param) {
		return updateQuery(namespace+"serverInfoInsert",param);
	}	
	
	@Override
	public int serverInfoUpdate(Map param) {
		return updateQuery(namespace+"serverInfoUpdate",param);
	}
	
	@Override
	public int serverInfoDelete(Map param) {
		return updateQuery(namespace+"serverInfoDelete",param);
	}
	
	@Override
	public List<OutputResult> selectBranchCode(Map param) {
		return (List<OutputResult>) getList(namespace + "selectBranchCode", param);
	}	
	
	@Override
	public int branchCodeInsert(Map param) {
		return updateQuery(namespace+"branchCodeInsert",param);
	}	
	
	@Override
	public int branchCodeUpdate(Map param) {
		return updateQuery(namespace+"branchCodeUpdate",param);
	}
	
	@Override
	public int branchCodeDelete(Map param) {
		return updateQuery(namespace+"branchCodeDelete",param);
	}	
	
	@Override
	public List<OutputResult> selectDept(Map param) {
		return (List<OutputResult>) getList(namespace + "selectDept", param);
	}	
		
	@Override
	public int deptInsert(Map param) {
		return updateQuery(namespace+"deptInsert",param);
	}	
	
	@Override
	public int deptUpdate(Map param) {
		return updateQuery(namespace+"deptUpdate",param);
	}
	
	@Override
	public int deptDelete(Map param) {
		return updateQuery(namespace+"deptDelete",param);
	}
	
	@Override
	public List<OutputResult> selectVpmInfo(Map param) {
		return (List<OutputResult>) getList(namespace + "selectVpmInfo", param);
	}	

	@Override
	public int vpmInfoInsert(Map param) {
		return updateQuery(namespace+"vpmInfoInsert",param);
	}	
	
	@Override
	public int vpmInfoUpdate(Map param) {
		return updateQuery(namespace+"vpmInfoUpdate",param);
	}
	
	@Override
	public int vpmInfoDelete(Map param) {
		return updateQuery(namespace+"vpmInfoDelete",param);
	}
		
	@Override
	public List<OutputResult> selectLevelCode(Map param) {
		return (List<OutputResult>) getList(namespace + "selectLevelCode", param);
	}	
			
	@Override
	public List<OutputResult> selectAgentInfo(Map param) {
		return (List<OutputResult>) getList(namespace + "selectAgentInfo", param);
	}	
		
	@Override
	public int agentInfoInsert(Map param) {
		return updateQuery(namespace+"agentInfoInsert",param);
	}	
	
	@Override
	public int agentInfoUpdate(Map param) {
		return updateQuery(namespace+"agentInfoUpdate",param);
	}
	
	@Override
	public int agentInfoDelete(Map param) {
		return updateQuery(namespace+"agentInfoDelete",param);
	}	
	
	@Override
	public int agentInfoResetPW(Map param) {
		return updateQuery(namespace+"agentInfoResetPW",param);
	}	
	
	@Override
	public List<OutputResult> selectRecData(Map param) {
		return (List<OutputResult>) getList(namespace + "selectRecData", param);
	}		
	
	@Override
	public List<OutputResult> selectListenLog(Map param) {
		return (List<OutputResult>) getList(namespace + "selectListenLog", param);
	}	
	
	@Override
	public List<OutputResult> selectRecStatistic(Map param) {
		return (List<OutputResult>) getList(namespace + "selectRecStatistic", param);
	}	
	
	@Override
	public List<OutputResult> vpmMonitoringSelect(Map param) {
		return (List<OutputResult>) getList(namespace + "vpmMonitoringSelect", param);
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
	public List<OutputResult> selectRealTimeVAgent(Map param) {
		return (List<OutputResult>)getList(namespace+"selectRealTimeVAgent", param);
	}

	@Override
	public int listenLogUpdate(Map param) {
		return updateQuery(namespace+"listenLogUpdate",param);
	}
	
	
}