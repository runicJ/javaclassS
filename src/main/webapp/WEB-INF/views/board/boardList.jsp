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
  		location.href = "boardList?pageSize="+pageSize;  // javaScript 함수니까 +pageSize
  	}
  	
  	function modalCheck(hostIp, mid, nickName, idx) {
  		$("#myModal #modalHostIp").text(hostIp);  // .html(html방식으로 변환해서 출력) / .val(form태그의 값을 출력)
  		$("#myModal #modalMid").text(mid);
  		$("#myModal #modalNickName").text(nickName);
  		$("#myModal #modalIdx").text(idx);
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
  		<td><c:if test="${sLevel != 1}"><a href="boardInput" class="btn btn-success btn-sm">글쓰기</a></c:if></td>  <!-- 세션 들어가서 준회원이면 안보이게 준회원은 읽을 수만 있게 -->
  		<td class="text-right">
  			<select name="pageSize" id="pageSize" onchange="pageSizeCheck()">  <!-- ajax로 할 필요 없음 -->
  				<option ${pageSize==5 ? "selected" : ""}>5</option>
  				<option ${pageSize==10 ? "selected" : ""}>10</option>
  				<option ${pageSize==15 ? "selected" : ""}>15</option>
  				<option ${pageSize==20 ? "selected" : ""}>20</option>
  				<option ${pageSize==30 ? "selected" : ""}>30</option>
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
  	<c:set var="curScrStartNo" value="${pageVO.curScrStartNo}" />
    <c:forEach var="vo" items="${vos}" varStatus="st">
	    <tr>
	      <td>${curScrStartNo}</td>
	      <td class="text-left">
	        <a href="boardContent?idx=${vo.idx}&pag=${pageVO.pag}&pageSize=${pageVO.pageSize}">${vo.title}</a>  <!-- 지금까지 idx만 넘겼지만, 페이지 수, 페이지 사이즈, 검색필드, 검색어 같이 넘겨야함 --> <!-- 확장자 쓰면 좋은점 ctp 안써도 됨 -->
	        <c:if test="${vo.hour_diff <= 24}"><img src="${ctp}/images/new.gif" /></c:if>  <!-- 이미지 무조건 절대경로 // 시작은 webapp -->
	        <c:if test="${vo.replyCnt != 0}">(${vo.replyCnt})</c:if>
	      </td>
	      <td>
	      	${vo.nickName}
	      	<c:if test="${sLevel == 0}">
	      		<a href="#" onclick="modalCheck('${vo.hostIp}','${vo.mid}','${vo.nickName}','${vo.idx}')" class="badge badge-success" data-toggle="modal" data-target="#myModal">모달출력</a>  <!-- 배찌는 a 태그 밖에 안됨 --> <!-- idx는 숫자니까 오류가 날 수 있음(타입때문에) 앞에는 문자니까 ''했음, js는 형변환이 자유로움(그냥 무조건 '' 붙여주자) --> <!-- 현재창에서 모달로 띄울 것이기 때문에 # --> <!-- data-toggle은 예약어 target은 id -->
	      	</c:if>
	      </td>
	      <td>
	        <!-- 1일(24시간) 이내는 시간만 표시(10:43), 이후는 날짜와 시간을 표시 : 2024-05-14 10:43 -->
	        <!-- 단, 24시간안에 만족하는 자료에 대해서는, 날짜가 '오늘날짜'만 시간으로 표시하고, 어제날짜는 '날짜시간'으로 표시하시오 // 이거 아님 -->
	        ${vo.date_diff == 0 ? fn:substring(vo.WDate,11,19) : fn:substring(vo.WDate,0,16)} <!-- wDate 두번째 글자가 대문자면 오류가 남 -->
	      </td>
	      <td>${vo.readNum}(${vo.good})</td>
	    </tr>
	    <c:set var="curScrStartNo" value="${curScrStartNo - 1}" />
	  </c:forEach>
  	<tr><td colspan="5" class="m-0 p-0"></td></tr>
  </table>
  <br>
  <!-- 블록페이지 시작 -->  <!-- 0블록: 1/2/3 -->
	<div class="text-center">
		<ul class="pagination justify-content-center" style="margin:20px 0">
			<c:if test="${pageVO.pag > 1}"><li class="page-item"><a class="page-link text-secondary" href="${ctp}/board/boardList?pag=1&pageSize=${pageVO.pageSize}">첫페이지</a></li></c:if>
			<c:if test="${pageVO.curBlock > 0}"><li class="page-item"><a class="page-link text-secondary" href="${ctp}/board/boardList?pag=${(pageVO.curBlock*pageVO.blockSize+1)-pageVO.blockSize}&pageSize=${pageVO.pageSize}">이전블록</a></li></c:if>  <!-- (curBlock-1)*blockSize +1 -->
			<c:forEach var="i" begin="${(pageVO.curBlock*pageVO.blockSize)+1}" end="${(pageVO.curBlock*pageVO.blockSize)+pageVO.blockSize}" varStatus="st">  <!-- 처음이니까 curBlock => 0블록 -->
				<c:if test="${i <= pageVO.totPage && i == pageVO.pag}"><li class="page-item active"><a class="page-link bg-secondary border-secondary" href="${ctp}/board/boardList?pag=${i}&pageSize=${pageVO.pageSize}">${i}</a></li></c:if>
				<c:if test="${i <= pageVO.totPage && i != pageVO.pag}"><li class="page-item"><a class="page-link text-secondary" href="${ctp}/board/boardList?pag=${i}&pageSize=${pageVO.pageSize}">${i}</a></li></c:if>
			</c:forEach>
			<c:if test="${pageVO.curBlock < pageVO.lastBlock}"><li class="page-item"><a class="page-link text-secondary" href="${ctp}/board/boardList?pag=${(pageVO.curBlock+1)*pageVO.blockSize+1}&pageSize=${pageVO.pageSize}">다음블록</a></li></c:if>
			<c:if test="${pageVO.pag < pageVO.totPage}"><li class="page-item"><a class="page-link text-secondary" href="${ctp}/board/boardList?pag=${pageVO.totPage}&pageSize=${pageVO.pageSize}">마지막페이지</a></li></c:if>
		</ul>
	</div>
	<!-- 블록페이지 끝 -->
	<br>
	<!-- 검색기 시작 -->
	<div class="container text-center">
	  <form name="searchForm" method="post" action="goardSearchList">
	    <b>검색 : </b>
	    <select name="search" id="search">
	      <option value="title">글제목</option>
	      <option value="nickName">글쓴이</option>
	      <option value="content">글내용</option>
	    </select>
	    <input type="text" name="searchString" id="searchString" required />
	    <input type="submit" value="검색" class="btn btn-secondary btn-sm"/>
	  </form>
	</div>
	<!-- 검색기 끝 -->
</div>
<p><br/></p>

<!-- 모달에 회원정보 출력하기 -->
  <div class="modal fade" id="myModal">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">Modal Heading</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
      		고유번호 : <span id="modalIdx"></span><br>
      		ip : <span id="modalHostIp"></span><br>
      		아이디 : <span id="modalMid"></span><br>
      		닉네임 : <span id="modalNickName"></span><br>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
        
      </div>
    </div>
  </div>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>