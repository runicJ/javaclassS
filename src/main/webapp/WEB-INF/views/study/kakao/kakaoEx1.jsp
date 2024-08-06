<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>kakaoEx1.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
    function addressSvae(latitude, longitude) {
    	let address = document.getElementById("address").value;
    	if(address == "") {
    		alert("선택한 지점의 장소명을 입력하세요");
    		document.getElementById("address").focus();
    		return false;
    	}
    	let query = {
    			address  : address,
    			latitude : latitude,
    			longitude: longitude
    	}
    	
    	$.ajax({
    		url  : "${ctp}/study/kakao/kakaoEx1",
    		type : "post",
    		data : query,
    		success:function(res) {
    			if(res != "0") alert("선택한 지점이 MyDB에 저장되었습니다.");
    			else alert("저장 실패~~(같은 지점명이 있습니다. 이름을 변경해서 다시 등록하세요)");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    // 장소 이미지로 저장하기
    function addressImageSvae(latitude, longitude) {
    	let address = document.getElementById("address").value;
    	if(address == "") {
    		alert("선택한 지점의 장소명을 입력하세요");
    		document.getElementById("address").focus();
    		return false;
    	}
    	
    	// 이미지 지도에 표시할 마커입니다
    	var marker = {
    	    position: new kakao.maps.LatLng(latitude, longitude), 
    	    text: address // text 옵션을 설정하면 마커 위에 텍스트를 함께 표시할 수 있습니다
    	};

    	var staticMapContainer  = document.getElementById('staticMap'), // 이미지 지도를 표시할 div
    	    staticMapOption = { 
    	        center: new kakao.maps.LatLng(latitude, longitude), // 이미지 지도의 중심좌표
    	        level: 3, // 이미지 지도의 확대 레벨
    	        marker: marker // 이미지 지도에 표시할 마커
    	    };

    	// 이미지 지도를 생성합니다
    	var staticMap = new kakao.maps.StaticMap(staticMapContainer, staticMapOption);
    	
	    $("#imageSaveBtn").show();	// 이미지 저장버튼 보이기..
    	
    	let query = {
    			address  : address,
    			latitude : latitude,
    			longitude: longitude
    	}
    	// 지정된 장소를 저장하려면 아래코드를 실행... 여기선 저장처리하지 않았다.
    	$.ajax({
    		url  : "${ctp}/study/kakao/kakaoEx1",
    		type : "post",
    		data : query,
    		success:function(res) {
    			if(res != "0") alert("선택한 지점이 이미지로 저장되었습니다.");
    			//else alert("저장 실패~~(같은 지점명이 있습니다. 이름을 변경해서 다시 등록하세요)");
    			else alert("저장 실패~~");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
  </script>
  
  <!-- 방법1(카카오지도가 하얗게 캡쳐되어 저장된다.) 화면에 표시된 지도를 이미지로 저장하기(html2canvas라이브러리를 CDN으로 지정한다.) -->
  <script src="https://cdn.jsdelivr.net/npm/html2canvas@1.0.0-rc.7/dist/html2canvas.min.js"></script>
  <script>
    function imageSaveCheck0() {
      
      html2canvas(document.getElementById('staticMap')).then(canvas => {
        // 캔버스를 이미지로 변환
        const imgData = canvas.toDataURL('image/png');
        // 이미지 다운로드 링크 생성
        const link = document.createElement('a');
        link.href = imgData;
        link.download = 'staticMap.png';
        // 링크 클릭하여 이미지 다운로드
        link.click();
      });
    }
  </script>
  
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>클릭한 지점에 마커표시/저장</h2>
	<div id="map" style="width:100%;height:500px;"></div>
	
	<form name="myform">
	  <div id="clickLatlng"></div>
	</form>
	
	<!-- 카카오맵 Javascript API -->
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=158c673636c9a17a27b67c95f2c6be5c"></script>
	<script>
	  // 1.지도를 띄워주는 기본 코드(지도 생성)
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new kakao.maps.LatLng(36.63508163115122, 127.45948750459904), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

		var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

		// 2.지도를 클릭한 위치에 표출할 마커입니다
		var marker = new kakao.maps.Marker({ 
		    // 지도 중심좌표에 마커를 생성합니다 
		    position: map.getCenter() 
		}); 
		// 지도에 마커를 표시합니다
		marker.setMap(map);

		// 지도에 클릭 이벤트를 등록합니다
		// 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
		kakao.maps.event.addListener(map, 'click', function(mouseEvent) {        
		    // 클릭한 위도, 경도 정보를 가져옵니다 
		    var latlng = mouseEvent.latLng; 
		    
		    // 마커 위치를 클릭한 위치로 옮깁니다
		    marker.setPosition(latlng);
		    
		    var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
		    message += '경도는 ' + latlng.getLng() + ' 입니다';
		    message += '<p>선택한 지점의 장소명 : <input type="text" name="address" id="address"/> &nbsp;';
		    message += '<input type="button" value="장소DB저장" onclick="addressSvae('+latlng.getLat()+','+latlng.getLng()+')" class="btn btn-success btn-sm mr-2"/>';
		    message += '<input type="button" value="장소이미지만들기" onclick="addressImageSvae('+latlng.getLat()+','+latlng.getLng()+')" class="btn btn-primary btn-sm"/></p>';
		    
		    var resultDiv = document.getElementById('clickLatlng'); 
		    resultDiv.innerHTML = message;
		    
		});
	</script>
	<hr/>
	<jsp:include page="kakaoMenu.jsp" />
	<hr/>
	<!-- 이미지 지도를 표시할 div 입니다 -->
	<div id="staticMap" style="width:800px;height:500px;"></div>
	<div id="imageSaveBtn" style="display:none;">
	  <input type="button" value="표시된지도를 이미지로 저장" onclick="imageSaveCheck()" class="btn btn-info mt-1 mr-2"/>
	  <input type="button" value="지도 다시검색" onclick="location.href='kakaoEx1';" class="btn btn-warning mt-1"/>
	</div>
</div>
  
  <!-- 방법2(???) 화면에 표시된 지도를 이미지로 저장하기(html2canvas라이브러리를 CDN으로 지정한다.) -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
  <script>
    function imageSaveCheck() {
    	var container = document.getElementById('map');
      html2canvas(container).then(function(canvas) {
        var link = document.createElement('a');
        link.href = canvas.toDataURL('image/png');
        link.download = 'kakao-map.png';
        link.click();
      }).catch(function(error) {
        console.error('Error capturing the map:', error);
      });
    }
  </script>

<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>