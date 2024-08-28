package com.interverse.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.interverse.demo.model.PostPhoto;
import com.interverse.demo.model.PostPhotosRepository;
import com.interverse.demo.model.UserPost;
import com.interverse.demo.model.UserPostRepository;

@Service
public class PostPhotoService {
	
    @Value("${upload.userPost.dir}")
    private String uploadDir;
	
	@Autowired
	private PostPhotosRepository postPhotoRepo;
	
	@Autowired
	private UserPostRepository postRepo;
	
	public PostPhoto createPhoto(MultipartFile file, Integer postId) throws IOException {
		
		UserPost post=postRepo.findById(postId).orElseThrow(() -> new RuntimeException("UserPost not found with id: " + postId));
	
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	
		String uniqueFileName=UUID.randomUUID().toString() + "_" + fileName;
	
		Path uploadPath = Paths.get(uploadDir);
	
		
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		Path filePath = uploadPath.resolve(uniqueFileName);
		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		
		PostPhoto postPhoto = new PostPhoto();
		postPhoto.setName(fileName);
		postPhoto.setUrl("/uploads" + fileName);
		postPhoto.setUserPost(post);
		
		return postPhotoRepo.save(postPhoto);
	}
	
	public List<PostPhoto> findPhotoListByPostId(Integer postId){
		List<PostPhoto> list = postPhotoRepo.findPhotoListByPostId(postId);
		return list;
	}
	
	
}
