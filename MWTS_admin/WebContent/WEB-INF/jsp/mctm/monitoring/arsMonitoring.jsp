<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>ARS 모니터링</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	
<%@include file="../../mwts/layout/topJS.jsp"%>
<style type="text/css">
/*배경 이미지*/
.demo-container:after {
    content : "";
    display: block;
    position: absolute;
    width: 100%;
    height: 100%;
    background-image: url('../images/bg_gray.jpg'); 
    background-repeat: no-repeat; 
    background-size: cover !important; 
    background-position: center center;
    opacity : 0.3;
    filter: alpha(opacity=30);    
    z-index: -1;
}
/*타이틀*/
#head_Grid span {
	position: absolute; 
	left: 30px; 
	font-size: 17px; 
	font-family: Noto Sans KR, sans-serif; 
	color: black;
}
</style>
</head>

<body class="dx-viewport">
    <div class="demo-container" style="position: absolute; height: 100%; width: 100% ">
		<div id="head_Grid" style="position: absolute; top: 3px; height: 30px; width: calc(100% - 200px); margin: 0 100px 0 100px; ">
			<img src="../images/mctm/phone.png" style="margin-top:2px;"></img>
     		<span>ARS 모니터링</span>	
		</div>
		<div id="service_Grid" 
			style="position: absolute; top: 34px; height: 60px; width: calc(100% - 200px); margin: 0 100px 0 100px; overflow-x:hidden; background-color: transparent;"></div><!-- dxPivotGrid IE브라우저 가로스크롤 숨김 -->
		<div id="ars_Grid" 
			style="position: absolute; top: 100px; height: calc(100% - 110px); width: calc(100% - 200px); margin: 0 100px 0 100px; overflow-x:hidden; background-color: transparent;"></div><!-- dxPivotGrid IE브라우저 가로스크롤 숨김 -->
	</div>
</body>
</html>

<script type="text/javascript">
/*
 * ARS통계 조회
 */
$("#ars_Grid").dxPivotGrid({
    allowSortingBySummary: true,
    allowSorting: true,
    allowExpandAll: true,
    showBorders: true,
    wordWrapEnabled: false,
    showRowGrandTotals: true,
    showColumnGrandTotals: false,
    showRowTotals: false,
    showColumnTotals: false,
    loadPanel: {
        enabled: false
    },     
    fieldChooser: {
        enabled: false
    },
    fieldPanel: {
        visible: false
    },
    stateStoring: {//area 지정 상태 저장
        enabled: true,
        type: "localStorage",
        storageKey: "arsMonitoring"
    },    
    dataSource: {
        fields: [{
            caption: "팀",
            dataField: "group_name",
           	area: "filter"
        }, {
            caption: "시간",
            dataField: "dt_H",
            area: "row"
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
            caption: "상담요청",
            dataField: "REQ_CALL",
            area: "data",
            summaryType: "sum"
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
                var result = e.value("REQ_CALL") / e.value("T_Call");
                return isNaN(result) ? "" : result;
            }            
        }, {
            caption: "포기호",
            dataField: "AB_CALL",
            area: "data",
            summaryType: "sum"
        }, {
            caption: "콜백",
            dataField: "CB_CALL",
            area: "data",
            summaryType: "sum"
        }, {
            caption: "전환",
            dataField: "FWD_CALL",
            area: "data",
            summaryType: "sum"
        }],
        store: new DevExpress.data.CustomStore({
        	load: function (loadOptions) {
        		var d = $.Deferred();
        		
	  	       	  $.getJSON('/MWTS/mctm/selectArsMonitoring.do', {
	  	   		    format: "json"
	  	 		  }).done(function(data) {
						if (data.RET == "0") {
							d.resolve(data.dataList);
						}else{
							clearInterval(updateArsMonitoring);
							$("#ars_Grid").dxPivotGrid("option", "dataSource.store", []);
							DevExpress.ui.notify(data.RET_MSG, "warning", 6000000);
						}
	  	 		  })
	  	 		  
	  	 		return d.promise();
  	   		}
        })        
    },
    onCellPrepared: function(e) {      
		e.cellElement.css("font-size", "12px"); 
		if(e.area==="data"){
			e.cellElement.css("background-color", "rgba(232, 234, 235, 0.5)"); 
			e.cellElement.css("color", "black"); 
		}else{
			e.cellElement.css("background-color", "#8ba2b5"); 
			e.cellElement.css("color", "white"); 
		}
	}   
}).dxPivotGrid("instance");	


/*
 * 서비스레벨통계 조회
 */
$("#service_Grid").dxPivotGrid({
    allowSortingBySummary: true,
    allowSorting: true,
    allowExpandAll: true,
    showBorders: true,
    wordWrapEnabled: false,
    showRowGrandTotals: true,
    showColumnGrandTotals: false,
    showRowTotals: false,
    showColumnTotals: false,
    loadPanel: {
        enabled: false
    },    
    fieldChooser: {
        enabled: false
    },
    fieldPanel: {
        visible: false
    },
    stateStoring: {//area 지정 상태 저장
        enabled: true,
        type: "localStorage",
        storageKey: "serviceMonitoring"
    },    
    dataSource: {
        fields: [{
            caption: "대기자 수",
            dataField: "cnt",
            area: "data",
            summaryType: "sum"
        }, {
            caption: "WAIT TIME",
            dataField: "waitTime",
            area: "column"
        }],
        store: new DevExpress.data.CustomStore({
        	load: function (loadOptions) {
        		var d = $.Deferred();
        		
	  	       	  $.getJSON('/MWTS/mctm/selectServiceMonitoring.do', {
	  	   		    format: "json"
	  	 		  }).done(function(data) {
	  	 			if (data.RET == "0") {
	  	 				d.resolve(data.dataList);
	  	 			}else{
	  	 				clearInterval(updateServiceMonitoring);
	  	 				$("#service_Grid").dxPivotGrid("option", "dataSource.store", []);
	  	 				DevExpress.ui.notify(data.RET_MSG, "warning", 6000000);
	  	 			}
	  	 		  })
	  	 		  
	  	 		return d.promise();
  	   		}
        })  
    },
    onCellPrepared: function(e) {      
		e.cellElement.css("font-size", "12px"); 
		if(e.area==="data"){
			e.cellElement.css("background-color", "rgba(232, 234, 235, 0.5)"); 
			e.cellElement.css("color", "black"); 
		}else{
			e.cellElement.css("background-color", "#8ba2b5"); 
			e.cellElement.css("color", "white"); 
		}
	}    
}).dxPivotGrid("instance");

//자동조회
updateArsMonitoring = setInterval(function() {
	$('#ars_Grid').dxPivotGrid('instance').getDataSource().reload();
  }, 5000);

updateServiceMonitoring = setInterval(function() {
	$('#service_Grid').dxPivotGrid('instance').getDataSource().reload();
  }, 5000);
</script>