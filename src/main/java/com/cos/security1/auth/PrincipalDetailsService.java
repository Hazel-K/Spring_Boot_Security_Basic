package com.cos.security1.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

/**
 * 
 * @author Hazel_K
 * 
 * 이 클래스는 PrincipaDetails에서 만들어진 UserDetails 정보를 Authentication에 저장하기 위해 사용
 * @Service 어노테이션은 보통 이 클래스가 Controller의 직접적인 논리를 처리하는 Service 영역이라는 것을 알리는 것이지만
 * 시큐리티 설정에서 loginProcessingUrl("/login")이라고 설정했기 때문에
 * 로그인 요청이 오면 자동으로 UserDetailsService 타입으로 IoC된 loadUserByUsername 함수가 실행됨
 * UserDetailsService.loadUserByUsername()메소드는 굳이 구현하지 않아도 자동으로 로드가 되지만
 * 굳이 구현해주는 이유는 리턴 타입을 UserDetails가 아닌 principalDetails로 받기 위해서이다.
 *
 */
@Service
public class PrincipalDetailsService implements UserDetailsService{
	/**
	 * 일반적인 MVC 구조의 Mapper에 해당하는 영역
	 */
	@Autowired
	private UserRepository userRepository;

	/**
	 * 아이디를 통해 User 정보를 불러오는 함수
	 * 만일 로그인 폼에 아이디를 받는 인풋 태그의 name이 username이 아니라면 인식 안하니까 참고
	 * 부득이하게 바꿀 경우, SecrurityConfig - configure 함수에서 usernameParameter를 String 형식으로 변경. 
	 * ex) .usernameParameter("username2");
	 * 함수 종료시 @AuthenticationPrincipal이 생성됨
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = userRepository.findByUsername(username); // findByUsername이란 메소드가 userRepository에 없으므로 새롭게 만들어야 함
		if(userEntity != null) {
			return new PrincipalDetails(userEntity); // 리턴되는 장소는 Authentication 내부.
			// Security Session > Authentication > UserDetails
		}
		return null;
	}
	
}
