package com.cos.photogramstart.domain.subscribe;

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
@Table( // 2개이상의 컬럼 유니크값 부여 
		uniqueConstraints = {
				@UniqueConstraint(
						name="subscribe_uk",
						columnNames = {"fromUserId", "toUserId"}
						)
		}
		)
public class Subscribe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호증가 전략이 사용하는 데이터베이스를 따라간다. 
	private int id;
	
	@JoinColumn(name="fromUserId") // 컬럼명 지정 
	@ManyToOne
	private User fromUser; // 구독하는 유저 many 
	
	@JoinColumn(name="toUserId")
	@ManyToOne
	private User toUser; // 구독 받는 유저 one 
	
	
	private LocalDateTime createDate;
	
	@PrePersist // 디비에 INSERT 되기 직전에 실행 
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
}
