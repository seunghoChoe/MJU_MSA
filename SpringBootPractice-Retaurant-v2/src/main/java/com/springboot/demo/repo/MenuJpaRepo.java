/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 */
package com.springboot.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.demo.entity.Menu;;

/**
 * @author Hwamoc Kim
 * @date 2019-12-13
 * @version 0.9
 * @description 
 *  * JpaRepository 인터페이스를 상속하는 MenuJpaRepo 인터페이스를 정의해  
 * 이 인터페이스를 통해 RDBMS의 Repository와 데이터를 주고받을 수 있는 빈을 자동적으로 등록합니다 
 */

@Repository
public interface MenuJpaRepo extends JpaRepository<Menu, Integer> {
}