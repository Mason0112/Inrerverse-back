
package com.interverse.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ClubArticleDTO {
	private Integer id;
	private Integer userId;
	private Integer clubId;
	private String title;
	private String content;
	private LocalDateTime added;
	private int likeCount;
	private List<String> photoUrls;
}
