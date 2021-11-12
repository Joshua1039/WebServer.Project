package com.modernwave.web.server.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.modernwave.web.server.framework.dataset.OutputResult;

@Transactional

public interface MwtsService{

	public void loginProc(HttpServletRequest request, String loginId, String password, ModelMap model);
	
	public void modifyPwProc(String currentPw, String modifyPw, String modifyPwChk, ModelMap model);

	public void menuSelect(String userId, ModelMap model);
	
	public void userSelect(ModelMap model);
	
	public void selectMenuAuth(String f_userId, ModelMap model);
	
	public void updateMenuAuth(String idx, String f_userId, HttpServletRequest request,ModelMap model);
	
	public void selectTheme(ModelMap model);
	
	public void updateTheme(String theme, String userId, HttpServletRequest request,ModelMap model);
	
	public void updateCodeMaster(String f_Flag, String f_Category, String f_Code, String f_Name, HttpServletRequest request,ModelMap model);
	
	public void selectCodeMaster(ModelMap model);
	
	public void updateUserInfo(String f_Flag, String f_UserID, String f_UserName, String f_ListenYN, String f_SaveYN, String f_UseYN, HttpServletRequest request,ModelMap model);
	
	
}