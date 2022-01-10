package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {
	
	private final ImageRepository imageRepository;
	
	@Transactional(readOnly = true)
	public List<Image> 인기사진(){
		return imageRepository.mPopular(); 
	}
	
	
	@Transactional(readOnly = true) // readOnly = true는 < 영속성 컨텍스트 변경 감지(더티체킹), 변경 0 -> flush로 반영 > 하는 작업 생략
	public Page<Image> 이미지스토리(int principalId, Pageable pageable){
		Page<Image> images = imageRepository.mStory(principalId, pageable);
		
		// 2(cos) 로그인 
		// images에 좋아요 상태 담기 
		images.forEach((image)->{
			
			image.setLikeCount(image.getLikes().size());
			
			image.getLikes().forEach((like) ->{
				if(like.getUser().getId() == principalId) { // 해당 이미지에 좋아요한 유저들을 찾아서 현재 로그인한 유저가 좋아요한것인지 비교 
					image.setLikeState(true);
				}
			});
		});
		
		return images;
	}
	
	
	@Value("${file.path}") // application.yml file path를 지정 
	private String uploadFolder;
	 
	@Transactional
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		UUID uuid  = UUID.randomUUID(); // 네트워크 상에서 고유성이 보장되는 id를 만들기 위한 표준 규약. 범용고유식별자 UUID (Universally Unique IDentifier) 
		String imageFileName = uuid+""+imageUploadDto.getFile().getOriginalFilename();
		System.out.println("이미지파일 이름 "+imageFileName);
		
		// 사진파일 저장 경로 
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		// 통신, I/O의 경우  -> 예외 발생 할수있음  
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		} catch (Exception e) { 
			e.printStackTrace();
		}
		
		// image 테이블에 저장 
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
		imageRepository.save(image);
		
		//System.out.println(imageEntity.toString());
	}
	
	
}
