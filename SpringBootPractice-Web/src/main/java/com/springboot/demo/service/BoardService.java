package com.springboot.demo.service;

import com.springboot.demo.model.Post;
import com.springboot.demo.model.Restaurant;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.springboot.demo.global.Constants.removeTag;

import java.util.List;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Jaeha Son
 * @date 2019-12-13
 * @version 0.9
 * @description
 * 리소스 서버(Board Service)와 HTTP 통신을 위한 게시판 Service 클래스
 */
@Service
@RequiredArgsConstructor
public class BoardService {
    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);
    private final RestTemplate restTemplate;

    /**
     * 게시글 목록 API 호출
     * @param uri
     *  Board Service API
     * @return ResponseEntity<List<Post>>
     *  JSON 타입 응답 객체
     */
    public ResponseEntity<List<Post>> getPosts(String uri) {
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>(){});
    }
    
//    /**
//     * 게시글 목록 API 호출
//     * @param uri
//     *  Board Service API
//     * @return ResponseEntity<List<Post>>
//     *  JSON 타입 응답 객체
//     * @throws Exception
//     */
//    public List<Post> getMinimizedPosts(String uri) throws Exception {
//        List<Post> postList = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>(){}).getBody();
//
//        for (Post post: postList) {
//            String tempStr = post.getPost_content();
//            post.setPost_content(removeTag(tempStr));
//        }
//
//        return postList;
//    }

    /**
     * 게시글 상세 API 호출
     * @param uri
     *  Board Service API
     * @return ResponseEntity<Post>
     *  JSON 타입 응답 객체
     */
    public ResponseEntity<Post> getPost(String uri) {
        return restTemplate.getForEntity(uri, Post.class);
    }

    /**
     * 게시글 상세 API 호출
     * @param uri
     *  Board Service API
     * @param post
     *  리소스 서버에 전달할 요청 객체
     * @return ResponseEntity<Post>
     *  JSON 타입 응답 객체
     */
    public ResponseEntity<Post> postPostNew(String uri, Post post) {
        return restTemplate.postForEntity(uri, post, Post.class);
    }

    /**
     * 게시글 수정 API 호출
     * @param uri
     *  Board Service API
     * @param entity
     *  리소스 서버에 전달할 요청 객체
     * @return ResponseEntity<Post>
     *  JSON 타입 응답 객체
     */
    public ResponseEntity<Post> putEditPost(String uri, HttpEntity entity) {
        return restTemplate.exchange(uri, HttpMethod.PUT, entity, Post.class);
    }

    /**
     * 게시글 삭제 API 호출
     * @param uri
     *  Board Service API
     * @param entity
     *  리소스 서버에 전달할 요청 객체
     * @return ResponseEntity<Post>
     *  JSON 타입 응답 객체
     */
    public ResponseEntity<Post> deletePost(String uri, HttpEntity entity) {
        return restTemplate.exchange(uri, HttpMethod.DELETE, entity, Post.class);
    }
}