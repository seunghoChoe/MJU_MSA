/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 */
package com.springboot.demo.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demo.entity.Menu;
import com.springboot.demo.entity.Restaurant;
import com.springboot.demo.repo.MenuJpaRepo;
import com.springboot.demo.repo.RestaurantJpaRepo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

/**
 * @author Hwamoc Kim
 * @date 2019-12-13
 * @version 0.9
 * @description 
 * Restaurant(식당) Controller는 사용자(Client)의 해당 요청(Request) url에 따른 처리(Handling) 방법을 명시한다
 */
@Api(tags = { "1-1. Restaurant" })
@RequiredArgsConstructor
@Component
@RestController // 결과값을 JSON으로 출력합니다.
@RequestMapping(value = "/")
public class RestaurantController {
	private final RestaurantJpaRepo restaurantJpaRepo;
	private final MenuJpaRepo menuJpaRepo;

	/**
	 * 사용자(Client)의 요청으로부터 모든 식당 리스트를 조회한다 
	 * @return ResponseEntity<List<Restaurant>>
	 * 	조회 성공할 경우 - 200(OK)를 반환
	 * 	조회 실패할 경우 - 406(Not Acceptable)를 반환 
	 */
	@ApiOperation(value = "식당 조회", notes = "모든 식당을 조회한다")
	@GetMapping(value = "/restaurants")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody ResponseEntity<List<Restaurant>> findAllRestaurant() {
		List<Restaurant> restaurantList = restaurantJpaRepo.findAll();
		return new ResponseEntity<List<Restaurant>>(restaurantList, HttpStatus.OK);
	}

	/**
	 * 사용자(Client)의 요청으로부터 한 식당의 아이디를 받아와 해당 식당을 조회한다 
	 * @param res_index 
	 * 	restaurant의 아이디
	 * @return ResponseEntity<Restaurant>
	 * 	조회 성공할 경우 - 200(OK)를 반환
	 * 	조회 실패할 경우 - 406(Not Acceptable)를 반환 
	 */
	@ApiOperation(value = "식당 하나 조회", notes = "res_index로 식당을 조회한다")
	@GetMapping(value = "/restaurants/{res_index}")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody ResponseEntity<Restaurant> findRestaurantById(
			@ApiParam(value = "식당ID", required = true) @PathVariable int res_index) {

		Restaurant restaurant = restaurantJpaRepo.getOne(res_index);

		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
	}

	/**
	 * 사용자(Client)의 요청으로부터 한 식당을 입력한다 
	 * @return ResponseEntity<Restaurant>
	 * 	입력 성공할 경우 - 200(OK)를 반환
	 * 	입력 실패할 경우 - 406(Not Acceptable)를 반환 
	 */
	@ApiOperation(value = "식당 입력", notes = "식당을 입력한다.")
	@PostMapping(value = "/restaurants")
	public @ResponseBody ResponseEntity<Restaurant> save(@Valid @RequestBody Restaurant restaurant)
			throws UnsupportedEncodingException {
		Restaurant newRestaurant = restaurantJpaRepo.save(restaurant);
		for (Menu m : newRestaurant.getRes_menues()) {
			m.setRestaurant(newRestaurant);
		}
		menuJpaRepo.saveAll(restaurant.getRes_menues());

		return new ResponseEntity<Restaurant>(newRestaurant, HttpStatus.OK);
	}

	/**
	 * 사용자(Client)의 요청으로부터 한 식당의 아이디를 받아와 해당 식당을 수정한다  
	 * @param res_index 
	 * 	restaurant의 아이디
	 * @return ResponseEntity<Restaurant>
	 * 	수정 성공할 경우 - 200(OK)를 반환
	 *  수정 실패할 경우 - 406(Not Acceptable)를 반환 
	 */
	@ApiOperation(value = "식당 수정", notes = "식당 정보를 수정한다")
	@PutMapping(value = "/restaurants/{res_index}")
	@ResponseBody
	public ResponseEntity<Restaurant> modify(@ApiParam(value = "식당 아이디", required = true) @PathVariable int res_index,
			@RequestBody Restaurant restaurant) throws UnsupportedEncodingException {

		Restaurant modifiedRestaurant = restaurantJpaRepo.getOne(res_index);
		modifiedRestaurant.setRes_name(restaurant.getRes_name());
		modifiedRestaurant.setRes_category(restaurant.getRes_category());
		modifiedRestaurant.setRes_grade(restaurant.getRes_grade());
		modifiedRestaurant.setRes_expected_minutes(restaurant.getRes_expected_minutes());
		modifiedRestaurant.setRes_menues(restaurant.getRes_menues());
		modifiedRestaurant.setRes_content(restaurant.getRes_content());
		modifiedRestaurant.setRes_image(restaurant.getRes_image());
		restaurantJpaRepo.save(modifiedRestaurant);

		return new ResponseEntity<Restaurant>(modifiedRestaurant, HttpStatus.OK);
	}

	/**
	 * 사용자(Client)의 요청으로부터 한 식당의 아이디를 받아와 해당 식당을 삭제한다 
	 * @param res_index 
	 * 	restaurant의 아이디
	 * @return ResponseEntity<Void>
	 * 	삭제 성공할 경우 - 200(OK)를 반환
	 * 	삭제 실패할 경우 - 406(Not Acceptable)를 반환 
	 */
	@ApiOperation(value = "식당 삭제", notes = "res_index로 식당 정보를 삭제한다")
	@DeleteMapping(value = "/restaurants/{res_index}")
	public @ResponseBody ResponseEntity<Void> delete(
			@ApiParam(value = "식당 아이디", required = true) @PathVariable int res_index) {
		restaurantJpaRepo.deleteById(res_index);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}