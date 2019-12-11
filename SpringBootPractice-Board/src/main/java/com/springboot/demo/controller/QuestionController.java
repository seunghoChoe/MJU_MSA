/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 */
package com.springboot.demo.controller;
/**
 * @author KimNawoo
 * @date 2019-12-13
 * @version 0.9
 * @description
 *  Controller-Service-DAO로 구성된 계층 관계에서 Q&A게시판과 관련된 Controller 클래스
 */
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
		
		/**
		 * Client로부터 문의글 정보를 입력받아 질문글 작성 성공 여부를 반환한다.
		 * 
		 * @param question(Question 타입의 객체)
		 * @return ResponseEntity<Question>
		 *  문의글 작성이 성공할 경우 - 200(OK)을 반환
		 */
		@ApiOperation(value = "문의사항 작성", notes = "문의사항을 작성한다")
		@RequestMapping(value="/question", method=RequestMethod.POST)
		public ResponseEntity<Question> question(@RequestBody Question question) {
			logger.info("question()");
			questionService.question(question);
			ResponseEntity<Question> response = new ResponseEntity<Question>(question,HttpStatus.OK);
			return response;
		}
		/**
		 * 모든 문의글 목록 불러오기 성공여부를 반환한다
		 * 
		 * @return ResponseEntity<List<Question>>
		 *  문의글 목록 불러오기를 성공할 경우 - 200(OK)을 반환 
		 */
		@ApiOperation(value = "문의사항 조회", notes = "모든 문의사항을 조회한다")
		@RequestMapping(value="/questions", method=RequestMethod.GET)
		public ResponseEntity<List<Question>> selectAllQuestion() {
			logger.info("selectAllQustion()");
			List<Question> questionList = questionService.selectAllQuestion();
			ResponseEntity<List<Question>> responseEntity = new ResponseEntity<List<Question>> (questionList,HttpStatus.OK);
			return responseEntity;
		}
		/**
		 * Client로부터 문의글 id를 받아 해당 게시글 불러오기 성공여부를 반환한다
		 * 
		 * @param id
		 * @return ResponseEntity<Question>
		 *  해당 문의글 불러오기를 성공한 경우 - 200(OK)을 반환
		 */
		@ApiOperation(value = "문의사항 상세조회", notes = "선택한 문의사항을 조회한다")
		@RequestMapping(value="/questions/{qna_id}", method=RequestMethod.GET)
		public ResponseEntity<Question> selectOneQuestion(@PathVariable("qna_id") Integer id) {
			logger.info("selectOneQuestion()");
			
			Question questionOne = questionService.selectOneQuestion(id);
			ResponseEntity<Question> responseEntity = new ResponseEntity<Question> (questionOne,HttpStatus.OK);
			return responseEntity;
		}
		/**
		 * 해당 문의글에 답변작성 성공 여부를 반환한다
		 * 
		 * @param id
		 * @param answer(Question 타입의 객체)
		 * @return ResponseEntity<Question>
		 *  해당 문의글에 답변 달기를 성공한 경우 - 200(OK)을 반환
		 */
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
