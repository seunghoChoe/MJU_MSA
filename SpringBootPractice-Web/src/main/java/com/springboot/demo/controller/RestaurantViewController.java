package com.springboot.demo.controller;

import com.springboot.demo.interceptor.LoginCheck;
import com.springboot.demo.model.Grade;
import com.springboot.demo.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.springboot.demo.model.Restaurant;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import static com.springboot.demo.global.Constants.*;
import static com.springboot.demo.global.UserSession.getUserSession;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Jaeha Son, Seungho Choe
 * @date 2019-12-13
 * @version 0.9
 * @description
 * 식당 뷰와 관련된 Controller 클래스
 */
@RestController
@RequiredArgsConstructor
public class RestaurantViewController {
    private static final Logger logger = LoggerFactory.getLogger(RestaurantViewController.class);
    private final String restaurantServiceApi = createApiUri("restaurant-service");
    private final RestaurantService restaurantService;

    /**
     * 식당 등록 폼
     * @param mv
     *  ModelAndView 객체
     * @return ModelAndView
     *  빈 식당 모델과 JSP 페이지 반환
     */
    @LoginCheck(type = LoginCheck.Type.TRUE)
    @RequestMapping(value = "/restaurants/new", method = RequestMethod.GET)
    public ModelAndView getRestaurantNew(ModelAndView mv) {
        logger.info("getRestaurantNew()");

        mv.addObject("restaurant", new Restaurant());
        mv.setViewName("restaurants/new");

        return mv;
    }

    /**
     * 식당 등록
     * @param restaurant
     *  뷰에서 전달된 식당 모델
     * @param result
     *  유효성 검증 결과(BindingResult) 객체
     * @param mv
     *  ModelAndView 객체
     * @param rttr
     *  RedirectAttributes 객체
     * @return ModelAndView
     *  유효성 검증 실패 - 실패 메세지와 JSP 페이지 반환
     *  식당 등록 성공 - 성공 메세지와 JSP 페이지 반환
     *  리소스 서버 응답 실패 - 실패 메세지와 JSP 페이지 반환
     */
    @LoginCheck(type = LoginCheck.Type.TRUE)
    @RequestMapping(value = "/restaurants/new", method = RequestMethod.POST)
    public ModelAndView postRestaurantNew(@ModelAttribute("restaurant") @Valid Restaurant restaurant, BindingResult result, ModelAndView mv, RedirectAttributes rttr) {
        logger.info("postRestaurantNew()");

        if (result.hasErrors()) {
            logger.info(result.toString());

            mv.addObject(SERVER_MESSAGE, "식당 등록 정보를 확인해주세요.");
            mv.setViewName("restaurants/new");

        } else {
            restaurant.setRes_image(getImaSrc(restaurant.getRes_content())); // 썸네일 이미지 세팅
            ResponseEntity<Restaurant> response = restaurantService.postRestaurantNew(restaurantServiceApi + "/restaurants", restaurant);

            if (response.getStatusCode().is2xxSuccessful()) {
                rttr.addFlashAttribute(SERVER_MESSAGE, "식당 등록이 완료되었습니다.");
                mv.setViewName("redirect:/restaurants");

            } else {
                mv.addObject(SERVER_MESSAGE, "식당 등록 중, 문제가 발생하였습니다.");
                mv.setViewName("restaurants/new");
            }
        }

        return mv;
    }

    /**
     * 식당 목록 조회
     * @param mv
     *  ModelAndView 객체
     * @return ModelAndView
     *  식당 목록 모델과 JSP 페이지 반환
     */
    @RequestMapping(value = "/restaurants", method = RequestMethod.GET)
    public ModelAndView getRestaurants(ModelAndView mv) {
        logger.info("getRestaurants()");

        List<Restaurant> restaurantList = restaurantService.getRestaurants(restaurantServiceApi + "/restaurants").getBody();
        mv.addObject("restaurantList", restaurantList);
        mv.setViewName("restaurants/list");

        return mv;
    }

    /**
     * 식당 상세 조회
     * @param mv
     *  ModelAndView 객체
     * @param res_index
     *  식당 번호
     * @return ModelAndView
     *  식당 상세 모델과 JSP 페이지 반환
     */
    @RequestMapping(value = "/restaurants/{res_index}", method = RequestMethod.GET)
    public ModelAndView getRestaurant(ModelAndView mv, @PathVariable int res_index) {
        logger.info("getRestaurant()");

        ResponseEntity<Restaurant> response = restaurantService.getRestaurant(restaurantServiceApi + "/restaurants/" + res_index);
        mv.addObject("grade", new Grade()); // 평점 등록 폼
        mv.addObject("restaurant", response.getBody());
        mv.setViewName("restaurants/restaurant");

        return mv;
    }

    /**
     * 식당 수정 폼
     * @param res_index
     *  식당 번호
     * @param mv
     *  ModelAndView 객체
     * @return ModelAndView
     *  수정을 위한 식당 수정 상세 모델과 JSP 페이지 반환
     */
    @LoginCheck(type = LoginCheck.Type.TRUE)
    @RequestMapping(value = "/restaurants/{res_index}/edit", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getRestaurantEdit(@PathVariable("res_index") int res_index, ModelAndView mv) {
        logger.info("getRestaurantEdit()");

        ResponseEntity<Restaurant> response = restaurantService.getRestaurant(restaurantServiceApi + "/restaurants/" + res_index);
        mv.addObject("restaurant", response.getBody());
        mv.setViewName("restaurants/edit");

        return mv;
    }

    /**
     * 식당 수정
     * @param res_index
     *  식당 번호
     * @param restaurant
     *  뷰에서 전달된 식당 모델
     * @param result
     *  유효성 검증 결과(BindingResult) 객체
     * @param mv
     *  ModelAndView 객체
     * @param rttr
     *  RedirectAttributes 객체
     * @return ModelAndView
     *  유효성 검증 실패 - 실패 메세지와 JSP 페이지 반환
     *  식당 수정 성공 - 성공 메세지와 JSP 페이지 반환
     *  리소스 서버 응답 실패 - 실패 메세지와 JSP 페이지 반환
     */
    @LoginCheck(type = LoginCheck.Type.TRUE)
    @RequestMapping(value = "/restaurants/{res_index}/edit", method = RequestMethod.PUT)
    public ModelAndView putRestaurantEdit(@PathVariable("res_index") int res_index,
                                          @ModelAttribute("restaurant") @Valid Restaurant restaurant, BindingResult result, ModelAndView mv, RedirectAttributes rttr) {
        logger.info("putRestaurantEdit()");

        if (result.hasErrors()) {
            logger.info(result.toString());

            rttr.addFlashAttribute(SERVER_MESSAGE, "식당 수정 정보를 확인해주세요.");
            mv.setViewName("redirect:/restaurants/" + res_index + "/edit");

        } else {
            restaurant.setRes_image(getImaSrc(restaurant.getRes_content())); // 썸네일 이미지 세팅
            ResponseEntity<Restaurant> response = restaurantService.putEditRestaurant(restaurantServiceApi + "/restaurants/" + res_index, new HttpEntity<>(restaurant));

            if (response.getStatusCode().is2xxSuccessful()) {
                rttr.addFlashAttribute(SERVER_MESSAGE, "식당 수정이 완료되었습니다.");
                mv.setViewName("redirect:/restaurants");

            } else {
                mv.addObject(SERVER_MESSAGE, "식당 수정 중, 문제가 발생하였습니다.");
                mv.setViewName("restaurants/edit");
            }
        }

        return mv;
    }

    /**
     * 식당 삭제
     * @param res_index
     *  식당 번호
     * @return int
     *  식당 삭제 성공 - 1 반환
     *  식당 삭제 실패 - 0 반환
     */
    @LoginCheck(type = LoginCheck.Type.TRUE)
    @RequestMapping(value = "/restaurants/{res_index}", method = RequestMethod.DELETE)
    public int deleteRestaurant(@PathVariable("res_index") int res_index) {
        logger.info("deleteRestaurant()");

        Restaurant restaurant = Restaurant.builder().res_index(res_index).build();
        ResponseEntity<Restaurant> response = restaurantService.deleteRestaurant(restaurantServiceApi + "/restaurants/" + res_index, new HttpEntity<>(restaurant));

        if (response.getStatusCode().is2xxSuccessful()) {
            return 1;

        } else {
            return 0;
        }
    }

    /**
     * 평점 등록
     * @param res_index
     *  식당 번호
     * @param grade
     *  뷰에서 전달된 평점 모델
     * @param result
     *  유효성 검증 결과(BindingResult) 객체
     * @param request
     *  HttpServletRequest 객체
     * @param rttr
     *  RedirectAttributes 객체
     * @param mv
     *  ModelAndView 객체
     * @return ModelAndView
     *  평점 등록 성공 - 성공 메세지와 JSP 페이지 반환
     *  평점 등록 실패 - 실패 메세지와 JSP 페이지 반환
     */
    @LoginCheck(type = LoginCheck.Type.TRUE)
    @RequestMapping(value = "/restaurants/{res_index}/grades/new", method = RequestMethod.POST)
    public ModelAndView postGradeNew(@PathVariable("res_index") int res_index,
                                     @ModelAttribute("grade") @Valid Grade grade, BindingResult result, HttpServletRequest request, RedirectAttributes rttr, ModelAndView mv) {
        logger.info("postGradeNew()");

        if (result.hasErrors()) {
            logger.info(result.toString());

            mv.addObject(SERVER_MESSAGE, "평점 등록 정보를 확인해주세요.");
            mv.setViewName("restaurants/" + res_index);

        } else {
            grade.setUser_id(getUserSession(request).getUser_id()); // 로그인 계정의 ID 세팅
            ResponseEntity<Grade> response = restaurantService.postGradeNew(restaurantServiceApi + "/restaurants/" + res_index + "/grade", grade);

            if (response.getStatusCode().is2xxSuccessful()) {
                rttr.addFlashAttribute(SERVER_MESSAGE, "평점이 등록되었습니다.");
                mv.setViewName("redirect:/restaurants/" + res_index);

            } else {
                rttr.addFlashAttribute(SERVER_MESSAGE, "평점 등록 중, 문제가 발생하였습니다.");
                mv.setViewName("redirect:/restaurants/" + res_index);
            }
        }

        return mv;
    }
}