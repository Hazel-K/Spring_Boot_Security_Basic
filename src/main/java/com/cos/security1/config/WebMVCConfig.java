package com.cos.security1.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * @author Hazel-K
 * 
 * @Configuration 어노테이션은 이 클래스가 어플리케이션과 통신하기 위한 Bean을 제공한다는 것을 의미함
 *
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
	
	/**
	 * 해당 메소드는 웹페이지 파일을 Controller에서 View로 밀어주는 Mustache 의존성을 재설정하기 위해 만들어졌음
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		MustacheViewResolver resolver = new MustacheViewResolver();
	    resolver.setCharset("UTF-8"); // 문자는 UTF-8로 설정
	    resolver.setContentType("text/html;charset=UTF-8"); // 웹페이지 컨텐트 타입 설정
	    resolver.setPrefix("classpath:/templates/"); // 사전 경로 설정, classpath는 해당 프로젝트의 경로를 의미
	    resolver.setSuffix(".html"); // 확장자 설정

	    registry.viewResolver(resolver); // 설정을 바탕으로 viewResolver 실행
	}
}
