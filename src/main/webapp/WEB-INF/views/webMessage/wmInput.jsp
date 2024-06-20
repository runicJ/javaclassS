<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>wmInput.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
  	'use strict';
  	
    $(document).ready(function(){
    	// 모달창에서 아이디 검색할시에는, 커맨드객체에서 아이디 검색된 결과와 함께 mSwCheck변수에 0을 넣어서 보내준다.
	    <c:if test="${mSwCheck == 0}">
	      document.getElementById("jusorokBtn").click();
	    </c:if>
    });
  	
  	function fCheck() {
  		let receiveId = myform.receiveId.value;
  		let title = myform.title.value;
  		let content = myform.content.value;
  		
    	if(receiveId.trim() == "") {
    		alert("받는사람 아이디를 입력하세요");
    		myform.receiveId.focus();
    		return false;
    	}
    	else if(title.trim() == "") {
    		alert("메시지 제목을 입력하세요");
    		myform.title.focus();
    		return false;
    	}
    	else if(content.trim() == "") {
    		alert("메시지 내용을 입력하세요");
    		myform.content.focus();
    		return false;
    	}
    	else {
  			myform.submit();
    	}
  	}

    function idSearchView() {
    	$("#idSearchShowBtn").hide();
    	
    	let str = '';
    	str += '<div class="input-group m-3">';
    	str += '<input type="text" name="mid" id="mid" class="form-control"/>';
    	str += '<div class="input-group-append">';
    	str += '<input type="button" value="아이디검색" id="idSearchBtn" onclick="idSearchCheck()" class="btn btn-info btn-sm"/>';
    	str += '<div>';
    	str += '</div>';
    	str += '';
    	idSearch.innerHTML = str;
    	//document.getElementById("mid").focus();
    	mid.focus();
    }
    
    function idSearchCheck() {
    	let mid = $("#mid").val();
    	if(mid.trim() == "") {
    		alert("아이디를 입력하세요");
    		return false;
    	}
    	
    	location.href = "IdSearchCheck.wm?mid="+mid;  // ajax처리하면 안됨 어차피 값을 다시 불러와서 써야함
    }
  </script>
</head>
<body>
<p><br/></p>
<div class="container">
  <form name="myform" method="post" action="WmInputOk.wm">
  	<table class="table table-bordered">
  		<tr>
  			<th>보내는 사람</th>
  			<td><input type="text" name="sendId" value="${sMid}" readOnly class="form-control" /></td>
  		</tr>
  		<tr>
  			<th>받는 사람</th>
  			<td>  <!-- 주소록버튼을 클릭해서 아이디를 선택하면 받는사람 입력란에 표시되도록 작성(아이디가 없는 사람은 보낼 수 없게) -->
					<c:if test="${empty param.receiveId}">
<%--             <div class="input-group">
	            <input type="text" name="receiveId" placeholder="받는사람 아이디" class="form-control"/>
              <div class="input-group-append"><input type="button" value="주소록" onclick="#" class="btn btn-info"/></div>
            </div>
          </c:if>
  				<c:if test="${!empty param.receiveId}"><input type="text" name="receiveId" id="receiveId" value="${param.receiveId}" readonly class="form-control" /></c:if>
  			</td>
  		</tr> --%>
  							<div class="input-group">
	            <input type="text" name="receiveId" placeholder="받는사람 아이디" class="form-control"/>
              <div class="input-group-append"><a href="#" id="jusorokBtn" class="btn btn-info" data-toggle="modal" data-target="#myModal">주소록</a></div>
            </div>
          </c:if>
          <c:if test="${!empty param.receiveId}"><input type="text" name="receiveId" value="${param.receiveId}" readonly class="form-control"/></c:if>
        </td>
      </tr>
  		<tr>
  			<th>메시지 제목</th>
  			<td><input type="text" name="title" id="title" placeholder="메시지 제목을 입력하세요" class="form-control" /></td>
  		</tr>
  		<tr>
  			<th>메시지 내용</th>
  			<td><textarea rows="5" name="content" id="content" placeholder="메시지 내용을 입력하세요" class="form-control"></textarea></td>
  		</tr>
  		<tr>
  			<td colspan="2" class="text-center">
  				<input type="button" value="메시지 전송" onclick="fCheck()" class="btn btn-success mr-2"/>
  				<input type="reset" value="다시쓰기" class="btn btn-warning mr-2"/>
  				<input type="button" value="돌아가기" onclick="location.href='WebMessage.wm?mSw=1';" class="btn btn-danger"/>
  			</td>
  		</tr>
  	</table>
  </form>
</div>
<p><br/></p>
<!-- 회원 주소록을 모달로 처리 -->
  <div class="modal" id="myModal">
    <div class="modal-dialog modal-dialog-scrollable">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <!-- <div class="modal-header"> -->
        <div class="m-3">
          <div class="row">
            <!-- <div class="col"><h3 class="modal-title">회원 주소록</h3></div> -->
            <div class="col"><h4>회원 주소록</h4></div>
            <div class="col text-right"><input type="button" value="아이디검색" onclick="idSearchView()" id="idSearchShowBtn" class="btn btn-success btn-sm text-right"/></div>
          </div>
          <div class="row" id="idSearch"></div>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
          <table class="table table-hover">
            <tr class="table-dark text-dark">
              <th>번호</th>
              <th>아이디</th>
              <th>닉네임</th>
            </tr>
            <c:forEach var="vo" items="${mVos}" varStatus="st">
              <tr>
                <td>${st.count}</td>
                <td><a href="WebMessage.wm?mSw=0&receiveId=${vo.mid}">${vo.mid}</a></td>
                <td>${vo.nickName}</td>
              </tr>
            </c:forEach>
            <tr><td colspan="3" class="m-0 p-0"></td></tr>
          </table>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
        </div>
        
      </div>
    </div>
  </div>
</body>
</html>