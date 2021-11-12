<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>관리자 설정</title>
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
<%
	String modulus = (String)request.getAttribute("modulus");
	String exponent = (String)request.getAttribute("exponent");	
%>
</head>

<body class="dx-viewport">
    <div class="demo-container" style="position: absolute; height: 100%; width: 100% ">
    
		<div id="select_Container" style="position: absolute; height: 100%; width: calc(100% - 350px); left:0px; ">
				<div id="user_Grid" style="position: absolute; height: calc(100% - 10px); width: 100%; border: 2px solid #e8e8e8; "></div>
		</div>

		<div id="update_Container" style="position: absolute; height: 100%; width: 300px; right:20px; overflow:auto;">
			<div style="position: absolute; height: 270px; width: 100%; top: 100px;">
	  			<label style="position: absolute; right: 220px; width: 100px; text-align: right "><strong>* 아이디</strong></label>
				<div id="userID" style="position: absolute; right: 0px; width: 200px "></div>
	 			<label style="position: absolute; right: 220px; width: 100px; text-align: right; top: 45px "><strong>* 이름</strong></label>
				<div id="userName" style="position: absolute; right: 0px; width: 200px; top: 45px "></div>
	 			<label style="position: absolute; right: 220px; width: 100px; text-align: right; top: 90px ">녹취청취</label>
				<div id="listenYN" style="position: absolute; right: 0px; width: 200px; top: 90px "></div>
	 			<label style="position: absolute; right: 220px; width: 100px; text-align: right; top: 135px ">녹취저장</label>
				<div id="saveYN" style="position: absolute; right: 0px; width: 200px; top: 135px "></div>
	 			<label style="position: absolute; right: 220px; width: 100px; text-align: right; top: 180px ">사용여부</label>
				<div id="useYN" style="position: absolute; right: 0px; width: 200px; top: 180px "></div>

	 			<div id="btn_Grid" style="position: absolute; width: 100%; height:40px; bottom: 0px; text-align: center; ">
		 			<div id="updateBtn" style="position: absolute; width:90px; right:100px;"></div>
					<div id="deleteBtn" style="position: absolute; width:90px; right:0px; "></div>
					<div id="insertBtn" style="position: absolute; width:90px; right:0px; "></div>			
				</div>
			</div>
		</div>	
    </div>
</body>
</html>

<script language="JavaScript" src="../js/jsbn.js"></script>
<script language="JavaScript" src="../js/prng4.js"></script>
<script language="JavaScript" src="../js/rng.js"></script>
<script language="JavaScript" src="../js/rsa.js"></script>
<script type="text/javascript">
var selectYN =[{ desc : "Y", val : "Y"}, { desc : "N", val : "N"}];
var strFlag ="";
var strUserID ="";
var strUserName ="";
var strListenYN ="";
var strSaveYN ="";
var strUseYN ="";

//아이디
$("#userID").dxTextBox({
	maxLength: 20,
	readOnly: false,
	value: "",
	onValueChanged: function (e) {
		strUserID = e.value;
	}
})
//이름
$("#userName").dxTextBox({
	maxLength: 20,
	value: "",
	onValueChanged: function (e) {
		strUserName = e.value;
	}
})
//녹취청취
$("#listenYN").dxSelectBox({
 	dataSource: selectYN,
    displayExpr: "desc",
    valueExpr: "val",
    value: "Y",
    onInitialized: function (e) {
    	strListenYN = e.component._options.value;
    },      
    onValueChanged: function (e) {
    	strListenYN = e.value;
    }
});
//녹취저장
$("#saveYN").dxSelectBox({
 	dataSource: selectYN,
    displayExpr: "desc",
    valueExpr: "val",
    value: "Y",
    onInitialized: function (e) {
    	strSaveYN = e.component._options.value;
    },      
    onValueChanged: function (e) {
    	strSaveYN = e.value;
    }
});	
//사용여부
$("#useYN").dxSelectBox({
 	dataSource: selectYN,
    displayExpr: "desc",
    valueExpr: "val",
    value: "Y",
    onInitialized: function (e) {
    	strUseYN = e.component._options.value;
    },    
    onValueChanged: function (e) {
    	strUseYN = e.value;
    }
});	

$("#user_Grid").dxDataGrid({
	dataSource : {},
	keyExpr : "user_id",
	selection : {
		mode : "single"
	},
    headerFilter: {
        visible: true
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
		caption : "아이디",
		dataField : "user_id"
	}, {
		caption : "이름",
		dataField : "user_name"
	},  {
		caption : "녹취청취",
		dataField : "listen_yn",
        lookup: {
            dataSource: selectYN,
            displayExpr: "desc",
            valueExpr: "val"
        }
	}, {
		caption : "녹취저장",
		dataField : "save_yn",
        lookup: {
            dataSource: selectYN,
            displayExpr: "desc",
            valueExpr: "val"
        }
	}, {
		caption : "사용여부",
		dataField : "use_yn",
        lookup: {
            dataSource: selectYN,
            displayExpr: "desc",
            valueExpr: "val"
        }
	}, {
		caption : "비밀번호",
        alignment: 'center',
        width : 100,
        cellTemplate: function (container, options) {

    		if(!options.key || options.key == undefined){ //행 추가 시 초기화 불가(key여부로 체크)
                $('<img />').attr('src', '../images/padlock2_disable.png')
                .appendTo(container);
    		}else{
                $('<img />').attr('src', '../images/padlock2_enable.png').attr('style', 'cursor:pointer').attr('title', '초기화')
                .on('dxclick', function () {
                	var result = DevExpress.ui.dialog.confirm("ID : <b>"+options.key+"</b><br>비밀번호를 초기화 하시겠습니까?");
                	$(".dx-popup-title.dx-toolbar").css("display", "none");//popup title 숨기기(내용없음)
                	
                    result.done(function (dialogResult) {
                    	if (dialogResult) {
                    		strFlag = "resetPW";
                    		strUserID =options.key;
                    		
                    		userInfoUpdate(strFlag, strUserID, "", "", "", "");
                    	}
                    });
                })                        
                .appendTo(container);
    		}
        }				
	}],
	onContentReady : function(e) {
		//e.component.selectRowsByIndexes([0]);
	
		if(strFlag=="insert" || strFlag=="delete"){
			$("#userID").dxTextBox({ value: "", readOnly: false });
			$("#userName").dxTextBox({ value: "" });
			$("#listenYN").dxSelectBox({ value: "Y" });
			$("#saveYN").dxSelectBox({ value: "Y" });
			$("#useYN").dxSelectBox({ value: "Y" });	
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
                	var dxDataGrid = $("#user_Grid").dxDataGrid('instance');
                	var keys = dxDataGrid.getSelectedRowKeys();
                	dxDataGrid.deselectRows(keys);
                	
                	$("#userID").dxTextBox('instance').focus();            		
            	} 
        	},
            location: 'after'
        });
    },	
	onSelectionChanged : function(e) {
		var userSelData = e.selectedRowsData[0];
		
		if(userSelData){
			$("#userID").dxTextBox({ value: userSelData.user_id, readOnly: true });
			$("#userName").dxTextBox({ value: userSelData.user_name });
			$("#listenYN").dxSelectBox({ value: userSelData.listen_yn });
			$("#saveYN").dxSelectBox({ value: userSelData.save_yn });
			$("#useYN").dxSelectBox({ value: userSelData.use_yn });
			
			$("#insertBtn").hide();
			$("#updateBtn").show().css('display', '');
	    	$("#deleteBtn").show().css('display', '');
		}else{
			$("#userID").dxTextBox({ value: "", readOnly: false });
			$("#userName").dxTextBox({ value: "" });
			$("#listenYN").dxSelectBox({ value: "Y" });
			$("#saveYN").dxSelectBox({ value: "Y" });
			$("#useYN").dxSelectBox({ value: "Y" });	
			
			$("#insertBtn").show().css('display', '');
			$("#updateBtn").hide();
	    	$("#deleteBtn").hide();
		}
	}
});	

//관리자 조회
function userInfoSelect(){
	$.ajax({
		url : "/MWTS/mwts/userSelect.do",
		type : "POST",
		data : {},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
	}).done(function(data) {
		var data = data.userList;
		
		$("#user_Grid").dxDataGrid({ dataSource: data });
	});
}
userInfoSelect();

//저장
$("#insertBtn").dxButton({
    text: "저장",
    type: "default",
    onClick: function(e) { 
    	strFlag = "insert";
    	userInfoUpdate(strFlag, strUserID, strUserName, strListenYN, strSaveYN, strUseYN);
    }
});
//수정
$("#updateBtn").dxButton({
    text: "수정",
    type: "success",
    onClick: function(e) { 
    	strFlag = "update";
    	userInfoUpdate(strFlag, strUserID, strUserName, strListenYN, strSaveYN, strUseYN);
    }
});
//삭제
$("#deleteBtn").dxButton({
    text: "삭제",
    type: "danger",
    onClick: function(e) { 
    	var result = DevExpress.ui.dialog.confirm("삭제하시겠습니까?");
    	$(".dx-popup-title.dx-toolbar").css("display", "none");//popup title 숨기기(내용없음)
    	
        result.done(function (dialogResult) {
        	if (dialogResult) {
        		strFlag = "delete";
        		userInfoUpdate(strFlag, strUserID, "", "", "", "");
        	}
      	
        });       	
    }
});
$("#updateBtn").hide();
$("#deleteBtn").hide();


function userInfoUpdate(f_Flag, f_UserID, f_UserName, f_ListenYN, f_SaveYN, f_UseYN){
	//publicKey
    var rsa = new RSAKey();
    	rsa.setPublic("<%=modulus%>", "<%=exponent%>");

    var rsaId = f_UserID;
	
	$.ajax({
		url : "/MWTS/mwts/updateUserInfo.do",
		data : {
			f_Flag : f_Flag,
			f_UserID : rsa.encrypt(rsaId),
			f_UserName : f_UserName,
			f_ListenYN : f_ListenYN,
			f_SaveYN : f_SaveYN,
			f_UseYN : f_UseYN
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
		success : function(result) {
			if(result.RET == "0"){
				userInfoSelect();
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