package com.springboot.demo.controller;

import static com.springboot.demo.global.Constants.createBaseURI;
import static com.springboot.demo.global.UserSession.getUserSession;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.demo.model.Post;
import com.springboot.demo.model.Question;

/**
 * @Class: 게시판 뷰 컨트롤러 클래스 (매거진)
 */
@RestController
public class QuestionViewController {
	 	private static final Logger logger = LoggerFactory.getLogger(BoardViewController.class);
	    private String baseURI = createBaseURI("board-service");
	    
	    
	    @Autowired
	    private RestTemplate restTemplate;
	    
	    /**
	     * @Method: 문의사항 목록 조회
	     */
	    @RequestMapping(value = "/board/questions", method = RequestMethod.GET)
	    public ModelAndView getQuestions(ModelAndView mv) {
	        logger.info("getQuestions()");

	        String uri = baseURI + "/questions";
	        ResponseEntity<List<Question>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Question>>() {});
	        List<Question> questionList = response.getBody();

	        mv.addObject("questionList", questionList);
	        mv.setViewName("board/questions/list");
	        return mv;
	    }
	    
	    /**
	     * @Method: 문의사항 상세 조회
	     */
	    @RequestMapping(value = "/board/question/{qna_index}", method = RequestMethod.GET)
	    public ModelAndView getQuestion(ModelAndView mv, @PathVariable int qna_index) {
	        logger.info("getQuestion()");

	        String uri = baseURI + "/questions/" + qna_index;
	        System.out.println(uri);
	        Question responseQeustion = restTemplate.getForObject(uri, Question.class);
	        System.out.println(responseQeustion.getQna_question());
	        mv.addObject("question", responseQeustion);
	        mv.setViewName("board/questions/question");
	        return mv;
	    }
	    
	    /**
	     * @Method: 문의사항 작성 페이지 
	     */
	    @RequestMapping(value = "/board/questions/new", method = RequestMethod.GET)
	    public ModelAndView getQuestionNew(ModelAndView mv) {
	        logger.info("getQuestionNew()");

	        mv.setViewName("board/questions/new");
	        return mv;
	    }
	    
	    /**
	     * @Method: 문의사항 작성 
	     */
	    @RequestMapping(value = "/board/questions/new", method = RequestMethod.POST)
	    public ModelAndView postQuestionNew(ModelAndView mv, @ModelAttribute Question newquestion, HttpServletRequest request) {
	        logger.info("postQuestionNew()");
	        System.out.println(newquestion.getQna_category() + " " + newquestion.getQna_question());
	        newquestion.setQna_user_id(getUserSession(request).getUser_id());
	        
	        String uri = baseURI + "/question";
	        Question responseQeustion = restTemplate.postForObject(uri, newquestion, Question.class);
	        
	        mv.setViewName("redirect:/board/questions");
	        return mv;
	    }
}
