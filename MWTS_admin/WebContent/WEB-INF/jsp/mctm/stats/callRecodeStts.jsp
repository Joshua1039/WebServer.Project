<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>콜 이력 조회</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	
<%@include file="../../mwts/layout/topJS.jsp"%>
</head>

<body class="dx-viewport">
    <div class="demo-container" style="position: absolute; height: 100%; width: 100% ">
		<div id="head_Grid" style="position: absolute; height: 50px; width: 100% ">
			<label style="position: absolute; top: 8px; width: 70px; text-align: center ">조회기간</label>
			<div id="startDate" style="display: inline-block; left: 75px; width:150px "></div>
			<div id="endDate" style="display: inline-block; left: 80px; width:150px "></div>
			<div id="selectBtn" style="position: absolute; left: 400px "></div>
		</div>
		
		<div id="select_Grid" style="position: absolute; top: 60px; height: calc(100% - 70px); width: 100%; overflow-x:hidden; overflow-y:hidden;"><!-- IE브라우저 가로, 세로스크롤 숨김 -->
		</div>
    </div>
</body>
</html>

<script type="text/javascript">
var startD ="";
var start_date = "";
var endD ="";
var end_date = "";
var todayD = new Date();
var today = todayD.getFullYear() +""
				+ ((todayD.getMonth() + 1) >= 10 ? (todayD.getMonth() + 1) : '0'+(todayD.getMonth() + 1)) +""
				+ ((todayD.getDate()) >= 10 ? (todayD.getDate()) : '0'+(todayD.getDate()));

$("#startDate").dxDateBox({
    value: new Date(),
    displayFormat: "yyyy-MM-dd",
    maxLength: 10,
    maxZoomLevel: "month",
    minZoomLevel: "decade",     
    onInitialized: function(e) {
    	startD = e.component._options.value;
     	start_date = startD.getFullYear() +""
			    	+ ((startD.getMonth() + 1) >= 10 ? (startD.getMonth() + 1) : '0'+(startD.getMonth() + 1)) +""
			    	+ ((startD.getDate()) >= 10 ? (startD.getDate()) : '0'+(startD.getDate()));
    },    
    onValueChanged: function(e) {
    	startD = e.value;
    	start_date = startD.getFullYear() +""
			    	+ ((startD.getMonth() + 1) >= 10 ? (startD.getMonth() + 1) : '0'+(startD.getMonth() + 1)) +""
			    	+ ((startD.getDate()) >= 10 ? (startD.getDate()) : '0'+(startD.getDate()));
    }
});

$("#endDate").dxDateBox({
    value: new Date(),
    displayFormat: "yyyy-MM-dd",
    maxLength: 10,
    maxZoomLevel: "month",
    minZoomLevel: "decade",     
    onInitialized: function(e) {
    	endD = e.component._options.value;
    	end_date = endD.getFullYear() +""
			    	+ ((endD.getMonth() + 1) >= 10 ? (endD.getMonth() + 1) : '0'+(endD.getMonth() + 1)) +""
			    	+ ((endD.getDate()) >= 10 ? (endD.getDate()) : '0'+(endD.getDate()));
    },    
    onValueChanged: function(e) {
    	endD = e.value;
    	end_date = endD.getFullYear() +""
			    	+ ((endD.getMonth() + 1) >= 10 ? (endD.getMonth() + 1) : '0'+(endD.getMonth() + 1)) +""
			    	+ ((endD.getDate()) >= 10 ? (endD.getDate()) : '0'+(endD.getDate()));
    }
});

$("#selectBtn").dxButton({
    text: "조회",
    type: "normal",
    width: 100,
    onClick: function(e) {        	
    	callRecodeSttsSelect(start_date, end_date);
    }
});

$("#select_Grid").dxDataGrid({
	dataSource : {},
	columnAutoWidth : false,
	allowColumnResizing : true,
	allowColumnReordering : true,
	columnResizingMode: "widget",//nextColumn
	hoverStateEnabled: true,//마우스 오버 시 색상 변경
    paging : {
    	enabled: false
    },
	headerFilter : {
		visible : true
	},
	searchPanel : {
		visible : true,
		width : 300
	},
    "export": {
        enabled: true,
        fileName: "콜 이력 조회_"+today,
        allowExportSelectedData: false
    },
	scrolling: {
        columnRenderingMode: "standard",
        showScrollbar: "always"
    },    
	grouping : {
		contextMenuEnabled : true
	},
	columns : [{
		caption : "통화시작시간",
		dataField : "D_TIME",
		alignment : "center",
		sortOrder: "desc"
	}, {
		caption : "통화종료시간",
		dataField : "N_TIME",
		alignment : "center"
	}, {
		caption : "소요시간",
		dataField : "DUR",
		alignment : "center",
	    customizeText: function(e) {
       		var retTxt = customText(e);
       		return retTxt;
	    }
	}, {
		caption : "고객전화번호",
		dataField : "ANI_NO",
		alignment : "center",
        customizeText: function(e) {
        	//하이픈 정규식
        	var aniNo = e.value;
        	aniNo = aniNo.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
            return aniNo;
        },
		calculateFilterExpression: function(filterValue, selectedFilterOperation) {
			filterValue = filterValue.replace(/-/gi,"");// 하이픈 치환
			filterValue = filterValue.replace(/\s/gi, "");//공백 제거
			
		    if (this.calculateCellValue) {
		        return this.defaultCalculateFilterExpression(filterValue, selectedFilterOperation);
		    }
		    return [this.dataField, "=", filterValue];
		}
	}, {
		caption : "수발신구분",
		dataField : "GUBUN_DESC",
		alignment : "center"
	}, {
		caption : "내선/국선",
		dataField : "TYPE_DESC",
		alignment : "center"
	}, {
		caption : "연결구분",
		dataField : "CON_OK",
		alignment : "center"
	}, {
		caption : "내선번호",
		dataField : "DIV_NO",
		alignment : "center"
	}, {
		caption : "상담원명",
		dataField : "AGENT_NAME",
		alignment : "center"
	}, {
		caption : "전환번호",
		dataField : "TRF_NO",
		alignment : "center"
	}]
}).dxDataGrid("instance");

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

//콜 이력 조회
function callRecodeSttsSelect(f_startDate, f_endDate){
 	$.ajax({
		url: "/MWTS/mctm/selectCallRecodeStts.do",
		type: "POST",
		data: {
			f_startDate : f_startDate,
			f_endDate : f_endDate
		},
		success: function(result){
			var data = result.dataList;
			
			$("#select_Grid").dxDataGrid({ dataSource: data });
		},
	    beforeSend: function(){
	    	var dataGrid = $("#select_Grid").dxDataGrid("instance");
	    	dataGrid.beginCustomLoading();
	    },
	    complete: function(){
	    	var dataGrid = $("#select_Grid").dxDataGrid("instance");
	    	dataGrid.endCustomLoading();
	    },
		error: function(jqXHR, textStatus, errorThrown){
			DevExpress.ui.notify("조회 중 오류 발생 status["+textStatus+"]\nmessage["+errorThrown+"]", "error");
		}});	
}
callRecodeSttsSelect(start_date, end_date);
</script>