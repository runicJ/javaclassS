<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%-- <%@ include file="/include/certification.jsp" %> --%>  <!-- controller에서 체크 -->
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>memberMain.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
  	'use strict';
  	
  	// 채팅 내용을 DB에 저장
  	function chatInput() {
  		let chat = $("#chat").val();
  		if(chat.trim() != "") {
  			$.ajax({
  				url : "MemberChatInput.mem",
  				type : "post",
  				data : {chat : chat},
  				//success:function() {},  갔다와서 보고할 것 없음
  				error: function() {
  					alert("전송오류!");
  				}
  			});
  		}
  	}
  	
  	// 채팅 대화 입력 후 엔터키를 누르면 자동으로 메시지 DB에 저장시키기...chatInput() 함수 호출하기
  	$(function(){
  		$("#chat").on("keydown",function(e){  // 키를 눌렀을때 함수 부름 발생하는 이벤트
  			if(e.keyCode == 13) chatInput();  // key 13번 엔터키
  		});
  	});
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>회원 전용방</h2>
  <hr>
  <!-- 실시간 채팅방(DB) -->
<div class="row">
  <div class="col" style="width:420px">
  	<form name="chatForm">
  		<label for="chat"><b>실시간 대화방</b></label>  <!-- label for id로 가려고 -->
  		<iframe src="${ctp}/include/memberMessage.jsp" width="100%" height="200px" class="border"></iframe>  <!-- controller 안타면 jsp 경로 써도 보낼 수 있음 // 글이 띄워지는 것  // 부분리로딩x -->
  		<div class="input-group mt-1">
  			<input type="text" name="chat" id="chat" class="form-control" placeholder="대화내용을 입력하세요" autofocus />
  			<div class="input-group-append">
  				<input type="button" value="글등록" onclick="chatInput()" class="btn btn-success" />
  			</div>
  		</div>
  	</form>
  </div>
	<div class="col text-center">
	    <b>신규 메세지(<font color='red'><b>${wmCnt}</b></font>건)</b>
	    <span style="font-size:11px">...<a href="WebMessage.wm">more</a></span>
      <c:if test="${wmCnt != 0}">
		    <table class="table table-bordered table-hover text-center">
		      <tr class="table-dark text-dark">
		        <th>번호</th>
		        <th>아이디</th>
		        <th>보낸날짜</th>
		      </tr>
			    <c:forEach var="vo" items="${wmVos}" varStatus="st">
			      <c:if test="${st.count <= 3}">
				      <tr>
				        <td>${st.count}</td>
				        <td>${vo.sendId}</td>
				        <td>${fn:substring(vo.sendDate,0,16)}</td>
				      </tr>
			      </c:if>
			    </c:forEach>
			    <tr><td colspan="3" class="m-0 p-0"></td></tr>
		    </table>
      </c:if>
      <hr/>
      <div>
        오늘의 일정이 <font color='blue'><b><a href="ScheduleMenu.sc?ymd=${ymd}">${scheduleCnt}</a></b></font>건 있습니다.
      </div>
	  </div>
	</div>
  <hr>
	<div class="row" id="visitCount">
    <div class="col">
	  	<p>현재 <b><font color="blue">${mVo.nickName}</font>(<font color="red">${strLevel}</font>)</b> 님이 로그인 중이십니다.</p>
	  	<%-- <p>현재 <b><font color="blue">${mVo.nickName}</font>(<font color="red">${strLevel}</font>)</b> 님이 로그인 중이십니다.</p> --%>
	  	<p>총 방문횟수 : <b>${mVo.visitCnt}</b> 회</p>
	  	<p>오늘 방문횟수 : <b>${mVo.todayCnt}</b> 회</p>
	  	<p>총 보유 포인트 : <b>${mVo.point}</b> 점</p>
  	</div>
    <div class="col">
      <p><img src="${ctp}/images/member/${mVo.photo}" width="200px"/></p>
  	</div>
  </div>
  <hr/>
  <div>
    <h5>활동내역</h5>
    <p>방명록에 올린글수 : <b>${guestCnt}</b> 건</p>  <!-- 방명록에 올린이가 '성명/아이디/닉네임'과 같은면 모두 같은 사람이 올린글로 간주한다. -->
    <p>게사판에 올린글수 : <b>${boardCnt}</b>건</p>
    <p>자료실에 올린글수 : <b>${pdsCnt}</b>건</p>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>
<%-- 관리자 부분 월요일 아직 안함 // 등업처리 --%>
<%--<svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 32 32"><g fill="none"><path fill="#CDC4D6" d="M27.45 21.89c0-.45-.04-.9-.12-1.33c-.26-1.73-1.05-4.82-3.49-7.32c-.55-.57-1.13-1.04-1.72-1.43l1.22-6.61c.3-1.66-.98-3.2-2.67-3.2c-1.5 0-2.71 1.21-2.71 2.71v5.4c-1.32-.26-2.23-.22-2.23-.22s-.7-.04-1.76.13V4.71c0-1.5-1.21-2.71-2.71-2.71c-1.7 0-2.98 1.54-2.67 3.2l1.17 6.34c-.73.44-1.46 1-2.15 1.7c-2.43 2.5-3.23 5.58-3.49 7.32c-.08.44-.12.88-.13 1.34v.02c0 2.88 1.67 5.36 4.08 6.55c0 0 2.62 1.53 7.64 1.53c5.03 0 7.64-1.53 7.64-1.53a7.285 7.285 0 0 0 4.08-6.55c.02 0 .02-.01.02-.03"></path><path fill="#FF8687" d="M11.13 10.78c.06.32.33.55.66.55c.38 0 .68-.31.68-.67V4.74c0-.67-.56-1.24-1.23-1.24c-.49 0-.79.27-.93.43c-.13.16-.35.51-.26 1zm8.32-6.04v5.91c0 .37.3.68.68.68c.32 0 .6-.23.66-.55l1.08-5.84c.09-.49-.12-.84-.26-1c-.14-.16-.44-.44-.93-.44c-.68 0-1.23.57-1.23 1.24"></path><path fill="#B4ACBC" d="M2.5 21a.5.5 0 0 0 0 1h7a.5.5 0 0 0 0-1zm7.137 2.98a.5.5 0 0 0-.274-.96l-7 2a.5.5 0 0 0 .274.96zM22 21.5a.5.5 0 0 1 .5-.5h7a.5.5 0 1 1 0 1h-7a.5.5 0 0 1-.5-.5m.638 1.52a.5.5 0 0 0-.275.96l7 2a.5.5 0 0 0 .274-.96z"></path><path fill="#F4F4F4" d="m15.96 24.67l-1.36.74c-.08.04-.13.13-.13.22v.78c0 .24.2.44.44.44h2.12c.24 0 .44-.2.44-.44v-.78c0-.09-.05-.18-.13-.22z"></path><path fill="#CA0B4A" d="m15.52 22.81l-.94-.77a.67.67 0 0 1-.24-.51c0-.36.3-.66.66-.66h1.92c.36 0 .66.3.66.66c0 .2-.09.38-.24.51l-.94.77c-.26.21-.63.21-.88 0"></path><path fill="#1C1C1C" d="M11 17.969a1 1 0 0 1 2 0v1a1 1 0 0 1-2 0zm8 0a1 1 0 0 1 2 0v1a1 1 0 0 1-2 0zm-6.52 5.051a.5.5 0 0 1 .5.5a1.241 1.241 0 0 0 2.48 0a.5.5 0 0 1 .5-.5h.01a.5.5 0 0 1 .5.5a1.241 1.241 0 0 0 2.48 0a.5.5 0 1 1 1 0a2.241 2.241 0 0 1-3.985 1.404a2.241 2.241 0 0 1-3.985-1.404a.5.5 0 0 1 .5-.5"></path></g></svg>--%>