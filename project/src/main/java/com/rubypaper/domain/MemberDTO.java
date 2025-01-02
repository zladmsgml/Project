package com.rubypaper.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDTO {
	private String userid;
	private String password;
	private String nickname;
}

