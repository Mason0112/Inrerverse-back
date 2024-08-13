package com.interverse.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interverse.demo.model.ClubArticle;
import com.interverse.demo.model.ClubArticlesRepository;

@Service
public class ClubArticleService {

	@Autowired
	private ClubArticlesRepository clubArticlesRepo;
	
	public ClubArticle saveArticle(ClubArticle article) {
		return clubArticlesRepo.save(article);
	}
	
	public ClubArticle findArticleById(Integer articleId) {
		Optional<ClubArticle> optional = clubArticlesRepo.findById(articleId);
		if(optional.isPresent()) {
		return optional.get();
		}
		return null;
	}

	@Transactional
	public ClubArticle updateArticle(Integer articleId,
										String title,
										String content) {
		Optional<ClubArticle> optional = clubArticlesRepo.findById(articleId);
		if(optional.isPresent()) {
			ClubArticle article = optional.get();
			article.setTitle(title);
			article.setContent(content);
			return article;
		}
		return null;
	}
	
	public void deleteArticleById(Integer articleId) {
		clubArticlesRepo.deleteById(articleId);
	}
	
	
}
