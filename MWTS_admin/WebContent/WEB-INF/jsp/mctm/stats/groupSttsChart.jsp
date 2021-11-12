<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>그룹통계 차트</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<%@include file="../../mwts/layout/topJS.jsp"%>
</head>

<body class="dx-viewport">
    <div class="demo-container" style="position: absolute; height: 100%; width: 100% ">
		<div id="head_Grid" style="position: absolute; height: 50px; width: 90%; top: 30px; margin: 0 5% 0 5%; ">
			<h4 style="position: absolute; font-size: 15px; font-weight: bold">그룹통계 차트</h4><br />
			<hr style="border: thin solid #e6e3dd; width: 100%; text-align: left; margin-left: 0px;"/><br />		
		</div>
		<div id="pivot_Grid" style="display: none; "></div>
		<div id="pivot_Chart" style="position: absolute; height: calc(100% - 100px); width: 90%; top: 80px; margin: 0 5% 0 5%; "></div>
    </div>
</body>
</html>

<script type="text/javascript">
var pivotGridInstance = $("#pivot_Grid").dxPivotGrid({}).dxPivotGrid("instance");

$("#pivot_Grid").dxPivotGrid(window.opener.pivotOption);
$("#pivot_Grid").dxPivotGrid("option", "dataSource.store", window.opener.retData);

pivotGridInstance.bindChart(
	$("#pivot_Chart").dxChart({
	    commonSeriesSettings: {
	        type: "bar"       
	    },
        tooltip: {//마우스오버
            enabled: true,
            location: "edge", //center, edge
            customizeTooltip: function(e) {
				if (e.seriesName.indexOf("통화시간") != -1 || e.seriesName.indexOf("평균통화시간") != -1) {
					var retTxt = customText(e);
					return {
						text: e.seriesName + "<br/>" + retTxt
					};
				}else{
					return {
						text: e.seriesName + "<br/>" + e.value
					};					    	  
				}
            },
            zIndex: 2000 //dxPopup안에 dxChart는  zIndex가 1001이상이어야 tooltip이 표시됨 (기본값1000)
        }    
	}).dxChart("instance")
);

function customText(e){
	if (isNaN(e.value) || e.value === null || e.value === undefined) {
	    return "";
	}else{
		var hour = parseInt(e.value/3600);
		var min = parseInt((e.value%3600)/60);
		var sec = parseInt(e.value%60);
		
		if (hour < 10) {hour = "0"+hour;}
		if (min < 10) {min = "0"+min;}
		if (sec < 10) {sec = "0"+sec;}
		
		return hour +":"+ min +":"+ sec;
	}
}
</script>