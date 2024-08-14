package com.interverse.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.model.Category;
import com.interverse.demo.service.CategoryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
public class CategoriesController {
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/categoeies/{categoryid}")
	@ResponseBody
	public String  findCategory(@PathVariable("categoryid") Integer categoryid) {
		
		Category cateGoryById = categoryService.findCateGoryById(categoryid);
		
		if (cateGoryById != null) {
			return cateGoryById.getId()+""+cateGoryById.getName()+"";
		}
		return "No result";
			
	}
	
	
	@PostMapping("/categoeies/addpost")
	public Category postCategories(@RequestBody Category category) {
		
		return categoryService.saveCategory(category);
		
		
	}
	
	


}
