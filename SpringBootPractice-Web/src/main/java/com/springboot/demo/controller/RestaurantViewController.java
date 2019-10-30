package com.springboot.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.springboot.demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.springboot.demo.model.Menu;
import com.springboot.demo.model.Restaurant;

import static com.springboot.demo.global.Constants.createBaseURI;

/**
 * @Class: 맛집 뷰 컨트롤러 클래스
 */
@RestController
public class RestaurantViewController {
    private static final Logger logger = LoggerFactory.getLogger(WebViewController.class);
    private String baseURI = createBaseURI("restaurant-service");

    @Autowired
    private RestTemplate restTemplate;

    /**
     * @Method: 맛집 목록 조회
     */
    @RequestMapping(value = "/restaurants", method = RequestMethod.GET)
    public ModelAndView getRestaurants(ModelAndView mv, Model model) {
        logger.info("getRestaurants()");

        String uri = baseURI + "/restaurants";
//        ResponseEntity<Restaurant[]> response = restTemplate.getForEntity(uri, Restaurant[].class);
//        System.out.println(response);
//        List<Restaurant> restaurantList = Arrays.asList(Objects.requireNonNull(response.getBody()));

        ResponseEntity<List<Restaurant>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Restaurant>>() {});
        System.out.println(response);
        List<Restaurant> restaurantList = response.getBody();

//		List<Restaurant> restaurantList = new ArrayList<>();
//		restaurantList.add(new Restaurant(1, "A식당", "한식", 1, 10));
//		restaurantList.add(new Restaurant(2, "B식당", "중식", 1, 10));
//		restaurantList.add(new Restaurant(3, "C식당", "양식", 1, 10));
//		restaurantList.add(new Restaurant(4, "D식당", "분식", 1, 10));
//		logger.info(restaurantList.get(1).getRes_name());
//		logger.info(restaurantList.get(1).getRes_category());
        mv.addObject("restaurantList", restaurantList);
        mv.setViewName("restaurants/list");
        return mv;
    }


//	/**
//	 * @Method:
//	 */
//	@RequestMapping(value="/restaurant/restaurantList", method=RequestMethod.GET)
//	public ModelAndView restaurantList(ModelAndView modelAndView) {
//		logger.info("restaurantList()");
//		String url = baseURI + "/restaurant";
//		ResponseEntity<List<Restaurant>> response = restTemplate.exchange(url,HttpMethod.GET, null, new ParameterizedTypeReference<List<Restaurant>>() {});
//		List<Restaurant> restaurantList = response.getBody();
//
//		modelAndView.addObject("restaurantList", restaurantList);
//		modelAndView.setViewName("restaurant/restaurantList");
//		return modelAndView;
//	}
//
//	/**
//	 * @Method:
//	 */
//	@RequestMapping(value="/restaurant/menuList", method=RequestMethod.GET)
//	public ModelAndView menuList(ModelAndView modelAndView) {
//		logger.info("menuList()");
//		String url = baseURI+ "/menu";
//		ResponseEntity<List<Menu>> response = restTemplate.exchange(url,HttpMethod.GET, null, new ParameterizedTypeReference<List<Menu>>() {});
//		List<Menu> menuList = response.getBody();
//
//		modelAndView.addObject("menuList", menuList);
//		modelAndView.setViewName("restaurant/menuList");
//		return modelAndView;
//	}
}
