package com.interverse.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.User;
import com.interverse.demo.model.UserDetail;
import com.interverse.demo.model.UserDetailRepository;
import com.interverse.demo.model.UserRepository;

@Service
public class UserService {

	@Autowired
	private PasswordEncoder pwdEcoder;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserDetailRepository uDetailRepo;

	public User register(User user) {

		String encodedPassword = pwdEcoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		return userRepo.save(user);
	}

	public User login(String accountNumber, String password) {
		User user = userRepo.findByAccountNumber(accountNumber);

		if (user != null) {
			String encodedPassword = user.getPassword();
			boolean isMatched = pwdEcoder.matches(password, encodedPassword);

			if (isMatched) {
				return user;
			}
		}
		return null;
	}

	public List<User> findAllUser() {
		return userRepo.findAll();
	}

	public User findUserById(Integer id) {
		Optional<User> optional = userRepo.findById(id);

		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	public void deleteUserById(Integer id) {
		userRepo.deleteById(id);
	}

	public User updateUserDetail(User user) {
		return userRepo.save(user);
	}

	public boolean existsByAccountNumber(String accountNumber) {
		User user = userRepo.findByAccountNumber(accountNumber);
		return user != null;   // 如果user存在，返回true，否則返回false
	}
	
	public boolean existsByEmail(String email) {
		User user = userRepo.findByEmail(email);
		return user != null;   // 如果user存在，返回true，否則返回false
	}
	
	public boolean existsByPhoneNumber(String phoneNumber) {
		UserDetail user = uDetailRepo.findByPhoneNumber(phoneNumber);
		return user != null;   // 如果user存在，返回true，否則返回false
	}

}
