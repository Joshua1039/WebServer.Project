package com.modernwave.web.server.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.modernwave.web.server.dao.AcrDao;
import com.modernwave.web.server.framework.core.BaseService;
import com.modernwave.web.server.framework.dataset.InputParam;
import com.modernwave.web.server.framework.dataset.OutputResult;
import com.modernwave.web.server.service.AcrService;

@Service
public class AcrServiceImpl extends BaseService implements AcrService {

	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	AcrDao acrDao;

	
	@Override
	public void selectServerInfo(ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			List<OutputResult> dataList = acrDao.selectServerInfo(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectServerInfo]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectServerInfo]", e);
		}		
	}

	
	@Override
	public void updateServerInfo(String f_Flag, String f_ServerCode, String f_ServerName, String f_ServerIp, String f_FTPId, String f_FTPPw,  String f_FTPPort,  String f_UseYN, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("SERVERCODE", f_ServerCode);
			param.put("SERVERNAME", f_ServerName);
			param.put("SERVERIP", f_ServerIp);
			param.put("FTPID", f_FTPId);
			param.put("FTPPW", f_FTPPw);
			param.put("FTPPORT", f_FTPPort);
			param.put("USEYN", f_UseYN);
			
			if(f_Flag.equals("insert")) {
				acrDao.serverInfoInsert(param);
					
			}else if(f_Flag.equals("update")) {
				acrDao.serverInfoUpdate(param);
				
			}else if(f_Flag.equals("delete")) {
				acrDao.serverInfoDelete(param);
				
			}
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : updateServerInfo]");	
			log.error("처리중 문제가 발생했습니다. [Exception : updateServerInfo]", e);
		}
	}
	

	@Override
	public void selectBranchCode(ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			List<OutputResult> dataList = acrDao.selectBranchCode(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectBranchCode]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectBranchCode]", e);
		}		
	}

	
	@Override
	public void updateBranchCode(String f_Flag, String f_BranchCode, String f_BranchName, String f_UseYN, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("BRANCHCODE", f_BranchCode);
			param.put("BRANCHNAME", f_BranchName);
			param.put("USEYN", f_UseYN);
			
			if(f_Flag.equals("insert")) {
				acrDao.branchCodeInsert(param);
					
			}else if(f_Flag.equals("update")) {
				acrDao.branchCodeUpdate(param);
				
			}else if(f_Flag.equals("delete")) {
				acrDao.branchCodeDelete(param);
				
			}
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : updateBranchCode]");	
			log.error("처리중 문제가 발생했습니다. [Exception : updateBranchCode]", e);
		}
	}
		
	
	@Override
	public void selectDept(ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			List<OutputResult> dataList = acrDao.selectDept(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectDept]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectDept]", e);
		}		
	}
	

	@Override
	public void updateDept(String f_Flag, String f_ID1, String f_ID2, String f_ID3, String f_ID4, String f_ID5, String f_ID6, String f_ID7, String f_NM1, String f_NM2, String f_NM3, String f_NM4, String f_NM5, String f_NM6, String f_NM7, String f_DeptId , String f_DeptName , String f_DeptLevel , String f_Seq , String f_Useyn, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("ID1", f_ID1);
			param.put("ID2", f_ID2);
			param.put("ID3", f_ID3);
			param.put("ID4", f_ID4);
			param.put("ID5", f_ID5);
			param.put("ID6", f_ID6);
			param.put("ID7", f_ID7);
			param.put("NM1", f_NM1);
			param.put("NM2", f_NM2);
			param.put("NM3", f_NM3);
			param.put("NM4", f_NM4);
			param.put("NM5", f_NM5);
			param.put("NM6", f_NM6);
			param.put("NM7", f_NM7);
			param.put("DEPTID", f_DeptId);
			param.put("DEPTNAME", f_DeptName);
			param.put("DEPTLEVEL", f_DeptLevel);
			param.put("SEQ", f_Seq == "" ? "0" : f_Seq);
			param.put("USEYN", f_Useyn);
			
			if(f_Flag.equals("insert")) {
				acrDao.deptInsert(param);
					
			}else if(f_Flag.equals("update")) {
				acrDao.deptUpdate(param);
				
			}else if(f_Flag.equals("delete")) {
				acrDao.deptDelete(param);
				
			}
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : updateDept]");	
			log.error("처리중 문제가 발생했습니다. [Exception : updateDept]", e);
		}
	}
		
	
	@Override
	public void selectVpmInfo(ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			List<OutputResult> dataList = acrDao.selectVpmInfo(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectVpmInfo]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectVpmInfo]", e);
		}		
	}
	
	
	@Override
	public void updateVpmInfo(String f_Flag, String f_ServerCode, String f_VpmNum, String f_TelNum, String f_Extension, String f_TelPw, String f_IpmAgentIp, String f_AgentId, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("SERVERCODE", f_ServerCode);
			param.put("VPMNUM", f_VpmNum);
			param.put("TELNUM", f_TelNum);
			param.put("EXTENSION", f_Extension);
			param.put("TELPW", f_TelPw);
			param.put("IPMAGENTIP", f_IpmAgentIp);
			param.put("AGENTID", f_AgentId);
			
			if(f_Flag.equals("insert")) {
				acrDao.vpmInfoInsert(param);
					
			}else if(f_Flag.equals("update")) {
				acrDao.vpmInfoUpdate(param);
				
			}else if(f_Flag.equals("delete")) {
				acrDao.vpmInfoDelete(param);
				
			}
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : updateVpmInfo]");	
			log.error("처리중 문제가 발생했습니다. [Exception : updateVpmInfo]", e);
		}
	}	
	
	
	@Override
	public void selectLevelCode(ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			List<OutputResult> dataList = acrDao.selectLevelCode(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectLevelCode]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectLevelCode]", e);
		}		
	}	
	
	
	@Override
	public void selectAgentInfo(ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			List<OutputResult> dataList = acrDao.selectAgentInfo(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectAgentInfo]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectAgentInfo]", e);
		}		
	}	
	
	
	@Override
	public void updateAgentInfo(String f_Flag, String f_AgentID, String f_AgentName, String f_AgentPW, String f_LevelCode, String f_BranchCode, String f_DeptCode, String f_ServerCode, String f_Useyn, String f_TelNum, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("AGENTID", f_AgentID);
			param.put("AGENTNAME", f_AgentName);
			param.put("AGENTPW", f_AgentPW);
			param.put("LEVELCODE", f_LevelCode == "" ? "0" : f_LevelCode);
			param.put("BRANCHCODE", f_BranchCode);
			param.put("DEPTCODE", f_DeptCode);
			param.put("SERVERCODE", f_ServerCode == "" ? "0" : f_ServerCode);
			param.put("USEYN", f_Useyn);
			param.put("TELNUM", f_TelNum);
			
			if(f_Flag.equals("insert")) {
				acrDao.agentInfoInsert(param);
					
			}else if(f_Flag.equals("update")) {
				acrDao.agentInfoUpdate(param);
				
			}else if(f_Flag.equals("delete")) {
				acrDao.agentInfoDelete(param);
				
			}else if(f_Flag.equals("resetPW")) {
				acrDao.agentInfoResetPW(param);
				
			}
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : updateAgentInfo]");	
			log.error("처리중 문제가 발생했습니다. [Exception : updateAgentInfo]", e);
		}
	}		
	

	@Override
	public void selectRecData(String f_startDate, String f_endDate, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("STARTDATE", f_startDate);
			param.put("ENDDATE", f_endDate);
			
			List<OutputResult> dataList = acrDao.selectRecData(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectRecData]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectRecData]", e);
		}		
	}	
	
	
	@Override
	public void selectListenLog(String f_startDate, String f_endDate, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("STARTDATE", f_startDate);
			param.put("ENDDATE", f_endDate);
			
			List<OutputResult> dataList = acrDao.selectListenLog(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectListenLog]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectListenLog]", e);
		}		
	}	
		
	
	@Override
	public void selectRecStatistic(String f_startDate, String f_endDate, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("STARTDATE", f_startDate);
			param.put("ENDDATE", f_endDate);
			
			List<OutputResult> dataList = acrDao.selectRecStatistic(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectRecStatistic]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectRecStatistic]", e);
		}		
	}		
	
	
	@Override
	public void vpmMonitoringSelect(String f_Code, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("SERVERCODE", f_Code);
			
			List<OutputResult> dataList = acrDao.vpmMonitoringSelect(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : vpmMonitoringSelect]");	
			log.error("처리중 문제가 발생했습니다. [Exception : vpmMonitoringSelect]", e);
		}		
	}		
	
	
	@Override
	public void selectUserTel(String f_userId, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", f_userId);
			
			List<OutputResult> dataList = acrDao.selectUserTel(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectUserTel]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectUserTel]", e);
		}		
	}		
	
	
	@Override
	public void updateUserTel(String f_userId, String f_telNum, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("USERID", f_userId);
			param.put("TELNUM", f_telNum);
			
			acrDao.updateUserTel(param);
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : updateUserTel]");	
			log.error("처리중 문제가 발생했습니다. [Exception : updateUserTel]", e);
		}
	}	
		
	@Override
	public void selectRealTimeVAgent(ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			List<OutputResult> dataList = acrDao.selectRealTimeVAgent(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectRealTimeVAgent]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectRealTimeVAgent]", e);
		}		
	}	

	@Override
	public void updateListenLog(String f_fileName, String f_job, String f_recDate, String f_serverCode, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("USERID", getUserId());
			param.put("USERNAME", getUserName());
			param.put("RECFILENAME", f_fileName);
			param.put("JOB", f_job);
			param.put("RECDATE", f_recDate);
			param.put("SERVERCODE", f_serverCode);
			
			acrDao.listenLogUpdate(param);
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : updateListenLog]");	
			log.error("처리중 문제가 발생했습니다. [Exception : updateListenLog]", e);
		}
	}
	
	
}