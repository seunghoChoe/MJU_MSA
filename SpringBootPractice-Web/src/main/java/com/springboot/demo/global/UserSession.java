package com.springboot.demo.global;

import com.springboot.demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import static com.springboot.demo.global.Constants.LOGGED_USER;

/**
 * @Class: 세션 유틸리티 클래스
 */
public class UserSession {
    private static final Logger logger = LoggerFactory.getLogger(UserSession.class);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * @Method: 세션에 계정 정보 등록/수정하기
     */
    public static void setUserSession(HttpServletRequest request, User user) {
        request.getSession().setAttribute(LOGGED_USER, user);
    }

    /**
     * @Method: 세션에서 현재 로그인 계정 정보 가져오기 (그외 세션이 필요할 경우, 세션 이름을 매개변수로 추가하여 사용한다.)
     */
    public static User getUserSession(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(LOGGED_USER);
    }

    /**
     * @Method: 세션에서 계정 정보 삭제하기
     */
    public static void deleteUserSession(HttpServletRequest request) {
        request.getSession().removeAttribute(LOGGED_USER);
    }
}