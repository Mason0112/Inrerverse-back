package com.interverse.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.Club;
import com.interverse.demo.model.ClubPhoto;
import com.interverse.demo.model.ClubRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClubService {

	@Autowired
	private ClubRepository clubRepo;

	public Club saveClub(Club club) {
		return clubRepo.save(club);
	}

	public Club findClubById(Integer id) {
		Optional<Club> optional = clubRepo.findById(id);

		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	public void deleteClubById(Integer id) {
//		Optional<Club> optional = clubRepo.findById(id);
//
//		if (optional.isPresent()) {
			clubRepo.deleteById(id);
//			return;
//		}
//		throw new EntityNotFoundException("Club with id " + id + " not found");
	}

	public List<Club> findAllClub() {
		return clubRepo.findAll();
	}

//	public boolean existsById(Integer id) {
//		return clubRepo.existsById(id);
//	}
	
}
