package com.example.demo.user.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.user.model.MyRestaurantVO;
import com.example.demo.user.model.UserVO;

@Repository
public class ResDAO {
	@Autowired
	SqlSessionTemplate sqlsession;
	private static final String MAPPER_NAME_SPACE = "mapper.UserMapper.";
	
	public List<MyRestaurantVO> myResList(String user_id) {
		return sqlsession.selectList(MAPPER_NAME_SPACE + "selectResById", user_id);
	}

	public void myResInsert(MyRestaurantVO myRes) {
		sqlsession.insert(MAPPER_NAME_SPACE + "myresInsert", myRes);
	}

}
