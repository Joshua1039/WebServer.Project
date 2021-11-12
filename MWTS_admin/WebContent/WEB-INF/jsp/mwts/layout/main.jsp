<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
content="width=device-width, initial-scale=1.0, maximum-scale=1" />
<meta http-equiv="Pragma" content="no-cache" />
<title>MODERNWAVE</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<!-- 브라우저아이콘 -->
<link rel="shortcut icon" href="../images/favicon.ico" type="image/x-icon"/>
<!-- 구글폰트 -->
<link rel="stylesheet" type="text/css" href="../devextreame/Sources/Lib/css/google.font.css" />
<!-- 테마 -->
<link rel="stylesheet" type="text/css" href="../devextreame/Sources/Lib/css/dx.spa.css" />
<link rel="stylesheet" type="text/css" href="../devextreame/Sources/Lib/css/dx.common.css" />
<%@include file="../../mwts/theme/color.jsp"%>

<script src="../devextreame/Sources/Lib/js/jquery-2.2.3.min.js"></script>
<script src="../devextreame/Sources/Lib/js/knockout-3.4.0.js"></script>
<script src="../devextreame/Sources/Lib/js/jszip.min.js"></script>
<script src="../devextreame/Sources/Lib/js/localization/dx.all_ko.js"></script>

<!-- 사이드바 -->
<link rel="stylesheet" type="text/css" href="../devextreame/Sources/Lib/css/sidebar.css" />
<script src="../devextreame/Sources/Lib/js/jquery-ui-1.11.4.custom.min.js"></script>
<!-- 메뉴 탭 -->
<link rel="stylesheet" type="text/css" href="../devextreame/Sources/Lib/css/easyui.css" />
<script src="../devextreame/Sources/Lib/js/jquery.easyui.min.js"></script>

<style type="text/css">
/*배경 이미지*/
#main_container:after {
    content : "";
    display: block;
    position: absolute;
    width: 100%;
    height: 100%;
    background-image: url('../images/bg_gray.png'); 
    background-repeat: no-repeat; 
    background-size: cover !important; 
    background-position: center center;
    opacity : 0.6;
    filter: alpha(opacity=60);
    z-index: -1;
}
/* 버튼 */
.topBtn {
	position: absolute; 
	top:0px;
	right: 0px;
	height: 40px; 
	margin:20px; 
	min-width: 500px; 
	text-align:right;
}
/*버튼 구분선*/
 #userID:after {
    content: "";
    position: absolute;
    border-left: 1.4px solid #727171;
    height: 55%;
    left: 260px;
    top: 6px;
} 
 #logout:after {
    content: "";
    position: absolute;
    border-left: 1.4px solid #727171;
    height: 55%;
    left: 370px;
    top: 6px;
}
/* 메뉴 */
#menu {
	position: absolute; 
	top: 0px;
	left: 210px;
	height: 40px;
	margin: 18px 0 20px 0; 
	text-align: center; 
	font-size: 18px; 
	font-weight:600; 
	font-family: Noto Sans KR, sans-serif; 
	color: #727171; 
	z-index: 1;
}
/* 메뉴 최상위 배경없음*/
#menu > .dx-menu-horizontal > .dx-menu-items-container > .dx-menu-item-wrapper > .dx-state-hover {
    background-color: transparent;
}
/* 탭 iframe */
#contentTab > div.tabs-panels > div.panel > div.panel-body > iframe {
	position: absolute; 
	top: 50px; 
	bottom: 20px; 
	left: 20px; 
	right: 20px; 
	height:calc(100% - 70px); 
	width:calc(100% - 40px);
}
/* 탭 새로고침*/
.icon-reload {
    cursor: pointer;
    background-image: url('../images/refresh_tab.png'); 
}​

</style>
<%
Object loginSession = session.getAttribute("SESSION_USER");
	if(loginSession == null) {
		response.sendRedirect("/MWTS/mwts/logout.do");
		//out.println("<Script language='JavaScript'>alert('세션이 종료되어 로그인창으로 돌아갑니다.');location.href='/MWTS/mwts/logout.do';</Script>");
		return;
	}
	
	String modulus = (String)session.getAttribute("modulus");
	String exponent = (String)session.getAttribute("exponent");	
%>
</head>
<body class="dx-viewport">
<div class="demo-container" id="main_container" 
	style="position: relative; height: 100%; width: 100%;">

	<div class="topContainer"
		style="position: absolute; height: 90px; width: 100%; background: linear-gradient(to bottom, rgba(255,0,0,0), white);">
			<img src="../images/modern_logo.png" style="margin:20px; cursor:pointer; " onclick="home();"/>
			
			<div id="menu"></div>
			
			<div class="topBtn">
				<div id="userID" style="min-width:120px;"></div>
				<div id="logout" style="width:100px;"></div>
				<div id="changePW" style="width:130px;"></div>
				<!-- <div id="theme" style="display: inline-block; vertical-align: middle; width: 130px"></div> -->
			</div>
	</div>

	<div class="bottomContainer" 
		style="position: absolute; top: 90px; height: calc(100% - 90px); width: 100%; background-color:#fff;">
		<!-- 왼쪽 -->
		<div class="leftContainer"
			style="position: absolute; height: 100%; left: 0px; width: calc(100% - 50px);">
			<div id="contentTab" class="easyui-tabs" fit="true"></div><!-- fit="true" 부모창 크기에 맞춤-->
			<div id="popup"></div>
		</div>
	
		<!-- 오른쪽 -->
		<div class="rightContainer"
			style="position: absolute; height: 100%; right: 0px; width:50px;">
			<div id="agentContainer" class="sidebar_content"></div>
			<div class="sidebar_bg">
				<div class="sidebar_btn"/>
			</div>			
		</div>		
	</div>

</div>	
</body>
</html>

<%-- <jsp:include page='../theme/theme.jsp' flush="false" /> --%>
<jsp:include page='../../mctm/agent/agent.jsp' flush="false" />

<script language="JavaScript" src="../js/jsbn.js"></script>
<script language="JavaScript" src="../js/prng4.js"></script>
<script language="JavaScript" src="../js/rng.js"></script>
<script language="JavaScript" src="../js/rsa.js"></script>
<script type="text/javascript">
var userGrade = "<c:out value="${sessionScope.SESSION_USER.gradeCode}" />";
var userId = "<c:out value="${sessionScope.SESSION_USER.userId}" />";
var userName = "<c:out value="${sessionScope.SESSION_USER.userName}" />";
var menuData = [];

$(document).ready(function() {
	$('#contentTab').tabs('add',{
		fit:true,
		title:'HOME',
		content:'<iframe src="/MWTS/mwts/home.do"></iframe>',
		closable:false
	});
});

function mainMenu() {
	$.ajax({
		url : "/MWTS/mwts/menuSelect.do",
		type : "POST",
		data : {
			userId : userId
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
	}).done(function(data) {
		var data = data.menuList;

		$.each(data, function(i, item) {

			if (item.step == 1) {
				//대분류
				var main_arr = {
					code : "",
					name : "",
					index_no : "",
					icon : "",
					items : []
				}

				main_arr.code = item.step;
				main_arr.name = item.title;
				main_arr.index_no = item.index_no;
				main_arr.icon = item.icon;
				//대분류 배열
				menuData.push(main_arr);
			}

			$.each(menuData, function(i, element) {

				if (item.step == 2) {
					if (element.index_no == item.parent_idx) {
						//중분류
						var middle_arr = {
							code : "",
							name : "",
							index_no : "",
							parent_no : "",
							icon : "",
							items : []
						}
						middle_arr.code = item.step;
						middle_arr.name = item.title;
						middle_arr.index_no = item.index_no;
						middle_arr.parent_idx = item.parent_idx;
						middle_arr.icon = item.icon;
						//중분류 배열
						element.items.push(middle_arr);
					}
				}

				var middle_item = element.items;

				$.each(middle_item, function(i, element2) {

					if (item.step == 3) {
						if (element2.index_no == item.parent_idx) {
							//소분류
							var small_arr = {
								code : "",
								name : "",
								url : "",
								visible : "",
								index_no : "",
								icon : "",
							}
							small_arr.code = item.step;
							small_arr.name = item.title;
							small_arr.url = item.url;
							small_arr.visible = item.visible;
							small_arr.index_no = item.index_no;
							small_arr.icon = item.icon;
							//소분류 배열
							element2.items.push(small_arr);
						}
					}
				})

			})
		});	
	});		

}
//메뉴 조회
mainMenu();

var openClass="";
var openName="";
$(function() {
	$("#menu").dxMenu({
		dataSource : menuData,
		hideSubmenuOnMouseLeave : false,
		displayExpr : "name",
		onItemClick : function(data) {
			var item = data.itemData;
			//탭 열기
			if(item.url){
				var menuName = item.name;
				var findStr = "모니터링";
				
				if (menuName.indexOf(findStr) != -1) {//모니터링은 새창으로열기, 그 외 탭으로 열기
					window.open(item.url);
				}else{
					addTab(item.name, item.url);
				}
			}
			//하위메뉴 숨기기
			if(openClass=='' && openName==''){
				openClass= 'Y';
				openName= data.itemData.name;
				$(".dx-overlay-wrapper").css("display", "");
			}else if(openClass=='Y' && openName!=data.itemData.name){
				openClass= 'Y';
				openName= data.itemData.name;
				$(".dx-overlay-wrapper").css("display", "");
			}else if(openClass=='Y' && openName==data.itemData.name){
				openClass= '';
				openName= '';
				$(".dx-overlay-wrapper").css("display", "none");
			}
		}
	}).dxMenu("instance");

	var userIDBtn = $("#userID").dxButton({
		text : userName,
		type : "normal",
		icon: "user"
	});
	userIDBtn.css({"font-family":"Nanum Gothic", "font-size":"15px", "color":"black", "background":"transparent", "border":"transparent" , "cursor":"text"});
	
	var logoutBtn = $("#logout").dxButton({
		text : "로그아웃",
		type : "normal",
		onClick : function(e) {
			$(location).attr('href', '/MWTS/mwts/logout.do');
		}
	});
	logoutBtn.css({"font-family":"Nanum Gothic", "font-size":"15px", "color":"black", "background":"transparent", "border":"transparent"});
	
	var changePWBtn = $("#changePW").dxButton({
		text : "비밀번호 변경",
		type : "normal",
		onClick : function(e) {
			$("#popup").dxPopup("instance").show();
		}
	});
	changePWBtn.css({"font-family":"Nanum Gothic", "font-size":"15px", "color":"black", "background":"transparent", "border":"transparent"});
});

var strPasswordOld ="";
var strPasswordNew ="";
var strPasswordChk ="";

$("#popup").dxPopup({
	title : '비밀번호 변경',
	visible : false,
	width : 350,
	height : 260,
	contentTemplate : function(contentElement) {
		$("<label style='position:absolute; width:100px; top:75px;'>기존 비밀번호</label>").appendTo(contentElement);
			$("<div style='position:absolute; width:200px; left:125px;' id='passwordOld' />")
		.appendTo(contentElement)
		.dxTextBox({
			maxLength: 20,
			mode: "password",
			value : "",
			valueChangeEvent: "keyup",//IE에서 onEnterKey 이벤트 동작하지않아 추가
			onValueChanged: function (e) {
				strPasswordOld = e.value;
			},
			onEnterKey: function(e) {
				pressEnterKey();
			}	
		}); 
		$("<label style='position:absolute; width:100px; top:117px;'>신규 비밀번호</label>").appendTo(contentElement);
			$("<div style='position:absolute; width:200px; left:125px; top:110px;' id='passwordNew' />")
		.appendTo(contentElement)
		.dxTextBox({
			maxLength: 20,
			mode: "password",
			value : "",
			valueChangeEvent: "keyup",//IE에서 onEnterKey 이벤트 동작하지않아 추가
			onValueChanged: function (e) {
				strPasswordNew = e.value;
			},
			onEnterKey: function(e) {
				pressEnterKey();
			}					
		});	
		$("<label style='position:absolute; width:100px; top:158px;'>비밀번호 확인</label>").appendTo(contentElement);
			$("<div style='position:absolute; width:200px; left:125px; top:152px;' id='passwordChk' />")
		.appendTo(contentElement)
		.dxTextBox({
			maxLength: 20,
			mode: "password",
			value : "",
			valueChangeEvent: "keyup",//IE에서 onEnterKey 이벤트 동작하지않아 추가
			onValueChanged: function (e) {
				strPasswordChk = e.value;
			},
			onEnterKey: function(e) {
				pressEnterKey();
			}					
		});	
			$("<div id='passwordBtn' style='position:absolute; top:200px; right:15px; '>")
			.appendTo(contentElement)
		.dxButton({
			text : "변경하기",
			onClick : function() {
				submitPassword();
			}
		});
	},
    onShown: function() {
    	$("#passwordOld").dxTextBox('instance').focus();
    },
	onHiding: function() {
		$("#passwordOld").dxTextBox({ value: "" });
		$("#passwordNew").dxTextBox({ value: "" });
		$("#passwordChk").dxTextBox({ value: "" });
	}
});

function submitPassword(){
	 var chkCnt = 0;
	 if(strPasswordNew.search(/[0-9]/g) != -1 ) chkCnt ++;
	 if(strPasswordNew.search(/[a-z]/ig)  != -1 ) chkCnt ++;
	 if(chkCnt < 2){ 
		DevExpress.ui.notify("변경할 비밀번호는 숫자와 영문을 혼용해야 합니다.", "warning");
		return;
	 }
	 
	//publicKey
    var rsa = new RSAKey();
    	rsa.setPublic("<%=modulus%>", "<%=exponent%>");

    var rsaOld = strPasswordOld;
    var rsaNew = strPasswordNew;
    var rsaChk = strPasswordChk;
	    
	$.ajax({
		url : '/MWTS/mwts/modifyPwPorc.do',
		data : {
				currentPw : rsa.encrypt(rsaOld),
				modifyPw : rsa.encrypt(rsaNew),
				modifyPwChk : rsa.encrypt(rsaChk)
				},
		success : function(result) {
			if (result.RET == "0") {
				$("#popup").dxPopup("instance").hide();
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

function pressEnterKey(){
	if(event.keyCode == 13)
		submitPassword();
}

function home(){
	addTab('HOME','/MWTS/mwts/home.do');
}

function addTab(title, url){
	if ($('#contentTab').tabs('exists', title)){
		$('#contentTab').tabs('select', title);
	} else {
		var tabCount = $('#contentTab > .tabs-header > .tabs-wrap > ul >li').length;
		if (tabCount > 10){
			alert('탭은 10개까지 열 수 있습니다.');
			return;
		}else{
			var content = '<iframe src="'+url+'"></iframe>';
			$('#contentTab').tabs('add',{
				fit:true,
				title:title,
				content:content,
				closable:true,
				iconCls:'icon-reload'
			});
		}		
	}
}

//탭 새로고침
$(document).on('click', '.icon-reload', function () {
    var tabIndex = $(this).closest('li').index(); 

    var p = $('#contentTab').tabs('getTab', tabIndex); 
    	p.panel('refresh'); 
});
//헤더 메뉴와 버튼 겹침 방지
$(window).resize(function() {
	var topContainer = $('div.topContainer').width();
	
	if(topContainer > 1350){
		$('div.topContainer').find('.topBtn').css({"display":""});
	}else{
		$('div.topContainer').find('.topBtn').css({"display":"none"});
	}
});
</script>