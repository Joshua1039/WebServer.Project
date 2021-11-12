<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>메뉴권한</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<%@include file="../../mwts/layout/topJS.jsp"%>
<style type="text/css">
/*그리드 헤더 여백*/
#user_Grid > .dx-datagrid > .dx-datagrid-header-panel {
    padding: 10px 10px 0px 0px;
}
</style>
</head>

<body class="dx-viewport">
	<div class="demo-container" style="position: absolute; height: 100%; width: 100% ">
		<div id="user_Grid" style="position: absolute; height: 100%; width: 400px; border: 3px solid #f5f5f5; "></div>
		<div id="list_Grid" style="position: absolute; height: 100%; width: calc(100% - 480px); left:450px; "></div>
		<input type="hidden" id="selectData" name="selectData"/>
	</div>
</body>
</html>

<script type="text/javascript">
var userId ="";
//사용자 조회
$("#user_Grid").dxDataGrid({
    dataSource: {
        store: new DevExpress.data.CustomStore({
        	load: function (loadOptions) {
        		var d = $.Deferred();
        		
	  	       	  $.getJSON('/MWTS/mwts/userSelect.do', {
	  	   		    format: "json",
	  	 		  }).done(function(data) {
	  	 				d.resolve(data.userList);
	  	 		  })
	  	 		  
	  	 		return d.promise();
  	   		}
        }) 
    },
	hoverStateEnabled : true,
	columnAutoWidth : false,
    allowColumnResizing: true,
    columnResizingMode: 'widget', // or 'nextColumn'
	scrolling: {
        columnRenderingMode: "standard",
        showScrollbar: "always"
    },
    loadPanel: {
        enabled: false
    },
	headerFilter : {
		visible : true
	},		
	searchPanel : {
		visible : true,
		width : 375
	},
	keyExpr : "user_id",
	selection : {
		mode : "single"
	},
	columns : [ {
		dataField : "user_id",
		caption : "아이디"
	}, {
		dataField : "user_name",
		caption : "이름"
	} ],
	onContentReady : function(e) {
		// Selects the first visible row
		e.component.selectRowsByIndexes([0]);
	},
	onSelectionChanged : function(e) {
		var selData = e.selectedRowsData[0];
		
		if(selData){
			userId = selData.user_id;
			if(userId){
				$('#list_Grid').dxDataGrid('instance').getDataSource().reload();
			}
		}
	}
});

$("#list_Grid").dxDataGrid({
    dataSource: {
        store: new DevExpress.data.CustomStore({
        	load: function (loadOptions) {
        		var d = $.Deferred();
        		
	  	       	  $.getJSON('/MWTS/mwts/selectMenuAuth.do', {
	  	   		    format: "json",
					f_userId : userId
	  	 		  }).done(function(data) {
	  	 				d.resolve(data.dataList);
	  	 		  })
	  	 		  
	  	 		return d.promise();
  	   		}
        }) 
    },
	hoverStateEnabled : true,
	columnAutoWidth : false,
    allowColumnResizing: true,
    columnResizingMode: 'widget', // or 'nextColumn'
	scrolling: {
        columnRenderingMode: "standard",
        showScrollbar: "always"
    },
    paging : {
    	enabled: false
    },
    loadPanel: {
        enabled: true
    },    
	headerFilter : {
		visible : true
	},
	searchPanel : {
		visible : true,
		width : 400
	},
    groupPanel: {
        visible: true
    },	
	grouping : {
		contextMenuEnabled : true
	},
	keyExpr : "Task_ID",
	selection : {
		mode : 'multiple'
	},
	columns : [ {
		caption : "대분류",
		dataField : "L_Title"
		//groupIndex : 0
	}, {
		caption : "중분류",
		dataField : "M_Title"
		//groupIndex : 1
	}, {
		caption : "소분류",
		dataField : "S_Title"
	}, {
		caption : "메뉴경로",
		dataField : "url"
	} ],
	onContentReady : function(e) {
		var grid = $("#list_Grid").dxDataGrid('instance');
		var rows = grid.getVisibleRows();

		var rowIndex = [];

		for (var i = 0; i < rows.length; i++) {
			var row = rows[i];

			if (row.rowType == "data") {
				if (row.data["user_id"] == userId) {
					var newLength = rowIndex.push(row.rowIndex);
				}
			}

		}
		//로드 시 사용중인 메뉴 체크
		grid.selectRowsByIndexes(rowIndex);
	},
    onToolbarPreparing: function (e) {
		var toolbarItems = e.toolbarOptions.items;
		
        toolbarItems.push({
            widget: 'dxButton', 
            options: { 
            	text: '저장', 
            	onClick: function() {
            		if(userId){
            			menuUpdate(userId);
            		}
            	} 
        	},
            location: 'after'
        });
    },	
	onSelectionChanged : function(selectedItems) {
		var selectRow = selectedItems.selectedRowsData;
		if (selectRow.length > 0)

			$("#selectData").val($.map(selectRow, function(value) {
				//return value.L_Title + "/ " + value.M_Title + "/ " + value.S_Title;
				return value.Task_ID;
			}).join("^"));
		else
			$("#selectData").val("");

	}
}).dxDataGrid("instance");
	  
function menuUpdate(f_userId) {
	$.ajax({
		url : "/MWTS/mwts/updateMenuAuth.do",
		data : {
			idx : $("#selectData").val(),
			f_userId : f_userId
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
		success : function(result) {
			if (result.RET == "0") {
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