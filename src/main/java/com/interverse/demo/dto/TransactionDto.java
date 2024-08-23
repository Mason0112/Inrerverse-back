package com.interverse.demo.dto;

import java.time.LocalDateTime;

import com.interverse.demo.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {
	
	private Integer id;
	private String transactionNo;
	private String paymentMethod;
	private Long amount;
	private Boolean status;
	private LocalDateTime added;
	private User user;

}
