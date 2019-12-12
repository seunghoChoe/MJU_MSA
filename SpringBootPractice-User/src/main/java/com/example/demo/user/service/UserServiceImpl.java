/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 */
package com.example.demo.user.service;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.user.model.MyRestaurantVO;
import com.example.demo.user.model.UserVO;

/**
 * 
 * @author Lee sojeong
 * @date 2019-12-13
 * @version 0.9
 * @description 
 * Controller-Service-DAO로 구성된 계층 관계에서 사용자와 관련된 Service를 구현하는 클래스
 *
 */

@Service("com.example.demo.user.service.UserService")
public class UserServiceImpl implements UserService{

	@Autowired
	UserDAO mUserDAO;
	@Autowired
	ResDAO mResDAO;
	

	@Override
	public UserVO login(UserVO member) throws Exception {
		// 가입되지 않은 아이디일 경우
		if(mUserDAO.check_id(member.getUser_id()) == 0) {
			return null;
			
		// 가입된 아이디인 경우
		} else {
			String pw = member.getUser_password();
			member = mUserDAO.userLogin(member.getUser_id());
			if(!member.getUser_password().equals(pw)) {
				return null;
			}
		return member;
		}
	}
	
	/**
	 * 아이디 중복 검사<br>
	 * 로그인, 회원가입, 회원삭제 시  사용
	 */
	@Override
	   public int check_id(String user_id) throws Exception {
	      return mUserDAO.check_id(user_id);
	   }
	
	@Override
	public void logout(HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("location.href=document.referrer;");
		out.println("</script>");
		out.close();
		
	}
	
	/**
	 * test용 method<br>
	 * 모든 사용자의 정보 리스트를 return
	 */
	public List<UserVO> userListService() throws Exception {

		return mUserDAO.userList();
	}


	public void userSignUpService(UserVO user) throws Exception {

		mUserDAO.userInsert(user);
	}

	/**
	 * selectUserById<br>
	 * userId와 일치하는 UserVO return
	 */
	public UserVO userSelectService(String user_id) {

		return mUserDAO.userSelect(user_id);
	}

	public int userModifyService(UserVO member) {
		return mUserDAO.userModify(member);
	}

	public int userDeleteService(String user_id) throws Exception {
		// 가입되지 않은 아이디인 경우
		if(mUserDAO.check_id(user_id) == 0) {
			return 0;
			
		// 가입된 아이디인 경우
		} else {
			return mUserDAO.userDelete(user_id);	
		}
			
	}

	/**
	 * user_id를 가진 List MyRestaurantVO를 return
	 */
	public List<MyRestaurantVO> myResListService(String user_id) {
		return mResDAO.myResList(user_id);
	}

	public void myresInsert(MyRestaurantVO myRes) {
		mResDAO.myResInsert(myRes);
		
	}

	
	
}
