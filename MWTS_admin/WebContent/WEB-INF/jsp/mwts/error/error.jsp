<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>
<% response.setStatus(200); %>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>ERROR</title>
<style type="text/css">
.error_container:after {
    content : "";
    display: block;
    position: absolute;
    width: 100%;
    height: 100%;
    background-image: url('../images/errorPage.jpg'); 
    background-repeat: no-repeat; 
    background-size: contain; 
    background-position: center center;
    z-index: -1;
}
</style>
<%
	String MESSAGE = (String)request.getAttribute("MESSAGE");
	//response.getWriter().print(MESSAGE);
%>
</head>
<body>
	<div class="error_container" style="position: relative; height: 100%; width: 100%;">
		<div style="position: absolute; width: 100%; top: 20%; text-align: center;">
			<h1 style="font-size: 30px"><%=MESSAGE%></h1>
		</div>
	</div>
</body>
</html>