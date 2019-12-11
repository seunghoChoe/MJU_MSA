<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<head>
    <link href="/resources/css/fragments/homeCarousel.css?v=<%=System.currentTimeMillis() %>" rel="stylesheet">
    <script src="/resources/js/fragments/homeCarousel.js?v=<%=System.currentTimeMillis() %>" type="text/javascript"></script>
    <title>명지리본: 명지대학교 맛집 검색</title>
</head>

<div id="homeCarousel" class="container">
    <div class="container my-4">
        <h5><span class="colorText">최근</span> 등록된 맛집 정보</h5>

        <div id="multi-item-example" class="carousel slide" data-ride="carousel">
            <div class="carousel-inner" role="listbox">
                <%-- 슬라이드1 --%>
                <div class="carousel-item active">
                    <div class="row">
                        <%-- 슬라이드1 - 카드1 --%>
                        <div class="col-md-4">
                            <div class="card ribbonEffect">
                                <img class="card-img-top" src="${restaurantList[0].res_image}" alt="Card image cap">
                                <div class="card-img-text">
                                    <p class="card-title">${restaurantList[0].res_name} <span class="card-category">${restaurantList[0].res_category}</span></p>
                                    <p class="card-text">${restaurantList[0].res_content}</p>
                                </div>
                                <div class="card-more">
                                    <span class="restaurantHref" data-toggle="tooltip" data-placement="top" title="맛집 보기" onclick="getRestaurant(${restaurantList[0].res_index})">
                                        <i class="fas fa-angle-double-right"></i> 더보기
                                    </span>
                                </div>
                            </div>
                        </div>
                        <%-- 슬라이드1 - 카드2 --%>
                        <div class="col-md-4">
                            <div class="card ribbonEffect">
                                <img class="card-img-top" src="${restaurantList[1].res_image}" alt="Card image cap">
                                <div class="card-img-text">
                                    <p class="card-title">${restaurantList[1].res_name} <span class="card-category">${restaurantList[1].res_category}</span></p>
                                    <p class="card-text">${restaurantList[1].res_content}</p>
                                </div>
                                <div class="card-more">
                                    <span class="restaurantHref" data-toggle="tooltip" data-placement="top" title="맛집 보기" onclick="getRestaurant(${restaurantList[1].res_index})">
                                        <i class="fas fa-angle-double-right"></i> 더보기
                                    </span>
                                </div>
                            </div>
                        </div>
                        <%-- 슬라이드1 - 카드3 --%>
                        <div class="col-md-4">
                            <div class="card ribbonEffect">
                                <img class="card-img-top" src="${restaurantList[2].res_image}" alt="Card image cap">
                                <div class="card-img-text">
                                    <p class="card-title">${restaurantList[2].res_name} <span class="card-category">${restaurantList[2].res_category}</span></p>
                                    <p class="card-text">${restaurantList[2].res_content}</p>
                                </div>
                                <div class="card-more">
                                    <span class="restaurantHref" data-toggle="tooltip" data-placement="top" title="맛집 보기" onclick="getRestaurant(${restaurantList[2].res_index})">
                                        <i class="fas fa-angle-double-right"></i> 더보기
                                    </span>
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
                            <div class="card ribbonEffect">
                                <img class="card-img-top" src="${restaurantList[3].res_image}" alt="Card image cap">
                                <div class="card-img-text">
                                    <p class="card-title">${restaurantList[3].res_name} <span class="card-category">${restaurantList[3].res_category}</span></p>
                                    <p class="card-text">${restaurantList[3].res_content}</p>
                                </div>
                                <div class="card-more">
                                    <span class="restaurantHref" data-toggle="tooltip" data-placement="top" title="맛집 보기" onclick="getRestaurant(${restaurantList[3].res_index})">
                                        <i class="fas fa-angle-double-right"></i> 더보기
                                    </span>
                                </div>
                            </div>
                        </div>
                        <%-- 슬라이드2 - 카드2 --%>
                        <div class="col-md-4">
                            <div class="card ribbonEffect">
                                <img class="card-img-top" src="${restaurantList[4].res_image}" alt="Card image cap">
                                <div class="card-img-text">
                                    <p class="card-title">${restaurantList[4].res_name} <span class="card-category">${restaurantList[4].res_category}</span></p>
                                    <p class="card-text">${restaurantList[4].res_content}</p>
                                </div>
                                <div class="card-more">
                                    <span class="restaurantHref" data-toggle="tooltip" data-placement="top" title="맛집 보기" onclick="getRestaurant(${restaurantList[4].res_index})">
                                        <i class="fas fa-angle-double-right"></i> 더보기
                                    </span>
                                </div>
                            </div>
                        </div>
                        <%-- 슬라이드2 - 카드3 --%>
                        <div class="col-md-4">
                            <div class="card ribbonEffect">
                                <img class="card-img-top" src="${restaurantList[5].res_image}" alt="Card image cap">
                                <div class="card-img-text">
                                    <p class="card-title">${restaurantList[5].res_name} <span class="card-category">${restaurantList[5].res_category}</span></p>
                                    <p class="card-text">${restaurantList[5].res_content}</p>
                                </div>
                                <div class="card-more">
                                    <span class="restaurantHref" data-toggle="tooltip" data-placement="top" title="맛집 보기" onclick="getRestaurant(${restaurantList[5].res_index})">
                                        <i class="fas fa-angle-double-right"></i> 더보기
                                    </span>
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
<br/><br/>