package com.modernwave.web.server.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

@Transactional

public interface MctmService{
	
	public void selectRealTimeVAgent(ModelMap model);
	
	public void selectVGroup(ModelMap model);
	
	public void selectVagent(String f_GroupName, ModelMap model);
	
	public void selectCodeMaster(String f_LD, ModelMap model);
	
	//public void selectPredialog(String f_Distribute, String f_Result, String f_Agent, String f_CallbackNum, String f_Service, String f_start_date, String f_end_date, ModelMap model);
	public void selectPredialog(String f_start_date, String f_end_date, ModelMap model);
	
	public void deletePredialog(String f_Idx, HttpServletRequest request,ModelMap model);
	
	public void updatePredialog(String f_IndexNo, String f_UserId, String f_UserName, String f_ResultUpdate, String f_Memo, HttpServletRequest request,ModelMap model);
	
	public void updatePredialogAgentReg(String f_agentId, HttpServletRequest request,ModelMap model);
	
	public void distributePredialog(String f_IndexNo, String f_DisAgentID, String f_DisAgentName, HttpServletRequest request,ModelMap model);
	
	public void selectUserTel(String f_userId, ModelMap model);
	
	//public void updateUserTel(String f_userId, String f_telNum, HttpServletRequest request,ModelMap model);
	public boolean updateUserTel(String f_userId, String f_telNum, HttpServletRequest request,ModelMap model);
	
	public void selectgroupRegDept(ModelMap model);
	
	public void updategroupRegDept(String f_DeptFlag, String f_DeptCode, String f_DeptName, HttpServletRequest request,ModelMap model);
	
	public void selectgroupRegTeam(String f_dept, ModelMap model);
	
	public void updategroupRegTeam(String f_TeamFlag, String f_TeamCode, String f_TeamName, String f_WorkTime, String f_TeamDeptCode, HttpServletRequest request,ModelMap model);
	
	public void selectGroupRegCode(ModelMap model);
	
	public void selectAgentReg(String f_Code, ModelMap model);
	
	public void updateAgentReg(String f_AgentFlag, String f_AgentID, String f_AgentName, String f_AgentTel, String f_AgentPw, String f_AgentPickup, String f_AgentGroup, HttpServletRequest request,ModelMap model);
	
	public void selectCallStatistic(String f_startDate, String f_endDate, ModelMap model);
	
	public void selectArsStts(String f_startDate, String f_endDate, ModelMap model);
	
	public void selectGroupStts(String f_startDate, String f_endDate, ModelMap model);
	
	public void selectQueueStts(String f_startDate, String f_endDate, ModelMap model);
	
	public void selectServiceLevelStts(String f_startDate, String f_endDate, ModelMap model);
	
	public void selectCallbackStts(String f_startDate, String f_endDate, ModelMap model);
	
	public void selectCodeReg(ModelMap model);	
	
	public void updateCodeReg(String f_Flag, String f_Category, String f_Code, String f_Name, HttpServletRequest request,ModelMap model);
	
	public void selectArsMonitoring(ModelMap model);	
	
	public void selectServiceMonitoring(ModelMap model);	
	
	public void selectMctmArs(ModelMap model);	
	
	public void selectMctmAgent(ModelMap model);	
	
	public void selectAgentCallStts(String f_startDate, String f_endDate, ModelMap model);
	
	public void selectCallRecodeStts(String f_startDate, String f_endDate, ModelMap model);

	
}