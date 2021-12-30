package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // 디비 테이블 생성 
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호증가 전략이 사용하는 데이터베이스를 따라간다. 
	private int id;
	private String caption; // 사진 세부 설명 
	private String postImageUrl; // 사진을 전송받아 그 사진을 서버에 특정폴더에 저장 - db에 그 저장된 경로를 insert 
	
	@JoinColumn(name="userId")
	@ManyToOne
	private User user; 
	
	// 이미지 좋아요 
	
	// 댓글 
	
	private LocalDateTime createDate;
	
	@PrePersist // 디비에 INSERT 되기 직전에 실행 
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

	// 오브젝트를 콘솔에 출력할때 무한참조오류 발생 User부분을 출력되지않게 지워둠 
//	@Override
//	public String toString() {
//		return "Image [id=" + id + ", caption=" + caption + ", postImageUrl=" + postImageUrl 
//				+ ", createDate=" + createDate + "]";
//	}
	
}
