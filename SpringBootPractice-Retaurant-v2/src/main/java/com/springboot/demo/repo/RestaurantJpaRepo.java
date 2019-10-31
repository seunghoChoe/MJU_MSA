package com.springboot.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.demo.entity.Restaurant;

public interface RestaurantJpaRepo extends JpaRepository<Restaurant, Integer> {
//	Restaurant findByName(String restaurantName);
}