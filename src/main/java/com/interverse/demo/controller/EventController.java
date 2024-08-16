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

import com.interverse.demo.model.Event;
import com.interverse.demo.service.EventService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/events")
public class EventController {
	
	@Autowired
	private EventService eService;
	
	@PostMapping
	public Event createEvent(@RequestBody Event event) {
		
		return eService.saveEvent(event);
	}
	
	@GetMapping
	public List<Event> getAllEvent(@RequestBody Event event) {
		return eService.findAllEvent();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getEventById(@PathVariable Integer id){
		Event result = eService.findEventById(id);
		
		if(result !=null) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.status(HttpStatus.OK).body("無此ID");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEvent(@PathVariable Integer id){
				
		if(eService.findEventById(id)==null) {	
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此ID");
		}
		
		eService.deleteEventById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body("Delete Successful");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateEvent(@PathVariable Integer id,@RequestBody Event event){
		
		Event existEvent = eService.findEventById(id);
		
		if(existEvent ==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此ID");
		}
		event.setId(id);
		event.setAdded(existEvent.getAdded());
		eService.saveEvent(event);
		
		return ResponseEntity.status(HttpStatus.OK).body("Update Successful");
	}
}
