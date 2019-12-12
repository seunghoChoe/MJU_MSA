<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<head>
	<link href="/resources/css/fragments/favorite.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet">
	<script src="/resources/js/fragments/favorite.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
	<title>명지리본: 맛집 매거진</title>
</head>

<div id="restaurantList" class="container">
	<spring:url var="restaurantListUri" value="/restaurants"/>

	<%-- 식당 목록 헤더 영역 --%>
	<div class="container headerLine">
	</div>
	<div id="restaurantListHeader" class="container header">
		<h5 class="headerStrongText">&ldquo; 즐겨 찾는 식당 &rdquo;</h5>
		<p>&colon; ${loggedUser.user_id}님이 즐겨찾기한 식당 목록입니다.<br></p>
		<div id="restaurantListHeaderButton" class="container">
			<button id="restaurantListManage" type="button" class="btn btn-sm btn-outline-dark" onclick="manageFavorite()">
				<i class="fas fa-cog"></i> 관리
			</button>
		</div>
		<hr/>
	</div>

	<c:if test="${fn:length(favoriteList) <= 0}">
		<div class="emptyFavoriteList">
			<h5>${loggedUser.user_id}님 자주 가는 식당을 추가해보세요!</h5>
			<span><a id="restaurantListHref" href="${restaurantListUri}"><i class="fas fa-search"></i> 명지대 주변의 식당 보러가기</a></span>
		</div>
	</c:if>

	<c:if test="${fn:length(favoriteList) > 0}">
		<%-- JSTL 리스트 3개씩 (https://roqkffhwk.tistory.com/44, https://sgroom.tistory.com/95) --%>
		<div id="restaurantListContent" class="container">
			<div class="row">
				<c:set var="i" value="0"/>
				<c:set var="j" value="3"/>

				<c:forEach var="favorite" items="${favoriteList}">
					<c:if test="${i % j == 0}">
						<div class="col-md-4">
							<div class="card" onclick="getRestaurant(${favorite.res_index})">
								<c:if test="${favorite.res_grade >= 4.5}">
									<div class="restaurantMedal">
										<i class="fas fa-award fa-3x"></i>
									</div>
								</c:if>
								<img class="card-img" src="${favorite.res_image}">

								<div class="card-img-text">
									<p class="restaurantName">${favorite.res_name} <span class="restaurantGrade" data-toggle="tooltip" data-placement="top" title="평점"><i class="fas fa-star fa-sm"></i> ${favorite.res_grade}</span></p>
									<p class="restaurantCategory">${favorite.res_category} <span class="restaurantTime" data-toggle="tooltip" data-placement="top" title="예상 시간">${favorite.res_expected_minutes}분</span></p>
									<p class="restaurantView"><i class="fas fa-eye"></i> 1,000 &middot; <i class="fas fa-comment-dots"></i><span class="restaurantReview"> 1,000</span></p>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${i % j == j - 2}">
						<div class="col-md-4">
							<div class="card" onclick="getRestaurant(${favorite.res_index})">
								<c:if test="${favorite.res_grade >= 4.5}">
									<div class="restaurantMedal">
										<i class="fas fa-award fa-3x"></i>
									</div>
								</c:if>
								<img class="card-img" src="${favorite.res_image}">

								<div class="card-img-text">
									<p class="restaurantName">${favorite.res_name} <span class="restaurantGrade" data-toggle="tooltip" data-placement="top" title="평점"><i class="fas fa-star fa-sm"></i> ${favorite.res_grade}</span></p>
									<p class="restaurantCategory">${favorite.res_category} <span class="restaurantTime" data-toggle="tooltip" data-placement="top" title="예상 시간">${favorite.res_expected_minutes}분</span></p>
									<p class="restaurantView"><i class="fas fa-eye"></i> 1,000 &middot; <i class="fas fa-comment-dots"></i><span class="restaurantReview"> 1,000</span></p>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${i % j == j - 1}">
						<div class="col-md-4">
							<div class="card" onclick="getRestaurant(${favorite.res_index})">
								<c:if test="${favorite.res_grade >= 4.5}">
									<div class="restaurantMedal">
										<i class="fas fa-award fa-3x"></i>
									</div>
								</c:if>
								<img class="card-img" src="${favorite.res_image}">

								<div class="card-img-text">
									<p class="restaurantName">${favorite.res_name} <span class="restaurantGrade" data-toggle="tooltip" data-placement="top" title="평점"><i class="fas fa-star fa-sm"></i> ${favorite.res_grade}</span></p>
									<p class="restaurantCategory">${favorite.res_category} <span class="restaurantTime" data-toggle="tooltip" data-placement="top" title="예상 시간">${favorite.res_expected_minutes}분</span></p>
									<p class="restaurantView"><i class="fas fa-eye"></i> 1,000 &middot; <i class="fas fa-comment-dots"></i><span class="restaurantReview"> 1,000</span></p>
								</div>
							</div>
						</div>
					</c:if>
					<c:set var="i" value="${i + 1}"/>
				</c:forEach>
			</div>
		</div>
	</c:if>

	<%-- 게시글 목록 푸터 영역 --%>
	<%-- 페이징 또는 더보기 버튼 --%>
	<div class="container">
		<hr/>
	</div>
</div>

<jsp:include page="/WEB-INF/views/fragments/serverMessage.jsp"/>

<%--<div id="favoriteList" class="container">--%>
<%--	&lt;%&ndash; 즐겨찾기 목록 헤더 영역 &ndash;%&gt;--%>
<%--	<div class="container headerLine">--%>
<%--	</div>--%>
<%--	<div id="favoriteListHeader" class="container header">--%>
<%--		<h5 class="headerStrongText">&ldquo; 즐겨찾는 식당 &rdquo;</h5>--%>
<%--		<p>&colon; ${loggedUser.user_id}님이 즐겨찾기한 식당 목록입니다.<br></p>--%>
<%--		<hr/>--%>

<%--		<c:if test="${fn:length(favoriteList) <= 0}">--%>
<%--			<div class="card myFavoriteList">--%>
<%--				<p>${loggedUser.user_id}님은 즐겨찾기한 식당이 없습니다.</p>--%>
<%--				<p>자주 가는 식당을 추가해보세요!</p>--%>
<%--			</div>--%>
<%--		</c:if>--%>
<%--		<c:if test="${fn:length(favoriteList) > 0}">--%>
<%--			<c:forEach var="favorite" items="${favoriteList}">--%>
<%--				<div class="card myFavoriteList" onclick="viewRestaurant(${favorite.res_index})">--%>
<%--					<img class="card-img" src="${favorite.res_image}">--%>
<%--					<c:if test="${favorite.res_grade >= 4.5}">--%>
<%--						<div class="restaurantMedal">--%>
<%--							<i class="fas fa-award fa-3x"></i>--%>
<%--						</div>--%>
<%--					</c:if>--%>
<%--					<img class="card-img" src="${favorite.res_image}">--%>

<%--					<div class="card-img-text">--%>
<%--						<p class="restaurantName">${favorite.res_name} <span class="restaurantGrade" data-toggle="tooltip" data-placement="top" title="평점"><i class="fas fa-star fa-sm"></i> ${favorite.res_grade}</span></p>--%>
<%--						<p class="restaurantCategory">${favorite.res_category} <span class="restaurantTime" data-toggle="tooltip" data-placement="top" title="예상 시간">${favorite.res_expected_minutes}분</span></p>--%>
<%--					</div>--%>
<%--				</div>--%>
<%--			</c:forEach>--%>
<%--		</c:if>--%>
<%--	</div>--%>
<%--</div>--%>