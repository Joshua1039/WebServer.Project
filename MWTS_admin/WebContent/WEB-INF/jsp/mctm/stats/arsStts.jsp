<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>ARS통계</title>
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
			<!-- <div id="chartBtn" style="position: absolute; right: 0px "></div>
			<div id="chartNewBtn" style="position: absolute; right: 120px "></div> -->
			<div id="chartNewBtn" style="position: absolute; right: 0px "></div>
			
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
    	$('#pivot_Grid').dxPivotGrid('instance').getDataSource().reload();
    }
});

var retData ="";
var pivotOption ="";
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
        fileName: "ARS통계_"+today
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
        store: new DevExpress.data.CustomStore({
        	load: function (loadOptions) {
        		var d = $.Deferred();
        		
	  	       	  $.getJSON('/MWTS/mctm/selectArsStts.do', {
	  	   		    format: "json",
	  	  			f_startDate : start_date,
	  	  			f_endDate : end_date
	  	 		  }).done(function(data) {
	  	 				d.resolve(data.dataList);
	  	 				retData = data.dataList;
	  	 		  })
	  	 		  
	  	 		return d.promise();
  	   		}
        }) 
    },
    onCellPrepared: function(e) {
    	e.cellElement.css("color", "black");
		e.cellElement.css("font-size", "15px");
		
		//배경 구분
		if(e.area == "column"){
			if(e.rowIndex == 0){
				//짝수 홀수
				if(e.columnIndex%2 == 0){
					e.cellElement.css("background-color", "rgb(211, 231, 247)");
				}else{
					e.cellElement.css("background-color", "rgba(223, 247, 188, 1)");
				}
			}
		}else if(e.area == "data"){
			//합계 색상 변경(showRowGrandTotals)
          	if (e.cell.rowType == "GT")  {
         		e.cellElement.css("color", "#f30505");
         		e.cellElement.css("font-weight", "bold");
         	} 
		}
	}
}).dxPivotGrid("instance");

var chartPopupInstance = $("#pivot_Chart").dxPopup({
    width: 1500,
    height: 600,
    resizeEnabled: true,
    dragEnabled: true,
    shading: false,
    contentTemplate: function(contentElement) {
    	//팝업 안에 차트 그리기
		$("<div style='margin-top: 50px; '/>")
		.appendTo(contentElement)
		.dxChart({
			dataSource: retData,
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
	        }
	    }).dxChart("instance");
    }
}).dxPopup("instance"); 

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
    	var agent = navigator.userAgent.toLowerCase();

		if ((agent.indexOf("msie") != -1) || (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1)) {
			//IE 브라우저
			//(agent.indexOf("msie") != -1) IE 10버전 이하
			//(navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) IE 11버전
			window.open("/MWTS/mctm/arsSttsChartIE.do");
		}else{
			//IE 브라우저 외
			pivotOption = $("#pivot_Grid").dxPivotGrid("instance").option();
			
			window.open("/MWTS/mctm/arsSttsChart.do");
		}
    }
});
</script>