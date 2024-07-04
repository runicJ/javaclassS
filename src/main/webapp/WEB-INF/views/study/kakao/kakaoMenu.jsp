<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!-- kakaoMenu.jsp(include로 포함해서 사용) -->
<div>
	<p>
		<a href="${ctp}/study/kakao/kakaoEx1" class="btn btn-success mr-2">마커표시/저장</a>
		<a href="${ctp}/study/kakao/kakaoEx2" class="btn btn-primary mr-2">MyDB에 저장된 지명검색</a>
		<a href="${ctp}/study/kakao/kakaoEx3" class="btn btn-secondary mr-2">KakaoDB에 저장된 키워드 검색</a>
		<a href="${ctp}/study/kakao/kakaoEx4" class="btn btn-info mr-2">주변검색</a>
		<a href="${ctp}/study/kakao/kakaoEx5" class="btn btn-warning mr-2">거리계산</a>  <!-- sqrt ex)반경 이내만 배달가능 -->
		<a href="${ctp}/study/kakao/kakaoEx6" class="btn btn-dark">검색지점 사진저장</a>
		<a href="${ctp}/study/kakao/kakaoEx7" class="btn btn-danger">커스텀오버레이</a>
	</p>
</div>