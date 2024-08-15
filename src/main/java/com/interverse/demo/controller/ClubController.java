package com.interverse.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
		Club result= cService.findClubById(id);
	     
	     if(result!=null) {
	    	 return ResponseEntity.ok(result);
	    	 
	     }
	     return ResponseEntity.notFound().build();
	}
}
