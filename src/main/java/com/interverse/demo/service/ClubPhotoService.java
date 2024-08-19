package com.interverse.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.ClubPhoto;
import com.interverse.demo.model.ClubPhotoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClubPhotoService {
	
	@Autowired
	private ClubPhotoRepository cpRepo;
	
	public ClubPhoto saveClubPhoto(ClubPhoto clubPhoto) {
		return cpRepo.save(clubPhoto);
	}
	
	public ClubPhoto findClubPhotoById(Integer id) {
		Optional<ClubPhoto> optional = cpRepo.findById(id);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	public void deleteClubPhotoById(Integer id) {
		Optional<ClubPhoto> optional = cpRepo.findById(id);
		
//		if(optional.isPresent()) {
			cpRepo.deleteById(id);
//			return;
//		}
//		 throw new EntityNotFoundException("ClubPhoto:" + id + " not found");
	}
	
	public List<ClubPhoto> findAllClubPhoto(){
		return cpRepo.findAll();
	}
	
}
