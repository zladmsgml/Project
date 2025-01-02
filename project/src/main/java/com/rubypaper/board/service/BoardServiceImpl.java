package com.rubypaper.board.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rubypaper.domain.Board;
import com.rubypaper.domain.Search;
import com.rubypaper.persistence.BoardRepository;

@Service
public class BoardServiceImpl implements BoardService {
	
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Override
	public void insertBoard(Board board) {
		boardRepo.save(board);
	}

	@Override
	public void updateBoard(Board board) {
		Board findBoard = boardRepo.findById(board.getSeq()).get();
		
		findBoard.setTitle(board.getTitle());
		findBoard.setContent(board.getContent());
		boardRepo.save(findBoard);
	}

	@Override
	public void deleteBoard(Long seq) {
		try {
	        Optional<Board> board = boardRepo.findById(seq);
	        boardRepo.delete(board.get());
	    } catch (EmptyResultDataAccessException e) {
	        throw new IllegalArgumentException("게시글이 존재하지 않습니다.");
	    }
	}
	
//	@Override
//	public void deleteBoard(Board board) {
//		try {
//	        boardRepo.delete(board);
//	    } catch (EmptyResultDataAccessException e) {
//	        throw new IllegalArgumentException("게시글이 존재하지 않습니다.");
//	    }
//	}

	@Override
	public Board getBoard(Board board) {		
		return boardRepo.findById(board.getSeq()).get();
	}
	
	@Override
	public Page<Board> getBoardList(Pageable pageable) {
	    return boardRepo.getBoardList(pageable);
	}

	@Override
	public Page<Board>getBoardList(Search search) {
		Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "seq");		
		if(search.getSearchCondition().equals("TITLE")) {
			return boardRepo.getBoardListByTitle(search.getSearchKeyword(), pageable);
		}else if(search.getSearchCondition().equals("CONTENT")) {
			return boardRepo.getBoardListByContent(search.getSearchKeyword(), pageable);
		}
		return Page.empty();
	}
	
	public Board findBySeq(Long seq) {
		Optional<Board> board = boardRepo.findById(seq);
		return board.get();
	}

}
