package com.interverse.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.User;
import com.interverse.demo.model.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	public User resgister(User user) {
		
		return userRepo.save(user);
	}
	
	public User findById(Integer id) {
		Optional<User> option = userRepo.findById(id);
		
		if(option.isPresent()){
			return option.get();
			
		}
		return null;
		
		
	}
	

}