<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>

<!-- Automatic Slideshow Images -->
<div class="mySlides w3-display-container w3-center">
  <img src="${ctp}/images/banner1.jpg" style="width:100%; height:400px; object-fit:cover;">
  <div class="w3-display-bottommiddle w3-container w3-text-white w3-padding-32 w3-hide-small">
    <h3>Los Angeles</h3>
    <p><b>We had the best time playing at Venice Beach!</b></p>   
  </div>
</div>
<div class="mySlides w3-display-container w3-center">
  <img src="${ctp}/images/banner2.jpg" style="width:100%; height:400px; object-fit:cover;">
  <div class="w3-display-bottommiddle w3-container w3-text-white w3-padding-32 w3-hide-small">
    <h3>New York</h3>
    <p><b>The atmosphere in New York is lorem ipsum.</b></p>    
  </div>
</div>
<div class="mySlides w3-display-container w3-center">
  <img src="${ctp}/images/banner3.jpg" style="width:100%; height:400px; object-fit:cover;">
  <div class="w3-display-bottommiddle w3-container w3-text-white w3-padding-32 w3-hide-small">
    <h3>Blue Door</h3>
    <p><b>Welcome!</b></p>    
  </div>
</div>