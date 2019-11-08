<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<head>
    <link href="/resources/css/board/posts/list.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet">
    <script src="/resources/js/board/posts/list.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
    <title>명지리본: 맛집 매거진</title>
</head>

<div id="postList" class="container">
    <spring:url var="newPostUri" value="/board/posts/new"/>

    <%-- 게시글 목록 헤더 영역 --%>
    <div class="container headerLine">
    </div>
    <div id="postListHeader" class="container header">
        <h5 class="headerStrongText">&ldquo; 맛집 매거진 &rdquo;</h5>
        <p>&colon; 명지리본 에디터가 선택한 맛집 매거진!</p>

        <div id="postItemHeaderButton" class="container">
            <button id="postItemNew" type="button" class="btn btn-sm btn-outline-dark" onclick="location.href='${newPostUri}'">
                <i class="fas fa-pen-square"></i> 등록
            </button>
        </div>
        <hr/>
    </div>

    <%-- JSTL 리스트 2개씩 (https://roqkffhwk.tistory.com/44, https://sgroom.tistory.com/95) --%>
    <div id="postListContent" class="container">
        <div class="row">
            <c:set var="i" value="0"/>
            <c:set var="j" value="2"/>

            <c:forEach var="post" items="${postList}">
                <c:if test="${i % j == 0}">
                    <div class="col-md-6">
                        <div class="card" onclick="getPost(${post.post_id})">
                            <img class="card-img" src="<c:url value="${post.post_image}"/>">
                            <div class="card-img-header">
                                <h4 class="card-img-text">${post.post_title}</h4>
                                <div class="container headerWhiteLine">
                                </div>
                                <p class="card-img-text">${post.post_user_id}</p>
                            </div>
                            <%-- <div class="card-profile-img">--%>
                            <%-- </div>--%>
                            <div class="card-content">
                                <%--<p>${post.post_content}</p>--%>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${i % j == j - 1}">
                    <div class="col-md-6">
                        <div class="card" onclick="getPost(${post.post_id})">
                            <img class="card-img" src="<c:url value="${post.post_image}"/>">
                            <div class="card-img-header">
                                <h4 class="card-img-text">${post.post_title}</h4>
                                <div class="container headerWhiteLine">
                                </div>
                                <p class="card-img-text">${post.post_user_id}</p>
                            </div>
                            <%-- <div class="card-profile-img">--%>
                            <%-- </div>--%>
                            <div class="card-content">
                                <%--<p>${post.post_content}</p>--%>
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