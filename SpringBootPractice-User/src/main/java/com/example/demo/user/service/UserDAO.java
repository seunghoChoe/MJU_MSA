/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 */
package com.example.demo.user.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.user.model.UserVO;

/**
 * 
 * @author Lee sojeong
 * @date 2019-12-13
 * @version 0.9
 * @description 
 * Controller-Service-DAO로 구성된 계층 관계에서 사용자와 관련된 DAO 클래스
 *
 */

@Repository
public class UserDAO {
	@Autowired
	SqlSessionTemplate sqlsession;
	private static final String MAPPER_NAME_SPACE = "mapper.UserMapper.";
	
	/**
	 * 아이디 중복 검사
	 * @param user_id
	 * @return user_id와 일치하는 ID의 갯수를 return한다.
	 * @throws Exception
	 */
	public int check_id(String user_id) throws Exception{
		return sqlsession.selectOne("check_id", user_id);
	}

	/**
	 * 로그인 검사
	 * @param user_id
	 * @return user_id와 일치하는 UserVO를 return
	 */
	public UserVO userLogin(String user_id) {
		return sqlsession.selectOne("userLogin", user_id);
	}
	
	/**
	 * 회원가입/회원추가
	 * @param user
	 */
	public void userInsert(UserVO user) {
		sqlsession.insert(MAPPER_NAME_SPACE + "userInsert", user);
	}

	/**
	 * 테스트용 method, DB의 모든 사용자 정보를 조회
	 * @return List UserVO를 return
	 */
	public List<UserVO> userList() {
		return sqlsession.selectList(MAPPER_NAME_SPACE + "userList");
	}
	
	/**
	 * 한명의 회원정보조회
	 * @param user_id
	 * @return user_id와 일치하는 userVO를 return
	 */
	public UserVO userSelect(String user_id) {
		return sqlsession.selectOne(MAPPER_NAME_SPACE + "selectUserById", user_id);
	}
	
	/**
	 * 회원정보수정
	 * @param user
	 * @return integer <br>
	 * update 성공 시 : 1 <br>
	 * update 실패 시 : 0
	 * 
	 */
	public int userModify(UserVO user) {
		System.out.println("DAO :" + sqlsession.update(MAPPER_NAME_SPACE + "userModify", user));
		return sqlsession.update(MAPPER_NAME_SPACE + "userModify", user);
	}

	/**
	 * 회원삭제
	 * @param user_id
	 @return integer <br>
	 * delete 성공 시 : 1 <br>
	 * delete 실패 시 : 0
	 */
	public int userDelete(String user_id) {
		//insert로 되어 있었음 gitpush 안돼있을 듯...
		return sqlsession.delete(MAPPER_NAME_SPACE + "userDelete", user_id);
	}
}
