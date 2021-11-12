<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" />
<title>MODERNWAVE</title>

<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
</head>
</body>
</html>
<script type="text/javascript">
//var userTheme = "<c:out value="${sessionScope.SESSION_USER.userTheme}" />";
var userTheme = "";

$(function(){
	$.ajax({
		url : "/MWTS/mwts/selectTheme.do",
		data : {},
		cache : false, //IE 브라우저 캐싱방지
		async : false, // ajax 순차적으로 실행
		dataType : "json",
	}).done(function(data) {
		userTheme = data.dataList[0].user_theme;
		
		ThemeChange(userTheme);	
	});
	
	var themeData = [{
	    id: "generic.light",
	    name: "Light",
	    visible: true
	}, {
	    id: "generic.dark",
	    name: "Dark",
	    visible: true
	}, {
	    id: "generic.carmine",
	    name: "Carmine",
	    visible: true
	}, {
	    id: "generic.darkmoon",
	    name: "DarkMoon",
	    visible: true
	}, {
	    id: "generic.softblue",
	    name: "SoftBlue",
	    visible: true
	}, {
	    id: "generic.darkviolet",
	    name: "DarkViolet",
	    visible: true
	}, {
	    id: "generic.greenmist",
	    name: "GreenMist",
	    visible: true
	}];
	
	$("#theme").dxSelectBox({
		dataSource: themeData,
        placeholder: "테마 선택",
        FieldEditEnabled: false,
        displayExpr: "name",
        valueExpr: "id",
        onItemClick: function (data) {
        	userTheme = data.itemData.id;
            	
            	//테마 적용
            	ThemeChange(userTheme);
            	//테마 저장
            	ThemeSave(userTheme);
        }
    });
});

function ThemeChange(f_userTheme){
	DevExpress.ui.themes.current(f_userTheme);
}

function ThemeSave(f_userTheme){
	$.ajax({
		url : "/MWTS/mwts/updateTheme.do",
		data : {
			theme : f_userTheme,
			userId : userId
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