package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableWebSecurity // 해당 파일로 시큐리티 활성화 
@Configuration //IoC    
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
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
			.defaultSuccessUrl("/"); // 성공적으로 signin(로그인)하면 "/" 주소로 보내겠다 
	}
}
