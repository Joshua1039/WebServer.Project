<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>상담사 관리</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	
<%@include file="../../mwts/layout/topJS.jsp"%>
<style type="text/css">
/*그리드 헤더 여백*/
#agent_Grid > .dx-datagrid > .dx-datagrid-header-panel {
    padding: 10px 10px 0px 0px;
}
</style>
</head>

<body class="dx-viewport">
    <div class="demo-container" style="position: absolute; height: 100%; width: 100% ">
    
		<div id="agent_Select_Container" style="position: absolute; height: 100%; width: calc(100% - 350px); left:0px; ">
				<label style="position: absolute; top:7px; width: 80px; text-align: center ">그룹 선택</label>
				<div id="teamDeptCode" style="position: absolute; width: 300px; left: 90px "></div>
				
				<div id="agent_Grid" style="position: absolute; height: calc(100% - 60px); width: 100%; top: 50px; border: 2px solid #e8e8e8; "></div>
		</div>

		<div id="agent_Update_Container" style="position: absolute; height: 100%; width: 320px; right:20px; overflow:auto;">
			<div style="position: absolute; height: 320px; width: 100%; top: 150px;">
	  			<label style="position: absolute; right: 220px; width: 100px; text-align: right "><strong>* 아이디</strong></label>
				<div id="agentID" style="position: absolute; right: 0px; width: 200px "></div>
	 			<label style="position: absolute; right: 220px; width: 100px; text-align: right; top: 45px "><strong>* 이름</strong></label>
				<div id="agentName" style="position: absolute; right: 0px; width: 200px; top: 45px "></div>
	 			<label style="position: absolute; right: 220px; width: 100px; text-align: right; top: 90px "><strong>* 내선번호</strong></label>
				<div id="agentTel" style="position: absolute; right: 0px; width: 200px; top: 90px "></div>
	 			<label style="position: absolute; right: 220px; width: 100px; text-align: right; top: 135px "><strong>* 비밀번호</strong></label>
				<div id="agentPw" style="position: absolute; right: 0px; width: 200px; top: 135px "></div>
	 			<label style="position: absolute; right: 220px; width: 100px; text-align: right; top: 180px "><strong>* PickUp 그룹</strong></label>
				<div id="agentPickup" style="position: absolute; right: 0px; width: 200px; top: 180px "></div>
	 			<label style="position: absolute; right: 220px; width: 100px; text-align: right; top: 225px "><strong>* 그룹</strong></label>
				<div id="agentGroup" style="position: absolute; right: 0px; width: 200px; top: 225px "></div>

	 			<div id="btn_Grid" style="position: absolute; width: 100%; height:40px; bottom: 0px; text-align: center; ">
		 			<div id="agentUpdateBtn" style="position: absolute; width:90px; right:100px;"></div>
					<div id="agentDeleteBtn" style="position: absolute; width:90px; right:0px; "></div>
					<div id="agentInsertBtn" style="position: absolute; width:90px; right:0px; "></div>			
				</div>
			</div>
		</div>	
    </div>
</body>
</html>

<script type="text/javascript">
var strAgentFlag ="";
var strAgentID ="";
var strAgentName ="";
var strAgentTel ="";
var strAgentPw ="";
var strAgentPickup ="";
var strAgentGroup ="";

//ID
$("#agentID").dxTextBox({
	maxLength: 20,
	readOnly: false,
	value: "",
	onValueChanged: function (e) {
		strAgentID = e.value;
	}
})
//이름
$("#agentName").dxTextBox({
	maxLength: 20,
	value: "",
	onValueChanged: function (e) {
		strAgentName = e.value;
	}
})
//내선번호
$("#agentTel").dxTextBox({
	maxLength: 4,
	value: "",
	onValueChanged: function (e) {
		strAgentTel = e.value;
	}
})
//비밀번호
$("#agentPw").dxTextBox({
	maxLength: 20,
	value: "",
	onValueChanged: function (e) {
		strAgentPw = e.value;
	}
})
//PickUp 그룹
$("#agentPickup").dxTextBox({
	maxLength: 2,
	value: "",
	onValueChanged: function (e) {
		strAgentPickup = e.value;
	}
})
//저장
$("#agentInsertBtn").dxButton({
    text: "저장",
    type: "default",
    onClick: function(e) { 
    	strAgentFlag = "insert";
    	agentRegUpdate(strAgentFlag, strAgentID, strAgentName, strAgentTel, strAgentPw, strAgentPickup, strAgentGroup);
    }
});
//수정
$("#agentUpdateBtn").dxButton({
    text: "수정",
    type: "success",
    onClick: function(e) { 
    	strAgentFlag = "update";
    	agentRegUpdate(strAgentFlag, strAgentID, strAgentName, strAgentTel, strAgentPw, strAgentPickup, strAgentGroup);
    }
});
//삭제
$("#agentDeleteBtn").dxButton({
    text: "삭제",
    type: "danger",
    onClick: function(e) { 
    	var result = DevExpress.ui.dialog.confirm("삭제하시겠습니까?");
    	$(".dx-popup-title.dx-toolbar").css("display", "none");//popup title 숨기기(내용없음)
    	
        result.done(function (dialogResult) {
        	if (dialogResult) {
        		strAgentFlag = "delete";
            	agentRegUpdate(strAgentFlag, strAgentID, strAgentName, strAgentTel, strAgentPw, strAgentPickup, strAgentGroup);
        	}
      	
        });       	
    }
});
$("#agentUpdateBtn").hide();
$("#agentDeleteBtn").hide();

$("#agent_Grid").dxDataGrid({
	dataSource : {},
	keyExpr : "AGENT_ID",
	selection : {
		mode : "single"
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
	headerFilter : {
		visible : true
	},
	searchPanel : {
		visible : true,
		width : 400
	},	
	columns : [{
		caption : "아이디",
		dataField : "AGENT_ID"
	}, {
		caption : "이름",
		dataField : "AGENT_NAME"
	}, {
		caption : "내선번호",
		dataField : "AGENT_TEL"
	}, {
		caption : "비밀번호",
		dataField : "AGENT_PASSWD"
	}, {
		caption : "PickUp 그룹",
		dataField : "PICKUP_GRP"
	}, {
		caption : "그룹",
		dataField : "GROUP_NAME"
	}, {
		caption : "그룹코드",
		dataField : "GROUP_NO",
		visible : false
	}],
	onContentReady : function(e) {
		//e.component.selectRowsByIndexes([0]);
    	
    	if(strAgentFlag=="insert" || strAgentFlag=="delete"){
    		$("#agentID").dxTextBox({ value: "", readOnly: false });
    		$("#agentName").dxTextBox({ value: "" });
    		$("#agentTel").dxTextBox({ value: "" });
    		$("#agentPw").dxTextBox({ value: "" });
    		$("#agentPickup").dxTextBox({ value: "" });
    		$("#agentGroup").dxSelectBox({ value: "" });
    	}
	},
    onToolbarPreparing: function (e) {
		var toolbarItems = e.toolbarOptions.items;
		
        toolbarItems.push({
            widget: 'dxButton', 
            options: { 
            	text: '추가', 
            	width: 90,
            	onClick: function() {
                	var dxDataGrid = $("#agent_Grid").dxDataGrid('instance');
                	var keys = dxDataGrid.getSelectedRowKeys();
                	dxDataGrid.deselectRows(keys);
                	
                	$("#agentID").dxTextBox('instance').focus();            		
            	} 
        	},
            location: 'after'
        });
    },		
	onSelectionChanged : function(e) {
		var agentSelData = e.selectedRowsData[0];
		
		if(agentSelData){
			$("#agentID").dxTextBox({ value: agentSelData.AGENT_ID, readOnly: true });
			$("#agentName").dxTextBox({ value: agentSelData.AGENT_NAME });
			$("#agentTel").dxTextBox({ value: agentSelData.AGENT_TEL });
			$("#agentPw").dxTextBox({ value: agentSelData.AGENT_PASSWD });
			$("#agentPickup").dxTextBox({ value: agentSelData.PICKUP_GRP });
			$("#agentGroup").dxSelectBox({ value: agentSelData.GROUP_NO });
			
			$("#agentInsertBtn").hide();
			$("#agentUpdateBtn").show().css('display', '');
	    	$("#agentDeleteBtn").show().css('display', '');
		}else{
			$("#agentID").dxTextBox({ value: "", readOnly: false });
			$("#agentName").dxTextBox({ value: "" });
			$("#agentTel").dxTextBox({ value: "" });
			$("#agentPw").dxTextBox({ value: "" });
			$("#agentPickup").dxTextBox({ value: "" });
			$("#agentGroup").dxSelectBox({ value: "" });
			
			$("#agentInsertBtn").show().css('display', '');
			$("#agentUpdateBtn").hide();
	    	$("#agentDeleteBtn").hide();
		}
	}
});

var selTeamDeptCode = "";
//그룹(조회조건)
$.ajax({
	url : "/MWTS/mctm/selectGroupRegCode.do",
	type : "POST",
	data : {},
	cache : false, //IE 브라우저 캐싱방지
	async : false, // ajax 순차적으로 실행
}).done(function(data) {
	var data = data.dataList;
	data.unshift({TEAM_CODE: "전체", TEAM_NAME: "전체", DEPT_NAME: "전체"});
	
	$("#teamDeptCode").dxSelectBox({
		dataSource: data,
		value: data[0] ? data[0] : "",
	    itemTemplate: function(e) {
	        return e.TEAM_NAME+" ("+e.DEPT_NAME+")";
	    },
	    fieldTemplate: function(e) {
	        var displayText = e ? e['TEAM_NAME']+" ("+e['DEPT_NAME']+")" :'';
	        return $('<div>').dxTextBox({value: displayText});
	    },
	    onInitialized: function (e) {
	    	selTeamDeptCode = e.component._options.value;
	    	
	    	if(selTeamDeptCode){
	    		agentRegSelect(selTeamDeptCode);
	    	}
	    },
	    onValueChanged: function (e) {
	    	selTeamDeptCode = e.value.TEAM_CODE;
	    	
	    	if(selTeamDeptCode){
	    		agentRegSelect(selTeamDeptCode);
	    	}
	    }
	});
});

//그룹(데이터 바인딩)
$.ajax({
	url : "/MWTS/mctm/selectGroupRegCode.do",
	type : "POST",
	data : {},
	cache : false, //IE 브라우저 캐싱방지
	async : false, // ajax 순차적으로 실행
}).done(function(data) {
	var data = data.dataList;
	
	$("#agentGroup").dxSelectBox({
	 	dataSource: data,
	    displayExpr: "TEAM_NAME",
	    valueExpr: "TEAM_CODE",
	    value: "",
	    onValueChanged: function (e) {
	    	strAgentGroup = e.value;
	    }
	});
});

function agentRegSelect(f_Code){
	$.ajax({
		url : "/MWTS/mctm/selectAgentReg.do",
		type : "POST",
		data : {
			f_Code : f_Code
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
	}).done(function(data) {
		var data = data.dataList;
		
		$("#agent_Grid").dxDataGrid({ dataSource: data });
	});
}

function agentRegUpdate(f_AgentFlag, f_AgentID, f_AgentName, f_AgentTel, f_AgentPw, f_AgentPickup, f_AgentGroup){
	$.ajax({
		url : "/MWTS/mctm/updateAgentReg.do",
		data : {
			f_AgentFlag : f_AgentFlag,
			f_AgentID : f_AgentID,
			f_AgentName : f_AgentName,
			f_AgentTel : f_AgentTel,
			f_AgentPw : f_AgentPw,
			f_AgentPickup : f_AgentPickup,
			f_AgentGroup : f_AgentGroup
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
		success : function(result) {
			if(result.RET == "0"){
				if(selTeamDeptCode){
					agentRegSelect(selTeamDeptCode);
					DevExpress.ui.notify(result.RET_MSG, "info");
				}				
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