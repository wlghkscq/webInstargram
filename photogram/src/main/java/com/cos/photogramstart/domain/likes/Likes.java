package com.cos.photogramstart.domain.likes;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // 디비 테이블 생성 
@Table( // 2개이상의 컬럼 유니크값 부여 
		uniqueConstraints = {
				@UniqueConstraint( 
						name="likes_uk",
						columnNames = {"imageId", "userId"} // 두개의 컬럼은 서로 중복 불가 
						)
		}
		)
public class Likes { // N
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호증가 전략이 사용하는 데이터베이스를 따라간다. 
	private int id;
	
	// 어떤 이미지를 누가 좋아요 했는가 
	
	//무한참조됨
	@JoinColumn(name = "imageId")
	@ManyToOne
	private Image image;  // 1
	
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name = "userId")
	@ManyToOne
	private User user; // 1
	
	private LocalDateTime createDate;
	
	@PrePersist // 디비에 INSERT 되기 직전에 실행 
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
}
