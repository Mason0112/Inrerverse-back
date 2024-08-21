package com.interverse.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.model.EventPhoto;
import com.interverse.demo.service.EventPhotoService;

@RestController
@RequestMapping("/eventPhoto")
public class EventPhotoController {
	
	@Autowired
	private EventPhotoService epService;
	
	@PostMapping
	public EventPhoto createEventPhoto(@RequestBody EventPhoto eventPhoto) {
		return epService.saveEventPhoto(eventPhoto);
	}
	
	@GetMapping
	public List<EventPhoto> getAllEventPhoto(){
		return epService.findAllEventPhoto();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getEventPhoto(@PathVariable Integer id, @RequestBody EventPhoto eventPhoto){
		EventPhoto existingEP = epService.findEventPhotoById(id);
		
		if(existingEP !=null) {
			return ResponseEntity.ok(existingEP);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無佌ID");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEventPhoto(@PathVariable Integer id){
		EventPhoto existingEP = epService.findEventPhotoById(id);
		
		
		if(existingEP ==null) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無佌ID");
		}
		epService.DeleteEventPhotoById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body("刪除成功");		
	}
}
