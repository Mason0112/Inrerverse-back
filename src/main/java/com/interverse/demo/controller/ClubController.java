package com.interverse.demo.controller;

import java.util.List;
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

import com.interverse.demo.model.Club;
import com.interverse.demo.service.ClubService;

@RestController
@RequestMapping("/clubs")
public class ClubController {

	@Autowired
	private ClubService cService;

//	@Autowired
//	private UserService uService;

	@PostMapping
	public Club createClub(@RequestBody Club club) {
		return cService.saveClub(club);
	}

	@GetMapping
	public List<Club> getAllClub() {
		return cService.findAllClub();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getClub(@PathVariable Integer id) {
		Club result = cService.findClubById(id);

		if (result != null) {
			return ResponseEntity.ok(result);

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
		cService.saveClub(club);
		
		return ResponseEntity.status(HttpStatus.OK).body("Update Successful");
	}
}
