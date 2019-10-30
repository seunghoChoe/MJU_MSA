<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <title>맛집 목록</title>
</head>

<body>
<div>
    <h1>식당 전체 조회</h1>

    <c:forEach var="restaurant" items="${restaurantList}">

        <ul>
        	<li>${restaurant.res_index}
        		<input type="button" value="delete" onclick="location.href='/deleteRestaurant/${restaurant.res_index}'">
        	</li>
        <li><a href="localhost:8080/restaurants/${restaurant.res_index}">${restaurant.res_name}</a></li>
        <li>${restaurant.res_category}</li>
        <li>${restaurant.res_grade}</li>
        <li>${restaurant.res_expected_minutes}</li>
        
        </ul>
    </c:forEach>
</div>

<script></script>
</body>