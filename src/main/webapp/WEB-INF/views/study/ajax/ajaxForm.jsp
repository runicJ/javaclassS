<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ajaxForm.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
  	'use strict';
  	
    function ajaxTest1(idx) {
    	//location.href = "${ctp}/study/ajax/ajaxTest1?idx="+idx;
    	$.ajax({
    		url  : "${ctp}/study/ajax/ajaxTest1",
    		type : "post",
    		data : {idx : idx},
    		success:function(res) {
    			$("#demo1").html(res);
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
  	
    function ajaxTest2(str) {
    	//location.href = "${ctp}/study/ajax/ajaxTest1?idx="+idx;
    	$.ajax({
    		url  : "${ctp}/study/ajax/ajaxTest2",
    		type : "post",
    		// contentType: "application/x-www-form-urlencoded; charset=UTF-8",
    		// headers : {Content-Type: "application/json"},
    		data : {str : str},
    		success:function(res) {
    			$("#demo2").html(res);
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
	<h2>AJAX 연습</h2>
	<hr>
	<div>기본(int -> String -> int -> String) : <br>  <!-- (int(출발) -> String(웹) -> int(받을때) -> String(처리하고 웹으로 돌아옴)) -->
		<a href="javascript:ajaxTest1(10)" class="btn btn-success mr-2 mb-2">값전달1</a>  <!-- 10은 숫자지만 실제론 문자로 감 -->
		: <span id="demo1"></span>
	</div>
	<div>기본(String) : <br>
		<a href="javascript:ajaxTest2('안녕')" class="btn btn-primary mr-2 mb-2">값전달2</a>
		: <span id="demo2"></span>
	</div>
	<hr>
	<div>응용(배열) - 시(도)/구(시,군,동) 출력<br>
		<a href="${ctp}/study/ajax/ajaxTest3_1" class="btn btn-info mr-2">String배열</a>
		<a href="${ctp}/study/ajax/ajaxTest3_2" class="btn btn-info mr-2">ArrayList</a>
		<a href="${ctp}/study/ajax/ajaxTest3_3" class="btn btn-info mr-2">Map형식</a>
		<a href="${ctp}/study/ajax/ajaxTest3_4" class="btn btn-info mr-2">DB 성명으로 자료검색</a>
	</div>
</div>
<p><br><p>
  <jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>