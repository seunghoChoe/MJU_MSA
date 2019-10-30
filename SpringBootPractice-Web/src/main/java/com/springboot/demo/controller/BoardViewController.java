package com.springboot.demo.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.springboot.demo.model.Post;

import javax.servlet.http.HttpServletRequest;

import static com.springboot.demo.global.Constants.createBaseURI;
import static com.springboot.demo.global.UserSession.getUserSession;

/**
 * @Class: 게시판 뷰 컨트롤러 클래스 (매거진)
 */
@RestController
public class BoardViewController {
    private static final Logger logger = LoggerFactory.getLogger(WebViewController.class);
    private String baseURI = createBaseURI("board-service");

    @Autowired
    private RestTemplate restTemplate;

    /**
     * @Method: 게시글 등록 폼
     */
    @RequestMapping(value = "/board/posts/new", method = RequestMethod.GET)
    public ModelAndView getPostsNew(ModelAndView mv) {
        logger.info("getPostNew()");

        mv.addObject("post", new Post());
        mv.setViewName("board/posts/new");
        return mv;
    }

    /**
     * @Method: 게시글 등록
     */
    @RequestMapping(value = "/board/posts/new", method = RequestMethod.POST)
    public ModelAndView postPostsNew(@ModelAttribute("post") Post post, HttpServletRequest request, ModelAndView mv) {
        logger.info("postPostsNew()");
        post.setPost_user_id(getUserSession(request).getUser_id()); // 로그인 계정의 ID 세팅

        System.out.println(post.getPost_user_id());
        System.out.println(post.getPost_title());
        System.out.println(post.getPost_content());

        String uri = baseURI + "/post-new";
        ResponseEntity<Post> response = restTemplate.postForEntity(uri, post, Post.class);

        mv.setViewName("board/posts/post"); // 게시글 등록 후, 해당 게시글 번호로 뷰를 지정해야 한다.
        return mv;
    }

    /**
     * @Method: 게시글 목록 조회
     */
    @RequestMapping(value = "/board/posts", method = RequestMethod.GET)
    public ModelAndView getPosts(ModelAndView mv) {
        logger.info("getPosts()");

        String uri = baseURI + "/posts";
        ResponseEntity<List<Post>> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {});
        List<Post> postList = response.getBody();

        mv.addObject("postList", postList);
        mv.setViewName("board/posts/list");
        return mv;
    }

    /**
     * @Method: 게시글 상세 조회
     */
    @RequestMapping(value = "/board/posts/{post_id}", method = RequestMethod.GET)
    public ModelAndView getPost(@PathVariable("post_id") String post_id, ModelAndView mv) {
        logger.info("getPost()");

        String uri = baseURI + "/posts/" + post_id;
        ResponseEntity<Post> response = restTemplate.getForEntity(uri, Post.class);
        Post post = response.getBody();

        mv.addObject("post", post);
        mv.setViewName("board/posts/post");
        return mv;
    }

    /**
     * @Method: 게시글 수정 폼
     */

    /**
     * @Method: 게시글 수정
     */

    /**
     * @Method: 게시글 삭제 (게시글 번호, 세션에 저장된 사용자 ID 전달 후, API 서버의 응답 확인하여 삭제)
     */
}