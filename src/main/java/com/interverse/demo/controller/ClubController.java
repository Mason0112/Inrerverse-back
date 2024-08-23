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

import com.interverse.demo.dto.ClubDTO;
import com.interverse.demo.model.Club;
import com.interverse.demo.service.ClubService;

@RestController
@RequestMapping("/clubs")
public class ClubController {

	@Autowired
	private ClubService cService;

//	@Autowired
//	private UserService uService;

	// 轉換DTO
	private ClubDTO convertToDTO(Club club) {
		ClubDTO dto = new ClubDTO();

		dto.setId(club.getId());
		dto.setClubName(club.getClubName());
		dto.setDescription(club.getDescription());//目前不可空值
		dto.setPhoto(club.getPhoto());//目前不可空值
		dto.setIsPublic(club.getIsPublic());
		dto.setIsAllowed(club.getIsAllowed());
		dto.setAdded(club.getAdded());
		dto.setClubCreatorId(club.getClubCreator().getId());
				
		// 如果需要，将 event 的名称列表填充到 DTO 中
		dto.setEventNames(club.getEvent().stream().map(event -> event.getEventName()) // 假设 Event 类有 getEventName 方法
				.collect(Collectors.toList()));
		
		return dto;
	}

	@PostMapping
	public ClubDTO createClub(@RequestBody Club club) {
		Club saveClub = cService.saveClub(club);
		return convertToDTO(saveClub);
	}

	@GetMapping
	public List<ClubDTO> getAllClub() {
		List<Club> allClub = cService.findAllClub();
		return allClub.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getClub(@PathVariable Integer id) {
		Club result = cService.findClubById(id);

		if (result != null) {
			ClubDTO clubDTO = convertToDTO(result);
			return ResponseEntity.ok(clubDTO);

		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此ID");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteClub(@PathVariable Integer id) {

		if (cService.findClubById(id) == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此 ID");
		}

		cService.deleteClubById(id);

		return ResponseEntity.status(HttpStatus.OK).body("Delete Successful");
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateClub(@PathVariable Integer id, @RequestBody Club club) {

		Club existingclub = cService.findClubById(id);

		if (existingclub == null) {
		
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此 ID");
		}
		
		club.setId(id);
		club.setAdded(existingclub.getAdded());
		
		convertToDTO(cService.saveClub(club));

		return ResponseEntity.status(HttpStatus.OK).body("Update Successful");
	}
}
