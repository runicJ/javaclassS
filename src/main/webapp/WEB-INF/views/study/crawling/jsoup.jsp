<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>jsoup.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
  	'use strict';
  	
  	$(function(){
  		$("#url").change(function(){
  			let url = document.querySelector("select[name=url]");  // 태그에 있는 속성 이름 []
  			let urlNo = url.options[url.selectedIndex].text.substring(0,url.options[url.selectedIndex].text.indexOf("."));  // 숫자 1이 값으로 지정 // value값을 따로 지정하지 않음
  			
  			let temp = "<option>";
  			let selector = document.myform.selector;
  			for(let i=0; i<selector.length; i++) {
  				if(urlNo == selector[i].value.substring(0,selector[i].value.indexOf("."))) {  // js에서 같은 이름일때 배열로 봄
  					temp += selector[i].value;
  					break;
  				}
  			}
  			temp += "</option>";
  			$("#selector").html(temp);  // html 형식으로 위에서 편집한 temp를 뿌림
  		});
  	});
  	
    function crawling1() {
    	let url = document.getElementById("url").value;
    	let selector = document.getElementById("selector").value;
    	
    	if(url.trim() == "") {
    		alert("웹크로링할 주소를 입력하세요");
    		return false;
    	}
    	
    	$.ajax({
    		url  : "${ctp}/study/crawling/jsoup",
    		type : "post",
    		data : {
    			url : url.substring(url.indexOf("-")+1),
    			selector : selector.substring(selector.indexOf(".")+1)
    		},
    		success:function(vos) {
    			if(vos != "") {
    				let str = '';
    				for(let i=0; i<vos.length; i++) {
    					str += vos[i] + "<br/>";
    				}
	    			$("#demo").html(str);
    			}
    			else $("#demo").html("검색된 자료가 없습니다.");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    function crawling2() {
    	$.ajax({
    		url  : "${ctp}/study/crawling/jsoup2",
    		type : "post",
    		success:function(vos) {
    			if(vos != "") {
    				let str = '<table class="table table-bordered text-center">';
    				str += '<tr class="table-secondary"><th>번호</th><th>제목</th><th>사진</th><th>언론사</th></tr>';
    				for(let i=0; i<vos.length; i++) {
	    				str += '<tr>';
    					str += '<td>'+(i+1)+'</td>';
    					str += '<td>'+vos[i].item1+'</td>';
    					str += '<td>'+vos[i].item2+'</td>';
    					str += '<td>'+vos[i].item3+'</td>';
	    				str += '</tr>';
    				}
    				str += '<tr><td colspan="4" class="p-0 m-0"></td></tr>';
    				str += '</table>';
	    			$("#demo").html(str);
    			}
    			else $("#demo").html("검색된 자료가 없습니다.");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    // 다음 연예계 소식
    function crawling3() {
    	$.ajax({
    		url  : "${ctp}/study/crawling/jsoup3",
    		type : "post",
    		success:function(vos) {
    			if(vos != "") {
    				let str = '<table class="table table-bordered text-center">';
    				str += '<tr class="table-secondary"><th>번호</th><th>제목</th><th>사진</th><th>언론사</th></tr>';
    				for(let i=0; i<vos.length; i++) {
	    				str += '<tr>';
    					str += '<td>'+(i+1)+'</td>';
    					str += '<td>'+vos[i].item1+'</td>';
    					str += '<td>'+vos[i].item2+'</td>';
    					str += '<td>'+vos[i].item3+'</td>';
	    				str += '</tr>';
    				}
    				str += '<tr><td colspan="4" class="p-0 m-0"></td></tr>';
    				str += '</table>';
	    			$("#demo").html(str);
    			}
    			else $("#demo").html("검색된 자료가 없습니다.");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    // 네이버 검색어로 검색결과 가져오기
    function crawling4() {
    	let searchString = document.getElementById("searchString").value;
    	let page = document.getElementById("page").value;
    	
    	if(searchString.trim() == "") {
    		alert("검색어를 입력하세요!");
    		document.getElementById("searchString").focus();
    		return false;
    	}
    	if(page.trim() == "") page = 1;
    	
    	let search = "https://search.naver.com/search.naver?nso=&page="+page+"&query="+searchString+"&sm=tab_pge&start="+(page*15+1)+"&where=web";
    	let searchSelector = "div.total_dsc_wrap";
    	
    	$.ajax({
    		url  : "${ctp}/study/crawling/jsoup4",
    		type : "post",
    		data : {
    			search : search,
    			searchSelector : searchSelector
    		},
    		success:function(vos) {
    			if(vos != "") {
    				let str = '';
    				for(let i=0; i<vos.length; i++) {
    					str += vos[i] + "<br/>";
    				}
	    			$("#demo").html(str);
    			}
    			else $("#demo").html("검색된 자료가 없습니다.");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    // '네어버' 검색어로 그림 검색결과 가져오기
    function crawling5() {
    	let searchString = document.getElementById("searchString").value;
    	let page = document.getElementById("page").value;
    	if(searchString.trim() == "") {
    		alert("검색어를 입력하세요");
    		document.getElementById("searchString").focus();
    		return false;
    	}
    	if(page.trim() == "") page = 1;
    	
    	let search = "https://search.naver.com/search.naver?nso=&page="+page+"&query="+searchString+"&sm=tab_pge&start="+(page*15+1)+"&where=web";
    	//let searchSelector = "div.thumb_single._parent";
    	let searchSelector = "a.thumb_link";
    	
    	$.ajax({
    		url  : "${ctp}/study/crawling/jsoup4",
    		type : "post",
    		data : {
    			search : search,
    			searchSelector : searchSelector
    		},
    		success:function(vos) {
    			if(vos != "") {
    				let str = '';
    				for(let i=0; i<vos.length; i++) {
    					str += vos[i] + "<br/>";
    				}
	    			$("#demo").html(str);
    			}
    			else $("#demo").html("검색된 자료가 없습니다.");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }

    // '멜론' 차트 검색하기
    function crawlingCheck() {
      $("#spinnerIcon").show();

      $.ajax({
        type  : "post",
        url   : "${ctp}/study/crawling/melonCrawling",
        success:function(vos) {
        	console.log("vos",vos);
          $("#demo").show();
          if(vos != "") {
        	  let str = '';
        	  str += '<table class="table table-bordered text-center"><tr class="table-dark text-dark"><th>순위</th><th>앨범사진</th><th>타이틀곡</th><th>가수</th></tr>';
						for(let i=0; i<vos.length; i++) {
							str += '<tr>';
							str += '<td>'+vos[i].item1+'위</td>';
							str += '<td>'+vos[i].item2+'</td>'
							str += '<td>'+vos[i].item3+'</td>';
							str += '<td>'+vos[i].item4+'</td>';
							str += '</tr>';
						}
						$("#demo").html(str);
            $("#spinnerIcon").hide();
          }
          else $("#demo").html("검색자료가 없습니다.");

        },
        error : function() {
          alert("전송오류!");
        }
      });
    }
    
    
    // 화살표클릭시 화면 상단으로 부드럽게 이동하기
    $(window).scroll(function(){
    	if($(this).scrollTop() > 100) {
    		$("#topBtn").addClass("on");
    	}
    	else {
    		$("#topBtn").removeClass("on");
    	}
    	
    	$("#topBtn").click(function(){
    		window.scrollTo({top:0, behavior: "smooth"});
    	});
    });
  </script>
  <style>
		h6 {
		  position: fixed;
		  right: 1rem;
		  bottom: -50px;
		  transition: 0.7s ease;
		}
   	.on {
		  opacity: 0.8;
		  cursor: pointer;
		  bottom: 0;
		}
  </style>
</head>
<body>
  <jsp:include page="/WEB-INF/views/include/nav.jsp" />
  <jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br><p>
<div class="container">
	<h2>JSOUP을 이용한 웹 크롤링</h2>
	<hr>
	<div><a href="javascript:location.reload()" class="btn btn-danger form-control">다시검색</a></div>
	<hr>
	<form name="myform">
		<div class="input-group mb-3">
			<div class="input-group-text">크롤링할 웹 주소</div>
			<select name="url" id="url" class="form-control">
				<option value="">URL을 선택하세요</option>
				<option>1.네이버뉴스-https://news.naver.com/</option>
				<option>2.네이버뉴스(이미지)-https://news.naver.com/</option>
				<option>3.네이버뉴스()-https://news.naver.com/</option>
			</select>
		</div>
		<div class="input-group mb-3">
			<div class="input-group-text">크롤링할 셀렉터</div>
			<select name="selector" id="selector" class="form-control">
				<option value="">셀렉터를 선택하세요</option>
				<option>1.div.cjs_t</option>
	      <option>2.div.cjs_news_mw</option>
	      <option>3.h4.channel</option>
			</select>
			<div class="input-group-append"><input type="button" value="크롤링1" onclick="crawling1()" class="btn btn-primary"></div>
		</div>
		<div><input type="button" value="크롤링2(네이버 헤드라인뉴스)" onclick="crawling2()" class="btn btn-success mb-3"></div>
		<div><input type="button" value="크롤링3(다음 연예뉴스)" onclick="crawling3()" class="btn btn-info"></div>
    <hr/>
    <div class="input-group mb-3">
	    <div class="input-group-text">네이버 검색어로 검색(검색어)</div>
	    <input type="text" name="searchString" id="searchString" value="인사이드아웃2" class="form-control"/>
    </div>
    <div class="input-group mb-3">
	    <div class="input-group-text">네이버 검색어로 검색(페이지번호)</div>
	    <input type="text" name="page" id="page" value="2" class="form-control"/>
	    <div class="input-group-append"><input type="button" value="크롤링4" onclick="crawling4()" class="btn btn-info"/></div>
    </div>
    <hr/>
    <div class="input-group mb-3">
	    <div class="input-group-text">네이버 검색어로 검색(이미지)</div>
	    <input type="text" name="searchString" id="searchString" value="인사이드아웃2" class="form-control"/>
    </div>
    <div class="input-group mb-3">
	    <div class="input-group-text">네이버 검색어로 검색(페이지번호)</div>
	    <input type="text" name="page" id="page" value="2" class="form-control"/>
	    <div class="input-group-append"><input type="button" value="크롤링5" onclick="crawling5()" class="btn btn-info"/></div>
    </div>
    <hr/>
    <div class="input-group mb-3">
      <!-- <div class="input-group-prepend"><input type="button" value="새로고침" onclick="location.reload()" class="btn btn-info" /></div> -->
      <span class="input-group-text" style="width:50%">멜론차트</span>
      <div class="input-group-append mr-4"><input type="button" value="웹크롤링6" onclick="crawlingCheck()" class="btn btn-success" /></div>
      <div class="input-group-append"><span id="spinnerIcon" style="display:none"><span class="spinner-border"></span>검색중입니다.<span class="spinner-border"></span></span></div>
    </div>
  </form>
  <hr/>
  <div id="demo"></div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
<!-- 위로가기 버튼 -->
<h6 id="topBtn" class="text-right mr-3"><img src="${ctp}/images/arrowTop.gif" title="위로이동"/></h6>
</body>
</html>