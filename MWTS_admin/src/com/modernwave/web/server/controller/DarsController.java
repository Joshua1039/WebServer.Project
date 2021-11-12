package com.modernwave.web.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.modernwave.web.server.framework.core.BaseAjaxException;
import com.modernwave.web.server.framework.core.BaseController;
import com.modernwave.web.server.framework.core.BaseDhtmlxException;
import com.modernwave.web.server.framework.dataset.OutputResult;
import com.modernwave.web.server.service.DarsService;

@Controller
@RequestMapping("/dars")
public class DarsController extends BaseController {

	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	DarsService darsService;
	
	/*****************************************************
		시나리오관리 - 멘트관리F
	 *****************************************************/
	//멘트관리 페이지
	@RequestMapping("/ment.do")
	public String ment(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/dars/scenario/ment";
	    }
	}	
	
	//멘트 조회
	@RequestMapping("/mentSelect.do")
	public ModelAndView mentSelect(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			darsService.mentSelect(model);
		}
		
		return jsonResult(model);
	}	
	
	//멘트 업데이트
	@RequestMapping("/updateMent.do")
	public ModelAndView updateMent(String f_Flag, String f_IndexNo, String f_Title, String f_Ment, HttpServletRequest request, ModelMap model) throws BaseAjaxException {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			try {
				darsService.updateMent(f_Flag, f_IndexNo, f_Title, f_Ment, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}
	
	
	/*****************************************************
		근무시간관리 - 휴일관리
	 *****************************************************/	
	//휴일관리 페이지
	@RequestMapping("/holiday.do")
	public String holiday(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/dars/work/holiday";
	    }
	}	
	
	//코드마스터 조회
	@RequestMapping("/selectCodeMst.do")
	public ModelAndView selectCodeMst(String f_Category, HttpServletRequest request, ModelMap model){

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			darsService.selectCodeMst(f_Category, model);
		}
		
		return jsonResult(model);
	}
	
	//휴일관리 조회
	@RequestMapping("/holidaySelect.do")
	public ModelAndView holidaySelect(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			darsService.holidaySelect(model);
		}
		
		return jsonResult(model);
	}	
	
	//휴일관리 업데이트
	@RequestMapping("/updateHoliday.do")
	public ModelAndView updateHoliday(String f_Flag, String f_Month, String f_Day, String f_Data, String f_Description, String f_Site, HttpServletRequest request, ModelMap model) throws BaseAjaxException {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			try {
				darsService.updateHoliday(f_Flag, f_Month, f_Day, f_Data, f_Description, f_Site, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}	
	
	
	/*****************************************************
		근무시간관리 - 근무시간관리
	 *****************************************************/		
	//근무시간관리 페이지
	@RequestMapping("/jobTimes.do")
	public String jobTimes(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/dars/work/jobTimes";
	    }
	}		
	
	//근무시간관리 조회
	@RequestMapping("/jobTimesSelect.do")
	public ModelAndView jobTimesSelect(String f_Site, HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			darsService.jobTimesSelect(f_Site, model);
		}
		
		return jsonResult(model);
	}
	
	//근무시간관리 업데이트
	@RequestMapping("/updateJobTimes.do")
	public ModelAndView updateJobTimes(String f_Site, String f_Weekday, String f_F1FromTime, String f_T1ToTime, String f_F2FromTime, String f_T2ToTime, String f_F3FromTime, String f_T3ToTime, HttpServletRequest request, ModelMap model) throws BaseAjaxException {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			try {
				darsService.updateJobTimes(f_Site, f_Weekday, f_F1FromTime, f_T1ToTime, f_F2FromTime, f_T2ToTime, f_F3FromTime, f_T3ToTime, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}

	
	//근무시간관리 복사
	@RequestMapping("/copyJobTimes.do")
	public ModelAndView copyJobTimes(String f_OriginSite, String f_CopySite, HttpServletRequest request, ModelMap model) throws BaseAjaxException {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			try {
				darsService.copyJobTimes(f_OriginSite, f_CopySite, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}	
	

	/*****************************************************
		IVR통계 - 채널통계
	 *****************************************************/	
	//채널통계 페이지
	@RequestMapping("/trafficCh.do")
	public String trafficCh(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/dars/stats/trafficCh";
	    }
	}	
	
	//채널통계 조회
	@RequestMapping("/trafficChSelect.do")
	public ModelAndView trafficChSelect(String f_startDate, String f_endDate, HttpServletRequest request, ModelMap model){

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			darsService.trafficChSelect(f_startDate, f_endDate, model);
		}
		
		return jsonResult(model);
	}	

	
	/*****************************************************
		IVR통계 - 서비스통계
	 *****************************************************/	
	//서비스통계 페이지
	@RequestMapping("/svcInfo.do")
	public String svcInfo(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/dars/stats/svcInfo";
	    }
	}	

	//서비스통계 조회
	@RequestMapping("/svcInfoSelect.do")
	public ModelAndView svcInfoSelect(String f_startDate, String f_endDate, HttpServletRequest request, ModelMap model){

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			darsService.svcInfoSelect(f_startDate, f_endDate, model);
		}
		
		return jsonResult(model);
	}	
	
	
	/*****************************************************
		IVR이력 - CID별 이력조회
	 *****************************************************/		
	//CID별 이력조회 페이지
	@RequestMapping("/recordCID.do")
	public String recordCID(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/dars/record/recordCID";
	    }
	}		

	//CID별 이력조회 조회
	@RequestMapping("/callHistorySelect.do")
	public ModelAndView callHistorySelect(String f_startDate, String f_endDate, HttpServletRequest request, ModelMap model){

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			darsService.callHistorySelect(f_startDate, f_endDate, model);
		}
		
		return jsonResult(model);
	}	
	
	
	/*****************************************************
		IVR환경설정 - 환경설정
	 *****************************************************/		
	//환경설정 페이지
	@RequestMapping("/config.do")
	public String config(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/dars/config/configuration";
	    }
	}	
	
	//환경설정 테이블 조회
	@RequestMapping("/selectParamTable.do")
	public ModelAndView selectParamTable(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			darsService.selectParamTable(model);
		}
		
		return jsonResult(model);
	}		
	
	//환경설정 조회
	@RequestMapping("/parameterSelect.do")
	public ModelAndView parameterSelect(String f_table, HttpServletRequest request, ModelMap model){

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			darsService.parameterSelect(f_table, model);
		}
		
		return jsonResult(model);
	}
	
	//환경설정 업데이트
	@RequestMapping("/parameterUpdate.do")
	public ModelAndView parameterUpdate(String f_Table, String f_Index, String f_data, String f_description, HttpServletRequest request, ModelMap model) throws BaseAjaxException {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			try {
				darsService.parameterUpdate(f_Table, f_Index, f_data, f_description, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}	
	
	/*****************************************************
		통계 - 서비스통계
	 *****************************************************/			
	//서비스통계 페이지
	@RequestMapping("/serviceStts.do")
	public String serviceStts(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/dars/stats/serviceStts";
	    }
	}		
	
	//서비스통계 조회
	@RequestMapping("/selectServiceStts.do")
	public ModelAndView selectServiceStts(String f_startDate, String f_endDate, HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			darsService.selectServiceStts(f_startDate, f_endDate, model);
		}
		
		return jsonResult(model);
	}		
	
	//서비스통계 차트
	@RequestMapping("/serviceSttsChart.do")
	public String serviceSttsChart(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/dars/stats/serviceSttsChart";
	    }
	}		
	
	
}