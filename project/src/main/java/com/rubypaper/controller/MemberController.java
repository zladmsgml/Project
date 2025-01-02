package com.rubypaper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rubypaper.domain.MemberDTO;
import com.rubypaper.service.MemberService;

@RestController
@RequestMapping("/members")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/join")
    public ResponseEntity<String> registerMember(@RequestBody MemberDTO memberDTO) {
	 	
        memberService.saveMember(memberDTO); 
        return ResponseEntity.ok("회원가입 성공");
    }	

}

//ResponseEntity<String> : 응답 본문을 스트링으로 반환
//@RequestBody : 요청 본문을 자바 객체로 변환, 클라이언트가 보내는 데이터를 자동으로 자바 객체로 매핑

//@Autowired는 **의존성 주입(Dependency Injection)**을 통해 MemberService 객체를 자동으로 주입하는 역할
//  Spring 컨테이너에서 **빈(Bean)**으로 관리되는 MemberService 객체를 주입하는 방식
