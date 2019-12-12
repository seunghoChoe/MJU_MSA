/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 */
package com.springboot.demo.service;

/**
 * @author KimNawoo
 * @date 2019-12-13
 * @version 0.9
 * @description
 *  Controller-Service-DAO로 구성된 계층 관계에서 Q&A게시판과 관련된 DAO 클래스
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.model.Question;
import com.springboot.demo.model.QuestionDAO;

@Service
public class QuestionService {
	@Autowired
	private QuestionDAO questionDAO;

	/**
	 * 문의글 작성하기
	 * @param question(Question 타입의 객체)
	 */
	public void question(Question question) {
		questionDAO.question(question);
	}

	/**
	 * 문의글 목록 불러오기
	 * @return
	 */
	public List<Question> selectAllQuestion() {
		return questionDAO.selectAllQuestion();
	}

	/**
	 * 해당 문의글 불러오가(자세히보기)
	 * @param id
	 * @return
	 */
	public Question selectOneQuestion(Integer id) {
		return questionDAO.selectOneQuestion(id);
	}

	/**
	 * 해당 문의글 답변 작성하기
	 * @param answer(Question 타입의 객체)
	 */
	public void answer(Question answer) {
		questionDAO.answer(answer);
	}

}
