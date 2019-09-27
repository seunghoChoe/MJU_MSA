package com.spring.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MyInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(MyInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		    throws Exception {
		
			logger.info("preHandle");
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			logger.info("getBean : " + handlerMethod.getBean());
			logger.info("getBeanType: " + handlerMethod.getBeanType());
			logger.info("getClass: " + handlerMethod.getClass());
			logger.info("getMethod : " + handlerMethod.getMethod());
			logger.info("getParameters-length : " + handlerMethod.getMethodParameters().length);
			logger.info("returntype-parametername : " + handlerMethod.getReturnType().getParameterName());
			logger.info("returntype-method : " + handlerMethod.getReturnType().getMethod());
			return true;
		}
	
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		
		logger.info("postHandle");

	}

	/**
	 * This implementation is empty.
	 */
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		logger.info("afterCompletion");

	}
	

}
