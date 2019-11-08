package com.springboot.demo.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demo.entity.Menu;
import com.springboot.demo.entity.Restaurant;
import com.springboot.demo.repo.MenuJpaRepo;
import com.springboot.demo.repo.RestaurantJpaRepo;
import com.springboot.demo.service.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = { "1-1. Restaurant" })
@RequiredArgsConstructor
@RestController // 결과값을 JSON으로 출력합니다.
@RequestMapping(value = "/")
public class RestaurantController {
	private final RestaurantJpaRepo restaurantJpaRepo;
	private final MenuJpaRepo menuJpaRepo;
	private final ResponseService responseService; // 결과를 처리할 Service

	@ApiOperation(value = "식당 조회", notes = "모든 식당을 조회한다")
	@GetMapping(value = "/restaurants")
	public ResponseEntity<List<Restaurant>> findAllRestaurant() {
		// 결과데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다.
//		ListResult<Restaurant> sampleList = responseService.getListResult(restaurantJpaRepo.findAll());
		
		List<Restaurant> restaurantList = restaurantJpaRepo.findAll();
		return new ResponseEntity<List<Restaurant>>(restaurantList, HttpStatus.OK);
//		Gson gson = new Gson(); // Gson 사용
//	    return gson.toJson(sampleList); // json으로 변환 후, 리턴
	}
	

	@ApiOperation(value = "식당 하나 조회", notes = "res_index로 식당을 조회한다")
	@GetMapping(value = "/restaurant/{res_index}")
	public ResponseEntity<Restaurant> findUserById(
			@ApiParam(value = "식당ID", required = true) @PathVariable int res_index) {
		// 결과데이터가 단일건인경우 getBasicResult를 이용해서 결과를 출력한다.
//		SingleResult<Restaurant> singleResult = responseService.getSingleResult(restaurantJpaRepo.findById(res_index).orElse(null));
//		Gson gson = new Gson(); // Gson 사용
//	    return gson.toJson(singleResult); // json으로 변환 후, 리턴
		
		Restaurant restaurant = restaurantJpaRepo.getOne(res_index);
		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
	}
	
	@ApiOperation(value = "식당 입력", notes = "식당을 입력한다.")
	@PostMapping(value = "/restaurant")
	public ResponseEntity<Restaurant> save(
//			@ApiParam(value = "식당 이름", required = true) @RequestParam String res_name,
//			@ApiParam(value = "식당 종류", required = true) @RequestParam String res_category,
//			@ApiParam(value = "식당 별점", required = false) @RequestParam Integer res_grade,
//			@ApiParam(value = "예상 대기 시간", required = true) @RequestParam Integer res_expected_minutes
			@RequestBody Restaurant restaurant
			) throws UnsupportedEncodingException {
//		Restaurant restaurant = Restaurant.builder()
//				.res_name(new String(res_name.getBytes("8859_1"), "UTF-8"))
//				.res_category(new String(res_category.getBytes("8859_1"), "UTF-8"))
//				.res_grade(res_grade)
//				.res_expected_minutes(res_expected_minutes)
//				.build();
		
//		return responseService.getSingleResult(restaurantJpaRepo.save(restaurant));
		Restaurant newRestaurant = restaurantJpaRepo.save(restaurant);
		int res_index = newRestaurant.getRes_index();
		for (Menu m: newRestaurant.getRes_menues()) {
			m.setRestaurant(newRestaurant);
		}
		menuJpaRepo.saveAll(restaurant.getRes_menues());
		
		return new ResponseEntity<Restaurant>(newRestaurant, HttpStatus.OK);
	}
	
	@ApiOperation(value = "식당 수정", notes = "식당 정보를 수정한다")
    @PutMapping(value = "/restaurant/{res_index}")
    public ResponseEntity<Restaurant> modify(@ApiParam(value = "식당 아이디", required = true) @PathVariable int res_index, 
//    		@ApiParam(value = "식당 이름", required = true) @RequestParam String res_name,
//			@ApiParam(value = "식당 종류", required = true) @RequestParam String res_category,
//			@ApiParam(value = "식당 별점", required = false) @RequestParam Integer res_grade,
//			@ApiParam(value = "예상 대기 시간", required = true) @RequestParam Integer res_expected_minutes
    		@RequestBody Restaurant restaurant) throws UnsupportedEncodingException {
//		Restaurant restaurant = Restaurant.builder()
//				.res_name(new String(res_name.getBytes("8859_1"), "UTF-8"))
//				.res_category(new String(res_category.getBytes("8859_1"), "UTF-8"))
//				.res_grade(res_grade)
//				.res_expected_minutes(res_expected_minutes)
//				.build();
		
//		return responseService.getSingleResult(restaurantJpaRepo.save(restaurant));
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
	
	@ApiOperation(value = "식당 삭제", notes = "res_index로 식당 정보를 삭제한다")
	@DeleteMapping(value = "/restaurant/{res_index}")
	public void delete(@ApiParam(value = "식당 아이디", required = true) @PathVariable int res_index) {
		restaurantJpaRepo.deleteById(res_index);
		// 성공 결과 정보만 필요한경우 getSuccessResult()를 이용하여 결과를 출력한다.
//		return responseService.getSuccessResult();
	}
}