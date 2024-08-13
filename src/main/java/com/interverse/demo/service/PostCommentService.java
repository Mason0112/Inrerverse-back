package com.interverse.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.PostComment;
import com.interverse.demo.model.PostCommentRepository;

@Service
public class PostCommentService {

	@Autowired
	private PostCommentRepository commentRepo;
	
	public PostComment addComment(PostComment comment) {
		return commentRepo.save(comment);
	}
	
	public PostComment findCommentById(Integer commentId) {
		Optional<PostComment> optional = commentRepo.findById(commentId);
		if(optional.isPresent()) {
			optional.get();
		}
		return null;
	}
	
	public PostComment updateComment(Integer commentId, String newComment) {
		Optional<PostComment> optional = commentRepo.findById(commentId);
		if(optional.isPresent()) {
			PostComment comment = optional.get();
			comment.setComment(newComment);
			return comment;
		}
		return null;
	}
	
	public void deleteCommentById(Integer commentId) {
		commentRepo.deleteById(commentId);
	}
	
	//findCommentByPostId
	
}

	