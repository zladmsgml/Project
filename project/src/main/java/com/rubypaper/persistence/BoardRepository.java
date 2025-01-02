package com.rubypaper.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rubypaper.domain.Board;


public interface BoardRepository extends JpaRepository<Board, Long>{
	@Query("SELECT b FROM Board b")
	Page<Board> getBoardList(Pageable pageable);
	
	@Query("SELECT b FROM Board b where b.title like %:keyword%")
	Page<Board> getBoardListByTitle(String keyword, Pageable pageable);
	
	@Query("SELECT b FROM Board b where b.content like %:keyword%")
	Page<Board> getBoardListByContent(String keyword, Pageable pageable);
}
