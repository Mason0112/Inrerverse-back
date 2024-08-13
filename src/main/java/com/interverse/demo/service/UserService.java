package com.interverse.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	

}
