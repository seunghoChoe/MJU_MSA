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
import com.springboot.demo.model.Post;
import static com.springboot.demo.global.Constants.createBaseURI;

/**
 * @Class: 게시판 뷰 컨트롤러 클래스
 */
@RestController
public class BoardViewController {
    private static final Logger logger = LoggerFactory.getLogger(WebViewController.class);
    private String baseURI = createBaseURI("board-service");

	@Autowired
    private RestTemplate restTemplate;

    /**
     * @Method: 게시글 목록 조회
     */
    @RequestMapping(value = "/board/posts", method = RequestMethod.GET)
    public ModelAndView getPosts(ModelAndView mv) {
        logger.info("getPosts()");

        ResponseEntity<List<Post>> response = restTemplate.exchange(baseURI + "/posts", HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {});
        List<Post> postList = response.getBody();

        mv.addObject("postList", postList);
        mv.setViewName("/board/posts");
        return mv;
    }
}