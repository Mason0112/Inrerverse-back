package com.interverse.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.model.UserPost;
import com.interverse.demo.service.UserPostService;
import com.interverse.demo.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
public class UserPostController {

	@Autowired
	private UserPostService postService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/userPost")
	public ResponseEntity<UserPost> addPost(@RequestBody UserPost post) {
		String content = post.getContent().replaceAll("\\r\\n|\\r|\\n", "\n");
		post.setContent(content);
		UserPost savePost = postService.savePost(post);
		return new ResponseEntity<>(savePost, HttpStatus.CREATED);
	}
	
	@GetMapping("/userPost/showUserAllPost/{userId}")
	public List<UserPost> showUserAllPost(@PathVariable Integer userId) {
		return postService.showUserAllPost(userId);
	}
	
	@PutMapping("/userPost/{postId}")
	public UserPost updatePost(@PathVariable Integer postId, 
									@RequestParam String content) {
		UserPost post = postService.findPostById(postId);
		post.setContent(content);
		postService.savePost(post);
		return post;
	}
	
	@DeleteMapping("/userPost/{postId}")
	public void deletePost(@PathVariable Integer postId) {
		postService.deletePostById(postId);
	}
	
	//待做  關鍵字查詢貼文
	
	//標註好友  好友動態牆上也顯示貼文
	
}
