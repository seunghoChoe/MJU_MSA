package com.springboot.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.springboot.demo.interceptor.AuthenticationInterceptor;
import java.io.IOException;

/**
 * Copyright(c) 2019 All rights reserved by MJU-Team in 19-2 Teamproject2-Class
 *
 * @author Jaeha Son
 * @date 2019-12-13
 * @version 0.9
 * @description
 * 웹 MVC 설정을 위한 Configuration 클래스
 */
@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// 아래 경로의 모든 JSP 파일을 뷰로 지정한다.
		registry.jsp("/WEB-INF/views/", ".jsp");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/")
				.setCachePeriod(3600);

		// 로컬 업로드 디렉토리 지정 (username 에 본인의 PC 계정 이름 입력)
		// registry.addResourceHandler("/upload/**")
		// 		   .addResourceLocations("file:///C:/Users/{username}/upload/");

		// 실서버 업로드 디렉토리 지정
		registry.addResourceHandler("/upload/**")
        		.addResourceLocations("file:///home/ubuntu/upload/")
				.setCachePeriod(3600);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthenticationInterceptor())

				// 파비콘 URI 경로 제외 및 검사 대상이 될 URI 경로 지정 (세부 URI 는 @LoginCheck 어노테이션을 사용한다.)
				.excludePathPatterns("**/favicon.ico")
				.addPathPatterns("/users/**")
				.addPathPatterns("/favorites/**")
				.addPathPatterns("/board/**")
				.addPathPatterns("/restaurants/**");
	}

	@Bean
	public RestTemplate restTemplate() throws Exception {
		// 커스텀 에러 핸들러가 없을 경우, 기본 핸들러(DefaultResponseErrorHandler)가 등록된다.
		// 기본 핸들러 클래스를 생성할 필요 없이 그대로 사용하여 메소드만 재정의한다.
		ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				// false 를 반환하는 것으로 재정의하여 getStatusCode 로 응답 코드 접근이 가능하도록 한다.
				// 없을 경우, Controller 에서 status 를 검사할 때, NPE 가 발생한다.
				return false;
			}
		};

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(errorHandler);
		return restTemplate;
	}

	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		// HTML Form 태그에서 PUT/DELETE 메소드를 지원하지 않으므로 필터를 등록한다.
		return new HiddenHttpMethodFilter();
	}
}