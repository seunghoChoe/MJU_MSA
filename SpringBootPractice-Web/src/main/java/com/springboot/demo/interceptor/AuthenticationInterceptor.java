package com.springboot.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.springboot.demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import static com.springboot.demo.global.Constants.LOGGED_USER;
import static com.springboot.demo.global.Constants.alertMessage;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Jaeha Son
 * @date 2019-12-13
 * @version 0.9
 * @description
 * 프로젝트 내에서 사용자 인증(로그인) 검사를 위해 공통적으로 사용되는 Interceptor 클래스
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

	/**
	 * Request 가 Controller 로 전달되기 전 호출되는 메소드
	 * @param request
	 *  HttpServletRequest 객체
	 * @param response
	 *  HttpServletResponse 객체
	 * @param handler
	 *  Object 타입 객체 (타입 검사가 필요함)
	 * @return boolean
	 *  인증 검사 성공 - true 반환
	 *  인증 검사 실패 - false 반환
	 * @throws Exception
	 */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("preHandle()");

		if (checkType(handler) == null) {
			// @LoginCheck 으로 지정된 로그인 검사가 없을 경우, 요청을 컨트롤러로 전달한다.
			return true;

		} else {
			// 로그인 검사 타입
			LoginCheck loginCheck = (LoginCheck) checkType(handler);

			// 로그인 검사 타입이 False 일 경우, 요청을 컨트롤러로 전달한다.
			if (loginCheck.type().equals(LoginCheck.Type.FALSE)) return true;
			User loggedUser = (User) request.getSession().getAttribute(LOGGED_USER);

			if (loggedUser == null) {
				logger.info("인증 검사: 해당 요청은 로그인이 필요합니다. 로그인 페이지로 이동합니다. (세션 없음)");
				alertMessage(response, "로그인이 필요합니다.", "/users/login");
				// response.sendRedirect(request.getContextPath() + "/users/login");
				return false;

			} else {
				logger.info("인증 검사: 해당 요청을 Controller 로 전달합니다. (세션 있음)");
				return true;
			}
		}
    }

	/**
	 * handler 객체의 타입을 확인하는 메소드 (handler 객체가 HandlerMethod 타입이 아닐 경우, 형변환 시 에러가 발생한다.)
	 * @param handler
	 *  HandlerMethod 객체
	 * @return LoginCheck
	 *  LoginCheck 객체
	 */
	private LoginCheck checkType(Object handler) {
		if (handler instanceof HandlerMethod) {
			// handler 객체가 HandlerMethod 타입일 경우, 메소드에 선언된 LoginCheck 어노테이션을 가져온다.
			return ((HandlerMethod) handler).getMethodAnnotation(LoginCheck.class);

		} else {
			return null;
		}
	}
}