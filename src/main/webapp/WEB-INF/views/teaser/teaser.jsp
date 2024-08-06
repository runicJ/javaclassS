d<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>teaser.jsp</title>
<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js"></script>
    <link href="${ctp}/css/navmenu-reveal.css" rel="stylesheet">
    <link href="${ctp}/css/style.css" rel="stylesheet">
    <link href="${ctp}/css/full-slider.css" rel="stylesheet">
    <link href="${ctp}/css/icostyle.css" rel="stylesheet" type="text/css" />
    <link href="${ctp}/css/animated-masonry-gallery.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${ctp}/css/normalize.css" />
    <link rel="stylesheet" type="text/css" href="${ctp}/css/demo.css" />
    <link rel="stylesheet" type="text/css" href="${ctp}/css/component.css" />
    <link href="${ctp}/css/lightbox.css" rel="stylesheet" type="text/css" />
    <style>
    	.photostack-img img {
    		width: 100%;
    		height: 100%;
    		object-fit: cover;
    	}
    	
    /* 텍스트 스타일 수정: 이탤릭체로 설정 */
    .photostack-title {
        font-weight: 700; /* 폰트를 굵게 설정 */
        color: black;    /* 글자 색상을 검정으로 설정 */
        font-style: italic; /* 이탤릭체로 설정 */
    }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br><p>
    <div class="canvas gallery"><br>
        <h1 class="blog-post-title text-center">FINAL PROJECT TEASER 0</h1>
        <span class="title-divider"></span>
            <section id="photostack-3" class="photostack">
                <div>
                    <figure>
                        <a href="${ctp}/teaser/01.jpg"  data-lightbox="studio2" class="photostack-img"><img src="${ctp}/teaser/01.jpg" alt="img01"/></a>
                        <figcaption>
                            <h2 class="photostack-title">01 IDOL FAN 이규혁</h2>
                        </figcaption>
                    </figure>
                    <figure>
                        <a href="${ctp}/teaser/02.jpg"  data-lightbox="studio2" class="photostack-img"><img src="${ctp}/teaser/02.jpg" alt="img02"/></a>
                        <figcaption>
                            <h2 class="photostack-title">02 BOOK TALK 윤서경</h2>
                        </figcaption>
                    </figure>
                    <figure>
                        <a href="${ctp}/teaser/03.jpg"  data-lightbox="studio2" class="photostack-img"><img src="${ctp}/teaser/03.jpg" alt="img03"/></a>
                        <figcaption>
                            <h2 class="photostack-title">03 BANK ACCOUNT 이규희</h2>
                        </figcaption>
                    </figure>
                    <figure>
                       <a href="${ctp}/teaser/04.jpg"  data-lightbox="studio2" class="photostack-img"><img src="${ctp}/teaser/04.jpg" alt="img01"/></a>
                        <figcaption>
                            <h2 class="photostack-title">04 CAR LAB 김성현</h2>
                        </figcaption>
                    </figure>
                    <figure>
                        <a href="${ctp}/teaser/05.jpg"  data-lightbox="studio2" class="photostack-img"><img src="${ctp}/teaser/05.jpg" alt="img04"/></a>
                        <figcaption>
                            <h2 class="photostack-title">05 WALK WITH 배민재</h2>
                        </figcaption>
                    </figure>
                    <figure>
                        <a href="${ctp}/teaser/06.jpg"  data-lightbox="studio2" class="photostack-img"><img src="${ctp}/teaser/06.jpg" alt="img06"/></a>
                        <figcaption>
                            <h2 class="photostack-title">06 QUESTION MARK 박영서</h2>
                        </figcaption>
                    </figure>
                    <figure>
                        <a href="${ctp}/teaser/07.jpg" data-lightbox="studio2" class="photostack-img"><img src="${ctp}/teaser/07.jpg" alt="img07"/></a>
                        <figcaption>
                            <h2 class="photostack-title">07 CLOUD SCHEDULER 탁민아</h2>
                        </figcaption>
                    </figure>
                    <figure>
                        <a href="${ctp}/teaser/08.jpg" data-lightbox="studio2" class="photostack-img"><img src="${ctp}/teaser/08.jpg" alt="img08"/></a>
                        <figcaption>
                            <h2 class="photostack-title">08 SPORT MATCH 심지언</h2>
                        </figcaption>
                    </figure>
                    <figure>
                        <a href="${ctp}/teaser/09.jpg" data-lightbox="studio2" class="photostack-img"><img src="${ctp}/teaser/09.jpg" alt="img09"/></a>
                        <figcaption>
                            <h2 class="photostack-title">09 PET COMMUNITY 최민영</h2>
                        </figcaption>
                    </figure>
                    <figure>
                        <a href="${ctp}/teaser/10.jpg" data-lightbox="studio2" class="photostack-img"><img src="${ctp}/teaser/10.jpg" alt="img10"/></a>
                        <figcaption>
                            <h2 class="photostack-title">10 INGAME XBOX 김한나</h2>
                        </figcaption>
                    </figure>
                    <figure>
                        <a href="${ctp}/teaser/11.jpg" data-lightbox="studio2" class="photostack-img"><img src="${ctp}/teaser/11.jpg" alt="img11"/></a>
                        <figcaption>
                            <h2 class="photostack-title">11 CAR RENTAL 김도완</h2>
                        </figcaption>
                    </figure>
                    <figure>
                        <a href="${ctp}/teaser/12.jpg" data-lightbox="studio2" class="photostack-img"><img src="${ctp}/teaser/12.jpg" alt="img12"/></a>
                        <figcaption>
                            <h2 class="photostack-title">12 ENGINEER PRO 안소은</h2>
                        </figcaption>
                    </figure>
                    <figure>
                        <a href="${ctp}/teaser/13.jpg" data-lightbox="studio2" class="photostack-img"><img src="${ctp}/teaser/13.jpg" alt="img13"/></a>
                        <figcaption>
                            <h2 class="photostack-title">13 NATURE MALL 김은하</h2>
                        </figcaption>
                    </figure>
                    <figure>
                        <a href="${ctp}/teaser/14.jpg" data-lightbox="studio2" class="photostack-img"><img src="${ctp}/teaser/14.jpg" alt="img14"/></a>
                        <figcaption>
                            <h2 class="photostack-title">14 LECTURE FLATFORM 이수연</h2>
                        </figcaption>
                    </figure>
                    <figure>
                        <a href="${ctp}/teaser/15.png" data-lightbox="studio2" class="photostack-img"><img src="${ctp}/teaser/15.png" alt="img15"/></a>
                        <figcaption>
                            <h2 class="photostack-title">15 ALLERGIC {,} 조현서</h2>
                        </figcaption>
                    </figure>
                    <figure>
                        <a href="${ctp}/teaser/16.jpg" data-lightbox="studio2" class="photostack-img"><img src="${ctp}/teaser/16.jpg" alt="img16"/></a>
                        <figcaption>
                            <h2 class="photostack-title">16 HIDDEN MARK 성민지</h2>
                        </figcaption>
                    </figure>
                </div>
            </section>
        </div>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
    <script type="text/javascript" src="${ctp}/js/isotope.js"></script>
    <script type="text/javascript" src="${ctp}/js/animated-masonry-gallery.js"></script>
    <script src="${ctp}/js/jasny-bootstrap.min.js"></script>
    <script src="${ctp}/js/classie.js"></script>
    <script src="${ctp}/js/photostack.js"></script>
    <script src="${ctp}/js/main.js"></script>
    <script src="${ctp}/js/lightbox.js"></script>
    <script src="${ctp}/js/modernizr.min.js"></script>
<p><br><p>

</body>
</html>