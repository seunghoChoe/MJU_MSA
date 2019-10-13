package com.springboot.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.demo.global.Constants;
import com.springboot.demo.model.Post;
@RestController
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(WebController.class);
	private String baseURI = "http://" + Constants.API_GATEWAY_IPADDRESS + ":"
			+ Constants.API_GATEWAY_PORTNUMBER + "/board-service";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value="/board/postList", method=RequestMethod.GET)
	public ModelAndView postList(ModelAndView modelAndView) {
		logger.info("postList()");
		String url = baseURI + "/posts";
		ResponseEntity<List<Post>> response =
				restTemplate.exchange(url,HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {});
		List<Post> postList = response.getBody();
		
		modelAndView.addObject("postList", postList);
		modelAndView.setViewName("board/postList");
		return modelAndView;
	}
}
