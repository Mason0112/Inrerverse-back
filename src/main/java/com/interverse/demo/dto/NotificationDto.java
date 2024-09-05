package com.interverse.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDto {
	
	private Integer id;
	private Integer source;
	private String content;
	private Boolean status;
	private Integer senderId;
	private Integer receiverId;

}
