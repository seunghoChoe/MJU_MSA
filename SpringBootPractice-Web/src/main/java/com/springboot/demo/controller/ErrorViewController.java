package com.springboot.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Jaeha Son
 * @date 2019-12-13
 * @version 0.9
 * @description
 * 에러 뷰와 관련된 Controller 클래스
 */
@RestController
public class ErrorViewController implements ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(ErrorViewController.class);
    private static final String ERROR_URI = "/error";

    /**
     * 에러가 발생한 URI 경로를 가져온다.
     * @return String
     *  에러가 발생한 URI
     */
    @Override
    public String getErrorPath() {
        return ERROR_URI;
    }

    /**
     * 익셉션 발생 시, 오류 페이지로 Forward
     * @param request
     *  HttpServletRequest 객체
     * @param mv
     *  ModelAndView 객체
     * @return ModelAndView
     *  에러 코드 및 메세지, 시간 모델과 JSP 페이지 반환
     */
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView handleError(HttpServletRequest request, ModelAndView mv) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.parseInt(status.toString()));
        logger.info("handleError(" + httpStatus.toString() + ")");

        mv.addObject("errorCode", status.toString());
        mv.addObject("errorMessage", httpStatus.getReasonPhrase());
        mv.addObject("errorTimeStamp", new Date());
        mv.setViewName("error");
        return mv;
    }
}