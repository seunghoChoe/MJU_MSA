package com.example.demo.user.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.user.model.UserVO;
import com.example.demo.user.service.UserServiceImpl;



@RestController
public class UserCotroller {
	private static final Logger logger = LoggerFactory.getLogger(UserCotroller.class);

	@Resource(name = "com.example.demo.user.service.UserService")
	UserServiceImpl mUserService;

	// 로그인
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<UserVO> login(RequestEntity<UserVO> request)
			throws Exception {
		
		UserVO user = (UserVO)request.getBody();
		UserVO member = mUserService.login(user);

		if(member!=null) {
			ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(HttpStatus.OK);
		
		return reponseEntity;}
		 else return null;
		
	}
	
	//로그아웃 - 웹과 테스트필요..
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout(HttpSession session) throws Exception {
		session.invalidate();
//		
	}
	
	//회원가입
	@RequestMapping(value="/user", method=RequestMethod.POST)
	private ResponseEntity<UserVO> userSignUpProc(RequestEntity<UserVO> request) throws Exception {
		
		UserVO user = (UserVO)request.getBody();
		System.out.println(user.getUser_id());
		System.out.println(user.getUser_password());
		System.out.println(user.getUser_email());
		System.out.println(user.getUser_name());
		
		mUserService.userSignUpService(user);
		ResponseEntity<UserVO> reponseEntity = new ResponseEntity<UserVO>(HttpStatus.OK);
		
		return reponseEntity;
	}
	
	//회원목록
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public ResponseEntity<List<UserVO>> selectAllUser() throws Exception {
		logger.info("selectAllUser()");
		List<UserVO> userList = mUserService.userListService();
		ResponseEntity<List<UserVO>> reponseEntity = new ResponseEntity<List<UserVO>>(userList, HttpStatus.OK);
		return reponseEntity;
	}
	
/**
 * 
 * Do not Delete
 * For Studying code
 * 
 * @throws Exception 
 *
 */
}
