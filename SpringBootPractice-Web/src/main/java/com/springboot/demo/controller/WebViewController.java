package com.springboot.demo.controller;

import com.springboot.demo.model.Restaurant;
import com.springboot.demo.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static com.springboot.demo.global.Constants.createApiUri;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Jaeha Son
 * @date 2019-12-13
 * @version 0.9
 * @description
 * 메인 뷰와 관련된 Controller 클래스
 */
@RestController
@RequiredArgsConstructor
public class WebViewController {
    private static final Logger logger = LoggerFactory.getLogger(WebViewController.class);
    private final String restaurantServiceApi = createApiUri("restaurant-service");
    private final RestaurantService restaurantService;

    /**
     * 메인 화면
     * @param mv
     *  ModelAndView 객체
     * @return ModelAndView
     *  식당 목록 모델과 JSP 페이지 반환
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(ModelAndView mv) {
        logger.info("home()");

        // 식당 6개가 필요함!
        mv.addObject("restaurantList", restaurantService.getSixRestaurants(restaurantServiceApi + "/restaurants"));
        mv.setViewName("home");

        return mv;
    }

    /**
     * 파비콘
     */
    @RequestMapping("/favicon.ico")
    public void favicon() {}
}