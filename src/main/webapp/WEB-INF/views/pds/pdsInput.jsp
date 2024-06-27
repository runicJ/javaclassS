<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>pdsInput.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
    'use strict';
    let cnt = 1;
    
/* 		$("#fName").on("change", function(e){
			//imgFiles = [];
			$("#fileBox").empty();
			
			let files = e.target.fName;
			let filesArr = Array.prototype.slice.call(files);
		)}
		fileBox.push(files); */
    
    function fCheck() {
  		let fName = $("#fName").val();
    	
    	// 파일 사이즈와 확장자 체크하기
			/* let fileSize = 0;
    	for(let i=1; i<=cnt; i++) {
    		let imsiName = 'fName' + i;  // id 첫번째 것
    		if(isNaN(document.getElementById(imsiName))) {  // isNaN 숫자가 아니냐? 즉 문자냐? 문자면 값을 가져와서 밑에 누적하고 있음
    			let fName = document.getElementById(imsiName).value;
    			if(fName != "") {
    				fileSize += document.getElementById(imsiName).files[0].size;
			    	let ext = fName.substring(fName.lastIndexOf(".")+1).toLowerCase();
			    	if(ext != 'jpg' && ext != 'gif' && ext != 'png' && ext != 'zip' && ext != 'hwp' && ext != 'ppt' && ext != 'pptx' && ext != 'doc' && ext != 'pdf' && ext != 'xlsx' && ext != 'txt') {
			    		alert("업로드 가능한 파일은 'jpg/gif/png/zip/hwp/ppt/pptx/doc/pdf/xlsx/txt'만 가능합니다.");
			    		return false;  // 확장자가 하나라도 안맞으면 탈주
			    	}
    			}
    		}
    	}
    	*/
/*     	if(fileSize > maxSize) {
    		alert("업로드할 1개 파일의 최대용량은 30MByte입니다.");
    		return false;
    	}
    	else { */
    		//myform.fSize.value = fileSize;
    		//alert("파일 총 사이즈 : " + fileSize);
    		myform.submit();
    	//}	
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br></p>
<div class="container">
  <form name="myform" method="post" action="${ctp}/pds/pdsInput" enctype="multipart/form-data">
  	<h2 class="text-center">자 료 올 리 기</h2>
  	<br>
  	<div>
    	<input type="file" name="fName" multiple class="border mb-2" accept=".jpg,.gif,.png,.zip,.ppt,.pptx,.hwp" />
    </div>
    <div id="fileBox"></div>
    <div class="mb-2">
    	올린이 : ${sNickName}
    </div>
    <div class="mb-2">
    	제목 : <input type="text" name="title" id="title" placeholder="자료의 제목을 입력하세요" class="form-control" required />  <!-- placeholder나 value 둘 중에 하난 써야 -->
    </div>
    <div class="mb-2">
    	내용 : <textarea rows="4" name="content" id="content" placeholder="자료의 상세내역을 입력하세요" class="form-control"></textarea>
    </div>
    <div class="mb-2">
    	분류 : 
			<select name="part" id="part" class="form-control">
				<option ${part=="학습" ? "selected" : ""}>학습</option>
				<option ${part=="여행" ? "selected" : ""}>여행</option>
				<option ${part=="음식" ? "selected" : ""}>음식</option>
				<option ${part=="기타" ? "selected" : ""}>기타</option>
			</select>
    </div>
    <div class="mb-2">
    	공개여부 : 
    	<input type="radio" name="openSw" value="공개" checked />공개 &nbsp;&nbsp;
    	<input type="radio" name="openSw" value="비공개" />비공개
    </div>
    <div>
    	<input type="submit" value="자료올리기" class="btn btn-success" />
    	<input type="reset" value="다시쓰기" class="btn btn-warning" />
    	<input type="button" value="돌아가기" onclick="location.href='pdsList?part=${part}';" class="btn btn-info" />
    </div>
    <input type="hidden" name="hostIp" value="${pageContext.request.remoteAddr}" />
    <input type="hidden" name="mid" value="${sMid}" />
    <input type="hidden" name="nickName" value="${sNickName}" />
    <input type="hidden" name="fSize" value="0" />
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>