<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>대상자관리</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	
<%@include file="../../mwts/layout/topJS.jsp"%>
<style type="text/css">
/*그리드 헤더 여백*/
#select_Grid > .dx-datagrid > .dx-datagrid-header-panel {
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
		<div id="select_Container" style="position: absolute; height: 100%; width: calc(100% - 370px); left:0px; ">
				<div id="select_Grid" style="position: absolute; height: calc(100% - 10px); width: 100%; border: 2px solid #e8e8e8; "></div>
		</div>
    
		<div id="update_Container" style="position: absolute; height: 100%; width: 320px; right:20px; overflow:auto;">
			<div style="position: absolute; height: 500px; width: 100%; top: 100px;">
	  			<label style="position: absolute; right: 220px; width: 100px; text-align: right "><strong>* 대상자ID</strong></label>
				<div id="agentID" style="position: absolute; right: 0px; width: 200px "></div>
	 			<label style="position: absolute; right: 220px; width: 100px; text-align: right; top: 45px "><strong>* 대상자명</strong></label>
				<div id="agentName" style="position: absolute; right: 0px; width: 200px; top: 45px "></div>
	 			<label style="position: absolute; right: 220px; width: 100px; text-align: right; top: 90px "><strong>* 직급</strong></label>
				<div id="levelCode" style="position: absolute; right: 0px; width: 200px; top: 90px "></div>
	 			<label style="position: absolute; right: 220px; width: 100px; text-align: right; top: 135px "><strong>* 회사</strong></label>
				<div id="branchCode" style="position: absolute; right: 0px; width: 200px; top: 135px "></div>
	 			<label style="position: absolute; right: 220px; width: 100px; text-align: right; top: 180px "><strong>* 부서</strong></label>
				<div id="deptCode" style="position: absolute; right: 0px; width: 200px; top: 180px "></div>
	 			<label style="position: absolute; right: 220px; width: 100px; text-align: right; top: 225px "><strong>* 서버</strong></label>
				<div id="serverCode" style="position: absolute; right: 0px; width: 200px; top: 225px "></div>
	 			<label style="position: absolute; right: 220px; width: 100px; text-align: right; top: 270px ">내선번호</label>
				<div id="telNum" style="position: absolute; right: 0px; width: 200px; top: 270px "></div>
	 			<label style="position: absolute; right: 220px; width: 100px; text-align: right; top: 315px ">채널번호</label>
				<div id="vpmNum" style="position: absolute; right: 0px; width: 200px; top: 315px "></div>
	 			<label style="position: absolute; right: 220px; width: 100px; text-align: right; top: 360px ">전화기IP</label>
				<div id="ipmAgentIp" style="position: absolute; right: 0px; width: 200px; top: 360px "></div>
	 			<label style="position: absolute; right: 220px; width: 100px; text-align: right; top: 405px ">사용여부</label>
				<div id="useYn" style="position: absolute; right: 0px; width: 200px; top: 405px "></div>
				
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
var levelData = [];
var branchData = [];
var deptData = [];
var serverData = [];
var vpmInfoData =[];
var useYnData =[{ code : "Y", name : "Y"}, { code : "N", name : "N"}];

//직급 조회
$.ajax({
	url : "/MWTS/acr/selectLevelCode.do",
	type : "POST",
	data : {},
	cache : false, //IE 브라우저 캐싱방지
	async : false, // ajax 순차적으로 실행
}).done(function(data) {
	var data = data.dataList; 
	
	$.each(data, function(i, item) {
		var levelArr = {
				code : "",
				name : ""
		}
		
		levelArr.code = item.Level_Code;
		levelArr.name = item.Level_Name;
		
		levelData.push(levelArr);
	});
});

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

//부서코드 조회
$.ajax({
	url : "/MWTS/acr/selectDept.do",
	type : "POST",
	data : {},
	cache : false, //IE 브라우저 캐싱방지
	async : false, // ajax 순차적으로 실행
}).done(function(data) {
	var data = data.dataList; 
	
	$.each(data, function(i, item) {
		var deptArr = {
				code : "",
				name : "",
				seq : ""
		}
		
		deptArr.code = item.dept_id;
		deptArr.name = item.dept_name;
		deptArr.seq = item.seq;
		
		deptData.push(deptArr);
	});
});

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

//내선번호 조회
$.ajax({
	url : "/MWTS/acr/selectVpmInfo.do",
	type : "POST",
	data : {},
	cache : false, //IE 브라우저 캐싱방지
	async : false, // ajax 순차적으로 실행
}).done(function(data) {
	var data = data.dataList; 
	
	$.each(data, function(i, item) {
		var vpmInfoArr = {
				server_code : "",
				telnum : "",
				vpmnum : "",
				ip : "",
		}
		
		vpmInfoArr.server_code = item.SERVER_CODE;
		vpmInfoArr.telnum = item.TEL_NUM;
		vpmInfoArr.vpmnum = item.VPM_NUM;
		vpmInfoArr.ip = item.IPM_AGENT_IP;
		
		vpmInfoData.push(vpmInfoArr);
	});
});


var strFlag ="";
var strAgentID ="";
var strAgentName ="";
var strLevelCode ="";
var strBranchCode ="";
var strDeptCode ="";
var strServerCode ="";
var strTelNum ="";
var strUseYn ="";

//대상자ID
$("#agentID").dxTextBox({
	maxLength: 10,
	readOnly: false,
	value: "",
	onValueChanged: function (e) {
		strAgentID = e.value;
	}
});

//대상자명
$("#agentName").dxTextBox({
	maxLength: 20,
	value: "",
	onValueChanged: function (e) {
		strAgentName = e.value;
	}
});

//직급
$("#levelCode").dxSelectBox({
 	dataSource: levelData,
    displayExpr: "name",
    valueExpr: "code",
    onInitialized: function (e) {
    	strLevelCode = e.component._options.value;
    },      
    onValueChanged: function (e) {
    	strLevelCode = e.value;
    }
});

//회사
$("#branchCode").dxSelectBox({
 	dataSource: branchData,
    displayExpr: "name",
    valueExpr: "code",
    onInitialized: function (e) {
    	strBranchCode = e.component._options.value;
    },      
    onValueChanged: function (e) {
    	strBranchCode = e.value;
    }
});

//부서
$("#deptCode").dxSelectBox({
 	dataSource: deptData,
    displayExpr: "name",
    valueExpr: "code",
    onInitialized: function (e) {
    	strDeptCode = e.component._options.value;
    },      
    onValueChanged: function (e) {
    	strDeptCode = e.value;
    }
});

//서버
$("#serverCode").dxSelectBox({
 	dataSource: serverData,
    displayExpr: "name",
    valueExpr: "code",
    readOnly: false,
    onInitialized: function (e) {
    	strServerCode = e.component._options.value;
    	
 		//부서 seq 정렬
        var deptCodeBox = $("#deptCode").dxSelectBox("instance");
        if (deptCodeBox) {
        	deptCodeBox.option("dataSource",deptData);
       	 
            var dataSource = deptCodeBox.getDataSource();
            dataSource.sort([{ field: "seq", asc: true }]);
            dataSource.load();
        }
    },      
    onValueChanged: function (e) {
    	strServerCode = e.value;
    	
    	//서버 선택 시 내선번호, 채널번호, 전화기IP 새로고침
		$("#telNum").dxSelectBox({ value: "" });
		$("#vpmNum").dxTextBox({ value: "" });
		$("#ipmAgentIp").dxTextBox({ value: "" });
		
		//선택한 서버코드에 따라 내선번호 필터링
         var telNumBox = $("#telNum").dxSelectBox("instance");
         if (telNumBox) {
        	 telNumBox.option("dataSource",vpmInfoData);
        	 
             var dataSource = telNumBox.getDataSource();
             dataSource.filter(["server_code", "=", strServerCode]);
             dataSource.load();
         }
    }
});

//내선번호
$("#telNum").dxSelectBox({
 	dataSource: "",
    displayExpr: "telnum",
    valueExpr: "telnum",
    onInitialized: function (e) {
    	strTelNum = e.component._options.value;
    },
    onValueChanged: function (e) {
    	strTelNum = e.value;
    	
    	//내선번호 선택 시 채널번호, 전화기IP 바인딩
    	var telNumBox = $("#telNum").dxSelectBox("instance");
    	
		var selVpmNum = telNumBox.option('selectedItem') ? telNumBox.option('selectedItem').vpmnum : "";
		var selIp = telNumBox.option('selectedItem') ? telNumBox.option('selectedItem').ip : "";
		
		$("#vpmNum").dxTextBox({ value: selVpmNum });
		$("#ipmAgentIp").dxTextBox({ value: selIp });
    }
});

//채널번호
$("#vpmNum").dxTextBox({
	value: "",
	readOnly: true
});

//전화기IP
$("#ipmAgentIp").dxTextBox({
	value: "",
	readOnly: true
});

//사용여부
$("#useYn").dxSelectBox({
 	dataSource: useYnData,
    displayExpr: "name",
    valueExpr: "code",
    value: "Y",
    onInitialized: function (e) {
    	strUseYn = e.component._options.value;
    },      
    onValueChanged: function (e) {
    	strUseYn = e.value;
    }
});

$("#select_Grid").dxDataGrid({
	dataSource : {},
	keyExpr: ["AGENT_ID","server_code"],
	hoverStateEnabled : true,
	columnAutoWidth : false,
	allowResizing: true,
	errorRowEnabled: false,
    allowColumnResizing: true,
    columnResizingMode: 'widget', // or 'nextColumn'
	selection : {
		mode : "single"
	},
	headerFilter : {
		visible : true
	},
	searchPanel : {
		visible : true,
		width:400
	},
	scrolling: {
        columnRenderingMode: "standard",
        showScrollbar: "always"
    },
	columns : [{
		caption : "대상자ID",
		dataField : "AGENT_ID"
	},{
		caption : "대상자명",
		dataField : "AGENT_NAME"
	},{
		caption : "직급",
		dataField : "LEVEL_CODE",
        lookup: {
            dataSource: levelData,
            displayExpr: "name",
            valueExpr: "code"
        }
	},{
		caption : "회사",
		dataField : "BRANCH_CODE",
        lookup: {
            dataSource: branchData,
            displayExpr: "name",
            valueExpr: "code"
        }
	},{
		caption : "부서",
		dataField : "DEPT_CODE",
        lookup: {
            dataSource: deptData,
            displayExpr: "name",
            valueExpr: "code"
        }
	},{
		caption : "서버",
		dataField : "server_code",
        lookup: {
            dataSource: serverData,
            displayExpr: "name",
            valueExpr: "code"
        }
	},{
		caption : "내선번호",
		dataField : "TEL_NUM",
        lookup: {
            dataSource: vpmInfoData,
            displayExpr: "telnum",
            valueExpr: "telnum"
        }
	},{
		caption : "채널번호",
		dataField : "VPM_NUM",
		alignment: 'left'
	},{
		caption : "전화기IP",
		dataField : "IPM_AGENT_IP"
	},{
		caption : "사용여부",
		dataField : "USE_YN",		
        lookup: {
            dataSource: useYnData,
            displayExpr: "name",
            valueExpr: "code"
        }
	},{
        caption : "비밀번호",
        alignment: 'center',
        width: 85,
        cellTemplate: function (container, options) {

    		if(!options.key.AGENT_ID || options.key.AGENT_ID == undefined){ //행 추가 시 초기화 불가(key여부로 체크)
                $('<img />').attr('src', '../images/padlock2_disable.png')
                .appendTo(container);
    		}else{
                $('<img />').attr('src', '../images/padlock2_enable.png').attr('style', 'cursor:pointer').attr('title', '초기화')
                .on('dxclick', function () {
                	var result = DevExpress.ui.dialog.confirm("ID : <b>"+options.key.AGENT_ID+"</b><br>비밀번호를 초기화 하시겠습니까?");
                	$(".dx-popup-title.dx-toolbar").css("display", "none");//popup title 숨기기(내용없음)
                	
                    result.done(function (dialogResult) {
                    	if (dialogResult) {
                    		strFlag = "resetPW";
                    		strAgentID =options.key.AGENT_ID;
                    		strServerCode =options.key.server_code;
                    		
                    		agentInfoUpdate(strFlag, strAgentID, "", strAgentID, "", "", "", strServerCode, "", "");
                    	}
                  	
                    });
                })                        
                .appendTo(container);
    		}
        }
	}],
	onContentReady : function(e) {
		if(strFlag=="insert" || strFlag=="delete"){			
			$("#agentID").dxTextBox({ value: "", readOnly: false });
			$("#agentName").dxTextBox({ value: "" });
			$("#levelCode").dxSelectBox({ value: "" });
			$("#branchCode").dxSelectBox({ value: "" });
			$("#deptCode").dxSelectBox({ value: "" });
			$("#serverCode").dxSelectBox({ value: "", readOnly: false });
			$("#telNum").dxSelectBox({ value: "" });
			$("#vpmNum").dxTextBox({ value: "" });
			$("#ipmAgentIp").dxTextBox({ value: "" });
			$("#useYn").dxSelectBox({ value: "Y" });
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
                	
                	$("#agentID").dxTextBox('instance').focus();            		
            	} 
        	},
            location: 'after'
        });
    },	
	onSelectionChanged : function(e) {
		var agentSelData = e.selectedRowsData[0];
		
		if(agentSelData){
			$("#agentID").dxTextBox({ value: agentSelData.AGENT_ID, readOnly: true });
			$("#agentName").dxTextBox({ value: agentSelData.AGENT_NAME });
			$("#levelCode").dxSelectBox({ value: agentSelData.LEVEL_CODE });
			$("#branchCode").dxSelectBox({ value: agentSelData.BRANCH_CODE });
			$("#deptCode").dxSelectBox({ value: agentSelData.DEPT_CODE });
			$("#serverCode").dxSelectBox({ value: agentSelData.server_code, readOnly: true });
			$("#telNum").dxSelectBox({ value: agentSelData.TEL_NUM });
			$("#vpmNum").dxTextBox({ value: agentSelData.VPM_NUM });
			$("#ipmAgentIp").dxTextBox({ value: agentSelData.IPM_AGENT_IP });
			$("#useYn").dxSelectBox({ value: agentSelData.USE_YN });
			
			$("#insertBtn").hide();
			$("#updateBtn").show().css('display', '');
	    	$("#deleteBtn").show().css('display', '');
		}else{
			$("#agentID").dxTextBox({ value: "", readOnly: false });
			$("#agentName").dxTextBox({ value: "" });
			$("#levelCode").dxSelectBox({ value: "" });
			$("#branchCode").dxSelectBox({ value: "" });
			$("#deptCode").dxSelectBox({ value: "" });
			$("#serverCode").dxSelectBox({ value: "", readOnly: false });
			$("#telNum").dxSelectBox({ value: "" });
			$("#vpmNum").dxTextBox({ value: "" });
			$("#ipmAgentIp").dxTextBox({ value: "" });
			$("#useYn").dxSelectBox({ value: "Y" });
			
			$("#insertBtn").show().css('display', '');
			$("#updateBtn").hide();
	    	$("#deleteBtn").hide();
		}
	}
}).dxDataGrid("instance");	

//대상자관리 조회
function agentInfoSelect(){
	$.ajax({
		url : "/MWTS/acr/selectAgentInfo.do",
		type : "POST",
		data : {},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
	}).done(function(data) {
		var data = data.dataList;

		$("#select_Grid").dxDataGrid({ dataSource: data });	
	});	
}
agentInfoSelect();

//저장
$("#insertBtn").dxButton({
    text: "저장",
    type: "default",
    onClick: function(e) { 
    	strFlag = "insert";
    	agentInfoUpdate(strFlag, strAgentID, strAgentName, strAgentID, strLevelCode, strBranchCode, strDeptCode, strServerCode, strUseYn, strTelNum);//신규 생성 시 패스워드는 아이디와 동일
    }
});
//수정
$("#updateBtn").dxButton({
    text: "수정",
    type: "success",
    onClick: function(e) { 
    	strFlag = "update";
    	agentInfoUpdate(strFlag, strAgentID, strAgentName, "", strLevelCode, strBranchCode, strDeptCode, strServerCode, strUseYn, strTelNum);//패스워드는 초기화만 가능(업데이트X)
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
        		agentInfoUpdate(strFlag, strAgentID, "", "", "", "", "", strServerCode, "", "");
        	}
      	
        });       	
    }
});
$("#updateBtn").hide();
$("#deleteBtn").hide();


function agentInfoUpdate(f_Flag, f_AgentID, f_AgentName, f_AgentPW, f_LevelCode, f_BranchCode, f_DeptCode, f_ServerCode, f_Useyn, f_TelNum){
	//publicKey
    var rsa = new RSAKey();
    	rsa.setPublic("<%=modulus%>", "<%=exponent%>");

    var rsaId = f_AgentID;
    var rsaPw = f_AgentPW;
	
	$.ajax({
		url : "/MWTS/acr/updateAgentInfo.do",
		data : {
			f_Flag : f_Flag,
			f_AgentID : rsa.encrypt(rsaId),
			f_AgentName : f_AgentName,
			f_AgentPW : rsa.encrypt(rsaPw),
			f_LevelCode : f_LevelCode,
			f_BranchCode : f_BranchCode,
			f_DeptCode : f_DeptCode,
			f_ServerCode : f_ServerCode,
			f_Useyn : f_Useyn,
			f_TelNum : f_TelNum
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
		success : function(result) {
			if(result.RET == "0"){
				agentInfoSelect();
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