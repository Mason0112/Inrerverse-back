package com.interverse.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.model.Category;
import com.interverse.demo.model.Product;
import com.interverse.demo.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("product/addpost")
	public Product postProduct(@RequestBody Product product) {
		
		return productService.saveProduct(product);
		
	}
	
	@GetMapping("/product/{productid}")
	@ResponseBody
	public String  findCategory(@PathVariable("productid") Integer productid) {
		
		Product productById = productService.findProductById(productid);
		
		if (productById != null) {
			
			return productById.getId()+"" +productById.getDescription();
		}
		return "No result";
	}
	
	
}
