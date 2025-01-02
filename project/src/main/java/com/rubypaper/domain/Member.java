package com.rubypaper.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rubypaper.domain.Board;
import com.rubypaper.favorites.Favorites;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)// JPA가 자동으로 값을 생성 //데이터베이스가 생성한 값을 조회하여 엔터티의 기본 키로 설정
	private Long id;
	@Column(unique = true)//중복입력안되게
	private String userid;
	private String password;
	@Column(unique = true)
	private String nickname;
	@Enumerated(EnumType.STRING)
	@Builder.Default
	private Role role = Role.ROLE_MEMBER;
	
	@JsonIgnore
	@ToString.Exclude
	@OneToMany(mappedBy="member", fetch=FetchType.EAGER) //양방향관계에서 주인이 아니다, 멤버조회시 보드 내용 즉시 가져옴
	@Builder.Default
	private List<Board> boardList = new ArrayList<Board>();
	
	//@ToString.Exclude
	//@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
   // private List<Favorites> favorites = new ArrayList<>(); // 즐겨찾기 목록
	
	public boolean isEnabled() {
		return true;
	}
}
