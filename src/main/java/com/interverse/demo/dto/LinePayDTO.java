package com.interverse.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinePayDTO {

	private String ChannelSecret;
	private String requestUri;
	private String nonce;
	private String signature;
	
}
