package com.cos.security1.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Hazel-K
 * 
 * - 참고자료: https://ithub.tistory.com/24
 * DataBase와 직접적으로 통신하기 위한 VO를 만듬
 * Table user와 관련된 VO
 *
 */
@Entity // 서버 실행 시 해당 클래스와 같은 Table이 자동으로 생성된다.
@Data // Getter, Setter 및 toString을 자동으로 생성
@NoArgsConstructor // 기본 생성자를 생략하겠다는 어노테이션
public class User {
	@Id // 아이디 번호를 자동 생성
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성을 DB에 위임
	private int id; // 위의 두 어노테이션을 받아 id가 기본 키가 된다
	private String username;
	private String password;
	private String email;
	private String role;
	@CreationTimestamp // 타임스탬프를 자동으로 생성함
	private Timestamp createDate;
	
	// OAuth2 사용자를 구분을 위한 컬럼
	private String provider;
	private String providerId;
	
	// 빌더 패턴을 이용한 생성자
	@Builder
	public User(String username, String password, String email, String role, Timestamp createDate,
			String provider, String providerId) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.createDate = createDate;
		this.provider = provider;
		this.providerId = providerId;
	}
}