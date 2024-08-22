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

import com.interverse.demo.dto.ClubPhotoDTO;
import com.interverse.demo.model.ClubPhoto;
import com.interverse.demo.service.ClubPhotoService;

@RestController
@RequestMapping("/clubPhoto")
public class ClubPhotoController {

	@Autowired
	private ClubPhotoService cpService;

	// 轉換DTO
	private ClubPhotoDTO convertToDTO(ClubPhoto clubPhoto) {
		ClubPhotoDTO dto = new ClubPhotoDTO();

		dto.setId(clubPhoto.getId());
		dto.setPhoto(clubPhoto.getPhoto());
		dto.setClubId(clubPhoto.getClub().getId());
		dto.setUploaderId(clubPhoto.getUploaderId().getId());

		return dto;
	}

	@PostMapping
	public ClubPhotoDTO createClubPhoto(@RequestBody ClubPhoto ClubPhoto) {
		ClubPhoto saveClubPhoto = cpService.saveClubPhoto(ClubPhoto);
		return convertToDTO(saveClubPhoto);
	}
	//要改from club
	@GetMapping
	public List<ClubPhotoDTO> getAllClubPhoto() {
		List<ClubPhoto> allClubPhoto = cpService.findAllClubPhoto();

		return allClubPhoto.stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getClubPhoto(@PathVariable Integer id) {

		ClubPhoto existingCP = cpService.findClubPhotoById(id);

		if (existingCP != null) {
			ClubPhotoDTO clubPhotoDTO = convertToDTO(existingCP);
			return ResponseEntity.ok(clubPhotoDTO);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此ID");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteClubPhoto(@PathVariable Integer id) {

		if (cpService.findClubPhotoById(id) == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此ID");
		}
		cpService.deleteClubPhotoById(id);
		return ResponseEntity.status(HttpStatus.OK).body("Delete Successful");
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateClubPhoto(@PathVariable Integer id, @RequestBody ClubPhoto clubPhoto) {

		ClubPhoto existingCP = cpService.findClubPhotoById(id);

		if (existingCP == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此 ID");
		}
		clubPhoto.setId(id);

		convertToDTO(cpService.saveClubPhoto(clubPhoto));

		return ResponseEntity.status(HttpStatus.OK).body("更新成功");
	}
}
