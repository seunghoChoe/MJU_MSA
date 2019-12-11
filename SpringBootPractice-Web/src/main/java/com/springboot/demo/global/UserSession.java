package com.springboot.demo.global;

import com.springboot.demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import static com.springboot.demo.global.Constants.LOGGED_USER;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Jaeha Son
 * @date 2019-12-13
 * @version 0.9
 * @description
 * 프로젝트 내에서 세션 처리를 위해 공통적으로 사용되는 Utility 클래스
 */
public class UserSession {
    private static final Logger logger = LoggerFactory.getLogger(UserSession.class);

    /**
     * 세션에 계정 정보 등록/수정하기
     * @param request
     *  세션에 로그인 계정 정보가 없는 HttpServletRequest 객체
     * @param user
     *  세션에 저장할 로그인 계정 정보
     */
    public static void setUserSession(HttpServletRequest request, User user) {
        request.getSession().setAttribute(LOGGED_USER, user);
    }

    /**
     * 세션에서 현재 로그인 계정 정보 가져오기 (그외 세션이 필요할 경우, 세션 이름을 매개변수로 추가하여 사용한다.)
     * @param request
     *  세션에 로그인 계정 정보가 저장된 HttpServletRequest 객체
     * @return User
     *  세션에 저장된 로그인 계정 정보
     */
    public static User getUserSession(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(LOGGED_USER);
    }

    /**
     * 세션에서 계정 정보 삭제하기
     * @param request
     *  세션에 로그인 계정 정보가 저장된 HttpServletRequest 객체
     */
    public static void deleteUserSession(HttpServletRequest request) {
        request.getSession().removeAttribute(LOGGED_USER);
    }
}