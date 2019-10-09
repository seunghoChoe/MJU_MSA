package com.springboot.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.model.User;
import com.springboot.demo.model.UserDAO;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	public List<User> selectAllUser() {
		return userDAO.selectAllUser();
	}

	public void createUser(User user) {
		userDAO.createUser(user);
	}

}
