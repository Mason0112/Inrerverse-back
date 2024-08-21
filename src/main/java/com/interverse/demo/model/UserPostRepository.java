package com.interverse.demo.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserPostRepository extends JpaRepository<UserPost, Integer> {

	@Query("from UserPost where user.id = :id order by added desc")
	List<UserPost> findAllPostByUserId(@Param("id") Integer userId);
}
