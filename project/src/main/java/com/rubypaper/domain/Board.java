package com.rubypaper.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity	
public class Board {
	@Id
	@GeneratedValue
	private Long seq;
	private String title;
	private String content;
	
	@Temporal(TemporalType.TIMESTAMP)//날짜와 시간 모두 저장
	@Column(updatable=false) //엔티티 변경되어도 필드 값은 변경되지 않음.
	private Date createDate = new Date();
	
	@Column(updatable = false)
	private Long cnt = 0L;
	
	@ManyToOne
	@JoinColumn(name="MEMBER_ID", nullable=false, updatable=false)
	private Member member;
	
	public void setMember(Member member) {
		this.member = member;
		member.getBoardList().add(this);
	}
		
}
