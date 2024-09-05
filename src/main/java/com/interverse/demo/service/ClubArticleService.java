package com.interverse.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interverse.demo.dto.ClubArticleDTO;
import com.interverse.demo.model.Club;
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
	
    private ClubArticleDTO convertToDTO(ClubArticle article) {
        ClubArticleDTO dto = new ClubArticleDTO();
        dto.setId(article.getId());
        // 安全地獲取 userId
        User user = article.getUser();
        if (user != null) {
            dto.setUserId(user.getId());
        }
        // 安全地獲取 clubId
        if (article.getClub() != null) {
            dto.setClubId(article.getClub().getId());
        }
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());
        dto.setAdded(article.getAdded());
        dto.setLikeCount(article.getLikeCount());
        if (article.getPhotos() != null) {
            dto.setPhotoUrls(article.getPhotos().stream()
                .map(photo -> photo.getUrl())
                .collect(Collectors.toList()));
        }
        return dto;
    }

    private ClubArticle convertToEntity(ClubArticleDTO dto) {
        ClubArticle article = new ClubArticle();
        article.setId(dto.getId());
        // 這裡需要從數據庫中獲取 User 和 Club 對象
        // 假設我們有 UserRepository 和 ClubRepository
        if (dto.getUserId() != null) {
            User user = userRepo.findById(dto.getUserId()).orElse(null);
            article.setUser(user);
        }
        if (dto.getClubId() != null) {
            Club club = clubRepo.findById(dto.getClubId()).orElse(null);
            article.setClub(club);
        }
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setAdded(dto.getAdded());
        article.setLikeCount(dto.getLikeCount());
        // 處理 photos 需要額外的邏輯，這裡省略
        return article;
    }
	
	public ClubArticleDTO saveArticle(ClubArticleDTO articleDTO) {
		ClubArticle article = convertToEntity(articleDTO);
		ClubArticle savedArticle = clubArticlesRepo.save(article);
		return convertToDTO(savedArticle);
	}
	
	public ClubArticle findArticleById(Integer articleId) {
		Optional<ClubArticle> optional = clubArticlesRepo.findById(articleId);
		if(optional.isPresent()){
			return  optional.get();
		}
		return null;
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
