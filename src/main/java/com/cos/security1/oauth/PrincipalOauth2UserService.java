package com.cos.security1.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Hazel-K
 * 
 * 구글로부터 받은 userRequest 데이터에 대한 후처리를 담당하는 메소드이다
 *
 */
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	/**
	 * 구글로부터 받은 여러 정보들을 확인할 수 있음
	 * - 구글 로그인 진행
	 * [구글 로그인 창] - [로그인 완료] - [code를 리턴(OAuth_Client라이브러리)] - [Access Token 요청] - [userRequest 정보로 loadUser() 호출] - [회원프로필 추출]
	 */
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("userRequest:" + userRequest);
		System.out.println("getClientRegistration:" + userRequest.getClientRegistration().getRegistrationId()); // 어떤 OAuth로 로그인했는지 확인 가능
		System.out.println("getAccessToken:" + userRequest.getAccessToken().getTokenValue()); // 토큰의 값을 받아온다. API요청을 주고받을때 주요 아이템
		System.out.println("getAttributes:" + super.loadUser(userRequest).getAttributes());
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		return super.loadUser(userRequest);
	}
}
