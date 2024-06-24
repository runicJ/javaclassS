<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- https://tadaktadak-it.tistory.com/96 // https://haaland09009.tistory.com/226 // https://velog.io/@ansalstmd/JSP8.-%EC%9C%A0%ED%9A%A8%EC%84%B1-%EA%B2%80%EC%82%AC --%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>memberJoin.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script src="${ctp}/resources/js/woo.js"></script>
  <script>
    'use strict';
    
    let idCheckSw = 0;  // 버튼 활성화 => 1로 바꾸고 아이디 고쳐도 0으로
    let nickCheckSw = 0;  // 둘다 1이 되어 있어야 submit 되도록
    

    function fCheck() {
    	// 유효성 검사.....(정규식-버튼체크-묶어서-담아서 submit)
    	// 아이디,닉네임,성명,이메일,홈페이지,전화번호,비밀번호 등등....
    	
    	// 정규식을 이용한 유효성검사처리.....
    	let regMid = /^[a-zA-Z0-9_]{4,20}$/;	// 아이디는 4~20의 영문 대/소문자와 숫자와 밑줄 가능
      let regNickName = /^[가-힣]+$/;					// 닉네임은 한글만 가능
      let regName = /^[가-힣a-zA-Z]+$/;				// 이름은 한글/영문 가능
      
    	
    	// 검사를 끝내고 필요한 내역들을 변수에 담아 회원가입 처리한다.
    	// 변수에 저장(위에서 해도됨)
    	let mid = myform.mid.value.trim();
    	let pwd = myform.pwd.value.trim();
    	let nickName = myform.nickName.value;
    	let name = myform.name.value;
    	
    	let email1 = myform.email1.value.trim();
    	let email2 = myform.email2.value;  // 콤보상자 선택 trim()안해도 됨
    	let email = email1 + "@" + email2;  // 결합 , 구분자 넣기
    	
    	let postcode = myform.postcode.value + " ";  // 공백 한칸으로 내용이 있는 것 처럼  // / 넣을 것 // db에 null 허용 설정 값이 없으면 ////로 들어가도록
    	let roadAddress = myform.roadAddress.value + " ";
    	let detailAddress = myform.detailAddress.value + " ";
    	let extraAddress = myform.extraAddress.value + " ";
    	let address = postcode + "/" + roadAddress + "/" + detailAddress + "/" + extraAddress + "/";  // 위에서 공백을 주지 않으면 값이 없을 경우 나중에 /기준으로 split 했을때(////) 안 나눠짐 하나로 봄(front에서 해주기)
    	
    	if(!regMid.test(mid)) {
    		alert("아이디는 4~20자리의 영문 소/대문자와 숫자, 언더바(_)만 사용가능합니다.");
    		myform.mid.focus();
    		return false;
    	}
    	else if(pwd.length < 4 && pwd.length > 20) {
        alert("비밀번호는 4~20 자리로 작성해주세요.");
        myform.pwd.focus();
        return false;
      }
      else if(!regNickName.test(nickName)) {
        alert("닉네임은 한글만 사용가능합니다.");
        myform.nickName.focus();
        return false;
      }
      else if(!regName.test(name)) {
        alert("성명은 한글과 영문대소문자만 사용가능합니다.");
        myform.name.focus();
        return false;
      }
  		// 이메일 주소형식체크
  		
  		// 홈페이지 주소형식체크
  		
  		// 전화번호 형식 체크
  		let tel1 = myform.tel1.value;
    	let tel2 = myform.tel2.value.trim();
    	let tel3 = myform.tel3.value.trim();
    	let tel = tel1 + "-" + tel2 + "-" + tel3;
    	
  		if(tel2 != "" && tel3 != "") {
  			// 전화번호 형식 정규화 체크
  		}
  		else {
  			tel2 = " ";
  			tel3 = " ";
  			tel = tel1 + "-" + tel2 + "-" + tel3;
  		}
  		
  		// 전송 전에 파일에 관련된 사항들을 체크해준다.=> 프론트체크
  		let fName = document.getElementById("file").value;
  		
  		if(fName.trim() != "") {
  			let ext = fName.substring(fName.lastIndexOf(".")+1).toLowerCase(); 
	  		let maxSize = 1024 * 1024 * 20;
	  		let fileSize = document.getElementById("file").files[0].size;
	  		
	  		if(ext != 'jpg' && ext != 'gif' && ext != 'png' && ext != 'jpeg') {
	  			alert("그림파일만 업로드 가능합니다.");
	  			return false;
	  		}
	  		else if(fileSize > maxSize) {
	  			alert("업로드할 파일의 최대용량은 20Mbyte입니다.");
	  			return false;
	  		}	  			
  		}
			  		
    	// 아이디/닉네임 중복체크
    	if(idCheckSw == 0) {
    		alert("아이디 중복체크 버튼을 눌러주세요");
    		document.getElementById("midBtn").focus();
    	}
    	else if(nickCheckSw == 0) {
    		alert("닉네임 중복체크 버튼을 눌러주세요");
    		document.getElementById("nickNameBtn").focus();
    	}
    	else {
    		myform.email.value = email;
    		myform.tel.value = tel;
    		myform.address.value = address;
    		    		    		
    		myform.submit();
    	}
    }
		
    // 아이디 중복체크
    function idCheck() {
    	let mid = myform.mid.value;
    	
    	if(mid.trim() == "") {
    		alert("아이디를 입력하세요!");
    		myform.mid.focus();
    	}
    	else {
    		idCheckSw = 1;
    		
	    	$.ajax({
	    		url : "${ctp}/member/memberIdCheck",
	    		type : "get",
	    		data : {mid : mid},
	    		success:function(res) {
	    			if(res != '0') {
	    				alert("이미 사용중인 아이디입니다. 다시 입력하세요.");
	    				myform.mid.focus();
	    			}
	    			else {
	    				alert("사용 가능한 아이디입니다.");
	    				$("#midBtn").attr("disabled",true);
	    				myform.pwd.focus();
	    			}
	    		},
	    		error:function() {
	    			alert("전송 오류!");
	    		}
	    	});
    	}
    }
    
    // 닉네임 중복체크
    function nickCheck() {
    	let nickName = myform.nickName.value;
    	
    	if(nickName.trim() == "") {
    		alert("닉네임을 입력하세요!");
    		myform.nickName.focus();
    	}
    	else {
    		nickCheckSw = 1;
    		
	    	$.ajax({
	    		url : "${ctp}/member/memberNickCheck",
	    		type : "get",
	    		data : {nickName : nickName},
	    		success:function(res) {
	    			if(res != 0) {
	    				alert("이미 사용중인 닉네임입니다. 다시 입력하세요.");
	    				myform.nickName.focus();
	    			}
	    			else {
	    				alert("사용 가능한 닉네임입니다.");
	    				$("#nickNameBtn").attr("disabled",true);
	    				myform.name.focus();
	    			}
	    		},
	    		error:function() {
	    			alert("전송 오류!");
	    		}
	    	});
    	}
    }
    
    // 입력창 누르면 스위치 리셋...?
    window.onload = function(){
    	mid.addEventListener('click',function(){
    		idCheckSw = 0;
    		$("#midBtn").removeAttr("disabled");
    	});
    	nickName.addEventListener('click',function(){
    		nickCheckSw = 0;
    		$("#nickNameBtn").removeAttr("disabled");
    	});
    }
    
    // 선택된 사진 미리보기
    function imgCheck(e) {
    	if(e.files && e.files[0]) {
    		let reader = new FileReader();
    		reader.onload = function(e) {
    			document.getElementById("photoDemo").src = e.target.result;
    		}
    		reader.readAsDataURL(e.files[0]);
    	}
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <form name="myform" method="post" class="was-validated" enctype="multipart/form-data">
    <h2>회 원 가 입</h2>
    <br/>
    <div class="form-group">
      <label for="mid">아이디 : &nbsp; &nbsp;<input type="button" value="아이디 중복체크" id="midBtn" class="btn btn-secondary btn-sm" onclick="idCheck()"/></label>
      <input type="text" class="form-control" name="mid" id="mid" placeholder="아이디를 입력하세요." required autofocus/>
    </div>
    <div class="form-group">
      <label for="pwd">비밀번호 :</label>
      <input type="password" class="form-control" id="pwd" placeholder="비밀번호를 입력하세요." name="pwd" required />
    </div>
    <div class="form-group">
      <label for="nickName">닉네임 : &nbsp; &nbsp;<input type="button" id="nickNameBtn" value="닉네임 중복체크" class="btn btn-secondary btn-sm" onclick="nickCheck()"/></label>
      <input type="text" class="form-control" id="nickName" placeholder="별명을 입력하세요." name="nickName" required />
    </div>
    <div class="form-group">
      <label for="name">성명 :</label>
      <input type="text" class="form-control" id="name" placeholder="성명을 입력하세요." name="name" required />
    </div>
    <div class="form-group">
      <label for="email1">Email address:</label>
        <div class="input-group mb-3">
          <input type="text" class="form-control" placeholder="Email을 입력하세요." id="email1" name="email1" required />
          <div class="input-group-append">
            <select name="email2" class="custom-select">
              <option value="naver.com" selected>naver.com</option>
              <option value="hanmail.net">hanmail.net</option>
              <option value="hotmail.com">hotmail.com</option>
              <option value="gmail.com">gmail.com</option>
              <option value="nate.com">nate.com</option>
              <option value="yahoo.com">yahoo.com</option>  <!-- 기타(입력) -->
            </select>
          </div>
        </div>
    </div>
    <div class="form-group">
      <div class="form-check-inline">
        <span class="input-group-text">성별 :</span> &nbsp; &nbsp;
        <label class="form-check-label">
          <input type="radio" class="form-check-input" name="gender" value="남자" checked>남자
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
          <input type="radio" class="form-check-input" name="gender" value="여자">여자
        </label>
      </div>
    </div>
    <div class="form-group">
      <label for="birthday">생일</label>
      <input type="date" name="birthday" value="<%=java.time.LocalDate.now() %>" class="form-control"/>
    </div>
    <div class="form-group">
      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text">전화번호 :</span> &nbsp;&nbsp;
            <select name="tel1" class="custom-select">
              <option value="010" selected>010</option>
              <option value="02">서울</option>
              <option value="031">경기</option>
              <option value="032">인천</option>
              <option value="041">충남</option>
              <option value="042">대전</option>
              <option value="043">충북</option>
              <option value="051">부산</option>
              <option value="052">울산</option>
              <option value="061">전북</option>
              <option value="062">광주</option>
            </select>-
        </div>
        <input type="text" name="tel2" size=4 maxlength=4 class="form-control"/>-
        <input type="text" name="tel3" size=4 maxlength=4 class="form-control"/>
      </div>
    </div>
    <div class="form-group">
      <label for="address">주소</label>
      <div class="input-group mb-1">
        <input type="text" name="postcode" id="sample6_postcode" placeholder="우편번호" class="form-control">  <!-- postcode : 내가쓰는것 // sample6_postcode : 다음api -->
        <div class="input-group-append">
          <input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기" class="btn btn-secondary">
        </div>
      </div>
      <input type="text" name="roadAddress" id="sample6_address" size="50" placeholder="주소" class="form-control mb-1">
      <div class="input-group mb-1">
        <input type="text" name="detailAddress" id="sample6_detailAddress" placeholder="상세주소" class="form-control"> &nbsp;&nbsp;
        <div class="input-group-append">
          <input type="text" name="extraAddress" id="sample6_extraAddress" placeholder="참고항목" class="form-control">
        </div>
      </div>
    </div>
    <div class="form-group">
      <label for="homepage">Homepage address:</label>
      <input type="text" class="form-control" name="homePage" value="http://" placeholder="홈페이지를 입력하세요." id="homePage"/>
    </div>
    <div class="form-group">
      <label for="name">직업</label>
      <select class="form-control" id="job" name="job">
        <!-- <option value="">직업선택</option> -->
        <option>학생</option>
        <option>회사원</option>
        <option>공무원</option>
        <option>군인</option>
        <option>의사</option>
        <option>법조인</option>
        <option>세무인</option>
        <option>자영업</option>
        <option selected>기타</option>
      </select>
    </div>
    <div class="form-group">
      <div class="form-check-inline">
        <span class="input-group-text">취미</span> &nbsp; &nbsp;
        <label class="form-check-label">
          <input type="checkbox" class="form-check-input" value="등산" name="hobby"/>등산
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
          <input type="checkbox" class="form-check-input" value="낚시" name="hobby"/>낚시
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
          <input type="checkbox" class="form-check-input" value="수영" name="hobby"/>수영
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
          <input type="checkbox" class="form-check-input" value="독서" name="hobby"/>독서
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
          <input type="checkbox" class="form-check-input" value="영화감상" name="hobby"/>영화감상
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
          <input type="checkbox" class="form-check-input" value="바둑" name="hobby"/>바둑
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
          <input type="checkbox" class="form-check-input" value="축구" name="hobby"/>축구
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
          <input type="checkbox" class="form-check-input" value="기타" name="hobby" checked/>기타
        </label>
      </div>
    </div>
    <div class="form-group">
      <label for="content">자기소개</label>
      <textarea rows="5" class="form-control" id="content" name="content" placeholder="자기소개를 입력하세요."></textarea>
    </div>
    <div class="form-group">
      <div class="form-check-inline">
        <span class="input-group-text">정보공개</span>  &nbsp; &nbsp;
        <label class="form-check-label">
          <input type="radio" class="form-check-input" name="userInfor" value="공개" checked/>공개
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
          <input type="radio" class="form-check-input" name="userInfor" value="비공개"/>비공개
        </label>
      </div>
    </div> 
    <div  class="form-group">
      회원 사진(파일용량:2MByte이내) :
      <input type="file" name="fName" id="file" onchange="imgCheck(this)" class="form-control-file border"/>
      <div><img id="photoDemo" width="100px"/></div>
    </div>
    <button type="button" class="btn btn-secondary" onclick="fCheck()">회원가입</button> &nbsp;
    <button type="reset" class="btn btn-secondary">다시작성</button> &nbsp;
    <button type="button" class="btn btn-secondary" onclick="location.href='${ctp}/member/memberLogin';">돌아가기</button>
    
    <input type="hidden" name="email" />
    <input type="hidden" name="tel" />
    <input type="hidden" name="address" />
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>