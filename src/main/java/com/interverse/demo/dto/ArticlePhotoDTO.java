package com.interverse.demo.dto;

import com.interverse.demo.model.ArticlePhoto;

import lombok.Data;

@Data
public class ArticlePhotoDTO {
    private Integer id;
    private String url;
    private String base64Photo;


    public static ArticlePhotoDTO fromEntity(ArticlePhoto entity) {
        ArticlePhotoDTO dto = new ArticlePhotoDTO();
        dto.setId(entity.getId());
        dto.setUrl(entity.getUrl());
        return dto;
    }
}