package com.interverse.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.ClubFavorite;
import com.interverse.demo.model.ClubFavoriteId;
import com.interverse.demo.model.ClubFavoriteRepository;
import jakarta.transaction.Transactional;

@Service
public class ClubFavoriteService {

	@Autowired
	private ClubFavoriteRepository cfRepo;
	
	@Transactional
	public ClubFavorite saveClubFavorite(ClubFavorite clubFavorite) {
		ClubFavoriteId clubFavoriteId = clubFavorite.getClubFavoriteId();
			if(cfRepo.existsById(clubFavoriteId)) {
	            throw new IllegalStateException("無法重複收藏");
			}
			return cfRepo.save(clubFavorite);
	}

	
	public void deleteClubFavoriteFromUser(Integer clubId, Integer userId) {
		cfRepo.deleteClubFavoriteFromUser(clubId,userId);
	}
	
	//用userId搜尋
	 public List<ClubFavorite> findByClubFavoriteIdUserId(Integer userId) {
	        return cfRepo.findByClubFavoriteIdUserId(userId);
	    }
	 
//		public List<ClubFavorite> findAllClubFavorite(){
//		return cfRepo.findAll();
//	}
}
