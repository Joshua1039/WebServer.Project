<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>채널관리</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	
<%@include file="../../mwts/layout/topJS.jsp"%>
</head>

<body class="dx-viewport">
    <div class="demo-container" style="position: absolute; height: 100%; width: 100% ">
		<div id="select_Grid" style="position: absolute; height: 100%; width: 100% "></div>
    </div>
</body>
</html>

<script type="text/javascript">
var serverData = [];

//서버코드 조회
$.ajax({
	url : "/MWTS/acr/selectServerInfo.do",
	type : "POST",
	data : {},
	cache : false, //IE 브라우저 캐싱방지
	async : false, // ajax 순차적으로 실행
}).done(function(data) {
	var data = data.dataList; 
	
	$.each(data, function(i, item) {
		var serverArr = {
				code : "",
				name : ""
		}
		
		serverArr.code = item.SERVER_CODE;
		serverArr.name = item.SERVER_NAME;
		
		serverData.push(serverArr);
	});
});

var strFlag ="";
var strServerCode ="";
var strVpmNum ="";
var strTelNum ="";
var strExtension ="";
var strTelPw ="";
var strIpmAgentIp ="";
var strAgentId ="";

$("#select_Grid").dxDataGrid({
	dataSource : {},
	keyExpr: ["SERVER_CODE", "VPM_NUM"],
	hoverStateEnabled : true,
	columnAutoWidth : false,
	allowResizing: true,
	showBorders: true,
	errorRowEnabled: false,
    allowColumnResizing: true,
    columnResizingMode: 'widget', // or 'nextColumn'
	scrolling: {
        columnRenderingMode: "standard",
        showScrollbar: "always"
    },
	headerFilter : {
		visible : true
	},
    editing: {
        mode: "row",
        allowUpdating: true,
        allowDeleting: true,
        allowAdding: true,
        useIcons: true // version 18.1
    },
	columns : [{
		caption : "서버코드",
		dataField : "SERVER_CODE",
		alignment : 'left',
        editorOptions: {
        	maxLength: 10
        },
        lookup: {
            dataSource: serverData,
            displayExpr: "code",
            valueExpr: "code"
        },
        validationRules: [{ type: "required", message: "서버코드를 선택해주세요." }, { type: "stringLength", max: 10, message: "10자까지 입력 가능합니다." }]
	},{
		caption : "채널번호",
		dataField : "VPM_NUM",
		alignment : 'left',
        dataType: 'number',
        precision: 0,
        editorOptions: {
        	maxLength: 10,
            onKeyPress: function (e) {
                var event = e.jQueryEvent,
                    str = String.fromCharCode(event.keyCode);
                if (!/[0-9]/.test(str))
                    event.preventDefault();
            }
        },
		validationRules: [{ type: "required", message: "채널번호를 입력해주세요." }, { type: "stringLength", max: 10, message: "10자까지 입력 가능합니다." }]
	},{
		caption : "내선번호1",
		dataField : "TEL_NUM",
        editorOptions: {
        	maxLength: 20
        },
		validationRules: [{ type: "required", message: "내선번호를 입력해주세요." }]
	},{
		caption : "내선번호2",
		dataField : "EXTENSION",
        editorOptions: {
        	maxLength: 20
        },
		validationRules: [{ type: "required", message: "내선번호를 입력해주세요." }]
	},{
		caption : "비밀번호",
		dataField : "TEL_PW",
        editorOptions: {
        	maxLength: 20
        },
		validationRules: [{ type: "required", message: "비밀번호를 입력해주세요." }]
	},{
		caption : "전화기IP",
		dataField : "IPM_AGENT_IP",
        editorOptions: {
        	maxLength: 32
        },
		validationRules: [{ type: "required", message: "전화기IP를 입력해주세요." }]
	},{
		caption : "대상자ID",
		dataField : "agent_id",
        editorOptions: {
        	maxLength: 20
        },
		allowEditing : false
	}],
	onKeyDown: function (e) {
	    if (e.event.key == "Enter") {
	    	//IE에서 엔터키 입력 시 포커스 유지
	        $("#select_Grid").dxDataGrid("instance").focus($(".dx-focused"));
	    }
	}, 			
	onInitNewRow: function(e) {
	    e.component.columnOption("SERVER_CODE", "allowEditing", true);
	    e.component.columnOption("VPM_NUM", "allowEditing", true);
	},
	onEditingStart: function(e) {
        e.component.columnOption("SERVER_CODE", "allowEditing", false);
        e.component.columnOption("VPM_NUM", "allowEditing", false);
	},				
	onRowInserting : function(e) {
		strFlag = "insert";
		strServerCode =e.data.SERVER_CODE;
		strVpmNum =e.data.VPM_NUM;
		strTelNum =e.data.TEL_NUM;
		strExtension =e.data.EXTENSION;
		strTelPw =e.data.TEL_PW;
		strIpmAgentIp =e.data.IPM_AGENT_IP;
		strAgentId =e.data.agent_id;

		vpmInfoUpdate(strFlag, strServerCode, strVpmNum, strTelNum, strExtension, strTelPw, strIpmAgentIp, strAgentId);
	},		
	onRowUpdating: function(e){
		strFlag = "update";
		strServerCode = e.key.SERVER_CODE;
		strVpmNum = e.key.VPM_NUM;
		
		if(e.newData.TEL_NUM == undefined){
			strTelNum = e.oldData.TEL_NUM;
		}else{
			strTelNum = e.newData.TEL_NUM;
		}
		
		if(e.newData.EXTENSION == undefined){
			strExtension = e.oldData.EXTENSION;
		}else{
			strExtension = e.newData.EXTENSION;
		}

		if(e.newData.TEL_PW == undefined){
			strTelPw = e.oldData.TEL_PW;
		}else{
			strTelPw = e.newData.TEL_PW;
		}		
		
		if(e.newData.IPM_AGENT_IP == undefined){
			strIpmAgentIp = e.oldData.IPM_AGENT_IP;
		}else{
			strIpmAgentIp = e.newData.IPM_AGENT_IP;
		}
		
		if(e.newData.agent_id == undefined){
			strAgentId = e.oldData.agent_id;
		}else{
			strAgentId = e.newData.agent_id;
		}
		
		vpmInfoUpdate(strFlag, strServerCode, strVpmNum, strTelNum, strExtension, strTelPw, strIpmAgentIp, strAgentId);
	},
	onRowRemoving : function(e) {
		strFlag = "delete";
		strServerCode = e.key.SERVER_CODE;
		strVpmNum = e.key.VPM_NUM;
		
		vpmInfoUpdate(strFlag, strServerCode, strVpmNum, "", "", "", "", "");
	}	
}).dxDataGrid("instance");	

//채널관리 조회
function vpmInfoSelect(){
	$.ajax({
		url : "/MWTS/acr/selectVpmInfo.do",
		type : "POST",
		data : {},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
	}).done(function(data) {
		var data = data.dataList;
		
		$("#select_Grid").dxDataGrid({ dataSource: data });
	});	
}
vpmInfoSelect();

$(document).on('focus', '#select_Grid input[type=text]', function() {
	// IE에서 커서 위치 지정
	 if (this.createTextRange) { 
		 var range = this.createTextRange(); 
		 range.move('character', this.value.length); // input box의 글자 수 만큼 커서를 뒤로 옮김
		 range.select(); 
		 } 
	 else if (this.selectionStart || this.selectionStart== '0') 
		 this.selectionStart = this.value.length;
});

function vpmInfoUpdate(f_Flag, f_ServerCode, f_VpmNum, f_TelNum, f_Extension, f_TelPw, f_IpmAgentIp, f_AgentId){
	$.ajax({
		url : "/MWTS/acr/updateVpmInfo.do",
		data : {
			f_Flag : f_Flag,
			f_ServerCode : f_ServerCode,
			f_VpmNum : f_VpmNum,
			f_TelNum : f_TelNum,
			f_Extension : f_Extension,
			f_TelPw : f_TelPw,
			f_IpmAgentIp : f_IpmAgentIp,
			f_AgentId : f_AgentId
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
		success : function(result) {
			if(result.RET == "0"){
				vpmInfoSelect();
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