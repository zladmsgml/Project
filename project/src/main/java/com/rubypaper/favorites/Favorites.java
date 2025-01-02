package com.rubypaper.favorites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Favorites {
	
	@Id //Primary Key(기본 키)
	@GeneratedValue(strategy = GenerationType.IDENTITY) //기본 키의 값을 자동 생성 //strategy 속성을 통해 자동 생성 방법을 지정 // Auto Increment 기능과 유사
	private Long id;
	
	@JoinColumn(name = "user_id", nullable = false)
	private String userId;
	private int dataNo;
}
