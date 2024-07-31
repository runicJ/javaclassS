<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <!-- 인증키(서울열린데이터광장 실시간 자전거보관소 신청 인증키) : 6c4a54744d636a7332354d6e6a6164 -->
  <script>
    'use strict';
    
    function bicycleCheck() {
    	let spin = "<div class='text-center'><div class='spinner-border text-muted'></div> 자료 검색중입니다. 잠시만 기다려주세요 <div class='spinner-border text-muted'></div></div>";
      $("#demo").html(spin);
    	let page = $("#page").val();
    	
    	$.ajax({
    		url  : "${ctp}/study/bicycle/bicycle",
    		type : "post",
    		data : {page : page},
    		success:function(vos) {
    			console.log(vos);
    			let str = '';
    			let cnt = 0;
    			for(let i=0; i<vos.length; i++) {
    				str += '자전거대여소명 : ' + vos[i].bcyclLendNm + '<br/>';
    				str += '자전거대여소구분 : ' + vos[i].bcyclLendSe + '<br/>';
    				str += '소재지도로명주소 : ' + vos[i].rdnmadr + '<br/>';
    				str += '소재지지번주소 : ' + vos[i].lnmadr + '<br/>';
    				str += '위도 : ' + vos[i].latitude + '<br/>';
    				str += '경도 : ' + vos[i].longitude + '<br/>';
    				str += '운영시작시각 : ' + vos[i].operOpenHm + '<br/>';
    				str += '운영종료시각 : ' + vos[i].operCloseHm + '<br/>';
    				str += '휴무일 : ' + vos[i].rstde + '<br/>';
    				str += '요금구분 : ' + vos[i].chrgeSe + '<br/>';
    				str += '자전거이용요금 : ' + vos[i].bcyclUseCharge + '<br/>';
    				str += '자전거보유대수 : ' + vos[i].bcyclHoldCharge + '<br/>';
    				str += '거치대수 : ' + vos[i].holderCo + '<br/>';
    				str += '공기주입기비치여부 : ' + vos[i].airInjectorYn + '<br/>';
    				str += '공기주입기유형 : ' + vos[i].airInjectorType + '<br/>';
    				str += '수리대설치여부 : ' + vos[i].repairStandYn + '<br/>';
    				str += '관리기관전화번호 : ' + vos[i].phoneNumber + '<br/>';
    				str += '관리기관명 : ' + vos[i].institutionNm + '<br/>';
    				str += '데이터기준일자 : ' + vos[i].referenceDate + '<br/>';
    				str += '제공기관코드 : ' + vos[i].insttCode + '<br/>';
    				str += '<input type="button" value="지도보기" onclick="mapShow('+vos[i].latitude+','+vos[i].longitude+',\''+vos[i].bcyclLendNm+'\')" class="btn btn-sm btn-outline-secondary form-control"/><hr/>';
    				cnt++;
    			}
    			if(cnt == 0) str += '<hr/>검색된 내역이 없습니다. 다시 검색해 주세요';
    			else str += '<hr/>총 '+cnt+'건이 검색되었습니다.';
    			$("#demo").html(str);
    		},
    		error : function() {
    			alert("출력할 Page가 없습니다. 페이지번호를 다시 선택후 조회하세요");
    			location.reload();
    		}
    	});
    }
    
    function bicycleCheck2() {
    	let spin = "<div class='text-center'><div class='spinner-border text-muted'></div> 자료 검색중입니다. 잠시만 기다려주세요 <div class='spinner-border text-muted'></div></div>";
      $("#demo").html(spin);
    	$.ajax({
    		url  : "${ctp}/study/bicycle/bicycle2",
    		type : "post",
    		contentType : "application/json;charset=UTF-8",
    		success:function(vos) {
    			console.log(vos);
    			let str = '';
    			let cnt = 0;
    			for(let i=0; i<vos.length; i++) {
    				str += (i+1) + "."
    				str += "대여소명 : " + vos[i].stationName + '<br/>';
    				str += "대여소ID : " + vos[i].stationId + '<br/>';
    				str += "거치대수 : " + vos[i].rackTotCnt + '<br/>';
    				str += "거치율 : " + vos[i].shared + '<br/>';
    				str += "자전거 주차 총건수 : " + vos[i].parkingBikeTotCnt + '<br/>';
    				str += "위도/경도 : " + vos[i].stationLatitude + ' / ' + vos[i].stationLongitude + '<br/>';
    				str += '<input type="button" value="지도보기" onclick="mapShow('+vos[i].stationLatitude+','+vos[i].stationLongitude+',\''+vos[i].stationId+'\')" class="btn btn-sm btn-outline-secondary form-control"/><hr/>';
    			}
    			if(cnt == 0) str += '<hr/>검색된 내역이 없습니다. 다시 검색해 주세요';
    			else str += '<hr/>총 '+cnt+'건이 검색되었습니다.';
    			$("#demo").html(str);
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    function bicycleCheck3() {
    	let spin = "<div class='text-center'><div class='spinner-border text-muted'></div> 자료 검색중입니다. 잠시만 기다려주세요 <div class='spinner-border text-muted'></div></div>";
      $("#demo").html(spin);
    	let page = $("#page").val();
    	let region = $("#region").val().trim();
    	if(region == "") {
    		alert("검색할 지역명을 입력하세요");
    		$("#region").focus();
    		return false;
    	}
    	
    	$.ajax({
    		url  : "${ctp}/study/bicycle/bicycle",
    		type : "post",
    		data : {page : page},
    		success:function(vos) {
    			console.log(vos);
    			let str = '';
    			let cnt = 0;
    			for(let i=0; i<vos.length; i++) {
    				if(vos[i].lnmadr.indexOf(region) != -1) {
	    				str += '자전거대여소명 : ' + vos[i].bcyclLendNm + '<br/>';
	    				str += '자전거대여소구분 : ' + vos[i].bcyclLendSe + '<br/>';
	    				str += '소재지도로명주소 : ' + vos[i].rdnmadr + '<br/>';
	    				str += '소재지지번주소 : ' + vos[i].lnmadr + '<br/>';
	    				str += '위도 : ' + vos[i].latitude + '<br/>';
	    				str += '경도 : ' + vos[i].longitude + '<br/>';
	    				str += '운영시작시각 : ' + vos[i].operOpenHm + '<br/>';
	    				str += '운영종료시각 : ' + vos[i].operCloseHm + '<br/>';
	    				str += '휴무일 : ' + vos[i].rstde + '<br/>';
	    				str += '요금구분 : ' + vos[i].chrgeSe + '<br/>';
	    				str += '자전거이용요금 : ' + vos[i].bcyclUseCharge + '<br/>';
	    				str += '자전거보유대수 : ' + vos[i].bcyclHoldCharge + '<br/>';
	    				str += '거치대수 : ' + vos[i].holderCo + '<br/>';
	    				str += '공기주입기비치여부 : ' + vos[i].airInjectorYn + '<br/>';
	    				str += '공기주입기유형 : ' + vos[i].airInjectorType + '<br/>';
	    				str += '수리대설치여부 : ' + vos[i].repairStandYn + '<br/>';
	    				str += '관리기관전화번호 : ' + vos[i].phoneNumber + '<br/>';
	    				str += '관리기관명 : ' + vos[i].institutionNm + '<br/>';
	    				str += '데이터기준일자 : ' + vos[i].referenceDate + '<br/>';
	    				str += '제공기관코드 : ' + vos[i].insttCode + '<br/>';
	    				str += '<input type="button" value="지도보기" onclick="mapShow('+vos[i].latitude+','+vos[i].longitude+',\''+vos[i].bcyclLendNm+'\')" class="btn btn-sm btn-outline-secondary form-control"/><hr/>';
	    				cnt++;
    				}
    			}
    			if(cnt == 0) str += "검색된 지역이 없습니다. 다시 검색해 주세요";
    			else str += '<hr/>총 '+cnt+'건이 검색되었습니다.';
    			$("#demo").html(str);
    		},
    		error : function() {
    			alert("출력할 Page가 없습니다. 페이지번호를 다시 선택후 조회하세요");
    			location.reload();
    		}
    	});
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>전국 자전거 대여소 현황</h2>
  <hr/>
  <div class="row">
    <div class="col-8 text-left">
      <input type="number" name="page" id="page" value="1" min="1" style="width:42px" />Page
	    <input type="button" value="전국자전거대여소출력" onclick="bicycleCheck()" class="btn btn-success"/>
	    <input type="button" value="서울시 공공자전거 실시간 대여정보" onclick="bicycleCheck2()" class="btn btn-primary"/>
    </div>
    <div class="col-4 text-right input-group">
	    <input type="text" name="region" id="region" placeholder="자전거 대여소 검색할 지역명을 입력하세요" class="form-control" style="width:150px"/>
	    <div class="input-group-append"><input type="button" value="검색" onclick="bicycleCheck3()" class="btn btn-secondary"/></div>
    </div>
  </div>
  <hr/>
  <div class="row">
    <div class="col" id="demo" style="width:350px;height:600px; overflow:auto;border:1px solid #ddd;"></div>
    <div class="col" id="sitemap" style="width:500px;height:600px;"></div>
  </div>
</div>
  <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=158c673636c9a17a27b67c95f2c6be5c"></script>
	<script>
	  function mapShow(latitude, longitude, stationId) {
		  // 1.지도를 띄워주는 기본 코드(지도 생성)
			var mapContainer = document.getElementById('sitemap'), // 지도를 표시할 div 
	    mapOption = {
				center: new kakao.maps.LatLng(36.63508163115122, 127.45948750459904), // 지도의 중심좌표
	        level: 3 // 지도의 확대 레벨
	    };  
	
			// 지도를 생성합니다    
			var map = new kakao.maps.Map(mapContainer, mapOption); 
	    var coords = new kakao.maps.LatLng(latitude, longitude);
	    //var coords = new kakao.maps.LatLng(36.63508163115122, 127.45948750459904);
	
	    // 결과값으로 받은 위치를 마커로 표시합니다
	    var marker = new kakao.maps.Marker({
	        map: map,
	        position: coords
	    });
	
	    // 인포윈도우로 장소에 대한 설명을 표시합니다
	    var infowindow = new kakao.maps.InfoWindow({
	        content: '<div style="width:150px;text-align:center;padding:6px 0;">'+stationId+'</div>'
	    });
	    infowindow.open(map, marker);
	
	    // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
	    map.setCenter(coords);
	  }
	</script>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>