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
		try {
			
		String content = articleDTO.getContent().replaceAll("\\r\\n|\\r|\\n", "\n");
		articleDTO.setContent(content);
		ClubArticle saveArticle = articleService.createArticle(articleDTO);
		ClubArticleDTO saveArticleDTO = ClubArticleDTO.fromEntity(saveArticle);
		return new ResponseEntity<>(saveArticleDTO, HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/all/{clubId}")
    public ResponseEntity<List<ClubArticleDTO>> showClubAllArticle(@PathVariable Integer clubId) {
        try {
            List<ClubArticleDTO> articleDTOs = articleService.findAllArticleByClubId(clubId);
            articleService.loadBase64Photos(articleDTOs);
            return ResponseEntity.ok(articleDTOs);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
	
	@GetMapping("/oneArticle/{articleId}")
	public ResponseEntity<ClubArticleDTO> findArticleById(@PathVariable Integer articleId){
		ClubArticleDTO articleDTO = articleService.findArticleById(articleId);
		if(articleDTO != null) {
			System.out.println(articleDTO);
			return ResponseEntity.ok(articleDTO);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{articleId}")
	public ResponseEntity<ClubArticle> updateArticle(@PathVariable Integer articleId,
									@RequestBody ClubArticle clubArticle) {
		 ClubArticle updatedArticle = articleService.updateArticle(articleId, clubArticle);
	        return ResponseEntity.ok(updatedArticle);
	}
	
	@DeleteMapping("/{articleId}")
	public void deleteArticle(@PathVariable Integer articleId) {
		articleService.deleteArticleById(articleId);
	}
	
	
	
}
