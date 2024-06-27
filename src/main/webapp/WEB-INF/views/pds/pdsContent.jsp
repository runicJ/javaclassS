<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>pdsContent.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
  	'use strict';
  	
  	// 다운로드 수 증가시키기
  	function downNumCheck(idx) {
  		$.ajax({
  			url : "${ctp}/pds/pdsDownNumCheck",
  			type : "post",
  			data : {idx : idx},
  			success:function() {
  				location.reload();
  			},
  			error : function() {
  				alert("전송오류!");
  			}
  		});
  	}
  	
  	// 화살표 클릭 시 화면 상단으로 부드럽게 이동하기
  	$(window).scroll(function(){  // 윈도우 화면이 움직이는 것
  		if($(this).scrollTop() > 100) {  // this => window  // scrollTop 시작 0 위에부터 계산
  			$("#topBtn").addClass("on");  // 보여라
  		}
  		else {
  			$("#topBtn").removeClass("on");  // 지워라
  		}
  	
  		$("#topBtn").click(function(){
  			window.scrollTo({top:0, behavior: "smooth"});  // 현재 위치 부터 위로 보냄  // 움직이는 동작에 대해서는 부드럽게 해주세요
  		});
  	});
  	
  	// 별점/리뷰평가 등록하기
  	function reviewCheck() {
  		let star = starForm.star.value;
  		let review = $("#review").val();  // 필수x
  		if(star == "") {
  			alert("별점을 부여해 주세요");
  			location.reload();
  		}
  		
  		let query = {
  				part : 'pds',
  				partIdx : ${vo.idx},  // pds 게시글의 번호
  				mid : '${sMid}',  // 문자는 ''안하면 에러
  				nickName : '${sNickName}',
  				star : star,
  				review : review
  		}
  		
  		$.ajax({
  			url : "${cpt}/review/reviewInputOk",
  			type : "post",
  			data : query,
  			success:function(res) {
  				alert(res);
  				location.reload();
  			},
  			error : function() {
  				alert("전송오류!");
  			}
  		});
  	}
  	
  	// 리뷰 삭제하기
  	function reviewDelete(idx) {
  		let ans = confirm("리뷰를 삭제하시겠습니까?");
  		if(!ans) return false;
  		
  		$.ajax({
  			url : "${cpt}/review/reviewDelete",
  			type : "post",
  			data : {idx : idx},
  			success:function(res) {
  				if(res != "0") {
	  				alert("리뷰가 삭제되었습니다.");
	  				location.reload();
  				}
  				else alert("리뷰 삭제 실패~~");
  			},
  			error : function() {
  				alert("전송오류!");
  			}
  		});
  	}
  	
  	// 처음 접속 시 '리뷰보기' 버튼을 감추고, '리뷰닫기' 버튼과 '리뷰박스'를 보이게 한다.
  	//$(document).ready(function(){});  아래와 같음
  	$(function(){
  		$("#reviewShowBtn").hide();
  		$("#reviewHideBtn").show();  		
  		$("#reviewBox").show();  		
  	});
  	
  	// 리뷰 보이기
  	function reviewShow() {
  		$("#reviewShowBtn").hide();
  		$("#reviewHideBtn").show();  		
  		$("#reviewBox").show();  		  		
  	}

  	// 리뷰 감추기
  	function reviewHide() {
  		$("#reviewShowBtn").show();
  		$("#reviewHideBtn").hide();  		
  		$("#reviewBox").hide();  		
  	}
  	
  	// 리뷰 댓글 달기 폼 보여주기
  	function reviewReply(idx, nickName, content) {  		
  		$("#myModal #reviewIdx").val(idx);
  		$("#myModal #reviewReplyNickName").text(nickName);
  		$("#myModal #reviewReplyContent").html(content);  // html로 바꾸면 밑에 설정한 엔터키 실제 적용(2번 바꿔야)
  	}
  	
  	// 리뷰 댓글 달기
  	function reviewReplyCheck() {
			let replyContent = reviewReplyForm.replyContent.value;  // 입력한 댓글 내용
			let reviewIdx = reviewReplyForm.reviewIdx.value;
			
			if(replyContent.trim() == "") {
				alert("리뷰 댓글을 입력하세요");
				return false;
			}
			
			let query = {
					reviewIdx : reviewIdx,
					replyMid : '${sMid}',
					replyNickName : '${sNickName}',
					replyContent : replyContent,
			}
			
			$.ajax({
				url : "${cpt}/review/reviewReplyInputOk",
				type : "post",
				data : query,
				success:function(res) {
					if(res != "0") {
						alert("댓글이 등록되었습니다.");  // 부분리로드 해도 되지만 부분리로드는 내용이 많을때 처리하는 것이 좋음
						location.reload();
					}
					else alert("댓글 등록 실패~~");
				},
				error : function() {
					alert("전송 오류!");
				}
			});
  	}
  </script>
  <style>
  	th {
  		background-color: #eee;
  		width: 15%;
  	}
  	h6 {
		  position: fixed;  /* 위치고정 */
		  right: 1rem;
		  bottom: -50px;
		  transition: 0.7s ease;
		}
   	.on {  /* 버튼에 관한것 마우스 올리면 */
		  opacity: 0.8;
		  cursor: pointer;
		  bottom: 0;
		}
  	
  	/* 별점 스타일 설정하기 */
  	#starForm fieldset {
  		direction: rtl;  /* 오른쪽에서 왼쪽으로(아랍권) */
  	}
  	#starForm input[type=radio] {
  		display: none;  /* 안보이게 숨김 */
  	}
  	#starForm label {
  		font-size: 1.7em;
  		color: transparent;
  		text-shadow: 0 0 0 #f0f0f0;  /* 오른쪽 아래쪽 크기 */
  	}
  	#starForm label:hover {
  		text-shadow: 0 0 0 rgba(250, 200, 0, 0.98);
  	}
  	#starForm label:hover ~ label {  /* ~ 흐름 앞의 레이블이 이어지는 곳에 적용하겠다 */
  		text-shadow: 0 0 0 rgba(250, 200, 0, 0.98);
  	}
  	#starForm input[type=radio]:checked ~ label {  /* 체크가 되어있는 곳에서 라벨까지 */
  		text-shadow: 0 0 0 rgba(250, 200, 0, 0.98);
  	}
  	
  	#reviewReplyForm {
      font-size: 11pt;
    }
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
	<!-- <h2 class="text-conter"><a name="topMenu">자료 내용 상세보기</a></h2> -->
	<h2 class="text-conter">자료 내용 상세보기</h2>
	<br>
  <table class="table table-bordered text-center">
  	<tr>
  		<th>분류</th>
  		<td>${vo.part}</td>
  		<th>접속IP</th>
  		<td>${vo.hostIp}</td>
  	</tr>
  	<tr>
  		<th>작성자</th>
  		<td>${vo.nickName}</td>
  		<th>작성일</th>
  		<td>${fn:substring(vo.fDate,0,fn:length(vo.fDate)-2)}</td>
  	</tr>
  	<tr>
  		<th>파일명</th>
			<td>
				<c:set var="fNames" value="${fn:split(vo.FName,'/')}"/>  <!-- 스크립트 사용하지 않고 jstl로 사용 // 변수 설정 c set-->
				<c:set var="fSNames" value="${fn:split(vo.FSName,'/')}"/>
				<c:forEach var="fName" items="${fNames}" varStatus="st">  <!-- 개별 다운로드 -->
					<a href="${ctp}/images/pds/${fSNames[st.index]}" download="${fName}" onclick="downNumCheck(${vo.idx})">${fName}<br></a>  <!-- download= $ {fName} 저장한 이름으로 다운 받도록 이름 설정 -->
				</c:forEach>
				(<fmt:formatNumber value="${vo.FSize/1024}" pattern="#,##0" />kByte)
  		</td>
  		<th>다운횟수</th>
  		<td>${vo.downNum}</td>
  	</tr>
  	<tr>
  		<th>제목</th>
  		<td colspan="3" class="text-left">${vo.title}</td>
  	</tr>
  	<tr>
  		<th>상세내역</th>
  		<td colspan="3" class="text-left" style="height:150px">${fn:replace(vo.content, newLine, "<br/>")}</td>
  	</tr>
  	<tr>
  		<td colspan="4">
  			<input type="button" value="돌아가기" onclick="location.href='pdsList?pag=${pag}&pageSize=${pageSize}&part=${part}';" class="btn btn-primary" />
  		</td>
  	</tr>
  </table>
  <hr>
  <div>
  	<form name="starForm" id="starForm">
  		<fieldset style="border:0px;">  <!-- 별점자리 영역으로 지정 // border 0으로 흔적 삭제 -->
  			<div class="viewPoint m-0 b-0">  <!-- margin때문에 별이 밀려서 0으로 설정 -->
  				<input type="radio" name="star" value="5" id="star1"><label for="star1">★</label>  <!-- 원래 label 먼저 썼는데 right to left를 위해 오른쪽부터니까 5점부터 -->
  				<input type="radio" name="star" value="4" id="star2"><label for="star2">★</label>
  				<input type="radio" name="star" value="3" id="star3"><label for="star3">★</label>
  				<input type="radio" name="star" value="2" id="star4"><label for="star4">★</label>
  				<input type="radio" name="star" value="1" id="star5"><label for="star5">★</label>  <!-- rtl로 바꿨기 때문에 반대로 읽는 것 // 맨 앞의 별 -->
  				: 별점을 남겨주세요 ■
  			</div>
  		</fieldset>
  		<div class="m-0 p-0">
  			<textarea rows="3" name="review" id="review" class="form-control" placeholder="별점 후기를 남겨주시면 100포인트를 지급합니다."></textarea>  <!-- cols를 안주려면 form-control로 작성 -->
  		</div>
  		<div>
  			<input type="button" value="별점/리뷰등록" onclick="reviewCheck()" class="btn btn-primary btn-sm form-control" />
  		</div>
  	</form>
  </div>
  <hr>
  <!-- 리뷰/평점 출력 -->
  <div class="row">
  	<div class="col">
  		<input type="button" value="리뷰보기" id="reviewShowBtn" onclick="reviewShow()" class="btn btn-success" />
  		<input type="button" value="리뷰닫기" id="reviewHideBtn" onclick="reviewHide()" class="btn btn-warning" />
  	</div>
  	<div class="col text-right">
  		<b>리뷰평점 : <fmt:formatNumber value="${reviewAvg}" pattern="#,##0.0" /></b>
  	</div>
  </div>
  <hr>
  <div id="reviewBox">
		<c:set var="imsiIdx" value="0" />  <!-- 임시로 변수하나 줌 // 0은 없으니까 값으로 먼저 줌 -->
  	<c:forEach var="vo" items="${rVos}" varStatus="st">
  		<c:if test="${imsiIdx != vo.idx}">  <!-- 리뷰가 새로운 것이 왔다 => 이때 뿌림 -->
	  		<div class="row">
	  			<div class="col ml-2">
	  				<b>${vo.nickName}</b>
	  				<span style="font-size:11px">${fn:substring(vo.rDate,0,10)}</span>  <!-- font로 하면 맘대로 조절이 안됨 -->
	  				<c:if test="${vo.mid == sMid || sLevel == 0}"><a href="javascript:reviewDelete(${vo.idx})" title="리뷰삭제" class="badge badge-danger">x</a></c:if>
	  				<a href="#" onclick="reviewReply('${vo.idx}','${vo.nickName}','${fn:replace(vo.content,newLine,'<br>')}')" title="댓글달기" data-toggle="modal" data-target="#myModal" class="badge badge-secondary">▤</a>  <!-- 내용 2줄이상이면 enter키 처리해야 보임 / 모달창으로 적용 / onclick이니까 어차피 js처리 -->
	  			</div>
	  			<div class="col text-right mr-2">
	  				<c:forEach var="i" begin="1" end="${vo.star}" varStatus="iSt">  <!-- 위에 st 사용 inlineSt -->
	  					<font color="gold">★</font>
	  				</c:forEach>
	  				<c:forEach var="i" begin="1" end="${5 - vo.star}" varStatus="iSt">☆</c:forEach>
	  			</div>
	  		</div>
				<div class="row border m-1 p-2" style="border-radius:5px">
	        ${fn:replace(vo.content, newLine, '<br/>')}
				</div>
			</c:if>
			<c:set var="imsiIdx" value="${vo.idx}" />  <!-- vo.idx 리뷰의 idx를 imsi에 넣어줌 -->
			<c:if test="${!empty vo.replyContent}">  <!-- 댓글이 비어있지 않으면 -->
				<div class="d-flex text-secondary">
					<div class="mt-2 ml-3">└─▶ </div>
					<div class="mt-2 ml-2">${vo.replyNickName}
						<span style="font-size:11px">${fn:substring(vo.replyRDate,0,10)}</span>  <!-- 한줄로 끝낼거라서 span으로 줌 -->
						<c:if test="${vo.replyMid == sMid || sLevel == 0}"><a href="javascript:reviewReplyDelete(${vo.replyIdx})" title="댓글삭제" class="badge badge-danger">x</a></c:if>
						<br>${vo.replyContent}
					</div>
				</div>
			</c:if>
			<hr>
  	</c:forEach>
  </div>
  
  <!-- 자료실에 등록된 자료가 사진이라면, 아래쪽에 모두 보여주기 -->
  <div class="text-center">
		<c:forEach var="fSName" items="${fSNames}" varStatus="st">
			${fNames[st.index]}<br>  <!-- 파일명 -->
			<c:set var="len" value="${fn:length(fSName)}" />
			<c:set var="ext" value="${fn:substring(fSName, len-3, len)}"/>
		  <c:set var="extLower" value="${fn:toLowerCase(ext)}"/>
			<c:if test="${extLower == 'jpg' || extLower == 'gif' || extLower == 'png'}">  <!-- 소문자로 해야함 -->
				<img src="${ctp}/pds/${fSName}" width="85%" />  <!-- px로 주면 안됨 %로 줘야함 -->
			</c:if>
			<hr>
		</c:forEach>	
  </div>
	
	<!-- 위로가기 버튼 -->
	<h6 id="topBtn" class="text-right mr-3"><img src="${ctp}/images/arrowTop.gif" title="위로 이동" /></h6>  <!-- 글자로 써도됨 그래서 h6 사용 -->
</div>
<p><br/></p>

<!-- 댓글달기를 위한 모달처리 -->
  <div class="modal fade" id="myModal">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">>> 리뷰에 댓글달기</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <!-- Modal body -->
        <div class="modal-body">
        	<form name="reviewReplyForm" class="was-validated">
						<table class="table table-bordered">
							<tr>
								<th style="width:25%;">리뷰 작성자</th>
								<td style="width:75%;"><span id="reviewReplyNickName"></span></td>
							</tr>
							<tr>
								<th>리뷰내용</th>  <!-- 첫번째 행에 style 적용하면 아래 같이 적용 -->
								<td><span id="reviewReplyContent"></span></td>
							</tr>
						</table>
						<hr>
						댓글 작성자 : ${sNickName}<br>
						댓글 내용 : <textarea rows="3" name="replyContent" id="replyContent" class="form-control" required></textarea>
						<input type="button" value="리뷰댓글등록" onclick="reviewReplyCheck()" class="btn btn-success form-control"/>
						<input type="hidden" name="reviewIdx" id="reviewIdx" />
        	</form>
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