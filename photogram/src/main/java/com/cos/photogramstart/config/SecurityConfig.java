package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity // 해당 파일로 시큐리티 활성화 
@Configuration //IoC    
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final OAuth2DetailsService oAuth2DetailsService;
	
	
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super 삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화 됨 . 
		http.csrf().disable();  // csrf 토큰 비활성화   
		http.authorizeRequests()
			.antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**", "/api/**").authenticated() // 괄호안 주소로 요청하면 인증이 필요하다 
			.anyRequest().permitAll() // 그 외 주소요청은 인증없이 허가 
			.and()
			.formLogin() 
			.loginPage("/auth/signin") // 인증이 필요한 anyMatchers 의 주소로 요청하면 /auth/signin(GET)으로 보내겠다 
			.loginProcessingUrl("/auth/signin") // POST -> 스프링시큐리티가 로그인 프로세스 PrincipalDetailsService에서 진행 
			.defaultSuccessUrl("/")
			.and()
			.oauth2Login() // form 로그인도 하고, oauth2 로그인도 할거야
			.userInfoEndpoint() // oauth2 로그인을 하면 최종응답을 회원정보로 바로 받게해줘
			.userService(oAuth2DetailsService); // 
	}
}
