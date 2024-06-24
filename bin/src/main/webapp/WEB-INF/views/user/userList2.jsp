<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>userList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
  	'use strict';
  	
    $(document).ready(function(){
    	$("#btnShow").show();
    	$("#btnHide").hide();
    	$("#userInput").hide();
    	
    	$("#btnShow").click(function(){
    		$("#userInput").show();
	    	$("#btnShow").hide();
	    	$("#btnHide").show();
    	});
    	
    	$("#btnHide").click(function(){
    		$("#userInput").hide();
	    	$("#btnShow").show();
	    	$("#btnHide").hide();
    	});
    });
  	
  	function deleteCheck(idx) {
  		let ans = confirm("선택하신 회원을 삭제처리 하시겠습니까?");
  		if(!ans) return false;
  		
  		location.href="${ctp}/user/userDelete?idx="+idx;
  	}
  	
   	function userSearch() {
  		let keyword = $("#keyword").val();
  		
  		if(keyword.trim() == "") {
  			alert("검색어를 입력하세요!");
  			return false;
  		}
  		
  		location.href="${ctp}/user/userSearchOk?keyword="+keyword;
  	}
  </script>
</head>
<body>
<p><br><p>
<div class="container text-center">
	<h2 class="mb-5" style="font-weight:bold;font-style:italic;">회원 리스트</h2>
	<hr>
	<input type="button" value="회원가입 창 보이기" id="btnShow" class="btn btn-primary btn-sm"/>
	<input type="button" value="회원가입 창 숨기기" id="btnHide" class="btn btn-secondary btn-sm"/>
<p><br><p>
	<div id="userInput">
		<form name="myform" method="post" action="${ctp}/user/userInputOk">
			<table class="table table-bordered">
				<tr>
					<th>아이디</th>
					<td><input type="text" name="mid" value="atom1234" class="form-control"></td>
				</tr>
				<tr>
					<th>이름</th>
					<td><input type="text" name="name" value="아톰" class="form-control"></td>
				</tr>
				<tr>
					<th>나이</th>
					<td><input type="number" name="age" value="22" class="form-control"></td>
				</tr>
				<tr>
					<th>주소</th>
					<td><input type="text" name="address" value="경기" class="form-control"></td>
				</tr>
				<tr>
					<td colspan="2" class="text-center">
						<input type="submit" value="회원가입" class="btn btn-success">
						<input type="reset" value="다시입력" class="btn btn-warning">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<table class="table table-hover">
		<tr class="table-warning">
			<th>번호</th>
			<th>아이디</th>
			<th>이름</th>
			<th>나이</th>
			<th>주소</th>
			<th>비고</th>
		</tr>
		<c:forEach var="vo" items="${vos}" varStatus="st">
		<tr>
			<td>${vo.idx}</td>
			<td>${vo.mid}</td>
			<td>${vo.name}</td>
			<td>${vo.age}</td>
			<td>${vo.address}</td>
			<td>
				<a href="javascript:deleteCheck(${vo.idx})" class="btn btn-danger btn-sm">삭제</a>  <!-- 배찌는 무조건 a태그 -->
			</td>
		</tr>
		</c:forEach>
		<tr><td colspan="6" class="m-0 p-0"></td></tr>
	</table>
	<div>
		<input type="text" id="keyword" name="keyword" placeholder="검색할 회원을 입력하세요!" style="width:200px;" /><input type="button" value="검색" onclick="userSearch()" class="btn btn-info">
		<a href="${ctp}/user/userList" class="btn btn-light">전체보기</a>
	</div>
	<hr>
	<div><a href="${ctp}/" class="btn btn-dark btn-lg" style="font-weight:bold;color:#fef9ee;">돌아가기</a></div>
</div>
<p><br><p>
</body>
</html>