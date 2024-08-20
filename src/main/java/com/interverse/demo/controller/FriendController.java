package com.interverse.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.service.FriendService;

@CrossOrigin
@RestController
@RequestMapping("/friend")
public class FriendController {
	
	@Autowired
	private FriendService friendService;
	
	@GetMapping("/switch-status/{user1Id}")
	public ResponseEntity<?> switchFriendStatus(@PathVariable Integer user1Id, @RequestParam Integer user2Id) {
		
		friendService.switchFriendStatus(user1Id, user2Id);
		return ResponseEntity.ok().build();
	}

}
