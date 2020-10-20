package com.cos.security1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author Hazel-K
 * 
 * 시작일: 2020. 10. 20.
 * - 참고자료: https://www.youtube.com/watch?v=GEv_hw0VOxE
 * - 사전 설정 의존성(Dependency)
 * Spring Boot DevTools - 소스 저장 시 자동 리로딩 기능 제공
 * Lombok - 클래스의 Getter, Setter 자동 설정
 * Spring Data JPA - ORM 사용
 * MySQL Driver - MySQL, Maria DB 사용하기 위한 드라이버
 * Spring Security - 로그인 절차 보안 기능 제공
 * Mustache - 템플릿 엔진
 * Spring Web - 웹과 관련된 어노테이션 사용 가능
 * 
 * 1. src/main/resources 폴더 아래에 application.properties 확장자를 yml로 변경 후 내용 추가(참고)
 * 2. src/main/java 폴더 아래 controller-IndexController.java에서 계속
 *
 */
@SpringBootApplication
public class Security1Application {

	public static void main(String[] args) {
		SpringApplication.run(Security1Application.class, args);
	}

}
