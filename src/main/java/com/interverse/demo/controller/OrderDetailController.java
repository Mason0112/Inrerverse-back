package com.interverse.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.dto.OrderDetailDTO;
import com.interverse.demo.model.Order;
import com.interverse.demo.model.OrderDetail;
import com.interverse.demo.model.OrderDetailId;
import com.interverse.demo.model.Product;
import com.interverse.demo.service.OrderDetailService;
import com.interverse.demo.service.OrderService;
import com.interverse.demo.service.ProductService;

@RestController
public class OrderDetailController {

	@Autowired
	private OrderDetailService orderDetailService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;
	
	
		
	}
	
