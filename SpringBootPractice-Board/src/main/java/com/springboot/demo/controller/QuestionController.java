package com.springboot.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demo.model.Post;
import com.springboot.demo.model.Question;
import com.springboot.demo.service.QuestionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(tags = { "2. Questionservice" })
@RestController
public class QuestionController {
	
	    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
	  
		@Autowired
		private QuestionService questionService;
		
		//문의 페이지
		@ApiOperation(value = "문의하는 페이지", notes = "문의하는 페이지")
		@RequestMapping(value="/question", method=RequestMethod.POST)
		public ResponseEntity<Question> question(@RequestBody Question question) {
			logger.info("question()");
			questionService.question(question);
			ResponseEntity<Question> response = new ResponseEntity<Question>(question,HttpStatus.OK);
			return response;
		}
		//문의사항 목록
		@ApiOperation(value = "문의사항 조회", notes = "모든 문의사항을 조회한다")
		@RequestMapping(value="/questions", method=RequestMethod.GET)
		public ResponseEntity<List<Question>> selectAllQuestion() {
			logger.info("selectAllQustion()");
			List<Question> questionList = questionService.selectAllQuestion();
			ResponseEntity<List<Question>> responseEntity = new ResponseEntity<List<Question>> (questionList,HttpStatus.OK);
			return responseEntity;
		}
		//문의사항 자세히 보기
		@ApiOperation(value = "문의사항 상세조회", notes = "선택한 문의사항을 조회한다")
		@RequestMapping(value="/questions/{qna_id}", method=RequestMethod.GET)
		public ResponseEntity<Question> selectOneQuestion(@PathVariable("qna_id") Integer id) {
			logger.info("selectOneQuestion()");
			
			Question questionOne = questionService.selectOneQuestion(id);
			ResponseEntity<Question> responseEntity = new ResponseEntity<Question> (questionOne,HttpStatus.OK);
			return responseEntity;
		}
		//답변 페이지
		@ApiOperation(value = "답변페이지", notes = "답변하는페이지")
		@RequestMapping(value="/questions/{qna_id}", method=RequestMethod.PUT)
		public ResponseEntity<Question> answer(@PathVariable("qna_id") Integer id,@RequestBody Question answer) {
			logger.info("answer()");
			
			answer.setQna_id(id);
			questionService.answer(answer);
			
			ResponseEntity<Question> response = new ResponseEntity<Question> (HttpStatus.OK);
			return response;
		}
}
