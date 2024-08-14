package com.interverse.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.OrderDetail;
import com.interverse.demo.model.OrderDetailRepository;

@Service
public class OrderDetailService {

	
	@Autowired
	private OrderDetailRepository orderDetailRepo;

	
	public OrderDetail addToOrder(OrderDetail orderDetail) {
		
		return orderDetailRepo.save(orderDetail);
	}
}
