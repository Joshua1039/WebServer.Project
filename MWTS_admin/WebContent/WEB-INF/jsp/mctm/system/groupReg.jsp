<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>부서 팀 관리</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	
<%@include file="../../mwts/layout/topJS.jsp"%>
</head>

<body class="dx-viewport">
    <div class="demo-container" style="position: absolute; height: 100%; width: 100% ">
        
		<!-- 부서관리 -->
		<div id="department_Container" style="position: absolute; height: 100%; width: 500px;">
		<div id="deptAddBtn" style="position: absolute; width:70px; right:0px; "></div>
			<h4 style="font-size: 16px; font-weight: bold; color: #333; margin-top: 5px; height: 20px; ">부서 관리</h4><hr style="border: 1.5px solid #e8e8e8; margin-top: 17px;"/>
			
			<label style="position: absolute; top: 58px; width: 70px; text-align: left; ">부서코드</label>
			<div id="deptCode" style="position: absolute; left: 70px; width: 200px "></div>
			<label style="position: absolute; top: 98px; width: 70px; text-align: left; ">부서명</label>
			<div id="deptName" style="position: absolute; left: 70px; width: 200px; top: 95px "></div>

			<div id="deptUpdateBtn" style="position: absolute; width:70px; right:80px; "></div>
			<div id="deptDeleteBtn" style="position: absolute; width:70px; right:0px; "></div>
			<div id="deptInsertBtn" style="position: absolute; width:70px; right:0px; "></div>
			
			<div id="department_Grid" style="position: absolute; height: calc(100% - 195px); width: 100%; top: 185px; border: 2px solid #e8e8e8; "></div>
		</div>		

		<!-- 팀관리 -->
		<div id="team_Container" style="position: absolute; height:100%; width: calc(100% - 600px); left:550px; ">
		<div id="teamAddBtn" style="position: absolute; width:70px; right:0px; "></div>
			<h4 style="font-size: 16px; font-weight: bold; color: #333; margin-top: 5px; height: 20px; ">팀 관리</h4><hr style="border: 1.5px solid #e8e8e8; margin-top: 17px;"/>
			
			<label style="position: absolute; top: 58px; width: 80px; text-align: left; ">팀코드</label>
			<div id="teamCode" style="position: absolute; left: 90px; width: 200px "></div>
			<label style="position: absolute; top: 100px; width: 80px; text-align: left; ">팀명</label>
			<div id="teamName" style="position: absolute; left: 90px; width: 200px; top: 95px "></div>
			<label style="position: absolute; top: 143px; width: 80px; text-align: left; ">후처리 시간</label>
			<div id="workTime" style="position: absolute; left: 90px; width: 200px; top: 138px "></div>
			
			<div id="teamUpdateBtn" style="position: absolute; width:70px; right:80px; "></div>
			<div id="teamDeleteBtn" style="position: absolute; width:70px; right:0px; "></div>
			<div id="teamInsertBtn" style="position: absolute; width:70px; right:0px; "></div>
			
			<div id="team_Grid" style="position: absolute; height: calc(100% - 195px); width: 100%; top: 185px; border: 2px solid #e8e8e8; "></div>
		</div>		
    </div>
</body>
</html>

<script type="text/javascript">

/****************************************
*****************부서관리******************
*****************************************/
var strdeptFlag ="";
var strdeptCode ="";
var strdeptName ="";
var deptSelData = "";

//부서코드
$("#deptCode").dxTextBox({
	maxLength: 4,
	readOnly: false,
	value: "",
	onValueChanged: function (e) {
		strdeptCode = e.value;
	}
})
//부서명
$("#deptName").dxTextBox({
	maxLength: 20,
	value: "",
	onValueChanged: function (e) {
		strdeptName = e.value;
	}
})
//추가
$("#deptAddBtn").dxButton({
    text: "추가",
    type: "normal",
    onClick: function(e) { 
    	var dxDataGrid = $("#department_Grid").dxDataGrid('instance');
    	var keys = dxDataGrid.getSelectedRowKeys();
    	dxDataGrid.deselectRows(keys);
		
		$("#deptCode").dxTextBox('instance').focus();
    }
});
//저장
$("#deptInsertBtn").dxButton({
    text: "저장",
    type: "default",
    onClick: function(e) { 
    	strdeptFlag = "insert";
    	deptUpdate(strdeptFlag, strdeptCode, strdeptName);
    }
});
//수정
$("#deptUpdateBtn").dxButton({
    text: "수정",
    type: "success",
    onClick: function(e) { 
    	strdeptFlag = "update";
    	deptUpdate(strdeptFlag, strdeptCode, strdeptName);
    }
});
//삭제
$("#deptDeleteBtn").dxButton({
    text: "삭제",
    type: "danger",
    onClick: function(e) { 
    	var result = DevExpress.ui.dialog.confirm("삭제하시겠습니까?");
    	$(".dx-popup-title.dx-toolbar").css("display", "none");//popup title 숨기기(내용없음)
    	
        result.done(function (dialogResult) {
        	if (dialogResult) {
            	strdeptFlag = "delete";
            	deptUpdate(strdeptFlag, strdeptCode, strdeptName);   		
        	}
      	
        });    	
    }
});
$("#deptUpdateBtn").hide();
$("#deptDeleteBtn").hide();

$("#department_Grid").dxDataGrid({
	dataSource : {},
	keyExpr : "GROUP_NO",
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
	columns : [{
		caption : "부서코드",
		dataField : "GROUP_NO"
	}, {
		caption : "부서명",
		dataField : "GROUP_NAME"
	}],
	onContentReady : function(e) {
		//e.component.selectRowsByIndexes([0]);		
		
		if(strdeptFlag=="insert" || strdeptFlag=="delete"){
	    	$("#deptCode").dxTextBox({ value: "" , readOnly: false});
			$("#deptName").dxTextBox({ value: "" });			
		}
	},
	onSelectionChanged : function(e) {
		deptSelData = e.selectedRowsData[0];
		
		if(deptSelData){
			$("#deptCode").dxTextBox({ value: deptSelData.GROUP_NO, readOnly: true });
			$("#deptName").dxTextBox({ value: deptSelData.GROUP_NAME });	
	    	
			$("#deptInsertBtn").hide();
			$("#deptUpdateBtn").show().css('display', '');
	    	$("#deptDeleteBtn").show().css('display', '');
	    	
	    	//팀 조회
	    	teamSelect(deptSelData.GROUP_NO);
		}else{
			$("#deptCode").dxTextBox({ value: "", readOnly: false });
			$("#deptName").dxTextBox({ value: "" });	
			
			$("#deptInsertBtn").show().css('display', '');
			$("#deptUpdateBtn").hide();
	    	$("#deptDeleteBtn").hide();			
		}
	}
});

//부서 조회
function deptSelect(){
	$.ajax({
		url : "/MWTS/mctm/selectgroupRegDept.do",
		type : "POST",
		data : {},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
	}).done(function(data) {
		var data = data.dataList;
		
		$("#department_Grid").dxDataGrid({ dataSource: data });	
	});	
}
deptSelect();

//부서 업데이트
function deptUpdate(f_DeptFlag, f_DeptCode, f_DeptName){
	$.ajax({
		url : "/MWTS/mctm/updategroupRegDept.do",
		data : {
			f_DeptFlag : f_DeptFlag,
			f_DeptCode : f_DeptCode,
			f_DeptName : f_DeptName
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
		success : function(result) {
			if(result.RET == "0"){
				deptSelect();
				DevExpress.ui.notify(result.RET_MSG, "info");
				
				if(f_DeptFlag == "delete"){
					//부서가 삭제될 경우 팀 재조회
			    	$("#teamCode").dxTextBox({ value: "" , readOnly: false});
					$("#teamName").dxTextBox({ value: "" });
					$("#workTime").dxTextBox({ value: "" });
					
					teamSelect("");
				}				
			}else{
				DevExpress.ui.notify(result.RET_MSG, "warning");
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			DevExpress.ui.notify("오류 발생 status[" + textStatus
					+ "]\nmessage[" + errorThrown + "]", "error");
		}
	});	
}



/****************************************
******************팀관리*******************
*****************************************/
var strteamFlag ="";
var strteamCode ="";
var strteamName ="";
var strworkTime ="";

//부서코드
$("#teamCode").dxTextBox({
	maxLength: 4,
	readOnly: false,
	value: "",
	onValueChanged: function (e) {
		strteamCode = e.value;
	}
})
//부서명
$("#teamName").dxTextBox({
	maxLength: 20,
	value: "",
	onValueChanged: function (e) {
		strteamName = e.value;
	}
})
//후처리시간
$("#workTime").dxTextBox({
	maxLength: 20,
	value: "",
	onValueChanged: function (e) {
		strworkTime = e.value;
	}
})
//추가
$("#teamAddBtn").dxButton({
    text: "추가",
    type: "normal",
    onClick: function(e) { 
    	var dxDataGrid = $("#team_Grid").dxDataGrid('instance');
    	var keys = dxDataGrid.getSelectedRowKeys();
    	dxDataGrid.deselectRows(keys);
    	
		$("#teamCode").dxTextBox('instance').focus();
    }
});
//저장
$("#teamInsertBtn").dxButton({
    text: "저장",
    type: "default",
    onClick: function(e) { 
    	strteamFlag = "insert";
    	teamUpdate(strteamFlag, strteamCode, strteamName, strworkTime);
    }
});
//수정
$("#teamUpdateBtn").dxButton({
    text: "수정",
    type: "success",
    onClick: function(e) { 
    	strteamFlag = "update";
    	teamUpdate(strteamFlag, strteamCode, strteamName, strworkTime);
    }
});
//삭제
$("#teamDeleteBtn").dxButton({
    text: "삭제",
    type: "danger",
    onClick: function(e) { 
    	var result = DevExpress.ui.dialog.confirm("삭제하시겠습니까?");
    	$(".dx-popup-title.dx-toolbar").css("display", "none");//popup title 숨기기(내용없음)
    	
        result.done(function (dialogResult) {
        	if (dialogResult) {
            	strteamFlag = "delete";
            	teamUpdate(strteamFlag, strteamCode, strteamName, strworkTime);
        	}
      	
        });       	
    }
});
$("#teamUpdateBtn").hide();
$("#teamDeleteBtn").hide();

$("#team_Grid").dxDataGrid({
	dataSource : {},
	keyExpr : "GROUP_NO",
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
	columns : [{
		caption : "팀코드",
		dataField : "GROUP_NO"
	}, {
		caption : "팀명",
		dataField : "GROUP_NAME"
	}, {
		caption : "후처리 시간",
		dataField : "GROUP_WORKTIME"
	}, {
		caption : "부서코드",
		dataField : "PGROUP_NO"
	}],
	onContentReady : function(e) {
		//e.component.selectRowsByIndexes([0]);
		
		if(strteamFlag=="insert" || strteamFlag=="delete"){    	
	    	$("#teamCode").dxTextBox({ value: "" , readOnly: false});
			$("#teamName").dxTextBox({ value: "" });
			$("#workTime").dxTextBox({ value: "" });			
		}
	},
	onSelectionChanged : function(e) {
		var teamSelData = e.selectedRowsData[0];
		
		if(teamSelData){
			$("#teamCode").dxTextBox({ value: teamSelData.GROUP_NO, readOnly: true });
			$("#teamName").dxTextBox({ value: teamSelData.GROUP_NAME });
			$("#workTime").dxTextBox({ value: teamSelData.GROUP_WORKTIME });
			
			$("#teamInsertBtn").hide();
			$("#teamUpdateBtn").show().css('display', '');
	    	$("#teamDeleteBtn").show().css('display', '');
		}else{
			$("#teamCode").dxTextBox({ value: "", readOnly: false });
			$("#teamName").dxTextBox({ value: "" });
			$("#workTime").dxTextBox({ value: "" });
			
			$("#teamInsertBtn").show().css('display', '');
			$("#teamUpdateBtn").hide();
	    	$("#teamDeleteBtn").hide();
		}
	}
});

//팀 조회
function teamSelect(f_dept){
	$.ajax({
		url : "/MWTS/mctm/selectgroupRegTeam.do",
		type : "POST",
		data : {
			f_dept : f_dept
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
	}).done(function(data) {
		var data = data.dataList;
		
		$("#team_Grid").dxDataGrid({ dataSource: data });
	});	
}
teamSelect("");

//팀 업데이트
function teamUpdate(f_TeamFlag, f_TeamCode, f_TeamName, f_WorkTime){
	$.ajax({
		url : "/MWTS/mctm/updategroupRegTeam.do",
		data : {
			f_TeamFlag : f_TeamFlag,
			f_TeamCode : f_TeamCode,
			f_TeamName : f_TeamName,
			f_WorkTime : f_WorkTime,
			f_TeamDeptCode : deptSelData.GROUP_NO
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
		success : function(result) {
			if(result.RET == "0"){
				if(deptSelData){
					teamSelect(deptSelData.GROUP_NO);
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