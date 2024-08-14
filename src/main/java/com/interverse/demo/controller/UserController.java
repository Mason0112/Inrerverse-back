package com.interverse.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.model.User;
import com.interverse.demo.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/users/addpost")
	public User addUser(@RequestBody User user ) {
		
		return  userService.register(user);
		
		
	}
	
}
