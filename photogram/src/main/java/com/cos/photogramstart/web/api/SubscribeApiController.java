package com.cos.photogramstart.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class SubscribeApiController {
	
	
	private final SubscribeService subscribeService;
	
	// 구독하기 
	@PostMapping("/api/subscribe/{toUserId}") // 누가 구독 받는지 
	public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int toUserId){  // 로그인한 사람이 구독한다.
		subscribeService.구독하기(principalDetails.getUser().getId(), toUserId);
		return new ResponseEntity<>(new CMRespDto<>(1, "구독완료", null),HttpStatus.OK);
	}
	
	// 구독취소
	@DeleteMapping("/api/subscribe/{toUserId}") 
	public ResponseEntity<?> unSubscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int toUserId){
		subscribeService.구독취소하기(principalDetails.getUser().getId(), toUserId	);
		return new ResponseEntity<>(new CMRespDto<>(1, "구독취소완료", null),HttpStatus.OK);
	}
}
