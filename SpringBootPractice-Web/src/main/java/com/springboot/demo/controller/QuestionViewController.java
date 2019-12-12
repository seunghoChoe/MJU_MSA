package com.springboot.demo.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.springboot.demo.interceptor.LoginCheck;
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
import com.springboot.demo.model.Question;
import static com.springboot.demo.global.Constants.createApiUri;
import static com.springboot.demo.global.UserSession.getUserSession;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Seungho Choe, Jaeha Son
 * @date 2019-12-13
 * @version 0.9
 * @description
 * 문의사항 뷰와 관련된 Controller 클래스
 */
@RestController
public class QuestionViewController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionViewController.class);
    private String baseURI = createApiUri("board-service");

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 문의사항 목록 조회
     * @param mv
     *  ModelAndView 객체
     * @return ModelAndView
     *  문의사항 목록 모델과 JSP 페이지 반환
     */
	@RequestMapping(value = "/board/questions", method = RequestMethod.GET)
    public ModelAndView getQuestions(ModelAndView mv) {
        logger.info("getQuestions()");

        String uri = baseURI + "/questions";
        ResponseEntity<List<Question>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Question>>() {
        });
        List<Question> questionList = response.getBody();

        mv.addObject("questionList", questionList);
        mv.setViewName("board/questions/list");
        return mv;
    }

    /**
     * 문의사항 상세 조회
     * @param mv
     *  ModelAndView 객체
     * @param qna_index
     *  문의사항 번호
     * @return ModelAndView
     *  문의사항 상세 모델과 JSP 페이지 반환
     */
    @RequestMapping(value = "/board/question/{qna_index}", method = RequestMethod.GET)
    public ModelAndView getQuestion(ModelAndView mv, @PathVariable int qna_index) {
        logger.info("getQuestion()");

        String uri = baseURI + "/questions/" + qna_index;
        Question responseQuestion = restTemplate.getForObject(uri, Question.class);

        mv.addObject("question", responseQuestion);
        mv.setViewName("board/questions/question");
        return mv;
    }

    /**
     * 문의사항 등록 폼
     * @param mv
     *  ModelAndView 객체
     * @return ModelAndView
     *  빈 문의사항 모델과 JSP 페이지 반환
     */
    @LoginCheck(type = LoginCheck.Type.TRUE)
	@RequestMapping(value = "/board/questions/new", method = RequestMethod.GET)
    public ModelAndView getQuestionNew(ModelAndView mv) {
        logger.info("getQuestionNew()");

        mv.setViewName("board/questions/new");

        return mv;
    }

    /**
     * 문의사항 등록
     * @param mv
     *  ModelAndView 객체
     * @param newQuestion
     *  뷰에서 전달된 문의사항 모델
     * @param request
     *  HttpServletRequest 객체
     * @return ModelAndView
     *  문의사항 등록 성공 - JSP 페이지 반환
     *  문의사항 등록 실패 - 미구현 (리소스 서버로부터 받은 응답(responseQuestion)를 검사하여 구현해야 함)
     */
    @LoginCheck(type = LoginCheck.Type.TRUE)
    @RequestMapping(value = "/board/questions/new", method = RequestMethod.POST)
    public ModelAndView postQuestionNew(ModelAndView mv, @ModelAttribute Question newQuestion, HttpServletRequest request) {
        logger.info("postQuestionNew()");
		newQuestion.setQna_user_id(getUserSession(request).getUser_id());

        String uri = baseURI + "/question";
        Question responseQuestion = restTemplate.postForObject(uri, newQuestion, Question.class);

        mv.setViewName("redirect:/board/questions");
        return mv;
    }
}
