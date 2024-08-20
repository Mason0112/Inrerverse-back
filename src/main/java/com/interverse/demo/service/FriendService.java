package com.interverse.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.Friend;
import com.interverse.demo.model.FriendId;
import com.interverse.demo.model.FriendRepository;
import com.interverse.demo.model.User;
import com.interverse.demo.model.UserRepository;

@Service
public class FriendService {
	
	@Autowired
	private FriendRepository friendRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	public void switchFriendStatus(Integer user1Id, Integer user2Id) {
		Friend possibility1 = friendRepo.findByUser1IdAndUser2Id(user1Id, user2Id);
		Friend possibility2 = friendRepo.findByUser1IdAndUser2Id(user2Id, user1Id);
		
		Optional<User> optional1 = userRepo.findById(user1Id);
		User user1 = optional1.get();
		
		Optional<User> optional2 = userRepo.findById(user2Id);
		User user2 = optional2.get();
		
		if(possibility1 == null && possibility2 == null) {
			
			FriendId friendId = new FriendId();
			friendId.setUser1Id(user1Id);
			friendId.setUser2Id(user2Id);
			
			Friend friend = new Friend();
			friend.setFirendId(friendId);
			friend.setUser1(user1);
			friend.setUser2(user2);
			friend.setStatus(false);
			
			friendRepo.save(friend);
		}
		
	}
}
