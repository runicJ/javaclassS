<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
  <!-- <script src="http://developers.kakao.com/sdk/js/kakao.js"></script> -->
<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.7.2/kakao.min.js" integrity="sha384-TiCUE00h649CAMonG018J2ujOgDKW/kVWlChEuu4jK2vxfAAD0eZxzCKakxg55G4" crossorigin="anonymous"></script>
<script>
	'use strict';

 	function userDelCheck() {
	  let ans = confirm("회원 탈퇴하시겠습니까?");
	  if(ans) {
		  ans = confirm("탈퇴하시면 1개월간 같은 아이디로 다시 가입하실수 없습니다.\n그래도 탈퇴 하시겠습니까?");
		  if(ans) {
			  $.ajax({
				  type : "post",
				  url  : "${ctp}/member/userDel",
				  success:function(res) {
					  if(res == "1") {
						  alert("회원에서 탈퇴 되셨습니다.");
						  location.href = '${ctp}/member/memberLogin';
					  }
					  else {
						  alert("회원 탈퇴신청 실패~~");
					  }
				  },
				  error : function() {
					  alert("전송오류!");
				  }
			  });
		  }
	  }
	}

	// 카카오 로그아웃
 	window.Kakao.init("f8adb2181213318e34c8e8f99a4eb70c");
	  function kakaoLogout() {
		  const accessToken = Kakao.Auth.getAccessToken();
		  if(accessToken) {
			  Kakao.Auth.logout(function() {
				  window.location.href = "https://kauth.kakao.com/oauth/logout?client_id=f8adb2181213318e34c8e8f99a4eb70c&logout_redirect_uri=http://localhost:9090/javaclassS/member/memberLogout";
			  });
		  }
	  }
	  
	 //window.Kakao.init('f8adb2181213318e34c8e8f99a4eb70c'); // 사용하려는 앱의 JavaScript 키 입력
	 
 // 네이버 로그아웃
  function naverLogout() {
	  var url = "${ctp}/member/naverLogout";
		var childWindow = window.open(url, "naverWindow", "width=50, height=50, top=0, left=0");
		setTimeout(() => {
	  	if(childWindow != null) childWindow.close();		  
	    location.href = "${ctp}/member/memberLogout";
	  }, 1500);
  }
</script>
<div class="w3-top">
  <div class="w3-bar w3-black w3-card">
    <a class="w3-bar-item w3-button w3-padding-large w3-hide-medium w3-hide-large w3-right" href="javascript:void(0)" onclick="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a>
    <a href="${ctp}/" class="w3-bar-item w3-button w3-padding-large">HOME</a>
    <!-- <a href="http://192.168.50.20:9090/javaclassS/" class="w3-bar-item w3-button w3-padding-large">HOME</a> -->
    <a href="${ctp}/guest/guestList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Guest</a>
    <c:if test="${!empty sLevel}">
	    <a href="${ctp}/board/boardList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Board</a>
	    <a href="${ctp}/pds/pdsList" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Pds</a>
	    <div class="w3-dropdown-hover w3-hide-small">
	      <button class="w3-padding-large w3-button" title="More">Study1 <i class="fa fa-caret-down"></i></button>     
	      <div class="w3-dropdown-content w3-bar-block w3-card-4">
	        <a href="${ctp}/user/userList" class="w3-bar-item w3-button">UserList</a>
	        <a href="${ctp}/dbtest/dbtestList" class="w3-bar-item w3-button">DB Test</a>
	        <a href="${ctp}/study/ajax/ajaxForm" class="w3-bar-item w3-button">Ajax Test</a>
	        <a href="${ctp}/study/restapi/restapi" class="w3-bar-item w3-button">REST API</a>
	        <a href="${ctp}/study/password/password" class="w3-bar-item w3-button">암호화</a>
	        <a href="${ctp}/study/mail/mailForm" class="w3-bar-item w3-button">메일연습</a>
	        <a href="${ctp}/study/fileUpload/fileUpload" class="w3-bar-item w3-button">파일업로드연습</a>
	        <a href="${ctp}/study/crawling/jsoup" class="w3-bar-item w3-button">크롤링(jsoup)</a>
	        <a href="${ctp}/study/crawling/selenium" class="w3-bar-item w3-button">크롤링(selenium)</a>
	        <a href="${ctp}/study/wordcloud/wordcloudForm" class="w3-bar-item w3-button">WordCloud</a>
	        <a href="${ctp}/study/exchangeRate/exchangeRate" class="w3-bar-item w3-button">환율계산하기</a>
	        <a href="${ctp}/study/bicycle/bicycle" class="w3-bar-item w3-button">전국자전거대여소</a>
	        <a href="${ctp}/study/tagoExpress/tagoExpress" class="w3-bar-item w3-button">고속버스운행정보</a>
	      </div>
	    </div>
	    <div class="w3-dropdown-hover w3-hide-small">
	      <button class="w3-padding-large w3-button" title="More">Study2 <i class="fa fa-caret-down"></i></button>     
	      <div class="w3-dropdown-content w3-bar-block w3-card-4">
	        <a href="${ctp}/study/random/randomForm" class="w3-bar-item w3-button">랜덤알파뉴메릭</a>
	        <a href="${ctp}/study/kakao/kakaomap" class="w3-bar-item w3-button">카카오맵</a>
	        <a href="${ctp}/study/weather/weatherForm" class="w3-bar-item w3-button">날씨API</a>
	        <a href="${ctp}/study/captcha/captchaForm" class="w3-bar-item w3-button">캡차연습</a>
	        <a href="${ctp}/study/qrCode/qrCodeForm" class="w3-bar-item w3-button">QR Code</a>
	        <a href="${ctp}/study/thumbnail/thumbnailForm" class="w3-bar-item w3-button">썸네일 연습</a>
	        <a href="${ctp}/study/chart/chartForm" class="w3-bar-item w3-button">웹 차트 1</a>
	        <a href="${ctp}/study/chart2/chart2Form" class="w3-bar-item w3-button">웹 차트 2</a>
	        <a href="${ctp}/study/validator/validatorForm" class="w3-bar-item w3-button">Validator 연습</a>
	        <a href="${ctp}/study/transaction/transactionForm" class="w3-bar-item w3-button">트랜잭션 연습</a>
	        <a href="${ctp}/study/calendar/calendar" class="w3-bar-item w3-button">인터넷달력</a>
	        <a href="#" class="w3-bar-item w3-button">스케줄러</a>
	        <a href="${ctp}/study/csv/csvForm" class="w3-bar-item w3-button">CSV를MySQL로</a>
	        <a href="${ctp}/study/slideBar/rangeSlider" class="w3-bar-item w3-button">Range Slider</a>
	      </div>
	    </div>
	    <div class="w3-dropdown-hover w3-hide-small">
	      <button class="w3-padding-large w3-button" title="More">Shopping mall <i class="fa fa-caret-down"></i></button>     
	      <div class="w3-dropdown-content w3-bar-block w3-card-4">
	        <a href="${ctp}/dbShop/dbProductList" class="w3-bar-item w3-button">상품리스트</a>
	        <a href="${ctp}/dbShop/dbCartList" class="w3-bar-item w3-button">장바구니</a>
	        <a href="${ctp}/dbShop/dbMyOrder" class="w3-bar-item w3-button">주문(배송)현황</a>
	        <a href="${ctp}/study/payment/payment" class="w3-bar-item w3-button">결재연습</a>
	        <a href="${ctp}/" class="w3-bar-item w3-button">QnA</a>
	        <a href="${ctp}/" class="w3-bar-item w3-button">1:1문의</a>
	      </div>
	    </div>
	    <div class="w3-dropdown-hover w3-hide-small">
	      <button onclick="location.href='${ctp}/member/memberMain';" class="w3-padding-large w3-button" title="More">MyPage <i class="fa fa-caret-down"></i></button>     
	      <div class="w3-dropdown-content w3-bar-block w3-card-4">
	        <a href="${ctp}/schedule/schedule" class="w3-bar-item w3-button">일정관리</a>
	        <a href="${ctp}/photoGallery/photoGalleryList" class="w3-bar-item w3-button">Photo Gallery</a>
	        <a href="${ctp}/" class="w3-bar-item w3-button">DB 채팅</a>
	        <a href="${ctp}/webSocket/webSocket" class="w3-bar-item w3-button">웹소켓 채팅</a>
	        <a href="${ctp}/webSocket/endPoint" class="w3-bar-item w3-button">1:1 채팅</a>
	        <a href="${ctp}/member/memberList" class="w3-bar-item w3-button">회원리스트</a>
	        <a href="${ctp}/member/memberPwdCheck/p" class="w3-bar-item w3-button">비밀번호변경</a>
	        <a href="${ctp}/member/memberPwdCheck/i" class="w3-bar-item w3-button">회원정보수정</a>
	        <a href="javascript:userDelCheck()" class="w3-bar-item w3-button">회원탈퇴</a>
	        <c:if test="${sLevel == 0}"><a href="${ctp}/admin/adminMain" class="w3-bar-item w3-button">관리자메뉴</a></c:if>
	      </div>
	    </div>
    </c:if>
    <c:if test="${empty sLevel}">
	    <a href="${ctp}/member/memberLogin" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Login</a>
	    <a href="${ctp}/member/memberJoin" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Join</a>
    </c:if>
    <c:if test="${!empty sLevel}">
	    <div class="w3-dropdown-hover w3-hide-small">
	      <button class="w3-padding-large w3-button" title="More">Logout <i class="fa fa-caret-down"></i></button>     
	      <div class="w3-dropdown-content w3-bar-block w3-card-4">
			    <a href="${ctp}/member/memberLogout" class="w3-bar-item w3-button w3-padding-large w3-hide-small">일반 Logout</a>
			    <a href="javascript:kakaoLogout()" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Kakao Logout</a>
			    <a href="javascript:naverLogout()" class="w3-bar-item w3-button w3-padding-large w3-hide-small">Naver Logout</a>
			  </div>
			</div>
    </c:if>
    <a href="javascript:void(0)" class="w3-padding-large w3-hover-red w3-hide-small w3-right"><i class="fa fa-search"></i></a>
  </div>
</div>

<!-- Navbar on small screens (remove the onclick attribute if you want the navbar to always show on top of the content when clicking on the links) -->
<div id="navDemo" class="w3-bar-block w3-black w3-hide w3-hide-large w3-hide-medium w3-top" style="margin-top:46px">
  <a href="${ctp}/guest/guestList" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">Guest</a>
  <a href="${ctp}/board/boardList" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">Board</a>
  <a href="${ctp}/pds/pdsList" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">Pds</a>
  <a href="#" class="w3-bar-item w3-button w3-padding-large" onclick="myFunction()">Study1</a>
</div>
<!-- <script>
	function kakaoLogout() {
    Kakao.Auth.logout()
      .then(function() {
        alert('logout ok\naccess token -> ' + Kakao.Auth.getAccessToken());
        deleteCookie();
      })
      .catch(function() {
        alert('Not logged in');
      });
  }

  // 아래는 데모를 위한 UI 코드입니다.
  function deleteCookie() {
    document.cookie = 'authorize-access-token=f8adb2181213318e34c8e8f99a4eb70c; Path=http://localhost:9090/javaclassS/member/memberLogout; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
  }
</script> -->