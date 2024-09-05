package com.interverse.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.dto.ClubArticleDTO;
import com.interverse.demo.model.ArticlePhoto;
import com.interverse.demo.model.ClubArticle;
import com.interverse.demo.model.UserPost;
import com.interverse.demo.service.ClubArticleService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/club/article")
public class ClubArticleController {

	@Autowired
	private ClubArticleService articleService;
	
	@PostMapping
	public ResponseEntity<ClubArticleDTO> addArticle(@RequestBody ClubArticleDTO articleDTO){
		
		if(articleDTO.getUserId() == null) {
			return ResponseEntity.badRequest().body(null);
		}
		
		String content = articleDTO.getContent().replaceAll("\\r\\n|\\r|\\n", "\n");
		articleDTO.setContent(content);
		
		ClubArticleDTO saveArticle = articleService.saveArticle(articleDTO);
		return new ResponseEntity<>(saveArticle, HttpStatus.CREATED);
	}
	
	@GetMapping("/{clubId}")
	public ResponseEntity<List<ClubArticle>> showClubAllArticle(@PathVariable Integer clubId)throws IOException {
		try {
			List<ClubArticle> articles = articleService.findAllArticleByClubId(clubId);
			for (ClubArticle clubArticle : articles) {
				List<ArticlePhoto> photos = clubArticle.getPhotos();
				for(ArticlePhoto articlePhoto : photos) {
					File file=new File(articlePhoto.getUrl());
					byte[] photoFile = Files.readAllBytes(file.toPath());
					String base64Photo = "data:image/png;base64," + Base64.getEncoder().encodeToString(photoFile);
					articlePhoto.setBase64Photo(base64Photo);
				}
			}
			return ResponseEntity.ok(articles);
		}catch(IOException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			
		}
	}
	
	@PutMapping("/{articleId}")
	public ClubArticle updateArticle(@PathVariable Integer articleId,
									@RequestParam String content) {
		ClubArticle article = articleService.findArticleById(articleId);
		article.setContent(content);
		return articleService.saveArticle(article);
	}
	
	@DeleteMapping("/{articleId}")
	public void deleteArticle(@PathVariable Integer articleId) {
		articleService.deleteArticleById(articleId);
	}
	
	
	
}
