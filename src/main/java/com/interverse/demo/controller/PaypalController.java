package com.interverse.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.dto.PaypalDTO;
import com.interverse.demo.service.PaypalService;

@RestController
@RequestMapping("/paypal")
public class PaypalController {
	
	@Autowired
	private PaypalService paypalService;
	
	@PostMapping("/token")
    public PaypalDTO getToken() {
		return paypalService.getToken();
		
    }
	
	@PostMapping("/request")
    public String sendRequest() throws JSONException {
		PaypalDTO token = paypalService.getToken();
		return paypalService.sendRequest(token);
		
    }

	

	
}
