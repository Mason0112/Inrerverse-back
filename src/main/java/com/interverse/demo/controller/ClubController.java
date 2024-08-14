package com.interverse.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.service.ClubService;
import com.interverse.demo.service.UserService;



@RestController
public class ClubController {

	@Autowired
	private ClubService cService;

	@Autowired
	private UserService uService;


}
