<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    <%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
  String filename = "BlackListReport";
  //Date date = new Date();
  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");  
  String today = simpleDateFormat.format(Calendar.getInstance().getTime());
  
  
  
  
  
  response.setHeader("Content-Disposition","attachment;filename="+filename+today+".xls");
  response.setHeader("Content-Description", "JSP Generated Data");
  response.setHeader("Cache-Control","no-store"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); 
%>
<html>
	<head>
	</head>
	<body>
		<table border="1">
			<tr>
				<td style="background-color:#E3EFFF;">���� �ٿ�ε� �׽�Ʈ ��� 1</td>
				<td style="background-color:#E3EFFF;">���� �ٿ�ε� �׽�Ʈ ��� 2</td>
				<td style="background-color:#E3EFFF;">���� �ٿ�ε� �׽�Ʈ ��� 3</td>
				<td style="background-color:#E3EFFF;">���� �ٿ�ε� �׽�Ʈ ��� 4</td>
				<td style="background-color:#E3EFFF;">���� �ٿ�ε� �׽�Ʈ ��� 5</td>
			</tr>
			<tr>
				<td style="background-color: #ffffff">���� �ٿ�ε� �׽�Ʈ ��� 1</td>
				<td style="background-color: #ffffff">���� �ٿ�ε� �׽�Ʈ ��� 2</td>
				<td style="background-color: #ffffff">���� �ٿ�ε� �׽�Ʈ ��� 3</td>
				<td style="background-color: #ffffff">���� �ٿ�ε� �׽�Ʈ ��� 4</td>
				<td style="background-color: #ffffff">���� �ٿ�ε� �׽�Ʈ ��� 5</td>
			</tr>
		</table>
	</body>
</html>