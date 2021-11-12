<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- excelUpload.jsp -->

 <script type="text/javascript">
 function goUploadExcel(){
     var uploadForm = document.uploadForm;

     if( uploadForm.file1.value == "" ) {
         alert( "파일을 업로드해주세요." );
         return false;
     } else if( !checkFileType(uploadForm.file1.value) ) {
         alert( "엑셀파일만 업로드 해주세요." );
         return false;
     }

     if( confirm("업로드 하시겠습니까?") ) {
         uploadForm.action = "/excelUpload.do";
         uploadForm.submit();
     }
 }

 function checkFileType( filePath ) {
     var fileFormat = filePath.toLowerCase();

     if( fileFormat.indexOf(".xls") > -1 ) return true;
     else return false;
 }
 </script>


 <form name="uploadForm" action="" method="post" onSubmit="return false;" encType="multipart/form-data">
     <input type="" name="file1" />
     <button type="button" onclick="goUploadExcel();" onkeypress="this.onclick();"></button>
 </form>