package com.example.demo.user.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.user.model.UserVO;

@Repository
public class UserDAO {
	@Autowired
	SqlSessionTemplate sqlsession;
	private static final String MAPPER_NAME_SPACE = "mapper.UserMapper.";
	
	// 아이디 중복 검사
	public int check_id(String user_id) throws Exception{
		return sqlsession.selectOne(MAPPER_NAME_SPACE + "check_id", user_id);
	}

	//로그인 검사
	public UserVO userLogin(String user_id) {
		return sqlsession.selectOne("userLogin", user_id);
	}
	
	//회원 가입
	public void userInsert(UserVO user) {
		sqlsession.insert(MAPPER_NAME_SPACE + "userInsert", user);
	}
	
	//회원 목록
	public List<UserVO> userList() {
		return sqlsession.selectList(MAPPER_NAME_SPACE + "userList");
	}
}
