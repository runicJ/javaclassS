<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- https://tadaktadak-it.tistory.com/96 // https://haaland09009.tistory.com/226 // https://velog.io/@ansalstmd/JSP8.-%EC%9C%A0%ED%9A%A8%EC%84%B1-%EA%B2%80%EC%82%AC --%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>memberSearch.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script src="${ctp}/js/woo.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>개별 회원 정보</h2>
  <br/>
  <div class="form-group">아이디 : ${vo.mid}</div>
  <div class="form-group">닉네임 : ${vo.nickName}</div>
  <div class="form-group">성명 : ${vo.name}</div>
  <div class="form-group">Email address : ${vo.email}</div>
  <div class="form-group">성별 : ${vo.gender}</div>
  <div class="form-group">생일 : ${vo.birthday}</div>
  <div class="form-group">전화번호 : ${vo.tel}</div>
  <div class="form-group">주소 : ${vo.address}</div>
  <div class="form-group">홈페이지 주소 : ${vo.homePage}</div>
  <div class="form-group">직업 : ${vo.job}</div>
  <div class="form-group">취미 : ${vo.hobby}</div>
  <div class="form-group">자기소개 : ${vo.content}</div>
  <div class="form-group">정보공개 : ${vo.userInfor}</div>
  <div class="form-group">회원 사진 : <img src="${ctp}/images/member/${vo.photo}" width="180px" /></div>
  <hr>
  <div>
  	<a href="javascript:history.back();" class="btn btn-success">돌아가기</a>  <!-- 작업한게 없으니까 그냥 바로 전단계로 보내면 됨(history.back()) -->
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>