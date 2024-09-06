
package com.interverse.demo.dto;

import java.time.LocalDateTime;
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


    public static ClubArticleDTO fromEntity(ClubArticle entity) {
        ClubArticleDTO dto = new ClubArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setAdded(entity.getAdded());
        dto.setLikeCount(entity.getLikeCount());
        dto.setPhotos(entity.getPhotos().stream()
                .map(ArticlePhotoDTO::fromEntity)
                .collect(Collectors.toList()));
        return dto;
    }
}
