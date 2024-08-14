package com.interverse.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.User;
import com.interverse.demo.model.UserRepository;



@Service
public class UserService {
	
	@Autowired
	private UserRepository uRepo;
	
	public User saveUser(User user) {
		return uRepo.save(user);
	}
	
	public List<User>findAllUser(){
		return uRepo.findAll();
	}
	
	public User findUserById(Integer id) {
		Optional<User> optional = uRepo.findById(id);
		
		if(optional.isPresent()) {
			User result =optional.get();
			return result;
		}
		return null;
	}
}
	
