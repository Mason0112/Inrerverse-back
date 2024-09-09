package com.interverse.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.dto.ArticleCommentDTO;
import com.interverse.demo.model.ClubArticleCommentRepository;
import com.interverse.demo.service.ClubArticlesCommentsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/club/article/comment")
public class ArticleCommentController {

	@Autowired
	private ClubArticlesCommentsService commentService;
	
	@PostMapping
	public ResponseEntity<ArticleCommentDTO> addComment(@RequestBody ArticleCommentDTO articleDTO) {
		ArticleCommentDTO savedComment = commentService.createComment(articleDTO);
		return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<ArticleCommentDTO>> showAricleAllComment(@PathVariable Integer articleId) {
		
	}
	
}
