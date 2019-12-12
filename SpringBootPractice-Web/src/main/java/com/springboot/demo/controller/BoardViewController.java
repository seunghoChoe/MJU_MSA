package com.springboot.demo.controller;

import com.springboot.demo.interceptor.LoginCheck;
import com.springboot.demo.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.springboot.demo.model.Post;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import static com.springboot.demo.global.Constants.*;
import static com.springboot.demo.global.UserSession.getUserSession;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Jaeha Son
 * @date 2019-12-13
 * @version 0.9
 * @description
 * 게시판 뷰와 관련된 Controller 클래스
 */
@RestController
@RequiredArgsConstructor
public class BoardViewController {
    private static final Logger logger = LoggerFactory.getLogger(BoardViewController.class);
    private final String boardServiceUri = createApiUri("board-service");
    private final BoardService boardService;

    /**
     * 게시글 등록 폼
     * @param mv
     *  ModelAndView 객체
     * @return ModelAndView
     *  빈 게시글 모델과 JSP 페이지 반환
     */
    @LoginCheck(type = LoginCheck.Type.TRUE)
    @RequestMapping(value = "/board/posts/new", method = RequestMethod.GET)
    public ModelAndView getPostNew(ModelAndView mv) {
        logger.info("getPostNew()");

        mv.addObject("post", new Post());
        mv.setViewName("board/posts/new");

        return mv;
    }

    /**
     * 게시글 등록
     * @param post
     *  뷰에서 전달된 게시글 모델
     * @param result
     *  유효성 검증 결과(BindingResult) 객체
     * @param request
     *  HttpServletRequest 객체
     * @param mv
     *  ModelAndView 객체
     * @param rttr
     *  RedirectAttributes 객체
     * @return ModelAndView
     *  유효성 검증 실패 - 실패 메세지와 JSP 페이지 반환
     *  게시글 등록 성공 - 성공 메세지와 JSP 페이지 반환
     *  리소스 서버 응답 실패 - 실패 메세지와 JSP 페이지 반환
     */
    @LoginCheck(type = LoginCheck.Type.TRUE)
    @RequestMapping(value = "/board/posts/new", method = RequestMethod.POST)
    public ModelAndView postPostNew(@ModelAttribute("post") @Valid Post post, BindingResult result,
                                    HttpServletRequest request, ModelAndView mv, RedirectAttributes rttr) {
        logger.info("postPostNew()");

        if (result.hasErrors()) {
            logger.info(result.toString());

            mv.addObject(SERVER_MESSAGE, "게시글 등록 정보를 확인해주세요.");
            mv.setViewName("board/posts/new");

        } else {
            post.setPost_user_id(getUserSession(request).getUser_id());
            post.setPost_image(getImaSrc(post.getPost_content())); // 썸네일 이미지 지정

            ResponseEntity<Post> response = boardService.postPostNew(boardServiceUri + "/post", post);

            if (response.getStatusCode().is2xxSuccessful()) {
                rttr.addFlashAttribute(SERVER_MESSAGE, "게시글 등록이 완료되었습니다.");
                mv.setViewName("redirect:/board/posts");

            } else {
                mv.addObject(SERVER_MESSAGE, "게시글 등록 중, 문제가 발생하였습니다.");
                mv.setViewName("board/posts/new");
            }
        }

        return mv;
    }

    /**
     * 게시글 목록 조회
     * @param mv
     *  ModelAndView 객체
     * @return ModelAndView
     *  게시글 목록 모델과 JSP 페이지 반환
     */
    @RequestMapping(value = "/board/posts", method = RequestMethod.GET)
    public ModelAndView getPosts(ModelAndView mv) {
        logger.info("getPosts()");

        List<Post> postList = boardService.getPosts(boardServiceUri + "/posts").getBody();
        mv.addObject("postList", postList);
        mv.setViewName("board/posts/list");

        return mv;
    }

    /**
     * 게시글 상세 조회
     * @param post_id
     *  게시글 번호
     * @param mv
     *  ModelAndView 객체
     * @return ModelAndView
     *  게시글 상세 모델과 JSP 페이지 반환
     */
    @RequestMapping(value = "/board/posts/{post_id}", method = RequestMethod.GET)
    public ModelAndView getPost(@PathVariable("post_id") int post_id, ModelAndView mv) {
        logger.info("getPost()");

        ResponseEntity<Post> response = boardService.getPost(boardServiceUri + "/posts/" + post_id);
        mv.addObject("post", response.getBody());
        mv.setViewName("board/posts/post");

        return mv;
    }

    /**
     * 게시글 수정 폼
     * @param post_id
     *  게시글 번호
     * @param request
     *  HttpServletRequest 객체
     * @param mv
     *  ModelAndView 객체
     * @param rttr
     *  RedirectAttributes 객체
     * @return ModelAndView
     *  작성자 확인 성공 - 수정을 위한 게시글 상세 모델과 JSP 페이지 반환
     *  작성자 확인 실패 - 실패 메세지와 JSP 페이지 반환
     */
    @LoginCheck(type = LoginCheck.Type.TRUE)
    @RequestMapping(value = "/board/posts/{post_id}/edit", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getEditPost(@PathVariable("post_id") int post_id, HttpServletRequest request, ModelAndView mv, RedirectAttributes rttr) {
        logger.info("getEditPost()");

        ResponseEntity<Post> response = boardService.getPost(boardServiceUri + "/posts/" + post_id);
        Post post = response.getBody();

        if (getUserSession(request).getUser_id().equals(post.getPost_user_id())) {
            mv.addObject("post", post);
            mv.setViewName("board/posts/edit");

        } else {
            rttr.addFlashAttribute(SERVER_MESSAGE, "게시글 작성자만 수정할 수 있습니다.");
            mv.setViewName("redirect:/board/posts/" + post_id);
        }

        return mv;
    }

    /**
     * 게시글 수정
     * @param post_id
     *  게시글 번호
     * @param post
     *  뷰에서 전달된 게시글 모델
     * @param result
     *  유효성 검증 결과(BindingResult) 객체
     * @param rttr
     *  RedirectAttributes 객체
     * @param request
     *  HttpServletRequest 객체
     * @param mv
     *  ModelAndView 객체
     * @return ModelAndView
     *  작성자 확인 실패 - 실패 메세지와 JSP 페이지 반환
     *  유효성 검증 실패 - 실패 메세지와 JSP 페이지 반환
     *  게시글 수정 성공 - 성공 메세지와 JSP 페이지 반환
     *  리소스 서버 응답 실패 - 실패 메세지와 JSP 페이지 반환
     */
    @LoginCheck(type = LoginCheck.Type.TRUE)
    @RequestMapping(value = "/board/posts/{post_id}/edit", method = RequestMethod.PUT)
    public ModelAndView putEditPost(@PathVariable("post_id") int post_id, @ModelAttribute("post") @Valid Post post, BindingResult result,
                                    RedirectAttributes rttr, HttpServletRequest request, ModelAndView mv) {
        logger.info("putEditPost()");

        String userId = boardService.getPost(boardServiceUri + "/posts/" + post_id).getBody().getPost_user_id();

        if (! getUserSession(request).getUser_id().equals(userId)) {
            rttr.addFlashAttribute(SERVER_MESSAGE, "게시글 작성자만 수정할 수 있습니다.");
            mv.setViewName("redirect:/board/posts/" + post_id);

        } else if (result.hasErrors()) {
            logger.info(result.toString());

            rttr.addFlashAttribute(SERVER_MESSAGE, "게시글 수정 정보를 확인해주세요.");
            mv.setViewName("redirect:/board/posts/" + post_id + "/edit");

        } else {
            // 게시글 번호, 제목, 내용, 썸네일, 세션에 저장된 계정 ID 를 리소스 서버로 전달함
            post.setPost_user_id(getUserSession(request).getUser_id()); // 로그인 계정의 ID 세팅
            post.setPost_image(getImaSrc(post.getPost_content())); // 썸네일 이미지 세팅
            ResponseEntity<Post> response = boardService.putEditPost(boardServiceUri + "/posts/" + post_id, new HttpEntity<>(post));

            if (response.getStatusCode().is2xxSuccessful()) {
                rttr.addFlashAttribute(SERVER_MESSAGE, "게시글 수정이 완료되었습니다.");
                mv.setViewName("redirect:/board/posts");

            } else {
                rttr.addFlashAttribute(SERVER_MESSAGE, "게시글 수정 중, 문제가 발생하였습니다.");
                mv.setViewName("redirect:/board/posts/edit");
            }
        }

        return mv;
    }

    /**
     * 게시글 삭제
     * @param post_id
     *  게시글 번호
     * @param request
     *  HttpServletRequest 객체
     * @return int
     *  게시글 삭제 성공 - 1 반환
     *  게시글 삭제 실패 - 0 반환
     */
    @LoginCheck(type = LoginCheck.Type.TRUE)
    @RequestMapping(value = "/board/posts/{post_id}", method = RequestMethod.DELETE)
    public int deletePost(@PathVariable("post_id") int post_id, HttpServletRequest request) {
        logger.info("deletePost()");

        String userId = boardService.getPost(boardServiceUri + "/posts/" + post_id).getBody().getPost_user_id();

        if (! getUserSession(request).getUser_id().equals(userId)) {
            return 0;

        } else {
            Post post = Post.builder().post_id(post_id).post_user_id(getUserSession(request).getUser_id()).build();

            // 게시글 번호, 세션에 저장된 사용자 ID 를 API 서버로 전달함
            ResponseEntity<Post> response = boardService.deletePost(boardServiceUri + "/posts/" + post_id, new HttpEntity<>(post));

            if (response.getStatusCode().is2xxSuccessful()) {
                return 1;

            } else {
                return 0;
            }
        }
    }
}