package com.rubypaper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rubypaper.domain.Member;
import com.rubypaper.persistence.MemberRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
	
	@Autowired
	private MemberRepository memRepo;

	@Override
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
		Member member = memRepo.findByUserid(userid).orElseThrow(()->new UsernameNotFoundException("Not Found!"));
		return new User(member.getUserid(), member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
	} //문자열 형태의 권한 목록을 GrantedAuthority 객체 목록으로 변환

}
