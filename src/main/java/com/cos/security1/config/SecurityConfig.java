package com.cos.security1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 
 * @author Hazel-K
 * 
 * 이 어노테이션은 Configuration의 속성을 가짐과 동시에 스프링 시큐리티 필터가 필터체인에 등록될 수 있도록 해줌
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	/**
	 * 해당 메소드는 SpringSecurity 전반에 해당하는 사항을 재설정하기 위해 만들어졌음
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); // 사이트 위변조 방지를 해제함
		http.authorizeRequests() // 웹페이지마다의 권한을 설정
			.antMatchers("/user/**").authenticated() // user 계열은 login만 완료하면 이용 가능 
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // manager는 로그인 이후 magager나 admin의 권한이 있어야 접근 가능
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // admin은 로그인 이후 admin 권한이 있어야 접근 가능
			.anyRequest().permitAll() // 그 외 나머지 웹페이지는 그냥 접근 가능
			.and()
			.formLogin() // 로그인 폼을 사용하겠다는 뜻
			.loginPage("/login"); // 폼을 사용하는 페이지를 설정하겠다는 뜻
	}
}
