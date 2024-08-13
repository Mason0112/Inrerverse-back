package com.interverse.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interverse.demo.model.ClubArticleComment;
import com.interverse.demo.model.ClubArticleCommentRepository;

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
