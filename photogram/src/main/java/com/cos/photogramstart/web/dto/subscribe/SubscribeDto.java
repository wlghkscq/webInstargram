package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {
	private int id;
	private String username;
	private String profileImageUrl; // 프로필 사진 
	private Integer subscribeState;
	private Integer equalUserState; // 	로그인한 유저와 동일 유저인지 구분하는 변수 
	
}
