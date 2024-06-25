<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>  <!-- 엔터키에 대한 처리 newLine 변수로 사용 -->
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>boardContent.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <style>
  	th {
  		text-align: center;
  		background-color: #eee;
  	}
  </style>
  <script>
  	'use strict';
  	
  	function boardDelete() {
  		let ans = confirm("현재 게시글을 삭제 하시겠습니까?");
  		if(ans) location.href = "boardDelete?idx=${vo.idx}";  // 여기서는 idx를 넘겨야함 // 아래에서 안가져오면 +idx
  	}
  	
  	// 좋아요 처리(중복허용)
  	function goodCheck() {
  		$.ajax({
  			url : "boardGoodCheck",
  			type : "post",
  			data : {idx : ${vo.idx}},  // 현재 게시글의 idx  // 숫자는 안써도 되지만 문자는 "" 무조건 써야함  // 여기까지가 전송할 것
  			success:function(res) {
  				if(res != "0") location.reload();  // 전부다 새로 읽는 것 (부분리로드 처리)
  			},
  			error:function() {
  				alert("전송오류");
  			}
  		});
  	}
  	
    // 좋아요 처리(중복불허)
    function goodCheck2() {
    	$.ajax({
    		url  : "boardGoodCheck2",
    		type : "post",
    		data : {idx : ${vo.idx}},
    		success:function(res) {
    			if(res != "0") {
    				//document.getElementById("liked").querySelector("font").style.color = "red";
    				//document.getElementById("liked").classList.add("liked");
    				location.reload();
    			}
    			else alert("이미 좋아요 버튼을 클릭하셨습니다.");
    		},
    		error : function() {
    			alert("전송오류");
    		}
    	});
    }
    
  	// 좋아요 토글 처리
  	function goodCheckToggle() {
      $.ajax({
          url  : "boardGoodCheckToggle",
          type : "post",
          data : {idx : ${vo.idx}},
          success:function() {                
            location.reload();
          },
          error : function() {
						alert("전송오류!");
          }
      });    
  	}

  	// 좋아요(따봉)수 증가 처리(중복허용)
  	function goodCheckPlus() {
  		$.ajax({
  			url : "boardGoodCheckPlusMinus",
  			type : "post",
  			data : {
  				idx : ${vo.idx},
  				goodCnt : +1
  			},
  			success:function(res) {
  				if(res != "0") location.reload();
  				else alert("이미 좋아요 버튼을 클릭하셨습니다.");
  			},
  			error:function() {
  				alert("전송오류");
  			}
  		});
  	}
  	
  	// 좋아요(따봉)수 감소 처리(중복허용)
  	function goodCheckMinus() {
  		$.ajax({
  			url : "boardGoodCheckPlusMinus",  // 한번에 처리
  			type : "post",
  			data : {
  				idx : ${vo.idx},
  				goodCnt : -1
  			},
  			success:function(res) {
  				if(res != "0") location.reload();
  			},
  			error:function() {
  				alert("전송오류");
  			}
  		});
  	}
  	
  	// 신고창 '기타'항목 선택 시 textarea 보여주기
  	function etcShow() {
  		$("#complaintTxt").show();  // 클릭하면 보여주도록
  	}
  	
  	// 신고화면 선택 후 신고사항 전송하기
  	function complaintCheck() {  // 한 화면에서 일어나는 거니까 ajax처리
  		if(!$("input[type=radio][name=complaint]:checked").is(':checked')) {  // 체크가 아무것도 안되어 있다면 // jquery 사용해서 값을 가져올때 아이디를 줘도 되고([type='radio'] ''안쓰려면 =에 붙여쓰기)  // 정확히 하려면 radio 버튼 중에서 complaint란 이름
  			alert("신고항목을 선택하세요");
  			return false;
  		}
  		//if($("input[type=radio][id=complaint7]:checked") && $("#complaintTxt").val() == "")
  		if($("input[type=radio]:checked").val() == "기타" && $("#complaintTxt").val() == "") {  // 기타 check되어 있 && 기타 사유칸이 비어 있다면, [id=complaint7] 필요없음
  			alert("기타 사유를 입력해 주세요");
  			return false;
  		}
  		
  		let cpContent = modalForm.complaint.value;
  		if(cpContent == '기타') cpContent += '/' + $("#complaintTxt").val();
  		
  		//alert("신고내용 : " + cpContent);
  		let query = {
  			part : 'board',
  			partIdx: ${vo.idx},
  			cpMid : '${sMid}',
  			cpContent : cpContent
  		}
  		
  		$.ajax({
  			url : "boardComplaintInput",
  			type : "post",
  			data : query,  // 값이 많으면 쿼리로 넣어주자
  			success:function(res) {
  				if(res != "0") {
  					alert("신고 처리 되었습니다.");
  					location.reload();  // 신고처리가 되었으니 버튼을 비활성화 하거나 하려면 다시 읽어주기
  				}
  				else alert("신고 처리 실패~~");
  			},
  			error:function() {
  				alert("전송오류!");
  			}
  		});
  	}
  	
  	// 댓글달기
  	function replyCheck() {
  		let content = $("#content").val();
  		if(content.trim() == "") {
  			alert("댓글을 입력하세요");
  			return false;
  		}
  		let query = {  // 값이 많으니까 변수에 담자
  			boardIdx : ${vo.idx},
  			mid : '${sMid}',
  			nickName : '${sNickName}',
  			hostIp : '${pageContext.request.remoteAddr}',
  			content : content
  		}
  		
  		$.ajax({
  			url : "boardReplyInput",
  			type : "post",
  			data : query,  // 여기까지가 전송
  			success:function(res) {  // 여기부터 받은 값
  				if(res != "0") {
  					alert("댓글이 입력되었습니다.");
  					location.reload();  // 부분 댓글 리로드 처리 하기
  				}
  				else alert("댓글 입력 실패!");
  			},
  			error:function() {
  				alert("전송 오류!");
  			}
  		});
  	}
  	
  	// 댓글 삭제하기
	  function replyDelete(idx) {
	  	let ans = confirm("선택하신 댓글을 삭제하시겠습니까?");
	  	if(!ans) return false;
	  	
	  	$.ajax({  // ans면
	  		url : "boardReplyDelete",
	  		type : "post",
	  		data : {idx : idx},
	  		success:function(res) {
	  			if(res != "0") {
	  				alert("댓글이 삭제되었습니다.");
	  				location.reload();  // 항시 리로드
	  			}
	  			else {
	  				alert("댓글 삭제 실패");
	  			}
	  		},
	  		error:function() {
	  			alert("전송오류!");
	  		}
	  	});
	  }
  	
    // 댓글 수정하기
    function replyEdit(modalIdx, content, boardIdx, pag, pagSize) {
      $("#myModal2 #modalIdx").val(modalIdx);
      $("#myModal2 #modalContent").val(content);
      $("#myModal2 #boardIdx").val(boardIdx);
      $("#myModal2 #pag").val(pag);
      $("#myModal2 #pagSize").val(pagSize);
  	}
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2 class="text-center mb-4">글 내 용 보 기</h2>
  <hr>
  <table class="table table-bordered">
  	<tr>
  		<th>글쓴이</th>
  		<td>${vo.nickName}</td>
  		<th>작성일</th>
  		<td>${fn:substring(vo.WDate, 0, 19)}</td>  <!-- 초 안나오게 하려면 16개 -->
  	</tr>
  	<tr>
  		<th>글조회수</th>
  		<td>${vo.readNum}</td>
  		<th>접속IP</th>
  		<td>${vo.hostIp}</td>
  	</tr>
  	<tr>
  		<th>글제목</th>
  		<td colspan="3">${vo.title}</td>
  	</tr>
  	<tr>
  		<th>글내용</th>
  		<td colspan="3" style="height:220px">${fn:replace(vo.content, newLine, "<br/>")}</td>
  	</tr>
  	<tr>
  		<td colspan="4">
  			<div class="row">
  				<div class="col">
	  				<c:if test="${empty flag}"><input type="button" value="돌아가기" onclick="location.href='boardList?pag=${pag}&pageSize=${pageSize}';" class="btn btn-warning" /></c:if>  <!-- controller로 넘겼으니까 command에서 값을 받아야함 --> <!-- 왔던 페이지랑 페이지사이즈를 가져가?검색 기능 있으면 검색분류와 검색어까지 같이 보냄 // 단순히 보기만 하면 history.back(), 수정삭제 하면 이걸로 안됨 // list에서 온거면 flag 값 없으니 그냥 비었는지 확인 -->
	  				<c:if test="${!empty flag}"><input type="button" value="돌아가기" onclick="location.href='boardSearchList?pag=${pag}&pageSize=${pageSize}&search=${search}&searchString=${searchString}';" class="btn btn-warning" /></c:if>
		  		</div>
		  		<div class="col text-center">
	  				<a href="javascript:goodCheck()"> ❤ </a> ${vo.good} /
	  				<a href="javascript:goodCheckPlus()"> 💖 </a> &nbsp;
	  				<a href="javascript:goodCheckMinus()"> 💔 </a> /
	  				<!-- <a href="javascript:goodCheck2()"><font color="blue" size="5">♥</font></a> ${vo.good} -->
	  				<!-- <a href="javascript:goodCheck2()" id="liked"><font size="6"> ♥ </font></a> ${vo.good} / -->
	  				<!-- <i class="fa-regular fa-heart"></i><i class="fa-solid fa-heart"></i> -->
	  				<a href="javascript:goodCheck2()"><font color="red" size="5">${liked == "1" ? '♥' : '♡'}</font></a> ${vo.good} /
	  				<!-- <a href="javascript:goodCheck2()" id="liked"><font size="5"> ♥ </font></a> ${vo.good} / -->
	  				<a href="javascript:goodCheckToggle()"><font color="red" size="5">${like == "1" ? '♥' : '♡'}</font></a>
	  			</div>
	  			<div class="col text-right">
			  		<c:if test="${sNickName == vo.nickName || sLevel == 0}">
				  		<input type="button" value="수정" onclick="location.href='boardUpdate?idx=${vo.idx}&pag=${pag}&pageSize=${pageSize}'" class="btn btn-primary" />
				  		<input type="button" value="삭제" onclick="boardDelete()" class="btn btn-danger" />  <!-- 현재 글에 있는 idx는 하나이기 때문에 idx 전달할 필요 없음 // 현재 페이지 -->
			  		</c:if>
			  		<c:if test="${sNickName != vo.nickName}">
			  			<c:if test="${call_112 == 'OK'}"><font color='red'><b>신고처리 중입니다..</b></font></c:if>
				  		<c:if test="${call_112 != 'OK'}"><input type="button" value="신고하기" data-toggle="modal" data-target="#myModal" class="btn btn-danger" /></c:if>  <!-- 현재 글에 있는 idx는 하나이기 때문에 idx 전달할 필요 없음 // 현재 페이지 // onclick은 버튼 속성이 있을 때는 필요없고, a태그일떄는 필요 -->
			  		</c:if>
			  	</div>
	  		</div>
  		</td>
  	</tr>
  </table>
  <hr>
  <!-- 이전글/ 다음글 출력하기 -->
  <table class="table table-borderless">  <!-- 얘도 테이블로 써야 위와 시작점이 같음 -->
  	<tr>
  		<td>
  			<c:if test="${!empty nextVo.title}">
  				▷ <a href="boardContent?idx=${nextVo.idx}">다음글 : ${nextVo.title}</a><br>  <!-- 페이지번호, 페이지크기 같이 전달 -->
  			</c:if>
  			<c:if test="${!empty preVo.title}">  			
  				◁ <a href="boardContent?idx=${preVo.idx}">이전글 : ${preVo.title}</a><br>
  			</c:if>
  		</td>
  	</tr>
  </table>
</div>
<p><br/></p>
<!-- 댓글 처리(리스트/입력) - 재사용 가능하도록 바깥으로 뺌 -->
<div class="container">
	<!-- 댓글 리스트 보여주기 -->
	<table class="table table-hover text-center">
		<tr>
			<th>작성자</th>
			<th>댓글내용</th>
			<th>댓글작성일</th>
			<th>접속IP</th>
		</tr>
		<c:forEach var="replyVo" items="${replyVos}" varStatus="st">
			<tr>
				<td>
					<c:if test="${sMid == replyVo.mid || sLevel == 0}">
						(<a href="javascript:replyDelete(${replyVo.idx})" title="댓글삭제"><i class="fa-solid fa-xmark"></i></a>)  <!-- 진짜 지울 것인지 거듭 물어봄 => js쓰라는 말 // title 마우스 올리면 뜨는 문구 -->
						<c:if test="${sNickName == replyVo.nickName}"><a href="#" onclick="replyEdit('${replyVo.idx}','${fn:replace(replyVo.content, newLine, '<br/>')}','${vo.idx}','${pag}','${pageSize}')" data-toggle="modal" data-target="#myModal2"><i class="fa-solid fa-pen-to-square"></i></a></c:if>
					</c:if>
          ${replyVo.nickName}
				</td>
				<td class="text-left">${fn:replace(replyVo.content,newLine,"<br>")}</td>  <!-- textarea가 아니라 브라우저에 출력하는 것이기 때문에 엔터키 처리를 해야함 newLine 엔터값(위에 set함) -->
				<td>${fn:substring(replyVo.wDate,0,10)}</td>
				<td>${replyVo.hostIp}</td>
			</tr>
		</c:forEach>
		<tr><td colspan="4" class="m-0 p-0"></td></tr>
	</table>
	
	<!-- 댓글 입력창 -->
	<form name="replyForm">
		<table class="table table-center">
			<tr>
				<td style="width:85%" class="text-left">
					댓글내용 : 
					<textarea rows="4" name="content" id="content" class="form-control"></textarea>
				</td>
				<td style="width:15%">
					<br>
					<p>작성자 : ${sNickName}</p>
					<p><input type="button" value="댓글입력" onclick="replyCheck()" class="btn btn-info btn-sm"></p>
				</td>
			</tr>
		</table>
	</form>
	<br>
</div>
<!-- 댓글 처리 -->

<!-- 신고하기 폼 모달창 -->
  <div class="modal fade" id="myModal">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">현재 게시글을 신고합니다.</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>  <!-- times는 x버튼? -->
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
        	<b>신고사유 선택</b>
        	<hr>
        	<form name="modalForm">
						<div><input type="radio" name="complaint" id="complaint1" value="광고,홍보,영리목적" />광고,홍보,영리목적</div>
						<div><input type="radio" name="complaint" id="complaint2" value="욕설,비방,차별,혐오" />욕설,비방,차별,혐오</div>
						<div><input type="radio" name="complaint" id="complaint3" value="불법정보" />불법정보</div>
						<div><input type="radio" name="complaint" id="complaint4" value="음란,청소년유해" />음란,청소년유해</div>
						<div><input type="radio" name="complaint" id="complaint5" value="개인정보노출,유포,거래" />개인정보노출,유포,거래</div>
						<div><input type="radio" name="complaint" id="complaint6" value="도배,스팸" />도배,스팸</div>
						<div><input type="radio" name="complaint" id="complaint7" value="기타" onclick="etcShow()" />기타</div>  <!-- 기타는 입력받도록 입력창을 만들어줌 -->
						<div id="etc"><textarea rows="2" id="complaintTxt" class="form-control" style="display:none"></textarea></div>  <!-- 처음에 안보이도록 -->
        		<hr>
        		<input type="button" value="확인" onclick="complaintCheck()" class="btn btn-danger from-control" />
        	</form>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
        
      </div>
    </div>
  </div>
  
<!-- 댓글 수정 폼 모달창 -->
  <div class="modal fade" id="myModal2">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
      
        <!-- Modal Header -->
        <div class="modal-header">
          <h4 class="modal-title">댓글 수정창</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
          <form name="modalForm BoardReplyEdit" method="post" action="BoardReplyEdit.bo">
              <textarea rows="4" name="modalContent" id="modalContent" class="form-control"></textarea>
              <input type="hidden" name="modalIdx" id="modalIdx" /> 
              <input type="hidden" name="boardIdx" id="boardIdx" /> 
              <input type="hidden" name="pag" id="pag" /> 
              <input type="hidden" name="pagSize" id="pagSize" /> 
              <input type="submit" value="수정하기" class="btn btn-success mr-2"/>
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