package com.interverse.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.dto.LinePayDTO;
import com.interverse.demo.dto.OrderDTO;
import com.interverse.demo.service.LinePayService;

@RestController
@RequestMapping("/linePay")
public class LinePayController {
	
	@Autowired
	private LinePayService linePayService;
	
	
	@PostMapping("/pay")
    public LinePayDTO linpay(@RequestBody OrderDTO dto) {
		return linePayService.LinePayPost(dto);
		
    }
	
	
	
	
	
}
