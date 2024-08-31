package com.interverse.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChargeRequest {
	private String token;
	private Long amount;

}
