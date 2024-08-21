package com.interverse.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interverse.demo.model.Event;
import com.interverse.demo.model.EventParticipant;
import com.interverse.demo.model.User;
import com.interverse.demo.service.EventParticipantService;
import com.interverse.demo.service.EventService;
import com.interverse.demo.service.UserService;

@RestController
@RequestMapping("/eventParticipant")
public class EventParticipantController {
	
	@Autowired
	private EventParticipantService eptService;
	@Autowired
	private EventService eService;
	@Autowired
	private UserService uService;
	
	
	@PostMapping
	public ResponseEntity<?> createEpt(@RequestBody EventParticipant eventParticipant) {
		Event event = eService.findEventById(eventParticipant.getEventParticipantId().getEventId());
		User user = uService.findUserById(eventParticipant.getEventParticipantId().getUserId());
        
		 if (event == null || user == null) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Club or User ID");
	        }
		
		eventParticipant.setEvent(event);
		eventParticipant.setUser(user);
		
		EventParticipant saveEpt = eptService.saveEpt(eventParticipant);
		return ResponseEntity.status(HttpStatus.CREATED).body(saveEpt);
	}
	
	@GetMapping("/event/{eventId}")
	public ResponseEntity<?> getAllEventParticipant(@PathVariable Integer eventId){
		Event existingEvent = eService.findEventById(eventId);
		if(existingEvent ==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此ID");
		}
		
		List<EventParticipant> allEventParticipant = eptService.findAllEventParticipant(eventId);
		
		return ResponseEntity.status(HttpStatus.OK).body(allEventParticipant);
	}
}
