package com.interverse.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.UserDetail;
import com.interverse.demo.model.UserDetailRepository;

@Service
public class UserDetailService {
	
	@Autowired
	private UserDetailRepository uDetailRepo;
	
	public UserDetail findUserDetailById(Integer id) {
		Optional<UserDetail> optional = uDetailRepo.findById(id);

		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}


}
