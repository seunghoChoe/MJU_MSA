<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <%-- 즐겨찾기 목록 --%>
</head>

<div>
    <div class="card" style="width:40%; margin: auto;">
	    <c:forEach var="favorite" items="${favoriteList}">
	        <p>${favorite.res_index}<br>
	        ${loggedUser.user_id}<br>
	        ${favorite.res_name}<br>
	        ${favorite.res_category}<br>
	        ${favorite.res_grade}<br>
	        ${favorite.res_expected_minutes}</p>
	    </c:forEach>
    </div>
</div>