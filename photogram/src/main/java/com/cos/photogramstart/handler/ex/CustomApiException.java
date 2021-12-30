package com.cos.photogramstart.handler.ex;

import java.util.Map;

// 구독 예외처리 
public class CustomApiException extends RuntimeException{
	
	// 객체를 구분하는 시리얼 넘버 
	private static final long serialVersionUID = 1L;
	
	// 생성자 
	public CustomApiException( String message) {
		super(message);
	}
	
}
