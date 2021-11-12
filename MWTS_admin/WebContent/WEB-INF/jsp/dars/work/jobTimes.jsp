<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<title>근무시간관리</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	
<%@include file="../../mwts/layout/topJS.jsp"%>
</head>

<body class="dx-viewport">
    <div class="demo-container" style="position: absolute; height: 100%; width: 100%">
    	<!-- 근무시간 조회 -->
		<div id="head_Grid" style="position: absolute; height: 80px; width: 100%; ">
			<h4 style="position: absolute; font-size: 15px; font-weight: bold">근무시간 조회</h4><br />
			<hr style="border: thin solid #e6e3dd;"/><br />		
			<span style="position: absolute; margin-top:-15px">사이트</span>
			<div id="siteBox" style="position: absolute; display: inline-block; width: 300px; left: 100px; margin-top:-15px"></div>
		</div>
		<!-- 그리드 -->
		<div id="select_Grid" style="position: absolute; height: calc(100% - 200px); width: 100%; top: 80px;"></div>
		<!-- 근무시간 복사 -->
		<div id="footer_Grid" style="position: absolute; height: 80px; width: 100%; bottom: 20px;">
				<h4 style="position: absolute; font-size: 15px; font-weight: bold">근무시간 복사</h4><br />
				<hr style="border: thin solid #e6e3dd;"/><br />
				<span style="position: absolute; margin-top:-15px">사이트</span>
				<div id="siteCopyBox" style="position: absolute; display: inline-block; width: 300px; left: 100px; margin-top:-15px"></div>
				
				<div id="copyBtn" style="position: absolute; display: inline-block; width: 110px; left: 430px; margin-top:-15px;"></div>
		</div>
    </div>
</body>
</html>

<script type="text/javascript">
$("#select_Grid").dxDataGrid({
	dataSource : {},
	keyExpr : ["site", "week_day"],
	hoverStateEnabled : true,
	columnAutoWidth : false,
	allowResizing: true,
	showBorders: true,
    editing: {
        mode: "batch",
        allowUpdating: true
    }, 
	columns : [{
		caption : "요일",
		dataField : "WD",
		alignment: "center",
		allowEditing : false
	},{
        caption: "근무시간1",
        alignment: "center",
        columns: [{
			dataField : "F1",
			caption : "시작시간",
			alignment: "center"
		}, {
			dataField : "T1",
			caption : "종료시간",
			alignment: "center"
		}]
    },{
        caption: "근무시간2",
        alignment: "center",
        columns: [{
			dataField : "F2",
			caption : "시작시간",
			alignment: "center"
		}, {
			dataField : "T2",
			caption : "종료시간",
			alignment: "center"
		}]
    },{
        caption: "점심시간",
        alignment: "center",
        columns: [{
			dataField : "F3",
			caption : "시작시간",
			alignment: "center"
		}, {
			dataField : "T3",
			caption : "종료시간",
			alignment: "center"
		}]
    }],
	onKeyDown: function (e) {
	    if (e.event.key == "Enter") {
	    	//IE에서 엔터키 입력 시 포커스 유지
	        $("#select_Grid").dxDataGrid("instance").focus($(".dx-focused"));
	    }
	},     
    onEditorPreparing: function(e) {
        if (e.parentType == 'dataRow' && e.dataField == 'F1' || e.parentType == 'dataRow' && e.dataField == 'T1' || e.parentType == 'dataRow' && e.dataField == 'F2' || e.parentType == 'dataRow' && e.dataField == 'T2' || e.parentType == 'dataRow' && e.dataField == 'F3' || e.parentType == 'dataRow' && e.dataField == 'T3') {
            e.editorOptions.maxLength = 5;
        }
    },
	onRowUpdating: function(e){
		var strSite = e.key.site;
		var strWeekday = e.key.week_day;
		//var strDayType = "";
		var strF1FromTime = "";
		var strT1ToTime = "";
		var strF2FromTime = "";
		var strT2ToTime = "";
		var strF3FromTime = "";
		var strT3ToTime = "";
		
		//근무시간1
		if(e.newData.F1 == undefined){
			strF1FromTime = e.oldData.F1;
		}else{
			strF1FromTime = e.newData.F1;
		}
		if(e.newData.T1 == undefined){
			strT1ToTime = e.oldData.T1;
		}else{
			strT1ToTime = e.newData.T1;
		}		
		
		//근무시간2
		if(e.newData.F2 == undefined){
			strF2FromTime = e.oldData.F2;
		}else{
			strF2FromTime = e.newData.F2;
		}
		if(e.newData.T2 == undefined){
			strT2ToTime = e.oldData.T2;
		}else{
			strT2ToTime = e.newData.T2;
		}	
		
		//점심시간
		if(e.newData.F3 == undefined){
			strF3FromTime = e.oldData.F3;
		}else{
			strF3FromTime = e.newData.F3;
		}
		if(e.newData.T3 == undefined){
			strT3ToTime = e.oldData.T3;
		}else{
			strT3ToTime = e.newData.T3;
		}					
		

		jobTimesUpdate(strSite, strWeekday, strF1FromTime, strT1ToTime, strF2FromTime, strT2ToTime, strF3FromTime, strT3ToTime);
	}
});	

var selSite =""; 
var selText ="";
var selCopySite ="";
var selCopyText ="";

//코드마스터 조회
$.ajax({
	url : "/MWTS/dars/selectCodeMst.do",
	type : "POST",
	data : {
		f_Category : "site"
	},
	cache : false, //IE 브라우저 캐싱방지
	async : false, // ajax 순차적으로 실행
}).done(function(data) {
	var siteData = data.siteList;
	
	//사이트
	$("#siteBox").dxSelectBox({
		dataSource: siteData,
	    displayExpr: "sdesc",
	    valueExpr: "code",
	    //value: siteData[0].code,
	    value: siteData[0] ? siteData[0].code : "",
	    onInitialized: function (e) {
	    	selSite = e.component._options.value;
	    	selText = siteData[0] ? siteData[0].sdesc : "";
	    	
	    	if(selSite){
	    		jobTimesSelect(selSite);
	    	}
	    },
	    onItemClick: function (e) {
	    	selSite = $("#siteBox").dxSelectBox('instance').option('value');
	    	selText = e.component._options.displayValue;
	    	
	    	if(selSite){
	    		jobTimesSelect(selSite);
	    	}
	    }
	});

	//사이트 복사
	$("#siteCopyBox").dxSelectBox({
		dataSource: siteData,
	    displayExpr: "sdesc",
	    valueExpr: "code",
	    //value: siteData[0].code,
	    value: siteData[0] ? siteData[0].code : "",
	    onInitialized: function (e) {
	    	selCopySite = e.component._options.value;
	    	selCopyText = siteData[0] ? siteData[0].sdesc : "";
	    },
	    onItemClick: function (e) {
	    	selCopySite = $("#siteCopyBox").dxSelectBox('instance').option('value');
	    	selCopyText = e.component._options.displayValue;
	    }
	});
});

//복사 버튼
$("#copyBtn").dxButton({
    text: "복사",
    icon: '../images/copy.png',
    type: "normal",
    onClick: function(e) {    
    	var result = DevExpress.ui.dialog.confirm("<b>"+ selText + "</b> 을<br><b>"+ selCopyText +"</b> (으)로 복사하시겠습니까?");
    	$(".dx-popup-title.dx-toolbar").css("display", "none");//popup title 숨기기(내용없음)
    	
        result.done(function (dialogResult) {
        	if (dialogResult) {
            	jobTimesCopy(selSite, selCopySite);
        	}
        });  
    }
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

//근무시간관리 조회
function jobTimesSelect(f_Site){
	$.ajax({
		url : "/MWTS/dars/jobTimesSelect.do",
		type : "POST",
		data : {
			f_Site : f_Site
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
	}).done(function(data) {
		var data = data.dataList;
		
		$("#select_Grid").dxDataGrid({ dataSource: data });
	});
}
//근무시간관리 업데이트
function jobTimesUpdate(f_Site, f_Weekday, f_F1FromTime, f_T1ToTime, f_F2FromTime, f_T2ToTime, f_F3FromTime, f_T3ToTime){
	$.ajax({
		url : "/MWTS/dars/updateJobTimes.do",
		data : {
			f_Site : f_Site,
			f_Weekday : f_Weekday,
			f_F1FromTime : f_F1FromTime,
			f_T1ToTime : f_T1ToTime,
			f_F2FromTime : f_F2FromTime,
			f_T2ToTime : f_T2ToTime,
			f_F3FromTime : f_F3FromTime,
			f_T3ToTime : f_T3ToTime
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
		success : function(result) {
			if(result.RET == "0"){
				if(selSite){
					jobTimesSelect(selSite);
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
//근무시간관리 복사
function jobTimesCopy(f_OriginSite, f_CopySite){
	$.ajax({
		url : "/MWTS/dars/copyJobTimes.do",
		data : {
			f_OriginSite : f_OriginSite,
			f_CopySite : f_CopySite
		},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
		success : function(result) {
			if(result.RET == "0"){
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