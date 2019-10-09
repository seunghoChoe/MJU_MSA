package com.springboot.demo.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository
public class UserDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	private static final String MAPPER_NAME_SPACE = "mapper.UserMapper.";


	public List<User> selectAllUser() {
		return sqlSessionTemplate.selectList(MAPPER_NAME_SPACE + "selectAllUser");
	}


	public void createUser(User user) {
		sqlSessionTemplate.selectList(MAPPER_NAME_SPACE + "createUser", user);
	}
	
}
