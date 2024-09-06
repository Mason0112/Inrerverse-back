
package com.interverse.demo.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.interverse.demo.model.ClubArticle;

import lombok.Data;

@Data
public class ClubArticleDTO {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime added;
    private int likeCount;
    private List<ArticlePhotoDTO> photos;
    private Integer userId;


    public static ClubArticleDTO fromEntity(ClubArticle entity) {
        ClubArticleDTO dto = new ClubArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setAdded(entity.getAdded());
        dto.setLikeCount(entity.getLikeCount());
        
        // 檢查 user 是否為 null
        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }
        
        // 檢查 photos 是否為 null
        if (entity.getPhotos() != null) {
            dto.setPhotos(entity.getPhotos().stream()
                .map(ArticlePhotoDTO::fromEntity)
                .collect(Collectors.toList()));
        } else {
            dto.setPhotos(new ArrayList<>()); // 設置一個空列表
        }
        
        return dto;
    }
}
