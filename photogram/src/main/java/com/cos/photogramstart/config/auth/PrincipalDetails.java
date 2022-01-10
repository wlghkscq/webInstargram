package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User{

	private static final long serialVersionUID = 1L;
	
	private User user;
	private Map<String, Object> attributes;
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
	}


	// 권한 : 한개가 아닐수 있음 ( 3개이상의 권한 ) -> Collection
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { // 계정 권한 
		
		Collection<GrantedAuthority> collector = new ArrayList<>();
		collector.add(() -> {return user.getRole();});
		return collector;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() { // 계정 만료 여부 
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { // 계정 잠김 여부 
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { // 계정 갱신 여부 
		return true;
	}

	@Override
	public boolean isEnabled() { // 계정 활성화 여부 
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes; // 페이스북 scope {id=1979705588875391, name=진윤상, email=wlghkscq@naver.com}
	}

	@Override
	public String getName() {
		return (String)attributes.get("name");
	}

}
