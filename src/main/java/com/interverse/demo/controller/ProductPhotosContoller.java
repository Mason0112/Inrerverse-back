package com.interverse.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.interverse.demo.service.ProductPhotoService;


@RestController
@RequestMapping("/product-photos")
public class ProductPhotosContoller {
	
	@Autowired
	private ProductPhotoService productPhotoService;
	
	@PostMapping
	public String  createProductPhoto(@RequestParam("file") MultipartFile file,
									@RequestParam("prductId") Integer id)throws IOException {
		
		productPhotoService.createProductPhotos(file, id);
		
		return "ok";
		
	}
	
	

}
