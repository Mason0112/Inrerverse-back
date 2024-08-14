package com.interverse.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.Order;
import com.interverse.demo.model.OrderReapository;

@Service
public class OrderService {
	
	@Autowired
	private OrderReapository orderReapo;
	
	public Order saveOrder(Order order) {
		
		return orderReapo.save(order);
	}
	
	public Order findOrderById(Integer id) {
		Optional<Order> optional = orderReapo.findById(id);
		
		if (optional.isPresent()) {
			Order result = optional.get();
			return result;
		}
		return null;
	}
	
	

}
