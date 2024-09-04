package com.interverse.demo.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.interverse.demo.dto.LinePayDTO;

public class PostApiUtil {
	
	
	public  void sendPost(LinePayDTO dto) {
		
		RestTemplate restTemplate = new RestTemplate();
		//Post.headers設定
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("X-LINE-ChannelId", dto.getChannelId());
		headers.add("X-LINE-Authorization", dto.getSignature());
		headers.add("X-LINE-Authorization-Nonce", dto.getNonce());
		
		
	}
		
		
		
	
}
