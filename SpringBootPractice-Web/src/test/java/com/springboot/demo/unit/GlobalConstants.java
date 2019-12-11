//package com.springboot.demo.unit;
//
//import com.springboot.demo.model.Menu;
//import com.springboot.demo.model.Post;
//import com.springboot.demo.model.Restaurant;
//import com.springboot.demo.model.User;
//import org.springframework.util.LinkedMultiValueMap;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
// *
// * @author Jaeha Son
// * @date 2019-12-13
// * @version 0.9
// * @description
// * 단위 테스트용 공통 클래스
// */
//public class GlobalConstants {
//
//    // 로컬 URI
//    public static final String WEB_URI = "http://localhost:8080";
//    public static final String USER_URI = "http://localhost:8081";
//    public static final String BOARD_URI = "http://localhost:8082";
//    public static final String RESTAURANT_URI = "http://localhost:8083";
//
//    // 모델 상수
//    public static final String SERVER_MESSAGE = "serverMessage";
//    public static final String LOGGED_USER  = "loggedUser";
//
//    /**
//     * 테스트용 계정 생성
//     * @return User
//     *  테스트용 계정 모델
//     */
//    public static User createUser() {
//        User user = new User();
//        user.setUser_id("admin");
//        user.setUser_password("test123!");
//        user.setUser_name("테스트");
//        user.setUser_email("admin@test.com");
//
//        return user;
//    }
//
//    /**
//     * 테스트용 게시글 생성
//     * @return Post
//     *  테스트용 게시글 모델
//     */
//    public static Post createPost() {
//        Post post = new Post();
//        post.setPost_id(1);
//        post.setPost_user_id("admin"); // 세션에 admin 계정이 등록되도록 테스트하므로
//        post.setPost_title("테스트 제목");
//        post.setPost_content("테스트 내용");
//        post.setPost_image("http://via.placeholder.com/350x150"); // UploadController 클래스에서 지정한 기본 값
//        post.setPost_created_date(LocalDateTime.now());
//        post.setPost_updated_date(LocalDateTime.now());
//
//        return post;
//    }
//
//    /**
//     * 테스트용 메뉴 목록 생성
//     * @return List<Menu>
//     *  테스트용 메뉴 목록 모델
//     */
//    public static List<Menu> createMenuList() {
//        return new ArrayList<Menu>() {
//            {
//                add(new Menu(1, "짜장면", 5000));
//                add(new Menu(2, "짬뽕", 6000));
//            }
//        };
//    }
//
//    /**
//     * 테스트용 식당 생성
//     * @return Restaurant
//     *  테스트용 식당 모델
//     */
//    public static Restaurant createRestaurant() {
//        Restaurant restaurant = new Restaurant();
//        restaurant.setRes_index(1);
//        restaurant.setRes_name("테스트 식당");
//        restaurant.setRes_category("테스트 카테고리");
//        restaurant.setRes_grade(5);
//        restaurant.setRes_expected_minutes(10);
//        restaurant.setRes_content("테스트 소개");
//        restaurant.setRes_image("http://via.placeholder.com/350x150");
//        restaurant.setRes_menues(new ArrayList<Menu>() {
//            {
//                // res_menues -> 의도된 오타임을 유의한다.
//                add(new Menu(1, "짜장면", 5000));
//                add(new Menu(2, "짬뽕", 6000));
//            }
//        });
//
//        return restaurant;
//    }
//
//    /**
//     * 테스트용 식당 생성
//     * @return LinkedMultiValueMap<String, String>
//     *  mockMvc 객체를 통해 전달될 매개변수
//     */
//    public static LinkedMultiValueMap<String, String> createRestaurantMenusParams() {
//        LinkedMultiValueMap<String, String> restaurantMenus = new LinkedMultiValueMap<String, String>() {
//            {
//                add("res_menues[0].menu_index", "1");
//                add("res_menues[0].menu_name", "짜장면");
//                add("res_menues[0].menu_price", "5000");
//
//                add("res_menues[1].menu_index", "2");
//                add("res_menues[1].menu_name", "짬뽕");
//                add("res_menues[1].menu_price", "6000");
//            }
//        };
//
//        return restaurantMenus;
//    }
//}