package com.modernwave.web.server.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.ModelMap;

import com.modernwave.web.server.dao.MctmDao;
import com.modernwave.web.server.framework.core.BaseService;
import com.modernwave.web.server.framework.dataset.InputParam;
import com.modernwave.web.server.framework.dataset.OutputResult;
import com.modernwave.web.server.service.MctmService;
import org.apache.commons.lang.StringUtils;

import com.modernwave.web.server.dao.AcrDao;

@Service
public class MctmServiceImpl extends BaseService implements MctmService {

	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	MctmDao mctmDao;

	@Autowired
	AcrDao acrDao;
	
	@Autowired
	 private TransactionTemplate transactionTemplate_MCTM;
	  
	 public void setTransactionTemplate(TransactionTemplate transactionTemplate){
	  this.transactionTemplate_MCTM = transactionTemplate;
	 }
	 
    public static String getPrintStackTrace(Exception e) {
         
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
         
        return errors.toString();
         
    }
	    
	@Override
	public void selectRealTimeVAgent(ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			List<OutputResult> dataList = mctmDao.selectRealTimeVAgent(param);
			
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
	public void selectVGroup(ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			List<OutputResult> dataList = mctmDao.selectVGroup(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectVGroup]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectVGroup]", e);
		}		
	}
	
	@Override
	public void selectVagent(String f_GroupName, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("GROUPNAME", f_GroupName);
			
			List<OutputResult> dataList = mctmDao.selectVagent(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectVagent]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectVagent]", e);
		}		
	}
		
	@Override
	public void selectCodeMaster(String f_LD, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("LD", f_LD);
			
			List<OutputResult> dataList = mctmDao.selectCodeMaster(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectCodeMaster]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectCodeMaster]", e);
		}		
	}	
	
/*	@Override
	public void selectPredialog(String f_Distribute, String f_Result, String f_Agent, String f_CallbackNum, String f_Service, String f_start_date, String f_end_date, ModelMap model) {
		
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("GUBUN", f_Distribute);
			param.put("PART", f_Result);
			param.put("AGENTNAME", f_Agent);
			param.put("CALLBACKNUM", f_CallbackNum);
			param.put("SVCTEXT", f_Service);
			param.put("STARTDATE", f_start_date);
			param.put("ENDDATE", f_end_date);
			
			List<OutputResult> dataList = mctmDao.selectPredialog(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
	}	*/	
	@Override
	public void selectPredialog(String f_start_date, String f_end_date, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("STARTDATE", f_start_date);
			param.put("ENDDATE", f_end_date);
			
			List<OutputResult> dataList = mctmDao.selectPredialog(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectPredialog]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectPredialog]", e);
		}		
	}	
	
	@Override
	public void deletePredialog(String f_Idx, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("IDX", f_Idx);
			
			mctmDao.deletePredialog(param);
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : deletePredialog]");	
			log.error("처리중 문제가 발생했습니다. [Exception : deletePredialog]", e);
		}
	}	
	
	@Override
	public void updatePredialog(String f_IndexNo, String f_UserId, String f_UserName, String f_ResultUpdate, String f_Memo, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("IDXNO", f_IndexNo);
			param.put("USERID", f_UserId);
			param.put("USERNAME", f_UserName);
			param.put("RESULT", f_ResultUpdate);
			param.put("MEMO", f_Memo);
			
			mctmDao.updatePredialog(param);
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : updatePredialog]");	
			log.error("처리중 문제가 발생했습니다. [Exception : updatePredialog]", e);
		}
	}	
		
	@Override
	public void updatePredialogAgentReg(String f_agentId, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("AGENTID", f_agentId);
			
			mctmDao.updatePredialogAgentReg(param);
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : updatePredialogAgentReg]");	
			log.error("처리중 문제가 발생했습니다. [Exception : updatePredialogAgentReg]", e);
		}
	}
	
	@Override
	public void distributePredialog(String f_IndexNo, String f_DisAgentID, String f_DisAgentName, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("INDEXNO", f_IndexNo);
			param.put("AGENTID", f_DisAgentID);
			param.put("AGENTNAME", f_DisAgentName);
			
			mctmDao.distributePredialog(param);
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : distributePredialog]");	
			log.error("처리중 문제가 발생했습니다. [Exception : distributePredialog]", e);
		}
	}	
	
	@Override
	public void selectUserTel(String f_userId, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", f_userId);
			
			List<OutputResult> dataList = mctmDao.selectUserTel(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectUserTel]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectUserTel]", e);
		}		
	}	
	
/*	@Override
	public void updateUserTel(String f_userId, String f_telNum, HttpServletRequest request,  ModelMap model) {
		
		try{
			InputParam param = new InputParam();
			
			param.put("USERID", f_userId);
			param.put("TELNUM", f_telNum);
			
			mctmDao.updateUserTel(param);
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			log.error("methodName");
			e.printStackTrace();
			
		}
	}		*/
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateUserTel(final String f_userId, final String f_telNum, HttpServletRequest request, final ModelMap model) {
		boolean result = false;
		result = (Boolean)transactionTemplate_MCTM.execute( new TransactionCallback() {
			public Object doInTransaction(TransactionStatus status) {
				try{
					InputParam param = new InputParam();
					
					param.put("USERID", f_userId);
					param.put("TELNUM", f_telNum);
					
						try {
							mctmDao.updateUserTel(param);
							
							String retCode1 = param.getString(RET_CODE);
							model.addAttribute(RET_CODE, param.getString(RET_CODE));
							model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
							
							if(StringUtils.equals(RET_SUCC, retCode1)){
								try {
									acrDao.updateUserTel(param);
									
									String retCode2 = param.getString(RET_CODE);
									model.addAttribute(RET_CODE, param.getString(RET_CODE));
									model.addAttribute(RET_MSG,  param.getString(RET_MSG));
									
									if(StringUtils.equals(RET_SUCC, retCode2)){
										return true;
									}else {
										model.addAttribute(RET_CODE, param.getString(RET_CODE));
										model.addAttribute(RET_MSG,  param.getString(RET_MSG));
										
										status.setRollbackOnly();
										return false;									
									}
								}catch(Exception e){
									model.addAttribute(RET_CODE, "E");
									model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : fail to acrDao.updateUserTel]");	
									log.error("처리중 문제가 발생했습니다. [Exception : fail to acrDao.updateUserTel]", e);
																
									status.setRollbackOnly();
									return false;	
								}	
							}else {
								model.addAttribute(RET_CODE, param.getString(RET_CODE));
								model.addAttribute(RET_MSG,  param.getString(RET_MSG));
								
								status.setRollbackOnly();
								return false;
							}
						}catch (Exception e){
							model.addAttribute(RET_CODE, "E");
							model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : fail to mctmDao.updateUserTel]");	
							log.error("처리중 문제가 발생했습니다. [Exception : fail to mctmDao.updateUserTel]", e);
														
							status.setRollbackOnly();
							return false;	
						}
					
				}catch(Exception e){
					model.addAttribute(RET_CODE, "E");
					model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : fail to updateUserTel]");	
					log.error("처리중 문제가 발생했습니다. [Exception : fail to updateUserTel]", e);
				    return false;	
					
				}
			}
		});	
		return result;
	}	
	

	@Override
	public void selectgroupRegDept(ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			List<OutputResult> dataList = mctmDao.selectgroupRegDept(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectgroupRegDept]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectgroupRegDept]", e);
		}		
	}	
	
	@Override
	public void updategroupRegDept(String f_DeptFlag, String f_DeptCode, String f_DeptName, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("DEPTCODE", f_DeptCode);
			param.put("DEPTNAME", f_DeptName);
			
			if(f_DeptFlag.equals("insert")) {
				mctmDao.groupRegDeptInsert(param);
				
			}else if(f_DeptFlag.equals("update")) {
				mctmDao.groupRegDeptUpdate(param);
				
			}else if(f_DeptFlag.equals("delete")) {
				mctmDao.groupRegDeptDelete(param);
				
			}
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : updategroupRegDept]");	
			log.error("처리중 문제가 발생했습니다. [Exception : updategroupRegDept]", e);
		}
	}
		
	@Override
	public void selectgroupRegTeam(String f_dept, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("DEPT", f_dept);
			
			List<OutputResult> dataList = mctmDao.selectgroupRegTeam(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectgroupRegTeam]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectgroupRegTeam]", e);
		}		
	}		
	
	@Override
	public void updategroupRegTeam(String f_TeamFlag, String f_TeamCode, String f_TeamName, String f_WorkTime, String f_TeamDeptCode, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("TEAMCODE", f_TeamCode);
			param.put("TEAMNAME", f_TeamName);
			param.put("WORKTIME", f_WorkTime);
			param.put("TEAMDEPTCODE", f_TeamDeptCode);
			
			if(f_TeamFlag.equals("insert")) {
				mctmDao.groupRegTeamInsert(param);
				
			}else if(f_TeamFlag.equals("update")) {
				mctmDao.groupRegTeamUpdate(param);
				
			}else if(f_TeamFlag.equals("delete")) {
				mctmDao.groupRegTeamDelete(param);
				
			}
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : updategroupRegTeam]");	
			log.error("처리중 문제가 발생했습니다. [Exception : updategroupRegTeam]", e);
		}
	}
	
	@Override
	public void selectGroupRegCode(ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			List<OutputResult> dataList = mctmDao.selectGroupRegCode(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectGroupRegCode]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectGroupRegCode]", e);
		}		
	}	
	
	@Override
	public void selectAgentReg(String f_Code, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("CODE", f_Code);
			
			List<OutputResult> dataList = mctmDao.selectAgentReg(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectAgentReg]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectAgentReg]", e);
		}		
	}	
	
	@Override
	public void updateAgentReg(String f_AgentFlag, String f_AgentID, String f_AgentName, String f_AgentTel, String f_AgentPw, String f_AgentPickup, String f_AgentGroup, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("AGENTID", f_AgentID);
			param.put("AGENTNAME", f_AgentName);
			param.put("AGENTTEL", f_AgentTel);
			param.put("AGENTPW", f_AgentPw);
			param.put("AGENTPICKUP", f_AgentPickup);
			param.put("AGENTGROUP", f_AgentGroup);
			
			if(f_AgentFlag.equals("insert")) {
				mctmDao.agentRegInsert(param);
				
			}else if(f_AgentFlag.equals("update")) {
				mctmDao.agentRegUpdate(param);
				
			}else if(f_AgentFlag.equals("delete")) {
				mctmDao.agentRegDelete(param);
				
			}
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : updateAgentReg]");	
			log.error("처리중 문제가 발생했습니다. [Exception : updateAgentReg]", e);
		}
	}
	
	@Override
	public void selectCallStatistic(String f_startDate, String f_endDate, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("STARTDATE", f_startDate);
			param.put("ENDDATE", f_endDate);
			
			List<OutputResult> dataList = mctmDao.selectCallStatistic(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectCallStatistic]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectCallStatistic]", e);
		}		
	}
	
	@Override
	public void selectArsStts(String f_startDate, String f_endDate, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("STARTDATE", f_startDate);
			param.put("ENDDATE", f_endDate);
			
			List<OutputResult> dataList = mctmDao.selectArsStts(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectArsStts]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectArsStts]", e);
		}		
	}
	
	@Override
	public void selectGroupStts(String f_startDate, String f_endDate, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("STARTDATE", f_startDate);
			param.put("ENDDATE", f_endDate);
			
			List<OutputResult> dataList = mctmDao.selectGroupStts(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectGroupStts]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectGroupStts]", e);
		}		
	}	
	
	@Override
	public void selectQueueStts(String f_startDate, String f_endDate, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("STARTDATE", f_startDate);
			param.put("ENDDATE", f_endDate);
			
			List<OutputResult> dataList = mctmDao.selectQueueStts(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectQueueStts]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectQueueStts]", e);
		}		
	}		
	
	@Override
	public void selectServiceLevelStts(String f_startDate, String f_endDate, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("STARTDATE", f_startDate);
			param.put("ENDDATE", f_endDate);
			
			List<OutputResult> dataList = mctmDao.selectServiceLevelStts(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectServiceLevelStts]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectServiceLevelStts]", e);
		}		
	}		
		
	@Override
	public void selectCallbackStts(String f_startDate, String f_endDate, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("STARTDATE", f_startDate);
			param.put("ENDDATE", f_endDate);
			
			List<OutputResult> dataList = mctmDao.selectCallbackStts(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectCallbackStts]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectCallbackStts]", e);
		}		
	}					
	
	@Override
	public void selectCodeReg(ModelMap model) {
		try{
			InputParam param = new InputParam();
			List<OutputResult> dataList = mctmDao.selectCodeReg(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectCodeReg]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectCodeReg]", e);
		}		
	}

	@Override
	public void updateCodeReg(String f_Flag, String f_Category, String f_Code, String f_Name, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("CATEGORY", f_Category);
			param.put("CODE", f_Code);
			param.put("NAME", f_Name);
			
			if(f_Flag.equals("insert")) {
				mctmDao.codeRegInsert(param);
				
			}else if(f_Flag.equals("update")) {
				mctmDao.codeRegUpdate(param);
				
			}else if(f_Flag.equals("delete")) {
				mctmDao.codeRegDelete(param);		
			}
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : updateCodeReg]");	
			log.error("처리중 문제가 발생했습니다. [Exception : updateCodeReg]", e);
		}
	}
	
	@Override
	public void selectArsMonitoring(ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			
			List<OutputResult> dataList = mctmDao.selectArsMonitoring(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectArsMonitoring]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectArsMonitoring]", e);
		}		
	}

	@Override
	public void selectServiceMonitoring(ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			
			List<OutputResult> dataList = mctmDao.selectServiceMonitoring(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectServiceMonitoring]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectServiceMonitoring]", e);
		}		
	}
	
	@Override
	public void selectMctmArs(ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			
			List<OutputResult> dataList = mctmDao.selectMctmArs(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectMctmArs]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectMctmArs]", e);
		}		
	}
	
	@Override
	public void selectMctmAgent(ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			
			List<OutputResult> dataList = mctmDao.selectMctmAgent(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectMctmAgent]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectMctmAgent]", e);
		}		
	}
	
	@Override
	public void selectAgentCallStts(String f_startDate, String f_endDate, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("STARTDATE", f_startDate);
			param.put("ENDDATE", f_endDate);
			
			List<OutputResult> dataList = mctmDao.selectAgentCallStts(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectAgentCallStts]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectAgentCallStts]", e);
		}		
	}

	@Override
	public void selectCallRecodeStts(String f_startDate, String f_endDate, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("STARTDATE", f_startDate);
			param.put("ENDDATE", f_endDate);
			
			List<OutputResult> dataList = mctmDao.selectCallRecodeStts(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectCallRecodeStts]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectCallRecodeStts]", e);
		}		
	}
	
	
}