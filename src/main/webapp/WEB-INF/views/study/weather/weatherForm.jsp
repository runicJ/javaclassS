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
  	
  	$.getJson('https://api.openweathermap.org/data/3.0/weather?q=Seoul,kr&appid=646c00d1f42849562666d94a172ef6e8&units=metric',  //('위치(연결할주소)',콜백함수)// weather함수에서 JSON데이터 형식 사용하기 위해서 // .SeoulNowtemp 서울의 현재기온(id 지정된 것) // jquery 사용중
			function(WeatherResult){
  		  // 기온출력 // 기능
  		  $(".SeoulNowtemp").html(WeatherResult.main.temp);
  	});
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
		<input type="button" value="현재위치날씨" onclick="weatherCheck1()" class="btn btn-success" />
	</div>
	<hr>
	<div id="demo"></div>
</div>
<p><br><p>
  <jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>