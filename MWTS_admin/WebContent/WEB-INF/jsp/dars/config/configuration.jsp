<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>환경설정</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	
<%@include file="../../mwts/layout/topJS.jsp"%>
</head>

<body class="dx-viewport">
    <div class="demo-container" style="position: absolute; height: 100%; width: 100% ">
		<div id="head_Grid" style="position: absolute; height: 50px; width: 100% ">
			<label style="position: absolute; top: 8px; width: 70px; text-align: center ">조회선택</label>
			<div id="tableBox" style="position: absolute; display: inline-block; width: 300px; left: 80px"></div>
		</div>
		
		<div id="select_Grid" style="position: absolute; top: 60px; height: calc(100% - 70px); width: 100%; overflow-x:hidden;">
		</div>
    </div>
</body>
</html>

<script type="text/javascript">
$("#select_Grid").dxDataGrid({
	dataSource : {},
	keyExpr : "index_no",
	hoverStateEnabled : true,
	columnAutoWidth : false,
	allowResizing: true,
    allowColumnResizing: true,
    columnResizingMode: 'widget', // or 'nextColumn'
	scrolling: {
        columnRenderingMode: "standard",
        showScrollbar: "always"
    },
	showBorders: true,
	headerFilter : {
		visible : true
	},
    editing: {
        mode: "row",
        allowUpdating: true,
        useIcons: true // version 18.1
    }, 
	columns : [{
		dataField : "index_no",
		caption : "IDX",
		alignment : "left",
		width : 150,
		allowEditing : false
	},{
		dataField : "data",
		caption : "데이터",
		width : 150,
        editorOptions: {
        	maxLength: 30
        }
	},{
		dataField : "description",
		caption : "설명",
        editorOptions: {
        	maxLength: 50
        }
	}],
	onKeyDown: function (e) {
	    if (e.event.key == "Enter") {
	    	//IE에서 엔터키 입력 시 포커스 유지
	        $("#select_Grid").dxDataGrid("instance").focus($(".dx-focused"));
	    }
	}, 
	onRowUpdating: function(e) {
    	var strIndex = e.key;
    	var strdata = "";
    	var strdescription = "";

		if(e.newData.data == undefined){
			strdata = e.oldData.data;
		}else{
			strdata = e.newData.data;
		}

		if(e.newData.description == undefined){
			strdescription = e.oldData.description;
		}else{
			strdescription = e.newData.description;
		}
		
		parameterUpdate(strIndex, strdata, strdescription);
    }
});	

var selTable = "";
//환경설정 테이블 조회
$.ajax({
	url : "/MWTS/dars/selectParamTable.do",
	type : "POST",
	data : {},
	cache : false, //IE 브라우저 캐싱방지
	async : false, // ajax 순차적으로 실행
}).done(function(data) {
	var tableData = data.dataList;
	
	$("#tableBox").dxSelectBox({
		dataSource: tableData,
	    displayExpr: "t_name",
	    valueExpr: "t_value",
	    //value: tableData[0].t_value,
	    value: tableData[0] ? tableData[0].t_value : "",
	    onInitialized: function (e) {
	    	selTable = e.component._options.value;
	    	
	    	if(selTable){
	    		parameterSelect(selTable);
	    	}
	    },
	    onItemClick: function (e) {
	    	selTable = $("#tableBox").dxSelectBox('instance').option('value');
	    	
	    	if(selTable){
	    		parameterSelect(selTable);
	    	}
	    }
	});	
});

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

function parameterSelect(f_table){
	$.ajax({
		url : "/MWTS/dars/parameterSelect.do",
		type : "POST",
		data : {
			f_table : f_table
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
	}).done(function(data) {
		var data = data.dataList;
		console.log(data);
		$("#select_Grid").dxDataGrid({ dataSource: data });
	});
}

function parameterUpdate(f_Index, f_data, f_description){
	$.ajax({
		url : "/MWTS/dars/parameterUpdate.do",
		data : {
			f_Table : selTable,
			f_Index : f_Index,
			f_data : f_data,
			f_description : f_description
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
		success : function(result) {
			if(result.RET == "0"){
				if(selTable){
					parameterSelect(selTable);
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