<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>tagoExpress.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
    'use strict';
    
    function expressCheck() {
    	let spin = "<div class='text-center'><div class='spinner-border text-muted'></div> 자료 검색중입니다. 잠시만 기다려주세요 <div class='spinner-border text-muted'></div></div>";
      $("#demo").html(spin);
    	let page = $("#page").val();
    	
    	$.ajax({
    		url  : "${ctp}/study/tagoExpress/tagoExpress",
    		type : "post",
    		data : {page : page},
    		success:function(vos) {
    			console.log(vos);
    			let str = '';
    			let cnt = 0;
    			for(let i=0; i<vos.length; i++) {
    				str += '노선ID : ' + vos[i].routeId + '<br/>';
    				str += '버스등급 : ' + vos[i].gradeNm + '<br/>';
    				str += '출발시간 : ' + vos[i].depPlandTime + '<br/>';
    				str += '도착시간 : ' + vos[i].arrPlandTime + '<br/>';
    				str += '출발지 : ' + vos[i].depPlaceNm + '<br/>';
    				str += '도착지 : ' + vos[i].arrPlaceNm + '<br/>';
    				str += '운임(단위 : 원) : ' + vos[i].charge + '<hr/>';
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
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>국토교통부_(TAGO)_고속버스정보</h2>
  <hr/>
	<div class="row">
    <div class="col-8 text-left">
      <input type="number" name="page" id="page" value="1" min="1" style="width:42px" />Page
	    <input type="button" value="전국고속버스정보출력" onclick="expressCheck()" class="btn btn-success"/>
    </div>
    <div class="col-4 text-right input-group">
	    <input type="text" name="region" id="region" placeholder="검색할 지역명을 입력하세요" class="form-control" style="width:150px"/>
	    <div class="input-group-append"><input type="button" value="검색" onclick="expressCheck2()" class="btn btn-secondary"/></div>
    </div>
  </div>
  <hr/>
  <div class="row">
    <div class="col" id="demo" style="width:350px;height:600px; overflow:auto;border:1px solid #ddd;"></div>
    <div class="col" id="sitemap" style="width:500px;height:600px;"></div>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>