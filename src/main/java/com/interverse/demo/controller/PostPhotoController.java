package com.interverse.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.interverse.demo.model.PostPhoto;
import com.interverse.demo.service.PostPhotoService;


@RestController
public class PostPhotoController {

	@Autowired
	private PostPhotoService photoService;
	
	@PostMapping("/postPhoto")
	public ResponseEntity<PostPhoto> createPostPhoto(@RequestParam MultipartFile file,
			@RequestParam("postId") Integer postId) throws IOException {
		PostPhoto photo = photoService.createPhoto(file, postId);
		return new ResponseEntity<>(photo, HttpStatus.CREATED);
	}
	
}
