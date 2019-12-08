<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<head>
    <link href="/resources/css/restaurants/list.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet">
    <script src="/resources/js/restaurants/list.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
    <title>명지리본: 맛집 스토리</title>
</head>

<div id="restaurantList" class="container">
    <spring:url var="newRestaurantUri" value="/restaurants/new"/>

    <%-- 식당 목록 헤더 영역 --%>
    <div class="container headerLine">
    </div>
    <div id="restaurantListHeader" class="container header">
        <h5 class="headerStrongText">&ldquo; 맛집 스토리 &rdquo;</h5>
        <p>&colon; 명지대 주변의 모든 식당</p>
        <div id="restaurantItemHeaderButton" class="container">
            <button id="restaurantItemNew" type="button" class="btn btn-sm btn-outline-dark" onclick="location.href='${newRestaurantUri}'">
                <i class="fas fa-pen-square"></i> 등록
            </button>
        </div>
        <hr/>
    </div>

    <%-- JSTL 리스트 3개씩 (https://roqkffhwk.tistory.com/44, https://sgroom.tistory.com/95) --%>
    <div id="restaurantListContent" class="container">
        <div class="row">
            <c:set var="i" value="0"/>
            <c:set var="j" value="3"/>

            <c:forEach var="restaurant" items="${restaurantList}">
                <c:if test="${i % j == 0}">
                    <div class="col-md-4">
                        <div class="card" onclick="getRestaurant(${restaurant.res_index})">
                            <c:if test="${restaurant.res_grade >= 4.5}">
                                <div class="restaurantMedal">
                                    <i class="fas fa-award fa-3x"></i>
                                </div>
                            </c:if>
                            <img class="card-img" src="${restaurant.res_image}">

                            <div class="card-img-text">
                                <p class="restaurantName">${restaurant.res_name} <span class="restaurantGrade" data-toggle="tooltip" data-placement="top" title="평점"><i class="fas fa-star fa-sm"></i> ${restaurant.res_grade}</span></p>
                                <p class="restaurantCategory">${restaurant.res_category} <span class="restaurantTime" data-toggle="tooltip" data-placement="top" title="예상 시간">${restaurant.res_expected_minutes}분</span></p>
                                <p class="restaurantView"><i class="fas fa-eye"></i> 1,000 &middot; <i class="fas fa-comment-dots"></i><span class="restaurantReview"> 1,000</span></p>
                            </div>

                            <div class="card-favorite">
                                <span class="restaurantFavorite" onclick="addFavorite(${restaurant.res_index})"><i class="fas fa-star" data-toggle="tooltip" data-placement="top" title="즐겨찾기 추가"></i></span>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${i % j == j - 2}">
                    <div class="col-md-4">
                        <div class="card" onclick="getRestaurant(${restaurant.res_index})">
                            <c:if test="${restaurant.res_grade >= 4.5}">
                                <div class="restaurantMedal">
                                    <i class="fas fa-award fa-3x"></i>
                                </div>
                            </c:if>
                            <img class="card-img" src="${restaurant.res_image}">

                            <div class="card-img-text">
                                <p class="restaurantName">${restaurant.res_name} <span class="restaurantGrade" data-toggle="tooltip" data-placement="top" title="평점"><i class="fas fa-star fa-sm"></i> ${restaurant.res_grade}</span></p>
                                <p class="restaurantCategory">${restaurant.res_category} <span class="restaurantTime" data-toggle="tooltip" data-placement="top" title="예상 시간">${restaurant.res_expected_minutes}분</span></p>
                                <p class="restaurantView"><i class="fas fa-eye"></i> 1,000 &middot; <i class="fas fa-comment-dots"></i><span class="restaurantReview"> 1,000</span></p>
                            </div>

                            <div class="card-favorite">
                                <span class="restaurantFavorite" onclick="addFavorite(${restaurant.res_index})"><i class="fas fa-star" data-toggle="tooltip" data-placement="top" title="즐겨찾기 추가" onclick=""></i></span>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${i % j == j - 1}">
                    <div class="col-md-4">
                        <div class="card" onclick="getRestaurant(${restaurant.res_index})">
                            <c:if test="${restaurant.res_grade >= 4.5}">
                                <div class="restaurantMedal">
                                    <i class="fas fa-award fa-3x"></i>
                                </div>
                            </c:if>
                            <img class="card-img" src="${restaurant.res_image}">

                            <div class="card-img-text">
                                <p class="restaurantName">${restaurant.res_name} <span class="restaurantGrade" data-toggle="tooltip" data-placement="top" title="평점"><i class="fas fa-star fa-sm"></i> ${restaurant.res_grade}</span></p>
                                <p class="restaurantCategory">${restaurant.res_category} <span class="restaurantTime" data-toggle="tooltip" data-placement="top" title="예상 시간">${restaurant.res_expected_minutes}분</span></p>
                                <p class="restaurantView"><i class="fas fa-eye"></i> 1,000 &middot; <i class="fas fa-comment-dots"></i><span class="restaurantReview"> 1,000</span></p>
                            </div>

                            <div class="card-favorite">
                                <span class="restaurantFavorite" onclick="addFavorite(${restaurant.res_index})"><i class="fas fa-star" data-toggle="tooltip" data-placement="top" title="즐겨찾기 추가" onclick=""></i></span>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:set var="i" value="${i + 1}"/>
            </c:forEach>
        </div>
    </div>

    <%-- 게시글 목록 푸터 영역 --%>
    <%-- 페이징 또는 더보기 버튼 --%>
    <div class="container">
        <hr/>
    </div>
</div>

<jsp:include page="/WEB-INF/views/fragments/serverMessage.jsp"/>