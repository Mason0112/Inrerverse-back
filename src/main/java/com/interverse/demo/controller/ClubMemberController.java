package com.interverse.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.dto.ClubMemberDTO;
import com.interverse.demo.model.ClubMember;
import com.interverse.demo.model.ClubMemberId;
import com.interverse.demo.service.ClubMemberService;

@RestController
@RequestMapping("/clubMember")
public class ClubMemberController {

	@Autowired
	private ClubMemberService cmService;
	
	private ClubMemberDTO convertToDTO(ClubMember clubMember) {
		ClubMemberDTO dto = new ClubMemberDTO();
		
		dto.setUserId(clubMember.getClubMemberId().getUserId());
		dto.setClubId(clubMember.getClubMemberId().getClubId());
		dto.setStatus(clubMember.getStatus());
		dto.setAdded(clubMember.getAdded());
		
		return dto;
	}
	
	
	@PostMapping  //@RequestParam Integer clubId,@RequestParam Integer userI
	public ClubMemberDTO createClubMember(@RequestBody ClubMember cm) {
	ClubMember saveClubMember = cmService.saveClubMember(cm);
	return convertToDTO(saveClubMember);
	}
	
	@GetMapping
	public List<ClubMemberDTO> getAllClubMember(){
		List<ClubMember> allClubMember = cmService.findAllClubMember();
		
		return allClubMember.stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());

	}
	
	@GetMapping("/{ClubMemberId}")
	public ResponseEntity<?> getClubMember(@PathVariable ClubMemberId cmId){
		ClubMember result = cmService.findClubMemberById(cmId);
		if(result != null) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此ID");
	}
	
	@DeleteMapping("/{ClubMemberId}")
	public ResponseEntity<String> deleteClubMember(@PathVariable ClubMemberId cmId){
		ClubMember result = cmService.findClubMemberById(cmId);
		if(result == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此ID");
		}
		cmService.deleteClubMemberById(cmId);
		return ResponseEntity.status(HttpStatus.OK).body("刪除成功");
	}
	
	@PutMapping("/{ClubMemberId}")
	public ResponseEntity<String> updateClubMember(@PathVariable ClubMemberId cmId, @RequestBody ClubMember clubMember){
		
		ClubMember existingCm = cmService.findClubMemberById(cmId);
		
		if(existingCm == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此ID");
		}
		clubMember.setClubMemberId(cmId);
		clubMember.setAdded(existingCm.getAdded());
		cmService.saveClubMember(clubMember);
		
		return ResponseEntity.status(HttpStatus.OK).body("更新成功");
	}
	
}
