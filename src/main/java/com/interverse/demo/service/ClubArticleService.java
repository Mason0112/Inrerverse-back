package com.interverse.demo.service;

import java.util.List;
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
	
	public List<ClubArticle> findAllArticleByClubId(Integer clubId) {
		return clubArticlesRepo.findAllArticleByClubId(clubId);
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
