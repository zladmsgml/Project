package com.rubypaper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rubypaper.domain.Member;
import com.rubypaper.domain.MemberDTO;
import com.rubypaper.persistence.MemberRepository;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memRepo;
	
	PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public void saveMember(MemberDTO memberDTO) {
		Member member = new Member();
		member.setUserid(memberDTO.getUserid());
		member.setPassword(encoder.encode(memberDTO.getPassword()));
		member.setNickname(memberDTO.getNickname());
		//member.setEnabled(true);
		memRepo.save(member);
	}

}
