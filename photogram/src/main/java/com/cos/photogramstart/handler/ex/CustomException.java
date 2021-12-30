package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomException extends RuntimeException{
	
	// 객체를 구분하는 시리얼 넘버 
	private static final long serialVersionUID = 1L;
	
	// 생성자 
	public CustomException( String message) {
		super(message);
	}
	
}
