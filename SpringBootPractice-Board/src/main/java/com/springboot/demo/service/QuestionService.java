package com.springboot.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.model.Question;
import com.springboot.demo.model.QuestionDAO;

@Service
public class QuestionService {
	@Autowired
	private QuestionDAO questionDAO;

	public void question(Question question) {
		questionDAO.question(question);
	}

	public List<Question> selectAllQuestion() {
		return questionDAO.selectAllQuestion();
	}

	public Question selectOneQuestion(Integer id) {
		return questionDAO.selectOneQuestion(id);
	}

	public void answer(Question answer) {
		questionDAO.answer(answer);
	}


}
