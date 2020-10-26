package com.cos.security1.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

import lombok.Data;

/**
 * 
 * @author Hazel_K
 * 
 * 해당 클래스는 시큐리티가 로그인을 낚아채 진행시킬 때
 * 진행이 완료되면 로그인 정보를 세션에 넣게 되는데
 * Security ContextHolder라는 키 값에다가 로그인 정보를 저장한다.
 * 이때 저장할 수 있는 Object가 정해져있는데, 이 객체는 무조건 Authentication이어야 한다.
 * Authentication 안에는 User 정보가 있어야 하는데, 이 Class의 타입도 무조건 UserDetails 타입의 객체여야 한다.
 * 즉, Security Session => Authentication => UserDetails 형태로 저장이 된다는 것.
 *
 */
@Data
public class PrincipalDetails implements UserDetails{
	
	/**
	 * 본인이 정한 User 정보를 따로 적어준다.
	 * 필드와 함께 기본 생성자도 함께 만든다.
	 */
	private User user;
	
	public PrincipalDetails(User user) {
		// TODO Auto-generated constructor stub
		this.user = user;
	}
	
	/**
	 * 유저의 설정된 권한을 반환
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { // UserDetails 형태이므로, Authentication 안에 집어넣을 수 있다.
		// 해당 유저의 권한을 리턴하는 곳 권한의 타입은 GrantedAuthority이다.
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() { // String을 바로 넣을 수 없으므로 형태를 변환하는 과정을 거친다.
			@Override
			public String getAuthority() {
				return user.getRole(); // 미리 지정한 권한을 리턴한다.
			}
		});
		return collect;
	}
	
	/**
	 * 유저의 패스워드를 반환
	 */
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	/**
	 * 유저의 아이디를 반환
	 */
	@Override
	public String getUsername() {
		return user.getUsername();
	}

	/**
	 * 계정이 만료되었는지를 묻는 것
	 */
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 계정이 잠겼는지를 묻는 것
	 */
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 기간이 오래됐는지, 비밀번호를 너무 오래 사용했는지를 묻는 것
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	 * 계정이 활성화되었는지를 묻는 것
	 */
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		// 현재시간 - 로긴시간 = 조건시간이면 로그인 안되게 할 수 있음
		return true;
	}
}
