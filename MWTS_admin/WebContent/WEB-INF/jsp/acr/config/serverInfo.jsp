<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>서버관리</title>
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
var useYN =[{ desc : "Y", val : "Y"}, { desc : "N", val : "N"}];
var strFlag ="";
var strServerCode ="";
var strServerName ="";
var strServerIp ="";
var strFTPId ="";
var strFTPPw ="";
var strFTPPort ="";
var strUseYN ="";

$("#select_Grid").dxDataGrid({
	dataSource : {},
	keyExpr: "SERVER_CODE",
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
		caption : "서버 코드",
		dataField : "SERVER_CODE",
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
		validationRules: [{ type: "required", message: "서버코드를 입력해주세요." }, { type: "stringLength", max: 10, message: "10자까지 입력 가능합니다." }]
	},{
		caption : "서버명",
		dataField : "SERVER_NAME",
        editorOptions: {
        	maxLength: 20
        },
		validationRules: [{ type: "required", message: "서버명을 입력해주세요." }]
	},{
		caption : "서버 IP",
		dataField : "SERVER_IP",
        editorOptions: {
        	maxLength: 15
        },
		validationRules: [{ type: "required", message: "서버IP를 입력해주세요." }]
	},{
		caption : "FTP 아이디",
		dataField : "FTP_ID",
        editorOptions: {
        	maxLength: 50
        }	
	},{
		caption : "FTP 패스워드",
		dataField : "FTP_PW",
        editorOptions: {
        	maxLength: 50
        }			
	},{
		caption : "FTP 포트",
		dataField : "FTP_PORT",
        editorOptions: {
        	maxLength: 5
        }			
	},{
		caption : "사용여부",
		dataField : "USE_YN",
        editorOptions: {
        	maxLength: 1
        },		
        lookup: {
            dataSource: useYN,
            displayExpr: "desc",
            valueExpr: "val"
        }
	}],
	onKeyDown: function (e) {
	    if (e.event.key == "Enter") {
	    	//IE에서 엔터키 입력 시 포커스 유지
	        $("#select_Grid").dxDataGrid("instance").focus($(".dx-focused"));
	    }
	}, 			
	onInitNewRow: function(e) {
	    e.component.columnOption("SERVER_CODE", "allowEditing", true);
	},
	onEditingStart: function(e) {
        e.component.columnOption("SERVER_CODE", "allowEditing", false);
	},
	onEditorPrepared: function(e) {
	    if (e.parentType == 'dataRow' && e.dataField == 'USE_YN') {
	    	if(!e.editorElement.dxSelectBox('instance').option('value')){
	    		e.editorElement.dxSelectBox('instance').option('value', useYN[0].val);
	    	}
	    }
	},	
	onRowInserting : function(e) {
		strFlag = "insert";
		strServerCode = e.data.SERVER_CODE;
		strServerName = e.data.SERVER_NAME;
		strServerIp = e.data.SERVER_IP;
		strFTPId = e.data.FTP_ID;
		strFTPPw = e.data.FTP_PW;
		strFTPPort = e.data.FTP_PORT;
		strUseYN = e.data.USE_YN;
		
		serverInfoUpdate(strFlag, strServerCode, strServerName, strServerIp, strFTPId, strFTPPw, strFTPPort, strUseYN);
	},		
	onRowUpdating: function(e){
		strFlag = "update";
		strServerCode = e.key;
		
		if(e.newData.SERVER_NAME == undefined){
			strServerName = e.oldData.SERVER_NAME;
		}else{
			strServerName = e.newData.SERVER_NAME;
		}
		
		if(e.newData.SERVER_IP == undefined){
			strServerIp = e.oldData.SERVER_IP;
		}else{
			strServerIp = e.newData.SERVER_IP;
		}		
		
		if(e.newData.FTP_ID == undefined){
			strFTPId = e.oldData.FTP_ID;
		}else{
			strFTPId = e.newData.FTP_ID;
		}
		
		if(e.newData.FTP_PW == undefined){
			strFTPPw = e.oldData.FTP_PW;
		}else{
			strFTPPw = e.newData.FTP_PW;
		}	
		
		if(e.newData.FTP_PORT == undefined){
			strFTPPort = e.oldData.FTP_PORT;
		}else{
			strFTPPort = e.newData.FTP_PORT;
		}
		
		if(e.newData.USE_YN == undefined){
			strUseYN = e.oldData.USE_YN;
		}else{
			strUseYN = e.newData.USE_YN;
		}

		serverInfoUpdate(strFlag, strServerCode, strServerName, strServerIp, strFTPId, strFTPPw, strFTPPort, strUseYN);
	},
	onRowRemoving : function(e) {
		strFlag = "delete";
		strServerCode = e.key;
		
		serverInfoUpdate(strFlag, strServerCode);
	}	
}).dxDataGrid("instance");	

//서버관리 조회
function serverInfoSelect(){
	$.ajax({
		url : "/MWTS/acr/selectServerInfo.do",
		type : "POST",
		data : {},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
	}).done(function(data) {
		var data = data.dataList;
		
		$("#select_Grid").dxDataGrid({ dataSource: data });
	});	
}
serverInfoSelect();

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

function serverInfoUpdate(f_Flag, f_ServerCode, f_ServerName, f_ServerIp, f_FTPId, f_FTPPw, f_FTPPort, f_UseYN){
	$.ajax({
		url : "/MWTS/acr/updateServerInfo.do",
		data : {
			f_Flag : f_Flag,
			f_ServerCode : f_ServerCode,
			f_ServerName : f_ServerName,
			f_ServerIp : f_ServerIp,
			f_FTPId : f_FTPId,
			f_FTPPw : f_FTPPw,
			f_FTPPort : f_FTPPort,
			f_UseYN : f_UseYN
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
		success : function(result) {
			if(result.RET == "0"){
				serverInfoSelect();
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