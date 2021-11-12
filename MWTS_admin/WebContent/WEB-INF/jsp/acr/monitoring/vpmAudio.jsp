<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>녹취 회선 모니터링 청취</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	
<%@include file="../../mwts/layout/topJS.jsp"%>
<%
String strServerIP = request.getParameter("server_ip");
String strVpmNum = request.getParameter("vpm_num");
%>
</head>
<body class="dx-viewport">
	<audio controls="controls" autoplay="true">
	    <source src="http://<%=strServerIP%>:8809/realtime/<%=strVpmNum%>" type="audio/mpeg" />
	    <!-- http://127.0.0.1:8809/realtime/1 -->
	</audio>
</body>
</html>