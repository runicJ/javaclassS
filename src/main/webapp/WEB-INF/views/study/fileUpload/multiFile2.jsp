<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>multiFile.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <style>
    .imgsWrap {
      border: 2px solid #eee;
      margin: 10px;
    }
    
    .imgsWrap img {
      width: 150px;
      hieght: auto;
      margin: 5px 10px;
    }
    
    .myBtn {
      width:200px;
      padding: 10px;
      background-color: #09A;
      color: #fff;
      border-radius: 5px;
    }
  </style>
  <script>
  	'use strict';
  	
  	let imgFiles = [];
  	
  	$(document).ready(function(){  // (document).ready 생략 가능
  		$("#inputImgs").on("change", function(e){
  			//imgFiles = [];
  			$(".imgsWrap").empty();
  			
  			let files = e.target.files;
  			let filesArr = Array.prototype.slice.call(files);  // 배열객체에 각각 잘라 분리해서 넣음
  			
  			let idx = 0;  // 그림파일인지 체크
  			filesArr.forEach(function(f) {
  				if(!f.type.match("image.*")) {  // mime 타입에 있는 모든 파일과 일치하니
  					alert("이미지 파일만 업로드 하실 수 있습니다.");
  					return false;
  				}
  				imgFiles.push(f);
  			
  			let reader = new FileReader()  // 그림파일을 랜더링 시켜서 브라우저 화면에 뿌림
  			reader.onload = function(e) {
  				let str = "<a href='javascript:void(0);' onclick='deleteImage("+idx+")' id='imgId"+idx+"'><img src='"+e.target.result+"' data-file='"+f.name+"' class='' title='선택한 객체를 클릭하시면 해당 사진이 제거됩니다'/></a>";  // 묶기 위해 준 변수  // 여러개의 파일중 하나를 지정해서 삭제하는 코드를 여기에 작성
  				$(".imgsWrap").append(str);  // html 하면 안됨 덮어쓰여짐, append로 해서 넣어줌
    			idx++;
  			}
				reader.readAsDataURL(f);
  			});
  		});
		});
  	
  	function deleteImage(idx)	{
/* 	  	imgFiles.slice(idx,1);  // slice도 됨
	  	
			let imgId = "#imgId"+idx;
	  	$(imgId).remove(); */
	  	
	  	imgFiles.splice(idx,1);
  	}
  	
    function imageUpload() {
    	$("#inputImgs").trigger('click');
    }
    
    function fCheck() {
    	if(imgFiles.length < 1) {
    		alert("한개 이상의 파일을 선택해 주세요");
    		return;
    	}
    	
    	let imgNames = "";
    	for(let i=0; i<imgFiles.length; i++) {
    		imgNames += imgFiles[i].name + "/";
    	}
    	imgNames = imgNames.substring(0, imgNames.length-1);
    	$("#imgNames").val(imgNames);
    	myform.submit();
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>멀티(그림)파일 업로드 연습</h2>
  <form name="myform" method="post" enctype="multipart/form-data">
    <a href="javascript:imageUpload()" class="myBtn">이미지불러오기</a> 
    <input type="file" name="fName" id="inputImgs" multiple accept=".jpg,.gif,.png,.zip,.ppt,.pptx,.hwp"/>
    <p class="mt-3">
      <input type="button" value="파일업로드" onclick="fCheck()" class="btn btn-success"/>
      <input type="reset" value="다시선택" class="btn btn-warning"/>
      <input type="button" value="싱글파일업로드로이동(파일리스트)" onclick="location.href='${ctp}/study/fileUpload/fileUpload';" class="btn btn-primary"/>
      <input type="button" value="멀티파일업로드로이동" onclick="location.href='${ctp}/study/fileUpload/multiFile';" class="btn btn-secondary"/>
    </p>
    <input type="hidden" name="imgNames" id="imgNames"/>
  </form>
  <hr/>
  <div>
    <div class="imgsWrap">
      <img id="img" />
    </div>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>