package com.interverse.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.PostLike;
import com.interverse.demo.model.PostLikeRepository;
import com.interverse.demo.model.User;
import com.interverse.demo.model.UserPost;
import com.interverse.demo.model.UserPostRepository;
import com.interverse.demo.model.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PostLikeService {

    @Autowired
    private PostLikeRepository postLikeRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserPostRepository userPostRepository;

    @Transactional
    public void toggleLike(Integer userId, Integer postId, Integer type) {
        if (postLikeRepository.existsByUserIdAndPostId(userId, postId)) {
            postLikeRepository.deleteByUserIdAndPostId(userId, postId);
        } else {
        	Optional<User> userOptional = userRepository.findById(userId);
        	Optional<UserPost> postOptional = userPostRepository.findById(postId);
        	if(userOptional.isPresent()&& postOptional.isPresent()) {

        	}
            PostLike postLike = new PostLike();
            postLike.setUser(userOptional.get());
            postLike.setPost(postOptional.get());
            postLike.setType(type);
            postLikeRepository.save(postLike);
        }
    }

    public boolean hasUserLikedPost(Integer userId, Integer postId) {
        return postLikeRepository.existsByUserIdAndPostId(userId, postId);
    }
}