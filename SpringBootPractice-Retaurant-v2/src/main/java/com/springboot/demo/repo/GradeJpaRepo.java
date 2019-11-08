package com.springboot.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.demo.entity.Grade;;

public interface GradeJpaRepo extends JpaRepository<Grade, Integer> {
//	Restaurant findByName(String restaurantName);
}