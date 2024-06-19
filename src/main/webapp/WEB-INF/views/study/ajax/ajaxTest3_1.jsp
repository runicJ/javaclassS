<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ajaxTest3_1</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
  	'use strict';
  	
  	function dodoCheck() {
  		let dodo = document.getElementById("dodo").value;
  		if(dodo.trim() == "") {
  			alert("지역을 선택하세요");
  			return false;
  		}
  		
  		$.ajax({
  			url : "${ctp}/study/ajax/ajaxTest3_1",
  			type : "post",
  			data : {dodo : dodo},
  			success:function(res) {
  				//console.log(res);
  				let str = '<option>도시선택</option>';
  				for (let i=0; i<res.length; i++) {
  					if(res[i] == null) break;
  					str += '<option>'+res[i]+'</option>'
  				}
  				$("#city").html(str);
  			},
  			error : function(){
  				alert("전송오류!");
  			}
  		});
  	}
  	
		function fCheck() {
			let dodo = $("#dodo").val();
			let city = $("#city").val();
			
			if(dodo === "" || city == "") {
				alert("지역을 선택 후 눌러주세요");
				return false;
			}
			let str = "선택하신 지역은? " + dodo + "/" + city;
			$("#demo").html(str);
		}
  </script>
</head>
<body>
  <jsp:include page="/WEB-INF/views/include/nav.jsp" />
  <jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br><p>
<div class="container">
	<h2>ajaxTest3_1.jsp(문자 배열처리)</h2>
	<hr>
	<form>
		<h3>도시를 선택하세요</h3>
		<select name="dodo" id="dodo" onchange="dodoCheck()">
			<option value="">지역선택</option>
			<option>서울</option>
			<option>경기</option>
			<option>충북</option>
			<option>충남</option>
		</select>
		<select name="city" id="city">
			<option>도시선택</option>
		</select>
		<input type="button" value="선택" onclick="fCheck()" class="btn btn-info btn-sm ml-3"/>
		<input type="button" value="돌아가기" onclick="location.href='ajaxForm';" class="btn btn-warning btn-sm ml-2"/>
	</form>
	<div id="demo"></div>
</div>
<p><br><p>
  <jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>