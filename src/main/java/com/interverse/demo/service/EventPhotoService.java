package com.interverse.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interverse.demo.model.EventPhoto;
import com.interverse.demo.model.EventPhotoRepository;

@Service
public class EventPhotoService {
	
	@Autowired
	private EventPhotoRepository epRepo;
	
	public EventPhoto saveEventPhoto(EventPhoto eventPhoto) {
		return epRepo.save(eventPhoto);
	}
	
	public EventPhoto findEventPhotoById(Integer id) {
		
		Optional<EventPhoto> optional= epRepo.findById(id);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	public List<EventPhoto> findAllEventPhoto(){
		return epRepo.findAll();
	}
	
	public void DeleteEventPhotoById(Integer id) {
		epRepo.deleteById(id);
	}
}
