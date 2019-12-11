/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 */
package com.example.demo.user.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.user.model.MyRestaurantVO;
import com.example.demo.user.model.UserVO;

/**
 * 
 * @author Lee sojeong
 * @date 2019-12-13
 * @version 0.9
 * @description 
 * Controller-Service-DAO로 구성된 계층 관계에서 사용자 식당과 관련된 DAO 클래스
 *
 */

@Repository
public class ResDAO {
	@Autowired
	SqlSessionTemplate sqlsession;
	private static final String MAPPER_NAME_SPACE = "mapper.UserMapper.";
	
	/**
	 * 내 식당목록 조회
	 * @param user_id
	 * @return user_id와 일치하는 List MyRestaurantVO return 
	 */
	public List<MyRestaurantVO> myResList(String user_id) {
		return sqlsession.selectList(MAPPER_NAME_SPACE + "selectResById", user_id);
	}

	/**
	 * 내 식당 추가
	 * @param myRes
	 */
	public void myResInsert(MyRestaurantVO myRes) {
		sqlsession.insert(MAPPER_NAME_SPACE + "myresInsert", myRes);
	}

}
