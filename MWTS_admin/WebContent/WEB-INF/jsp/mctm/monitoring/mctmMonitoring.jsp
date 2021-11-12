<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>실시간 모니터링</title>
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
/*그리드*/
#ars_Grid > .dx-datagrid, #agent_Grid > .dx-datagrid {
	color: black;
	border-radius: 1em;
	background-color: rgba(232, 234, 235, 0.3);
}
/*그리드 헤더*/
#ars_Grid > .dx-datagrid > .dx-datagrid-headers, #agent_Grid > .dx-datagrid > .dx-datagrid-headers {
	color: white;
	border-radius: 1em 1em 0 0;
	background-color: #8ba2b5;
	line-height: 20px;
}
/*필터링 아이콘*/
.dx-header-filter {
    color: white !important;
}
.dx-header-filter-empty {
    color: white !important;
}
/*hover*/
.dx-rtl .dx-datagrid-rowsview .dx-selection.dx-row > td:not(.dx-focused).dx-datagrid-group-space,
.dx-rtl .dx-datagrid-rowsview .dx-selection.dx-row:hover > td:not(.dx-focused).dx-datagrid-group-space {
    border-left-color: #d5dce2;
}

.dx-datagrid-rowsview .dx-selection.dx-row:not(.dx-row-lines) > td,
.dx-datagrid-rowsview .dx-selection.dx-row:hover:not(.dx-row-lines) > td {
    border-bottom: 1px solid #d5dce2;
    border-top: 1px solid #d5dce2;
}

.dx-datagrid-rowsview .dx-selection.dx-row > td.dx-datagrid-group-space,
.dx-datagrid-rowsview .dx-selection.dx-row:hover > td.dx-datagrid-group-space {
    border-right-color: #d5dce2;
}

.dx-datagrid-rowsview .dx-selection.dx-row > td,
.dx-datagrid-rowsview .dx-selection.dx-row:hover > td {
    background-color: #d5dce2;
    color: black;
}

.dx-datagrid-table .dx-data-row.dx-state-hover:not(.dx-selection):not(.dx-row-inserted):not(.dx-row-removed):not(.dx-edit-row) > td:not(.dx-focused) {
  background-color: #d5dce2;
  color: black;
}
</style>
</head>

<body class="dx-viewport">
    <div class="demo-container" style="position: absolute; height: 100%; width: 100% ">
		<div id="head_Grid" style="position: absolute; top: 15px; height: 33px; width: calc(100% - 200px); margin: 0 100px 0 100px; border-bottom:1px solid #777777;">
			<img src="../images/mctm/mctmMonitoring.png" style="margin-top:2px;"></img>
     		<span>실시간 모니터링</span>	
		</div>

		<div id="ars_Grid" 
			style="position: absolute; top: 60px; height: 180px; width: calc(100% - 200px); margin: 0 100px 0 100px; "></div>
		<div id="agent_Grid" 
			style="position: absolute; top: 250px; height: calc(100% - 270px); width: calc(100% - 200px); margin: 0 100px 0 100px; "></div>
	</div>
</body>
</html>

<script type="text/javascript">
/*
 * ARS 조회
 */
$.ajax({
	url : "/MWTS/mctm/selectMctmArs.do",
	type : "POST",
	data : {},
	cache : false, //IE 브라우저 캐싱방지
	async : false, // ajax 순차적으로 실행
}).done(function(data) {
	var data = data.dataList;

	$("#ars_Grid").dxDataGrid({
		dataSource : data,
		cacheEnabled : false,
		headerFilter : {
			visible : true
		},
	    paging : {
	    	enabled: false
	    },	
	    loadPanel: {
	        enabled: false
	     },
	    showRowLines: false,
	    showColumnLines: false,
	    hoverStateEnabled : true,
		columns : [{
			caption : "팀명",
			dataField : "GROUP_NAME",
			alignment : "center"
		}, {
			caption : "대기호",
			dataField : "WAIT_CNT",
			alignment : "center"
		}, {
			caption : "IVR응대율",
			dataField : "CON_PER",
			alignment : "center"
		}, {
			caption : "IVR인입호",
			dataField : "REQ_CALL",
			alignment : "center"
		}, {
			caption : "IVR응대호",
			dataField : "CON_CALL",
			alignment : "center"
		}, {
			caption : "IVR포기호",
			dataField : "AB_CALL",
			alignment : "center"
		}, {
			caption : "상담인입호",
			dataField : "Call_cnt",
			alignment : "center"
		}, {
	        caption: "상담부재호",
	        dataField: "AB_cnt",
			alignment : "center"
	    }, {
	        caption: "콜백",
	        dataField: "CB_CALL",
			alignment : "center"
	    }, {
	        caption: "로그인",
	        dataField: "LOGIN_AGT_CNT",
			alignment : "center"
	    }]  
	});	
});

/*
 * 사용자 조회
 */
$.ajax({
	url : "/MWTS/mctm/selectMctmAgent.do",
	type : "POST",
	data : {},
	cache : false, //IE 브라우저 캐싱방지
	async : false, // ajax 순차적으로 실행
}).done(function(data) {
	var data = data.dataList;

	$("#agent_Grid").dxDataGrid({
		dataSource : data,
		cacheEnabled : false,
		headerFilter : {
			visible : true
		},
	    paging : {
	    	enabled: false
	    },	
	    loadPanel: {
	        enabled: false
	     },
	    showRowLines: false,
	    showColumnLines: false,
	    hoverStateEnabled : true,
		columns : [{
			caption : "팀명",
			dataField : "GROUP_NAME",
			alignment : "center"
		}, {
			caption : "이름",
			dataField : "AGENT_NAME",
			alignment : "center"
		}, {
			caption : "아이디",
			dataField : "AGENT_ID",
			alignment : "center"
		}, {
			caption : "내선번호",
			dataField : "AGENT_TEL",
			alignment : "center"
		}, {
			caption : "상태",
			dataField : "AGENT_STATUS",
			alignment : "center"
		}, {
			caption : "상태시간",
			dataField : "STATUS_TIME",
			alignment : "center"
		}, {
			caption : "수신대기",
			dataField : "IT",
			alignment : "center",
           	customizeText: function(e) {
           		var retTxt = customText(e);
           		return retTxt;
            }
		}, {
	        caption: "후처리",
	        dataField: "WT",
			alignment : "center",
           	customizeText: function(e) {
           		var retTxt = customText(e);
           		return retTxt;
            }
	    }, {
	        caption: "휴식",
	        dataField: "BT",
			alignment : "center",
           	customizeText: function(e) {
           		var retTxt = customText(e);
           		return retTxt;
            }
	    }, {
	        caption: "교육",
	        dataField: "MT",
			alignment : "center",
           	customizeText: function(e) {
           		var retTxt = customText(e);
           		return retTxt;
            }
	    }, {
	        caption: "총통화시간",
	        dataField: "DUR",
			alignment : "center",
           	customizeText: function(e) {
           		var retTxt = customText(e);
           		return retTxt;
            }
	    }, {
	        caption: "수신콜",
	        dataField: "I_Cnt",
			alignment : "center"
	    }, {
	        caption: "발신콜",
	        dataField: "O_Cnt",
			alignment : "center"
	    }]  
	});	
});

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

//자동조회
updateMctmArsMonitoring = setInterval(function() {
	$.ajax({
		url : "/MWTS/mctm/selectMctmArs.do",
		type : "POST",
		data : {},
		cache : false,
		async : false,
	}).done(function(data) {
		if (data.RET == "0") {
			var data = data.dataList;
			$("#ars_Grid").dxDataGrid({ dataSource: data });
		}else{
			clearInterval(updateMctmArsMonitoring);
			$("#ars_Grid").dxDataGrid({ dataSource: [] });
			DevExpress.ui.notify(data.RET_MSG, "warning", 6000000);
		}
	});		
  }, 5000);

updateMctmAgentMonitoring = setInterval(function() {
	$.ajax({
		url : "/MWTS/mctm/selectMctmAgent.do",
		type : "POST",
		data : {},
		cache : false,
		async : false,
	}).done(function(data) {
		if (data.RET == "0") {
			var data = data.dataList;
			$("#agent_Grid").dxDataGrid({ dataSource: data });
		}else{
			clearInterval(updateMctmAgentMonitoring);
			$("#agent_Grid").dxDataGrid({ dataSource: [] });
			DevExpress.ui.notify(data.RET_MSG, "warning", 6000000);
		}
	});			
  }, 5000);
</script>