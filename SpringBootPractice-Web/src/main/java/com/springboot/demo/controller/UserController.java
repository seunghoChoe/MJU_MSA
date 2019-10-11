package com.springboot.demo.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.demo.model.User;
@RestController
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(WebController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value="/user/userList", method=RequestMethod.GET)
	public ModelAndView userList(ModelAndView modelAndView) {
		logger.info("userList()");
		String url = "http://localhost:8892/user-service/users";
		
		User[] users = restTemplate.getForObject(url, User[].class);
		List<User> userList = Arrays.asList(users);
		
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
		String url = "http://localhost:8892/user-service/user";
		
		ResponseEntity<User> response = restTemplate.postForEntity(url, user, User.class);
		User newUser = response.getBody();
		logger.info("responseUser : {}", newUser);
		
		modelAndView.addObject("message", "회원 가입 완료" );
		modelAndView.setViewName("index");
		return modelAndView;
	}
}
