package com.modernwave.web.server.controller;

import java.security.PrivateKey;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.modernwave.web.server.framework.core.BaseAjaxException;
import com.modernwave.web.server.framework.core.BaseController;
import com.modernwave.web.server.framework.core.BaseDhtmlxException;
import com.modernwave.web.server.framework.dataset.OutputResult;
import com.modernwave.web.server.model.AppraiserVo;
import com.modernwave.web.server.model.MarkingVo;
import com.modernwave.web.server.model.RecordSearchVo;
import com.modernwave.web.server.model.SheetVo;
import com.modernwave.web.server.service.MwtsService;
import com.modernwave.web.server.controller.RSAUtil;
import com.modernwave.web.server.controller.RSA;

@Controller
@RequestMapping("/mwts")
public class MwtsController extends BaseController {

	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	MwtsService mwtsService;

	@Autowired
	RSAUtil rsaUtil;
	
	@Autowired
	RSA rsa;

	/*****************************************************
		에러처리
	 *****************************************************/
	@RequestMapping(value="/error{error_code}.do")
	public String error(HttpServletRequest request, @PathVariable String error_code, ModelMap model) {
	    String msg = (String) request.getAttribute("javax.servlet.error.message"); 
	    
	    try {
	        int status_code = Integer.parseInt(error_code);
	        switch (status_code) {
	        case 400: msg = "Bad Request"; break;
	        case 401: msg = "Unauthorized"; break;
	        case 402: msg = "Payment Required"; break;
	        case 403: msg = "Forbidden"; break;
	        case 404: msg = "Not Found"; break;
	        case 405: msg = "Method not allowed"; break;
	        case 406: msg = "Not Acceptable"; break;
	        case 407: msg = "Proxy Authentication Required"; break;
	        case 408: msg = "Request timeout"; break;
	        case 409: msg = "Conflict"; break;
	        case 410: msg = "Gone"; break;
	        case 411: msg = "Length Required"; break;
	        case 412: msg = "Precondition Failed"; break;
	        case 413: msg = "Request entity too large"; break;
	        case 414: msg = "Request-URI too long"; break;
	        case 415: msg = "Unsupported media type"; break;
	        case 444: msg = "No Response"; break;
	        case 449: msg = "Retry with"; break;
	        case 500: msg = "Internal Server Error"; break;
	        case 501: msg = "Not Implemented"; break;
	        case 502: msg = "Bad gateway"; break;
	        case 503: msg = "Service Unavailable"; break;
	        case 504: msg = "Gateway timeout"; break;
	        case 505: msg = "HTTP Version Not Supported"; break;
	        case 506: msg = "Variant Also Negotiates"; break;
	        case 507: msg = "Insufficient Storage"; break;
	        case 510: msg = "Not Extended"; break;
	        default: msg = "알 수 없는 오류가 발생하였습니다."; break;
	        }
	    } catch(Exception e) {
	        msg = "기타 오류가 발생하였습니다.";
	    }
	    
	    log.info("MESSAGE : "+ msg);
	    log.info("STATUS_CODE : "+request.getAttribute("javax.servlet.error.status_code"));
	    log.info("REQUEST_URI : "+ request.getAttribute("javax.servlet.error.request_uri"));
	    log.info("EXCEPTION_TYPE : "+request.getAttribute("javax.servlet.error.exception_type"));
	    log.info("EXCEPTION : "+ request.getAttribute("javax.servlet.error.exception"));
	    log.info("SERVLET_NAME : "+ request.getAttribute("javax.servlet.error.servlet_name"));
	    
	    model.addAttribute("MESSAGE", msg);
	    model.addAttribute("STATUS_CODE", request.getAttribute("javax.servlet.error.status_code"));
	    model.addAttribute("REQUEST_URI", request.getAttribute("javax.servlet.error.request_uri"));
	    model.addAttribute("EXCEPTION_TYPE", request.getAttribute("javax.servlet.error.exception_type"));
	    model.addAttribute("EXCEPTION", request.getAttribute("javax.servlet.error.exception"));
	    model.addAttribute("SERVLET_NAME", request.getAttribute("javax.servlet.error.servlet_name"));
	    
	    return "/mwts/error/error";
	}
		
	/*****************************************************
		로그인
	 *****************************************************/
	@RequestMapping("/login.do")
	public String login(HttpSession session, ModelMap model){
		
	    PrivateKey key = (PrivateKey) session.getAttribute("RSAprivateKey");
	    if (key != null) { 
	    	// 기존 키 파기
	        session.removeAttribute("RSAprivateKey");
	        session.removeAttribute("exponent");
	        session.removeAttribute("modulus");
	    }
	    // RSA 키 생성
	    RSA rsa = rsaUtil.createRSA();
	    session.setAttribute("RSAprivateKey", rsa.getPrivateKey());
	    session.setAttribute("exponent", rsa.getExponent());
	    session.setAttribute("modulus", rsa.getModulus());
	    
		return "/mwts/login/login";
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		session.removeAttribute(SESSION_USER);
		session.removeAttribute(getKeyColumns());
		session.removeAttribute(getResultColumns());
		session.removeAttribute(getSeqColName());
		session.removeAttribute(getServerIp());
		session.removeAttribute(getUserGrade());
		session.removeAttribute(getUserId());
		session.removeAttribute(getUserTheme());
		session.removeAttribute("RSAprivateKey");
		session.removeAttribute("exponent");
		session.removeAttribute("modulus");
		
	    // RSA 키 생성
	    RSA rsa = rsaUtil.createRSA();
	    session.setAttribute("RSAprivateKey", rsa.getPrivateKey());
	    session.setAttribute("exponent", rsa.getExponent());
	    session.setAttribute("modulus", rsa.getModulus());
		
		return "/mwts/login/login";
	}
	
	@RequestMapping("/loginProc.do")
	public ModelAndView loginProc(HttpServletRequest request, String loginId, String password, ModelMap model) throws Exception{
		
		HttpSession session = request.getSession();
	    PrivateKey key = (PrivateKey) session.getAttribute("RSAprivateKey");
	    
	    if (key == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "비정상적인 접근 입니다.");
	    }else {
		    try {
		    	loginId = rsaUtil.getDecryptText(key, loginId); // 아이디 복호화
		    	password = rsaUtil.getDecryptText(key, password); // 비밀번호 복호화
		 
		    	mwtsService.loginProc(request, loginId, password, model);
		    	
		    } catch (Exception e) {
		    	model.addAttribute(RET_CODE, "E");
		        model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : loginProc]");	
		        log.error("처리중 문제가 발생했습니다. [Exception : loginProc]", e);
		    }
	    }
	    
		return jsonResult(model);
	}
	
	@RequestMapping("/modifyPwPorc.do")
	public ModelAndView modifyPwProc(HttpServletRequest request, String currentPw, String modifyPw, String modifyPwChk, ModelMap model){
		
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
			    	currentPw = rsaUtil.getDecryptText(key, currentPw);
			    	modifyPw = rsaUtil.getDecryptText(key, modifyPw);
			    	modifyPwChk = rsaUtil.getDecryptText(key, modifyPwChk);
			 
			    	mwtsService.modifyPwProc(currentPw, modifyPw, modifyPwChk, model);
			    	
			    } catch (Exception e) {
			    	model.addAttribute(RET_CODE, "E");
			        model.addAttribute(RET_MSG,  "처리중 문제가 발생했습니다. [Exception : modifyPwPorc]");	
			        log.error("처리중 문제가 발생했습니다. [Exception : modifyPwPorc]", e);
			    }
		    }	    	
	    }
		
		return jsonResult(model);
	}

	/*****************************************************
		메인
	 *****************************************************/
	//메인 페이지
	@RequestMapping("/recMain.do")
	public String listenLog(HttpServletRequest request, HttpSession session, ModelMap model){
		
		model.addAttribute("GUBUN", "ACR");//ACR, MCTM
		
	    PrivateKey key = (PrivateKey) session.getAttribute("RSAprivateKey");
	    if (key != null) { 
	    	// 기존 키 파기
	        session.removeAttribute("RSAprivateKey");
	        session.removeAttribute("exponent");
	        session.removeAttribute("modulus");
	    }
	    // RSA 키 생성
	    RSA rsa = rsaUtil.createRSA();
	    session.setAttribute("RSAprivateKey", rsa.getPrivateKey());
	    session.setAttribute("exponent", rsa.getExponent());
	    session.setAttribute("modulus", rsa.getModulus());
	    
	    return "/mwts/layout/main";
		
	}	

	//HOME
	@RequestMapping("/home.do")
	public String home(){
		return "/mwts/layout/home";
	}
	
	//메뉴 조회
	@RequestMapping("/menuSelect.do")
	public ModelAndView menuSelect(String userId, ModelMap model){
		mwtsService.menuSelect(userId, model);
		return jsonResult(model);
	}

	//테마 조회
	@RequestMapping("/selectTheme.do")
	public ModelAndView selectTheme(ModelMap model){
		mwtsService.selectTheme(model);
		return jsonResult(model);
	}		
	
	
	//테마 업데이트
	@RequestMapping("/updateTheme.do")
	public ModelAndView updateTheme(String theme, String userId, HttpServletRequest request, ModelMap model) throws BaseAjaxException {
		try {
			mwtsService.updateTheme(theme, userId, request, model);
			return jsonResult(model);
		} catch (Exception e) {
			throw new BaseAjaxException(e);
		}
	}	
	
	/*****************************************************
		메뉴권한 설정
	 *****************************************************/	
	//메뉴권한 페이지
	@RequestMapping("/menuauth.do")
	public String menuauth(HttpServletRequest request, ModelMap model) {
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mwts/manage/menuauth";
	    }
	}
	
	//사용자조회
	@RequestMapping("/userSelect.do")
	public ModelAndView userSelect(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mwtsService.userSelect(model);
		}
		
		return jsonResult(model);
	}

	//메뉴권한 조회
	@RequestMapping("/selectMenuAuth.do")
	public ModelAndView selectMenuAuth(HttpServletRequest request, String f_userId, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mwtsService.selectMenuAuth(f_userId, model);
		}
		
		return jsonResult(model);
	}
	
	//메뉴권한 업데이트
	@RequestMapping("/updateMenuAuth.do")
	public ModelAndView updateMenuAuth(String idx, String f_userId, HttpServletRequest request, ModelMap model) throws BaseAjaxException {
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			try {
				mwtsService.updateMenuAuth(idx, f_userId, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}
	
	/*****************************************************
		코드마스터 설정
	 *****************************************************/	
	//코드마스터 페이지
	@RequestMapping("/codeauth.do")
	public String codeauth(HttpServletRequest request, ModelMap model) {
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	return "/mwts/manage/codeauth";
	    }
	}

	//코드마스터 조회
	@RequestMapping("/selectCodeMaster.do")
	public ModelAndView selectCodeMaster(HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			mwtsService.selectCodeMaster(model);
		}
		
		return jsonResult(model);
	}
	
	//코드마스터 업데이트
	@RequestMapping("/updateCodeMaster.do")
	public ModelAndView updateCodeMaster(String f_Flag, String f_Category, String f_Code, String f_Name, HttpServletRequest request, ModelMap model) throws BaseAjaxException {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");
		if(loginSession == null) {
	    	model.addAttribute("RET", "1");
	        model.addAttribute("RET_MSG", "세션이 종료되었습니다. 다시 로그인해주세요.");
		}else {
			try {
				mwtsService.updateCodeMaster(f_Flag, f_Category, f_Code, f_Name, request, model);
			} catch (Exception e) {
				throw new BaseAjaxException(e);
			}
		}
		
		return jsonResult(model);
	}	

	/*****************************************************
		조회대상자 설정
	 *****************************************************/	
	//조회대상자 페이지
	@RequestMapping("/userTelList.do")
	public String userTelList(HttpServletRequest request, ModelMap model) {

		HttpSession session = request.getSession();
		Object loginSession = session.getAttribute("SESSION_USER");

	    if (loginSession == null) {
			model.addAttribute("MESSAGE", "세션이 종료되었습니다. 다시 로그인해주세요.");
			return "/mwts/error/error";
	    }else{
	    	model.addAttribute("GUBUN", "ACR");//ACR, MCTM
	    	return "/mwts/manage/userTelList";
	    }
	}	
	
	/*****************************************************
		관리자 설정
	 *****************************************************/	
	//관리자설정 페이지
	@RequestMapping("/userInfo.do")
	public String userInfo(HttpServletRequest request, ModelMap model){
		
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
			    return "/mwts/manage/userInfo";
			}			
		}
	}	

	//관리자설정 업데이트
	@RequestMapping("/updateUserInfo.do")
	public ModelAndView updateUserInfo(String f_Flag, String f_UserID, String f_UserName, String f_ListenYN, String f_SaveYN, String f_UseYN, HttpServletRequest request, ModelMap model) throws BaseAjaxException {
		
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
					f_UserID = rsaUtil.getDecryptText(key, f_UserID);
					
					mwtsService.updateUserInfo(f_Flag, f_UserID, f_UserName, f_ListenYN, f_SaveYN, f_UseYN, request, model);
					
				} catch (Exception e) {
					throw new BaseAjaxException(e);
				}
		    }	    	
	    }
		
	    return jsonResult(model);
	}
	
	
}