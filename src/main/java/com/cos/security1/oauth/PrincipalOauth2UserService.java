package com.cos.security1.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.security1.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.oauth.provider.FacebookUserInfo;
import com.cos.security1.oauth.provider.GoogleUserInfo;
import com.cos.security1.oauth.provider.OAuth2UserInfo;
import com.cos.security1.repository.UserRepository;

/**
 * 
 * @author Hazel-K
 * 
 * 구글로부터 받은 userRequest 데이터에 대한 후처리를 담당하는 메소드이다
 * DefaultOAuth2UserService.loadUser()메소드는 굳이 구현하지 않아도 자동으로 로드가 되지만
 * 굳이 구현해주는 이유는 리턴 타입을 UserDetails가 아닌 principalDetails로 받기 위해서이다.
 */
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	/**
	 * 구글로부터 받은 여러 정보들을 확인할 수 있음
	 * - 구글 로그인 진행
	 * [구글 로그인 창] - [로그인 완료] - [code를 리턴(OAuth_Client라이브러리)] - [Access Token 요청] - [userRequest 정보로 loadUser() 호출] - [회원프로필 추출]
	 */
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	/*
	 * 함수 종료시 @AuthenticationPrincipal이 생성됨
	 */
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//		System.out.println("userRequest:" + userRequest);
		System.out.println("getClientRegistration:" + userRequest.getClientRegistration().getRegistrationId()); // 어떤 OAuth로 로그인했는지 확인 가능
		System.out.println("getAccessToken:" + userRequest.getAccessToken().getTokenValue()); // 토큰의 값을 받아온다. API요청을 주고받을때 주요 아이템
		
		OAuth2User oauth2User = super.loadUser(userRequest);
		System.out.println("getAttributes:" + super.loadUser(userRequest).getAttributes());
		
		// 로그인 요청에 따라 분기를 나눔
		OAuth2UserInfo oAuth2UserInfo = null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("구글 로그인 요청");
			oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
		} else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			System.out.println("페이스북 로그인 요청");
			oAuth2UserInfo = new FacebookUserInfo(oauth2User.getAttributes());
		} else {
			System.out.println("우리는 구글과 페이스북만 지원합니다.");
		}
		
		// 위에서 걸러진 정보를 각 값에 저장
		String provider = oAuth2UserInfo.getProvider();
		String providerId = oAuth2UserInfo.getProviderId();
		String username = provider + "_" + providerId; // google_sub
		String password = bCryptPasswordEncoder.encode("겟인데어");
		String email = oAuth2UserInfo.getEmail();
		String role = "ROLE_USER";
		
		// 가져온 정보가 이미 있는 회원인지를 비교
		User userEntity = userRepository.findByUsername(username);
		if(userEntity == null) { // 로그인을 한 이력이 없다면
			// @build 패턴을 통해 객체 내용 채우기
			userEntity = User.builder()
					.username(username)
					.password(password)
					.email(email)
					.role(role)
					.provider(provider)
					.providerId(providerId)
					.build();
			userRepository.save(userEntity); // repository를 이용하여 db에 저장
		}
		
		return new PrincipalDetails(userEntity); // oauth2user를 implement한 PrincipalDetails 리턴
	}
}
