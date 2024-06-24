<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<%
	// 로그인창에 아이디 체크 유무에 대한 처리
	// 쿠키를 검색해서 cMid가 있을때 가져와서 아이디입력창에 뿌릴수 있게 한다.
	Cookie[] cookies = request.getCookies();

	if(cookies != null) {
		for(int i=0; i<cookies.length; i++) {
			if(cookies[i].getName().equals("cMid")) {
				pageContext.setAttribute("mid", cookies[i].getValue());
				break;
			}
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>memberLogin.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
  	'use strict';
  	
  	$(function() {
  		$("#searchPassword").hide();
  	});
  	
  	// 비밀번호 찾기
  	function pwdSearch() {
  		$("#searchPassword").show();
  	}
  	
  	// 새 비밀번호 등록 시켜주기
  	function newPassword() {
  		let mid = $("#midSearch").val().trim();
  		let email = $("#emailSearch").val().trim();
  		if(mid == "" || email == "") {
  			alert("가입시 등록한 아이디와 이메일을 입력하세요!");
  			$("#midSearch").focus();
  			return false;
  		}
  		
  		$.ajax({
  			url : "${ctp}/member/memberNewPassword",
  			type : "post",
  			data : {
  				mid : mid,
  				email : email
  			},
  			success:function(res) {
  				if(res != "0") alert("새로운 비밀번호가 회원님 메일로 발송 되었습니다.\n이메일을 확인하세요.");
  				else alert("등록하신 정보가 일치하지 않습니다. 회원 정보를 확인해 주세요.");
  			},
  			error: function() {
  				alert("전송오류!");
  			}
  		});
  	}
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <form name="myform" method="post" action="${ctp}member/memberLoginOk">
    <table class="table table-bordered text-center">
      <tr>
        <td colspan="2"><font size="5">로 그 인</font></td>
      </tr>
      <tr>
        <th>아이디</th>
        <td><input type="text" name="mid" value="${mid}" autofocus required class="form-control"/></td>
      </tr>
      <tr>
        <th>비밀번호</th>
        <td><input type="password" name="pwd" value="1234" required class="form-control"/></td>
      </tr>
      <tr>
        <td colspan="2">
          <input type="submit" value="로그인" class="btn btn-success mr-2"/>
          <input type="reset" value="다시입력" class="btn btn-warning mr-2"/>
          <input type="button" value="회원가입" onclick="location.href='${ctp}/member/memberJoin';" class="btn btn-primary mr-4"/>
	    		<input type="checkbox" name="idSave" checked /> 아이디 저장
        </td>
      </tr>
    	<tr>
    		<td colspan="2" class="text-center">
    			<input type="checkbox" name="idSave" checked /> 아이디 저장 &nbsp;&nbsp;&nbsp;
    			[<a href="javascript:midSearch()">아이디 찾기</a>] |
    			[<a href="javascript:pwdSearch()">비밀번호 찾기</a>]
    		</td>
    	</tr>
    </table>
  </form>
  <div id="searchPassword">
  	<hr>
  	<table class="table table-borderd p-0 text-center">
  		<tr>
  			<td colspan="2" class="text-center">
  				<font size="4"><b>비밀번호 찾기</b></font>
  				(가입시 입력한 아이디와 이메일을 입력하세요)
  			</td>
  		</tr>
  		<tr>
  			<th>아이디</th>
  			<td><input type="text" name="midSearch" id="midSearch" class="form-control" placeholder="아이디를 입력하세요" /></td>
  		</tr>
  		<tr>
  			<th>이메일</th>
  			<td><input type="text" name="emailSearch2" id="emailSearch2" class="form-control" placeholder="이메일을 입력하세요" /></td>
  		</tr>
  		<tr>
  			<td colspan="2" class="text-center"><input type="button" value="새 비밀번호 발급" onclick="newPassword()" class="btn btn-warning" /></td>
  		</tr>
  	</table>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>