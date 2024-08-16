package com.interverse.demo.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.interverse.demo.dto.UserDTO;

public interface UserPostRepository extends JpaRepository<UserPost, Integer> {

	
}
