package com.interverse.demo.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interverse.demo.dto.ArticlePhotoDTO;
import com.interverse.demo.dto.ClubArticleDTO;
import com.interverse.demo.model.ArticlePhoto;
import com.interverse.demo.model.ClubArticle;
import com.interverse.demo.model.ClubArticlesRepository;
import com.interverse.demo.model.ClubRepository;
import com.interverse.demo.model.User;
import com.interverse.demo.model.UserRepository;

@Service
public class ClubArticleService {

	@Autowired
	private ClubArticlesRepository clubArticlesRepo;
	
	@Autowired
	private ClubRepository clubRepo;
	
	@Autowired
	private UserRepository userRepo;
	
  
	
	public ClubArticle createArticle(ClubArticle article) {
		return clubArticlesRepo.save(article);
	}
	
	public ClubArticleDTO findArticleById(Integer articleId) {
		Optional<ClubArticle> optional = clubArticlesRepo.findById(articleId);
        return optional.map(ClubArticleDTO::fromEntity).orElse(null);
		

	}
	
    public List<ClubArticleDTO> findAllArticleByClubId(Integer clubId) {
        List<ClubArticle> articles = clubArticlesRepo.findAllArticleByClubId(clubId);
        
        //
        return articles.stream()
                .map(ClubArticleDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public void loadBase64Photos(List<ClubArticleDTO> articleDTOs) throws IOException {
        for (ClubArticleDTO articleDTO : articleDTOs) {
            for (ArticlePhotoDTO photoDTO : articleDTO.getPhotos()) {
                File file = new File(photoDTO.getUrl());
                byte[] photoFile = Files.readAllBytes(file.toPath());
                String base64Photo = "data:image/png;base64," + Base64.getEncoder().encodeToString(photoFile);
                photoDTO.setBase64Photo(base64Photo);
            }
        }
    }

	@Transactional
	public ClubArticle updateArticle(Integer articleId,
										ClubArticle clubArticle) {
		Optional<ClubArticle> optional = clubArticlesRepo.findById(articleId);
		if(optional.isPresent()) {
			ClubArticle article = optional.get();
			article.setTitle(clubArticle.getTitle());
			article.setContent(clubArticle.getContent());
			return article;
		}
		return null;
	}
	
	public void deleteArticleById(Integer articleId) {
		clubArticlesRepo.deleteById(articleId);
	}
	
	
}
