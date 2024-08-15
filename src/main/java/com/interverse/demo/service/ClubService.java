package com.interverse.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.Club;
import com.interverse.demo.model.ClubRepository;

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
			Club result = optional.get();
			return result;
		}
		return null;
	}

	public void deleteById(Integer id) {
		clubRepo.deleteById(id);
	}

	public List<Club> findAllCub() {
		return clubRepo.findAll();
	}
}
