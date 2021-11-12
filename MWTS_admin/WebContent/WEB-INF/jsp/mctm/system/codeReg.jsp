<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>코드 관리</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<%@include file="../../mwts/layout/topJS.jsp"%>
</head>

<body class="dx-viewport">
    <div class="demo-container" style="position: absolute; height: 100%; width: 100% ">
		<div id="gridContainer" style="position: absolute; height: 100%; width: 100% "></div>
    </div>
</body>
</html>

<script type="text/javascript">
var strFlag ="";
var strCategory ="";
var strCode ="";
var strName ="";

$("#gridContainer").dxDataGrid({
	dataSource : {},
	keyExpr : ["category", "code"],
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
		caption : "카테고리",
		dataField : "category",
		validationRules: [{ type: "required", message: "카테고리를 입력해주세요." }]
	}, {
		caption : "코드",
		dataField : "code",
		validationRules: [{ type: "required", message: "카테고리를 입력해주세요." }]
	}, {
		caption : "이름",
		dataField : "name"
	}],
	onKeyDown: function (e) {
	    if (e.event.key == "Enter") {
	    	//IE에서 엔터키 입력 시 포커스 유지
	        $("#gridContainer").dxDataGrid("instance").focus($(".dx-focused"));
	    }
	}, 	
	onInitNewRow: function(e) {
	    e.component.columnOption("category", "allowEditing", true);
	    e.component.columnOption("code", "allowEditing", true);
	},
	onEditingStart: function(e) {
        e.component.columnOption("category", "allowEditing", false);
        e.component.columnOption("code", "allowEditing", false);
	},		
    onEditorPreparing: function(e) {
         if (e.parentType == 'dataRow' && e.dataField == 'category') {
            e.editorOptions.maxLength = 2;
        }
         if (e.parentType == 'dataRow' && e.dataField == 'code') {
            e.editorOptions.maxLength = 4;
        }
        if (e.parentType == 'dataRow' && e.dataField == 'name') {
            e.editorOptions.maxLength = 100;
        }
    },		
    onRowInserting : function(e) {
		strFlag = "insert";
		strCategory = e.data.category;
		strCode = e.data.code;
		strName = e.data.name;
		
		codeMstUpdate(strFlag, strCategory, strCode, strName);
	},
	onRowUpdating : function(e) {
		strFlag = "update";
		strCategory = e.key.category;
		strCode = e.key.code;
		
		if(e.newData.name == undefined){
			strName = e.oldData.name;
		}else{
			strName = e.newData.name;
		}
		
		codeMstUpdate(strFlag, strCategory, strCode, strName);
	},
	onRowRemoving : function(e) {
		strFlag = "delete";
		strCategory = e.key.category;
		strCode = e.key.code;
		
		codeMstUpdate(strFlag, strCategory, strCode);
	}
}).dxDataGrid("instance");

//코드마스터 조회
function selectCodeMaster(){
	$.ajax({
		url : "/MWTS/mctm/selectCodeReg.do",
		type : "POST",
		data : {},
		cache : false,
		async : false,
	}).done(function(data) {
		var data = data.dataList;

		$("#gridContainer").dxDataGrid({ dataSource: data });
	});	
}
selectCodeMaster();

$(document).on('focus', '#gridContainer input[type=text]', function() {
	// IE에서 커서 위치 지정
	 if (this.createTextRange) { 
		 var range = this.createTextRange(); 
		 range.move('character', this.value.length); // input box의 글자 수 만큼 커서를 뒤로 옮김
		 range.select(); 
		 } 
	 else if (this.selectionStart || this.selectionStart== '0') 
		 this.selectionStart = this.value.length;
});
	
function codeMstUpdate(f_Flag, f_Category, f_Code, f_Name){
	$.ajax({
		url : "/MWTS/mctm/updateCodeReg.do",
		data : {
			f_Flag : f_Flag,
			f_Category : f_Category,
			f_Code : f_Code,
			f_Name : f_Name
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
		success : function(result) {
			if(result.RET == "0"){
				selectCodeMaster();
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