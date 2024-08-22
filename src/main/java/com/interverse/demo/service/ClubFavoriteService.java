package com.interverse.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.ClubFavorite;
import com.interverse.demo.model.ClubFavoriteId;
import com.interverse.demo.model.ClubFavoriteRepository;

@Service
public class ClubFavoriteService {

	@Autowired
	private ClubFavoriteRepository cfRepo;
	
	public ClubFavorite saveClubFavorite(ClubFavorite clubfavorite) {
		
		ClubFavoriteId clubFavoriteId=clubfavorite.getClubFavoriteId();
		 if (cfRepo.existsById(clubFavoriteId)) {
	            throw new IllegalStateException("ClubFavorite already exists.");
	        }
		return cfRepo.save(clubfavorite);
	}	
	
	public ClubFavorite findClubFavoriteById(ClubFavoriteId clubFavoriteId) {
		Optional<ClubFavorite> optional = cfRepo.findById(clubFavoriteId);
		
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	public void deleteClubFavoriteFromUser(Integer clubId, Integer userId) {
		cfRepo.deleteClubFavoriteFromUser(clubId,userId);
	}
	
//	public List<ClubFavorite> findAllClubFavorite(){
//		return cfRepo.findAll();
//	}
	
	//用userId搜尋
	 public List<ClubFavorite> findByClubFavoriteIdUserId(Integer userId) {
	        return cfRepo.findByClubFavoriteIdUserId(userId);
	    }
}
