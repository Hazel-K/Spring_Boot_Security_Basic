package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
	 * ViewResolver를 거쳐 index.jsp를 보여줌.
	 * 현재 스프링에서 권장하고 있는 템플릿 메소드가 mustache이므로 mustache 사용
	 * mustache가 기본 설정된 폴더는 src/main/resources 이므로 return부분에 "src/main/resources/index.jsp"라고 일일이 적을 필요가 없음
	 * ViewResolver 설정 시 templates (prefix), mustache (suffix)로 설정해야 하나 mustache는 생략 가능
	 * 
	 */
	@GetMapping({"", "/"})
	public String index() {
		return "index"; // 본 경로는 src/main/resources/template/index.mustache 이나 config/WebMVCConfig.java 파일로 인해 변경되었음
	}
}