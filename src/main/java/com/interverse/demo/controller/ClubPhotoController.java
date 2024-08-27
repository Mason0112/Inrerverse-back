package com.interverse.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.interverse.demo.dto.ClubPhotoDTO;
import com.interverse.demo.model.ClubPhoto;
import com.interverse.demo.model.ClubRepository;
import com.interverse.demo.model.ProductPhotos;
import com.interverse.demo.model.UserRepository;
import com.interverse.demo.service.ClubPhotoService;

@RestController
@RequestMapping("/clubPhoto")
public class ClubPhotoController {

	@Autowired
	private ClubPhotoService cpService;
	@Autowired
	private UserRepository uRepo;
	@Autowired
	private ClubRepository cRepo;

	// 建立照片
	@PostMapping
	public ResponseEntity<ClubPhoto> createClubPhoto(@RequestParam("file") MultipartFile file,
            @RequestParam("clubId") Integer clubId) throws IOException {
		ClubPhoto createdPhoto = cpService.createClubPhoto(file, clubId);
	        return new ResponseEntity<>(createdPhoto, HttpStatus.CREATED);
	}
	
//	public ResponseEntity<?> createClubPhoto(@RequestBody ClubPhotoDTO clubPhotoDTO) {
//	try {
//		ClubPhoto clubPhoto = convertToEntity(clubPhotoDTO);
//		ClubPhoto savedClubPhoto = cpService.saveClubPhoto(clubPhoto);
//		return ResponseEntity.ok(convertToDTO(savedClubPhoto));
//
//	} catch (Exception e) {
//
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//	}
//}

	// 尋找club中所有照片
	@GetMapping("/club/{clubId}")
	public ResponseEntity<List<ClubPhotoDTO>> getAllClubPhoto(@PathVariable Integer clubId) {
		try {
			List<ClubPhoto> allPhotosByClubId = cpService.getAllPhotosByClubId(clubId);
			List<ClubPhotoDTO> photosDTO = allPhotosByClubId.stream().map(this::convertToDTO)
					.collect(Collectors.toList());
			return ResponseEntity.ok(photosDTO);
		} catch (Exception e) {
			System.out.println("錯誤 " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}
	}

	// user可以在club中刪除自己上傳的照片
	// Controller method for handling the delete request
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteClubPhoto(@PathVariable Integer id, @RequestParam Integer uploaderId) {
	    if (uploaderId == null) {
	        return ResponseEntity.badRequest().body("Uploader ID is required.");
	    }
	    try {
	        cpService.deletePhotoIfOwner(id, uploaderId);
	        return ResponseEntity.ok("Photo deleted successfully.");
	    } catch (SecurityException | IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
	    } catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting the file.");
	    }
	}
	
	// 轉換DTO
	private ClubPhotoDTO convertToDTO(ClubPhoto clubPhoto) {
		ClubPhotoDTO dto = new ClubPhotoDTO();

		dto.setId(clubPhoto.getId());
//		dto.setPhoto(clubPhoto.getPhoto());
		dto.setClubId(clubPhoto.getClub().getId());
		dto.setUploaderId(clubPhoto.getUploaderId().getId());

		return dto;
	}

	// DTO轉換實體
	private ClubPhoto convertToEntity(ClubPhotoDTO dto) {
		ClubPhoto clubPhoto = new ClubPhoto();
//		clubPhoto.setPhoto(dto.getPhoto());
		clubPhoto.setUploaderId(uRepo.findById(dto.getUploaderId()).orElse(null));
		clubPhoto.setClub(cRepo.findById(dto.getClubId()).orElse(null));
		return clubPhoto;

	}
	
//	@GetMapping("/{id}")
//	public ResponseEntity<?> getClubPhoto(@PathVariable Integer id) {
//
//		ClubPhoto existingCP = cpService.findClubPhotoById(id);
//
//		if (existingCP != null) {
//			ClubPhotoDTO clubPhotoDTO = convertToDTO(existingCP);
//			return ResponseEntity.ok(clubPhotoDTO);
//		}
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此ID");
//	}

//	
//	@PutMapping("/{id}")
//	public ResponseEntity<String> updateClubPhoto(@PathVariable Integer id, @RequestBody ClubPhoto clubPhoto) {
//
//		ClubPhoto existingCP = cpService.findClubPhotoById(id);
//
//		if (existingCP == null) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此 ID");
//		}
//		clubPhoto.setId(id);
//
//		convertToDTO(cpService.saveClubPhoto(clubPhoto));
//
//		return ResponseEntity.status(HttpStatus.OK).body("更新成功");
//	}
}
