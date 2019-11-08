<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <title>맛집 목록</title>
</head>

<body>
<div>
    <h1>식당 추가</h1>

    <form method="post" action="/restaurant">
   			 <div class="form-group">
                    <label for="user_id" class="col-form-label">식당연번</label>
                    <div class="input-group input-group-seamless">
                        <span class="input-group-prepend">
                            <span class="input-group-text">
                                <i class="fas fa-user"></i>
                            </span>
                        </span>
                        <input type="text" class="form-control" id="res_index" name="res_index" placeholder="res_index"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="user_id" class="col-form-label">식당이름</label>
                    <div class="input-group input-group-seamless">
                        <span class="input-group-prepend">
                            <span class="input-group-text">
                                <i class="fas fa-user"></i>
                            </span>
                        </span>
                        <input type="text" class="form-control" id="res_name" name="res_name" placeholder="res_name"/>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="user_password" class="col-form-label">식당 카테고리</label>
                    <div class="input-group input-group-seamless">
                        <span class="input-group-prepend">
                            <span class="input-group-text">
                                <i class="fas fa-lock"></i>
                            </span>
                        </span>
                        <input type="text" class="form-control" id="res_category" name="res_category" placeholder="res_category"/>
                    </div>
                </div>
                 <div class="form-group">
                    <label for="user_password" class="col-form-label">예상 시간 </label>
                    <div class="input-group input-group-seamless">
                        <span class="input-group-prepend">
                            <span class="input-group-text">
                                <i class="fas fa-lock"></i>
                            </span>
                        </span>
                        <input type="text" class="form-control" id="res_expected_minutes" name="res_expected_minutes" placeholder="res_expected_minutes"/>
                    </div>
                </div>

                <div id="buttonGroup">
                    <button id="loginButton" type="submit" class="btn btn-dark btn-block">
                        <strong>식당 등록</strong>
                    </button>
                </div>
         </form>
</div>

<script></script>
</body>