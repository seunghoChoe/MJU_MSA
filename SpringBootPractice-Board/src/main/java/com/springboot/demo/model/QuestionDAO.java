package com.springboot.demo.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	private static final String MAPPER_NAME_SPACE = "mapper.QuestionMapper.";
	
	public void question(Question question) {
		sqlSessionTemplate.insert(MAPPER_NAME_SPACE + "question", question);
	}

	public List<Question> selectAllQuestion() {
		return sqlSessionTemplate.selectList(MAPPER_NAME_SPACE + "selectAllQuestion");
	}

	public Question selectOneQuestion(Integer id) {
		return sqlSessionTemplate.selectOne(MAPPER_NAME_SPACE + "selectOneQuestion",id);
	}

	public void answer(Question answer) {
		sqlSessionTemplate.update(MAPPER_NAME_SPACE + "answer",answer);
	}

}
