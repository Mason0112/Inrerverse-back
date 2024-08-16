package com.interverse.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
	public ResponseEntity<Club> getClubById(@PathVariable Integer id) {
		Club result = cService.findClubById(id);

		if (result != null) {
			return ResponseEntity.ok(result);

		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteClub(@PathVariable Integer id) {

		if (cService.findClubById(id) == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此 ID");
		}

		cService.deleteById(id);

		return ResponseEntity.status(HttpStatus.OK).body("Delete Successful");
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateClub(@PathVariable Integer id, @RequestBody Club club) {

		if (cService.findClubById(id) == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此 ID");
		}
		club.setId(id); 
		club.setAdded(cService.findClubById(id).getAdded());
		
		Club result = cService.saveClub(club);
		
		return ResponseEntity.status(HttpStatus.OK).body("Update Successful");
	}
}
