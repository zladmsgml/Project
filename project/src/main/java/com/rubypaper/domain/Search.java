package com.rubypaper.domain;

import lombok.Data;

@Data	
public class Search {
	private String searchCondition;//title인지 content인지
	private String searchKeyword;//검색어
}
