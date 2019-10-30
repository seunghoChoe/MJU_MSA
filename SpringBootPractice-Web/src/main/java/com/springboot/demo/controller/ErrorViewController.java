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
 * @Class: 에러 뷰 컨트롤러 클래스
 */
@RestController
public class ErrorViewController implements ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(ErrorViewController.class);
    private static final String ERROR_URI = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_URI;
    }

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