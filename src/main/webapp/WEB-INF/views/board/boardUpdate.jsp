<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>boardUpdate.jsp</title>
  <script src="${ctp}/ckeditor/ckeditor.js"></script>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
	<h2 class="text-center">게 시 글 수 정 하 기</h2>
  <form name="myform" method="post">  <!-- enctype 안넣어도 됨. 지금은 첨부파일을 올리는 것이 아님 editor 내에서 가져오는 기능, 추가로 그림파일 이외에 첨부파일을 올릴경우 필요 -->
  	<table class="table table-bordered">
  		<tr>
  			<th>글쓴이</th>
  			<td><input type="text" name="nickName" id="nickName" value="${sNickName}" class="form-control" readonly /></td>
  		</tr>
  		<tr>
  			<th>글제목</th>
  			<td><input type="text" name="title" id="title" value="${vo.title}" class="form-control" autofocus required /></td>
  		</tr>
  		<tr>
  			<th>글내용</th>
  			<td><textarea name="content" id="CKEDITOR" rows="6" class="form-control" required>${vo.content}</textarea></td>  <!-- textarea 공간에서 값을 보는것, html 코드가 아님 그래서 br \n 안써도 됨 -->
  			<script>
  				CKEDITOR.replace("content",{  // 라이브러리에서 지원해주는 코드만 써야함(jsp이런거 안됨)
  					height:480,
        	  filebrowserUploadUrl:"${ctp}/imageUpload",  /* 파일(이미지)를 업로드 시키기 위한 매핑여로(메소드) */
        	  uploadUrl : "${ctp}/imageUpload"  	/* uploadUrl : 여러개의 그림 파일을 드래그&드롭해서 올릴수 있다. */
  				});
  			</script>
  		</tr>
  		<tr>
  			<th>공개여부</th>
  			<td>
  				<input type="radio" name="openSw" id="openSw1" value="OK" ${vo.openSw=='OK' ? 'checked' : ''} />공개 &nbsp;
  				<input type="radio" name="openSw" id="openSw2" value="NO" ${vo.openSw=='NO' ? 'checked' : ''} />비공개
  			</td>
  		</tr>
  		<tr>
  			<td colspan="2" class="text-center">
  				<input type="submit" value="수정하기" class="btn btn-success mr-2" />  <!-- 유효성검사 안하려면 위에 required -->
  				
  				<input type="reset" value="다시입력" class="btn btn-warning mr-2" />
  				<input type="button" value="돌아가기" onclick="location.href='boardContent?idx=${vo.idx}&pag=${pag}&pageSize=${pageSize}';" class="btn btn-danger" />
  			</td>
  		</tr>
  	</table>
  	<input type="hidden" name="idx" value="${vo.idx}"/>
  	<input type="hidden" name="pag" value="${pag}"/>
  	<input type="hidden" name="pageSize" value="${pageSize}"/>
  	<input type="hidden" name="hostIp" value="${pageContext.request.remoteAddr}"/>
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>