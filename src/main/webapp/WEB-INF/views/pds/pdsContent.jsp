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
  <%@ include file = "/WEB-INF/views/include/bs4.jsp" %>
  <script>
    'use strict';
    
    // 처음접속시는 '리뷰보이기'버튼을 감추고, '리뷰가리기'버튼과 '리뷰박스'를 보이게한다.
    // $(document).ready(function(){});
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
    
    // 리뷰 가리기
    function reviewHide() {
    	$("#reviewShowBtn").show();
    	$("#reviewHideBtn").hide();
    	$("#reviewBox").hide();
    }
    
    
    // 다운로드수 증가시키기
    function downNumCheck(idx) {
    	$.ajax({
    		url  : "${ctp}/pds/pdsDownNumCheck",
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
    
    // 화살표클릭시 화면 상단으로 부드럽게 이동하기
    $(window).scroll(function(){
    	if($(this).scrollTop() > 100) {
    		$("#topBtn").addClass("on");
    	}
    	else {
    		$("#topBtn").removeClass("on");
    	}
    	
    	$("#topBtn").click(function(){
    		window.scrollTo({top:0, behavior: "smooth"});
    	});
    });
    
    // 리뷰 삭제하기
    function reviewDelete(idx) {
    	let ans = confirm("리뷰를 삭제하시겠습니까?");
    	if(!ans) return false;
    	
    	$.ajax({
    		url  : "${ctp}/pds/reviewDelete",
    		type : "post",
    		data : {idx : idx},
    		success:function(res) {
    			if(res != "0") {
    				alert('리뷰가 삭제되었습니다.');
    				location.reload();
    			}
    			else alert("리뷰 삭제 실패~~");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    } 

    function reviewReply(idx, nickName, content) {
        $("#myModal #reviewIdx").val(idx);
        $("#myModal #reviewReplyNickName").text(nickName);
        $("#myModal #reviewReplyContent").html(content);
        $("#myModal #parentId").val(idx);
    }

    function reviewReplyCheck() {
        let replyContent = reviewReplyForm.replyContent.value;
        let parentId = reviewReplyForm.parentId.value;
        
        if (replyContent.trim() == "") {
            alert("리뷰 댓글을 입력하세요");
            return false;
        }
        
        let query = {
            pdsIdx : ${vo.idx},
            mid    : '${sMid}',
            nickName : '${sNickName}',
            content: replyContent,
            parentId: parentId,
            hostIp: '${pageContext.request.remoteAddr}'
        }
                
        $.ajax({
            url  : "${ctp}/pds/pdsReplyInput",
            type : "post",
            data : JSON.stringify(query),
            success:function(res) {
                if (res != "0") {
                    alert("댓글이 입력되었습니다.");
                    location.reload();
                } else {
                    alert("댓글 입력 실패~~");
                }
            },
            error : function() {
                alert("전송오류!");
            }
        });
    }

    function reviewCheck() {
        let star = starForm.star.value;
        let review = $("#review").val();
        
        if (star == "") {
            alert("별점을 부여해 주세요");
            return false;
        }
        
        let query = {
            pdsIdx : ${vo.idx},
            mid    : '${sMid}',
            nickName : '${sNickName}',
            star   : star,
            hostIp  : '${pageContext.request.remoteAddr}',
            content: review
        }
        
        $.ajax({
            url  : "${ctp}/pds/pdsReplyInput",
            type : "post",
            data : query,
            success:function(res) {
                if(res != "0") {
                    alert("댓글이 입력되었습니다.");
                    location.reload();
                }
                else alert("이미 별점을 등록하였습니다.\n별점 등록은 게시글당 1번만 가능합니다.");
            },
            error : function() {
                alert("전송오류!");
            }
        });
    }

  </script>
  <style>
    th {
      background-color: #eee;
      width: 15%;
      text-align: center;
    }
	h6 {
	  position: fixed;
	  right: 1rem;
	  bottom: -50px;
	  transition: 0.7s ease;
	}
   	.on {
		  opacity: 0.8;
		  cursor: pointer;
		  bottom: 0;
		}
		
    /* 별점 스타일 설정하기 */
    #starForm fieldset {
	    display: inline-block;
	    direction: rtl;
	    border:0;
    }
    #starForm input[type=radio] {
      display: none;
    }
    #starForm label {
      font-size: 1.6em;
      color: transparent;
      text-shadow: 0 0 0 #f0f0f0;
    }
    #starForm label:hover {
      text-shadow: 0 0 0 rgba(250, 200, 0, 0.98);
    }
    #starForm label:hover ~ label {
      text-shadow: 0 0 0 rgba(250, 200, 0, 0.98);
    }
    #starForm input[type=radio]:checked ~ label {
      text-shadow: 0 0 0 rgba(250, 200, 0, 0.98);
    }
    
    #reviewReplyForm {
	    width: 100%;
	    height: auto;
	    padding: 10px;
	    box-sizing: border-box;
	    border: solid 1.5px #D3D3D3;
	    border-radius: 5px;
	    font-size: 16px;
	    resize: none;
    }
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2 class="text-center">자료 내용 상세보기</h2>
  <br/>
  <table class="table table-bordered text-center">
    <tr>
      <th>올린이</th>
      <td>${vo.nickName}</td>
      <th>올린날짜</th>
      <td>${fn:substring(vo.FDate,0,fn:length(vo.FDate)-2)}</td>
    </tr>
    <tr>
      <th>파일명</th>
      <td>
        <c:set var="fNames" value="${fn:split(vo.FName,'/')}"/>
        <c:set var="fSNames" value="${fn:split(vo.FSName,'/')}"/>
        <c:forEach var="fName" items="${fNames}" varStatus="st">
          <a href="${ctp}/images/pds/${fSNames[st.index]}" download="${fName}" onclick="downNumCheck(${vo.idx})">${fName}</a><br/>
        </c:forEach>
        (<fmt:formatNumber value="${vo.FSize/1024}" pattern="#,##0" />KByte)
      </td>
      <th>다운횟수</th>
      <td>${vo.downNum}</td>
    </tr>
    <tr>
      <th>분류</th>
      <td>${vo.part}</td>
      <th>접속IP</th>
      <td>${vo.hostIp}</td>
    </tr>
    <tr>
      <th>제목</th>
      <td colspan="3" class="text-left">${vo.title}</td>
    </tr>
    <tr>
      <th>상세내역</th>
      <td colspan="3" class="text-left" style="height:150px">${fn:replace(vo.content,newLine,'<br/>')}</td>
    </tr>
    <tr>
      <td colspan="4">
        <input type="button" value="돌아가기" onclick="location.href='pdsList?pag=${pag}&pageSize=${pageSize}&part=${part}';" class="btn btn-primary"/>
      </td>
    </tr>
  </table>
  <hr/>
  <div>
    <!-- 댓글 작성 폼 -->
	<form name="starForm" id="starForm" method="post">
	  <fieldset style="border:0px;">
	    <div class="text-left viewPoint m-0 b-0">
	      <input type="radio" name="star" value="5" id="star1"><label for="star1"><i class="fas fa-star"></i></label>
	      <input type="radio" name="star" value="4" id="star2"><label for="star2"><i class="fas fa-star"></i></label>
	      <input type="radio" name="star" value="3" id="star3"><label for="star3"><i class="fas fa-star"></i></label>
	      <input type="radio" name="star" value="2" id="star4"><label for="star4"><i class="fas fa-star"></i></label>
	      <input type="radio" name="star" value="1" id="star5"><label for="star5"><i class="fas fa-star"></i></label>
	      <span class="mr-3" style="font-weight:bold;">[별점을 선택해주세요]</span>
	    </div>
	  </fieldset>
	  <div class="m-0 p-0">
	    <textarea rows="3" name="review" id="review" class="form-control mb-1" placeholder="별점 후기를 남겨주시면 100포인트를 지급합니다."></textarea>
	  </div>
	  <div>
	    <input type="button" value="별점/리뷰등록" onclick="reviewCheck()" class="btn btn-primary btn-sm form-control mb-4"/>
	  </div>
	</form>
  </div>
  <div id="reviewBox">
    <c:set var="imsiIdx" value="0"/>
    <c:forEach var="rVo" items="${rVos}" varStatus="st">
        <c:if test="${rVo.parentId == null}">
            <div class="row">
                <div class="col ml-2">
                    <b>${rVo.nickName}</b>
                    <span style="font-size:11px">${fn:substring(rVo.RDate, 0, 10)}</span>
                    <c:if test="${rVo.mid == sMid || sLevel == 0}">
                        <a href="javascript:reviewDelete(${rVo.idx})" title="리뷰삭제" class="badge badge-danger">x</a>
                    </c:if>
                    <a href="#" onclick="reviewReply('${rVo.idx}','${rVo.nickName}','${fn:replace(rVo.content,newLine,'<br>')}')" title="댓글달기" data-toggle="modal" data-target="#myModal" class="badge badge-secondary">▤</a>
                </div>
                <div class="col text-right mr-2">
                    <c:forEach var="i" begin="1" end="${rVo.star}" varStatus="iSt">
                        <font color="gold"><i class="fas fa-star"></i></font>
                    </c:forEach>
                    <c:forEach var="i" begin="1" end="${5 - rVo.star}" varStatus="iSt">☆</c:forEach>
                </div>
            </div>
            <div class="row border m-1 p-2" style="border-radius:5px">
                ${fn:replace(rVo.content, newLine, '<br/>')}
            </div>
            <c:forEach var="replyVo" items="${rVos}" varStatus="stReply">
                <c:if test="${replyVo.parentId == rVo.idx}">
                    <div class="d-flex text-secondary ml-4">
                        <div class="mt-2 ml-3">└─▶ </div>
                        <div class="mt-2 ml-2">${replyVo.nickName}
                            <span style="font-size:11px">${fn:substring(replyVo.RDate,0,10)}</span>
                            <c:if test="${replyVo.mid == sMid || sLevel == 0}">
                                <a href="javascript:reviewReplyDelete(${replyVo.idx})" title="댓글삭제" class="badge badge-danger">x</a>
                            </c:if>
                            <br/>${fn:replace(replyVo.content, newLine, '<br/>')}
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </c:if>
    </c:forEach>
</div>
  <!-- 자료실에 등록된 자료가 사진이라면, 아래쪽에 모두 보여주기 -->
  <div class="text-center">
		<c:forEach var="fSName" items="${fSNames}" varStatus="st">
			${fNames[st.index]}<br/>
			<c:set var="len" value="${fn:length(fSName)}"/>
		  <c:set var="ext" value="${fn:substring(fSName, len-3, len)}"/>
		  <c:set var="extLower" value="${fn:toLowerCase(ext)}"/>
			<c:if test="${extLower == 'jpg' || extLower == 'gif' || extLower == 'png'}">
        <img src="${ctp}/pds/${fSName}" width="85%" />
      </c:if>
      <hr/>
    </c:forEach>
  </div>
  
  <!-- 위로가기 버튼 -->
  <h6 id="topBtn" class="text-right mr-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동"/></h6>
</div>
<p><br/></p>

<!-- 대댓글 작성 폼 -->
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
        <form name="reviewReplyForm" id="reviewReplyForm" class="was-validated">
          <table class="table table-bordered">
            <tr>
              <th style="width:25%">원본글작성자</th>
              <td style="width:75%"><span id="reviewReplyNickName"></span></td>
            </tr>
            <tr>
              <th>원본글</th>
              <td><span id="reviewReplyContent"></span></td>
            </tr>
          </table>
          	※ 댓글 작성자 : ${sNickName}<br><br>
          	※ 댓글 내용<br> <textarea rows="3" name="replyContent" id="replyContent" class="form-control" required></textarea>
          <hr/>
          <input type="hidden" name="parentId" id="parentId" value="" />
          <input type="hidden" name="reviewIdx" id="reviewIdx" value="" />
          <input type="button" value="리뷰댓글등록" onclick="reviewReplyCheck()" class="btn btn-success form-control"/>
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