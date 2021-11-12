<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>녹취 회선 모니터링</title>
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
#select_Grid > .dx-datagrid {
	color: black;
	border-radius: 1em;
	background-color: rgba(232, 234, 235, 0.3);
}
/*그리드 헤더*/
#select_Grid > .dx-datagrid > .dx-datagrid-headers {
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
/*서버*/
#serverCodeBox{
	border: 1px solid #777777;
}
/*전체*/
#totalCount {
	color: black;
	background: white;
	cursor : text;
	border: 1px solid #777777;
}
/*통화중*/
#callCount {
	color: black;
	background: white;
	cursor : text;
	border: 1px solid #777777;
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
			<img src="../images/acr/vpmMonitoring.png" style="margin-top:2px;"></img>
     		<span>녹취 회선 모니터링</span>	
		</div>
		
		<div id="serverCodeBox" style="position: absolute; width: 300px; top:60px; left:100px;"></div>
		<div id="totalCount" style="position: absolute; width: 180px; top: 60px; right: 300px;"></div>
		<div id="callCount" style="position: absolute; width: 180px; top: 60px; right: 100px;"></div>
		
		<div id="select_Grid" style="position: absolute; top: 110px; height: calc(100% - 130px); width: calc(100% - 200px); margin: 0 100px 0 100px;"></div>
		
		<form name="frmAudio" action="" method="post">
			<input type="hidden" name="server_ip" id="server_ip" value=""></input>
			<input type="hidden" name="vpm_num" id="vpm_num" value=""></input>
		</form>
		
		<div id="popup"></div>
    </div>
</body>
</html>

<script type="text/javascript">
//전체 카운트
$("#totalCount").dxButton({
	text : "",
	type : "normal",
	icon: "../images/line_black.png"
});

//통화중 카운트
$("#callCount").dxButton({
	text : "",
	type : "normal",
	icon: "../images/line_blue.png"
});

var call_count = "";
//녹취회선 모니터링 조회
$("#select_Grid").dxDataGrid({
	dataSource : {},
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
		caption : "채널",
		dataField : "vpm_num",
		alignment : "center"
	}, {
		caption : "부서",
		dataField : "nm3",
		alignment : "center"
	}, {
		caption : "내선번호",
		dataField : "tel_num",
		alignment : "center"
	}, {
		caption : "아이디",
		dataField : "agent_id",
		alignment : "center"
	}, {
		caption : "이름",
		dataField : "agent_name",
		alignment : "center"
	}, {
		caption : "상태",
		dataField : "vpm_status",
		alignment : "center"
	}, {
		caption : "상태시간",
		dataField : "status_time",
		alignment : "center"
	}, {
        caption: "서버IP",
        dataField: "server_ip",
		alignment : "center",
        visible: false 
    }, {
        caption : "PLAY",
        alignment: 'center',
        width: 85,
        cellTemplate: function (container, options) {
        	var serverIp = options.data.server_ip;
        	var vpmNum = options.data.vpm_num;
        	
    		if(!serverIp || !vpmNum){ 
                $('<img />').attr('src', '../images/play_disable.png')
                .appendTo(container);
    		}else{
                $('<img />').attr('src', '../images/play.png').attr('style', 'cursor:pointer').attr('title', 'PLAY')
                .on('dxclick', function () {
                	//팝업으로 열기
                	$("#popup").dxPopup({
						title : vpmNum+" 청취",
						showCloseButton: true,
						visible : true,
						shading : false,
						width : 580,
						height : 170,
						contentTemplate : function(contentElement) {
							$("<audio id ='real_player' controls controlsList='nodownload'><source src='http://"+serverIp+":8809/realtime/"+vpmNum+"' type='audio/mpeg' /></audio>").appendTo(contentElement);
						},
						onHiding: function() {
							//재생정지
							var realPlayer = document.getElementById('real_player');
							realPlayer.pause();
						}
					});	
                	
                	//새창으로 열기
/*                 	var formAudio = document.frmAudio;
                	window.open("","audioWindow");
                	//window.open("about:blank","audioWindow","width=600,height=100");
                	
                	formAudio.action ="/MWTS/acr/vpmAudio.do";
                	formAudio.method ="post";
                	formAudio.target ="audioWindow";
                	
                	$("#server_ip").val(serverIp);
                	$("#vpm_num").val(vpmNum);
                	formAudio.submit(); */
                })                        
                .appendTo(container);
    		}
        }
	}],
    summary: {
        totalItems: [{
            column: "vpm_num",//총 카운트
            summaryType: "count",
            customizeText: function(data) {
            	var commaText = numberWithCommas(data.value);
            	
            	var totalCountBtn = $('#totalCount').dxButton('instance'); 
            	totalCountBtn.option('text', '전체 : ' + commaText);
            }            
        },{
            name: "callRowsCount",
            showInColumn: "vpm_status",//녹취중 카운트
            summaryType: "custom"
        }],
        calculateCustomSummary: function (options) {
            if (options.name === "callRowsCount") {
                if (options.summaryProcess === "start") {
                		call_count = 0;
                }
                if (options.summaryProcess === "calculate") {
                    if (options.value["vpm_status"] == "통화중") {
                    	call_count = call_count + 1;
                    }
                }
                if (options.summaryProcess === "finalize") {
                	var commaText = numberWithCommas(call_count);
                	
                	var callCountBtn = $('#callCount').dxButton('instance'); 
	            	callCountBtn.option('text', '통화중 : ' + commaText);
                }  
            }
        }
    },
    onContentReady: function(e) {
        e.element.find(".dx-datagrid-total-footer").css("display", "none")//datagrid footer(summary) hide
    },
	onRowPrepared: function (e) {
	    if(e.rowType === 'data') {
	        if(e.data.vpm_status === '통화중'){
	        	e.rowElement.css({"color":"rgb(0, 150, 255)"});
	        }
	    }
	}    
});	

var selCode = "";
//서버코드 조회
$.ajax({
	url : "/MWTS/acr/selectServerInfo.do",
	type : "POST",
	data : {},
	cache : false, //IE 브라우저 캐싱방지
	async : false, // ajax 순차적으로 실행
}).done(function(data) {
	var data = data.dataList; 
	
	$("#serverCodeBox").dxSelectBox({
		dataSource: data,
	    displayExpr: "SERVER_NAME",
	    valueExpr: "SERVER_CODE",
	    value: data[0] ? data[0].SERVER_CODE : "",
	    onInitialized: function (e) {
	    	selCode = e.component._options.value;
	    	
	    	if(selCode){
	    		vpmMonitoringSelect(selCode);
	    	}	    	
	    },
	    onItemClick: function (e) {
	    	selCode = $("#serverCodeBox").dxSelectBox('instance').option('value');
	    	
	    	if(selCode){
	    		vpmMonitoringSelect(selCode);
	    	}
	    }
	});	
});

//세자리 콤마
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function vpmMonitoringSelect(f_Code){
	$.ajax({
		url : "/MWTS/acr/vpmMonitoringSelect.do",
		type : "POST",
		data : {
			f_Code : f_Code
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
	}).done(function(data) {
		var data = data.dataList;
		
		$("#select_Grid").dxDataGrid({ dataSource: data });
	});
}

//자동조회
updateVpmMonitoring = setInterval(function() {
	$.ajax({
		url : "/MWTS/acr/vpmMonitoringSelect.do",
		type : "POST",
		data : {
			f_Code : selCode
		},
		cache : false,
		async : false,
	}).done(function(data) {
		if (data.RET == "0") {
			var data = data.dataList;
			$("#select_Grid").dxDataGrid({ dataSource: data });	
		}else{
			clearInterval(updateVpmMonitoring);
			$("#select_Grid").dxDataGrid({ dataSource: [] });	
			DevExpress.ui.notify(data.RET_MSG, "warning", 6000000);
		}
	});		
  }, 5000);

</script>