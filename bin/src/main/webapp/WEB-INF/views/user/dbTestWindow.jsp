<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>dbTestWindow.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
    'use strict';
    
    function wClose() {
    	opener.window.myform.mid.value = '${mid}';
    	opener.window.myform.name.focus();
    	
    	window.close();
    }
    
    function idCheck() {
    	let mid = childForm.mid.value;
    	if(mid.trim() == "") {
    		alert("검색할 아이디를 입력하세요");
    		childForm.mid.focus();
    		return false;
    	}
    	childForm.submit();
    }
  </script>
</head>
<body>
<p><br/></p>
<div class="container">
  <h3>아이디 중복체크</h3>
  <hr/>
  <div class="text-center">
    <c:if test="${idCheck == 'OK'}"><font color="red"><b>${mid}는 사용 가능한 아이디 입니다.</b></font></c:if>
    <c:if test="${idCheck != 'OK'}">
    ${mid}는 이미 사용중인 아이디입니다. 다른 아이디로 검색해 주세요!
      <form name="childForm" method="get" action="${ctp}/dbTest/dbTestWindow">
        <p>아이디 : 
          <input type="text" name="mid" />
          <input type="button" value="아이디검색" onclick="idCheck()" />
        </p>
      </form>
    </c:if>
  </div>
  <hr/>
  <div class="text-center"><input type="button" value="창닫기" onclick="wClose()" class="btn btn-success"/></div>
</div>
<p><br/></p>
</body>
</html>