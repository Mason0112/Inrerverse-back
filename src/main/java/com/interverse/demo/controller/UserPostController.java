package com.interverse.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.model.UserPost;
import com.interverse.demo.service.UserPostService;
import com.interverse.demo.service.UserService;


@RestController
public class UserPostController {

	@Autowired
	private UserPostService postService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/userPost/addPost")
	public UserPost addPost(@RequestBody UserPost post) {
		return postService.savePost(post);
	}
	
	
}
