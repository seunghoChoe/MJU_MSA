package com.example.demo.user.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@EnableAspectJAutoProxy
//@ComponentScan("example")
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	//---중략---/

	//static 리소스 처리
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
		.addResourceLocations("/WEB-INF/resources/");

		registry.addResourceHandler("swagger-ui.html")
		.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars/");

	}
	 // CORS 설정 (웹 AJAX 호출 시, API 서버에서 접근을 허용하지 않는 크로스 도메인 이슈 처리)
	   @Override
	   public void addCorsMappings(CorsRegistry registry) {
	      registry.addMapping("/**")
	            .allowedOrigins("http://localhost:8080","http://52.78.148.181:8080", "http://13.125.99.51:8080", "http://52.78.148.181:9090"); // 로컬, 실서버 도메인 등록
	   }

}