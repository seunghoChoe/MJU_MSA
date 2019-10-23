package com.example.demo.user.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.user.model.UserVO;

@Repository
public class UserDAO {
	@Autowired
	SqlSession sqlsession = null;
	// 아이디 중복 검사
	public int check_id(String user_id) throws Exception{
		return sqlsession.selectOne("check_id", user_id);
	}

	//로그인 검사
	public UserVO userLogin(String user_id) {
		return sqlsession.selectOne("userLogin", user_id);
	}
}
