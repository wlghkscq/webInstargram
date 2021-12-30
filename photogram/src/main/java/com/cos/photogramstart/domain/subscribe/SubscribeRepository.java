package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {
	
	// 구독 하기 
	@Modifying // 데이터에 변경(insert, delete, update) 이 필요한 네이티브 쿼리문 작성시 필요 
	//nativeQuery = true 네이티브 쿼리문 활성화 (쿼리문 작성시 인식함) 
	@Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
	void mSubscribe(int fromUserId, int toUserId); // ->( :fromUserId, :toUserId) 파라미터를 의미 
	
	 // 구독 취소
	@Modifying
	@Query(value = "DELETE FROM subscribe WHERE  fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
	void mUnSubscribe(int fromUserId, int toUserId); // m은 my의 약자로 내가 만들었다는 의미! 
	
	// 구독여부 
	@Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :principalId AND toUserId =:pageUserId", nativeQuery = true)
	int mSubscribeState(int principalId, int pageUserId);
	
	
	// 구독 수 
	@Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :pageUserId", nativeQuery = true)
	int mSubscribeCount(int pageUserId);
	
	
	
	
}
