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

import com.interverse.demo.dto.ArticleCommentDTO;
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
	private ClubArticlesCommentsService commentService;
	
	@Autowired
	private UserRepository userRepo;
	
  
	
    public ClubArticleDTO createArticle(ClubArticleDTO articleDTO) {
        ClubArticle article = new ClubArticle();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setUser(userRepo.findById(articleDTO.getUserId()).orElseThrow());
        article.setClub(clubRepo.findById(articleDTO.getClubId()).orElseThrow());
        
        ClubArticle savedArticle = clubArticlesRepo.save(article);
        return ClubArticleDTO.fromEntity(savedArticle);
    }
	
    public ClubArticleDTO findArticleById(Integer articleId) {
        ClubArticleDTO articleDTO = clubArticlesRepo.findById(articleId)
                .map(ClubArticleDTO::fromEntity)
                .orElse(null);
        
        if(articleDTO != null) {
        	List<ArticleCommentDTO> comments = commentService.findAllCommentByArticleIdd(articleId);
        	articleDTO.setComments(comments);
        }
        return articleDTO;
    }
	
    public List<ClubArticleDTO> findAllArticleByClubId(Integer clubId) {
        List<ClubArticle> articles = clubArticlesRepo.findAllArticleByClubId(clubId);
        
        //
         List<ClubArticleDTO> collect = articles.stream()
        		 						.map(ClubArticleDTO::fromEntity)
        		 						.collect(Collectors.toList());
//         System.out.println("Service:" + collect);
         return collect;
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
    public ClubArticleDTO updateArticle(Integer articleId, ClubArticleDTO clubArticleDTO) {
        return clubArticlesRepo.findById(articleId)
                .map(article -> {
                    article.setTitle(clubArticleDTO.getTitle());
                    article.setContent(clubArticleDTO.getContent());
                    return ClubArticleDTO.fromEntity(article);
                })
                .orElse(null);
    }
	
	public void deleteArticleById(Integer articleId) {
		clubArticlesRepo.deleteById(articleId);
	}
	
	
}
