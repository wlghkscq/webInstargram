package com.cos.photogramstart.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.Subscribe;
import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;
import javax.persistence.Query; 

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	private final EntityManager em; // Repository는 EntityManger를 구현해서 만들어져 있는 구현체 
	
	
	@Transactional(readOnly = true)
	public List<SubscribeDto> 구독리스트(int principalId, int pageUserId){
		// 쿼리 준비 
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
		sb.append("if((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id), 1, 0) subscribeState, "); // ? -> principalId
		sb.append("if((?=u.id), 1, 0) equalUserState "); // ? -> 로그인한 아이디 principalId
		sb.append("FROM user u INNER JOIN subscribe s ");
		sb.append("ON u.id = s.toUserId ");
		sb.append("WHERE s.fromUserId = ?"); // ; 세미클론 꼭 삭제 // 마지막 ? pageUserId
		
		// 네이티브 쿼리문 작성 
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1, principalId)
				.setParameter(2, principalId)
				.setParameter(3, pageUserId);
		
		// 쿼리 실행 (	JpaResultMapper의 qlrm 라이브러리 필요 = Dto에 DB 결과를 매핑하기 위해서)
		JpaResultMapper result = new JpaResultMapper();
		List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);
				
		
		return subscribeDtos;  // 구독리스트 리턴 
	}
	
	
	@Transactional
	public void 구독하기(int fromUserId, int toUserId) {
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);
		} catch (Exception e) {
			throw new CustomApiException("이미 구독하신 유저입니다.");
		}
		 
		
	}
	
	@Transactional
	public void  구독취소하기(int fromUserId, int toUserId) {
		 subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}

}
