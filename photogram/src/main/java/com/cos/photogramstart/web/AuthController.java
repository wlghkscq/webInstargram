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
		
		// @Valid 유효성 검사 전처리 과정
		// signupDto에 Valid 검사를 통과하지못하고 오류가 나오면 그 오류들을 bindingResult에 담는다 
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			
			// 그오류들을 for문을 돌면서 다시 errorMap 에 담는다 
			for(FieldError error: bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("===========================");
				System.out.println(error.getDefaultMessage());
				System.out.println("===========================");

			} // CustomValidationException에 "유효성 검사 실패함"라는 errorMessage와 유효성 오류들이 담긴 errorMap을 넘겨준다 
			throw new CustomValidationException("유효성 검사 실패함",errorMap);
		}else { // 유효성 검사를 통과하면 회원가입 메소드 실행 
				// User <- signupDto  
				User user = signupDto.toEntity();		
				User userEntity = authService.회원가입(user);
				System.out.println(userEntity);
				return "auth/signin";
			
		}
	}
}
