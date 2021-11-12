<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>CID별 이력조회</title>
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
		
		<div id="select_Grid" style="position: absolute; top: 60px; height: calc(100% - 70px); width: 100%; overflow-x:hidden;"><!-- IE브라우저 가로스크롤 숨김 -->
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
    	callHistorySelect(start_date, end_date);
    }
});

$("#select_Grid").dxDataGrid({
	dataSource : {},
	columnAutoWidth : false,
	allowColumnResizing : true,
	allowColumnReordering : true,
	columnResizingMode: "widget",//nextColumn
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
    groupPanel: {
        visible: true
    },
    "export": {
        enabled: true,
        fileName: "CID별 이력조회_"+today,
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
		caption : "고객전화번호",
		dataField : "cid",
		groupIndex : 0
	}, {
		caption : "시간",
		dataField : "datetime"
	}, {
		caption : "DNIS",
		dataField : "dnis",
		width : 150
	}, {
		caption : "IVR 채널",
		dataField : "ch",
		alignment : "left",
		width : 100
	}, {
		caption : "내용",
		dataField : "description"
	}, {
		caption : "코드",
		dataField : "code",
		visible : false
	}]
}).dxDataGrid("instance");

//CID별 이력조회
function callHistorySelect(f_startDate, f_endDate){
 	$.ajax({
		url: "/MWTS/dars/callHistorySelect.do",
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
callHistorySelect(start_date, end_date);
</script>