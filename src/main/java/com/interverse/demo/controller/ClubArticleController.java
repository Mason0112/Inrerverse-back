package com.interverse.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.model.ArticlePhoto;
import com.interverse.demo.model.ClubArticle;
import com.interverse.demo.model.UserPost;
import com.interverse.demo.service.ClubArticleService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/club/article")
public class ClubArticleController {

	@Autowired
	private ClubArticleService articleService;
	
	@PostMapping
	public ResponseEntity<ClubArticle> addArticle(@RequestBody ClubArticle article){
		String content = article.getContent().replaceAll("\\r\\n|\\r|\\n", "\n");
		article.setContent(content);
		ClubArticle saveArticle = articleService.saveArticle(article);
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
					Files.readAllBytes(file.toPath());
				}
			}
		}catch(IOException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			
		}
	}
	
	
	
}
