package com.springboot.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Class: 메인 뷰 컨트롤러 클래스
 */
@Controller
public class WebViewController {
    private static final Logger logger = LoggerFactory.getLogger(WebViewController.class);

    /**
     * @Method: 메인 화면
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        logger.info("home()");

        return "/home";
    }

    /**
     * @Method: 서비스 화면
     */
    @RequestMapping(value = "/service", method = RequestMethod.GET)
    public String service() {
        logger.info("service()");

        return "/service";
    }
}