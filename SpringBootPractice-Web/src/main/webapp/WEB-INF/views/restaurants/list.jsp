<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <title>맛집 목록</title>
</head>

<body>
<div>
    <p>${zz}</p>

    <c:forEach var="restaurant" items="${restaurantList}">

        <p>${restaurant.res_index}</p>
        <p>${restaurant.res_name}</p>
        <p>${restaurant.res_category}</p>
        <p>${restaurant.res_grade}</p>
        <p>${restaurant.res_expected_minutes}</p>
    </c:forEach>
</div>

</body>