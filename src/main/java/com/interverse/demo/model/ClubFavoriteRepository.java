package com.interverse.demo.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubFavoriteRepository extends JpaRepository<ClubFavorite, ClubFavoriteId> {
	
	//用userId找收藏
	List<ClubFavorite> findByClubFavoriteIdUserId(Integer userId);
}
