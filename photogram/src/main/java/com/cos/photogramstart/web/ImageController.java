package com.cos.photogramstart.web;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ImageController {
	
	private final ImageService imageService;
	
	@GetMapping({"/", "/image/story"})
	public String story() {
		return "image/story";
	}
	
	@GetMapping( "/image/popular")
	public String popular(Model model) {
		// api는 데이터를 리턴하는 서버 그래서 ajax의 경우 ApiController를 사용 
		// api를 쓰는 이유는 데이터를 브라우저에서 요청하는 게 아니라 안드로이드, ios에서 요청하는 경우 사용 
		// 인기사진은 웹브라우저에 페이지를 리턴하는 거기 떄문에 model에 데이터를 담기만 하면 되기때문에 controller로 만듬 
		List<Image> images = imageService.인기사진(); 
		
		model.addAttribute("images", images);
		
		return "image/popular";
	}
	
	@GetMapping( "/image/upload")
	public String upload() {
		return "image/upload";
	}
	
	@PostMapping("/image")
	public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if(imageUploadDto.getFile().isEmpty()) {
			throw new CustomValidationException("이미지파일일 첨부되지 않았습니다.", null);
		}
		
		// 서비스 호출 
		imageService.사진업로드(imageUploadDto, principalDetails);
		return "redirect:/user/"+principalDetails.getUser().getId();
		
	}
	
	
}
