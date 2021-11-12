<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>멘트관리</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	
<%@include file="../../mwts/layout/topJS.jsp"%>
<style type="text/css">
/*그리드 헤더 여백*/
.dx-datagrid-header-panel {
    padding: 10px 10px 0px 0px;
}
</style>
</head>

<body class="dx-viewport">
    <div class="demo-container" style="position: absolute; height: 100%; width: 100%">
    
	<div id="select_Grid" style="position: absolute; height: calc(100% - 220px); width: 100%; top:0px; border: 2px solid #e8e8e8"></div>

	<div id="update_Grid" style="position: absolute; height: 200px; width: 100%; bottom: 0px">
 			<label style="position: absolute; width: 50px;">제목</label>
		<div id="title" style="position: absolute; left: 50px; width: calc(100% - 50px); "></div>
			<label style="position: absolute; width: 50px; top: 45px;">내용</label>
		<div id="ment" style="position: absolute; left: 50px; width: calc(100% - 50px); top: 45px "></div>		
		
		<div id="btn_Grid" style="position: absolute; width: 100%; height:40px; bottom: 0px; text-align: center; ">
	    	<div id="updateBtn" style="position: absolute; width:90px; right:100px;"></div>
	    	<div id="deleteBtn" style="position: absolute; width:90px; right:0px; "></div>	
	    	<div id="insertBtn" style="position: absolute; width:90px; right:0px; "></div>
		</div>		
	</div>		
    </div>
</body>
</html>

<script type="text/javascript">
var strFlag ="";
var strIndexNo ="";
var strTitle ="";
var strMent ="";

$("#select_Grid").dxDataGrid({
	dataSource : {},
	keyExpr : "index_no",
	selection : {
		mode : "single"
	},			
	headerFilter : {
		visible : true
	},
	searchPanel : {
		visible : true,
		width : 400
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
		dataField : "index_no",
		caption : "Index",
		visible: false
	}, {
		dataField : "tts_title",
		caption : "제목",
	}, {
		dataField : "make_flag",
		caption : "파일생성",
		width: 100
	}, {
		dataField : "tts_ment",
		caption : "내용",
		visible: false
	}],
	onContentReady : function(e) {
		//e.component.selectRowsByIndexes([ 0 ]);
		
		if(strFlag=="insert" || strFlag=="delete"){
			$("#title").dxTextBox({ value: "" });
			$("#ment").dxTextArea({ value: "" });			
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
                	var dxDataGrid = $("#select_Grid").dxDataGrid('instance');
                	var keys = dxDataGrid.getSelectedRowKeys();
                	dxDataGrid.deselectRows(keys);
            		
            		$("#title").dxTextBox('instance').focus();
            	} 
        	},
            location: 'after'
        });
    },		
	onSelectionChanged : function(selectedItems) {
			var selData = selectedItems.selectedRowsData[0];
			
			if(selData){
				strIndexNo = selData.index_no;
				$("#title").dxTextBox({ value: selData.tts_title });
				$("#ment").dxTextArea({ value: selData.tts_ment });
	        	$("#insertBtn").hide();
	        	$("#updateBtn").show().css('display', '');
	        	$("#deleteBtn").show().css('display', '');						
			}else{
				
				$("#title").dxTextBox({ value: "" });
				$("#ment").dxTextArea({ value: "" });
				$("#insertBtn").show().css('display', '');
				$("#updateBtn").hide();
		    	$("#deleteBtn").hide();						
			}	
	}
});


//멘트 조회
function mentSelect(){
	$.ajax({
		url : "/MWTS/dars/mentSelect.do",
		type : "POST",
		data : {},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
	}).done(function(data) {
		var data = data.dataList;

		$("#select_Grid").dxDataGrid({ dataSource: data });
	});	
}
mentSelect();


//제목
$("#title").dxTextBox({
	maxLength: 100,
	value: "",
	onValueChanged: function (e) {
		strTitle = e.value;
	}
})
//내용
$("#ment").dxTextArea({
	maxLength: 1000,
	height: 100,
	value: "",
	onValueChanged: function (e) {
		strMent = e.value;
	}
})

$("#insertBtn").dxButton({
    text: "저장",
    type: "default",
    onClick: function(e) {        	
    	strFlag = "insert";
    	mentUpdate(strFlag, "", strTitle, strMent);
    }
});

$("#updateBtn").dxButton({
    text: "수정",
    type: "success",
    onClick: function(e) {
    	strFlag = "update";
    	mentUpdate(strFlag, strIndexNo, strTitle, strMent);
    }
});

$("#deleteBtn").dxButton({
    text: "삭제",
    type: "danger",
    onClick: function(e) {
    	var result = DevExpress.ui.dialog.confirm("삭제하시겠습니까?");
    	$(".dx-popup-title.dx-toolbar").css("display", "none");//popup title 숨기기(내용없음)
    	
        result.done(function (dialogResult) {
        	if (dialogResult) {
            	strFlag = "delete";
            	mentUpdate(strFlag, strIndexNo, strTitle, strMent); 
        	}
      	
        });
    }
});
$("#updateBtn").hide();
$("#deleteBtn").hide();

function mentUpdate(f_Flag, f_IndexNo, f_Title, f_Ment){
	$.ajax({
		url : "/MWTS/dars/updateMent.do",
		data : {
			f_Flag : f_Flag,
			f_IndexNo : f_IndexNo,
			f_Title : f_Title,
			f_Ment : f_Ment
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
		success : function(result) {
			if(result.RET == "0"){
				mentSelect();
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