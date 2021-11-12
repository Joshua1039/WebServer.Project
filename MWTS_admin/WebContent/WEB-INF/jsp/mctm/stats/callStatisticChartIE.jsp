<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>콜시간통계 차트</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<%@include file="../../mwts/layout/topJS.jsp"%>
</head>

<body class="dx-viewport">
    <div class="demo-container" style="position: absolute; height: 100%; width: 100% ">
		<div id="head_Grid" style="position: absolute; height: 50px; width: 90%; top: 30px; margin: 0 5% 0 5%; ">
			<h4 style="position: absolute; font-size: 15px; font-weight: bold">콜시간통계 차트</h4><br />
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
        storageKey: "callStatistis"//key는 파일명과 동일하게함
    },
    dataSource: {
        fields: [{
            caption: "월",
            dataField: "dt_M"
            //expanded: true
        }, {
            caption: "일",
            dataField: "dt_D"
        }, {
            caption: "시간",
            dataField: "dt_H"
        }, {
            caption: "내선번호",
            dataField: "agent_tel"
        }, {
            caption: "이름",
            dataField: "AGENT_NAME"
        }, {
            caption: "요일",
            dataField: "dt_W"
        }, {
            caption: "그룹",
            dataField: "group_name"
        }, {
            caption: "대상자",
            dataField: "agent_id"
        }, {
            caption: "로그인 시간",
            dataField: "login_tm",
            summaryType: "min",
            showTotals: false
        }, {
            caption: "로그아웃 시간",
            dataField: "logout_tm",
            summaryType: "max",
            showTotals: false
        }, {
            caption: "근무시간",
            dataField: "job_tm",
            summaryType: "min",
            showTotals: false,
           	customizeText: function(e) {
           		var retTxt = customText(e);
           		return retTxt;
            }
        }, {
            caption: "수신대기",
            dataField: "IT",
            summaryType: "sum",
           	customizeText: function(e) {
           		var retTxt = customText(e);
           		return retTxt;
            }
        }, {
            caption: "업무처리",
            dataField: "WT",
            summaryType: "sum",
           	customizeText: function(e) {
           		var retTxt = customText(e);
           		return retTxt;
            }
        }, {
            caption: "휴식",
            dataField: "BT",
            summaryType: "sum",
           	customizeText: function(e) {
           		var retTxt = customText(e);
           		return retTxt;
            }
        }, {
            caption: "교육",
            dataField: "MT",
            summaryType: "sum",
           	customizeText: function(e) {
           		var retTxt = customText(e);
           		return retTxt;
            }
        }, {
            caption: "발신콜수",
            dataField: "O_cnt",
            summaryType: "sum"
        }, {
            caption: "발신통화시간",
            dataField: "O_call",
            summaryType: "sum",
           	customizeText: function(e) {
           		var retTxt = customText(e);
           		return retTxt;
            }		            
        }, {
            caption: "수신콜수",
            dataField: "I_cnt",
            summaryType: "sum"
        }, {
            caption: "수신통화시간",
            dataField: "I_call",
            summaryType: "sum",
           	customizeText: function(e) {
           		var retTxt = customText(e);
           		return retTxt;
            }		            
        }],
        store: window.opener.retData
    }
}).dxPivotGrid("instance");

$("#pivot_Chart").dxChart({
	dataSource: window.opener.retData,
    commonSeriesSettings: {
        type: "fullStackedBar",
        argumentField: "agent_id"
    },
    series: [
    	{ valueField: "IT", name: "수신대기" },
    	{ valueField: "WT", name: "업무처리" },
    	{ valueField: "BT", name: "휴식" },
    	{ valueField: "MT", name: "교육" },
    	{ valueField: "O_call", name: "발신통화시간" },
        { valueField: "I_call", name: "수신통화시간" }  
    ],
    legend: {
        verticalAlignment: "top",
        horizontalAlignment: "center",
        itemTextPosition: "right"
    },
    title: {
        text: "상담사 콜통계 차트",
        subtitle: {
            text: ""
        }
    }  
}).dxChart("instance");

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