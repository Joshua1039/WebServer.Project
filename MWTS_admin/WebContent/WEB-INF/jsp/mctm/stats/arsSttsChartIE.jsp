<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>ARS통계 차트</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<%@include file="../../mwts/layout/topJS.jsp"%>
</head>

<body class="dx-viewport">
    <div class="demo-container" style="position: absolute; height: 100%; width: 100% ">
		<div id="head_Grid" style="position: absolute; height: 50px; width: 90%; top: 30px; margin: 0 5% 0 5%; ">
			<h4 style="position: absolute; font-size: 15px; font-weight: bold">ARS통계 차트</h4><br />
			<hr style="border: thin solid #e6e3dd; width: 100%; text-align: left; margin-left: 0px;"/><br />		
		</div>
		<div id="pivot_Grid" style="display: none; "></div>
		<div id="pivot_Chart" style="position: absolute; height: calc(100% - 100px); width: 90%; top: 80px; margin: 0 5% 0 5%; "></div>
    </div>
</body>
</html>

<script type="text/javascript">
var pivotGridInstance = $("#pivot_Grid").dxPivotGrid({
    allowSortingBySummary: true,
    allowSorting: true,
    allowFiltering: true, //필드 필터
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
        storageKey: "arsStts"//key는 파일명과 동일하게함
    },
    dataSource: {
        fields: [{
            caption: "년",
            dataField: "dt_Y",
            area: "filter"
        }, {
            caption: "요일",
            dataField: "dt_W",
            area: "filter"
        }, {
            caption: "시간",
            dataField: "dt_H",
            area: "filter"
        }, {
            caption: "그룹",
            dataField: "group_name",
            area: "column"
        }, {
            caption: "월",
            dataField: "dt_M",
            area: "row",
            expanded: true
        }, {
            caption: "일",
            dataField: "dt_D",
            area: "row",
            expanded: true
        }, {
            caption: "총인입호",
            dataField: "T_Call",
            area: "data",
            summaryType: "sum"
        }, {
            caption: "단순조회",
            dataField: "DAN_CALL",
            area: "data",
            summaryType: "sum"
        }, {
            caption: "단순 비율",
            area: "data",
            dataType: "number",
            summaryType: "sum", 
            format: "percent", 
            precision:2, 
            calculateSummaryValue: function(e) {
                var result = e.value("DAN_CALL") / e.value("T_Call");
                return isNaN(result) ? "" : result;
            }
        }, {
            caption: "상담요청",
            dataField: "REQ_CALL",
            area: "data",
            summaryType: "sum"
        }, {
            caption: "상담요청 비율",
            area: "data",
            dataType: "number",
            summaryType: "sum", 
            format: "percent", 
            precision:2, 
            calculateSummaryValue: function(e) {
                var result = e.value("REQ_CALL") / e.value("T_Call");
                return isNaN(result) ? "" : result;
            }
        }, {
            caption: "상담연결",
            dataField: "CON_CALL",
            area: "data",
            summaryType: "sum"
        }, {
            caption: "상담연결 비율",
            area: "data",
            dataType: "number",
            summaryType: "sum", 
            format: "percent", 
            precision:2, 
            calculateSummaryValue: function(e) {
                var result = e.value("CON_CALL") / e.value("REQ_CALL");
                return isNaN(result) ? "" : result;
            }
        }, {
            caption: "전환",
            dataField: "FWD_CALL",
            area: "data",
            summaryType: "sum"
        }, {
            caption: "전환 비율",
            area: "data",
            dataType: "number",
            summaryType: "sum", 
            format: "percent", 
            precision:2, 
            calculateSummaryValue: function(e) {
                var result = e.value("FWD_CALL") / e.value("T_Call");
                return isNaN(result) ? "" : result;
            }
        }, {
            caption: "콜백",
            dataField: "CB_CALL",
            area: "data",
            summaryType: "sum"
        }, {
            caption: "콜백 비율",
            area: "data",
            dataType: "number",
            summaryType: "sum", 
            format: "percent", 
            precision:2, 
            calculateSummaryValue: function(e) {
                var result = e.value("CB_CALL") / e.value("T_Call");
                return isNaN(result) ? "" : result;
            }
        }, {
            caption: "포기호",
            dataField: "AB_CALL",
            area: "data",
            summaryType: "sum"
        }, {
            caption: "포기호 비율",
            area: "data",
            dataType: "number",
            summaryType: "sum", 
            format: "percent", 
            precision:2, 
            calculateSummaryValue: function(e) {
                var result = e.value("AB_CALL") / e.value("REQ_CALL");
                return isNaN(result) ? "" : result;
            }
        }],
        store: window.opener.retData
    }
}).dxPivotGrid("instance");

$("#pivot_Chart").dxChart({
	dataSource: window.opener.retData,
    commonSeriesSettings: {
    	type: "fullStackedBar",
        argumentField: "group_name"  
    },
    series: [
    	{ valueField: "DAN_CALL", name: "단순조회" },
    	{ valueField: "REQ_CALL", name: "상담요청" },
    	{ valueField: "CON_CALL", name: "상담연결" },
    	{ valueField: "FWD_CALL", name: "전환" },
    	{ valueField: "CB_CALL", name: "콜백" },
        { valueField: "AB_CALL", name: "포기호" }  
    ],
    legend: {
        verticalAlignment: "top",
        horizontalAlignment: "center",
        itemTextPosition: "right"
    },
    title: {
        text: "ARS통계 차트",
        subtitle: {
            text: ""
        }
    }  
}).dxChart("instance");
</script>