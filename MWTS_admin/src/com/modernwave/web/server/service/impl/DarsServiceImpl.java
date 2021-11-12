package com.modernwave.web.server.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.modernwave.web.server.dao.DarsDao;
import com.modernwave.web.server.framework.core.BaseService;
import com.modernwave.web.server.framework.dataset.InputParam;
import com.modernwave.web.server.framework.dataset.OutputResult;
import com.modernwave.web.server.service.DarsService;

@Service
public class DarsServiceImpl extends BaseService implements DarsService {

	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	DarsDao darsDao;
	
	@Override
	public void mentSelect(ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			List<OutputResult> dataList = darsDao.mentSelect(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : mentSelect]");	
			log.error("처리중 문제가 발생했습니다. [Exception : mentSelect]", e);
		}		
	}
	
	
	@Override
	public void updateMent(String f_Flag, String f_IndexNo, String f_Title, String f_Ment, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("INDEX", f_IndexNo);
			param.put("TITLE", f_Title);
			param.put("MENT", f_Ment);
			
			if(f_Flag.equals("insert")) {
				darsDao.mentInsert(param);
				
			}else if(f_Flag.equals("update")) {
				darsDao.mentUpdate(param);
				
			}else if(f_Flag.equals("delete")) {
				darsDao.mentDelete(param);
				
			}
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : updateMent]");	
			log.error("처리중 문제가 발생했습니다. [Exception : updateMent]", e);
		}
	}

	
	@Override
	public void selectCodeMst(String f_Category, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			
			if(f_Category.equals("site")) {
				List<OutputResult> siteList = darsDao.codeMstSiteList(param);
				model.addAttribute("siteList", siteList);
				
			}else if(f_Category.equals("holiday")) {
				List<OutputResult> holidayList = darsDao.codeMstHolidayList(param);
				model.addAttribute("holidayList", holidayList);			
			}
				
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectCodeMst]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectCodeMst]", e);
		}		
	}
	

	@Override
	public void holidaySelect(ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			List<OutputResult> dataList = darsDao.holidaySelect(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : holidaySelect]");	
			log.error("처리중 문제가 발생했습니다. [Exception : holidaySelect]", e);
		}		
	}
	
	
	@Override
	public void updateHoliday(String f_Flag, String f_Month, String f_Day, String f_Data, String f_Description, String f_Site, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("MONTH", f_Month);
			param.put("DAY", f_Day);
			param.put("DATA", f_Data);
			param.put("DESCRIPTION", f_Description);
			param.put("SITE", f_Site);
			
			if(f_Flag.equals("insert")) {
				darsDao.holidayInsert(param);
					
			}else if(f_Flag.equals("delete")) {
				darsDao.holidayDelete(param);
				
			}
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : updateHoliday]");	
			log.error("처리중 문제가 발생했습니다. [Exception : updateHoliday]", e);
		}
	}
	
	
	@Override
	public void jobTimesSelect(String f_Site, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("SITE", f_Site);
			
			List<OutputResult> dataList = darsDao.jobTimesSelect(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : jobTimesSelect]");	
			log.error("처리중 문제가 발생했습니다. [Exception : jobTimesSelect]", e);
		}		
	}	
	

	@Override
	public void updateJobTimes(String f_Site, String f_Weekday, String f_F1FromTime, String f_T1ToTime, String f_F2FromTime, String f_T2ToTime, String f_F3FromTime, String f_T3ToTime, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("SITE", f_Site);
			param.put("WEEKDAY", f_Weekday);
			param.put("F1FROMTIME", f_F1FromTime);
			param.put("T1TOTIME", f_T1ToTime);
			param.put("F2FROMTIME", f_F2FromTime);
			param.put("T2TOTIME", f_T2ToTime);
			param.put("F3FROMTIME", f_F3FromTime);
			param.put("T3TOTIME", f_T3ToTime);
			
			darsDao.updateJobTimes(param);
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : updateJobTimes]");	
			log.error("처리중 문제가 발생했습니다. [Exception : updateJobTimes]", e);
		}
	}
	
	
	@Override
	public void copyJobTimes(String f_OriginSite, String f_CopySite, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("ORIGINSITE", f_OriginSite);
			param.put("COPYSITE", f_CopySite);
			
			darsDao.copyJobTimes(param);
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : copyJobTimes]");	
			log.error("처리중 문제가 발생했습니다. [Exception : copyJobTimes]", e);
		}
	}

	
	@Override
	public void trafficChSelect(String f_startDate, String f_endDate, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("STARTDATE", f_startDate);
			param.put("ENDDATE", f_endDate);
			
			List<OutputResult> dataList = darsDao.trafficChSelect(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : trafficChSelect]");	
			log.error("처리중 문제가 발생했습니다. [Exception : trafficChSelect]", e);
		}		
	}	

	
	@Override
	public void callHistorySelect(String f_startDate, String f_endDate, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("STARTDATE", f_startDate);
			param.put("ENDDATE", f_endDate);
			
			List<OutputResult> dataList = darsDao.callHistorySelect(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : callHistorySelect]");	
			log.error("처리중 문제가 발생했습니다. [Exception : callHistorySelect]", e);
		}		
	}	
	
	
	@Override
	public void svcInfoSelect(String f_startDate, String f_endDate, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("STARTDATE", f_startDate);
			param.put("ENDDATE", f_endDate);
			
			List<OutputResult> dataList = darsDao.svcInfoSelect(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : svcInfoSelect]");	
			log.error("처리중 문제가 발생했습니다. [Exception : svcInfoSelect]", e);
		}		
	}	
	

	@Override
	public void selectParamTable(ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			List<OutputResult> dataList = darsDao.selectParamTable(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectParamTable]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectParamTable]", e);
		}		
	}
	

	@Override
	public void parameterSelect(String f_table, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("TABLE", f_table);
			
			List<OutputResult> dataList = darsDao.parameterSelect(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : parameterSelect]");	
			log.error("처리중 문제가 발생했습니다. [Exception : parameterSelect]", e);
		}		
	}
	

	@Override
	public void parameterUpdate(String f_Table, String f_Index, String f_data, String f_description, HttpServletRequest request,  ModelMap model) {
		try{
			InputParam param = new InputParam();
			
			param.put("TABLE", f_Table);
			param.put("INDEX", f_Index);
			param.put("DATA", f_data);
			param.put("DESCRIPTION", f_description);
			
			darsDao.parameterUpdate(param);
			
			model.addAttribute(RET_CODE, param.getString(RET_CODE));
			model.addAttribute(RET_MSG,  param.getString(RET_MSG));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : parameterUpdate]");	
			log.error("처리중 문제가 발생했습니다. [Exception : parameterUpdate]", e);
		}
	}
	
	
	@Override
	public void selectServiceStts(String f_startDate, String f_endDate, ModelMap model) {
		try{
			InputParam param = new InputParam();
			param.put("USERID", getUserId());
			param.put("STARTDATE", f_startDate);
			param.put("ENDDATE", f_endDate);
			
			List<OutputResult> dataList = darsDao.selectServiceStts(param);
			
			model.addAttribute("dataList", dataList);
			model.addAttribute(RET_MSG, param.get(RET_MSG));
			model.addAttribute(RET_CODE, param.get(RET_CODE));	
		}catch(Exception e){
			model.addAttribute(RET_CODE, "E");
			model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : selectServiceStts]");	
			log.error("처리중 문제가 발생했습니다. [Exception : selectServiceStts]", e);
		}
	}
	
	
	
	
}