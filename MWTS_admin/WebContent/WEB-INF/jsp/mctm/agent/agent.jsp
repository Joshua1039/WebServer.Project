<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" />
<title>MCTM 상담사 조회</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<style type="text/css">
/*그리드 색상 변경 */
#agentContainer > .dx-datagrid {
	color: #dae3e6;
	background-color: #29323b;
	font-family: Nanum Gothic;
	font-weight: 600;
}
#agentContainer > .dx-datagrid > .dx-datagrid-headers {
	color: #e8eaeb;
	background-color: #29323b;
	font-family: Nanum Gothic;
	font-weight: 600;
	line-height: 35px;
	border-bottom: 1.3px solid #e8eaeb;
}
.boldText {
	font-weight: 600;
	color: #ffca00; /*#eebe06*/
}
/*필터링 아이콘 변경*/
.dx-header-filter {
    color: #e8eaeb !important;
    cursor: pointer;
}
.dx-header-filter-empty {
    color: #e8eaeb !important;
}
.dx-datagrid .dx-column-indicators {
	margin-top: 10px;
}
</style>
<%
	String gubun = (String)request.getAttribute("GUBUN");
	//response.getWriter().print(gubun);
%>
</head>
</html>
<script type="text/javascript">
var selectURL = "";
if ("<%=gubun%>" == "ACR"){
	selectURL = "/MWTS/acr/selectRealTimeVAgent.do";
}else if("<%=gubun%>" == "MCTM"){
	selectURL = "/MWTS/mctm/selectRealTimeVAgent.do";
}

//상담사 조회
$("#agentContainer").dxDataGrid({
	dataSource : {
        store: new DevExpress.data.CustomStore({
        	load: function (loadOptions) {
        		var d = $.Deferred();
        		
	  	       	  $.getJSON(selectURL, {
	  	   		    format: "json",
	  	 		  }).done(function(data) {
		  	 			if (data.RET == "0") {
		  	 				d.resolve(data.dataList);
		  	 			}else{
		  	 				clearInterval(agentUpdate);
		  	 				$("#agentContainer").dxDataGrid("option", "dataSource.store", []);
		  	 				DevExpress.ui.notify(data.RET_MSG, "warning", 6000000);
		  	 			}
	  	 		  })
	  	 		  
	  	 		return d.promise();
  	   		}
        }) 
	},
	cacheEnabled : false,
	headerFilter : {
		visible : true
	},
    paging : {
    	enabled: false
    },
    loadPanel: {
       enabled: false
    },
    showRowLines: false,
    showColumnLines: false,
	columns : [{
		caption : "아이디",
		dataField : "AGENT_ID"
	}, {
		caption : "이름",
		dataField : "AGENT_NAME"
	}, {
		caption : "상태",
		dataField : "AGENT_STATUS"
	}, {
		caption : "시간",
		dataField : "STATUS_TIME"
	}],
	onRowPrepared: function (e) {
		e.rowElement.css({ height: 50});
		
	    if(e.rowType === 'data') {
	        if(e.data.AGENT_STATUS === '로그아웃'){
	        	e.rowElement.css({"color":"#99a1a8"});
	        }else if(e.data.AGENT_STATUS === '수신대기'){
	        	e.rowElement.css({"color":"#e0be6e"});
	        }else if(e.data.AGENT_STATUS === '벨울림'){
	        	e.rowElement.css({"color":"#90bfdc"});
	        }else if(e.data.AGENT_STATUS === '발신'){
	        	e.rowElement.css({"color":"#a8a3ef"});
	        }else if(e.data.AGENT_STATUS === '통화중'){
	        	e.rowElement.css({"color":"#d07979"});
	        }else if(e.data.AGENT_STATUS === '업무처리'){
	        	e.rowElement.css({"color":"#e6e88d"});
	        }else if(e.data.AGENT_STATUS === '휴식'){
	        	e.rowElement.css({"color":"#92dab3"});
	        }else if(e.data.AGENT_STATUS === 'DND'){
	        	e.rowElement.css({"color":"#61dbe2"});
	        }else if(e.data.AGENT_STATUS === '교육'){
	        	e.rowElement.css({"color":"#eca8d7"});
	        }else{
	        	e.rowElement.css({"color":"#99a1a8"});
	        }
	      //e.rowElement.addClass('boldText');
	    }
	}
}).dxDataGrid("instance");

//자동조회
/* agentUpdate = setInterval(function() {
	$('#agentContainer').dxDataGrid('instance').getDataSource().reload();
}, 10000); */

function tabResize(f_width){
    //jquery easyUI tabs 헤더 크기조정
	$('.leftContainer').find('.tabs-container').css({"width":f_width});
	$('.tabs-container').find('.tabs-header').css({"width":f_width});
	$('.tabs-header').find('.tabs-wrap').css({"width":f_width});
}

//사이드바 너비 조정
 $(function() {
	var duration = 400;	 
    var $rightSide = $('.rightContainer');
    
    var $rightSideButton = $rightSide.find('.sidebar_btn').on('click', function(){
        $rightSide.toggleClass('open');
        if($rightSide.hasClass('open')){
            //오른쪽 사이드바 열기
            $rightSide.stop(true).animate({right: '0px', width: '434px'}, duration, 'easeOutBack');
            //왼쪽 컨텐츠 축소
            var leftWidth = parseInt($('div.bottomContainer').width() - 434);
            $('.leftContainer').stop(true).animate({width:+leftWidth+'px'}, duration, 'easeInBack');
            
            tabResize(leftWidth);
        }else{
            //오른쪽 사이드바 숨기기
        	$rightSide.stop(true).animate({right: '0px', width: '50px'}, duration, 'easeInBack');
            //왼쪽 컨텐츠 확장
            var leftWidth = parseInt($('div.bottomContainer').width() - 50);
            $('.leftContainer').stop(true).animate({width:+leftWidth+'px'}, duration, 'easeInBack');
            
            tabResize(leftWidth);
        };
    });
});  

$(window).resize(function() {
	if($('.rightContainer').hasClass('open')){
		$('.leftContainer').css({"width":"calc(100% - 434px)"});
	}else{
		$('.leftContainer').css({"width":"calc(100% - 50px)"});
	}
	
	tabResize($('div.leftContainer').width());
});
</script>