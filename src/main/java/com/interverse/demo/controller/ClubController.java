package com.interverse.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
}
