<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <%-- 즐겨찾기 목록 --%>
</head>

<div>
    <c:forEach var="favorite" items="${favoriteList}">
        <p>${favorite.index}</p>
        <p>${favorite.user_id}</p>
        <p>${favorite.res_name}</p>
        <p>${favorite.res_category}</p>
        <p>${favorite.res_grade}</p>
        <p>${favorite.res_expected_minutes}</p>
    </c:forEach>
</div>