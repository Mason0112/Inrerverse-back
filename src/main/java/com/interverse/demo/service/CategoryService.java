package com.interverse.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.Category;
import com.interverse.demo.model.CategoryRepository;



@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoriesRepo;
	
	
	public Category saveCategory(Category category) {
		
		return categoriesRepo.save(category);
		
	}
	
	public Category findCateGoryById(Integer idInteger) {
		Optional<Category> optional = categoriesRepo.findById(idInteger);
		
		if(optional.isPresent()) {
			Category result = optional.get();
			return result;
		}
		
		return null;
	}
}
