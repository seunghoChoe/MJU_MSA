package com.springboot.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.model.Menu;
import com.springboot.demo.model.MenuDAO;
import com.springboot.demo.model.Restaurant;
import com.springboot.demo.model.RestaurantDAO;

@Service
public class RestaurantService {
	@Autowired
	private RestaurantDAO restaurantDAO;
	@Autowired
	private MenuDAO menuDAO;

	public List<Restaurant> selectAllRestaurant() {
		return restaurantDAO.selectAllRestaurant();
	}

	public List<Menu> selectAllMenu() {
		return menuDAO.selectAllMenu();
	}
	
	
}
