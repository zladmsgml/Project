package com.rubypaper.favorites;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rubypaper.service.SecurityUser;

@RestController
@RequestMapping("/member/favorites")
public class FavoritesController {
	
	@Autowired
	private FavoritesService favoserv;
	
	@PostMapping("/insertFavo")//userId, dataNo 받음
	public ResponseEntity<String> insertFavorites(@RequestBody FavoritesDTO favoDTO) { 
		
		favoserv.saveFavo(favoDTO);
		return ResponseEntity.ok("즐겨찾기에 추가되었습니다.");
	}
	
	@GetMapping("/getFavo")//userId 받음
	public List<Favorites> getFavorites(String userId) {
		return favoserv.getFavo(userId);//즐겨찾기 목록 반환
	}
	
	@GetMapping("/deleteFavo")//userId, dataNo 받음
	public ResponseEntity<String> deleteFavorites(@AuthenticationPrincipal SecurityUser principal, @RequestBody FavoritesDTO favoDTO){
		
		String userId = principal.getUsername();
		favoserv.deleteFavo(userId,favoDTO);
		return ResponseEntity.ok("즐겨찾기에서 삭제되었습니다.");
	}
}
