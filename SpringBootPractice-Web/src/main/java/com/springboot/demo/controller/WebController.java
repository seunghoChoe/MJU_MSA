package com.springboot.demo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class WebController {
	private static final Logger logger = LoggerFactory.getLogger(WebController.class);
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public ModelAndView index(ModelAndView modelAndView) {
		logger.info("index()");
		
		modelAndView.setViewName("index");
		return modelAndView;
	}
	
	
	
}
