package com.springboot.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demo.model.Menu;
import com.springboot.demo.model.Restaurant;
import com.springboot.demo.service.RestaurantService;

@RestController
@RequestMapping(value="/restaurant")
public class Controller {
	
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

	@Autowired
	private RestaurantService restaurantService;
	
	@RequestMapping(value="/restaurant", method=RequestMethod.GET)
	public List<Restaurant> selectAllRestaurant() {
		logger.info("selectAllRestaurant()");
		return restaurantService.selectAllRestaurant();
	}
	
	@RequestMapping(value="/menu", method=RequestMethod.GET)
	public List<Menu> selectAllMenu() {
		logger.info("selectAllMenu()");
		return restaurantService.selectAllMenu();
	}
}
