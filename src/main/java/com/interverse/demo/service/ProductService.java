package com.interverse.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.interverse.demo.model.Product;
import com.interverse.demo.model.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepo;
	
	
	public Product saveProduct(Product product) {
		return productRepo.save(product);
		
		
	}
	
	
	public Product findProductById(Integer id) {
		
		Optional<Product> optional = productRepo.findById(id);
		
		if (optional.isPresent()) {
			Product result = optional.get();
			return result;
		}
		
		return null;
	}
	
}
