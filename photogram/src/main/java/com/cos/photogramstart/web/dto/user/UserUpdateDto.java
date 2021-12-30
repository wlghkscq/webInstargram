package com.cos.photogramstart.web.dto.user;

import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {
	
	@NotBlank
	private String name; // 필수 입력값 
	
	@NotBlank
	private String password; // 필수 입력값
	private String website;
	private String bio;
	private String phone; 
	private String gender;
	
	public User toEntity() {
		return User.builder()
				.name(name) // 미입력시 문제 -> validation 체크 
				.password(password) // 패스워드를 미입력시 null 값으로 db에 저장 -> 따라서 validation 체크 
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
}
