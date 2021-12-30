package com.cos.photogramstart.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/user/{pageUserId}")
	public String profile(@PathVariable int pageUserId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		UserProfileDto dto = userService.회원프로필(pageUserId, principalDetails.getUser().getId());
		model.addAttribute("dto", dto);
		return "user/profile"; 
	}
	
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) { // @AuthenticationPrincipa~  세션 접근 
		// 1. 추천  
		//System.out.println("세션 정보 "+ principalDetails.getUser());
		
		
		
		// 1번이 세션을 찾는 구체적 과정 (비추) 
//		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
//		PrincipalDetails mPrincipaDetails = (PrincipalDetails) auth.getPrincipal(); 
//		System.out.println("직접 찾은 세션 정보 : "+mPrincipaDetails.getUser());
		
		
		return "user/update";
	}
	
}
