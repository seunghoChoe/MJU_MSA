package com.example.demo.user.service;

import javax.servlet.http.HttpServletResponse;

import com.example.demo.user.model.UserVO;


public interface UserService {

	public UserVO login(UserVO member) throws Exception;

	public int check_id(String user_id) throws Exception;

}