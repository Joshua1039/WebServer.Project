<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>녹취조회청취</title>
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
			<div style="position: absolute; top: 6px; left: 520px; width: 250px; font-size: 17px;">TOTAL COUNT : <span id="count"></span></div>						
		</div>
		<div id="popup"></div>
		<div id="select_Grid" style="position: absolute; top: 60px; height: calc(100% - 70px); width: 100%; overflow-x:hidden; overflow-y:hidden;"><!-- IE브라우저 가로, 세로 스크롤 숨김 -->
		</div>
    </div>
</body>
</html>

<script type="text/javascript">
var listenYn = "<c:out value="${sessionScope.SESSION_USER.listenYn}" />";
var saveYn = "<c:out value="${sessionScope.SESSION_USER.saveYn}" />";
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
    	recDataSelect(start_date, end_date);
    }
});

$("#select_Grid").dxDataGrid({
    dataSource: {},
    columnAutoWidth : false,
	allowColumnResizing : true,
	allowColumnReordering : true,
	columnResizingMode: "widget",//nextColumn
	hoverStateEnabled: true,
    paging : {
    	enabled: true
    }, 
    headerFilter:{
    	visible: true
    },
    searchPanel:{
    	visible: true,
    	width : 300
    },
    groupPanel: {
        visible: true
    },
    "export": {
        enabled: true,
        fileName: "녹취조회청취_"+today
    },
    selection: {
        mode: "single"
    },
	scrolling: {
        columnRenderingMode: "standard",
        showScrollbar: "always"
    },
    grouping: {
    	contextMenuEnabled: true
    },
    keyExpr: ["SERVER_CODE" , "REC_FILENAME"],
    columns: [{
        caption: "녹취일자",
        dataField: "REC_START_DATE",
        width : 90
    }, {
        caption: "시작시간",
        dataField: "REC_START_TIME",
        width : 90
    }, {
        caption: "종료시간",
        dataField: "REC_END_DATETIME",
        width : 150
    }, {
        caption: "수발신",
        dataField: "REC_TYPE"            
    }, {
        caption: "통화시간",
        dataField: "DURATION"            
    }, {
        caption: "내선번호",
        dataField: "TEL_NO"            
    }, {
        caption: "대상자 ID",
        dataField: "AGENT_ID"            
    }, {
        caption: "대상자명",
        dataField: "AGENT_NAME"            
    }, {
        caption: "회사",
        dataField: "NM1"            
    }, {
        caption: "부서",
        dataField: "NM2"            
    }, {
        caption: "팀",
        dataField: "NM3"            
    }, {
        caption: "고객전화번호",
        dataField: "CID",
        width : 130,
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
        caption: "청취횟수",
        dataField: "LISTEN_BIT",
        alignment : 'left'
    }, {
        caption: "서버명",
        dataField: "SERVER_NAME"            
    }, {
        caption: "녹취파일명",
        dataField: "REC_FILENAME",
        width : 220
    }, {
        caption: "URL",
        dataField: "URL",
        visible: false 
    }, {
        caption : "PLAY",
        alignment: 'center',
        width: 85,
        cellTemplate: function (container, options) {
        	var serverIp = options.data.URL;
        	var fileName = options.data.REC_FILENAME;
        	var recDate = options.data.REC_START_DATE;
        	var serverCode = options.data.SERVER_CODE;
        	
    		if(!serverIp || !fileName || listenYn=="N"){ 
                $('<img />').attr('src', '../images/play_disable.png')
                .appendTo(container);
    		}else{
                $('<img />').attr('src', '../images/play.png').attr('style', 'cursor:pointer').attr('title', 'PLAY')
                .on('dxclick', function () {
                	fnPlay(serverIp, fileName, 'P', recDate, serverCode);
                })                        
                .appendTo(container);
    		}
        }
	}, {
        caption : "SAVE",
        alignment: 'center',
        width: 85,
        cellTemplate: function (container, options) {
        	var serverIp = options.data.URL;
        	var fileName = options.data.REC_FILENAME;
        	var recDate = options.data.REC_START_DATE;
        	var serverCode = options.data.SERVER_CODE;
        	
        	if(!serverIp || !fileName || saveYn=="N"){ 
        		$('<img />').attr('src', '../images/save_disable.png')
                .appendTo(container);
    		}else{
                $('<img />').attr('src', '../images/save.png').attr('style', 'cursor:pointer').attr('title', 'SAVE')
                .on('dxclick', function () {
                	fnPlay(serverIp, fileName, 'S', recDate, serverCode);
                })                        
                .appendTo(container);
    		}
        }
	}],
    onContentReady: function(e) {
    	//$('#count').text(e.component.totalCount());
    	var commaText = numberWithCommas(e.component.totalCount());
    	$('#count').text(commaText);
    	
    	//grouping 카운트 표시
        if (!e.component.__summaryIsNotAdded) {
          var items = [];
          for (var i = 0; i < e.component.columnCount(); i++) {
            if (i == 0) {
               items.push({
					column: e.component.columnOption(i, "dataField"),
					summaryType: "count",
	                valueFormat: "#,##0",
	                displayFormat: "Count: {0}"
               });  
              break;
            }
            
          }

          e.component.option("summary.groupItems", items);
          e.component.__summaryIsNotAdded = true;
        }
  }/* ,
  sortByGroupSummaryInfo: [
    {
      summaryItem: "count"
    }
  ]	 */        
});

//세자리 콤마
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

//녹취조회청취 조회
function recDataSelect(f_startDate, f_endDate){
 	$.ajax({
		url: "/MWTS/acr/selectRecData.do",
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
recDataSelect(start_date, end_date);

//플레이어
function fnPlay(server_ip, file_name, job, rec_date, server_code){
	$.ajax({
	url : '/MWTS/acr/recPlay.do',
	data : {
			f_serverIP : server_ip,
			f_fileName : file_name,
			f_job : job,
			f_recDate : rec_date,
			f_serverCode : server_code
	},
	success : function(result) {
		if (result.RET == "0") {
			if (job == "P"){
				$("#popup").dxPopup({
					title : result.FILE_NAME,
					visible : true,
					shading : false,
					width : 580,
					height : 200,
					contentTemplate : function(contentElement) {
						//127.0.0.1:8001
						//$("<audio controls='controls' autoplay='true'><source src='http://"+server_ip+":8080/MWTS/mp3/"+result.FILE_NAME+"' type='audio/mpeg' /></audio>").appendTo(contentElement);
						//$("<audio controls='controls' autoplay='true'><source src='/MWTS/mp3/"+result.FILE_NAME+"' type='audio/mpeg' /></audio>").appendTo(contentElement);
						$("<audio id ='audio_player' controls controlsList='nodownload' autoplay='true'><source src='/MWTS/mp3/"+result.FILE_NAME+"' type='audio/mpeg' /></audio>").appendTo(contentElement);
						$("<div id='p_speed_0.5' style='position: absolute; top: 150px; right: 280px;'><span style='font-weight: bold;'>0.5</span><img src='../images/p_speed.png' style='cursor:pointer;' onclick='playbackspeed(0.5);'/></div>").appendTo(contentElement);
						$("<div id='p_speed_1.0' style='position: absolute; top: 150px; right: 220px;'><span style='font-weight: bold;'>1.0</span><img src='../images/p_speed.png' style='cursor:pointer;' onclick='playbackspeed(1.0);'/></div>").appendTo(contentElement);
						$("<div id='p_speed_1.5' style='position: absolute; top: 150px; right: 160px;'><span style='font-weight: bold;'>1.5</span><img src='../images/p_speed.png' style='cursor:pointer;' onclick='playbackspeed(1.5);'/></div>").appendTo(contentElement);
						$("<div id='p_speed_2.0' style='position: absolute; top: 150px; right: 100px;'><span style='font-weight: bold;'>2.0</span><img src='../images/p_speed.png' style='cursor:pointer;' onclick='playbackspeed(2.0);'/></div>").appendTo(contentElement);
						$("<div id='p_loop' style='position: absolute; top: 150px; right: 30px;'><span style='font-weight: bold;'>loop</span><img src='../images/p_loop.png' style='cursor:pointer;' onclick='playloop();'/></div>").appendTo(contentElement);
					},
					onHiding: function() {
						//재생정지
						var audioPlayer = document.getElementById('audio_player');
						audioPlayer.pause();
						
						//청취횟수 업데이트 재조회
						recDataSelect(start_date, end_date);
					}
				});						
			}else if(job == "S"){
				//var saveLink = $("<a onclick=location.href='http://"+server_ip+":8080/MWTS/mp3/"+result.FILE_NAME+"' download>");
				var saveLink = $("<a onclick=location.href='/MWTS/mp3/"+result.FILE_NAME+"' download>");
				$("#popup").append(saveLink);
				$("#popup").find("a").trigger("click");
			}
		}else {
			DevExpress.ui.notify(result.RET_MSG, "error");
		}
	},
    beforeSend: function(){
    	var dataGrid = $("#select_Grid").dxDataGrid("instance");
    	dataGrid.beginCustomLoading();
    },
    complete: function(){
    	var dataGrid = $("#select_Grid").dxDataGrid("instance");
    	dataGrid.endCustomLoading();
    },		
	error : function(jqXHR, textStatus, errorThrown) {
		DevExpress.ui.notify("오류 발생 status[" + textStatus
				+ "]\nmessage[" + errorThrown + "]", "error");
	}
});
}

//재생속도
function playbackspeed(f_speed){
	var audioPlayer = document.getElementById('audio_player');
	audioPlayer.playbackRate = f_speed;
}

//반복재생
function playloop(){
	var audioPlayer = document.getElementById('audio_player');
	audioPlayer.loop = true;
}

/*
// Active X 사용하지않음
function fnPlay(server_ip, file_name, job){
    var URL = "http://192.168.0.11:8080/player/Mplayer.application?Server=" + server_ip + "&filename=" + file_name + "&job=" + job;
	window.open(URL);
}
*/
</script>