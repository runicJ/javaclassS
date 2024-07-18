<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
	  <c:forEach var="photo" items="${vos}" varStatus="st">
	    <div class="card mb-5" style="width:97%;">
		    <div class="card-body m-0 p-2 text-center">
		      <img src="${ctp}/images/photoGallery/${photo[1]}" width="95%" />
		    </div> 
		    <div class="card-footer">
		      <div class="text-center" style="font-size:10px">${photo[0]} : ${photo[1]}</div>
		    </div>
		  </div>
	  </c:forEach>