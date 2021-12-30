package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service // 1. IoC  2. 트랜잭션 관리  
public class AuthService {
	
	private final UserRepository userRepository;
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional // Write (insert, update, delete) 
	public User 회원가입(User user) {
		// 회원가입 진행 
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 해쉬 암호화 완료 
		user.setPassword(encPassword); // 해쉬암호화된 pw로 변경 
		user.setRole("ROLE_USER"); // USER로 권한 설정 
		
		User userEntity =  userRepository.save(user);
		return userEntity;
		
	}

}
