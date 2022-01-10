package com.cos.photogramstart.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;

@Component // RestController, Service 모든 것들이 Compent를 상속해서 만들어져있음 
@Aspect
public class ValidatiomAdvice {
	
	@Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")
	public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		
		System.out.println("web api 컨트롤러===========================================");
		Object[] args = proceedingJoinPoint.getArgs();
		for (Object arg : args) {
			if(arg instanceof BindingResult) {
				System.out.println("유효성 검사를 하는 함수입니다.");
				BindingResult bindingResult = (BindingResult) arg;
				
				// @Valid 유효성 검사 전처리 과정
				// signupDto에 Valid 검사를 통과하지못하고 오류가 나오면 그 오류들을 bindingResult에 담는다 
				if(bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					
					// 그오류들을 for문을 돌면서 다시 errorMap 에 담는다 
					for(FieldError error: bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
						

					} // CustomValidationException에 "유효성 검사 실패함"라는 errorMessage와 유효성 오류들이 담긴 errorMap을 넘겨준다 
					throw new CustomValidationApiException("유효성 검사 실패함",errorMap);
				}
			}
		}
		// proceedingJoinPoint -> profile 함수의 모든 곳에 접근할 수 있는 변수
		// profile 함수보다 먼저 실행 
		return proceedingJoinPoint.proceed(); // profile 함수가 실행됨 
	}
	
	@Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
	public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		
		System.out.println("web 컨트롤러 ===========================================");
		Object[] args = proceedingJoinPoint.getArgs();
		for (Object arg : args) {
			if(arg instanceof BindingResult) {
				// @Valid 유효성 검사 전처리 과정
				// signupDto에 Valid 검사를 통과하지못하고 오류가 나오면 그 오류들을 bindingResult에 담는다 
				BindingResult bindingResult = (BindingResult) arg;
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
				}
			}
		}
		return proceedingJoinPoint.proceed();
	}
	
}
