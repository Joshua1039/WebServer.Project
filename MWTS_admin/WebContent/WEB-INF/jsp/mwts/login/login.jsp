<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" />
<title>MODERNWAVE</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<!-- 브라우저아이콘 -->
<link rel="shortcut icon" href="../images/favicon.ico" type="image/x-icon"/>
<!-- 구글폰트 -->
<link rel="stylesheet" type="text/css" href="../devextreame/Sources/Lib/css/google.font.css" />
<!-- 테마 -->
<link rel="stylesheet" type="text/css" href="../devextreame/Sources/Lib/css/dx.spa.css" />
<link rel="stylesheet" type="text/css" href="../devextreame/Sources/Lib/css/dx.common.css" />
<%@include file="../../mwts/theme/color.jsp"%>

<script src="../devextreame/Sources/Lib/js/jquery-2.2.3.min.js"></script>
<script src="../devextreame/Sources/Lib/js/knockout-3.4.0.js"></script>
<script src="../devextreame/Sources/Lib/js/jszip.min.js"></script>
<script src="../devextreame/Sources/Lib/js/localization/dx.all_ko.js"></script>

<!-- 로딩 -->
<link rel="stylesheet" type="text/css" href="../devextreame/Sources/Lib/css/fakeLoader.css" />
<script src="../devextreame/Sources/Lib/js/fakeLoader.js"></script>

<style type="text/css">
/*배경 이미지*/
#login_container:after {
    content : "";
    display: block;
    position: absolute;
    width: 100%;
    height: 100%;
    background-image: url('../images/bg_gray.png'); 
    background-repeat: no-repeat; 
    background-size: cover !important; 
    background-position: center center;
    opacity : 0.6;
    filter: alpha(opacity=60);
    z-index: -1;
}
.loginContainerIn:before {
    content : "";
    position: absolute;
    top: 0px;
    left: 0px;
    border-top: 83px solid white;
    border-right: 150px solid rgba(0, 0, 0, 0.4);
    width: 0;
}
</style>
<%
Object loginSession = session.getAttribute("SESSION_USER");
	if(loginSession != null) {
		response.sendRedirect("/MWTS/mwts/recMain.do");
		return;
	}
	
	String modulus = (String)session.getAttribute("modulus");
	String exponent = (String)session.getAttribute("exponent");	
%>
</head>
<body class="dx-viewport">
	<div class="demo-container" id="login_container" 
		style="position: relative; height: 100%; width: 100%;">
	
		<div class="loginContainerOut" style="position: absolute; top: 15%; left: 20%; height:70%; width: 60%;">
			<img src="../images/headset.jpg" style="position: absolute; height:100%; width:100%;"/>
			
			<div class="loginContainerIn" 
				style="position:absolute; bottom: 0px; right: 0px; height: 450px; width: 350px; max-height: 100%; max-width: 100%; background:rgba(0, 0, 0, 0.4); z-index: 999;">
				<img src="../images/modern_logo.png" style="position: absolute; margin-top:55px; margin-left:140px; z-index:998; "/>
				
					<div class="user" style="position: absolute; top: 155px; left: 55px; height: 50px; width: 50px;">
						<img src="../images/user.png" style="height:100%; width:100% "/>
					</div>
					<div id="userId" style="position: absolute; top: 165px; left: 120px; width: 200px; font-size:16px;"></div>	
					
					<div class="password" style="position: absolute; top:230px; left:55px; height: 50px; width: 50px;">
						<img src="../images/password.png" style="height:100%; width:100% "/>
					</div>
					<div id="userPw" style="position: absolute; top:240px; left: 120px; width: 200px; font-size:16px;"></div>
					
					<div id="saveId" style="position: absolute; top:300px; left:220px; "></div>
					<label style="position: absolute; top: 300px; left: 255px; width: 80px; font-family: Noto Sans KR, sans-serif; font-size: 18px; font-weight: 600; color: white; padding: 2px;">ID 저장</label>
					<div id="loginBtn" style="position: absolute; top: 360px; left: 45px; width: 275px; font-weight: bold; font-size: 20px; color: white; background: transparent; border-width: 2px;"></div>
				</div>
		</div>	
		<div id="fakeloader"></div><!-- Loading -->
	</div>	
</body>
</html>

<script language="JavaScript" src="../js/jsbn.js"></script>
<script language="JavaScript" src="../js/prng4.js"></script>
<script language="JavaScript" src="../js/rng.js"></script>
<script language="JavaScript" src="../js/rsa.js"></script>
<script type="text/javascript">
var strUserPw ="";
//아이디
$("#userId").dxTextBox({
	maxLength: 20,
	mode: "text",
	placeholder: "아이디",
	value : "",
	valueChangeEvent: "keyup",//IE에서 onEnterKey 이벤트 동작하지않아 추가
	onContentReady: function (e) {
		//tab시 마지막 글자 잘림 개선
		e.element.find(".dx-texteditor-input").attr({id: 'strUserId'});
		//포커스
		$("#userId").dxTextBox('instance').focus();
		//한글입력방지
/* 		$(".dx-texteditor-input").keyup(function(e) {
			if (!(e.keyCode >=37 && e.keyCode<=40)) {
				var v = $(this).val();
				$(this).val(v.replace(/[^a-z0-9]/gi,''));
			}
		}); */
	},
	onValueChanged: function (e) {
		//최초 로그인 이후 아이디를 수정해도 변경되지않아 값 수정시 강제 false
		$("#saveId").dxCheckBox({ value: false });
	},
	onEnterKey: function(e) {
		pressEnterKey();
	}
});
//비밀번호
$("#userPw").dxTextBox({
	maxLength: 20,
	mode: "password",
	placeholder: "비밀번호",
	value : "",
	valueChangeEvent: "keyup",//IE에서 onEnterKey 이벤트 동작하지않아 추가
	onValueChanged: function (e) {
		strUserPw = e.value;
	},
	onEnterKey: function(e) {
		pressEnterKey();
	}	
});
//아이디 저장
$("#saveId").dxCheckBox({
	value: false,
	onValueChanged: function(e) {
 		if(e.value == true){
			if($("#strUserId").val() == ''){
				alert("아이디를 입력해주세요.");
				$("#saveId").dxCheckBox({ value: false });
				$("#userId").dxTextBox('instance').focus();
				return;
			}
			setCookie("saveId", $("#strUserId").val(), 10);
		}else{
			setCookie("saveId", '', 10);
		} 
	}
});
//로그인 버튼
$("#loginBtn").dxButton({
    text: "LOGIN",
    type: "normal",
    //icon: "check",
    onClick: function(e) { 
    	submitLogin();
    }
});

 $(document).ready(function(){
	var saveId = getDomainCookie("saveId");
	if(saveId != ''){
		$("#userId").dxTextBox({ value: saveId });
		$("#saveId").dxCheckBox({ value: true });
		$("#userPw").dxTextBox('instance').focus();
	}else
		$("#userId").dxTextBox('instance').focus();
}); 

function submitLogin(){
	//publicKey
    var rsa = new RSAKey();
    	rsa.setPublic("<%=modulus%>", "<%=exponent%>");

    var rsaId = $("#strUserId").val();
    var rsaPw = strUserPw;
    
 	$.ajax({
		url:'/MWTS/mwts/loginProc.do',
		data: {
			loginId: rsa.encrypt(rsaId),
			password: rsa.encrypt(rsaPw)
			},
		success:function(result){
			if(result.RET == "0"){
				location = "/MWTS/mwts/recMain.do";
			}else if (result.RET == "1"){
				DevExpress.ui.notify(result.RET_MSG, "warning");
				$("#userId").dxTextBox('instance').focus();
			}else if (result.RET == "E"){
				DevExpress.ui.notify(result.RET_MSG, "error");
				$("#userId").dxTextBox('instance').focus();
			}
		},
	    beforeSend:function(){
	      	$("#fakeloader").fakeLoader({
	    	    timeToHide:10000, // 로딩 시간
	    	    bgColor:'rgba(0,0,0,.3)', // 배경
	    	    spinner:"spinner2" // 효과 1~7
	    	  });
	    },
	    complete:function(){
	    	$('#fakeloader').css({"display":"none"});
	    },
		error:function(jqXHR, textStatus, errorThrown){
			DevExpress.ui.notify("로그인 중 오류 발생 status["+textStatus+"]\nmessage["+errorThrown+"]", "error");
			
			$("#userId").dxTextBox('instance').focus();
		}});
}

function pressEnterKey(){
	if(event.keyCode == 13)
		submitLogin();
}

function setCookie(cName, cValue, cDay){
    var expire = new Date();
    expire.setDate(expire.getDate() + cDay);
    cookies = cName + '=' + escape(cValue) + '; path=/ '; // 한글 깨짐을 막기위해 escape(cValue)를 합니다.
    if(typeof cDay != 'undefined') cookies += ';expires=' + expire.toGMTString() + ';';
    document.cookie = cookies;
}

function getDomainCookie(name){
	var nameOfCookie = name + "=";
	var x = 0;
	while ( x <= document.cookie.length )
	{
	  var y = (x+nameOfCookie.length);
	  if ( document.cookie.substring( x, y ) == nameOfCookie )
	  {
	    if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
	      endOfCookie = document.cookie.length;
	    return unescape( document.cookie.substring( y, endOfCookie ) );
	  }
	  x = document.cookie.indexOf( " ", x ) + 1;
	  if ( x == 0 )
	    break;
	}
	return "";
}

</script>