package com.springboot.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.demo.model.Menu;
import com.springboot.demo.model.Post;
import com.springboot.demo.model.Restaurant;
import com.springboot.demo.model.User;

@RestController
public class WebController {
	private static final Logger logger = LoggerFactory.getLogger(WebController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public ModelAndView index(ModelAndView modelAndView) {
		logger.info("index()");
//		ResponseEntity<Restaurant[]> responseEntity = restTemplate.getForEntity(url, Restaurant[].class);
//		List<Object> list = Arrays.asList(responseEntity.getBody());
		
		modelAndView.setViewName("index");
		return modelAndView;
	}
	
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
	
	@RequestMapping(value="/user/userList", method=RequestMethod.GET)
	public ModelAndView userList(ModelAndView modelAndView) {
		logger.info("userList()");
		String url = "http://localhost:8889/user/userList";
		ResponseEntity<List<User>> response =
				restTemplate.exchange(url,HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});
		List<User> userList = response.getBody();
		
		modelAndView.addObject("userList", userList);
		modelAndView.setViewName("user/userList");
		return modelAndView;
	}
	
	@RequestMapping(value="/user/userJoin", method=RequestMethod.GET)
	public ModelAndView userJoinForm(ModelAndView modelAndView) {
		logger.info("userJoinForm()");
		
		modelAndView.setViewName("user/userJoin");
		return modelAndView;
	}
	
	@RequestMapping(value="/user/userJoin", method=RequestMethod.POST)
	public ModelAndView userJoin(ModelAndView modelAndView, @ModelAttribute User user) {
		logger.info("userJoin()");
		String url = "http://localhost:8889/user/userJoin";
		restTemplate.postForObject(url, user, String.class);
		modelAndView.addObject("message", "회원 가입 완료" );
		modelAndView.setViewName("index");
		return modelAndView;
	}
	
	@RequestMapping(value="/board/postList", method=RequestMethod.GET)
	public ModelAndView postList(ModelAndView modelAndView) {
		logger.info("postList()");
		String url = "http://localhost:8890/board/postList";
		ResponseEntity<List<Post>> response =
				restTemplate.exchange(url,HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {});
		List<Post> postList = response.getBody();
		System.out.println(postList.get(0).getPost_content());
		modelAndView.addObject("postList", postList);
		modelAndView.setViewName("board/postList");
		return modelAndView;
	}
}
