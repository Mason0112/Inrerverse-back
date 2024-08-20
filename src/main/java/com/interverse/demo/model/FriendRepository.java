package com.interverse.demo.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, FriendId> {
	
	Friend findByUser1IdAndUser2Id(Integer User1Id, Integer User2Id);
	
	List<Friend> findByUser1Id(Integer User1Id);
	List<Friend> findByUser2Id(Integer User2Id);

}
