<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>회사관리</title>
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
var strBranchCode ="";
var strBranchName ="";
var strUseYN ="";

$("#select_Grid").dxDataGrid({
	dataSource : {},
	keyExpr: "BRANCH_CODE",
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
		caption : "회사 코드",
		dataField : "BRANCH_CODE",
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
		validationRules: [{ type: "required", message: "회사코드를 입력해주세요." }, { type: "stringLength", max: 10, message: "10자까지 입력 가능합니다." }]
	},{
		caption : "회사명",
		dataField : "BRANCH_NAME",
        editorOptions: {
        	maxLength: 12
        },
		validationRules: [{ type: "required", message: "회사명을 입력해주세요." }]
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
	    e.component.columnOption("BRANCH_CODE", "allowEditing", true);
	},
	onEditingStart: function(e) {
        e.component.columnOption("BRANCH_CODE", "allowEditing", false);
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
		strBranchCode = e.data.BRANCH_CODE;
		strBranchName = e.data.BRANCH_NAME;
		strUseYN = e.data.USE_YN;
		
		branchCodeUpdate(strFlag, strBranchCode, strBranchName, strUseYN);
	},		
	onRowUpdating: function(e){
		strFlag = "update";
		strBranchCode = e.key;
		
		if(e.newData.BRANCH_NAME == undefined){
			strBranchName = e.oldData.BRANCH_NAME;
		}else{
			strBranchName = e.newData.BRANCH_NAME;
		}
		
		if(e.newData.USE_YN == undefined){
			strUseYN = e.oldData.USE_YN;
		}else{
			strUseYN = e.newData.USE_YN;
		}

		branchCodeUpdate(strFlag, strBranchCode, strBranchName, strUseYN);
	},
	onRowRemoving : function(e) {
		strFlag = "delete";
		strBranchCode = e.key;
		
		branchCodeUpdate(strFlag, strBranchCode);
	}
}).dxDataGrid("instance");	

//회사관리 조회
function branchCodeSelect(){
	$.ajax({
		url : "/MWTS/acr/selectBranchCode.do",
		type : "POST",
		data : {},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
	}).done(function(data) {
		var data = data.dataList;
		
		$("#select_Grid").dxDataGrid({ dataSource: data });
	});	
}
branchCodeSelect();

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

function branchCodeUpdate(f_Flag, f_BranchCode, f_BranchName, f_UseYN){
	$.ajax({
		url : "/MWTS/acr/updateBranchCode.do",
		data : {
			f_Flag : f_Flag,
			f_BranchCode : f_BranchCode,
			f_BranchName : f_BranchName,
			f_UseYN : f_UseYN
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
		success : function(result) {
			if(result.RET == "0"){
				branchCodeSelect();
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