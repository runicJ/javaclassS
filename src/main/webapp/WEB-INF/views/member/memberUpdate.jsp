<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%-- https://tadaktadak-it.tistory.com/96 // https://haaland09009.tistory.com/226 // https://velog.io/@ansalstmd/JSP8.-%EC%9C%A0%ED%9A%A8%EC%84%B1-%EA%B2%80%EC%82%AC --%>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>  <!-- certification 누구나 입장가능하도록 지워놓음 -->
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>memberUpdate.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>  <!-- CDN 쓰는 이유는 어차피 웹은 인터넷이 없으면 무의미 -->
  <script src="${ctp}/js/woo.js"></script>  <!-- 다음api js파일로 만듦 -->
  <script>
    'use strict';
    
    let idCheckSw = 0;  // 버튼 활성화 => 1로 바꾸고 아이디 고쳐도 0으로
    let nickCheckSw = 0;  // 둘다 1이 되어 있어야 submit 되도록
    
    
    function fCheck() {
    	// 유효성 검사.....(정규식-버튼체크-묶어서-담아서 submit)
    	// 아이디,닉네임,성명,이메일,홈페이지,전화번호,비밀번호 등등....
    	
    	// 정규식을 이용한 유효성검사처리.....
      let regNickName = /^[가-힣]+$/;					// 닉네임은 한글만 가능
      let regName = /^[가-힣a-zA-Z]+$/;				// 이름은 한글/영문 가능
    	
    	// 검사를 끝내고 필요한 내역들을 변수에 담아 회원가입 처리한다.
    	// 변수에 저장(위에서 해도됨)
    	let nickName = myform.nickName.value;
    	let name = myform.name.value;
    	let photo = myform.photo.value;
    	
    	let email1 = myform.email1.value.trim();
    	let email2 = myform.email2.value;  // 콤보상자 선택 trim()안해도 됨
    	let email = email1 + "@" + email2;  // 결합 , 구분자 넣기

    	let tel1 = myform.tel1.value;
    	let tel2 = myform.tel1.value.trim();
    	let tel3 = myform.tel1.value.trim();
    	let tel = tel1 + "-" + tel2 + "-" + tel3;
    	
    	let postcode = myform.postcode.value + " ";  // 공백 한칸으로 내용이 있는 것 처럼  // / 넣을 것 // db에 null 허용 설정 값이 없으면 ////로 들어가도록
    	let roadAddress = myform.roadAddress.value + " ";
    	let detailAddress = myform.detailAddress.value + " ";
    	let extraAddress = myform.extraAddress.value + " ";
    	let address = postcode + "/" + roadAddress + "/" + detailAddress + "/" + extraAddress + "/";  // 위에서 공백을 주지 않으면 값이 없을 경우 나중에 /기준으로 split 했을때(////) 안 나눠짐 하나로 봄(front에서 해주기)
    	
      if(!regNickName.test(nickName)) {
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
    	
    	if(nickCheckSw == 0) {
    		alert("닉네임 중복체크 버튼을 눌러주세요");
    		document.getElementById("nickNameBtn").focus();
    	}
    	else {
    		myform.email.value = email;  // hidden에 묶어서 보내는거 담기
    		myform.tel.value = tel;
    		myform.address.value = address;
    		
    		myform.submit();
    	}
    }
    
    // 닉네임 중복체크
    function nickCheck() {
    	let nickName = myform.nickName.value;
    	
    	if(nickName.trim() == "") {
    		alert("닉네임을 입력하세요!");
    		myform.nickName.focus();
    	}
    	else if(nickName.trim() == '${sNickName}') {  // 자바스크립트임(nickName.equals('${sNickName}')) X
    		nickCheckSw = 1;
    		return false;  // 닉네임이 같으면 굳이 중복체크하러 보낼 필요가 없음
    	}
    	else {
    		nickCheckSw = 1;
    		
	    	$.ajax({
	    		url : "${ctp}/MemberNickCheck.mem",
	    		type : "get",
	    		data : {nickName : nickName},
	    		success:function(res) {
                    if(res != '0') {
                        if('${sNickName}'==nickName) {
                            alert("이전 닉네임(${sNickName}과 똑같은 닉네임입니다.)")
                        } else {
                            alert("이미 사용중인 닉네임 입니다. 다시 입력하세요.");
                        }
                        myform.nickName.focus();
                    }
                    else alert("사용 가능한 닉네임 입니다.");
	    		},
	    		error:function() {
	    			alert("전송 오류!");
	    		}
	    	});
    	}
    }
    
    $(function(){	
    	$("#nickName").on("blur", () => {
    		nickCheckSw = 0;
    	});
    });
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />s
<p><br/></p>
<div class="container">
  <form name="myform" method="post" class="was-validated">
    <h2>회 원 정 보 수 정</h2>
    <br/>
    <div class="form-group">
      아이디 :
      <input type="text" class="form-control" id="mid" name="mid" value="${vo.mid}" readonly />
    </div>
    <div class="form-group">
      <label for="nickName">닉네임 : &nbsp; &nbsp;<input type="button" id="nickNameBtn" value="닉네임 중복체크" class="btn btn-secondary btn-sm" onclick="nickCheck()"/></label>
      <input type="text" class="form-control" id="nickName" value="${vo.nickName}" name="nickName" required />
    </div>
    <div class="form-group">
      <label for="name">성명 :</label>
      <input type="text" class="form-control" id="name" value="${vo.name}" name="name" required />
    </div>
    <div class="form-group">
      <label for="email1">Email address:</label>  <!-- jsp에서 분리하려면 jstl 사용 // html 태그이기 때문에 지저분해지기 때문에 서블릿에서 자르는 것이 좋음 간단한 것은 ㄱㅊ -->
        <div class="input-group mb-3">
        <c:set var="email" value="${fn:split(vo.email,'@')}" />  <!-- jstl 변수에 담은 것 -->
          <input type="text" class="form-control" id="email1" name="email1" value="${email[0]}" required />  <!-- email 변수가 @로 분리해서 배열변수임 -->
          <div class="input-group-append">
            <select name="email2" class="custom-select">
              <option value="naver.com" ${email[1] == 'naver.com' ? 'selected' : ''}>naver.com</option>  <!-- c:if로 해도되고 삼항 연산자로 해도됨 -->  <!-- 자바스크립트는 '문자기호' 넣어야 먹음 -->
              <option value="hanmail.net" ${email[1] == 'hanmail.net' ? 'selected' : ''}>hanmail.net</option>
              <option value="hotmail.com" ${email[1] == 'hotmail.com' ? 'selected' : ''}>hotmail.com</option>
              <option value="gmail.com" ${email[1] == 'gmail.com' ? 'selected' : ''}>gmail.com</option>
              <option value="nate.com" ${email[1] == 'nate.com' ? 'selected' : ''}>nate.com</option>
              <option value="yahoo.com" ${email[1] == 'yahoo.com' ? 'selected' : ''}>yahoo.com</option>
            </select>
          </div>
        </div>
    </div>
    <div class="form-group">
      <div class="form-check-inline">
        <span class="input-group-text">성별 :</span> &nbsp; &nbsp;
        <label class="form-check-label">
          <input type="radio" class="form-check-input" name="gender" value="남자" ${vo.gender == '남자' ? 'checked' : ''}>남자
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
         	<input type="radio" class="form-check-input" name="gender" value="여자" ${vo.gender == '여자' ? 'checked' : ''}>여자
        </label>
      </div>
    </div>
    <div class="form-group">
      <label for="birthday">생일</label>
      <input type="date" name="birthday" value="${fn:substring(vo.birthday,0,10)}" class="form-control"/>
    </div>
    <div class="form-group">
      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text">전화번호 :</span> &nbsp;&nbsp;  <!-- 서버단에서 나눌 것 -->
            <select name="tel1" class="custom-select">
              <option value="010" ${tel1 == '010' ? 'selected' : ''}>010</option>
              <option value="02" ${tel1 == '02' ? 'selected' : ''}>서울</option>
              <option value="031" ${tel1 == '031' ? 'selected' : ''}>경기</option>
              <option value="032" ${tel1 == '032' ? 'selected' : ''}>인천</option>
              <option value="041" ${tel1 == '041' ? 'selected' : ''}>충남</option>
              <option value="042" ${tel1 == '042' ? 'selected' : ''}>대전</option>
              <option value="043" ${tel1 == '043' ? 'selected' : ''}>충북</option>
              <option value="051" ${tel1 == '051' ? 'selected' : ''}>부산</option>
              <option value="052" ${tel1 == '052' ? 'selected' : ''}>울산</option>
              <option value="061" ${tel1 == '061' ? 'selected' : ''}>전북</option>
              <option value="062" ${tel1 == '062' ? 'selected' : ''}>광주</option>
            </select> - 
        </div>
        <input type="text" name="tel2" value="${tel2}" size=4 maxlength=4 class="form-control"/> - 
        <input type="text" name="tel3" value="${tel3}" size=4 maxlength=4 class="form-control"/>
      </div>
    </div>
    <div class="form-group">
      <label for="address">주소</label>
      <div class="input-group mb-1">
        <input type="text" name="postcode" id="sample6_postcode" value="${postcode}" class="form-control">  <!-- postcode : 내가쓰는것 // sample6_postcode : 다음api -->
        <div class="input-group-append">
          <input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기" class="btn btn-secondary">  <!-- 다음api에서 찾는 함수 -->
        </div>
      </div>
      <input type="text" name="roadAddress" id="sample6_address" size="50" value="${roadAddress}" class="form-control mb-1">
      <div class="input-group mb-1">
        <input type="text" name="detailAddress" id="sample6_detailAddress" value="${detailAddress}" class="form-control"> &nbsp;&nbsp;
        <div class="input-group-append">
          <input type="text" name="extraAddress" id="sample6_extraAddress" value="${extraAddress}" class="form-control">
        </div>
      </div>
    </div>
    <div class="form-group">
      <label for="homepage">Homepage address:</label>
      <input type="text" class="form-control" name="homePage" value="${vo.homePage}" id="homePage"/>
    </div>
    <div class="form-group">
      <label for="name">직업</label>
      <select class="form-control" id="job" name="job">  <!-- 콤보상자는 값이 하나만 들어감 -->
        <!-- <option value="">직업선택</option> -->
        <option ${vo.job == '학생' ? 'selected' : ''}>학생</option>
        <option ${vo.job == '회사원' ? 'selected' : ''}>회사원</option>
        <option ${vo.job == '공무원' ? 'selected' : ''}>공무원</option>
        <option ${vo.job == '군인' ? 'selected' : ''}>군인</option>
        <option ${vo.job == '의사' ? 'selected' : ''}>의사</option>
        <option ${vo.job == '법조인' ? 'selected' : ''}>법조인</option>
        <option ${vo.job == '세무인' ? 'selected' : ''}>세무인</option>
        <option ${vo.job == '자영업' ? 'selected' : ''}>자영업</option>
        <option ${vo.job == '기타' ? 'selected' : ''}>기타</option>
      </select>
    </div>
    <div class="form-group">
			취미 : 
			<c:set var="varHobbys" value="${fn:split('등산,낚시,수영,독서,영화감상,바둑,축구,기타',',')}" />  <!-- 자르는 기준은 관계없음 /해도됨 -->
			<c:forEach var="tempHobby" items="${varHobbys}" varStatus="st">
				<input type="checkbox" name="hobby" value="${tempHobby}" <c:if test="${fn:contains(vo.hobby,tempHobby)}">checked</c:if> />${tempHobby} &nbsp;&nbsp;
			</c:forEach>        
    </div>
    <div class="form-group">
      <label for="content">자기소개</label>
      <textarea rows="5" class="form-control" id="content" name="content">${vo.content}</textarea>
    </div>
    <div class="form-group">
      <div class="form-check-inline">
        <span class="input-group-text">정보공개</span>  &nbsp; &nbsp;
        <label class="form-check-label">
          <input type="radio" class="form-check-input" name="userInfor" ${vo.userInfor == '공개' ? 'checked' : ''} />공개
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
          <input type="radio" class="form-check-input" name="userInfor" ${vo.userInfor == '비공개' ? 'checked' : ''} />비공개
        </label>
      </div>
    </div>
    <div  class="form-group">
      회원 사진(파일용량:2MByte이내) : <img src="${ctp}/images/member/${vo.photo}" width="100px"/>
      <input type="file" name="fName" id="file" class="form-control-file border"/>
    </div>
    <button type="button" class="btn btn-success" onclick="fCheck()">회원정보수정</button> &nbsp;
    <button type="reset" class="btn btn-warning">다시작성</button> &nbsp;
    <button type="button" class="btn btn-danger" onclick="location.href='${ctp}/MemberMain.mem';">돌아가기</button>
    
    <input type="hidden" name="email" />
    <input type="hidden" name="tel" />
    <input type="hidden" name="address" />
    <input type="hidden" name="photo" value="${vo.photo}" />
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>