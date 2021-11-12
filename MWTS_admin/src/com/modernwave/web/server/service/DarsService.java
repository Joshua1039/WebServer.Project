package com.modernwave.web.server.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.modernwave.web.server.framework.dataset.OutputResult;

@Transactional

public interface DarsService{
	
	public void mentSelect(ModelMap model);
	
	public void updateMent(String f_Flag, String f_IndexNo, String f_Title, String f_Ment, HttpServletRequest request,ModelMap model);
	
	public void selectCodeMst(String f_Category, ModelMap model);
	
	public void holidaySelect(ModelMap model);
	
	public void updateHoliday(String f_Flag, String f_Month, String f_Day, String f_Data, String f_Description, String f_Site, HttpServletRequest request,ModelMap model);
	
	public void jobTimesSelect(String f_Site, ModelMap model);
	
	public void updateJobTimes(String f_Site, String f_Weekday, String f_F1FromTime, String f_T1ToTime, String f_F2FromTime, String f_T2ToTime, String f_F3FromTime, String f_T3ToTime, HttpServletRequest request,ModelMap model);
	
	public void copyJobTimes(String f_OriginSite, String f_CopySite, HttpServletRequest request,ModelMap model);
	
	public void trafficChSelect(String f_startDate, String f_endDate, ModelMap model);
	
	public void callHistorySelect(String f_startDate, String f_endDate, ModelMap model);
	
	public void svcInfoSelect(String f_startDate, String f_endDate, ModelMap model);
	
	public void selectParamTable(ModelMap model);
	
	public void parameterSelect(String f_table, ModelMap model);
	
	public void parameterUpdate(String f_Table, String f_Index, String f_data, String f_description, HttpServletRequest request,ModelMap model);
	
	public void selectServiceStts(String f_startDate, String f_endDate, ModelMap model);	
	
	
}