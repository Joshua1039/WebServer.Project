<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>상담원 콜시간통계</title>
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
    	agentCallSttsSelect(start_date, end_date);
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
    groupPanel: {
        visible: true
    },
    "export": {
        enabled: true,
        fileName: "상담원 콜시간통계_"+today,
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
		caption : "월",
		dataField : "dt_M",
		alignment : "center",
		groupIndex : 0
	}, {
		caption : "대상자",
		dataField : "AGENT_NAME",
		alignment : "center",
		groupIndex : 0
	}, {
		caption : "일",
		dataField : "dt_D",
		alignment : "center"
	}, {
		//caption : "로그인 시간",
		headerCellTemplate: $('<div>로그인</br>시간</div>'),
		dataField : "login_tm",
		alignment : "center"
	}, {
		//caption : "로그아웃 시간",
		headerCellTemplate: $('<div>로그아웃</br>시간</div>'),
		dataField : "logout_tm",
		alignment : "center"
	}, {
		caption : "근무시간",
		dataField : "job_tm",
		alignment : "center",
	    customizeText: function(e) {
       		var retTxt = customText(e);
       		return retTxt;
	    }
	}, {
		caption : "수신대기",
		dataField : "IT",
		alignment : "center",
	    customizeText: function(e) {
       		var retTxt = customText(e);
       		return retTxt;
	    }
	}/* , {
		caption : "DND",
		dataField : "DT",
		alignment : "center",
	    customizeText: function(e) {
       		var retTxt = customText(e);
       		return retTxt;
	    }
	}, {
		caption : "업무처리",
		dataField : "WT",
		alignment : "center",
	    customizeText: function(e) {
       		var retTxt = customText(e);
       		return retTxt;
	    }
	}, {
		caption : "휴식",
		dataField : "BT",
		alignment : "center",
	    customizeText: function(e) {
       		var retTxt = customText(e);
       		return retTxt;
	    }
	}, {
		caption : "교육",
		dataField : "MT",
		alignment : "center",
	    customizeText: function(e) {
       		var retTxt = customText(e);
       		return retTxt;
	    }
	}*/, {
		caption : "이석",//업무처리 + 휴식 + 교육
		dataField : "ABT",
		alignment : "center",
	    customizeText: function(e) {
       		var retTxt = customText(e);
       		return retTxt;
	    }
	}, {
		//caption : "총 시간(인/아웃)",
		headerCellTemplate: $('<div>총 시간</br>(인/아웃)</div>'),
		dataField : "all_call",
		alignment : "center",
	    customizeText: function(e) {
       		var retTxt = customText(e);
       		return retTxt;
	    }
	}, {
		//caption : "총 건수(인/아웃)",
		headerCellTemplate: $('<div>총 건수</br>(인/아웃)</div>'),
		dataField : "all_cnt",
		alignment : "center"
	}, {
        caption: "응대호",
        alignment: "center",
        columns: [{
        	caption : "콜수",
			dataField : "I_cnt",
			alignment: "center"
		}, {
			caption : "총통화량",
			dataField : "I_call",
			alignment: "center",
		    customizeText: function(e) {
	       		var retTxt = customText(e);
	       		return retTxt;
		    }
		}, {
        	caption : "평균",
			dataField : "I_avg",
			alignment: "center",
		    customizeText: function(e) {
	       		var retTxt = customText(e);
	       		return retTxt;
		    }
		}]
    }, {
        caption: "발신호",
        alignment: "center",
        columns: [{
        	caption : "콜수",
			dataField : "O_cnt",
			alignment: "center"
		}, {
			caption : "총통화량",
			dataField : "O_call",
			alignment: "center",
		    customizeText: function(e) {
	       		var retTxt = customText(e);
	       		return retTxt;
		    }
		}, {
        	caption : "평균",
			dataField : "O_avg",
			alignment: "center",
		    customizeText: function(e) {
	       		var retTxt = customText(e);
	       		return retTxt;
		    }
		}]
    }],
    summary: {//합계
        totalItems: [{
            column: "dt_D",//'합계'문구 추가용으로 첫번째 컬럼 사용
            summaryType: "sum",
            customizeText: function(e) {
	       		var retTxt = "합계"
	       		return retTxt;
            }
        }, {
            column: "job_tm",//근무시간
            summaryType: "sum",
            customizeText: function(e) {
	       		var retTxt = customText(e);
	       		return retTxt;
            }
        }, {
            column: "IT",//수신대기
            summaryType: "sum",
            customizeText: function(e) {
	       		var retTxt = customText(e);
	       		return retTxt;
            }
        }, {
            column: "WT",//업무처리
            summaryType: "sum",
            customizeText: function(e) {
	       		var retTxt = customText(e);
	       		return retTxt;
            }
        }, {
            column: "BT",//휴식
            summaryType: "sum",
            customizeText: function(e) {
	       		var retTxt = customText(e);
	       		return retTxt;
            }
        }, {
            column: "MT",//교육
            summaryType: "sum",
            customizeText: function(e) {
	       		var retTxt = customText(e);
	       		return retTxt;
            }
        }, {
            column: "ABT",//이석(업무처리 + 휴식 + 교육)
            summaryType: "sum",
            customizeText: function(e) {
	       		var retTxt = customText(e);
	       		return retTxt;
            }
        }, {
            column: "all_call",//총시간
            summaryType: "sum",
            customizeText: function(e) {
	       		var retTxt = customText(e);
	       		return retTxt;
            }
        }, {
            column: "all_cnt",//총건수
            summaryType: "sum",
            valueFormat: "#,##0",
            displayFormat: "{0}"
        }, {
            column: "I_cnt",//응대호 콜수
            summaryType: "sum",
            valueFormat: "#,##0",
            displayFormat: "{0}"
        }, {
            column: "I_call",//응대호 총통화량
            summaryType: "sum",
            customizeText: function(e) {
	       		var retTxt = customText(e);
	       		return retTxt;
            }
        }, {
            column: "I_avg",//응대호 평균
            summaryType: "sum",
            customizeText: function(e) {
	       		var retTxt = customText(e);
	       		return retTxt;
            }
        }, {
            column: "O_cnt",//발신호 콜수
            summaryType: "sum",
            valueFormat: "#,##0",
            displayFormat: "{0}"
        }, {
            column: "O_call",//발신호 총통화량
            summaryType: "sum",
            customizeText: function(e) {
	       		var retTxt = customText(e);
	       		return retTxt;
            }
        }, {
            column: "O_avg",//발신호 평균
            summaryType: "sum",
            customizeText: function(e) {
	       		var retTxt = customText(e);
	       		return retTxt;
            }
        }]
    },
    onCellPrepared: function(e) {
        if (e.rowType == "totalFooter") {
        	//그리드 하단 합계 색상 변경
        	e.cellElement.find(".dx-datagrid-summary-item").css("color","red");
        }
    }
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

//상담원 콜시간통계 조회
function agentCallSttsSelect(f_startDate, f_endDate){
 	$.ajax({
		url: "/MWTS/mctm/selectAgentCallStts.do",
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
agentCallSttsSelect(start_date, end_date);
</script>