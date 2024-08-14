package com.interverse.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.model.Order;
import com.interverse.demo.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/order/addpost")
	public String postOrder(@RequestBody Order order) {
		orderService.saveOrder(order);
		
		return "ok";
		
	}
	
}
