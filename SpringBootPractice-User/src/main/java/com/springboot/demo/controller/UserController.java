package com.springboot.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demo.model.User;
import com.springboot.demo.service.UserService;

@RestController
// @Controller + @Responsebody 
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public ResponseEntity<List<User>> selectAllUser() {
		logger.info("selectAllUser()");
		List<User> userList = userService.selectAllUser();
		ResponseEntity<List<User>> reponseEntity = new ResponseEntity<List<User>>(userList, HttpStatus.OK);
		return reponseEntity;
	}
	
	@RequestMapping(value="/user", method=RequestMethod.POST)
	public ResponseEntity<User> userJoin(RequestEntity<User> request) {
		logger.info("userJoin()");
		User newUser = request.getBody();
		logger.info("newUser : {}", newUser);
		logger.info("HttpMethodType : " + request.getMethod());
		
		userService.createUser(newUser);
		ResponseEntity<User> response = new ResponseEntity<User>(newUser, HttpStatus.OK);
		return response;
	}
}
