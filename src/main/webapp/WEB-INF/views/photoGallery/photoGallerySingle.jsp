<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>photoGallerySingle.jsp</title>
  <%@ include file = "/WEB-INF/views/include/bs4.jsp" %>
  <script>
    'use strict';
    
    // 무한 스크롤 구현(aJax처리)
    let lastScroll = 0;
    let curPage = 1;
    
    $(document).scroll(function(){
    	let currentScroll = $(this).scrollTop();			// 스크롤바 위쪽시작 위치, 처음은 0이다.
    	let documentHeight = $(document).height();		// 화면에 표시되는 전체 문서의 높이
    	let nowHeight = $(this).scrollTop() + $(window).height();	// 현재 화면상단 + 현재 화면높이
    	
    	// 스크롤이 아래로 내려갔을때 이벤트 처리..
    	if(currentScroll > lastScroll) {
    		if(documentHeight < (nowHeight + (documentHeight*0.1))) {
    			console.log("다음페이지 가져오기");
    			curPage++;
    			//getList(curPage);
    			$.ajax({
  	    		url  : "PhotoGallerySinglePaging.ptg",
  	    		type : "post",
  	    		data : {pag : curPage},
  	    		success:function(res) {
  	    			$("#list-wrap").append(res);
  	    		}
  	    	});
    		}
    	}
    	lastScroll = currentScroll;
    });
    
  </script>
  <style>
    .container {
      width: 1000px;
      margin: 0 auto;
    }
    /* 
    .card {
      float:left;
      margin: 10px;
    }
     */
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <table class="table table-borderless">
    <tr>
      <td colspan="2"><h2 class="text-center">포토 갤러리</h2></td>
    </tr>
    <tr>
      <td>
      </td>
      <td class="text-right">
        <input type="button" value="사진올리기" onclick="location.href='PhotoGalleryInput.ptg';" class="btn btn-success"/>
        <input type="button" value="여러장씩보기" onclick="location.href='PhotoGallery.ptg';" class="btn btn-info mr-2"/>
      </td>
    </tr>
  </table>
  <section id="list-wrap">
	  <c:forEach var="photo" items="${vos}" varStatus="st">
	    <div class="card mb-5" style="width:95%;">
		    <div class="card-body m-0 p-2 text-center">
		      <img src="${ctp}/images/photoGallery/${photo[1]}" width="90%" />
		    </div> 
		    <div class="card-footer">
		      <div class="text-center" style="font-size:10px">${photo[0]} : ${photo[1]}</div>
		    </div>
		  </div>
	  </c:forEach>
  </section>
</div>
<p style="clear:both;"><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>