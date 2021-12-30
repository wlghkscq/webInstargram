package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import java.util.List;
import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// JPA - Java Persistence API (자바로 데이터를 영구적으로 (DB에) 저장할수 있는 API제공) 

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // 디비 테이블 생성 
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호증가 전략이 사용하는 데이터베이스를 따라간다. 
	private int id;
	
	@Column(length = 20, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String name;
	
	private String website; // 웹사이트 
	
	private String bio; // 자기소개 
	
	@Column(nullable = false)
	private String email;
	
	private String phone;
	
	private String gender;
	
	private String profileImageUrl; // 프로필 사진 
	private String role; // 권한 
	
	@OneToMany(mappedBy = "user" , fetch = FetchType.LAZY) 
	// mappedBy -> 나는 연관관계의 주인이 아니다. 그러므로 테이블에 컬럼을 만들지마 
	// user를 Select 할 때 해당 User id로 등록된 image들을 다 가지고 와 
	// LAZY = user를 select 할때 해당 User id로 등록된 image들을 가져오지마 - 대신 getImages()함수의 image들이 호출 될때 가져와 
	// Eager = User 를 select 할 때 해당 User id로 등록된 image들을 전부 Join해서 가져와 
	@JsonIgnoreProperties({"user"}) // jpa 무한참조 방지 
	private List<Image> images; // 양방향 매핑 
	 
	private LocalDateTime createDate;
	
	@PrePersist // 디비에 INSERT 되기 직전에 실행 
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
}