package com.modernwave.web.server.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.modernwave.web.server.framework.dataset.OutputResult;

@Transactional

public interface AcrService{
	
	public void selectServerInfo(ModelMap model);
	
	public void updateServerInfo(String f_Flag, String f_ServerCode, String f_ServerName, String f_ServerIp, String f_FTPId, String f_FTPPw,  String f_FTPPort,  String f_UseYN, HttpServletRequest request,ModelMap model);
	
	public void selectBranchCode(ModelMap model);
	
	public void updateBranchCode(String f_Flag, String f_BranchCode, String f_BranchName, String f_UseYN, HttpServletRequest request,ModelMap model);
	
	public void selectDept(ModelMap model);
	
	public void updateDept(String f_Flag, String f_ID1, String f_ID2, String f_ID3, String f_ID4, String f_ID5, String f_ID6, String f_ID7, String f_NM1, String f_NM2, String f_NM3, String f_NM4, String f_NM5, String f_NM6, String f_NM7, String f_DeptId , String f_DeptName , String f_DeptLevel , String f_Seq , String f_Useyn, HttpServletRequest request,ModelMap model);
	
	public void selectVpmInfo(ModelMap model);
	
	public void updateVpmInfo(String f_Flag, String f_ServerCode, String f_VpmNum, String f_TelNum, String f_Extension, String f_TelPw, String f_IpmAgentIp, String f_AgentId, HttpServletRequest request,ModelMap model);
	
	public void selectLevelCode(ModelMap model);
	
	public void selectAgentInfo(ModelMap model);
	
	public void updateAgentInfo(String f_Flag, String f_AgentID, String f_AgentName, String f_AgentPW, String f_LevelCode, String f_BranchCode, String f_DeptCode, String f_ServerCode, String f_Useyn, String f_TelNum, HttpServletRequest request,ModelMap model);
	
	public void selectRecData(String f_startDate, String f_endDate, ModelMap model);
	
	public void selectListenLog(String f_startDate, String f_endDate, ModelMap model);
	
	public void selectRecStatistic(String f_startDate, String f_endDate, ModelMap model);
	
	public void vpmMonitoringSelect(String f_Code, ModelMap model);
	
	public void selectUserTel(String f_userId, ModelMap model);
	
	public void updateUserTel(String f_userId, String f_telNum, HttpServletRequest request,ModelMap model);
	
	public void selectRealTimeVAgent(ModelMap model);
	
	public void updateListenLog(String f_fileName, String f_job, String f_recDate, String f_serverCode, HttpServletRequest request,ModelMap model);
	
}