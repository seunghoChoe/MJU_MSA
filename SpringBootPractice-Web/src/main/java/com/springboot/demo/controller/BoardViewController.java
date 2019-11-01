package com.springboot.demo.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.springboot.demo.model.Post;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.springboot.demo.global.Constants.SERVER_MESSAGE;
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
    public ModelAndView getPostNew(ModelAndView mv) {
        logger.info("getPostNew()");

        mv.addObject("post", new Post());
        mv.setViewName("board/posts/new");
        return mv;
    }

    /**
     * @Method: 게시글 등록
     */
    @RequestMapping(value = "/board/posts/new", method = RequestMethod.POST)
    public ModelAndView postPostNew(@ModelAttribute("post") @Valid Post post, BindingResult result, HttpServletRequest request, ModelAndView mv, RedirectAttributes rttr) {
        logger.info("postPostNew()");

        if (result.hasErrors()) {
            logger.info(result.toString());

            mv.addObject(SERVER_MESSAGE, "게시글 등록 정보를 확인해주세요.");
            mv.setViewName("board/posts/new");
        } else {
            String uri = baseURI + "/post";
            post.setPost_user_id(getUserSession(request).getUser_id()); // 로그인 계정의 ID 세팅
            ResponseEntity<Post> response = restTemplate.postForEntity(uri, post, Post.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                rttr.addFlashAttribute(SERVER_MESSAGE, "게시글 등록이 완료되었습니다.");
                mv.setViewName("redirect:/board/posts/"); // 게시글 목록으로 페이지 이동
            } else {
                mv.addObject(SERVER_MESSAGE, "게시글 등록 중, 문제가 발생하였습니다.");
                mv.setViewName("board/posts/new");
            }
        }
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
    public ModelAndView getPost(@PathVariable("post_id") int post_id, ModelAndView mv) {
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
    @RequestMapping(value = "/board/posts/{post_id}/edit", method = RequestMethod.GET)
    public ModelAndView getPostEdit(@PathVariable("post_id") int post_id, HttpServletRequest request, ModelAndView mv, RedirectAttributes rttr) {
        logger.info("getPostEdit()");

        String uri = baseURI + "/posts/" + post_id;
        ResponseEntity<Post> response = restTemplate.getForEntity(uri, Post.class);
        Post post = response.getBody();

        if (getUserSession(request).getUser_id().equals(post.getPost_user_id())) {
            mv.addObject("post", post);
            mv.setViewName("board/posts/edit");
        } else {
            rttr.addFlashAttribute(SERVER_MESSAGE, "게시글 작성자만 수정할 수 있습니다.");
            mv.setViewName("redirect:/board/posts/" + post.getPost_id());
        }

        return mv;
    }

    /**
     * @Method: 게시글 수정
     */
    @RequestMapping(value = "/board/posts/{post_id}/edit", method = RequestMethod.PUT)
    public ModelAndView putEditPost(@PathVariable("post_id") int post_id, @ModelAttribute("post") @Valid Post post, BindingResult result, RedirectAttributes rttr, HttpServletRequest request, ModelAndView mv) {
        logger.info("putEditPost()");

        if (result.hasErrors()) {
            logger.info(result.toString());

            mv.addObject(SERVER_MESSAGE, "게시글 등록 정보를 확인해주세요.");
            mv.setViewName("board/posts/edit");
        } else {
            String uri = baseURI + "/posts" + post_id;
            post.setPost_user_id(getUserSession(request).getUser_id()); // 로그인 계정의 ID 세팅

            HttpEntity<Post> entity = new HttpEntity<>(post);
            ResponseEntity<Post> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, Post.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                rttr.addFlashAttribute(SERVER_MESSAGE, "게시글 수정이 완료되었습니다.");
                mv.setViewName("redirect:/board/posts/"); // 게시글 목록으로 페이지 이동
            } else {
                mv.addObject(SERVER_MESSAGE, "게시글 수정 중, 문제가 발생하였습니다.");
                mv.setViewName("board/posts/new");
            }
        }
        return mv;
    }

    /**
     * @Method: 게시글 삭제 (게시글 번호, 세션에 저장된 사용자 ID 전달 후, API 서버의 응답 확인하여 삭제)
     */
    @RequestMapping(value = "/board/posts/{post_id}", method = RequestMethod.DELETE)
    public int deletePost(@PathVariable("post_id") int post_id, HttpServletRequest request, ModelAndView mv, RedirectAttributes rttr) {
        logger.info("deletePost()");

        String uri = baseURI + "/posts/" + post_id;
        Post post = Post.builder().post_id(post_id).post_user_id(getUserSession(request).getUser_id()).build();

        HttpEntity<Post> entity = new HttpEntity<>(post);
        ResponseEntity<Integer> response = restTemplate.exchange(uri, HttpMethod.DELETE, entity, Integer.class);
        System.out.println(response);
        System.out.println(response.getBody());

        // 응답 코드로 구현할 경우, 게시판 서비스에서 응답 코드를 전달해야 한다.
        if (response.getStatusCode().is2xxSuccessful()) {
            return 1;
        } else {
            return 0;
        }

        // 응답 값으로 구현할 경우
        // return response.getBody();
    }
}