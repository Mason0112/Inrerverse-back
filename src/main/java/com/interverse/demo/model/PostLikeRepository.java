package com.interverse.demo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
    boolean existsByUserIdAndPostId(Integer userId, Integer postId);
    void deleteByUserIdAndPostId(Integer userId, Integer postId);
}