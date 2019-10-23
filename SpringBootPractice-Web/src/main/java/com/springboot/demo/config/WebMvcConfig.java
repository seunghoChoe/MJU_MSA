package com.springboot.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.springboot.demo.interceptor.AuthenticationInterceptor;

/**
 * @Class: 웹 MVC 설정 클래스
 */
@Configuration
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
		registry.addResourceHandler("/resources/")
				.addResourceLocations("/resources/**");
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
	 */
	@Bean
	public RestTemplate restTemplate() throws Exception {
		return new RestTemplate();
	}
}