package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

/**
 * 
 * @author Hazel-K
 * 
 * @Controller 는 실제 JSP페이지인 View를 리턴하겠다는 의미를 가진 어노테이션
 *
 */
@Controller
public class IndexController {

	/**
	 * 
	 * @author Hazel-K
	 * 
	 * localhost:8080, localhost:8080/을 브라우저에서 입력하면
	 * ViewResolver를 거쳐 index.html를 보여줌.
	 * 현재 스프링에서 권장하고 있는 템플릿 메소드가 mustache이므로 mustache 사용
	 * mustache가 기본 설정된 폴더는 src/main/resources 이므로 return부분에 "src/main/resources/index.jsp"라고 일일이 적을 필요가 없음
	 * ViewResolver 설정 시 templates (prefix), mustache (suffix)로 설정해야 하나 mustache는 생략 가능
	 * 
	 */
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping({"", "/"})
	public String index() {
		return "index"; // 본 경로는 src/main/resources/template/index.mustache 이나 config/WebMVCConfig.java 파일로 인해 변경되었음
	}
	
	/**
	 * @ResponseBody 어노테이션은 해당 return 값을 웹페이지와 연결시키는 게 아니라, 바로 웹페이지에 동적으로 표시하기 위해 하는 것 
	 */
	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}
	
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	
	// Spring Security 가 해당 주소를 낚아채 미리 사용하기 때문에 설정을 따로 해주지 않는 이상 이것을 변경할 수 없음
	// SecurityConfig - Configure 가 완성된 이후에는 미리 낚아채는 행동을 하지 않음
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}
	
	@PostMapping("/join")
	public String join(User user) {
		// System.out.println(user);
		user.setRole("ROLE_USER"); // 해당 회원의 권한 설정
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword); // 스프링 시큐리티 비밀번호 엔코더를 이용한 비밀번호 설정
		userRepository.save(user); // 비밀번호를 암호화하면 시큐리티에서도 로그인이 가능해짐
		return "redirect:/loginForm"; // redirect하고 싶으면 붙임
	}
	
	// @Secured("ROLE_ADMIN") // 이 메소드는 관리자에게만 허용된다는 의미 EnableSecured 어노테이션 필요, 한 개만 주고 싶을때
	// @PreAutorize(hasRole('ROLE_MANAGER' or hasRole('ROLE_ADMIN'))) // 이 메소드는 매니저, 관리자에게만 허용된다는 의미 preAuthorize 어노테이션 필요, 여러 개 주고 싶을 때
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}
}