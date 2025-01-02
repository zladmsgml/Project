package com.rubypaper.favorites;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

	@Query("SELECT f FROM Favorites f where f.userId = :userId")
	List<Favorites> getFavoritesByUserId(String userId);
	
	@Query("SELECT f FROM Favorites f where f.userId = :userId and f.dataNo = :dataNo")
	Favorites findFavorites(String userId, int dataNo);
}
