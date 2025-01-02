package com.rubypaper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.rubypaper.domain.MemberDTO;
import com.rubypaper.service.MemberService;

@Controller
public class LoginController {
	@Autowired
	private MemberService memberService;
		
}
