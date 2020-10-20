package com.cos.security1.config;

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
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		
	}
}
