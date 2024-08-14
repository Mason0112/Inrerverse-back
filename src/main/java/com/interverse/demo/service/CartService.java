package com.interverse.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.Cart;
import com.interverse.demo.model.CartRepository;


@Service
public class CartService {
	
	@Autowired
	private CartRepository cartRepo;
	
	public Cart addToCart(Cart cart) {
		
		return cartRepo.save(cart);
	}
	
	

}
