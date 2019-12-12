package com.springboot.demo.controller;

import com.springboot.demo.interceptor.LoginCheck;
import com.springboot.demo.model.MyRestaurant;
import com.springboot.demo.model.Restaurant;
import com.springboot.demo.service.RestaurantService;
import com.springboot.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import static com.springboot.demo.global.Constants.SERVER_MESSAGE;
import static com.springboot.demo.global.Constants.createApiUri;
import static com.springboot.demo.global.UserSession.getUserSession;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Seungho Choe, Jaeha Son
 * @date 2019-12-13
 * @version 0.9
 * @description
 * 즐겨찾기 뷰와 관련된 Controller 클래스
 */
@RestController
@RequiredArgsConstructor
public class FavoriteViewController {
    private static final Logger logger = LoggerFactory.getLogger(FavoriteViewController.class);
    private final String userServiceUri = createApiUri("user-service");
    private final String restaurantServiceUri = createApiUri("restaurant-service");
    private final UserService userService;
    private final RestaurantService restaurantService;

    /**
     * 즐겨찾기 목록 조회
     * @param mv
     *  ModelAndView 객체
     * @param request
     *  HttpServletRequest 객체
     * @return ModelAndView
     *  즐겨찾기 모델과 JSP 페이지 반환
     */
    @LoginCheck(type = LoginCheck.Type.TRUE)
    @RequestMapping(value = "/favorites", method = RequestMethod.GET)
    public ModelAndView getFavorites(ModelAndView mv, HttpServletRequest request) {
        logger.info("getFavorites()");

        // 즐겨찾기 된 식당 데이터 가져오기.
        String userId = getUserSession(request).getUser_id();
        List<MyRestaurant> favoriteList = userService.getFavorites(userServiceUri + "/user/my-restaurants/" + userId).getBody();

        // 모든 식당 데이터 가져오기.
        List<Restaurant> restaurantList = restaurantService.getRestaurants(restaurantServiceUri + "/restaurants").getBody();

        // 가고 싶은 식당 데이터 추출하기
        List<Restaurant> myRestaurantList = new ArrayList<>();
        for (Restaurant restaurant : restaurantList) {
            for (MyRestaurant myRestaurant : favoriteList) {
                if (myRestaurant.getRes_index() == restaurant.getRes_index()) {
                    myRestaurantList.add(restaurant);
                }
            }
        }

        mv.addObject("favoriteList", myRestaurantList);
        mv.setViewName("fragments/favorite");

        return mv;
    }

    /**
     * 즐겨찾기 등록
     * @param res_index
     *  식당 번호
     * @param mv
     *  ModelAndView 객체
     * @param request
     *  HttpServletRequest 객체
     * @param rttr
     *  RedirectAttributes 객체
     * @return ModelAndView
     *  즐겨찾기 등록 성공 - 성공 메세지와 JSP 페이지 반환
     *  즐겨찾기 등록 실패 - 미구현 (리소스 서버로부터 받은 응답(response)를 검사하여 구현해야 함)
     */
    @LoginCheck(type = LoginCheck.Type.TRUE)
    @RequestMapping(value = "/favorites/new/{res_index}", method = RequestMethod.GET)
    public ModelAndView postFavorite(@PathVariable int res_index, ModelAndView mv, HttpServletRequest request, RedirectAttributes rttr) {
        logger.info("postFavorite()");

        String userId = getUserSession(request).getUser_id();
        MyRestaurant myRestaurant = MyRestaurant.builder().user_id(userId).res_index(res_index).build();
        ResponseEntity<MyRestaurant> response = userService.postFavorite(userServiceUri + "/user/my-restaurant-insert", myRestaurant);

        rttr.addFlashAttribute(SERVER_MESSAGE, "내 식당 목록에 추가되었습니다.");
        mv.setViewName("redirect:/favorites");

        return mv;
    }
}