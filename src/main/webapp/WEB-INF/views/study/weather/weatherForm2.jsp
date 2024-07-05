<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>weatherForm.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
  	function weatherCheck1() {
			// 현재 위치 정보 가져오는 명령어(브라우저에서 자동으로 지원)
			navigator.geolocation.getCurrentPosition(accessToGeo);
  	}
  	
  	// 나의 위치를 '위도/경도'로 찾아서 처리..
  	function accessToGeo(position) {
	    const positionObj = {
	  	      latitude: position.coords.latitude,
	  	      longitude: position.coords.longitude
	  	}
	    
	    let loc = "(현재위치) 위도 : " + positionObj.latitude + " , 경도 : " + positionObj.longitude;
	    $("#demo").text(loc);
  	}
  	
    //('위치(연결할주소)',콜백함수)// weather함수에서 JSON데이터 형식 사용하기 위해서 // .SeoulNowtemp 서울의 현재기온(id 지정된 것) // jquery 사용중
  	$.getJSON('https://api.openweathermap.org/data/2.5/weather?q=Seoul,kr&appid=646c00d1f42849562666d94a172ef6e8&units=metric',
			function(WeatherResult){
  		  // 기온출력 // 기능
  		  $(".SeoulNowtemp").html(WeatherResult.main.temp);
  		  $(".SeoulLowtemp").html(WeatherResult.main.temp_min);
  		  $(".SeoulHightemp").html(WeatherResult.main.temp_max);
  		  
  		  // 날씨아이콘출력(WeatherResult.weather[0].icon)
  		  var weathericonUrl = 
  			  '<img src="http://openweathermap.org/img/wn/'
  			  +WeatherResult.weather[0].icon+ '.png" alt="'+WeatherResult.weather[0].description+'"/>';  // 헷갈려ㅠㅠ
  			  $('#demo').html(weathericonUrl);
  	});
    
  	function weatherCheck2() {
			navigator.geolocation.getCurrentPosition(accessToGeo);
  	}
  	
  	function accessToGeo2(position) {
	    const positionObj = {
	  	      latitude: position.coords.latitude,
	  	      longitude: position.coords.longitude
	  	}
	    
	    let loc = "(현재위치) 위도 : " + positionObj.latitude + " , 경도 : " + positionObj.longitude;
	    $("#demo").text(loc);
	    
	    //('위치(연결할주소)',콜백함수)// weather함수에서 JSON데이터 형식 사용하기 위해서 // .SeoulNowtemp 서울의 현재기온(id 지정된 것) // jquery 사용중
	  	$.getJSON('https://api.openweathermap.org/data/2.5/weather?q=Seoul,kr&appid=646c00d1f42849562666d94a172ef6e8&units=metric',
				function(WeatherResult){
	  		  // 기온출력 // 기능
/* 	  		  $(".SeoulNowtemp").html(WeatherResult.main.temp);
	  		  $(".SeoulLowtemp").html(WeatherResult.main.temp_min);
	  		  $(".SeoulHightemp").html(WeatherResult.main.temp_max); */
	  		  $(".nowtemp").html(WeatherResult.main.temp + " ℃");
	  		  $(".lowtemp").html(WeatherResult.main.temp_min + " ℃");
	  		  $(".hightemp").html(WeatherResult.main.temp_max + " ℃");
	  		  
	  		  // 날씨아이콘출력(WeatherResult.weather[0].icon)
	  		  var weathericonUrl = 
	  			  '<img src="http://openweathermap.org/img/wn/'
	  			  +WeatherResult.weather[0].icon+ '.png" alt="'+WeatherResult.weather[0].description+'"/>';  // 헷갈려ㅠㅠ
	  			  $('.icon2').html(weathericonUrl);
	  	});
  	}
  </script>
</head>
<body>
  <jsp:include page="/WEB-INF/views/include/nav.jsp" />
  <jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br><p>
<div class="container">
	<h2>날씨 정보</h2>
	<hr>
	<div>
		<input type="button" value="현재위치" onclick="weatherCheck1()" class="btn btn-success mr-2" />
		<input type="button" value="현재위치날씨" onclick="weatherCheck2()" class="btn btn-primary" />
	</div>
	<hr>
	<div id="demo"></div>
	<hr>
	<div class="icon1"></div>
	<div class="card-body">
	    <h4 class="card-title">서울</h4>
	    <p class="card-text SeoulNowtemp">현재기온:</p>
	    <p class="card-text SeoulLowtemp">최저기온:</p>
	    <p class="card-text SeoulHightemp">최저기온:</p>
	</div>
	<hr>
	<div class="icon2"></div>
	<div class="card-body">
	    <h4 class="card-title">청주</h4>
	    <p class="card-text nowtemp">현재기온:</p>
	    <p class="card-text lowtemp">최저기온:</p>
	    <p class="card-text hightemp">최저기온:</p>
	</div>
</div>
<p><br><p>
  <jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>