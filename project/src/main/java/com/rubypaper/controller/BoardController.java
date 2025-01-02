package com.rubypaper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rubypaper.board.service.BoardService;
import com.rubypaper.domain.Board;
import com.rubypaper.domain.Search;
import com.rubypaper.service.SecurityUser;

@RestController
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	

	@RequestMapping(value="/getBoardList",method = {RequestMethod.GET, RequestMethod.POST})//get,post 둘다 처리.
	public Page<Board> getBoardList(Search search){
		if(search.getSearchCondition()==null)
			search.setSearchCondition("TITLE");
		if(search.getSearchKeyword()==null)
			search.setSearchKeyword("");
		Page<Board> boardList = boardService.getBoardList(search);
		return boardList;
	}
	
	@GetMapping("/getBoard")
	public Board getBoard(Board board) {
		return boardService.getBoard(board);
	}		
	
	@PostMapping("/insertBoard")
	public Board insertBoard(@RequestBody Board board, @AuthenticationPrincipal SecurityUser principal) {
		board.setMember(principal.getMember());
		boardService.insertBoard(board);
		return boardService.getBoard(board);
	}
	
	@PostMapping("/updateBoard")
	public ResponseEntity<?> updateBoard(@RequestBody Board board, @AuthenticationPrincipal SecurityUser principal) {
		Board currentBoard = boardService.getBoard(board);
		
		if (!currentBoard.getMember().getUserid().equals(principal.getMember().getUserid())) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
	    }
		board.setMember(principal.getMember());
		boardService.updateBoard(board);
		return ResponseEntity.ok(board);
	}
	
	@GetMapping("/deleteBoard")
	public ResponseEntity<?> deleteBoard(@RequestParam Long seq, @AuthenticationPrincipal SecurityUser principal, Pageable pageable) {
	    try {
	    	Board currentBoard = boardService.findBySeq(seq);
	    	if (!currentBoard.getMember().getUserid().equals(principal.getMember().getUserid())) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
	        }
	        System.out.println("삭제 요청");
	        boardService.deleteBoard(seq);       
	        Page<Board> boardList = boardService.getBoardList(pageable);
	        return ResponseEntity.ok(boardList);  // 상태 코드 200 OK와 함께 반환
	    } catch (Exception e) {
	        // 오류가 발생하면 500 상태 코드와 함께 실패 메시지 반환
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}		
//	@GetMapping("/deleteBoard")
//	public ResponseEntity<?> deleteBoard(Board board, @AuthenticationPrincipal SecurityUser principal, Pageable pageable) {
//	    try {
//	    	Board currentBoard = boardService.getBoard(board);
//	    	if (!currentBoard.getMember().getUserid().equals(principal.getMember().getUserid())) {
//	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
//	        }
//	        System.out.println("삭제 요청");
//	        boardService.deleteBoard(board);        
//	        Page<Board> boardList = boardService.getBoardList(pageable);
//	        return ResponseEntity.ok(boardList);  // 상태 코드 200 OK와 함께 반환
//	    } catch (Exception e) {
//	        // 오류가 발생하면 500 상태 코드와 함께 실패 메시지 반환
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//	    }
//	}
}
