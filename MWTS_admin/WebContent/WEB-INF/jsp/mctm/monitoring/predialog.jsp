<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>콜백관리</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	
<%@include file="../../mwts/layout/topJS.jsp"%>
<style type="text/css">
/*필터링 아이콘 변경*/
#distributeSelectGrid > .dx-datagrid .dx-header-filter {
    color: #444 !important;
}
#distributeSelectGrid > .dx-datagrid .dx-header-filter-empty {
    color: #444 !important;
}
#distributeSelectGrid > .dx-datagrid .dx-column-indicators {
	margin-top: 3px;
}
#distributeSelectGrid > .dx-datagrid .dx-sort-up:before {
  content: "\f051";
  color: #444;
}
#distributeSelectGrid > .dx-datagrid .dx-sort-down:before {
  content: "\f052";
  color: #444;
}
/*그리드 헤더 여백*/
.dx-datagrid-header-panel {
    padding: 10px 10px 0px 0px;
}
</style>
</head>

<body class="dx-viewport">
    <div class="demo-container" style="position: absolute; height: 100%; width: 100% ">
			
		<div id="search_Grid" style="position: absolute; height: 80px; width: 502px; ">
			<span style="position: absolute; top: 4px; font-size: 15px; font-weight: bold; color: #333; ">콜백조회</span>
			<hr style="border: 1px solid #e8e8e8; margin-top:33px;"/>
				<label style="position: absolute; width: 70px; top: 50px;">조회기간</label>
					<div id="startDate" style="position: absolute; left: 70px; width: 150px; "></div>					
					<div id="endDate" style="position: absolute; left: 230px; width: 150px; "></div>
				<div id="reloadBtn" style="position: absolute; left: 400px; width: 100px "></div>	

				<div id="autoReload" style="position: absolute; top: 3px; right:0px; width: 100px; height: 24px;">
					<div style="position: absolute; height: 24px; width: 24px;" class="refresh_check">
						<img src="../images/refresh_uncheck.png" class="check_img" style="cursor:pointer; height:100%; width:100% "/>
					</div>
					<span style="position: absolute; width: 70px; left: 31px; top: 1px; font-size:16px; color: #333;">자동조회</span>				
				</div>						
		</div>
		 		
 		<div id="distribute_Grid" style="position: absolute; height: 80px; width: 352px; left:550px; ">
			<span style="position: absolute; top: 4px; font-size: 15px; font-weight: bold; color: #333; ">콜백분배</span>
			<hr style="border: 1px solid #e8e8e8; margin-top:33px;"/>	
				<label style="position: absolute; top: 50px; width: 60px; ">담당자</label>
				<div id="agentDisBox" style="position: absolute; left: 60px; width: 170px "></div>
				<div id="distributeBtn" style="position: absolute; left: 250px; width: 100px "></div>
		</div>
		
		<div id="select_Grid" style="position: absolute; height: calc(100% - 190px); width: calc(100% - 220px); top: 90px; border: 2px solid #e8e8e8;"></div>
		<input type="hidden" id="chkIdx" name="chkIdx"/>
		
		<div id="update_Grid" style="position: absolute; height: 90px; width: calc(100% - 220px); bottom: 0px; ">
			<h4 style="font-size: 15px; font-weight: bold; color: #333 ">콜백처리</h4><hr style="border: 1px solid #e8e8e8; "/>
				<span style="position: absolute; top: 40px; width: 70px; text-align: center ">처리결과</span>
				<div id="resultUpdateBox" style="position: absolute; left: 80px; width: 130px "></div>
				<span style="position: absolute; top: 40px; width: 70px; text-align: center; left: 230px ">메모</span>
				<div id="memoBox" style="position: absolute; left: 300px; width: 480px; height: 37px "></div>
				<div id="predialogUpdateBtn" style="position: absolute; left: 800px; width: 100px "></div>
		</div>
		
		<div id="distribute_Container" style="position: absolute; height: 100%; width: 200px; right:0px; background-color: #f7f7f7 ">
			<div id="distributeSelectGrid" style="position: absolute; width: 100%; height: calc(100% - 70px) "></div>
			<div id="distributeUpdateBtn" style="position: absolute; width: 70%; left: 15%; bottom:15px; height:36px"></div>
		</div>
		<input type="hidden" id="chkYNAgentId" name="chkYNAgentId"/>
    </div>
</body>
</html>

<script type="text/javascript">
var userId = "<c:out value="${sessionScope.SESSION_USER.userId}" />";
var userName = "<c:out value="${sessionScope.SESSION_USER.userName}" />";
var strAgentID = "";
var strAgentName = "";
var strResult = "";
var strMemo = "";

//검색기간
var startD ="";
var start_date = "";
var endD ="";
var end_date = "";
var todayD = new Date();
var today = todayD.getFullYear() +""
				+ ((todayD.getMonth() + 1) >= 10 ? (todayD.getMonth() + 1) : '0'+(todayD.getMonth() + 1)) +""
				+ ((todayD.getDate()) >= 10 ? (todayD.getDate()) : '0'+(todayD.getDate()));

//검색시작일
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

//검색종료일
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

//담당자 조회
$.ajax({
	url : "/MWTS/mctm/selectVagent.do",
	type : "POST",
	data : {
		f_GroupName : ""
	},
	cache : false,
	async : false,
}).done(function(data) {
	var agentData = data.dataList;
	
	$("#agentDisBox").dxSelectBox({
		dataSource: agentData,
	    displayExpr: "AGENT_NAME",
	    valueExpr: "AGENT_ID",
	    onItemClick: function (data) {
	    	strAgentID = $("#agentDisBox").dxSelectBox('instance').option('value');
	    	strAgentName = $("#agentDisBox").dxSelectBox('instance').option('displayValue');
	    }
	}); 
	
	$("#distributeSelectGrid").dxDataGrid({
		dataSource : agentData,
		keyExpr: "AGENT_ID",
		paging: {
			enabled: false 
		},
		headerFilter : {
			visible : true
		},
	    selection: {
	        mode: "multiple"
	    },
	    columns: [{
	        dataField: "AGENT_NAME",
	        headerCellTemplate: $('<span style="font-size: 15px; font-weight: bold; color: black">콜백처리 담당자</span>')
	    }], 
	    onContentReady: function (e) {
	    	//체크박스 넓이 조정
	        if (e.component.shouldSkipNextReady) {
	            e.component.shouldSkipNextReady = false;
	        } else {
	            e.component.shouldSkipNextReady = true;
	            e.component.columnOption("command:select", "width", 40);
	            e.component.updateDimensions();
	        }
	    	
	    	//Y인 담당자 체크
			var grid = $("#distributeSelectGrid").dxDataGrid('instance');
			var rows = grid.getVisibleRows();

			var rowIndex = [];

			for (var i = 0; i < rows.length; i++) {
				var row = rows[i];

				if (row.rowType == "data") {
					if (row.data["AGENT_TEL3"] == "Y") {
						rowIndex.push(row.rowIndex);
					}
				}

			}

			grid.selectRowsByIndexes(rowIndex);    	
		},
		onSelectionChanged : function(selectedItems) {
			var selectRow = selectedItems.selectedRowsData;
			if (selectRow.length > 0)

				$("#chkYNAgentId").val($.map(selectRow, function(value) {
					return value.AGENT_ID;
				}).join("^"));
			else
				$("#chkYNAgentId").val("");

		}			
	}).dxDataGrid("instance");
	$(".dx-datagrid").css("background-color", "transparent");//배경색 투명
	
});	

//처리결과
$.ajax({
	url : "/MWTS/mctm/selectCodeMaster.do",
	type : "POST",
	data : {
		f_LD : "09"
	},
	cache : false,
	async : false,
}).done(function(data) {
	var resultData = data.dataList;
	
	$("#resultUpdateBox").dxSelectBox({
		dataSource: resultData,
	    displayExpr: "DESCRIPTION",
	    valueExpr: "md",
	    //value: resultData[0] ? resultData[0].DESCRIPTION : "",
		value: resultData[0] ? 'Y' : "",//Y : 처리완료
	    onInitialized: function (e) {
	    	strResult = e.component._options.value;
	    },
	    onItemClick: function (data) {
	    	strResult = $("#resultUpdateBox").dxSelectBox('instance').option('value');
	        
	    }
	});	
});

//메모
$("#memoBox").dxTextArea({
	maxLength: 100,
	value : "",
	onValueChanged: function (e) {
		strMemo = e.value;
	}
});

//조회
$("#reloadBtn").dxButton({
    text: "조회",
    type: "normal",
    onClick: function(e) {    
    	selectPredialog(start_date, end_date);
    }
}); 

$("#select_Grid").dxDataGrid({
	dataSource : {},
	cacheEnabled : false,
	headerFilter : {
		visible : true
	},			
	searchPanel : {
		visible : true,
		width:480
	},
	keyExpr : "INDEX_NO",
	selection : {
		mode : "multiple"
	},
	scrolling: {
           columnRenderingMode: "standard",
           showScrollbar: "always"
       },
    "export": {
        enabled: true,
        fileName: "콜백관리_"+today
    },
	hoverStateEnabled : true,
	columnAutoWidth : false,
	allowResizing: true,
    allowColumnResizing: true,
    columnResizingMode: 'widget', // or 'nextColumn'
	scrolling: {
        columnRenderingMode: "standard",
        showScrollbar: "always"
    },
	columns : [{
		caption : "index",
		dataField : "INDEX_NO",
		visible: false
	}, {
		caption : "서비스",
		dataField : "svc"
		
	}, {
		caption : "처리결과",
		dataField : "description"
		
	}, {
		caption : "직원ID",
		dataField : "AGENT_ID",
		visible: false
	}, {
		caption : "직원명",
		dataField : "AGENT_NAME"
		
	}, {
		caption : "콜백번호",
		dataField : "CALLBACK_NUM"
	}, {
		caption : "콜백일자",
		dataField : "CALL_DATE"
	}, {
		caption : "처리일자",
		dataField : "AGENT_DATE"
	}, {
		caption : "처리시간",
		dataField : "result_time"
	}, {
		caption : "분배일자",
		dataField : "ADMIN_DATE"
		
	}, {
		caption : "메모",
		dataField : "SMEMO"
		
	}],
/* 	onContentReady: function(e){
		e.element.find(".dx-datagrid-export-button").dxButton("instance").option("text", "엑셀");
    }, */		
    onToolbarPreparing: function (e) {
		var toolbarItems = e.toolbarOptions.items;
        
        //searchPanel 왼쪽정렬
        var searchPanel = $.grep(toolbarItems, function (item) {
            return item.name === "searchPanel";
        })[0];
        searchPanel.location = "before";
        
        //삭제버튼 추가
        toolbarItems.unshift({
            widget: 'dxButton', 
            options: { 
                elementAttr: {
                    "class": "deleteButton"
                }, 
            	icon: 'trash', 
            	//text: '삭제', 
            	disabled: true,
            	onClick: function() {
               		var result = DevExpress.ui.dialog.confirm("삭제하시겠습니까?");
               		$(".dx-popup-title.dx-toolbar").css("display", "none");//popup title 숨기기(내용없음)
               		
					result.done(function (dialogResult) {
						if (dialogResult) {
							deletePredialog();
						}
					});
            	} 
        	},
            location: 'after'
        });
    },			
    onSelectionChanged : function(selectedItems) {
    	var deleteBtn = $('.deleteButton').dxButton('instance'); 
      	var selectData = selectedItems.selectedRowsData;
      	
      	if(selectData.length > 0){
			$("#chkIdx").val($.map(selectData, function(value) {
				return value.INDEX_NO;
			}).join("^"));
			deleteBtn.option('disabled',false);
      	}else{
      		$("#chkIdx").val("");
      		deleteBtn.option('disabled',true);
      	}
	}
}).dxDataGrid("instance");
	
//콜백조회
function selectPredialog(f_start_date, f_end_date){
	$.ajax({
		url : "/MWTS/mctm/selectPredialog.do",
		type : "POST",
		data : {
			f_start_date : f_start_date,
			f_end_date : f_end_date
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
	}).done(function(data) {
		var data = data.dataList;

		$("#select_Grid").dxDataGrid({ dataSource: data });
	});	
}
selectPredialog(start_date, end_date);

//자동조회
var auto_reload;
var reload_timeOut;
var refreshCheck = $('.refresh_check');
var checkImg = refreshCheck.find('.check_img');

refreshCheck.on('click', function(){
	refreshCheck.toggleClass('check');
	if(refreshCheck.hasClass('check')){
		checkImg.attr('src','../images/refresh_check.png');
		
 		auto_reload = setInterval(function() {
			selectPredialog(start_date, end_date);
		}, 60000);			
	}else{
		checkImg.attr('src','../images/refresh_uncheck.png');
		
		clearInterval(auto_reload);
	}
})

function deletePredialog(){
	$.ajax({
		url : "/MWTS/mctm/deletePredialog.do",
		data : {
			f_Idx : $("#chkIdx").val()
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
		success : function(result) {
			if(result.RET == "0"){
				selectPredialog(start_date, end_date);
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

//콜백분배
function distributePredialog(f_DisAgentID, f_DisAgentName){
	$.ajax({
		url : "/MWTS/mctm/distributePredialog.do",
		data : {
			f_IndexNo : $("#chkIdx").val(),
			f_DisAgentID : f_DisAgentID,
			f_DisAgentName : f_DisAgentName
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
		success : function(result) {
			if(result.RET == "0"){
				selectPredialog(start_date, end_date);
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
//분배
$("#distributeBtn").dxButton({
    text: "분배",
    type: "normal",
    onClick: function(e) {    
    	if(checkIdx()){
    		distributePredialog(strAgentID, strAgentName);
    	}
    }
}); 

//콜백분배 적용
function updatePredialogAgentReg(){
	$.ajax({
		url : "/MWTS/mctm/updatePredialogAgentReg.do",
		data : {
			f_agentId : $("#chkYNAgentId").val()
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
		success : function(result) {
			if(result.RET == "0"){
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
//적용
$("#distributeUpdateBtn").dxButton({
    text: "적용",
    type: "default",
    onClick: function(e) {        	
    	updatePredialogAgentReg();
    }
}); 

//콜백처리
function updatePredialog(f_UserId, f_UserName, f_ResultUpdate, f_Memo){
	$.ajax({
		url : "/MWTS/mctm/updatePredialog.do",
		data : {
			f_IndexNo : $("#chkIdx").val(),
			f_UserId : f_UserId,
			f_UserName : f_UserName,
			f_ResultUpdate : f_ResultUpdate,
			f_Memo : f_Memo
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
		success : function(result) {
			if(result.RET == "0"){
				selectPredialog(start_date, end_date);
				DevExpress.ui.notify(result.RET_MSG, "info");
			}else if (result.RET == "1"){
				DevExpress.ui.notify(result.RET_MSG, "warning");
			}else if (result.RET == "E"){
				DevExpress.ui.notify(result.RET_MSG, "error");
			}
			//저장 후 처리영역 초기화
			$("#resultUpdateBox").dxSelectBox({ value: "Y" });
			$("#memoBox").dxTextArea({ value: "" });
		},
		error : function(jqXHR, textStatus, errorThrown) {
			DevExpress.ui.notify("오류 발생 status[" + textStatus
					+ "]\nmessage[" + errorThrown + "]", "error");
		}
	});		
}
//저장
$("#predialogUpdateBtn").dxButton({
    text: "저장",
    type: "normal",
    onClick: function(e) {      
    	if(checkIdx()){
    		updatePredialog(userId, userName, strResult, strMemo);
    	}
    }
}); 

//콜백리스트 체크 여부 확인
function checkIdx (){
	if(!$("#chkIdx").val() || $("#chkIdx").val()==undefined || $("#chkIdx").val()==null){
		return false;
	}else{
		return true;
	}	
}

</script>