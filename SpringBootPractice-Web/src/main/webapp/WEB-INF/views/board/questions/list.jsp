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
    <spring:url var="newQuestionUri" value="/board/questions/new"/>

    <%-- 문의사항 목록 헤더 영역 --%>
    <div class="container headerLine">
    </div>
    <div id="postListHeader" class="container header">
        <h5 class="headerStrongText">&ldquo; 문의사항 &rdquo;</h5>
        <p>&colon; 명지리본 서비스에 대해 자유롭게 문의사항을 남겨주세요.</p>

        <div id="postItemHeaderButton" class="container">
            <button id="postItemNew" type="button" class="btn btn-sm btn-outline-dark" onclick="location.href='${newQuestionUri}'">
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
                            <div class="card-title">
                                <i class="fas fa-comments qnaIcon" data-toggle="tooltip" data-placement="top" title="${question.qna_id}번 문의사항"></i>
                                <span class="qnaCategory"><i class="fas fa-tag"></i> ${question.qna_category}</span>
                            </div>
                            <div class="card-content">
                                <span class="qnaContent">${question.qna_question}</span>
                            </div>
                            <div class="card-footer">
                                <span class="qnaUserId"><i class="fas fa-user-circle"></i> ${question.qna_user_id}</span>
                            </div>
                        </div>
                    </div>
            </c:forEach>
        </div>
    </div>

    <%-- 문의사항 목록 푸터 영역 --%>
    <%-- 페이징 또는 더보기 버튼 --%>
    <div class="container">
        <hr/>
    </div>
</div>

<jsp:include page="/WEB-INF/views/fragments/serverMessage.jsp"/>