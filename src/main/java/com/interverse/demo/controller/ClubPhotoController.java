package com.interverse.demo.controller;

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

import com.interverse.demo.model.ClubPhoto;
import com.interverse.demo.service.ClubPhotoService;

@RestController
@RequestMapping("/clubPhoto")
public class ClubPhotoController {
	
	@Autowired
	private ClubPhotoService cpService;
	
	@PostMapping
	public ClubPhoto createClubPhoto(@RequestBody ClubPhoto ClubPhoto) {
		return cpService.saveClubPhoto(ClubPhoto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findClubPhoto(@PathVariable Integer id){
		
		ClubPhoto existingCP = cpService.findClubPhotoById(id);
		
		if(existingCP !=null) {
			return ResponseEntity.ok(existingCP);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此ID");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteClubPhoto(@PathVariable Integer id){
		
		if(cpService.findClubPhotoById(id) ==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此ID");
		}
		cpService.deleteClubPhotoById(id);
		return ResponseEntity.status(HttpStatus.OK).body("Delete Successful");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateClubPhoto(@PathVariable Integer id ,@RequestBody ClubPhoto clubPhoto){
		
		ClubPhoto existingCP = cpService.findClubPhotoById(id);
		
		if(existingCP ==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此 ID");
		}
		clubPhoto.setId(id);
		cpService.saveClubPhoto(clubPhoto);
		
		return ResponseEntity.status(HttpStatus.OK).body("更新成功");
	}
}
