package com.interverse.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.dto.ClubPhotoDTO;
import com.interverse.demo.model.Club;
import com.interverse.demo.model.ClubPhoto;
import com.interverse.demo.model.ClubPhotoRepository;
import com.interverse.demo.model.ClubRepository;
import com.interverse.demo.model.User;
import com.interverse.demo.model.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClubPhotoService {
	
	@Autowired
	private ClubPhotoRepository cpRepo;
	
	@Autowired
	private ClubRepository cRepo;
	
	@Autowired
	private UserRepository uRepo;
	
	public ClubPhoto saveClubPhoto(ClubPhoto clubPhoto) {
		Club club = clubPhoto.getClub();
		User uploaderId = clubPhoto.getUploaderId();
		if(cRepo.existsById(club.getId()) && uRepo.existsById(uploaderId.getId())) {
			return cpRepo.save(clubPhoto);
		}
		throw new IllegalStateException("Club or User is not exists.");
	}
	
	//尋找club中所有照片
	 public List<ClubPhoto> getAllPhotosByClubId(Integer clubId) {
	        return cpRepo.findByClubId(clubId);
	    }
	
	//user從club中刪除自己上傳的照片
	 public void deletePhotoIfOwner(Integer id, Integer uploaderId) {
	        ClubPhoto photo = cpRepo.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Photo not found with ID: " + id));

	        // 檢查上傳者ID是否與要求刪除的使用者ID相同
	        if (!photo.getUploaderId().getId().equals(uploaderId)) {
	            throw new SecurityException("You do not have permission to delete this photo.");
	        }

	        cpRepo.deleteById(id);
	    }
	
//	public List<ClubPhoto> findAllClubPhoto(){
//		return cpRepo.findAll();
//	}
//	
}
