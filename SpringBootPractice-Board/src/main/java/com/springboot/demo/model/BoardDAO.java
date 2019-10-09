package com.springboot.demo.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	private static final String MAPPER_NAME_SPACE = "mapper.BoardMapper.";


	public List<Post> selectAllPost() {
		return sqlSessionTemplate.selectList(MAPPER_NAME_SPACE + "selectAllPost");
	}


}
