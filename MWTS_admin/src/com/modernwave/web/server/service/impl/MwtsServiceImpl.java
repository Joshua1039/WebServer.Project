package com.modernwave.web.server.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.modernwave.web.server.dao.MctmDao;
import com.modernwave.web.server.dao.MwtsDao;
import com.modernwave.web.server.dao.impl.MwtsDaoImpl;
import com.modernwave.web.server.framework.core.BaseService;
import com.modernwave.web.server.framework.dataset.InputParam;
import com.modernwave.web.server.framework.dataset.OutputResult;
import com.modernwave.web.server.model.UserVo;
import com.modernwave.web.server.service.MwtsService;

@Service
public class MwtsServiceImpl extends BaseService implements MwtsService {

	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	MwtsDao mwtsDao;
	
	@Autowired
	MctmDao mctmDao;

	@Autowired
	MwtsDaoImpl mwtsDaoImpl;
	
	@Autowired
	 private TransactionTemplate transactionTemplate_MWTS;
	  
	 public void setTransactionTemplate(TransactionTemplate transactionTemplate){
	  this.transactionTemplate_MWTS = transactionTemplate;
	 }
	 
	/*****************************************************
		Login
	 *****************************************************/
	@Override
	public void loginProc(HttpServletRequest request, String loginId, String password, ModelMap model) {
		try {
			InputParam param = new InputParam();
			
			//String remoteAddr = request.getRemoteAddr();
	        HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
	        String ip = req.getHeader("X-FORWARDED-FOR");
	        if (ip == null)
	            ip = req.getRemoteAddr();
	        
	        param.put("USER_IP", ip);
			param.put("USER_ID", loginId);
			param.put("USER_PW", password);
			mwtsDao.loginProc(param);
	
			String retCode = param.getString(RET_CODE);
			log.debug(retCode + " , "  + RET_SUCC);
			if(StringUtils.equals(RET_SUCC, retCode)){
				//로그인 기록 업데이트
				loginHistory(req, param, model);
			}
			
			model.addAttribute(RET_CODE, retCode);
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : loginProc]");	
			log.error("처리중 문제가 발생했습니다. [Exception : loginProc]", e);
		}			
	}
	
	public void loginHistory(HttpServletRequest request, InputParam param, ModelMap model) {
		try {
			mwtsDao.loginHistory(param);
			
			String retCode2 = param.getString(RET_CODE);
			String retMsg2 = param.getString(RET_MSG);
			
			if(StringUtils.equals(RET_SUCC, retCode2)){
				UserVo userVo = new UserVo();
				userVo.setUserId(param.getString("USER_ID"));
				userVo.setUserName(param.getString("USER_NAME"));
				//userVo.setDeptCode(param.getString("DEPT_CODE"));
				//userVo.setBranchCode(param.getString("BRANCH_CODE"));
				userVo.setGradeCode(param.getString("GRADE"));
				userVo.setUserTheme(param.getString("USER_THEME"));
				userVo.setListenYn(param.getString("LISTEN_YN"));
				userVo.setSaveYn(param.getString("SAVE_YN"));
				log.debug("set session user info["+userVo.toString()+"]");
				setRequestAttribute(SESSION_USER, userVo);
			}
			model.addAttribute(RET_CODE, retCode2);
			model.addAttribute(RET_MSG,  retMsg2);
		} catch (Exception e) {
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : loginHistory]");	
			log.error("처리중 문제가 발생했습니다. [Exception : loginHistory]", e);
		}		
	}
	
	@Override
	public void modifyPwProc(String currentPw, String modifyPw, String modifyPwChk, ModelMap model) {
		try{
			InputParam param = new InputParam();

			String userId = getUserId();
			param.put("USER_ID", userId);
			param.put("CURRENT_PW", currentPw);
			param.put("MODIFY_PW", modifyPw);
			param.put("MODIFY_PW_CHK", modifyPwChk);
			mwtsDao.modifyPwProc(param);
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));		
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : modifyPwProc]");	
			log.error("처리중 문제가 발생했습니다. [Exception : modifyPwProc]", e);
		}		
	}

	@Override
	public void menuSelect(String userId, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			List<OutputResult> menuList = mwtsDao.menuSelect(param);
			
			model.addAttribute("menuList", menuList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : menuSelect]");	
			log.error("처리중 문제가 발생했습니다. [Exception : menuSelect]", e);
		}		
	}
	
	@Override
	public void userSelect(ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			List<OutputResult> userList = mwtsDao.userSelect(param);
			
			model.addAttribute("userList", userList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : userSelect]");	
			log.error("처리중 문제가 발생했습니다. [Exception : userSelect]", e);
		}		
	}
	
	@Override
	public void selectMenuAuth(String f_userId, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", f_userId);
			List<OutputResult> dataList = mwtsDao.selectMenuAuth(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectMenuAuth]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectMenuAuth]", e);
		}		
	}
	
	@Override
	public void updateMenuAuth(String idx, String f_userId, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USER_ID", f_userId);
			param.put("IDX", idx);
			mwtsDao.updateMenuAuth(param);
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : updateMenuAuth]");	
			log.error("처리중 문제가 발생했습니다. [Exception : updateMenuAuth]", e);
		}
	}

	@Override
	public void selectTheme(ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			List<OutputResult> dataList = mwtsDao.selectTheme(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectTheme]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectTheme]", e);
		}		
	}
	
	@Override
	public void updateTheme(String theme, String userId, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USER_ID", getUserId());
			param.put("USER_THEME", theme);
			mwtsDao.updateTheme(param);
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : updateTheme]");	
			log.error("처리중 문제가 발생했습니다. [Exception : updateTheme]", e);
		}
	}
	
	@Override
	public void updateCodeMaster(String f_Flag, String f_Category, String f_Code, String f_Name, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("CATEGORY", f_Category);
			param.put("CODE", f_Code);
			param.put("NAME", f_Name);
			
			if(f_Flag.equals("insert")) {
				mwtsDao.codeMasterInsert(param);
				
			}else if(f_Flag.equals("update")) {
				mwtsDao.codeMasterUpdate(param);
				
			}else if(f_Flag.equals("delete")) {
				mwtsDao.codeMasterDelete(param);		
			}
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : updateCodeMaster]");	
			log.error("처리중 문제가 발생했습니다. [Exception : updateCodeMaster]", e);
		}
	}
	
	@Override
	public void selectCodeMaster(ModelMap model) {
		try{
			InputParam param = new InputParam();
			List<OutputResult> dataList = mwtsDao.selectCodeMaster(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectCodeMaster]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectCodeMaster]", e);
		}		
	}

	@Override
	public void updateUserInfo(String f_Flag, String f_UserID, String f_UserName, String f_ListenYN, String f_SaveYN, String f_UseYN, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("USERID", f_UserID);
			param.put("USERNAME", f_UserName);
			param.put("LISTENYN", f_ListenYN);
			param.put("SAVEYN", f_SaveYN);
			param.put("USEYN", f_UseYN);
			
			if(f_Flag.equals("insert")) {
				mwtsDao.userInfoInsert(param);
				
			}else if(f_Flag.equals("update")) {
				mwtsDao.userInfoUpdate(param);
				
			}else if(f_Flag.equals("delete")) {
				mwtsDao.userInfoDelete(param);
				
			}else if(f_Flag.equals("resetPW")){
				mwtsDao.userInfoResetPW(param);
			}
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : updateUserInfo]");	
			log.error("처리중 문제가 발생했습니다. [Exception : updateUserInfo]", e);
		}
	}
	
	
	
	
	
}