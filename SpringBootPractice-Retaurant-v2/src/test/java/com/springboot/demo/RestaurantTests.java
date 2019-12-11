/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 */
package com.springboot.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.springboot.demo.controller.RestaurantController;
import com.springboot.demo.entity.Menu;
import com.springboot.demo.entity.Restaurant;
import com.springboot.demo.repo.MenuJpaRepo;
import com.springboot.demo.repo.RestaurantJpaRepo;

/**
 * @author Hwamoc Kim
 * @date 2019-12-13
 * @version 0.9
 * @description 
 * 	Restaurant Controller를 테스트합니다 
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantTests {

    private MockMvc mockMvc;
	
    @Autowired
	private RestaurantController restaurantController;

	@MockBean
	private RestaurantJpaRepo restaurantJpaRepo;
	@MockBean
	private MenuJpaRepo menuJpaRepo;
	
	
	@Before
    public void init() {
        this.restaurantController = new RestaurantController(restaurantJpaRepo, menuJpaRepo);
    }
	@Before
    public void setUpMockMvc() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new RestaurantController(restaurantJpaRepo, menuJpaRepo))
        		.alwaysExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8")).build();
    }

    private static MediaType JSON_CONTENT_TYPE = new MediaType(MediaType.APPLICATION_JSON_UTF8.getType(), MediaType.APPLICATION_JSON_UTF8.getSubtype(), Charset.forName("utf8"));

	@Test
	public void findAllRestaurant() throws Exception {
		// given
		// request Data
		// response data
		List<Restaurant> restaurantList = new ArrayList<Restaurant>();
		Restaurant restaurant = new Restaurant();
		restaurant.setRes_name("테스트 명지리본 맛집");
		restaurant.setRes_category("테스트 식당 카테고리");
		restaurantList.add(restaurant);
		this.restaurantJpaRepo.save(restaurant);

		given(restaurantJpaRepo.findAll()).willReturn(restaurantList);

		ResponseEntity<List<Restaurant>>  res_restaurantList = restaurantController.findAllRestaurant();
		for (Restaurant restaurantInfo : res_restaurantList.getBody()) {
			assertThat(restaurantInfo.getRes_name().equals("테스트 명지리본 맛집"));
			assertThat(restaurantInfo.getRes_category().equals("테스트 식당 카테고리"));
		}
		//actions
		this.mockMvc.perform(get("/restaurants"))
			.andExpect(status().isOk())
			.andDo(print())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].res_name").value("테스트 명지리본 맛집"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].res_category").value("테스트 식당 카테고리"));
	}
	
	@Test
	public void findRestaurantById() throws Exception {
		//given
		Restaurant restaurant = new Restaurant();
		restaurant.setRes_name("테스트 명지리본 맛집");
		restaurant.setRes_category("테스트 식당 카테고리");
		List<Menu> menues = new ArrayList<Menu>();
		Menu menu1 = new Menu(restaurant, 1, "테스트 메뉴1", 5000);
		Menu menu2 = new Menu(restaurant, 2, "테스트 메뉴2", 6000);
		menues.add(menu1);
		menues.add(menu2);
		restaurant.setRes_menues(menues);
		
		given(this.restaurantJpaRepo.getOne(0)).willReturn(restaurant);
		
		ResponseEntity<Restaurant> responseRestaurant = restaurantController.findRestaurantById(0); 
		assertThat(responseRestaurant.getBody().getRes_name().equals("테스트 명지리본 맛집"));
		assertThat(responseRestaurant.getBody().getRes_category().equals("테스트 식당 카테고리"));
	}
	
	@Test
	public void save() throws Exception {
		Restaurant restaurant = new Restaurant();
		restaurant.setRes_name("테스트 명지리본 맛집");
		restaurant.setRes_category("테스트 식당 카테고리");
		List<Menu> menues = new ArrayList<Menu>();
		Menu menu1 = new Menu(restaurant, 1, "테스트 메뉴1", 5000);
		Menu menu2 = new Menu(restaurant, 2, "테스트 메뉴2", 6000);
		menues.add(menu1);
		menues.add(menu2);
		restaurant.setRes_menues(menues);
		
		given(this.restaurantJpaRepo.save(restaurant)).willReturn(restaurant);
		ResponseEntity<Restaurant> created = this.restaurantController.save(restaurant);
		
		assertThat(created.getBody().getRes_name().equals("테스트 명지리본 맛집"));
		assertThat(created.getBody().getRes_category().equals("테스트 식당 카테고리"));
	}
	
	@Test
	public void modify() throws Exception {
		Restaurant restaurant = new Restaurant();
		restaurant.setRes_name("테스트 명지리본 맛집");
		restaurant.setRes_category("테스트 식당 카테고리");
		
		Restaurant updatedOne = new Restaurant();
		updatedOne.setRes_name("이름 업데이트");
		updatedOne.setRes_category("카테고리 업데이트");

		given(this.restaurantJpaRepo.getOne(0)).willReturn(restaurant);
		given(this.restaurantJpaRepo.save(updatedOne)).willReturn(updatedOne);
		
		ResponseEntity<Restaurant> created = this.restaurantController.modify(0, updatedOne);

		assertThat(created.getBody().getRes_name().equals("테스트 명지리본 맛집"));
		assertThat(created.getBody().getRes_category().equals("테스트 식당 카테고리"));
	}

	@Test
	public void delete() throws Exception {
		Restaurant restaurant = new Restaurant();
		restaurant.setRes_name("테스트 명지리본 맛집");
		restaurant.setRes_category("테스트 식당 카테고리");
		given(this.restaurantJpaRepo.save(restaurant)).willReturn(restaurant);
		
		ResponseEntity<Void> deleted = this.restaurantController.delete(restaurant.getRes_index());
		
		assertThat(deleted.getBody() == null);
	}
}