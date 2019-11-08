<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<head>
    <link href="/resources/css/board/posts/list.css" rel="stylesheet">
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

            <c:forEach var="question" items="${questionList}" varStatus="loop">
                    <div class="col-md-6">
                        <div class="card">
                           <p>
                           ${question.qna_id}<br>
                           ${question.qna_user_id}<br>
                           ${question.qna_manager}<br>
                           ${question.qna_category}<br>
                           ${question.qna_question}<br>
                           ${question.qna_answer}<br>
                           </p>
                        </div>
                    </div>
            </c:forEach>
        </div>
    </div>

    <%-- 게시글 목록 푸터 영역 --%>
    <%-- 페이징 또는 더보기 버튼 --%>
    <div class="container">
        <hr/>
    </div>

    <%-- 카드 예시: https://m.blog.naver.com/PostView.nhn?blogId=pjh445&logNo=221159714850&proxyReferer=https%3A%2F%2Fwww.google.com%2F --%>
    <%--    <c:forEach var="post" items="${postList}">--%>
    <%--        <div class="postListItem container">--%>
    <%--            <div class="card" onclick="getPost(${post.post_id})">--%>
    <%--                <img id="cardThumbnail" class="card-img-top" src="https://mdbootstrap.com/img/Photos/Horizontal/Nature/4-col/img%20(34).jpg" alt="Card image cap">--%>
    <%--                <div class="card-img-overlay">--%>
    <%--                    <h5 class="card-title">${post.post_title}</h5>--%>
    <%--                </div>--%>
    <%--                <div class="card-body">--%>
    <%--                    <p class="card-text">${post.post_content}</p>--%>
    <%--                </div>--%>
    <%--                <div class="card-footer">--%>
    <%--                    <p class="card-text">${post.post_user_id}</p>--%>
    <%--                </div>--%>
    <%--            </div>--%>
    <%--        </div>--%>
    <%--    </c:forEach>--%>
</div>

<jsp:include page="/WEB-INF/views/fragments/serverMessage.jsp"/>