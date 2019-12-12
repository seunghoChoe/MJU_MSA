package com.springboot.demo.service;

import com.springboot.demo.model.MyRestaurant;
import com.springboot.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Jaeha Son
 * @date 2019-12-13
 * @version 0.9
 * @description
 * 리소스 서버(User Service)와 HTTP 통신을 위한 계정 Service 클래스
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final RestTemplate restTemplate;

    /**
     * 계정 목록 API 호출
     * @param uri
     *  User Service API
     * @return ResponseEntity<List<User>>
     *  JSON 타입 응답 객체
     */
    public ResponseEntity<List<User>> getUsers(String uri) {
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>(){});
    }

    /**
     * 계정 등록 API 호출
     * @param uri
     *  User Service API
     * @param user
     *  리소스 서버에 전달할 요청 객체
     * @return ResponseEntity<User>
     *  JSON 타입 응답 객체
     */
    public ResponseEntity<User> postJoin(String uri, User user) {
        return restTemplate.postForEntity(uri, user, User.class);
    }

    /**
     * 계정 로그인 API 호출
     * @param uri
     *  User Service API
     * @param user
     *  리소스 서버에 전달할 요청 객체
     * @return ResponseEntity<User>
     *  JSON 타입 응답 객체
     */
    public ResponseEntity<User> postLogin(String uri, User user) {
        return restTemplate.postForEntity(uri, user, User.class);
    }

    /**
     * 계정 수정 API 호출
     * @param uri
     *  User Service API
     * @param entity
     *  리소스 서버에 전달할 요청 객체
     * @return ResponseEntity<User>
     *  JSON 타입 응답 객체
     */
    public ResponseEntity<User> putEdit(String uri, HttpEntity entity) {
        return restTemplate.exchange(uri, HttpMethod.PUT, entity, User.class);
    }

    /**
     * 계정 탈퇴 API 호출
     * @param uri
     *  User Service API
     */
    public void deleteUser(String uri) {
        restTemplate.delete(uri);
    }

    /**
     * 즐겨찾기 목록 API 호출
     * @param uri
     *  User Service API
     * @return ResponseEntity<List<MyRestaurant>>
     *  JSON 타입 응답 객체
     */
    public ResponseEntity<List<MyRestaurant>> getFavorites(String uri) {
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<MyRestaurant>>(){});
    }

    /**
     * 즐겨찾기 등록 API 호출
     * @param uri
     *  User Service API
     * @param myRestaurant
     *  리소스 서버에 전달할 요청 객체
     * @return ResponseEntity<MyRestaurant>
     *  JSON 타입 응답 객체
     */
    public ResponseEntity<MyRestaurant> postFavorite(String uri, MyRestaurant myRestaurant) {
        return restTemplate.postForEntity(uri, myRestaurant, MyRestaurant.class);
    }
}