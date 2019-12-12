/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 */
package com.springboot.demo.model;

/**
 * @author KimNawoo
 * @date 2019-12-13
 * @version 0.9
 * @description
 *  Controller-Service-DAO로 구성된 계층 관계에서 Q&A게시판과 관련된 DAO 클래스
 */
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	private static final String MAPPER_NAME_SPACE = "mapper.QuestionMapper.";

	/**
	 * 문의글 작성하기
	 * @param question
	 */
	public void question(Question question) {
		sqlSessionTemplate.insert(MAPPER_NAME_SPACE + "question", question);
	}

	/**
	 * 문의글 목록 불러오기
	 * @return
	 */
	public List<Question> selectAllQuestion() {
		return sqlSessionTemplate.selectList(MAPPER_NAME_SPACE + "selectAllQuestion");
	}

	/**
	 * 해당 문의글 불러오가(자세히보기)
	 * @param id
	 * @return
	 */
	public Question selectOneQuestion(Integer id) {
		return sqlSessionTemplate.selectOne(MAPPER_NAME_SPACE + "selectOneQuestion", id);
	}

	/**
	 * 해당 문의글 답변 작성하기
	 * @param answer
	 */
	public void answer(Question answer) {
		sqlSessionTemplate.update(MAPPER_NAME_SPACE + "answer", answer);
	}

}
