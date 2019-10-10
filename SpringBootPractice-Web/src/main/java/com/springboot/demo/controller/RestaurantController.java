package com.springboot.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.demo.model.Menu;
import com.springboot.demo.model.Restaurant;
@RestController
public class RestaurantController {
	private static final Logger logger = LoggerFactory.getLogger(WebController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value="/restaurant/restaurantList", method=RequestMethod.GET)
	public ModelAndView restaurantList(ModelAndView modelAndView) {
		logger.info("restaurantList()");
		String url = "http://localhost:8888/restaurant/restaurant";
		ResponseEntity<List<Restaurant>> response =
				restTemplate.exchange(url,HttpMethod.GET, null, new ParameterizedTypeReference<List<Restaurant>>() {});
		List<Restaurant> restaurantList = response.getBody();
		
		modelAndView.addObject("restaurantList", restaurantList);
		modelAndView.setViewName("restaurant/restaurantList");
		return modelAndView;
	}
	
	@RequestMapping(value="/restaurant/menuList", method=RequestMethod.GET)
	public ModelAndView menuList(ModelAndView modelAndView) {
		logger.info("menuList()");
		String url = "http://localhost:8888/restaurant/menu";
		ResponseEntity<List<Menu>> response =
				restTemplate.exchange(url,HttpMethod.GET, null, new ParameterizedTypeReference<List<Menu>>() {});
		List<Menu> menuList = response.getBody();
		
		modelAndView.addObject("menuList", menuList);
		modelAndView.setViewName("restaurant/menuList");
		return modelAndView;
	}
}
