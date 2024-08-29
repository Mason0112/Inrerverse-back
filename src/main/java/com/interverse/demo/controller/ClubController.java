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
		dto.setDescription(club.getDescription());
		dto.setIsPublic(club.getIsPublic());
		dto.setIsAllowed(club.getIsAllowed());
		dto.setAdded(club.getAdded());
		dto.setClubCreator(club.getClubCreator().getId());
				
		// 如果需要，将 event 的名称列表填充到 DTO 中
//		dto.setEventNames(club.getEvent().stream().map(event -> event.getEventName()) // 假设 Event 类有 getEventName 方法
//				.collect(Collectors.toList()));
//		
		return dto;
	}

	@PostMapping
	public ClubDTO createClub(@RequestBody Club club) {
		Club saveClub = cService.saveClub(club);
		return convertToDTO(saveClub);
	}
	
//	@PostMapping
//	public Club createClub(@RequestBody Club club) {
//		if (club.getClubCreator() == null ) {
//	        throw new IllegalArgumentException("Club creator must be provided");
//	    }
//
//	    Club saveClub = cService.saveClub(club);
//	    return saveClub;
//	}

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
	    // 查找现有的Club对象
	    Club existingClub = cService.findClubById(id);

	    if (existingClub == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此 ID");
	    }

	    // 保留原有的clubCreator，防止在更新时被修改
	    club.setClubCreator(existingClub.getClubCreator());

	    // 保留原有的添加日期
	    club.setId(id);
	    club.setAdded(existingClub.getAdded());

	    // 保存更新后的Club对象
	    cService.saveClub(club);

	    return ResponseEntity.status(HttpStatus.OK).body("Update Successful");
	}

}
