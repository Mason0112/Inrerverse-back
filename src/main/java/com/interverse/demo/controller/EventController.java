package com.interverse.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.interverse.demo.dto.EventDTO;
import com.interverse.demo.model.Event;
import com.interverse.demo.service.EventService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/events")
public class EventController {

	@Autowired
	private EventService eService;
	
	//轉換DTO
	private EventDTO convertToDTO(Event event) {
		EventDTO dto = new EventDTO();
		
		dto.setId(event.getId());
		dto.setSource(event.getSource());
		dto.setEventName(event.getEventName());
		dto.setAdded(event.getAdded());
		dto.setCreatorName(event.getEventCreator().getNickname()); // 假设 User 有一个 getUsername() 方法
		
		if (event.getClub() != null) {
	        dto.setClubName(event.getClub().getClubName());
	    } else {
	        dto.setClubName(null); // 或者設置為一個默認值，比如 "No Club"
	    }
		
		return dto;
	}

	@PostMapping
	public EventDTO createEvent(@RequestBody Event event) {
		Event result = eService.saveEvent(event);
		return convertToDTO(result);
	}

	@GetMapping
	public List<EventDTO> getAllEvent() {
		List<Event> result = eService.findAllEvent();
		
		return result.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getEvent(@PathVariable Integer id) {
		Event result = eService.findEventById(id);

		if (result != null) {
			EventDTO eventDTO = convertToDTO(result);
			return ResponseEntity.ok(eventDTO);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此ID");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEvent(@PathVariable Integer id) {

		if (eService.findEventById(id) == null) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此ID");
		}

		eService.deleteEventById(id);

		return ResponseEntity.status(HttpStatus.OK).body("Delete Successful");
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateEvent(@PathVariable Integer id, @RequestBody Event event) {

		Event existEvent = eService.findEventById(id);

		if (existEvent == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("無此ID");
		}
		event.setId(id);
		event.setAdded(existEvent.getAdded());
				
		convertToDTO(eService.saveEvent(event));
		
		return ResponseEntity.status(HttpStatus.OK).body("Update Successful");
	}
}
