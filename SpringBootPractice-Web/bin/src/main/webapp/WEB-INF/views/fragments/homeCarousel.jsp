<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<head>
    <link href="/resources/css/fragments/homeCarousel.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet">
    <title>명지리본: 명지대학교 맛집 검색</title>
</head>

<div id="homeCarousel" class="container">
    <div class="container my-4">
        <p>맛집 추천 목록</p>

        <div id="multi-item-example" class="carousel slide" data-ride="carousel">
            <div class="carousel-inner" role="listbox">
                <%-- 슬라이드1 --%>
                <div class="carousel-item active">
                    <div class="row">
                        <%-- 슬라이드1 - 카드1 --%>
                        <div class="col-md-4">
                            <div class="card mb-2">
                                <img class="card-img-top" src="https://mdbootstrap.com/img/Photos/Horizontal/Nature/4-col/img%20(34).jpg" alt="Card image cap">
                                <div class="card-body">
                                    <h4 class="card-title">Card title</h4>
                                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                                    <button type="button" class="btn btn-secondary" data-toggle="tooltip" data-placement="top" title="맛집 정보 보기">
                                        보기
                                    </button>
                                </div>
                            </div>
                        </div>
                        <%-- 슬라이드1 - 카드2 --%>
                        <div class="col-md-4 clearfix d-none d-md-block">
                            <div class="card mb-2">
                                <img class="card-img-top" src="https://mdbootstrap.com/img/Photos/Horizontal/Nature/4-col/img%20(18).jpg" alt="Card image cap">
                                <div class="card-body">
                                    <h4 class="card-title">Card title</h4>
                                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                                    <button type="button" class="btn btn-secondary" data-toggle="tooltip" data-placement="top" title="맛집 정보 보기">
                                        보기
                                    </button>
                                </div>
                            </div>
                        </div>
                        <%-- 슬라이드1 - 카드3 --%>
                        <div class="col-md-4 clearfix d-none d-md-block">
                            <div class="card mb-2">
                                <img class="card-img-top" src="https://mdbootstrap.com/img/Photos/Horizontal/Nature/4-col/img%20(35).jpg" alt="Card image cap">
                                <div class="card-body">
                                    <h4 class="card-title">Card title</h4>
                                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                                    <button type="button" class="btn btn-secondary" data-toggle="tooltip" data-placement="top" title="맛집 정보 보기">
                                        보기
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <%-- 슬라이드2 --%>
                <div class="carousel-item">
                    <div class="row">
                        <%-- 슬라이드2 - 카드1 --%>
                        <div class="col-md-4">
                            <div class="card mb-2">
                                <img class="card-img-top" src="https://mdbootstrap.com/img/Photos/Horizontal/City/4-col/img%20(60).jpg" alt="Card image cap">
                                <div class="card-body">
                                    <h4 class="card-title">Card title</h4>
                                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                                    <button type="button" class="btn btn-secondary" data-toggle="tooltip" data-placement="top" title="맛집 정보 보기">
                                        보기
                                    </button>
                                </div>
                            </div>
                        </div>
                        <%-- 슬라이드2 - 카드2 --%>
                        <div class="col-md-4 clearfix d-none d-md-block">
                            <div class="card mb-2">
                                <img class="card-img-top" src="https://mdbootstrap.com/img/Photos/Horizontal/City/4-col/img%20(47).jpg" alt="Card image cap">
                                <div class="card-body">
                                    <h4 class="card-title">Card title</h4>
                                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                                    <button type="button" class="btn btn-secondary" data-toggle="tooltip" data-placement="top" title="맛집 정보 보기">
                                        보기
                                    </button>
                                </div>
                            </div>
                        </div>
                        <%-- 슬라이드2 - 카드3 --%>
                        <div class="col-md-4 clearfix d-none d-md-block">
                            <div class="card mb-2">
                                <img class="card-img-top" src="https://mdbootstrap.com/img/Photos/Horizontal/City/4-col/img%20(48).jpg" alt="Card image cap">
                                <div class="card-body">
                                    <h4 class="card-title">Card title</h4>
                                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                                    <button type="button" class="btn btn-secondary" data-toggle="tooltip" data-placement="top" title="맛집 정보 보기">
                                        보기
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%-- 화살표 인디케이터 --%>
            <a class="carousel-control-prev" href="#multi-item-example" role="button" data-slide="prev">
                <i class="fas fa-chevron-left"></i>
            </a>
            <a class="carousel-control-next" href="#multi-item-example" role="button" data-slide="next">
                <i class="fas fa-chevron-right"></i>
            </a>
            <%-- 하단 인디케이터 (향후 삭제) --%>
            <ol class="carousel-indicators">
                <li data-target="#multi-item-example" data-slide-to="0" class="active"></li>
                <li data-target="#multi-item-example" data-slide-to="1"></li>
                <li data-target="#multi-item-example" data-slide-to="2"></li>
            </ol>
        </div>
    </div>
</div>