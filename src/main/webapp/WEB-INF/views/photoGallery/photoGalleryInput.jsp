<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>photoGalleryInput.jsp</title>
  <%@ include file = "/WEB-INF/views/include/bs4.jsp" %>
  <script src="${ctp}/ckeditor/ckeditor.js"></script>
  <script>
    'use strict';
    
    function fCheck() {
    	let title = document.getElementById("title").value;
    	// ckeditor 입력 내용 받기 = CKEDITOR.instances.textarea태그의id.getData();
    	if(CKEDITOR.instances.content.getData() == '' || CKEDITOR.instances.content.getData().length == 0) {
  	    alert("사진을 등록해주세요.");
  	    $("#content").focus();
    	}
    	else if(title.trim() == "") {
    		alert('제목을 입력하세요');
    		$("#title").focus();
    	}
    	else {
    		myform.submit();
    	}
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>사진파일 업로드</h2>
  <hr/>
  <form name="myform" method="post">
    <div class="input-group mb-2">
      <div class="input-group-prepend input-group-text">분 류</div>
      <select name="part" id="part" class="form-control">
        <option value="풍경" selected>풍경</option>
        <option value="인물">인물</option>
        <option value="음식">음식</option>
        <option value="여행">여행</option>
        <option value="학습">학습</option>
        <option value="사물">사물</option>
        <option value="기타">기타</option>
      </select>
    </div>
    <div class="input-group mb-2">
      <div class="input-group-prepend input-group-text">제 목</div>
      <input type="text" name="title" id="title" class="form-control"/>
    </div>
    <div class="mb-2">
		  <textarea rows="6" name="content" id="content" required></textarea>
      <script>
		    CKEDITOR.replace("content",{
		    	height:600,
		    	filebrowserUploadUrl: "${ctp}/imageUpload",
		    	uploadUrl:"${ctp}/imageUpload"
		    });
		  </script>
    </div>
    <div class="row">
    	<div class="col"><input type="button" value="파일전송" onclick="fCheck()" class="btn btn-success"/></div>
    	<div class="col text-right"><input type="button" value="돌아가기" onclick="location.href='photoGallery';" class="btn btn-warning"/></div>
    </div>
    <input type="hidden" name="mid" value="${sMid}" />
    <input type="hidden" name="hostIp" value="${pageContext.request.remoteAddr}" />
  </form>
  <hr/>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
<p><br/></p>
</body>
</html>