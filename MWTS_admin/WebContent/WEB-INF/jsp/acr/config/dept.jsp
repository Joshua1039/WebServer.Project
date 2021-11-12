<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>부서관리</title>
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
var branchData = [];

//회사코드 조회
$.ajax({
	url : "/MWTS/acr/selectBranchCode.do",
	type : "POST",
	data : {},
	cache : false, //IE 브라우저 캐싱방지
	async : false, // ajax 순차적으로 실행
}).done(function(data) {
	var data = data.dataList; 
	
	$.each(data, function(i, item) {
		var branchArr = {
				code : "",
				name : ""
		}
		
		branchArr.code = item.BRANCH_CODE;
		branchArr.name = item.BRANCH_NAME;
		
		branchData.push(branchArr);
	});
});

var useYN =[{ desc : "Y", val : "Y"}, { desc : "N", val : "N"}];
var strFlag ="";
var strID1 ="";
var strID2 ="";
var strID3 ="";
var strID4 ="";
var strID5 ="";
var strID6 ="";
var strID7 ="";
var strNM1 ="";
var strNM2 ="";
var strNM3 ="";
var strNM4 ="";
var strNM5 ="";
var strNM6 ="";
var strNM7 ="";
var strDeptId ="";
var strDeptName ="";
var strDeptLevel ="";
var strSeq ="";
var strUseyn ="";

var ID1Branch = "";
var ID1Lookup = "";
var NM1Lookup = "";

$("#select_Grid").dxDataGrid({
	dataSource : {},
	keyExpr: "dept_id",
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
		caption : "회사",
		dataField : "ID1_branch",
        lookup: {
            dataSource: branchData,
            displayExpr: "name",
            valueExpr: "code"
        }
	},{
		caption : "코드1",
		dataField : "ID1",
        editorOptions: {
        	maxLength: 20
        }
	},{
		caption : "코드2",
		dataField : "ID2",
        editorOptions: {
        	maxLength: 20
        }
	},{
		caption : "코드3",
		dataField : "ID3",
        editorOptions: {
        	maxLength: 20
        }
	},{
		caption : "코드4",
		dataField : "ID4",
		visible : false,
        editorOptions: {
        	maxLength: 20
        }
	},{
		caption : "코드5",
		dataField : "ID5",
		visible : false,
        editorOptions: {
        	maxLength: 20
        }
	},{
		caption : "코드6",
		dataField : "ID6",
		visible : false,
        editorOptions: {
        	maxLength: 20
        }
	},{
		caption : "코드7",
		dataField : "ID7",
		visible : false,
        editorOptions: {
        	maxLength: 20
        }
	},{
		caption : "코드명1",
		dataField : "NM1",
        editorOptions: {
        	maxLength: 100
        }
	},{
		caption : "코드명2",
		dataField : "NM2",
        editorOptions: {
        	maxLength: 100
        }
	},{
		caption : "코드명3",
		dataField : "NM3",
        editorOptions: {
        	maxLength: 100
        }
	},{
		caption : "코드명4",
		dataField : "NM4",
		visible : false,
        editorOptions: {
        	maxLength: 100
        }
	},{
		caption : "코드명5",
		dataField : "NM5",
		visible : false,
        editorOptions: {
        	maxLength: 100
        }
	},{
		caption : "코드명6",
		dataField : "NM6",
		visible : false,
        editorOptions: {
        	maxLength: 100
        }
	},{
		caption : "코드명7",
		dataField : "NM7",
		visible : false,
        editorOptions: {
        	maxLength: 100
        }
	},{
		caption : "부서코드",
		dataField : "dept_id",
        editorOptions: {
        	maxLength: 20
        },
		validationRules: [{ type: "required", message: "부서코드를 입력해주세요." }]
	},{
		caption : "부서명",
		dataField : "dept_name",
        editorOptions: {
        	maxLength: 100
        }
	},{
		caption : "부서단계",
		dataField : "dept_level",
		visible : false
	},{
		caption : "정렬순서",
		dataField : "seq",
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
		validationRules: [{ type: "stringLength", max: 10, message: "10자까지 입력 가능합니다." }]
	},{
		caption : "사용여부",
		dataField : "useyn",
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
	    e.component.columnOption("dept_id", "allowEditing", true);
	},
	onEditingStart: function(e) {
        e.component.columnOption("dept_id", "allowEditing", false);
	},			
	onEditorPrepared: function(e) {
		//사용여부
	    if (e.parentType == 'dataRow' && e.dataField == 'useyn') {
	    	if(!e.editorElement.dxSelectBox('instance').option('value')){
	    		e.editorElement.dxSelectBox('instance').option('value', useYN[0].val);
	    	}
	    }
		
		/*회사 선택 시 코드, 코드명에 데이터 바인딩*/
		//코드1
		if (e.parentType == 'dataRow' && e.dataField == 'ID1') {
			ID1Lookup = e.editorElement.dxTextBox('instance');
		}
			//코드명1
		if (e.parentType == 'dataRow' && e.dataField == 'NM1') {
			NM1Lookup = e.editorElement.dxTextBox('instance');
		}	
			
		//회사
		if (e.parentType == 'dataRow' && e.dataField == 'ID1_branch') {
			ID1Branch = e.editorElement.dxSelectBox('instance');
			ID1Branch.on("valueChanged", function() {
				
				var selCode = e.editorElement.dxSelectBox('instance').option('value');
				var selName = e.editorElement.dxSelectBox('instance').option('displayValue');

				setTimeout(function(){
						ID1Lookup.option("value", selCode);
						NM1Lookup.option("value", selName);
						ID1Lookup.option("disabled", true);
						NM1Lookup.option("disabled", true);
            	}, 100);
        	})
		}

	},		
	onRowInserting : function(e) {
		strFlag = "insert";
		strID1 =e.data.ID1;
		strID2 =e.data.ID2;
		strID3 =e.data.ID3;
		strID4 =e.data.ID4;
		strID5 =e.data.ID5;
		strID6 =e.data.ID6;
		strID7 =e.data.ID7;
		strNM1 =e.data.NM1;
		strNM2 =e.data.NM2;
		strNM3 =e.data.NM3;
		strNM4 =e.data.NM4;
		strNM5 =e.data.NM5;
		strNM6 =e.data.NM6;
		strNM7 =e.data.NM7;
		strDeptId =e.data.dept_id;
		strDeptName =e.data.dept_name;
		strDeptLevel =e.data.dept_level;
		strSeq =e.data.seq;
		strUseyn =e.data.useyn;

		deptUpdate(strFlag, strID1, strID2, strID3, strID4, strID5, strID6, strID7, strNM1, strNM2, strNM3, strNM4, strNM5, strNM6, strNM7, strDeptId, strDeptName, strDeptLevel, strSeq, strUseyn);
	},		
	onRowUpdating: function(e){
		strFlag = "update";
		strDeptId = e.key;
		
		if(e.newData.ID1 == undefined){
			strID1 = e.oldData.ID1;
		}else{
			strID1 = e.newData.ID1;
		}

		if(e.newData.ID2 == undefined){
			strID2 = e.oldData.ID2;
		}else{
			strID2 = e.newData.ID2;
		}
		
		if(e.newData.ID3 == undefined){
			strID3 = e.oldData.ID3;
		}else{
			strID3 = e.newData.ID3;
		}
		
		if(e.newData.ID4 == undefined){
			strID4 = e.oldData.ID4;
		}else{
			strID4 = e.newData.ID4;
		}
		
		if(e.newData.ID5 == undefined){
			strID5 = e.oldData.ID5;
		}else{
			strID5 = e.newData.ID5;
		}
		
		if(e.newData.ID6 == undefined){
			strID6 = e.oldData.ID6;
		}else{
			strID6 = e.newData.ID6;
		}
		
		if(e.newData.ID7 == undefined){
			strID7 = e.oldData.ID7;
		}else{
			strID7 = e.newData.ID7;
		}

		if(e.newData.NM1 == undefined){
			strNM1 = e.oldData.NM1;
		}else{
			strNM1 = e.newData.NM1;
		}
		
		if(e.newData.NM2 == undefined){
			strNM2 = e.oldData.NM2;
		}else{
			strNM2 = e.newData.NM2;
		}

		if(e.newData.NM3 == undefined){
			strNM3 = e.oldData.NM3;
		}else{
			strNM3 = e.newData.NM3;
		}

		if(e.newData.NM4 == undefined){
			strNM4 = e.oldData.NM4;
		}else{
			strNM4 = e.newData.NM4;
		}

		if(e.newData.NM5 == undefined){
			strNM5 = e.oldData.NM5;
		}else{
			strNM5 = e.newData.NM5;
		}

		if(e.newData.NM6 == undefined){
			strNM6 = e.oldData.NM6;
		}else{
			strNM6 = e.newData.NM6;
		}

		if(e.newData.NM7 == undefined){
			strNM7 = e.oldData.NM7;
		}else{
			strNM7 = e.newData.NM7;
		}

		if(e.newData.dept_name == undefined){
			strDeptName = e.oldData.dept_name;
		}else{
			strDeptName = e.newData.dept_name;
		}

		if(e.newData.dept_level == undefined){
			strDeptLevel = e.oldData.dept_level;
		}else{
			strDeptLevel = e.newData.dept_level;
		}

		if(e.newData.seq == undefined){
			strSeq = e.oldData.seq;
		}else{
			strSeq = e.newData.seq;
		}
						
		if(e.newData.useyn == undefined){
			strUseyn = e.oldData.useyn;
		}else{
			strUseyn = e.newData.useyn;
		}

		deptUpdate(strFlag, strID1, strID2, strID3, strID4, strID5, strID6, strID7, strNM1, strNM2, strNM3, strNM4, strNM5, strNM6, strNM7, strDeptId, strDeptName, strDeptLevel, strSeq, strUseyn);
	},
	onRowRemoving : function(e) {
		strFlag = "delete";
		strDeptId = e.key;
		
		deptUpdate(strFlag, "", "", "", "", "", "", "", "", "", "", "", "", "", "", strDeptId, "", "", "", "");
	}
}).dxDataGrid("instance");

//부서관리 조회
function deptSelect(){
	$.ajax({
		url : "/MWTS/acr/selectDept.do",
		type : "POST",
		data : {},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
	}).done(function(data) {
		var data = data.dataList;
		
		$("#select_Grid").dxDataGrid({ dataSource: data });
	});	
}
deptSelect();

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

function deptUpdate(f_Flag, f_ID1, f_ID2, f_ID3, f_ID4, f_ID5, f_ID6, f_ID7, f_NM1, f_NM2, f_NM3, f_NM4, f_NM5, f_NM6, f_NM7, f_DeptId, f_DeptName, f_DeptLevel, f_Seq, f_Useyn){
	$.ajax({
		url : "/MWTS/acr/updateDept.do",
		data : {
			f_Flag : f_Flag,
			f_ID1 : f_ID1,
			f_ID2 : f_ID2,
			f_ID3 : f_ID3,
			f_ID4 : f_ID4,
			f_ID5 : f_ID5,
			f_ID6 : f_ID6,
			f_ID7 : f_ID7,
			f_NM1 : f_NM1,
			f_NM2 : f_NM2,
			f_NM3 : f_NM3,
			f_NM4 : f_NM4,
			f_NM5 : f_NM5,
			f_NM6 : f_NM6,
			f_NM7 : f_NM7,
			f_DeptId : f_DeptId,
			f_DeptName : f_DeptName,
			f_DeptLevel : f_DeptLevel,
			f_Seq : f_Seq,
			f_Useyn : f_Useyn
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
		success : function(result) {
			if(result.RET == "0"){
				deptSelect();
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