package com.interverse.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.ClubMember;
import com.interverse.demo.model.ClubMemberId;
import com.interverse.demo.model.ClubMemberRepository;

@Service
public class ClubMemberService {

	@Autowired
	private ClubMemberRepository cmRepo;
	
	public ClubMember saveClubMember(ClubMember clubMember) {
		return cmRepo.save(clubMember);
	}
	
	public ClubMember findClubMemberById(ClubMemberId clubMemberId) {
		Optional<ClubMember> optional = cmRepo.findById(clubMemberId);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	public void deleteClubMemberById(ClubMemberId clubMemberId) {
		cmRepo.deleteById(clubMemberId);
	}
	
	public List<ClubMember> findAllClubMember(){
		return cmRepo.findAll();
	}
}
