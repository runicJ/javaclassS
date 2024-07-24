<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>exchangeRate.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
    'use strict';
    // https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=9p1ZRWn6RsyGLKlXhdbYl1ZQtbs7KoMS&searchdate=2024-07-22&data=AP01

    $(function(){
    	//alert('${searchdate}');
	    if('${searchdate}' != '') $("#searchdate").val('${searchdate}');
    });
    // 날짜 새롭게 반영(적용)하기
	  function searchdateCheck() {
		  let searchdate = $("#searchdate").val();
		  location.href = "exchangeRate?searchdate="+searchdate;
	  }
    		
    // 송금액 입력후 엔터키누르면 환율계산으로 바로가기
    $(function(){
	    $('#send_amount').keypress(function (e) {
	      if (e.which === 13) {
	        getSendAmount();
	      }
	    });
    });
    
    // 수취 국가 변경할때마다 새로운 환율 구해오기
    function getExchangeRates(){
    	let searchdate = $("#searchdate").val();
      let receiveCountry = $('#receiveCountry').val();
      let receiveCountryText = $("#receiveCountry option:checked").text();
      if(receiveCountry == ""){
        $('#exchangeRate').text("");
        return;
      }
      $.ajax({
        url : "${ctp}/study/exchangeRate/exchangeRate?receiveCountry=" + receiveCountry + "&searchdate=" + searchdate,
        type : "post",
        success : function(res){
        	//alert(searchdate);
        	console.log(res);
        	let resArr = res.split("@");
        	let jsObj = JSON.parse(resArr[0]);
        	console.log(jsObj);
        	
        	if(res != "") {
	        	let str = '<option value="">선택</option>';
	        	for(let i=0; i<jsObj.length; i++) {
		        	str += '<option value='+jsObj[i].cur_unit+'>'+jsObj[i].cur_nm+'('+jsObj[i].cur_unit+')</option>';
		        }
	        	str += '<option value='+receiveCountry+' selected>'+receiveCountryText+'</option>';
	        	//let temp = receiveCountry.indexOf("(100)") == -1 ? "1" : "100";
	        	$('#receiveCountry').html(str);
	          //$('#exchangeRate').text(resArr[1] + " KRW(원) / " + temp + " " + receiveCountry);
	          $('#exchangeRate').text(resArr[1] + " KRW(원) / " + receiveCountry);
	          $('#searchdate').val(searchdate);
	        }
        },
        error : function(err){
          if(err.status === 400){
              alert("잘못 입력된 값 입니다.")
          }else if(err.status === 500){
              alert("서버에 문제가 발생했습니다.")
          }
        }
      })
    }
    
    // 전체 체크함수(송금액이 0미만이거나 5000000원 초과는 송금할수 없다. 물론 공백 금지이다.)
    function validCheck() {
      let amount = $('#send_amount').val();
      let receivingCountry = $('#receiveCountry').val();
      return (amount.length <= 0 || amount < 0 || amount > 5000000 || receivingCountry == "");
    }

    // 최신 환율에따른 송금액 구하기
    function getSendAmount(){
    	let searchdate = $("#searchdate").val();
      let receivingCountry = $('#receiveCountry').val();
      let sendAmount = $('#send_amount').val();
      if (validCheck()) {
        $('#receiveResult').html("<div class=\"alert alert-danger\"><p>수취국가를 선택하고 송금액을 입력해주세요.<br/>송금액은 0 ~ 5000000 사이의 수를 입력하세요</p></div>");
        return;
      }
      let query = {
    		"searchdate" : searchdate,
        "receiveCountry" : receivingCountry,
        "sendAmount" : sendAmount
      }
      //var jsonData = JSON.stringify(inputData);
      
      $.ajax({
        url : "${ctp}/study/exchangeRate/exchangeRateCompute",
        type : "POST",
        data : query,
        //contentType : "application/json",
        success:function(res) {
        	//alert(res);
        	//let str = '<div class="alert alert-success"><p>수취금액은 < fmt : formatNumber value="' +  res + '" type="percent" pattern="#,00.00" /> ' + receivingCountry + ' 입니다.</p></div>';
        	let temps = res.split(".");
        	let temp = "<b>" + numberWithCommas(temps[0]) + "<font color='blue'>.</font></b>" + temps[1].substring(0,4);
        	let str = '<div class="alert alert-success"><p>수취금액은 <font size="5" color="orange">' + temp + '</font> ' + receivingCountry + ' 입니다.</p></div>';
          $('#receiveResult').html(str);
        },
        error : function(err){
          if(err.status === 400){
            alert("잘못 입력된 값 입니다.")
          }else if(err.status === 500){
            alert("서버에 문제가 발생했습니다.")
          }
        }
      })
    }
    
	  // 천단위마다 쉼표처리
    function numberWithCommas(x) {
      return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
  </script>
  <style>
    th {
      text-align: center;
      background-color: #eee;
    }
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <div class="col-md-6" style="width:100%;margin:0 auto;">
	  <div>
	    <h2 style="border-bottom: 2px solid black">환율 계산</h2>
	    <div><b>0 ~ 5,000,000 원</b> 까지 송금가능합니다.</div>
	    <hr/>
	  </div>
    <table class="table table-bordered">
      <tr>
		    <th>환율적용날자</th>
		    <td>
		      <input type="date" name="searchdate" id="searchdate" class="col-sm-6" value="<%=java.time.LocalDate.now() %>" />
		      <input type="button" value="적용" onclick="searchdateCheck()" />
		    </td>
	    </tr>
	    <tr>
	      <th><label>송금 국가</label></th>
	      <td><span>대한민국(KRW)</span></td>
	    </tr>
	    <tr>
	      <th><label>수취 국가</label></th>
	      <td>
		      <select name="receiveCountry" class="custom-select col-sm-6" id="receiveCountry" onchange = "getExchangeRates()">
		        <option selected value="">국가 선택</option>
		        <!-- 
		        <option value="KRW">한국(KRW)</option>
		        <option value="PHP">필리핀(PHP)</option>
		        <option value="JPY">일본(JPY)</option>
		        -->
		        <c:forEach var="vo" items="${unitVos}" varStatus="st">
		          <option value="${vo.cur_unit}">${vo.cur_nm}(${vo.cur_unit})</option>
		        </c:forEach>
		      </select>
	      </td>
	    </tr>
	    <tr>
	      <th><label>환율</label></th>
	      <td><span id="exchangeRate"></span></td>
	    </tr>
	    <tr>
	      <th><label>송금액</label></th>
	      <td><span><input type="number" id="send_amount" value="100000"></span> KRW(원)</td>
	    </tr>
	    <tr>
	      <td colspan="2" style="text-align:center"><button type="button" class="btn btn-primary btn-sm " onclick="getSendAmount()" >환율 계산</button></td>
	    </tr>
    </table>
	  <table class="table text-center">
	    <tr><td>
			  <div id="receiveResult" class="col"></div>
			  <c:if test="${empty unitVos}">
			    <div id="receiveResult" class="col bg-warning p-2"><font color="red"><b>오늘날짜(지정날짜)의 환율이 반영되지 않았습니다.<br/>잠시후에 다시 조회해 주세요.</b></font></div>
			  </c:if>
	    </td></tr>
	  </table>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>