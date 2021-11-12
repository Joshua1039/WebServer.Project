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
import com.modernwave.web.server.service.MctmService;

@Controller
@RequestMapping("/mctm")
public class MctmController extends BaseController {

	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	MctmService mctmService;
	
	/*****************************************************
		MCTM 실시간 Agent조회
	 *****************************************************/	
	//agent 조회
	@RequestMapping("/selectRealTimeVAgent.do")
	public ModelAndView selectRealTimeVAgent(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectRealTimeVAgent(model);
		}
		
		return jsonResult(model);
	}
	
	/*****************************************************
		모니터링 - 콜백관리
	 *****************************************************/		
	//콜백관리 페이지
	@RequestMapping("/predialog.do")
	public String predialog(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/monitoring/predialog";
	    }
	}		
	
	//그룹 조회
	@RequestMapping("/selectVGroup.do")
	public ModelAndView selectVGroup(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectVGroup(model);
		}
		
		return jsonResult(model);
	}		
	
	//담당자 조회
	@RequestMapping("/selectVagent.do")
	public ModelAndView selectVagent(String f_GroupName, HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectVagent(f_GroupName, model);
		}
		
		return jsonResult(model);
	}		
		
	//처리결과 조회
	@RequestMapping("/selectCodeMaster.do")
	public ModelAndView selectCodeMaster(String f_LD, HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectCodeMaster(f_LD, model);
		}
		
		return jsonResult(model);
	}		
	
	//콜백관리 조회
/*	@RequestMapping("/selectPredialog.do")
	public ModelAndView selectPredialog(String f_Distribute, String f_Result, String f_Agent, String f_CallbackNum, String f_Service, String f_start_date, String f_end_date, ModelMap model) {
		mctmService.selectPredialog(f_Distribute, f_Result, f_Agent, f_CallbackNum, f_Service, f_start_date, f_end_date, model);
		return jsonResult(model);
	}*/		
	@RequestMapping("/selectPredialog.do")
	public ModelAndView selectPredialog(String f_start_date, String f_end_date, HttpServletRequest request, ModelMap model) {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectPredialog(f_start_date, f_end_date, model);
		}
		
		return jsonResult(model);
	}		
	
	//콜백관리 삭제
	@RequestMapping("/deletePredialog.do")
	public ModelAndView deletePredialog(String f_Idx, HttpServletRequest request, ModelMap model) throws BaseAjaxException {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			try {
				mctmService.deletePredialog(f_Idx, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}		
	
	//콜백관리 저장
	@RequestMapping("/updatePredialog.do")
	public ModelAndView deletePredialog(String f_IndexNo, String f_UserId, String f_UserName, String f_ResultUpdate, String f_Memo, HttpServletRequest request, ModelMap model) throws BaseAjaxException {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			try {
				mctmService.updatePredialog(f_IndexNo, f_UserId, f_UserName, f_ResultUpdate, f_Memo, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}
	
	//콜백처리 담당자 적용
	@RequestMapping("/updatePredialogAgentReg.do")
	public ModelAndView updatePredialogAgentReg(String f_agentId, HttpServletRequest request, ModelMap model) throws BaseAjaxException {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			try {
				mctmService.updatePredialogAgentReg(f_agentId, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}		
	
	//콜백 분배
	@RequestMapping("/distributePredialog.do")
	public ModelAndView distributePredialog(String f_IndexNo, String f_DisAgentID, String f_DisAgentName,HttpServletRequest request, ModelMap model) throws BaseAjaxException {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			try {
				mctmService.distributePredialog(f_IndexNo, f_DisAgentID, f_DisAgentName, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}		
		
	/*****************************************************
		관리자 - 조회대상자 관리 - 조회대상자 설정
	 *****************************************************/		
	//내선번호 조회
	@RequestMapping("/selectUserTel.do")
	public ModelAndView selectUserTel(String f_userId, HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectUserTel(f_userId, model);
		}
		
		return jsonResult(model);
	}	
	
	//내선번호 업데이트
	@RequestMapping("/updateUserTel.do")
	public ModelAndView updateUserTel(String f_userId, String f_telNum, HttpServletRequest request, ModelMap model) throws BaseAjaxException {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			try {
				mctmService.updateUserTel(f_userId, f_telNum, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}		
	
	/*****************************************************
		시스템관리 - 부서팀관리
	 *****************************************************/		
	//부서팀관리 페이지
	@RequestMapping("/groupReg.do")
	public String groupReg(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/system/groupReg";
	    }
	}
		
	//부서 조회
	@RequestMapping("/selectgroupRegDept.do")
	public ModelAndView selectgroupRegDept(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectgroupRegDept(model);
		}
		
		return jsonResult(model);
	}	

	//부서 업데이트
	@RequestMapping("/updategroupRegDept.do")
	public ModelAndView updategroupRegDept(String f_DeptFlag, String f_DeptCode, String f_DeptName, HttpServletRequest request, ModelMap model) throws BaseAjaxException {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			try {
				mctmService.updategroupRegDept(f_DeptFlag, f_DeptCode, f_DeptName, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}		
	
	//팀 조회
	@RequestMapping("/selectgroupRegTeam.do")
	public ModelAndView selectgroupRegTeam(String f_dept, HttpServletRequest request, ModelMap model){

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectgroupRegTeam(f_dept, model);
		}
		
		return jsonResult(model);
	}		
	
	//팀 업데이트
	@RequestMapping("/updategroupRegTeam.do")
	public ModelAndView updategroupRegTeam(String f_TeamFlag, String f_TeamCode, String f_TeamName, String f_WorkTime, String f_TeamDeptCode, HttpServletRequest request, ModelMap model) throws BaseAjaxException {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			try {
				mctmService.updategroupRegTeam(f_TeamFlag, f_TeamCode, f_TeamName, f_WorkTime, f_TeamDeptCode, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}		
		
	/*****************************************************
		시스템관리 - 상담사관리
	 *****************************************************/		
	//상담사관리 페이지
	@RequestMapping("/agentReg.do")
	public String agentReg(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/system/agentReg";
	    }
	}	
		
	//팀,부서 조회
	@RequestMapping("/selectGroupRegCode.do")
	public ModelAndView selectGroupRegCode(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectGroupRegCode(model);
		}
		
		return jsonResult(model);
	}		
	
	//상담사 조회
	@RequestMapping("/selectAgentReg.do")
	public ModelAndView selectAgentReg(String f_Code, HttpServletRequest request, ModelMap model){

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectAgentReg(f_Code, model);
		}
		
		return jsonResult(model);
	}			
	
	//상담사 업데이트
	@RequestMapping("/updateAgentReg.do")
	public ModelAndView updateAgentReg(String f_AgentFlag, String f_AgentID, String f_AgentName, String f_AgentTel, String f_AgentPw, String f_AgentPickup, String f_AgentGroup, HttpServletRequest request, ModelMap model) throws BaseAjaxException {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			try {
				mctmService.updateAgentReg(f_AgentFlag, f_AgentID, f_AgentName, f_AgentTel, f_AgentPw, f_AgentPickup, f_AgentGroup, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}
	
	/*****************************************************
		통계 - 콜시간통계
	 *****************************************************/			
	//콜시간통계 페이지
	@RequestMapping("/callStatistic.do")
	public String callStatistic(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/stats/callStatistic";
	    }
	}		
	
	//콜시간통계 조회
	@RequestMapping("/selectCallStatistic.do")
	public ModelAndView selectCallStatistic(String f_startDate, String f_endDate, HttpServletRequest request, ModelMap model){

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectCallStatistic(f_startDate, f_endDate, model);
		}
		
		return jsonResult(model);
	}
	
	//콜시간통계 차트
	@RequestMapping("/callStatisticChart.do")
	public String callStatisticChart(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/stats/callStatisticChart";
	    }
	}
	
	//콜시간통계 차트(IE 브라우저)
	@RequestMapping("/callStatisticChartIE.do")
	public String callStatisticChartIE(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/stats/callStatisticChartIE";
	    }
	}	
	
	/*****************************************************
		통계 - ARS통계
	 *****************************************************/		
	//ARS통계 페이지
	@RequestMapping("/arsStts.do")
	public String arsStts(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/stats/arsStts";
	    }
	}		
	
	//ARS통계 조회
	@RequestMapping("/selectArsStts.do")
	public ModelAndView selectArsStts(String f_startDate, String f_endDate, HttpServletRequest request, ModelMap model){

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectArsStts(f_startDate, f_endDate, model);
		}
		
		return jsonResult(model);
	}	
	
	//ARS통계 차트
	@RequestMapping("/arsSttsChart.do")
	public String arsSttsChart(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/stats/arsSttsChart";
	    }
	}	
	
	//ARS통계 차트 (IE 브라우저)
	@RequestMapping("/arsSttsChartIE.do")
	public String arsSttsChartIE(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/stats/arsSttsChartIE";
	    }
	}	
	
	/*****************************************************
		통계 - 그룹통계
	 *****************************************************/			
	//그룹통계 페이지
	@RequestMapping("/groupStts.do")
	public String groupStts(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/stats/groupStts";
	    }
	}		
	
	//그룹통계 조회
	@RequestMapping("/selectGroupStts.do")
	public ModelAndView selectGroupStts(String f_startDate, String f_endDate, HttpServletRequest request, ModelMap model){

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectGroupStts(f_startDate, f_endDate, model);
		}
		
		return jsonResult(model);
	}	
		
	//그룹통계 차트
	@RequestMapping("/groupSttsChart.do")
	public String groupSttsChart(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/stats/groupSttsChart";
	    }
	}		
	
	//그룹통계 차트(IE 브라우저)
	@RequestMapping("/groupSttsChartIE.do")
	public String groupSttsChartIE(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/stats/groupSttsChartIE";
	    }
	}	
	
	/*****************************************************
		통계 - 큐통계
	 *****************************************************/			
	//큐통계 페이지
	@RequestMapping("/queueStts.do")
	public String queueStts(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/stats/queueStts";
	    }
	}		
	
	//큐통계 조회
	@RequestMapping("/selectQueueStts.do")
	public ModelAndView selectQueueStts(String f_startDate, String f_endDate, HttpServletRequest request, ModelMap model){

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectQueueStts(f_startDate, f_endDate, model);
		}
		
		return jsonResult(model);
	}		
	
	//큐통계 차트
	@RequestMapping("/queueSttsChart.do")
	public String queueSttsChart(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/stats/queueSttsChart";
	    }
	}			

	//큐통계 차트(IE 브라우저)
	@RequestMapping("/queueSttsChartIE.do")
	public String queueSttsChartIE(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/stats/queueSttsChartIE";
	    }
	}		
	
	/*****************************************************
		통계 - 서비스레벨통계
	 *****************************************************/			
	//서비스레벨통계 페이지
	@RequestMapping("/serviceLevelStts.do")
	public String serviceLevelStts(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/stats/serviceLevelStts";
	    }
	}		
	
	//서비스레벨통계 조회
	@RequestMapping("/selectServiceLevelStts.do")
	public ModelAndView selectServiceLevelStts(String f_startDate, String f_endDate, HttpServletRequest request, ModelMap model){

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectServiceLevelStts(f_startDate, f_endDate, model);
		}

		return jsonResult(model);
	}		
	
	//서비스레벨통계 차트
	@RequestMapping("/serviceLevelSttsChart.do")
	public String serviceLevelSttsChart(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/stats/serviceLevelSttsChart";
	    }
	}			

	//서비스레벨통계 차트(IE 브라우저)
	@RequestMapping("/serviceLevelSttsChartIE.do")
	public String serviceLevelSttsChartIE(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/stats/serviceLevelSttsChartIE";
	    }
	}	
	
	/*****************************************************
		통계 - 콜백통계
	 *****************************************************/			
	//콜백통계 페이지
	@RequestMapping("/callbackStts.do")
	public String callbackStts(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/stats/callbackStts";
	    }
	}		
	
	//콜백통계 조회
	@RequestMapping("/selectCallbackStts.do")
	public ModelAndView selectCallbackStts(String f_startDate, String f_endDate, HttpServletRequest request, ModelMap model){

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectCallbackStts(f_startDate, f_endDate, model);
		}
		
		return jsonResult(model);
	}		
	
	//콜백통계 차트
	@RequestMapping("/callbackSttsChart.do")
	public String callbackSttsChart(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/stats/callbackSttsChart";
	    }
	}		

	//콜백통계 차트(IE 브라우저)
	@RequestMapping("/callbackSttsChartIE.do")
	public String callbackSttsChartIE(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/stats/callbackSttsChartIE";
	    }
	}		
	
	/*****************************************************
		코드마스터 설정
	 *****************************************************/	
	//코드마스터 페이지
	@RequestMapping("/codeReg.do")
	public String codeReg(HttpServletRequest request, ModelMap model) {
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/system/codeReg";
	    }
	}
	
	//코드마스터 조회
	@RequestMapping("/selectCodeReg.do")
	public ModelAndView selectCodeReg(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectCodeReg(model);
		}
		
		return jsonResult(model);
	}
	
	//코드마스터 업데이트
	@RequestMapping("/updateCodeReg.do")
	public ModelAndView updateCodeReg(String f_Flag, String f_Category, String f_Code, String f_Name, HttpServletRequest request, ModelMap model) throws BaseAjaxException {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			try {
				mctmService.updateCodeReg(f_Flag, f_Category, f_Code, f_Name, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}			
	
	/*****************************************************
		모니터링 - ARS 모니터링
	 *****************************************************/	
	//ARS 모니터링 페이지
	@RequestMapping("/arsMonitoring.do")
	public String arsMonitoring(HttpServletRequest request, ModelMap model) {
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/monitoring/arsMonitoring";
	    }
	}

	//ARS통계 조회
	@RequestMapping("/selectArsMonitoring.do")
	public ModelAndView selectArsMonitoring(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectArsMonitoring(model);
		}
		
		return jsonResult(model);
	}

	//서비스레벨통계 조회
	@RequestMapping("/selectServiceMonitoring.do")
	public ModelAndView selectServiceMonitoring(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectServiceMonitoring(model);
		}
		
		return jsonResult(model);
	}

	/*****************************************************
		모니터링 - MCTM 모니터링
	 *****************************************************/	
	//MCTM 모니터링 페이지
	@RequestMapping("/mctmMonitoring.do")
	public String mctmMonitoring(HttpServletRequest request, ModelMap model) {
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/monitoring/mctmMonitoring";
	    }
	}

	//ARS 조회
	@RequestMapping("/selectMctmArs.do")
	public ModelAndView selectMctmArs(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectMctmArs(model);
		}

		return jsonResult(model);
	}	
	
	//사용자 조회
	@RequestMapping("/selectMctmAgent.do")
	public ModelAndView selectMctmAgent(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectMctmAgent(model);
		}
		
		return jsonResult(model);
	}		
	
	/*****************************************************
		통계 - 상담원 콜시간통계
	 *****************************************************/			
	//상담원 콜시간통계 페이지
	@RequestMapping("/agentCallStts.do")
	public String agentCallStts(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
	
	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/stats/agentCallStts";
	    }
	}		
	
	//상담원 콜시간통계 조회
	@RequestMapping("/selectAgentCallStts.do")
	public ModelAndView selectAgentCallStts(String f_startDate, String f_endDate, HttpServletRequest request, ModelMap model){
	
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectAgentCallStts(f_startDate, f_endDate, model);
		}
		
		return jsonResult(model);
	}	
	
	/*****************************************************
		통계 - 콜 이력 조회
	 *****************************************************/			
	//콜 이력 조회 페이지
	@RequestMapping("/callRecodeStts.do")
	public String callRecodeStts(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
	
	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mctm/stats/callRecodeStts";
	    }
	}		
	
	//콜 이력 조회 조회
	@RequestMapping("/selectCallRecodeStts.do")
	public ModelAndView selectCallRecodeStts(String f_startDate, String f_endDate, HttpServletRequest request, ModelMap model){
	
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mctmService.selectCallRecodeStts(f_startDate, f_endDate, model);
		}
		
		return jsonResult(model);
	}	
	
}