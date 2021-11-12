package com.modernwave.web.server.controller;

import java.io.IOException;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.modernwave.web.server.service.AcrService;

import kr.co.modernwave.filetransfer.client.Client;
import kr.co.modernwave.filetransfer.client.Client.ConvertTypes;
import kr.co.modernwave.filetransfer.client.Client.FileNotFound;
import kr.co.modernwave.filetransfer.client.Client.InternalError;
import kr.co.modernwave.filetransfer.client.Client.TransportTypes;
import com.modernwave.web.server.controller.RSAUtil;
import com.modernwave.web.server.controller.RSA;

@Controller
@RequestMapping("/acr")
public class AcrController extends BaseController {

	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	AcrService acrService;

	@Autowired
	RSAUtil rsaUtil;
	
	@Autowired
	RSA rsa;
	
	/*****************************************************
		환경설정 - 서버관리
	 *****************************************************/		
	//서버관리 페이지
	@RequestMapping("/serverInfo.do")
	public String serverInfo(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/acr/config/serverInfo";
	    }
	}		

	//서버관리 조회
	@RequestMapping("/selectServerInfo.do")
	public ModelAndView selectServerInfo(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			acrService.selectServerInfo(model);
		}
		
		return jsonResult(model);
	}	
	
	//서버관리 업데이트
	@RequestMapping("/updateServerInfo.do")
	public ModelAndView updateServerInfo(String f_Flag, String f_ServerCode, String f_ServerName, String f_ServerIp, String f_FTPId, String f_FTPPw,  String f_FTPPort,  String f_UseYN, HttpServletRequest request, ModelMap model) throws BaseAjaxException {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			try {
				acrService.updateServerInfo(f_Flag, f_ServerCode, f_ServerName, f_ServerIp, f_FTPId, f_FTPPw, f_FTPPort, f_UseYN, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}		

	
	/*****************************************************
		환경설정 - 회사관리
	 *****************************************************/		
	//회사관리 페이지
	@RequestMapping("/branch.do")
	public String branch(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/acr/config/branch";
	    }
	}		
	
	//회사관리 조회
	@RequestMapping("/selectBranchCode.do")
	public ModelAndView selectBranchCode(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			acrService.selectBranchCode(model);
		}
		
		return jsonResult(model);
	}		
	
	//회사관리 업데이트
	@RequestMapping("/updateBranchCode.do")
	public ModelAndView updateBranchCode(String f_Flag, String f_BranchCode, String f_BranchName, String f_UseYN, HttpServletRequest request, ModelMap model) throws BaseAjaxException {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			try {
				acrService.updateBranchCode(f_Flag, f_BranchCode, f_BranchName, f_UseYN, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}	
	
	
	/*****************************************************
		환경설정 - 부서관리
	 *****************************************************/		
	//부서관리 페이지
	@RequestMapping("/dept.do")
	public String dept(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/acr/config/dept";
	    }
	}		
	
	//부서관리 조회
	@RequestMapping("/selectDept.do")
	public ModelAndView selectDept(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			acrService.selectDept(model);
		}
		
		return jsonResult(model);
	}		
	
	//부서관리 업데이트
	@RequestMapping("/updateDept.do")
	public ModelAndView updateDept(String f_Flag, String f_ID1, String f_ID2, String f_ID3, String f_ID4, String f_ID5, String f_ID6, String f_ID7, String f_NM1, String f_NM2, String f_NM3, String f_NM4, String f_NM5, String f_NM6, String f_NM7, String f_DeptId , String f_DeptName , String f_DeptLevel , String f_Seq , String f_Useyn, HttpServletRequest request, ModelMap model) throws BaseAjaxException {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			try {
				acrService.updateDept(f_Flag, f_ID1, f_ID2, f_ID3, f_ID4, f_ID5, f_ID6, f_ID7, f_NM1, f_NM2, f_NM3, f_NM4, f_NM5, f_NM6, f_NM7, f_DeptId, f_DeptName, f_DeptLevel, f_Seq, f_Useyn, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}		
	
	
	
	/*****************************************************
		환경설정 - 채널관리
	 *****************************************************/		
	//채널관리 페이지
	@RequestMapping("/vpmInfo.do")
	public String vpmInfo(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/acr/config/vpmInfo";
	    }
	}			
		
	//채널관리 조회
	@RequestMapping("/selectVpmInfo.do")
	public ModelAndView selectVpmInfo(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			acrService.selectVpmInfo(model);
		}
		
		return jsonResult(model);
	}		
	
	//채널관리 업데이트
	@RequestMapping("/updateVpmInfo.do")
	public ModelAndView updateVpmInfo(String f_Flag, String f_ServerCode, String f_VpmNum, String f_TelNum, String f_Extension, String f_TelPw, String f_IpmAgentIp, String f_AgentId, HttpServletRequest request, ModelMap model) throws BaseAjaxException {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			try {
				acrService.updateVpmInfo(f_Flag, f_ServerCode, f_VpmNum, f_TelNum, f_Extension, f_TelPw, f_IpmAgentIp, f_AgentId, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}	

	
	/*****************************************************
		환경설정 - 대상자관리
	 *****************************************************/		
	//대상자관리 페이지
	@RequestMapping("/agentInfo.do")
	public String agentInfo(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		PrivateKey key = (PrivateKey) session.getAttribute("RSAprivateKey");
		Object loginSession = session.getAttribute("SESSION_USER");
		
		if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다.");
			return "/mwts/error/error";
		}else {
			//메인페이지의 하위에 위치하는 페이지로 RSA 키를 새로 생성하지 않음
			if (key == null) {
				model.addAttribute("MESSAGE", "비정상적인 접근 입니다.");
				return "/mwts/error/error";			
			}else {
			    model.addAttribute("exponent", (String) session.getAttribute("exponent"));
			    model.addAttribute("modulus", (String) session.getAttribute("modulus"));
			    return "/acr/config/agentInfo";
			}			
		}
	}	

	//대상자관리 조회
	@RequestMapping("/selectAgentInfo.do")
	public ModelAndView selectAgentInfo(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			acrService.selectAgentInfo(model);
		}
		
		return jsonResult(model);
	}

	//대상자관리 업데이트
	@RequestMapping("/updateAgentInfo.do")
	public ModelAndView updateAgentInfo(String f_Flag, String f_AgentID, String f_AgentName, String f_AgentPW, String f_LevelCode, String f_BranchCode, String f_DeptCode, String f_ServerCode, String f_Useyn, String f_TelNum, HttpServletRequest request, ModelMap model) throws BaseAjaxException {
		
		HttpSession session = request.getSession();
	    PrivateKey key = (PrivateKey) session.getAttribute("RSAprivateKey");
	    Object loginSession = session.getAttribute("SESSION_USER");
	    
	    if (loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다.");
	    }else{
		    if (key == null) {
		    	model.addAttribute("RET", "1");
		        model.addAttribute("RET_MSG", "비정상적인 접근 입니다.");
		    }else {
				try {
					f_AgentID = rsaUtil.getDecryptText(key, f_AgentID);
					f_AgentPW = rsaUtil.getDecryptText(key, f_AgentPW);
					
					acrService.updateAgentInfo(f_Flag, f_AgentID, f_AgentName, f_AgentPW, f_LevelCode, f_BranchCode, f_DeptCode, f_ServerCode, f_Useyn, f_TelNum, request, model);
					
				} catch (Exception e) {
					throw new BaseAjaxException(e);
				}
		    }	    	
	    }
	    
	    return jsonResult(model);   
	}	

	/*****************************************************
		환경설정 - 레벨코드조회
	 *****************************************************/		
	//레벨코드조회
	@RequestMapping("/selectLevelCode.do")
	public ModelAndView selectLevelCode(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			acrService.selectLevelCode(model);
		}
		
		return jsonResult(model);
	}		
	

	/*****************************************************
		녹취조회 - 녹취조회청취
	 *****************************************************/		
	//녹취조회청취 페이지
	@RequestMapping("/recData.do")
	public String recData(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/acr/recode/recData";
	    }
	}	

	//녹취조회청취 조회
	@RequestMapping("/selectRecData.do")
	public ModelAndView selectRecData(String f_startDate, String f_endDate, HttpServletRequest request, ModelMap model){

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			acrService.selectRecData(f_startDate, f_endDate, model);
		}
		
		return jsonResult(model);
	}		

	/*****************************************************
		녹취조회 - 청취로그
	 *****************************************************/		
	//청취로그 페이지
	@RequestMapping("/listenLog.do")
	public String listenLog(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/acr/recode/listenLog";
	    }
	}	

	//청취로그 조회
	@RequestMapping("/selectListenLog.do")
	public ModelAndView selectListenLog(String f_startDate, String f_endDate, HttpServletRequest request, ModelMap model){

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			acrService.selectListenLog(f_startDate, f_endDate, model);
		}
		
		return jsonResult(model);
	}		

	/*****************************************************
		녹취조회 - 녹취통계
	 *****************************************************/	
	//녹취통계 페이지
	@RequestMapping("/recStatistic.do")
	public String recStatistic(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/acr/recode/recStatistic";
	    }
	}	

	//녹취통계 조회
	@RequestMapping("/selectRecStatistic.do")
	public ModelAndView selectRecStatistic(String f_startDate, String f_endDate, HttpServletRequest request, ModelMap model){

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			acrService.selectRecStatistic(f_startDate, f_endDate, model);
		}
		
		return jsonResult(model);
	}	

	/*****************************************************
		모니터링 - 회선모니터링
	 *****************************************************/	
	//회선모니터링 페이지
	@RequestMapping("/vpmMonitoring.do")
	public String vpmMonitoring(HttpServletRequest request, ModelMap model){

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/acr/monitoring/vpmMonitoring";
	    }
	}	

	//회선모니터링 조회
	@RequestMapping("/vpmMonitoringSelect.do")
	public ModelAndView vpmMonitoringSelect(String f_Code, HttpServletRequest request, ModelMap model){

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			acrService.vpmMonitoringSelect(f_Code, model);
		}
		
		return jsonResult(model);
	}

	//회선모니터링 청취 페이지
	@RequestMapping("/vpmAudio.do")
	public String vpmAudio(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/acr/monitoring/vpmAudio";
	    }
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
			acrService.selectUserTel(f_userId, model);
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
				acrService.updateUserTel(f_userId, f_telNum, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}

	/*****************************************************
		ACR 실시간 Agent조회
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
			acrService.selectRealTimeVAgent(model);
		}
		
		return jsonResult(model);
	}

	/*****************************************************
		녹취조회청취 Player
	 *****************************************************/	
	//녹취조회청취 Player
	@RequestMapping("/recPlay.do")
	public ModelAndView recPlay(String f_serverIP, String f_fileName, String f_job, String f_recDate, String f_serverCode, HttpServletRequest request, ModelMap model) throws BaseAjaxException {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			//현재시간
			SimpleDateFormat format1 = new SimpleDateFormat ("yyyyMMddHHmmss");
			Date time = new Date();
			String time1 = format1.format(time);
			System.out.println("======================================"+time1);
			
			model.addAttribute("RET", "1");
			model.addAttribute("RET_MSG", "실패");
			model.addAttribute("FILE_NAME", f_fileName);
			
			try {
				//wav file
				Client client = new Client(f_serverIP, 4132, f_fileName, TransportTypes.tls, ConvertTypes.mp3);
				
				try {
					String fileMp3 = f_fileName.replace(".wav", ".mp3");
					//mp3 file
					client.Download("/usr/local/apache-tomcat-7.0.96/webapps/MWTS/mp3/"+fileMp3);
					System.out.println("Mp3 fileName======================================"+fileMp3);
					
					model.addAttribute("RET", "0");
					model.addAttribute("RET_MSG", "성공");
					model.addAttribute("FILE_NAME", fileMp3);
					
					try {
						//청취로그 업데이트
						acrService.updateListenLog(f_fileName, f_job, f_recDate, f_serverCode, request, model);
						
					} catch (Exception e) {
						model.addAttribute("RET", "1");
						model.addAttribute("RET_MSG", "청취로그 업데이트 실패");
						log.error(e);
					}

				} catch (IOException e) {
					log.error(e);
				} catch (FileNotFound e) {
					log.error(e);
				} catch (InternalError e) {
					log.error(e);
				}
			} catch (Exception e) {
				log.error(e);
				//e.printStackTrace();
			}
		}
		
		return jsonResult(model);
	}
	
	
}