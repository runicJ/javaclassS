<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>boardList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
  	'use strict';
  	
  	function pageSizeCheck() {  // 글 상세 보기 하고 '돌아가기'버튼 누르면 해당 페이지로 돌아가지 않음
  		let pageSize = $("#pageSize").val();
  		location.href = "BoardList.bo?pageSize="+pageSize;  // javaScript 함수니까 +pageSize
  	}
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <table class="table table-borderless m-0 p-0">
  	<tr>
  		<td colspan="2"><h2 class="text-center">게 시 판 리 스 트</h2></td>
  	</tr>
  	<tr>
  		<td><c:if test="${sLevel != 1}"><a href="BoardInput.bo" class="btn btn-success btn-sm">글쓰기</a></c:if></td>  <!-- 세션 들어가서 준회원이면 안보이게 준회원은 읽을 수만 있게 -->
  		<td class="text-right">
  			<select name="pageSize" id="pageSize" onchange="pageSizeCheck()">  <!-- ajax로 할 필요 없음 -->
  				<option ${pVo.pageSize==5 ? "selected" : ""}>5</option>
  				<option ${pVo.pageSize==10 ? "selected" : ""}>10</option>
  				<option ${pVo.pageSize==15 ? "selected" : ""}>15</option>
  				<option ${pVo.pageSize==20 ? "selected" : ""}>20</option>
  				<option ${pVo.pageSize==30 ? "selected" : ""}>30</option>
  			</select>
  		</td>
  	</tr>
  </table>
  <table class="table table-hover m-0 p-0 text-center">
  	<tr class="table-dark text-dark">
  		<th>글번호</th>
  		<th>글제목</th>
  		<th>글쓴이</th>
  		<th>작성일</th>
  		<th>조회수(좋아요)</th>
  	</tr>
<%--   	<c:forEach var="vo" items="${vos}" varStatus="st">
	  	<tr>
	  		<td>${vo.idx}</td>
	  		<td>
	  			<a href="BoardContent.bo?idx=${vo.idx}">${vo.title}</a>  <!-- 확장자 쓰면 좋은점 ctp 안써도 됨 -->
	  			<c:if test="${vo.hour_diff <= 24}"><img src="${ctp}/images/new.gif" /></c:if>  <!-- 이미지 무조건 절대경로 // 시작은 webapp -->
	  		</td>
	  		<td>${vo.nickName}</td>
	  		<td>
	  			<!-- 1일(24시간) 이내는 시간만 표시, 이후는 날짜와 시간을 표시 : 2024-05-14 10:43 -->
	  			<!-- 단, 24시간 안에 만족하는 자료에 대해서는, 날짜가 '오늘날짜'만 시간으로 표시하고, 어제 날짜는 '날짜시간'으로 표시하시오 -->  <!-- < c:if test="$ { vo.date_diff == 0}"> 쓰면 또 안에서 함수를 써야해서 삼항연산자로 -->
	  			${vo.date_diff == 0 ? fn:substring(vo.wDate,11,19) : fn:substring(vo.wDate,0,16)}
	  			${vo.wDate}
	  		</td>
	  		<td>${vo.readNum}</td>
	  	</tr>
  	</c:forEach> --%>
  	<c:forEach var="vo" items="${vos}" varStatus="st">
  		<c:if test="${vo.openSw == 'OK' || sLevel == 0 || sNickName == vo.nickName}">
		    <tr>
		      <td>${vo.idx}</td>
		      <td class="text-left">
		        <a href="BoardContent.bo?idx=${vo.idx}&pag=${pVo.pag}&pageSize=${pVo.pageSize}">${vo.title}</a>  <!-- 지금까지 idx만 넘겼지만, 페이지 수, 페이지 사이즈, 검색필드, 검색어 같이 넘겨야함 -->
		        <c:if test="${vo.hour_diff <= 24}"><img src="${ctp}/images/new.gif" /></c:if>  
		      </td>
		      <td>${vo.nickName}</td>
		      <td>
		        <!-- 1일(24시간) 이내는 시간만 표시(10:43), 이후는 날짜와 시간을 표시 : 2024-05-14 10:43 -->
		        <!-- 단, 24시간안에 만족하는 자료에 대해서는, 날짜가 '오늘날짜'만 시간으로 표시하고, 어제날짜는 '날짜시간'으로 표시하시오 // 이거 아님 -->
		        ${vo.date_diff == 0 ? fn:substring(vo.wDate,11,19) : fn:substring(vo.wDate,0,16)}
		      </td>
		      <td>${vo.readNum}(${vo.good})</td>
		    </tr>
	  	</c:if>
	  </c:forEach>
  	<tr><td colspan="5" class="m-0 p-0"></td></tr>
  </table>
  <br>
  <!-- 블록페이지 시작 -->  <!-- 0블록: 1/2/3 -->
	<div class="text-center">
		<ul class="pagination justify-content-center" style="margin:20px 0">
			<c:if test="${pVo.pag > 1}"><li class="page-item"><a class="page-link text-secondary" href="${ctp}/BoardList.bo?pag=1&pageSize=${pVo.pageSize}">첫페이지</a></li></c:if>
			<c:if test="${pVo.curBlock > 0}"><li class="page-item"><a class="page-link text-secondary" href="${ctp}/BoardList.bo?pag=${(pVo.curBlock*pVo.blockSize+1)-pVo.blockSize}&pageSize=${pVo.pageSize}">이전블록</a></li></c:if>  <!-- (curBlock-1)*blockSize +1 -->
			<c:forEach var="i" begin="${(pVo.curBlock*pVo.blockSize)+1}" end="${(pVo.curBlock*pVo.blockSize)+pVo.blockSize}" varStatus="st">  <!-- 처음이니까 curBlock => 0블록 -->
				<c:if test="${i <= pVo.totPage && i == pVo.pag}"><li class="page-item active"><a class="page-link bg-secondary border-secondary" href="${ctp}/BoardList.bo?pag=${i}&pageSize=${pVo.pageSize}">${i}</a></li></c:if>
				<c:if test="${i <= pVo.totPage && i != pVo.pag}"><li class="page-item"><a class="page-link text-secondary" href="${ctp}/BoardList.bo?pag=${i}&pageSize=${pVo.pageSize}">${i}</a></li></c:if>
			</c:forEach>
			<c:if test="${pVo.curBlock < pVo.lastBlock}"><li class="page-item"><a class="page-link text-secondary" href="${ctp}/BoardList.bo?pag=${(pVo.curBlock+1)*pVo.blockSize+1}&pageSize=${pVo.pageSize}">다음블록</a></li></c:if>
			<c:if test="${pVo.pag < pVo.totPage}"><li class="page-item"><a class="page-link text-secondary" href="${ctp}/BoardList.bo?pag=${pVo.totPage}&pageSize=${pVo.pageSize}">마지막페이지</a></li></c:if>
		</ul>
	</div>
	<!-- 블록페이지 끝 -->
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>