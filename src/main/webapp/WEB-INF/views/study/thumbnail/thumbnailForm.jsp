<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>thumbnailForm.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
  	'use strict';
  	
  	function fCheck() {
  		let file = document.getElementById("file").value;
    	let ext = file.substring(file.lastIndexOf(".")+1).toLowerCase();
    	let maxSize = 1024 * 1024 * 10;
    	
    	if(file.trim() == "") {
    		alert("업로드할 파일을 선택하세요.");
    		return false;
    	}
    	
    	let fileSize = document.getElementById("file").files[0].size;
    	if(ext != 'jpg' && ext != 'gif' && ext != 'png' && ext != 'jpeg') {
    		alert("업로드 가능한 파일은 'jpg/gif/png/jpeg'만 가능합니다.");
    		return false;
    	}
    	
    	if(fileSize > maxSize) {
    		alert("업로드 파일의 최대용량은 10MByte입니다.");
    		return false
    	}
    	
  		//myform.submit(); 이렇게 안하고 ajax 처리
  		
  		let formData = new FormData();  // 생성해야함
  		formData.append("file", document.getElementById("file").files[0]);  // 변수명, 한장에 대한 파일(파일명x)
  		
  		$.ajax({
  			url : "${ctp}/study/thumbnail/thumbnailForm",
  			type : "post",
  			data : formData,	// 한글코드나 그림이라는 것에 대한 정보를 넘겨줘야함
  			processData : false,  // 검증하지말고
  			contentType : false,  // 무시하고 바이너리로 넘김
  			success:function(res) {
  				if(res != "") {
  					alert("썸네일 등록 완료~");
						let str = '<p>썸네일 이미지 :<br><img src="${ctp}/thumbnail/s_'+res+'" /></p>';
						str += '<p>원본이미지 :<br><img src="${ctp}/thumbnail/'+res+'" /></p>';
						$("#demo").html(str);
  				}
  				else alert("썸네일 생성 실패!");
  			},
  			error : function() {
  				alert("전송오류!");
  			}
  		});
  	}
  </script>
</head>
<body>
  <jsp:include page="/WEB-INF/views/include/nav.jsp" />
  <jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br><p>
<div class="container">
	<h2>썸네일 연습</h2>
	<form name="myform" method="post" enctype="multipart/form-data">
		<p>파일명 :
			<input type="file" name="file" id="file" class="form-control-file border" accept=".jpg,.gif,.png,.jpeg" /> 
		</p>
		<p>
			<input type="button" value="썸네일만들기" onclick="fCheck()" class="btn btn-success"/>
			<input type="reset" value="다시선택" class="btn btn-warning"/>
			<input type="button" value="썸네일리스트로" onclick="location.href='#';" class="btn btn-primary"/>
		</p>
	</form>
	<br>
	<div id="demo"></div>
</div>
<p><br><p>
  <jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>