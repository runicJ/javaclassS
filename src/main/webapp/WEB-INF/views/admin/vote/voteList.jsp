<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<%
  String realUrl = application.getRealPath("/");
  request.setAttribute("realUrl", realUrl);
%>
<%-- -${realUrl} --%>
<html>
<head>
  <meta charset="UTF-8">
  <title>voteList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
    function partCheck() {
    	var part = partForm.part.value;
    	location.href = "${ctp}/vote/aVoteList?pag=${pag}&part="+part;
    }
    
    // 문항보기, 문항접기 버튼 클릭시 수행내용
    $(document).ready(function() {
	  //  $("#votePopularHide").hide();
    	$(".votePopularShowBtn2").hide();
    	$(".votePopularHide").hide();
    	$(".resultHide").hide();
    });
    
    function votePopularShow(idx) {
    	$("#voteQuestion").show();
    	$("#votePopularHide").show();
    	var query = {idx : idx};
    	$.ajax({
    		url  : "${ctp}/vote/votePopularView",
    		type : "get",
    		data : query,
    		success : function(data) {
   			  //var imsiVotePopularHide = "#voteQuestion"+data.idx;
    			var insRow = "";
    			if(data != null) {
    				var subTitles = data.subTitle.split("/");
    			  var realImgs = data.realImg.split("/");
    			  var thumbImgs = data.thumbImg.split("/");
    			  //console.log(thumbImgs[0]);
    			  insRow += "<form name='voteForm' method='post'>";
    			  insRow += "<table style='width:100%;text-align:center'>";
    			  insRow += "<tr>";
    			  insRow += "<c:forEach var='i' begin='0' end='4'>";
    			  insRow += "<td><a href='javascript:imgView(\""+realImgs[${i}]+"\")' title=\""+subTitles[${i}]+"\"><img src='${ctp}/vote/"+thumbImgs[${i}]+"'/></a></td>";
    			  insRow += "</c:forEach>";
    			  insRow += "</tr><tr>";
    			  insRow += "<c:forEach var='i' begin='0' end='4'>";
    			  insRow += "<td style='text-align:center'><input type='radio' name='popular"+data.idx+"' value='${i}' onclick='voteCheck("+data.idx+",${i})'/></td>";
    			  insRow += "</c:forEach>";
    			  insRow += "</tr>";
    				insRow += "</table>";
    				insRow += "</form>";
	   			  var imsiVoteQuestion = "#voteQuestion"+data.idx;
						$(imsiVoteQuestion).html(insRow);
    			  var imsiVotePopularShowBtn = "#votePopularShowBtn"+data.idx; 
						$(imsiVotePopularShowBtn).hide();
    			  var imsiVotePopularShowBtn2 = "#votePopularShowBtn2"+data.idx; 
						$(imsiVotePopularShowBtn2).hide();
						var imsiVotePopularHide = "#votePopularHide"+data.idx;
						$(imsiVotePopularHide).show();
    			}
    		},
    		error : function(data) {
    			alert("검색실패!");
    		}
    	});
    }
    
    // 그림 클릭시 큰 그림으로 보기
    function imgView(tImg) {
    	var url = tImg;
      var imgObj = new Image();
      imgObj.src = "${ctp}/vote/"+url;
      imageWin = window.open("", "profile_popup", "width=" + imgObj.width + "px, height=" + imgObj.height + "px"); 
      imageWin.document.write("<html><body style='margin:0'>"); 
      imageWin.document.write("<a href=javascript:window.close()><img src='" + imgObj.src + "' border=0></a>"); 
      imageWin.document.write("</body><html>"); 
      imageWin.document.title = imgObj.src;
    }
    
    // 문항접기
    function votePopularHide(idx) {
    	var voteQuestionIdx = "#voteQuestion"+idx;
    	var votePopularShowBtn2 = "#votePopularShowBtn2"+idx;
    	var imsiVotePopularHide = "#votePopularHide"+idx;
    	$(voteQuestionIdx).hide();
    	$(votePopularShowBtn2).show();
    	$(imsiVotePopularHide).hide();
    }
    
    // 문항보기
    function votePopularShow2(idx) {
    	var voteQuestionIdx = "#voteQuestion"+idx;
    	$(voteQuestionIdx).show();
    	var imsiVotePopularShowBtn2 = "#votePopularShowBtn2"+idx; 
			$(imsiVotePopularShowBtn2).hide();
    	var imsiVotePopularHide = "#votePopularHide"+idx;
		  $(imsiVotePopularHide).show();
    }
    
    // 투표할경우 수행
    function voteCheck(idx, sel) {
    	if('${slevel}' == 0) {
	    	// 관리자는 관리자하면에서는 투표처리 불가함...
	    	var ans = confirm(idx+"번 설문지 항목중"+"\n"+(sel+1)+" 번째 사진을 선택하셨습니다.\n관리자화면에서는 투표하실 수 없습니다.");
	    	return false;
    	}    	
    	else if(!'${sVotePersonInforIdx}') {
    		location.href = "${ctp}/vote/aVoteInforInsert?mid=${sMid}";
	    	return false;
    	}
    	/* var ans = confirm(idx+"번 설문지 항목중"+"\n"+(sel+1)+" 번째 사진을 선택하셨습니다.\n투표 하시겠습니까?"); */
    }
    
    // 문항 삭제
    function votePopularDelete(idx,realImg,thumbImg) {
    	var ans = confirm("선택문항을 삭제하시겠습니까?");
    	if(!ans) {
    		return false;
    	}
    	var query = {
    			idx : idx,
    			realImg:realImg,
    			thumbImg:thumbImg
    	};
    	
    	$.ajax({
    		url  : "${ctp}/vote/votePopularDelete",
    		type : "get",
    		data : query,
    		success : function(data) {
    			alert("항목이 삭제되었습니다.");
    			location.reload();
    		},
    		error : function() {
    			alert("항목이 삭제되지 않았습니다.");
    		}
    	});
    }
    
    // 결과보기
    function votePopularShowResult(idx,voteCnt) {
    	var voteResultHideBtn = "#voteResultHide"+idx;
    	var voteResultShowBtn = "#voteResultShow"+idx;
    	var votePiechart = "#piechart"+idx;
    	$(voteResultHideBtn).show();
    	$(voteResultShowBtn).hide();
    	$(votePiechart).show();
    	
    	var query = {idx : idx};
    	$.ajax({
    		url  : "${ctp}/vote/voteResultView",
    		type : "get",
    		data : query,
    		success : function(data) {
    			var subTitles = data.pVo.subTitle.split("/");
    			var pie = "''pie''";
    			var insResult = "<input type='button' class='btn btn-secondary' value='차트분석보기' onclick='chartAnalysis("+idx+",\"pie\")' style='float:right'/>";
    			var voteNum = 0;
    			for(var i=0; i<data.vos.length; i++) {
   			    //insResult += "<div style='text-align:left;text-indent:30px;'>"+data.vos[i].voteNum+" : "+data.vos[i].voteSum+" : "+subTitles[i]+"</div>";
   			    var voteSum = data.vos[i].voteSum / voteCnt * 100;
   			    insResult += "<div style='text-align:left;font-size:0.8em;'>투표자 : "+data.vos[i].voteSum+"명("+voteSum.toFixed(1)+"%) - "+subTitles[i]+"</div>";
   			    insResult += "<div style='height:25px;'>"
   			    insResult += "<div class='progress'>";
   			    insResult += "<span class='progress-bar bg-info' style='width:"+(voteSum*5)+"'>"+subTitles[i]+"("+data.vos[i].voteSum+"명)"+"</span>";
   			    insResult += "</div></div>";
    			}
				  var imsiVoteResult = "#voteResult"+idx;
					$(imsiVoteResult).html(insResult);
    		}
    	});

    }
    // 결과가리기
    function votePopularHideResult(idx) {
    	var voteResultHideBtn = "#voteResultHide"+idx;
    	var voteResultShowBtn = "#voteResultShow"+idx;
    	$(voteResultHideBtn).hide();
    	$(voteResultShowBtn).show();
    	var imsiVoteResult = "#voteResult"+idx;
		  //$(imsiVoteResult).hide();
    	location.reload();
    }
    
    // 분석용 차트 새창으로 띄우기
    function chartAnalysis(idx,chartFlag) {
    	var url = "${ctp}/vote/chartAnalysis?idx="+idx+"&chartFlag="+chartFlag;
    	var winX = 620;
    	var winY = 510;
    	var x = (window.screen.width)/2 - winX/2;
    	var y = (window.screen.height)/2 - winY/2;
    	window.open(url, "chartWindow","width="+winX+",height="+winY+",left="+x+",top="+y);
    }
    
    // 진행중/진행중지 표시유무
    function progressCheck(idx) {
    	var progress = document.getElementById("progress"+idx).value;
    	$.ajax({
    		type : "get",
    		url  : "${ctp}/vote/voteProgress",
    		data : {
    			idx : idx,
    			progress : progress
    		},
    		success : function(data) {
    			alert("변경완료!!");
    			location.reload();
    		}
    	});
    }
  </script>
</head>
<body>
<p><br/></p>
<div class="mr-2">
  <table class="table table-borderless" style="margin:0px;padding:0px;">
    <tr>
      <td colspan="2" style="text-align:center"><h2>설문자료 리스트</h2></td>
    </tr>
    <tr>
      <td style="text-align:left; border:none; width:15%;">
        <form name="partForm">
          <select name="part" size="1" onchange="partCheck();" class="form-control">
            <option value="전체" ${pageVO.part=='전체' ? 'selected' : ''}>전체</option>
            <option value="여행" ${pageVO.part=='여행' ? 'selected' : ''}>여행</option>
            <option value="학습" ${pageVO.part=='학습' ? 'selected' : ''}>학습</option>
            <option value="건강" ${pageVO.part=='건강' ? 'selected' : ''}>건강</option>
            <option value="기타" ${pageVO.part=='기타' ? 'selected' : ''}>기타</option>
          </select>
        </form>
      </td>
      <td style="text-align:right">
        <button type="button" onclick="location.href='${ctp}/vote/aVoteInput';" class="btn btn-secondary">설문등록</button>
      </td>
    </tr>
  </table>
  <table class="table table-hover">
    <thead class="thead-light">
      <tr style="text-align:center">
        <th>설문번호</th>
        <th>설문제목</th>
        <th>분류</th>
        <th>참여자수</th>
        <th>문항보기</th>
        <th>진행여부</th>
      </tr>
    </thead>
    <tbody>
      <c:set var="curScrStrarNo" value="${pageVO.curScrStrarNo}"/>
      <c:forEach var="vo" items="${vos}">
        <tr class="m-0 p-0" style="text-align:center">
          <td>${curScrStrarNo}</td>
          <td><b>${vo.title}</b></td>
          <td>${vo.part}</td>
          <td>${vo.voteCnt}</td>
          <td>
            <input type="button" value="문항보기" id="votePopularShowBtn${vo.idx}" class="btn btn-secondary btn-sm showBtn" onclick="votePopularShow('${vo.idx}')"/>
            <input type="button" value="문항보기" id="votePopularShowBtn2${vo.idx}" class="btn btn-secondary btn-sm showBtn votePopularShowBtn2" onclick="votePopularShow2('${vo.idx}')"/>
            <input type='button' value='문항접기' id='votePopularHide${vo.idx}' class='btn btn-secondary btn-sm votePopularHide' onclick='votePopularHide(${vo.idx})'/>
            <%-- <input type="button" value="투표결과" class="btn btn-secondary btn-sm" onclick="votePopularResult('${vo.idx}','${vo.thumbImg}')"/> --%>
            <input type="button" value="문항삭제" class="btn btn-secondary btn-sm" onclick="votePopularDelete('${vo.idx}','${vo.realImg}','${vo.thumbImg}')"/>
            <input type="button" value="결과보이기" id='voteResultShow${vo.idx}' class="btn btn-secondary btn-sm resultShow" onclick="votePopularShowResult('${vo.idx}','${vo.voteCnt}')"/>
            <input type="button" value="결과가리기" id='voteResultHide${vo.idx}' class="btn btn-secondary btn-sm resultHide" onclick="votePopularHideResult('${vo.idx}')"/>
          </td>
          <td>
            <select name="progress" id="progress${vo.idx}" onchange="progressCheck(${vo.idx})">
              <option value="ON"  <c:if test="${vo.progress=='ON'}" >selected</c:if>>진행중</option>
              <option value="OFF" <c:if test="${vo.progress=='OFF'}">selected</c:if>>진행중단</option>
            </select>
          </td>
        </tr>
		    <tr class="m-0 p-0">
		      <td colspan="6" class="m-0 p-0" style="text-align:center"><div id="voteQuestion${vo.idx}"></div></td>
		    </tr>
		    <tr class="m-0 p-0">
		      <td colspan="6" class="m-0 p-0" style="text-align:center;margin:0px;padding:0px;"><div id="voteResult${vo.idx}"></div></td>
		    </tr>
		    <%-- 
		    <tr>
		      <td colspan="6" class="m-0 p-0" style="text-align:center;margin:0px;padding:0px;"><div id="piechart${vo.idx}"></div></td>
		    </tr>
		     --%>
        <c:set var="curScrStrarNo" value="${curScrStrarNo - 1}"/>
      </c:forEach>
    </tbody>
  </table>
</div>
<div class="container text-center"><c:if test="${pageVO.totRecCnt == 0}">설문 자료가 없습니다.</c:if></div>
<c:if test="${pageVO.totRecCnt != 0}">
	<!-- 아래로 블록페이징 처리 -->
	<div class="container">
	  <c:set var="startPageNum" value="${pageVO.pag - (pageVO.pag-1)%pageVO.blockSize}"/>
	  <ul class="pagination justify-content-center pagination-sm">
	    <c:if test="${pageVO.pag != 1}">
	      <li class="page-item"><a href="aVoteList?pag=1&pageSize=${pageVO.pageSize}" class="page-link" style="color:#777">첫페이지</a></li>
	      <li class="page-item"><a href="aVoteList?pag=${pageVO.pag-1}&pageSize=${pageVO.pageSize}" class="page-link" style="color:#777">이전</a></li>
	    </c:if>
	    <c:forEach var="i" begin="0" end="2">  <!-- 하나의 블록페이지의 갯수를 3개로 지정함. -->
	      <c:if test="${(startPageNum+i) <= pageVO.totPage}">
	        <c:if test="${pageVO.pag == (startPageNum+i)}">
	          <li class="page-item"><a href="aVoteList?pag=${startPageNum+i}&pageSize=${pageVO.pageSize}" class="page-link btn btn-secondary active" style="color:#fff">${startPageNum+i}</a></li>
	        </c:if>
	        <c:if test="${pageVO.pag != (startPageNum+i)}">
	          <li class="page-item"><a href="aVoteList?pag=${startPageNum+i}&pageSize=${pageVO.pageSize}" class="page-link" style="color:#777">${startPageNum+i}</a></li>
	        </c:if>
	      </c:if>
	    </c:forEach>
	    <c:if test="${pageVO.pag != pageVO.totPage}">
	      <li class="page-item"><a href="aVoteList?pag=${pageVO.pag+1}&pageSize=${pageVO.pageSize}" class="page-link" style="color:#777">다음</a></li>
	      <li class="page-item"><a href="aVoteList?pag=${pageVO.totPage}&pageSize=${pageVO.pageSize}" class="page-link" style="color:#777">마지막페이지</a></li>
	    </c:if>
	  </ul>
	</div>
	<!-- 여기까지 블록페이징 처리 -->
</c:if>
<p><br/></p>
</body>
</html>