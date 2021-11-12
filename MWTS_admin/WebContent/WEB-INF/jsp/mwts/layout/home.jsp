<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>HOME</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	
<%@include file="../../mwts/layout/topJS.jsp"%>
<style type="text/css">
.home_image {
    position: absolute;
    height: 100%;
    width: 90%;
    background: linear-gradient(to right,rgba(0,0,0, 0),white),url(../images/phone.jpg);
    background-repeat: no-repeat; 
    background-size: 100% 100%; 
}
.home_image h4 {
	position: absolute;
	display: inline-block;
	top:70px;
	right:0px;
	color: #444;
	font-size: 60px;
	font-weight: 600;
	line-height: 1;
	padding-bottom: 1%;
	border-bottom: 2.5px solid #444;
}
.home_image p {
	position: absolute;
	display: inline-block;
	top:150px;
	right:0px;
	color: black;
	font-size: 23px;
	font-weight: 500;
	font-family: Noto Sans KR, sans-serif; 
	padding-top: 1%;
}
a:link { color: black; text-decoration: none;} /*클릭하지 않은 링크*/
a:visited { color: black; text-decoration: none;} /*한번 클릭했던 혹은 다녀갔던 링크*/
a:hover { color: black; text-decoration: none;} /*링크를 클릭하려고 마우스를 가져갔을 때*/

</style>
</head>

<body class="dx-viewport">
    <div class="demo-container" style="position: absolute; height: 100%; width: 100%;">
    	<div class="home_image">
    		<h4>MODERNWAVE</h4>
    		<!-- <p>통합관리시스템</p> -->
    		
    		<div class="manual" style="position: absolute; top: 160px; right:0px; width:280px; height: 32px; cursor: pointer;">
	    		<a href="../manual/RecodeManual.pdf" target="pdf">
		    		<img src="../images/pdf.png"></img>
		    		<span style="position: absolute; right: 0px; font-size: 23px; font-weight: 500; font-family: Noto Sans KR, sans-serif;">
		    			통합관리시스템 메뉴얼
		    		</span>
	    		</a>
    		</div>
    	</div>
    </div>
</body>
</html>