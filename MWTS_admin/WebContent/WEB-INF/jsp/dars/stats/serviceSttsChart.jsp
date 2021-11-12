<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>서비스통계 차트</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<%@include file="../../mwts/layout/topJS.jsp"%>
</head>

<body class="dx-viewport">
    <div class="demo-container" style="position: absolute; height: 100%; width: 100% ">
		<div id="head_Grid" style="position: absolute; height: 50px; width: 90%; top: 30px; margin: 0 3% 0 3%; ">
			<h4 style="position: absolute; font-size: 15px; font-weight: bold">서비스통계 차트</h4><br />
			<hr style="border: thin solid #e6e3dd; width: 100%; text-align: left; margin-left: 0px;"/><br />		
		</div>
		<div id="pivot_Grid" style="display: none; "></div>
		<div id="pivot_Chart" style="position: absolute; height: 83%; width: 90%; top: 80px; margin: 0 3% 0 3%; "></div>
    </div>
</body>
</html>

<script type="text/javascript">
$(function(){
	var pivotGridChart = $("#pivot_Chart").dxChart({
	    commonSeriesSettings: {
	        type: "bar"       
	    },
        tooltip: {//마우스오버
            enabled: true,
            location: "edge", //center, edge
            customizeTooltip: function(e) {
				return {
					text: e.seriesName + "<br/>" + e.value
				};	
            },
            zIndex: 2000 //dxPopup안에 dxChart는  zIndex가 1001이상이어야 tooltip이 표시됨 (기본값1000)
        }    
	}).dxChart("instance");


	var pivotGridInstance = $("#pivot_Grid").dxPivotGrid({
	    allowSortingBySummary: true,
	    allowSorting: true,
	    allowFiltering: false, //필드 필터
	    allowExpandAll: true,
	    showBorders: true,
	    wordWrapEnabled: false,
        showColumnGrandTotals: true,
        showRowGrandTotals: true,
        showRowTotals: true,
        showColumnTotals: false,
        scrolling: {
            mode: "virtual",
            useNative : true
        },
	    fieldChooser: {
	        enabled: true //필드 커스텀
	    },
	    fieldPanel: {
	    	visible: true,
	    	showDataFields: false //area :data부분 필드 숨기기
	    },
        stateStoring: {//area 지정 상태 저장
            enabled: true,
            type: "localStorage",
            storageKey: "serviceStts"//key는 파일명과 동일하게함
        },		
	    dataSource: {
	        fields: [{
	            caption: "년",
	            dataField: "dt_Y",
	            area: "filter"
	        }, {
	            caption: "월별",
	            dataField: "dt_M",
	            area: "filter"
	        }, {
	            caption: "시간대별",
	            dataField: "dt_H",
	            area: "filter"
	        }, {
	            caption: "일별",
	            dataField: "dt_D",
	            area: "row"
	        }, {
	            caption: "서비스 대",
	            dataField: "svc_1",
	            area: "column"
	        }, {
	            caption: "서비스 중",
	            dataField: "svc_2",
	            area: "column"
	        }, {
	            caption: "서비스 소",
	            dataField: "svc_3",
	            area: "column"
	        }, {
	            caption: "인입호",
	            dataField: "cnt",
	            area: "data",
	            summaryType: "sum"
	        }],
            store: window.opener.retData
	    },
	    onContentReady: function(e) {
	    	$("#pivot_Grid").dxPivotGrid(window.opener.parentOption);
	    }
	}).dxPivotGrid("instance");	
	
	
	pivotGridInstance.bindChart(pivotGridChart);
});
</script>