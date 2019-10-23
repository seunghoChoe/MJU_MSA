package com.example.demo.user.service;

import java.io.PrintWriter;
import java.util.List;

import javax.inject.Inject;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.model.UserVO;

@Service("com.example.demo.user.service.UserService")
public class UserServiceImpl implements UserService{

	@Resource(name = "com.example.demo.user.mapper.UserMapper")
	UserMapper mUserMapper;
	@Inject
	UserDAO mUserDAO;
	
	// 로그인
	@Override
	public UserVO login(UserVO member) throws Exception {


		if(mUserDAO.check_id(member.getUser_id()) == 0) {
			return null;
		} else {
			String pw = member.getUser_password();
			member = mUserDAO.userLogin(member.getUser_id());
			if(!member.getUser_password().equals(pw)) {
				return null;
			}
		return member;
		}
	}
	
	@Override
	public void check_id(String id, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		out.println(mUserDAO.check_id(id));
		out.close();
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
	
	public List<UserVO> userListService() throws Exception {

		return mUserMapper.userList();
	}

	public UserVO boardDetailService(int bno) throws Exception {

		return mUserMapper.boardDetail(bno);
	}

	public int userSignUpService(UserVO user) throws Exception {

		return mUserMapper.userInsert(user);
	}

	public int boardUpdateService(UserVO board) throws Exception {

		return mUserMapper.boardUpdate(board);
	}

	public int boardDeleteService(int bno) throws Exception {

		return mUserMapper.boardDelete(bno);
	}


}
