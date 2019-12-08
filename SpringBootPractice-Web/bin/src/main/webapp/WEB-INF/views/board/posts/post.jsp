<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<head>
    <link href="/resources/css/board/posts/post.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet">
    <script src="/resources/js/board/posts/post.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
    <title>명지리본: 맛집 매거진</title>
</head>

<div id="postItem" class="container">
    <spring:url var="postListUri" value="/board/posts"/>

    <%-- 게시글 헤더 영역 --%>
    <div class="container headerLine">
    </div>
    <div id="postItemHeader" class="container header">
        <h5 class="headerStrongText">&ldquo; 맛집 매거진 &rdquo;</h5>
        <p>&colon; ${post.post_title}</p>

    <%-- Row 버전 --%>
<%--        <div id="postItemHeaderGroup" class="container row">--%>
<%--            <div id="postItemHeaderInfo" class="container col-md-6">--%>
<%--                <i class="fas fa-user-circle"></i>--%>
<%--                <span id="postItemUserId">${post.post_user_id}</span>--%>
<%--                &lt;%&ndash; LocalDateTime 포맷 (https://stackoverflow.com/questions/35606551/jstl-localdatetime-format) &ndash;%&gt;--%>
<%--                <fmt:parseDate value="${post.post_created_date}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedCreatedDate" type="both" />--%>
<%--                <span id="postItemCreatedDate"><fmt:formatDate value="${parsedCreatedDate}" pattern="yyyy-MM-dd HH:mm"/></span>--%>
<%--            </div>--%>
<%--            <div id="postItemHeaderButton" class="container col-md-6">--%>
<%--                <button id="postItemUri" type="button" class="btn btn-sm btn-outline-dark">URL 복사</button>--%>
<%--                <button id="postItemEdit" type="button" class="btn btn-sm btn-outline-dark"><i class="fas fa-edit"></i> 수정</button>--%>
<%--                <button id="postItemDelete" type="button" class="btn btn-sm btn-outline-dark"><i class="fas fa-eraser"></i> 삭제</button>--%>
<%--            </div>--%>
<%--        </div>--%>

        <%-- FLEX 버전 (https://www.w3schools.com/bootstrap4/bootstrap_flex.asp) class 값에 p-2는 간격 --%>
        <div id="postItemHeaderGroup" class="d-flex">
            <div id="postItemHeaderInfo" class="container">
                <i class="fas fa-user-circle"></i>
                <span id="postItemUserId">${post.post_user_id}</span>
                <%-- LocalDateTime 포맷 (https://stackoverflow.com/questions/35606551/jstl-localdatetime-format) --%>
                <fmt:parseDate value="${post.post_created_date}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedCreatedDate" type="both" />
                <span id="postItemCreatedDate"><fmt:formatDate value="${parsedCreatedDate}" pattern="yyyy-MM-dd HH:mm"/></span>
            </div>
            <div id="postItemHeaderButton" class="container ml-auto">
                <button id="postItemUri" type="button" class="btn btn-sm btn-outline-dark">URL 복사</button>
                <button id="postItemEdit" type="button" class="btn btn-sm btn-outline-dark" onclick="editPost(${post.post_id})"><i class="fas fa-edit"></i> 수정</button>
                <button id="postItemDelete" type="button" class="btn btn-sm btn-outline-dark" onclick="deletePost(${post.post_id})"><i class="fas fa-eraser"></i> 삭제</button>
            </div>
        </div>
        <hr/>
    </div>

    <%-- 게시글 컨텐트 영역 --%>
    <div id="postItemContent" class="container">
        <p>${post.post_content}</p>
        <hr/>
    </div>

    <%-- 게시글 푸터 영역 --%>
    <div id="postItemFooter" class="container d-flex">
        <div id="postItemFooterPostButton" class="container">
            <button id="postItemView" type="button" class="btn btn-sm btn-outline-dark"><i class="far fa-eye"></i></button>
            <button id="postItemLike" type="button" class="btn btn-sm btn-outline-dark"><i class="far fa-heart"></i></button>
            <button id="postItemComment" type="button" class="btn btn-sm btn-outline-dark"><i class="far fa-comment-dots"></i></button>
        </div>
        <div id="postItemFooterListButton" class="container ml-auto">
            <button id="postItemList" type="button" class="btn btn-sm btn-outline-dark" onclick="location.href='${postListUri}'"><i class="fas fa-list"></i> 목록</button>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/fragments/serverMessage.jsp"/>