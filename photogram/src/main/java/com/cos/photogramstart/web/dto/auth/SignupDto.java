package com.cos.photogramstart.web.dto.auth;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data // Getter, Setter 
public class SignupDto { // DataTransferObject (회원가입)통신할 때 필요한 데이터를 담아두는 오브젝트 
	// @Valid 종류 https://bamdule.tistory.com/35
	
	
	@Size(min=2, max =20)
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String name;
	
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.build();
	}
	
	
	
}
