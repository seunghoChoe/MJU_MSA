package com.springboot.demo.service;

import com.springboot.demo.model.Grade;
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
import java.util.List;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Jaeha Son
 * @date 2019-12-13
 * @version 0.9
 * @description
 * 리소스 서버(Restaurant Service)와 HTTP 통신을 위한 식당 Service 클래스
 */
@Service
@RequiredArgsConstructor
public class RestaurantService {
    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);
    private final RestTemplate restTemplate;

    /**
     * 식당 목록 API 호출
     * @param uri
     *  Restaurant Service API
     * @return ResponseEntity<List<Restaurant>>
     *  JSON 타입 응답 객체
     */
    public ResponseEntity<List<Restaurant>> getRestaurants(String uri) {
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Restaurant>>(){});
    }

    /**
     * 식당 목록 API 호출 (최근 등록된 식당 6개)
     * @param uri
     *  Restaurant Service API
     * @return List<Restaurant>
     *  Collection 타입의 리스트 객체
     */
    public List<Restaurant> getSixRestaurants(String uri) {
        // 식당 6개가 필요함!
        List<Restaurant> restaurantList = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Restaurant>>(){}).getBody();
        List<Restaurant> subRestaurantList = restaurantList.subList(restaurantList.size() - 6, restaurantList.size());

        return subRestaurantList;
    }

    /**
     * 식당 상세 API 호출
     * @param uri
     *  Restaurant Service API
     * @return ResponseEntity<Restaurant>
     *  JSON 타입 응답 객체
     */
    public ResponseEntity<Restaurant> getRestaurant(String uri) {
        return restTemplate.getForEntity(uri, Restaurant.class);
    }

    /**
     * 식당 등록 API 호출
     * @param uri
     *  Restaurant Service API
     * @param restaurant
     *  리소스 서버에 전달할 요청 객체
     * @return ResponseEntity<Restaurant>
     *  JSON 타입 응답 객체
     */
    public ResponseEntity<Restaurant> postRestaurantNew(String uri, Restaurant restaurant) {
        return restTemplate.postForEntity(uri, restaurant, Restaurant.class);
    }

    /**
     * 식당 수정 API 호출
     * @param uri
     *  Restaurant Service API
     * @param entity
     *  리소스 서버에 전달할 요청 객체
     * @return ResponseEntity<Restaurant>
     *  JSON 타입 응답 객체
     */
    public ResponseEntity<Restaurant> putEditRestaurant(String uri, HttpEntity entity) {
        return restTemplate.exchange(uri, HttpMethod.PUT, entity, Restaurant.class);
    }

    /**
     * 식당 삭제 API 호출
     * @param uri
     *  Restaurant Service API
     * @param entity
     *  리소스 서버에 전달할 요청 객체
     * @return ResponseEntity<Restaurant>
     *  JSON 타입 응답 객체
     */
    public ResponseEntity<Restaurant> deleteRestaurant(String uri, HttpEntity entity) {
        return restTemplate.exchange(uri, HttpMethod.DELETE, entity, Restaurant.class);
    }

    /**
     * 평점 등록 API 호출
     * @param uri
     *  Restaurant Service API
     * @param grade
     *  리소스 서버에 전달할 요청 객체
     * @return ResponseEntity<Grade>
     *  JSON 타입 응답 객체
     */
    public ResponseEntity<Grade> postGradeNew(String uri, Grade grade) {
        return restTemplate.postForEntity(uri, grade, Grade.class);
    }
}