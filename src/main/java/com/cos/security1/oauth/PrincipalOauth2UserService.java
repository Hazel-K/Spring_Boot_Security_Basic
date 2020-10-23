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
	 */
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("userRequest:" + userRequest);
		System.out.println("userRequest:" + userRequest.getClientRegistration().getRegistrationId());
		System.out.println("userRequest:" + userRequest.getAccessToken().getTokenValue());
		System.out.println(super.loadUser(userRequest).getAttributes());
		return super.loadUser(userRequest);
	}
}
