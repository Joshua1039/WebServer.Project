<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>서비스통계</title>
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
			<div id="chartBtn" style="position: absolute; right: 0px "></div>
			<div id="chartNewBtn" style="position: absolute; right: 120px "></div>
			
		</div>
		<div id="pivot_Grid" style="position: absolute; top: 60px; height: calc(100% - 70px); width: 100%; overflow-x:hidden; "></div><!-- dxPivotGrid IE브라우저 가로스크롤 숨김 -->
		<div id="pivot_Chart"></div>
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
    	serviceSttsSelect(start_date, end_date);
    }
});

var retData ="";
var chartPopupInstance ="";

serviceSttsSelect(start_date, end_date);
function serviceSttsSelect(f_startDate, f_endDate){
	$.ajax({
		url : "/MWTS/dars/selectServiceStts.do",
		type : "POST",
		data : {
			f_startDate : f_startDate,
			f_endDate : f_endDate
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
	}).done(function(data) {
		retData = data.dataList;
	    
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
		    "export": {
		        enabled: true,
		        fileName: "서비스통계_"+today
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
		        store: retData
		    }
		}).dxPivotGrid("instance");
	    
	    
		chartPopupInstance = $("#pivot_Chart").dxPopup({
		    width: 1500,
		    height: 600,
		    resizeEnabled: true,
		    dragEnabled: true,
		    shading: false,
		    contentTemplate: function(contentElement) {
		    	//팝업 안에 차트 그리기
		    	pivotGridInstance.bindChart(
				$("<div style='margin-top: 50px; '/>")
				.appendTo(contentElement)
				.dxChart({
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
			    }).dxChart("instance"));
		    }
		}).dxPopup("instance"); 
	    
	    
	    
	});	
}

$("#chartBtn").dxButton({
    text: "차트",
    icon: 'chart',
    type: "normal",
    width: 100,
    onClick: function(e) {
    	//팝업으로 차트 띄우기
    	chartPopupInstance.show();
    }
});

$("#chartNewBtn").dxButton({
    text: "차트 (새 창)",
    icon: 'chart',
    type: "normal",
    width: 150,
    onClick: function(e) {
    	var parentOption = $("#pivot_Grid").dxPivotGrid("instance").option();
    	
    	window.open("/MWTS/dars/serviceSttsChart.do");
    }
});

</script>