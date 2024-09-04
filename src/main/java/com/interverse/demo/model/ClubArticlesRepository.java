package com.interverse.demo.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubArticlesRepository extends JpaRepository<ClubArticle, Integer> {
	
	@Query("from ClubArticle where club.id = :id order by added desc")
	List<ClubArticle> findAllArticleByClubId(@Param("id") Integer clubId);
}
