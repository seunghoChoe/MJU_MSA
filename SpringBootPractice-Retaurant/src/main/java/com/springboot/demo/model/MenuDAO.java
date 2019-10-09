package com.springboot.demo.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class MenuDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	private static final String MAPPER_NAME_SPACE = "mapper.MenuMapper.";


	public List<Menu> selectAllMenu() {
		return sqlSessionTemplate.selectList(MAPPER_NAME_SPACE + "selectAllMenu");
	}
}
