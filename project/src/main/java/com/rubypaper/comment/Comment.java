package com.rubypaper.comment;

import java.util.Date;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nickname;
	private String content;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable=false)
	private Date createDate = new Date();
	
	@Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateDate;
	
}
