package com.interverse.demo.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventPhotoRepository extends JpaRepository<EventPhoto, Integer> {
	
	//尋找event中所有照片
		List<EventPhoto> findByEventId(Integer eventId);
}
