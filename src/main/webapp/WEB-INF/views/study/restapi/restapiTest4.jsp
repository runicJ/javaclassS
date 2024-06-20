<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>restapiTest4.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
  	'use strict'
  	
  	const API_KEY = 'otJChM%2F2%2FlhEU46DhF2TXCxo%2FN9BNwpNNkd7XGrlrOdggtMr%2FDciosXbEvJ4D4KWcS5sjYmneyYHiQSWh%2ByUMQ%3D%3D';
  	
  	async function getCrimeDate() {  // async는 비동기로 사용하겠다 fetch 쓸때 사용
    	let year = $("#year").val();
    	let apiYear = '';
  		  		
  		if(year == 2015) apiYear = "/15084592/v1/uddi:fbbfd36d-d528-4c8e-aa9b-d5cdbdeec669";
  		else if(year == 2016) apiYear = "/15084592/v1/uddi:21ec6fa1-a033-413b-b049-8433f5b446ff";
  		else if(year == 2017) apiYear = "/15084592/v1/uddi:67117bd9-5ee1-4e07-ae4a-bfe0861ee116";
  		else if(year == 2018) apiYear = "/15084592/v1/uddi:2d687e27-b5c3-4bdb-9b77-c644dcafcbc7";
  		else if(year == 2019) apiYear = "/15084592/v1/uddi:b6cc7731-181b-48e1-9a6c-ae81388e46b0";
  		else if(year == 2020) apiYear = "/15084592/v1/uddi:fdde1218-987c-49ba-9326-8e3ba276141e";
  		else if(year == 2021) apiYear = "/15084592/v1/uddi:943e757d-462b-4b3a-ab9f-9a8553637ca2";
  		else if(year == 2022) apiYear = "/15084592/v1/uddi:5e08264d-acb3-4842-b494-b08f318aa14c";
  		
    	let url = "https://api.odcloud.kr/api";
    	url += apiYear;
    	url += "?serviceKey=" + API_KEY;
    	url += "&page=1&perPage=200";
  		
  		let response = await fetch(url);  // await 데이터를 읽어오는 동안 기다려
  		//console.log("response : " + response);
  		
  		let res = await response.json();
  		console.log("res : " + res);
  		  		
  		let str = res.data.map((item, i) => [
  			(i+1) + "."
  			+ "발생년도 : " + item.발생년도 + "년"
  			+ ", 경찰서 : " + item.경찰서
  			+ ", 강도 : " + item.강도 + "건"  // item.변수명(바꿔쓰면 안됨)
  			+ ", 절도 : " + item.절도 + "건"
  			+ ", 살인 : " + item.살인 + "건"
  			+ "<br>"
  		]);  // jquery에서 앞이 값 뒤에가 인덱스(반대)
  		
  		$("#demo").html(str);
  	}
  	
  	// 검색한 자료를 DB에 저장하기
  	async function saveCrimeDate() {  // async는 비동기로 사용하겠다 fetch 쓸때 사용
    	let year = $("#year").val();
    	let apiYear = '';
  		  		
  		if(year == 2015) apiYear = "/15084592/v1/uddi:fbbfd36d-d528-4c8e-aa9b-d5cdbdeec669";
  		else if(year == 2016) apiYear = "/15084592/v1/uddi:21ec6fa1-a033-413b-b049-8433f5b446ff";
  		else if(year == 2017) apiYear = "/15084592/v1/uddi:67117bd9-5ee1-4e07-ae4a-bfe0861ee116";
  		else if(year == 2018) apiYear = "/15084592/v1/uddi:2d687e27-b5c3-4bdb-9b77-c644dcafcbc7";
  		else if(year == 2019) apiYear = "/15084592/v1/uddi:b6cc7731-181b-48e1-9a6c-ae81388e46b0";
  		else if(year == 2020) apiYear = "/15084592/v1/uddi:fdde1218-987c-49ba-9326-8e3ba276141e";
  		else if(year == 2021) apiYear = "/15084592/v1/uddi:943e757d-462b-4b3a-ab9f-9a8553637ca2";
  		else if(year == 2022) apiYear = "/15084592/v1/uddi:5e08264d-acb3-4842-b494-b08f318aa14c";
  		
    	let url = "https://api.odcloud.kr/api";
    	url += apiYear;
    	url += "?serviceKey=" + API_KEY;
    	url += "&page=1&perPage=200";
  		
  		let response = await fetch(url);  // await 데이터를 읽어오는 동안 기다려
  		//console.log("response : " + response);
  		
  		let res = await response.json();
  		console.log("res : " + res);
  		  		
  		let str = res.data.map((item, i) => [
  			(i+1) + "."
  			+ "발생년도 : " + item.발생년도 + "년"
  			+ ", 경찰서 : " + item.경찰서
  			+ ", 강도 : " + item.강도 + "건"  // item.변수명(바꿔쓰면 안됨)
  			+ ", 절도 : " + item.절도 + "건"
  			+ ", 살인 : " + item.살인 + "건"
  			+ ", 폭력 : " + item.폭력 + "건"
  			+ "<br>"
  		]);  // jquery에서 앞이 값 뒤에가 인덱스(반대)
  		
  		$("#demo").html(str);
  		
  		// 화면에 출력된 자료들을 모두 DB에 저장시켜준다.
  		let query = "";
  		//alert("경찰서 : " + res.data[0].경찰서);
  		for(let i=0; i<res.data.length; i++) {
  			if(res.data[i].경찰서 != null) {
  				query = {
  						year     : year,
  						police   : res.data[i].경찰서,
  						robbery  : res.data[i].강도,
  						theft    : res.data[i].절도,
  						murder   : res.data[i].살인,
  						violence : res.data[i].폭력
  				}
  			}
  			
  			$.ajax({
  				url  : "${ctp}/study/restapi/saveCrimeData",
  				type : "post",
  				data : query,
  				error:function() {
  					alert('전송오류!');
  				}
  			});
  		}
  		alert(year + "년도 자료가 DB에 저장되었습니다.");
  	}
  	
  	// DB 삭제하기
  	function deleteCrimeData() {
  		let year = $("#year").val();
  		let ans = confirm("선택하신 년도의 자료를 정말 삭제하시겠습니까?");
  		if(!ans) return;

  		$.ajax({
  			usr : "${ctp}/study/restapi/deleteCrimeData",
  			type : "get",
  			data : {year : year},
            success : function(res) {
                if(res != "0") alert(year + "년도의 자료가 DB에서 삭제되었습니다.");
                else {
                	alert(‎"자료 삭제 실패!");
                	return;
                }
            },
            error : function() {
                alert(‎"전송오류!");
            }
        });
    }
  	
  	// DB 출력
  	function listCrimeDate() {
  		let year = $("#year").val();
  		
  		if(year == "") {
  			alert("출력하실 년도를 선택해 주세요.");
  			return false;
  		}
  		
  		$.ajax({
  			usl:"${ctp}/study/restapi/listCrimeDate",
  			type: "post",
  			data : {year : year},
  			success: function(res) {
  				$("#demo").html(res);
  			}
  			error:function() {
  				alert("전송오류!");
  			}
  		});
  	}
  	
  	function policeCheck() {
        let police = $("#police").val();
        
        if(police == ''){
            alert(‎"지역을 선택해주세요");
            return false;
        }
        
        $.ajax({
            url : "${ctp}/study/restapi/policeCheck",
            type : "post",
            data : {police : police},
            success : function(res) {
                $("#demo").html(res);
            },
            error : function() {
                alert(‎"전송오류!");
            }
        });
    }
    
    function yearPoliceCheck() {
        let year = $("#year").val();
        let police = $("#police").val();
        let sort = $('input[name="yearOrder"]:checked').val();
        
        if(year == '') {
            alert(‎"년도를 선택해주세요");
            return false;
        }
        
        if(police == ''){
            alert(‎"지역을 선택해주세요");
            return false;
        }
        
        $.ajax({
            url : "${ctp}/study/restapi/yearPoliceCheck",
            type : "post",
            data : {
            	police : police, 
            	year : year, 
            	sort : sort
            },
            success : function(res) {
                $("#demo").html(res);
            },
            error : function() {
                alert(‎"전송오류!");
            }
        });
    }
  </script>
</head>
<body>
  <jsp:include page="/WEB-INF/views/include/nav.jsp" />
  <jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br><p>
<div class="container">
	<h2>경찰청 강력범죄 발생 현황 자료 리스트</h2>
	<hr>
	<form name="myform" method="post">
    <div>
      <select name="year" id="year">
        <c:forEach var="i" begin="2015" end="2022">
        	<option value="${i}" ${year == i ? 'selected' : ''}>${i}년도</option>
        </c:forEach>
      </select>
      <input type="button" value="강력범죄제공현황조회" onclick="getCrimeDate()" class="btn btn-success mb-3"/>
      <input type="button" value="강력범죄DB저장" onclick="saveCrimeDate()" class="btn btn-dark mb-3"/>
      <input type="button" value="강력범죄DB삭제" onclick="deleteCrimeDate()" class="btn btn-danger mb-3"/>
      <input type="button" value="강력범죄DB출력" onclick="listCrimeDate()" class="btn btn-info mb-3"/>
    </div>
    <div>
    	경찰서 지역명 : 
    	<select name="police" onchange="policeCheck()">
    		<option>서울</option>
    		<option>부산</option>
    		<option>대구</option>
    		<option>인천</option>
    		<option>광주</option>
    		<option>대전</option>
    		<option>울산</option>
    		<option>세종</option>
    		<option>경기</option>
    		<option>강원</option>
    		<option>충북</option>
    		<option>충남</option>
    		<option>경북</option>
    		<option>경남</option>
    		<option>전북</option>
    		<option>전남</option>
    	</select> &nbsp;&nbsp;
    	: 정렬순서 :
    	<input type="radio" name="yearOrder" value="a" />오름차순
    	<input type="radio" name="yearOrder" value="d" />내림차순 :
    	<input type="button" value="년도/경찰서출력" onclick="yearPoliceCheck()" class="btn btn-primary" />
    </div>
  </form>
  <hr/>
  <div id="demo"></div>
  <hr>
  <h3>범죄 분석 통계</h3>
  <!-- 1. 년도/강도/살인/절도/폭력 -->
  <!-- 2. 경찰서별 통계 : 년도/강도/살인/절도/폭력 -->
  <!-- 3. 범죄발생건수가 가장 작은 지역? -->
  <table>
  </table>
</div>
<p><br><p>
  <jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>