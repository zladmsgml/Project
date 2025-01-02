package com.rubypaper.favorites;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoritesService {
	
	@Autowired
	private FavoritesRepository favorRepo;

	public void saveFavo(FavoritesDTO favoDTO) {
		Favorites fav = new Favorites();
		fav.setUserId(favoDTO.getUserId());
		fav.setDataNo(favoDTO.getDataNo());
		favorRepo.save(fav);
	}
	
	public List<Favorites> getFavo(String userId) {
		return favorRepo.getFavoritesByUserId(userId);
	}
	
	public void deleteFavo(String userId ,FavoritesDTO favoDTO) {
		Favorites findFavo = favorRepo.findFavorites(userId, favoDTO.getDataNo());
		favorRepo.delete(findFavo);
	}
}
