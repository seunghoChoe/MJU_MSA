package com.springboot.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.demo.entity.Grade;
import com.springboot.demo.entity.Menu;;

public interface MenuJpaRepo extends JpaRepository<Menu, Integer> {

}