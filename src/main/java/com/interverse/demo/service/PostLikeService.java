package com.interverse.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.PostLike;
import com.interverse.demo.model.PostLikeRepository;
import com.interverse.demo.model.User;
import com.interverse.demo.model.UserPost;

import jakarta.transaction.Transactional;

@Service
public class PostLikeService {

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Transactional
    public void toggleLike(Integer userId, Integer postId, Integer type) {
        if (postLikeRepository.existsByUserIdAndPostId(userId, postId)) {
            postLikeRepository.deleteByUserIdAndPostId(userId, postId);
        } else {
            PostLike postLike = new PostLike();
            postLike.setUser(new User(userId));
            postLike.setPost(new UserPost(postId));
            postLike.setType(type);
            postLikeRepository.save(postLike);
        }
    }

    public boolean hasUserLikedPost(Integer userId, Integer postId) {
        return postLikeRepository.existsByUserIdAndPostId(userId, postId);
    }
}