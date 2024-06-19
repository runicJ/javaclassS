<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ajaxTest3_4</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
  	'use strict';
  	
  	function userCheck() {
  		$.ajax({
  			url : "${ctp}/study/ajax/ajaxTest3_4",
  			type : "post",
  			success:function(vos) {
  				let str = '<option>유저선택</option>';
  				for (let i=0; i<res.length; i++) {
  					str += '<option>'+res[i].mid+'</option>'
  				}
  				$("#mid").html(str);

  			},
  			error : function(){
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
	<h2>ajaxTest3_4.jsp()</h2>
	<hr>
	<input type="button" value="유저정보 조회하기" onclick="userCheck()" class="btn btn-info btn-sm ml-3"/>
	<hr>
	<form>
		<h3>유저 아이디를 선택하세요</h3>
		<select name="mid" id="mid" onchange="userCheck()">
			<option value="">유저선택</option>
		</select>
		<input type="button" value="돌아가기" onclick="location.href='ajaxForm';" class="btn btn-warning btn-sm ml-2"/>
	</form>
	<div id="demo"></div>
</div>
<p><br><p>
  <jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>