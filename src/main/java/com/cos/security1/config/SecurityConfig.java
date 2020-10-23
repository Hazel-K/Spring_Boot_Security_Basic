package com.cos.security1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.security1.oauth.PrincipalOauth2UserService;

/**
 * 
 * @author Hazel-K
 * 
 * 이 어노테이션들은 Configuration의 속성을 가짐과 동시에 스프링 시큐리티 필터가 필터체인에 등록될 수 있도록 해줌
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화 preAuthorize 어노테이션 활성화
// preAuthorize + postAuthorize
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
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
			.and()// http 다시 시작
			.formLogin() // 로그인 폼을 사용하겠다는 뜻
			.loginPage("/loginForm") // 로그인 페이지를 설정
			.loginProcessingUrl("/login") // 주소 호출 시 시큐리티가 대신 로그인 진행
			.defaultSuccessUrl("/") // 로그인 성공 시 이동할 주소
			.and()
			.oauth2Login() // oauth2 로그인을 하기 위해 필요한 메소드
			.loginPage("/loginForm") // oauth2로그인 시 보여줄 로그인 페이지
			// oauth2 로그인 과정: 코드받기(인증) - 엑세스토큰(권한) - 프로필받기 + 프로필로 회원가입 진행 - 회원가입 진행 시 추가적 구성 입력
			// 구글 로그인이 완료되면 엑세스 토큰과 사용자 프로필 정보를 한번에 받을 수 있음
			.userInfoEndpoint()
			.userService(principalOauth2UserService);
	}
	
	/**
	 * 해당 메소드는 회원가입 / 로그인 시 비밀번호를 스프링 시큐리티의 형태에 맞게 암호화하는 작업을 돕기 위해 만들어졌음
	 * @Bean 어노테이션은 해당 메소드의 리턴값을 IoC에 등록해주는 역할을 함.
	 */
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
}
