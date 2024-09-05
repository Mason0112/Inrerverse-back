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
import com.interverse.demo.model.Club;
import com.interverse.demo.model.Event;
import com.interverse.demo.model.User;
import com.interverse.demo.service.ClubService;
import com.interverse.demo.service.EventService;
import com.interverse.demo.service.UserService;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/events")
public class EventController {

	@Autowired
	private EventService eService;

	@Autowired
	private ClubService clubService;

	@Autowired
	private UserService uService;

	// 轉換DTO
	private EventDTO convertToDTO(Event event) {
		Club club = clubService.findClubById(event.getClub().getId());
		String clubName = club.getClubName();
		
		User user = uService.findUserById(event.getEventCreator().getId());
		String nickname = user.getNickname();

		EventDTO dto = new EventDTO();

		dto.setId(event.getId());
		dto.setSource(event.getSource());
		dto.setEventName(event.getEventName());
		dto.setAdded(event.getAdded());
		dto.setCreatorName(nickname);
		dto.setClubName(clubName);
		dto.setClubId(event.getClub().getId());
		dto.setEventCreatorId(event.getEventCreator().getId());

		return dto;
	}

	// 建立社團活動
	@PostMapping("/new/Event")
	public EventDTO createEvent(@RequestBody Event event) {
		event.setSource(1);
		Event result = eService.saveEvent(event);
		return convertToDTO(result);
	}

	// 建立工作坊
	@PostMapping("/new/Class")
	public EventDTO createClass(@RequestBody Event event) {
		event.setSource(0);
		Event result = eService.saveEvent(event);
		return convertToDTO(result);
	}

	@GetMapping
	public List<EventDTO> getAllEvent() {
		List<Event> result = eService.findAllEvent();

		return result.stream().map(this::convertToDTO).collect(Collectors.toList());
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

	@PutMapping("/{id}/edit")
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
