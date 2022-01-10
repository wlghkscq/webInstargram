package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final에 대한 모든 생성자 생성 DI 
@Controller // 1. IoC  2. 파일을 리턴하는 컨트롤러 
public class AuthController {	
	
	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	private final AuthService authService;
	
//	public AuthController(AuthService authService) {
//		this.authService = authService;
//	}
	
	
	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin"; 
	}
	
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup"; 
	}
	  
	// 회원가입 버튼 -> /auth/signup -> /auth/signin
	@PostMapping("/auth/signup")
	public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {
		
				// User <- signupDto  
				User user = signupDto.toEntity();		
				authService.회원가입(user);
				//System.out.println(userEntity);
				
				// 로그를 남기는 후처리 
				return "auth/signin";
			
	}
}
