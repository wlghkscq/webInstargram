package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.validation.Valid; 

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {
	
	private final UserService userService;
	private final SubscribeService subscribeService;
	
	// 로그인한 유저를 구독한 유저 정보 모두 가져오기 
	@GetMapping("/api/user/{pageUserId}/subscribe")
	public ResponseEntity<?> subscribeList(@PathVariable int pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails ){
		
		List<SubscribeDto> subscribeDto = subscribeService.구독리스트(principalDetails.getUser().getId(), pageUserId);
		
		return new ResponseEntity<>(new CMRespDto<>(1, "구독자 정보 리스트 가져오기 성공", subscribeDto), HttpStatus.OK);
	}
	
	
	
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(
			@PathVariable int id, 
			@Valid UserUpdateDto userUpdateDto, 
			BindingResult bindingResult, // 꼭 @Valid가 적혀있는 다음 파라미터에 적어야함 
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
				// @Valid 유효성 검사 전처리 과정
				// signupDto에 Valid 검사를 통과하지못하고 오류가 나오면 그 오류들을 bindingResult에 담는다 
				if(bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					
					// 그오류들을 for문을 돌면서 다시 errorMap 에 담는다 
					for(FieldError error: bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
						

					} // CustomValidationException에 "유효성 검사 실패함"라는 errorMessage와 유효성 오류들이 담긴 errorMap을 넘겨준다 
					throw new CustomValidationApiException("유효성 검사 실패함",errorMap);
				}else { // 유효성검사를 통과하면 
					User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
					principalDetails.setUser(userEntity); // 세션정보 반영 (변경된 회원정보 ui에 반영 ) 
					return new CMRespDto<>(1, "회원수정완료", userEntity); // 응답시에 userEntity의 모든 getter 함수가 호출되고 JSON으로 파싱하여 응답한다. 
				}
		
		
	}
	
}
