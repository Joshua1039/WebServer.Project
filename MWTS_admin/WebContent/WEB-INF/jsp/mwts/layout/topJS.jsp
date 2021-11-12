<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" />
<title>MODERNWAVE</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<!-- 브라우저아이콘 -->
<link rel="shortcut icon" href="../images/favicon.ico" type="image/x-icon"/>

<link rel="stylesheet" type="text/css"
	href="../devextreame/Sources/Lib/css/dx.spa.css" />
<link rel="stylesheet" type="text/css"
	href="../devextreame/Sources/Lib/css/dx.common.css" />
<%@include file="../../mwts/theme/color.jsp"%>

<script src="../devextreame/Sources/Lib/js/jquery-2.2.3.min.js"></script>
<script src="../devextreame/Sources/Lib/js/knockout-3.4.0.js"></script>
<script src="../devextreame/Sources/Lib/js/jszip.min.js"></script>
<script src="../devextreame/Sources/Lib/js/localization/dx.all_ko.js"></script>
<%
Object loginSession = session.getAttribute("SESSION_USER");
	if(loginSession == null) {
		out.println("<Script language='JavaScript'>alert('세션이 종료되었습니다. 다시 로그인해주세요.');window.parent.location.href='/MWTS/mwts/logout.do';window.close();</Script>");
		return;
	}
%>	
</head>
</html>
<script type="text/javascript">
//IE readonly 커서 방지
$(document).on('focus', 'input[readonly]', function () {
    this.blur();
});

//IE 뒤로가기 버튼 방지 
var killBackSpace = function(e) {
	e = e ? e : window.event;
	var t = e.target ? e.target : e.srcElement ? e.srcElement : null;
	if (t
			&& t.tagName
			&& (t.type && /(password)|(text)|(file)/.test(t.type
					.toLowerCase()))
			|| t.tagName.toLowerCase() == 'textarea') {
		return true;
	}
	var k = e.keyCode ? e.keyCode : e.which ? e.which : null;
	if (k == 8) {
		if (e.preventDefault) {
			e.preventDefault();
		}
		return false;
	}
	return true;
};
if (typeof document.addEventListener != 'undefined') {
	document.addEventListener('keydown', killBackSpace, false);
} else if (typeof document.attachEvent != 'undefined') {
	document.attachEvent('onkeydown', killBackSpace);
} else {
	if (document.onkeydown != null) {
		var oldOnkeydown = document.onkeydown;
		document.onkeydown = function(e) {
			oldOnkeydown(e);
			killBackSpace(e);
		};
	} else {
		document.onkeydown = killBackSpace;
	}
}
</script>