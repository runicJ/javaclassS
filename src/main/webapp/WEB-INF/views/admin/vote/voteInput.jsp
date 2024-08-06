<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <meta charset="UTF-8">
  <title>voteInput.jsp</title>
  <script>
    function fCheck() {        //  aa.bb.cc.xlsx
    	var file1 = myform.file1.value;  // 업로드 파일명
    	var file2 = myform.file2.value;
    	var file3 = myform.file3.value;
    	var file4 = myform.file4.value;
    	var file5 = myform.file5.value;
    	var ext = file1.substring(file1.lastIndexOf(".")+1);  // 확장자 발췌
    	var uExt = ext.toUpperCase();    // 확장자를 대문자로...
    	var maxSize = 1024 * 1024 * 10;  // 최대 10MByte
    	
    	var title = myform.title.value;  // 자료파일의 제목
    	
    	if(title == "") {
    		alert("자료파일의 제목을 입력하세요!");
    		myform.title.focus();
    		return false;
    	}
    	else if(file1 == "") {
    		alert("업로드할 첫번째 파일을 선택하세요!");
    		return false;
    	}
    	else if(file2 == "") {
    		alert("업로드할 두번째 파일을 선택하세요!");
    		return false;
    	}
    	else if(file3 == "") {
    		alert("업로드할 세번째 파일을 선택하세요!");
    		return false;
    	}
    	else if(file4 == "") {
    		alert("업로드할 네번째 파일을 선택하세요!");
    		return false;
    	}
    	else if(file5 == "") {
    		alert("업로드할 다섯번째 파일을 선택하세요!");
    		return false;
    	}
    	
    	var fileSize = document.getElementById("file1").files[0].size;
    	
    	if(uExt != "ZIP" && uExt != "JPG" && uExt != "GIF" && uExt != "PNG") {
    		alert("업로드 가능한 파일은 'zip/jpg/gif/png'파일만 가능합니다.");
    	}
    	else if(fileSize > maxSize) {
    		alert("업로드 파일의 최대용량은 10MByte입니다.");
    	}
    	else if(file1.indexOf(" ") != -1) {
    		alert("업로드할 파일명에는 공백을 포함할 수 없습니다.");
    	}
    	else {
    		//alert("파일을 전송합니다.");
    		myform.submit();
    	}
    }
  </script>
</head>
<body>
<%-- <%@ include file = "/WEB-INF/views/include/nav.jsp" %> --%>
<%@ include file = "/WEB-INF/views/include/bs4.jsp" %>
<p><br/></p>
<div class="container">
  <h2>자 료 올 리 기</h2>
  <form name="myform" method="post" enctype="multipart/form-data">
    <div class="form-group">
      <label for="part">투표항목분류 : </label>
      <select name="part" size=1 class="form-control">
        <option value="여행" selected>여행</option>
        <option value="학습">학습</option>
        <option value="건강">건강</option>
        <option value="기타">기타</option>
      </select>
    </div>
    <div class="form-group">
      <label for="title">현재 투표항목의 총괄 제목 : </label>
      <input type="text" name="title" id="title" placeholder="자료 제목을 입력하세요." class="form-control"/> 
    </div>
    <hr color="red"/>
    <div class="input-group mb-3">
	    <div class="input-group-prepend">
	      <span class="input-group-text">첫번째그림</span>
	    </div>
	    <input type="text" class="form-control" name="subTitle" placeholder="첫번째그림">
	  </div>
    <div class="form-group">
      <img src="${ctp}/data/vote/noimage.jpg" width="70px" id="preview-image1"/>
      <input type="file" name="file" id="file1" class="form-control-file border" accept=".jpg,.gif,.png"/>
    </div>
	  <hr/>
    <div class="input-group mb-3">
	    <div class="input-group-prepend">
	      <span class="input-group-text">두번째그림</span>
	    </div>
	    <input type="text" class="form-control" name="subTitle" placeholder="두번째그림">
	  </div>
    <div class="form-group">
      <img src="${ctp}/data/vote/noimage.jpg" width="70px" id="preview-image2"/>
      <input type="file" name="file" id="file2" class="form-control-file border" accept=".jpg,.gif,.png"/>
    </div>
	  <hr/>
    <div class="input-group mb-3">
	    <div class="input-group-prepend">
	      <span class="input-group-text">세번째그림</span>
	    </div>
	    <input type="text" class="form-control" name="subTitle" placeholder="세번째그림">
	  </div>
    <div class="form-group">
      <img src="${ctp}/data/vote/noimage.jpg" width="70px" id="preview-image3"/>
      <input type="file" name="file" id="file3" class="form-control-file border" accept=".jpg,.gif,.png"/>
    </div>
	  <hr/>
    <div class="input-group mb-3">
	    <div class="input-group-prepend">
	      <span class="input-group-text">네번째그림</span>
	    </div>
	    <input type="text" class="form-control" name="subTitle" placeholder="네번째그림">
	  </div>
    <div class="form-group">
    	<img src="${ctp}/data/vote/noimage.jpg" width="70px" id="preview-image4"/>
      <input type="file" name="file" id="file4" class="form-control-file border" accept=".jpg,.gif,.png"/>
    </div>
	  <hr/>
    <div class="input-group mb-3">
	    <div class="input-group-prepend">
	      <span class="input-group-text">다섯째그림</span>
	    </div>
	    <input type="text" class="form-control" name="subTitle" placeholder="다섯째그림">
	  </div>
    <div class="form-group">
    	<img src="${ctp}/data/vote/noimage.jpg" width="70px" id="preview-image5"/>
      <input type="file" name="file" id="file5" class="form-control-file border" accept=".jpg,.gif,.png"/>
    </div><br/>
    <div class="form-group">
      <button type="button" onclick="fCheck()" class="btn btn-secondary">투표항목등록</button>
      <button type="reset" class="btn btn-secondary">다시쓰기</button>
      <button type="button" class="btn btn-secondary" onclick="location.href='${ctp}/vote/aVoteList';">돌아가기</button>
    </div>
    <input type="hidden" name="pag" value="${pag}"/>
  </form>
  
  <script>
    // 첫번째 이미지 미리보기 처리
    // input type='file'에 change 이벤트 부여시켜두고 감시한다. 
	  const inputImage1 = document.getElementById("file1");
	  inputImage1.addEventListener("change", e => {
	  	readImage1(e.target);
	  });
	  // 1번째 파일에 대해서 이미지 미리보기 처리
	  function readImage1(input) {
		  // input type='file' 태그에 파일이 있는 경우
	  	if(input.files && input.files[0]) {
	  		// 이곳에서 이미지 파일인지를 먼저 검사하는것이 좋다.
	  		
	  		// FileReader 객체 생성
	  		const reader = new FileReader();
	  		
	  		// 이미지가 Load된 경우 처리..
	  		reader.onload = e => {
	  			const previewImage1 = document.getElementById("preview-image1");
	  			previewImage1.src = e.target.result;
	  		}
	  		// reader가 이미지 읽도록 처리한다.
	  		reader.readAsDataURL(input.files[0]);
	  	}
	  }
	  
	  
	  // 2번째 이미지 미리보기 처리....
	  const inputImage2 = document.getElementById("file2");
	  inputImage2.addEventListener("change", e => {
	  	readImage2(e.target);
	  });
	  function readImage2(input) {
	  	if(input.files && input.files[0]) {
	  		const reader = new FileReader();
	  		
	  		reader.onload = e => {
	  			const previewImage2 = document.getElementById("preview-image2");
	  			previewImage2.src = e.target.result;
	  		}
	  		reader.readAsDataURL(input.files[0]);
	  	}
	  }
	  
	  // 3번째 이미지 미리보기 처리
	  const inputImage3 = document.getElementById("file3");
	  inputImage3.addEventListener("change", e => {
	  	readImage3(e.target);
	  });
	  function readImage3(input) {
	  	if(input.files && input.files[0]) {
	  		const reader = new FileReader();
	  		
	  		reader.onload = e => {
	  			const previewImage3 = document.getElementById("preview-image3");
	  			previewImage3.src = e.target.result;
	  		}
	  		reader.readAsDataURL(input.files[0]);
	  	}
	  }
	  
	  // 4번째 이미지 미리보기 처리
	  const inputImage4 = document.getElementById("file4");
	  inputImage4.addEventListener("change", e => {
	  	readImage4(e.target);
	  });
	  function readImage4(input) {
	  	if(input.files && input.files[0]) {
	  		const reader = new FileReader();
	  		
	  		reader.onload = e => {
	  			const previewImage4 = document.getElementById("preview-image4");
	  			previewImage4.src = e.target.result;
	  		}
	  		reader.readAsDataURL(input.files[0]);
	  	}
	  }
	  
	  // 5번째 이미지 미리보기 처리
	  const inputImage5 = document.getElementById("file5");
	  inputImage5.addEventListener("change", e => {
	  	readImage5(e.target);
	  });
	  function readImage5(input) {
	  	if(input.files && input.files[0]) {
	  		const reader = new FileReader();
	  		
	  		reader.onload = e => {
	  			const previewImage5 = document.getElementById("preview-image5");
	  			previewImage5.src = e.target.result;
	  		}
	  		reader.readAsDataURL(input.files[0]);
	  	}
	  }
	  
  </script>
  
</div>
</body>
</html>