package com.springboot.demo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
import java.util.Arrays;
import java.util.Collections;

/**
 * @Class: 웹 MVC 설정 클래스
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

	// 에러 페이지 참고 (https://frontdev.tistory.com/entry/SpringSpring-Boot-Tiles)

	/**
	 * @Method: JSP 뷰 리졸버 설정
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/views/", ".jsp");
	}

	/**
	 * @Method: 정적 리소스 설정
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/");

		// 로컬
//		registry.addResourceHandler("/upload/**")
//				.addResourceLocations("file:///C:/Users/amelon/upload/");

		// 실서버
		registry.addResourceHandler("/upload/**")
        		.addResourceLocations("file:///home/ubuntu/upload/");
	}

	/**
	 * @Method: 인터셉터 설정
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(new AuthenticationInterceptor())
		//		.addPathPatterns("/*");
	}

	/**
	 * @Method: REST 템플릿 빈 등록
	 * @Memo: 응답 에러 처리 (https://stackoverflow.com/questions/50239209/spring-resttemplate-how-to-reach-state-to-check-is4xxclienterror-instead-of-r)
	 */
	@Bean
	public RestTemplate restTemplate() throws Exception {
		// 커스텀 에러 핸들러가 없을 경우, 기본 핸들러(DefaultResponseErrorHandler)가 등록된다.
		// 기본 핸들러 클래스를 생성할 필요 없이 그대로 사용하여 메소드만 재정의한다.
		ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				// false 를 반환하는 것으로 재정의하여 getStatusCode 로 응답 코드 접근이 가능하도록 한다.
				return false;
			}
		};

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(errorHandler);
		return restTemplate;
	}

	/**
	 * @Method: 클라이언트 PUT/DELETE 필터 빈 등록
	 */
	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}
//	@Bean
//	public FilterRegistrationBean hiddenHttpMethodFilter() {
//		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//		filterRegistrationBean.setUrlPatterns(Collections.singletonList("/*"));
//		return filterRegistrationBean;
//	}
}