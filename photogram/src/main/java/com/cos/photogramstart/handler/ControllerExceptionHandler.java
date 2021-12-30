package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
@ControllerAdvice // 모든 Exception을 가로채온다 
public class ControllerExceptionHandler {
	
	@ExceptionHandler(CustomValidationException.class) // Runtime에서 발생되는 모든 Exception을 validationException()메소드가 가로채겠다.
	public String validationException(CustomValidationException e) {
		// CMRespDto, Script 비교
		// 1. 클라이언트에게 응답할 때는 Script 가 좋음
		// 2. Ajax 통신 - CMRespDto 
		// 3. Android 통신 - CMRespDto 
		if(e.getErrorMap() == null) {
			return Script.back(e.getMessage());
		}else {
			return Script.back(e.getErrorMap().toString()); // 전 페이지(signup 페이지)로 돌아가기 js리턴 
		}
		
	}
	 
	@ExceptionHandler(CustomException.class) // Runtime에서 발생되는 모든 Exception을 validationException()메소드가 가로채겠다.
	public String exception(CustomException e) {
		return Script.back(e.getMessage());
	}
	
	
	// 유효성 검사 예외 처리 
	@ExceptionHandler(CustomValidationApiException.class) // Runtime에서 발생되는 모든 Exception을 validationException()메소드가 가로채겠다.
	public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST); // 상태코드 전달
	}

	// 구독 예외 처리 
	@ExceptionHandler(CustomApiException.class) // Runtime에서 발생되는 모든 Exception을 validationException()메소드가 가로채겠다.
	public ResponseEntity<?> apiException(CustomApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST); // 상태코드 전달
	}
}
