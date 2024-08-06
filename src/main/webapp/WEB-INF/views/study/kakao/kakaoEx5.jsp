<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>kakaoEx5.jsp(마커 클러스터러 사용하기)</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
	<script>
	  
	</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>등록된 지명정보를 클러스터러 활용해서 상세보기</h2>
	<hr/>
	<!-- <div id="map" style="width:100%;height:350px;"></div> -->
	<div id="map" style="width:100%;height:500px;position:relative;overflow:hidden;"></div>
	
	<!-- <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=158c673636c9a17a27b67c95f2c6be5c&libraries=services"></script> -->
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=158c673636c9a17a27b67c95f2c6be5c&libraries=clusterer"></script>
	<script>
	    var map = new kakao.maps.Map(document.getElementById('map'), { // 지도를 표시할 div
	        center : new kakao.maps.LatLng(36.2683, 127.6358), // 지도의 중심좌표 
	        level : 12 // 지도의 확대 레벨 
	    });
	    
	    // 마커 클러스터러를 생성합니다 
	    var clusterer = new kakao.maps.MarkerClusterer({
	        map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체 
	        averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정 
	        minLevel: 10 // 클러스터 할 최소 지도 레벨 
	    });
	 
	    // 데이터를 가져오기 위해 jQuery를 사용합니다
	    // 데이터를 가져와 마커를 생성하고 클러스터러 객체에 넘겨줍니다
	    //$.get("/download/web/data/chicken.json", function(data) {
	        // 데이터에서 좌표 값을 가지고 마커를 표시합니다
	        // 마커 클러스터러로 관리할 마커 객체는 생성할 때 지도 객체를 설정하지 않습니다
	        //alert(${jsonVos});
	        var vos = ${jsonVos};
	        var markers = $(vos).map(function(i, position) {
	            return new kakao.maps.Marker({
	                position : new kakao.maps.LatLng(position.latitude, position.longitude),
	                title : position.address
	            });
	        });
	
	        // 클러스터러에 마커들을 추가합니다
	        clusterer.addMarkers(markers);
	    //});
	</script>
	<hr/>
	<jsp:include page="kakaoMenu.jsp" /></div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>