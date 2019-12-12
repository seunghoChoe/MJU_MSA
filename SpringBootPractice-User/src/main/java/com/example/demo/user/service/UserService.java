/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 */
package com.example.demo.user.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.example.demo.user.model.MyRestaurantVO;
import com.example.demo.user.model.UserVO;

/**
 * 
 * @author Lee sojeong
 * @date 2019-12-13
 * @version 0.9
 * @description 
 * Controller-Service-DAO로 구성된 계층 관계에서 사용자와 관련된 Service interface 클래스
 *
 */

public interface UserService {
	
	int check_id(String user_id) throws Exception;

	public void logout(HttpServletResponse response) throws Exception;

	public UserVO login(UserVO member) throws Exception;

	public List<UserVO> userListService() throws Exception;
	
	public void userSignUpService(UserVO user) throws Exception;
	
	public UserVO userSelectService(String user_id);
	
	public int userModifyService(UserVO member);
	
	public int userDeleteService(String user_id) throws Exception;
	
	public List<MyRestaurantVO> myResListService(String user_id);
	
	public void myresInsert(MyRestaurantVO myRes);
	

}