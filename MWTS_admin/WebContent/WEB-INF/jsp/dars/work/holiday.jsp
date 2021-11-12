<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>휴일관리</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	
<%@include file="../../mwts/layout/topJS.jsp"%>
<style type="text/css">
/* 달력 */
.dx-calendar.dx-calendar-with-footer .dx-calendar-body {
	top : 90px;
	bottom : 10px;
}
.dx-calendar-navigator {
	top : 45px;
}
.dx-calendar.dx-calendar-with-footer .dx-calendar-footer {
	top : 0px;
	text-align : right;
}
.dx-calendar.dx-calendar-with-footer .dx-calendar-footer .dx-button {
	width : 110px;
}
</style>
</head>

<body class="dx-viewport">
    <div class="demo-container" style="position: absolute; height: 100%; width: 100%">
		<!-- 설정 -->
		<div id="updateContainer" style="position: absolute; height: 100%; width: 400px;">
			<h4 style="position: absolute; font-size: 15px; font-weight: bold">휴일 설정</h4><br />
			<hr style="border: thin solid #e6e3dd;"/><br />
			<div id="calendarGrid" style="position: absolute; height: calc(100% - 300px); width: 400px;"></div>
			<span style="position: absolute; bottom:230px; left:10px;">사이트</span>
			<div id="siteBox" style="position: absolute; left: 80px; width: 320px; bottom:215px;"></div>
			<span style="position: absolute; bottom:188px; left:10px;">휴일구분</span>
			<div id="holidayBox" style="position: absolute; left: 80px; width: 320px; bottom:172px;"></div></br>
			<span style="position: absolute; bottom:145px; left:10px;">설명</span>
			<div id="description" style="position: absolute; left: 80px; width: 320px; bottom:110px;"></div>

		    <div id="insertBtn" style="position: absolute; right: 0px; bottom: 60px;"></div>
		</div>
		<!-- 조회 -->
		<div id="selectContainer" style="position: absolute; height: 100%; width: calc(100% - 480px); left: 450px;">
			<h4 style="position: absolute; font-size: 15px; font-weight: bold">휴일 조회</h4><br />
			<hr style="border: thin solid #e6e3dd;"/><br />		
			<div id="holidayGrid" style="position: absolute; height: calc(100% - 50px); width: 100%; top:50px;"></div>		
		</div>
    </div>
</body>
</html>

<script type="text/javascript">
var strFlag ="";
var strMonth ="";
var strDay ="";
var strSite =""; 
var strData =""; 
var strDescription ="";

$("#calendarGrid").dxCalendar({
    value: new Date(),
    firstDayOfWeek: 0,
    zoomLevel: "month",
    maxZoomLevel: "month",
    minZoomLevel: "year", 
    showTodayButton: true,
    onInitialized: function(e) {
    	var dateVal = e.component._options.value;
    	strMonth = dateVal.getMonth()+1;
    	strDay = dateVal.getDate();
    },
    onValueChanged: function(e) {
    	var dateVal = e.value;
    	strMonth = dateVal.getMonth()+1;
    	strDay = dateVal.getDate();
    }
}).dxCalendar("instance");

//사이트 조회
$.ajax({
	url : "/MWTS/dars/selectCodeMst.do",
	type : "POST",
	data : {
		f_Category : "site"
	},
	cache : false, //IE 브라우저 캐싱방지
	async : false, // ajax 순차적으로 실행
}).done(function(data) {
	var siteData = data.siteList;
	
	$("#siteBox").dxSelectBox({
		dataSource: siteData,
	    displayExpr: "sdesc",
	    valueExpr: "code",
	    //value: siteData[0].code,
	    value: siteData[0] ? siteData[0].code : "",
	    onInitialized: function (e) {
	    	strSite = e.component._options.value;
	    },
	    onItemClick: function (data) {
	    	strSite = $("#siteBox").dxSelectBox('instance').option('value');
	        
	    }
	});
});

//휴일구분 조회
$.ajax({
	url : "/MWTS/dars/selectCodeMst.do",
	type : "POST",
	data : {
		f_Category : "holiday"
	},
	cache : false, //IE 브라우저 캐싱방지
	async : false, // ajax 순차적으로 실행
}).done(function(data) {
	var holidayData = data.holidayList;
	
	$("#holidayBox").dxSelectBox({
		dataSource: holidayData,
	    displayExpr: "sdesc",
	    valueExpr: "code",
	    //value: holidayData[0].code,
	    value: holidayData[0] ? holidayData[0].code : "",
	    onInitialized: function (e) {
	    	strData = e.component._options.value;
	    },
	    onItemClick: function (data) {
	    	strData = $("#holidayBox").dxSelectBox('instance').option('value');
	        
	    }
	});
});	

$("#description").dxTextArea({
	value: "",
	maxLength: 30,
    onValueChanged: function(data) {
    	strDescription = $("#description").dxTextArea('instance').option('value');
     }
});

$("#insertBtn").dxButton({
    text: "추가",
    type: "default",
    width: 120,
    onClick: function(e) {        	
    	strFlag ="insert";
    	
    	holidayUpdate(strFlag, strMonth, strDay, strData, strDescription, strSite);
    }
});

$("#holidayGrid").dxDataGrid({
	dataSource : {},
	//keyExpr : "site",
	keyExpr : ["month", "day", "site"],
	hoverStateEnabled : true,
	columnAutoWidth : false,
	allowResizing: true,
    allowColumnResizing: true,
    columnResizingMode: 'widget', // or 'nextColumn'
	scrolling: {
        columnRenderingMode: "standard",
        showScrollbar: "always"
    },
	showBorders: true,
    headerFilter: {
        visible: true
    },			
    editing: {
        mode: "row",
        allowDeleting: true,
        useIcons: true // version 18.1
    }, 
	columns : [{
		dataField : "month",
		caption : "월",
		alignment: "center",
		width: "100px",
	    customizeText: function(cellInfo) {
	        return cellInfo.value + "월";
	    }	
	},{
		dataField : "day",
		caption : "일",
		alignment: "center",
		width: "100px",
	    customizeText: function(cellInfo) {
	        return cellInfo.value + "일";
	    }			
	}, {
		dataField : "site_desc",
		caption : "사이트",
		alignment: "left"
	}, {
		dataField : "data_desc",
		caption : "휴일 구분",
		alignment: "left"
	}, {
		dataField : "description",
		caption : "설명",
		alignment: "left"
	}, {
		dataField : "site",
		caption : "사이트",
		visible: false
	}],
    onRowRemoving: function(e) {
    	strFlag ="delete";
    	strMonth = e.key.month;
    	strDay = e.key.day;
    	strSite = e.key.site;
    	
    	holidayUpdate(strFlag, strMonth, strDay, "", "", strSite);
    }
});	

//휴일관리 조회
function holidaySelect(){
	$.ajax({
		url : "/MWTS/dars/holidaySelect.do",
		type : "POST",
		data : {},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
	}).done(function(data) {
		var data = data.dataList;
		
		$("#holidayGrid").dxDataGrid({ dataSource: data });
	});
}
holidaySelect();

function holidayUpdate(f_Flag, f_Month, f_Day, f_Data, f_Description, f_Site){
	$.ajax({
		url : "/MWTS/dars/updateHoliday.do",
		data : {
			f_Flag : f_Flag,
			f_Month : f_Month,
			f_Day : f_Day,
			f_Data : f_Data,
			f_Description : f_Description,
			f_Site : f_Site
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
		success : function(result) {
			if(result.RET == "0"){
				holidaySelect();
				DevExpress.ui.notify(result.RET_MSG, "info");
			}else if (result.RET == "1"){
				DevExpress.ui.notify(result.RET_MSG, "warning");
			}else if (result.RET == "E"){
				DevExpress.ui.notify(result.RET_MSG, "error");
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			DevExpress.ui.notify("오류 발생 status[" + textStatus
					+ "]\nmessage[" + errorThrown + "]", "error");
		}
	});	
}

</script>
