package com.rubypaper.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rubypaper.domain.Board;
import com.rubypaper.domain.Search;

public interface BoardService {
	void insertBoard(Board board);
	void updateBoard(Board board);
	void deleteBoard(Long seq);
	Board getBoard(Board board);
	Page<Board> getBoardList(Search search);
	Page<Board> getBoardList(Pageable pageable);
	Board findBySeq(Long seq);
}
