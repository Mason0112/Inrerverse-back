package com.interverse.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.ClubArticleComment;
import com.example.demo.model.ClubArticleCommentRepository;
import com.example.demo.model.ClubArticle;

@Service
public class ClubArticlesCommentsService {

	@Autowired
	private ClubArticleCommentRepository commentRepo;
	
	public ClubArticleComment saveComment(ClubArticleComment comment) {
		return commentRepo.save(comment);
	}
	
	public ClubArticleComment findCommentById(Integer id) {
		Optional<ClubArticleComment> optional = commentRepo.findById(id);
		if(optional.isPresent()) {
		return optional.get();
		}
		return null;
	}
	
	@Transactional
	public ClubArticleComment updateCommentById(Integer id, String content) {
		Optional<ClubArticleComment> optional = commentRepo.findById(id);
		if(optional.isPresent()) {
		ClubArticleComment comments = optional.get();
		comments.setContent(content);
		return comments;
		}
		return null;
	}
	
	public void deleteCommentById(Integer id) {
		commentRepo.deleteById(id);
	}
	
//	public List<ClubArticleComment> findCommentByArticle(Integer articleId){
//		
//	}
	
	
}
