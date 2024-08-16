package com.interverse.demo.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPhotosRepository extends JpaRepository<ProductPhotos, Integer> {

	
	    List<ProductPhotos> findByProducts(Product product);
	
}
