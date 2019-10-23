package com.example.demo.user.service;

import javax.servlet.http.HttpServletResponse;

import com.example.demo.user.model.UserVO;


public interface UserService {
	
	public void check_id(String id, HttpServletResponse response) throws Exception;


	public void logout(HttpServletResponse response) throws Exception;

	public UserVO login(UserVO member) throws Exception;

}